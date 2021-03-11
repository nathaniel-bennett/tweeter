package com.nathanielbennett.tweeter.client.model.net;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
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

    private final String serverHost = "192.168.1.71";
    private final String serverPort = "4040";

    private WebRequestStrategy webRequestStrategy;

    public interface WebRequestStrategy {

        boolean hasRequestBody();

        String getRequestPath();

        String getRequestMethod();

        TweeterAPIResponse formResponse(String serializedResponse);

        TweeterAPIResponse formFailureResponse(int httpResponseCode);

        TweeterAPIResponse formIOErrorResponse(String message);
    }

    public ClientCommunicator(WebRequestStrategy strategy) {
        this.webRequestStrategy = strategy;
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
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


    public TweeterAPIResponse doWebRequest(TweeterAPIRequest request, AuthToken authToken) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + webRequestStrategy.getRequestPath());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(webRequestStrategy.getRequestMethod());
            connection.setDoOutput(webRequestStrategy.hasRequestBody());
            if (authToken != null) {
                connection.addRequestProperty("Authorization", authToken.toString());
            }

            connection.connect();


            if (webRequestStrategy.hasRequestBody()) {
                String serializedRequest = "put Gson serializer here with APIRequest";
                OutputStream os = connection.getOutputStream();
                writeString(serializedRequest, os);
                os.close();
            }

            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:
                    InputStream responseBody = connection.getInputStream();
                    String responseData = readString(responseBody);
                    responseBody.close();

                    return webRequestStrategy.formResponse(responseData);

                default: // TODO: add additional responses for various HTTP responses?
                    return webRequestStrategy.formFailureResponse(connection.getResponseCode());
            }
        } catch (IOException e) {
            return webRequestStrategy.formIOErrorResponse(e.getMessage());
        }
    }
}
