package com.salesforce.monitored;

import com.google.common.base.Preconditions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

/**
 * @author sbabu
 * @since 10/5/2017
 */
public class IdGeneratorClient {
    private static final String ID_SERVICE_ENDPOINT = "%s/api/v1/id-generator/nextKey/%s/%s";
    private final String idServiceUrl;

    private final OkHttpClient client = new OkHttpClient();
    public IdGeneratorClient() {
        this.idServiceUrl = System.getenv("ID_GENERATOR_URL");
        Preconditions.checkNotNull(idServiceUrl, "Missing required id generator endpoint on environment var ID_GENERATOR_URL");
    }

    public String getUrl() {
        return idServiceUrl;
    }

    public String getKey(String orgId, String keyPrefix) throws IOException {
        Request request = new Request.Builder()
                .url(String.format(ID_SERVICE_ENDPOINT, idServiceUrl, orgId, keyPrefix))
                .get()
                .build();

        Response r = client.newCall(request).execute();
        try (ResponseBody body = r.body()) {
            Preconditions.checkState(r.isSuccessful());
            return body.string().trim();
        }
    }
}