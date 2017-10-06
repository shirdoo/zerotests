package refocus;

import com.google.common.base.Preconditions;
import com.squareup.okhttp.*;
import refocus.pojo.Subject;

import java.io.IOException;

/**
 * @author sbabu
 * @since 10/5/17
 */
public class RefocusClient {
    private static final String SUBJECT_ENDPOINT= "%s/v1/subjects";
    private static final String ASPECT_ENDPOINT = "%s/v1/aspects";

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
        return Subject.fromJson(executeGet(String.format(SUBJECT_ENDPOINT + "/%s", urlEndpoint, subjectName)));
    }

    public void postSubject(Subject subject) throws IOException {
        executePut(String.format(SUBJECT_ENDPOINT, urlEndpoint), subject.toJson());
    }

    public void deleteSubject(String subjectName) throws Exception {
        executeDelete(String.format(SUBJECT_ENDPOINT + "/%s", urlEndpoint, subjectName));
    }

    public String getAspect(String aspectName) throws IOException {
        return executeGet(String.format(ASPECT_ENDPOINT, urlEndpoint, aspectName));
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
                Preconditions.checkState(r.isSuccessful(), r.body() + r.toString());
                return body.string().trim();
            }
        }
    }

    private String executePut(String url, String body)  throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", accessToken)
                .post(RequestBody.create(mediaType, body))
                .build();

        Response r = client.newCall(request).execute();
        try (ResponseBody responseBody = r.body()) {
            Preconditions.checkState(r.isSuccessful());
            return responseBody.string().trim();
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
            Preconditions.checkState(r.isSuccessful());
            return body.string().trim();
        }
    }

}
