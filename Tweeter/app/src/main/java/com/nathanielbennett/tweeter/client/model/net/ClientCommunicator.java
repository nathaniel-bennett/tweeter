package com.nathanielbennett.tweeter.client.model.net;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class ClientCommunicator {

    private final String serverHost = "https://mg0d2gdm2b.execute-api.us-west-2.amazonaws.com/Development";
    private final String serverPort = "";

    private WebRequestStrategy webRequestStrategy;

    public interface WebRequestStrategy {

        boolean hasRequestBody();

        String getRequestPath(TweeterAPIRequest request);

        String getRequestMethod();

        TweeterAPIResponse formResponse(String serializedResponse, int httpResponseCode);
    }

    public ClientCommunicator(WebRequestStrategy strategy) {
        this.webRequestStrategy = strategy;
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[4096];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


    public TweeterAPIResponse doWebRequest(TweeterAPIRequest request, AuthToken authToken) throws IOException {
        URL url = new URL(serverHost + webRequestStrategy.getRequestPath(request));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(webRequestStrategy.getRequestMethod());
        connection.setDoOutput(webRequestStrategy.hasRequestBody());
        if (authToken != null) {
            connection.addRequestProperty("Authorization", authToken.toString());
        }

        connection.connect();

        if (webRequestStrategy.hasRequestBody()) {
            Serializer serializer = new Serializer();
            String serializedRequest = serializer.serialize(request);
            OutputStream os = connection.getOutputStream();
            writeString(serializedRequest, os);
            os.close();
        }

        InputStream responseBody = connection.getInputStream();
        String responseData = readString(responseBody);
        responseBody.close();
        return webRequestStrategy.formResponse(responseData, connection.getResponseCode());
    }
}
