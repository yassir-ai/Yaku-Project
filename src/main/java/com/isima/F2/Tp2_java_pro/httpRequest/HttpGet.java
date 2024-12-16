package com.isima.F2.Tp2_java_pro.httpRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpGet
{
    public OkHttpClient _client;
    public Request _request;
    public HttpGet(String url)
    {
        _client = new OkHttpClient();
        _request = new Request.Builder().url(url).build();
    }


    public JsonNode GetResponse() throws IOException {
        Response response = _client.newCall(_request).execute();

        if (response.isSuccessful()) {

            ObjectMapper objectMapper = new ObjectMapper();
            // Convertir la chaîne JSON en objet JsonNode
            String reposStr = response.body().string();
            JsonNode jsonNode = objectMapper.readTree(reposStr);

            return jsonNode;
        }

        return null;
    }
}
