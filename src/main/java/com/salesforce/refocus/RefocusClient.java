package com.salesforce.refocus;

import com.google.common.base.Preconditions;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.salesforce.GsonUtils.GSON;

/**
 * @author sbabu
 * @since 10/5/17
 */
public class RefocusClient {
    private static final String SUBJECT_ENDPOINT= "%s/v1/subjects";
    private static final String ASPECT_ENDPOINT = "%s/v1/aspects";
    private static final String SAMPLE_ENDPOINT = "%s/v1/samples/upsert/bulk";


    private final String urlEndpoint;
    private final String accessToken;
    private final OkHttpClient client = new OkHttpClient();

    public RefocusClient() {
        this.urlEndpoint = System.getenv("REFOCUS_ENDPOINT");
        Preconditions.checkNotNull(urlEndpoint, "Refocus endpoint must be specified as an env variable named REFOCUS_ENDPOINT, ie: https://zero-focus.herokuapp.com");
        this.accessToken = System.getenv("REFOCUS_ACCESS_TOKEN");
        Preconditions.checkNotNull(accessToken, "A token against your refocus endpoint must be defined as an env variable named: REFOCUS_ACCESS_TOKEN. See https://salesforce.github.io/refocus/docs/10-security.html#api-tokens");
    }

    public Subject getSubject(String subjectName) throws IOException {
        String json = executeGet(String.format(SUBJECT_ENDPOINT + "/%s", urlEndpoint, subjectName));
        return GSON.fromJson(json, Subject.class);
    }

    public List<Subject> getSubjects() throws IOException {
        String json = executeGet(String.format(SUBJECT_ENDPOINT, urlEndpoint));
        return GSON.fromJson(json, new TypeToken<List<Subject>>(){}.getType());
    }

    public void createSubject(Subject subject) throws IOException {
        executePost(String.format(SUBJECT_ENDPOINT, urlEndpoint), GSON.toJson(subject));
    }

    public void updateSubject(Subject subject) throws IOException {
        executePatch(String.format(SUBJECT_ENDPOINT + "/%s", urlEndpoint, subject.getName()), GSON.toJson(subject));
    }

    public void deleteSubject(String subjectName) throws Exception {
        executeDelete(String.format(SUBJECT_ENDPOINT + "/%s", urlEndpoint, subjectName));
    }

    public List<Aspect> getAspects() throws IOException {
        String jsonResponse = executeGet(String.format(ASPECT_ENDPOINT, urlEndpoint));
        return GSON.fromJson(jsonResponse, new TypeToken<List<Aspect>>(){}.getType());
    }

    public Aspect getAspect(String aspectName) throws IOException {
        String json = executeGet(String.format(ASPECT_ENDPOINT + "/%s", urlEndpoint, aspectName));
        return GSON.fromJson(json, Aspect.class);
    }

    public void createAspect(Aspect aspect) throws IOException {
        executePost(String.format(ASPECT_ENDPOINT, urlEndpoint), GSON.toJson(aspect));
    }

    public void updateAspect(Aspect aspect) throws IOException {
        executePatch(String.format(ASPECT_ENDPOINT + "/%s", urlEndpoint, aspect.getName()), GSON.toJson(aspect));
    }

    public void deleteAspect(String aspectName) throws IOException {
        executeDelete(String.format(ASPECT_ENDPOINT + "/%s", urlEndpoint, aspectName));
    }

    public void postSamples(Set<Sample> samples) throws IOException {
        executePost(String.format(SAMPLE_ENDPOINT, urlEndpoint), GSON.toJson(samples));
    }

    private String executeGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", accessToken)
                .get()
                .build();

        Response r = client.newCall(request).execute();
        try (ResponseBody body = r.body()) {
            if (!r.isSuccessful() && r.code() == 404) {
                return null; // refocus returns 404 for subjects not in DB
            } else {
                String respBody = body.string();
                Preconditions.checkState(r.isSuccessful(), respBody);
                return respBody.trim();
            }
        }
    }

    private void executePost(String url, String body)  throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", accessToken)
                .post(RequestBody.create(mediaType, body))
                .build();

        System.out.println(String.format("Running POST against %s with body %s", url, body));
        Response r = client.newCall(request).execute();
        try (ResponseBody responseBody = r.body()) {
            Preconditions.checkState(r.isSuccessful(), "Error executing POST against refocus: " + responseBody.string());
        }
    }

    private void executePatch(String url, String body) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", accessToken)
                .patch(RequestBody.create(mediaType, body))
                .build();

        Response r = client.newCall(request).execute();
        try (ResponseBody responseBody = r.body()) {
            Preconditions.checkState(r.isSuccessful(), "Error executing POST against refocus: " + responseBody.string());
        }
    }

    private String executeDelete(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", accessToken)
                .delete()
                .build();

        Response r = client.newCall(request).execute();
        try (ResponseBody body = r.body()) {
            String respBody = body.string();
            Preconditions.checkState(r.isSuccessful(), respBody);
            return respBody.trim();
        }
    }

}
