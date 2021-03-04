package com.nathanielbennett.tweeter.client.model.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.request.*;
import com.nathanielbennett.tweeter.model.service.response.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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


    public TweeterAPIResponse doWebRequest(TweeterAPIRequest request) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + webRequestStrategy.getRequestPath());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(webRequestStrategy.getRequestMethod());
            connection.setDoOutput(webRequestStrategy.hasRequestBody());
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
                    responseBody.close(); // TODO: should we actually do this???

                    return webRequestStrategy.formResponse(responseData);

                default: // TODO: add additional responses for various HTTP responses?
                    return webRequestStrategy.formFailureResponse(connection.getResponseCode());
            }
        } catch (MalformedURLException e) {
            return null; // TODO: change this!!!
        } catch (IOException e) {
            return null; // TODO: change this!!!!
        }
    }



    public TweeterAPIResponse Register(TweeterAPIRequest rr){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String request = gson.toJson(rr);
            OutputStream os = http.getOutputStream();
            writeString(request, os);
            os.close();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, LoginResponse.class);
            }
            else if (http.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED){
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, TweeterAPIResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred while registering");
            e.printStackTrace();
        }
        return null;
    }

    public TweeterAPIResponse Login(TweeterAPIRequest lr){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String request = gson.toJson(lr);
            OutputStream os = http.getOutputStream();
            writeString(request, os);
            os.close();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, LoginResponse.class);
            }
            else if (http.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED){
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, TweeterAPIResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred while signing in");
            e.printStackTrace();
        }
        return null;
    }

    public TweeterAPIResponse clear(){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/clear");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.connect();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, TweeterAPIResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred while clearing the database");
            e.printStackTrace();
        }
        return null;
    }

    public FollowResponse getFollowing(String username){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/" + username + "/following");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, FollowResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred while retrieving " + username + "'s following info");
            e.printStackTrace();
        }
        return null;
    }

    public FollowResponse getFollowers(String username){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/" + username + "/followers");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, FollowResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred while retrieving " + username + "'s followers info");
            e.printStackTrace();
        }
        return null;
    }

    public StatusResponse getStory(String username){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/" + username + "/story");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, StatusResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred while retrieving " + username + "'s story");
            e.printStackTrace();
        }
        return null;
    }

    public TweeterAPIResponse post(String authToken, String status, String username){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/" + username + "/post");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, FollowResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred while posting " + username + "'s status");
            e.printStackTrace();
        }
        return null;
    }

    public FollowUserResponse follow(String authToken, String currentUser, String userToFollow){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/" + userToFollow + "/following/add/" + currentUser);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, FollowUserResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred when " + currentUser + " tried to follow" + userToFollow);
            e.printStackTrace();
        }
        return null;
    }

    public FollowUserResponse unfollow(String authToken, String currentUser, String userToUnfollow){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/" + userToUnfollow + "/following/add/" + currentUser);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, FollowUserResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred when " + currentUser + " tried to unfollow" + userToUnfollow);
            e.printStackTrace();
        }
        return null;
    }
}
