package com.nathanielbennett.tweeter.client.model.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.request.*;
import com.nathanielbennett.tweeter.model.service.response.*;


public class ClientCommunicator {

    private final String serverHost = "192.168.1.71";
    private final String serverPort = "4040";

    private WebRequestStrategy webRequestStrategy;

    public interface WebRequestStrategy {

        String getRequestPath();

        String getRequestMethod();

        TweeterAPIResponse formResponse(String serializedResponse);

        TweeterAPIResponse formFailureResponse(int httpResponseCode);
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
        URL url = new URL("http://" + serverHost + ":" + serverPort + webRequestStrategy.getRequestPath());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(webRequestStrategy.getRequestMethod());
        connection.setDoOutput(true);
        if (authToken != null) {
            connection.addRequestProperty("Authorization", authToken.toString());
        }

        connection.connect();

        String serializedRequest = "put Gson serializer here with APIRequest";
        OutputStream os = connection.getOutputStream();
        writeString(serializedRequest, os);
        os.close();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            String responseData = readString(responseBody);
            responseBody.close();

            return webRequestStrategy.formResponse(responseData);
        } else {
            return webRequestStrategy.formFailureResponse(connection.getResponseCode());
        }
    }
}
