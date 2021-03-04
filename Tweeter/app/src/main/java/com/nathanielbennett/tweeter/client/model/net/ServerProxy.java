package com.nathanielbennett.tweeter.client.model.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.nathanielbennett.tweeter.model.service.request.*;
import com.nathanielbennett.tweeter.model.service.response.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerProxy {

    private final String serverHost;
    private final String serverPort;

    ServerProxy(String serverHost, String serverPort){
        this.serverHost = serverHost;
        this.serverPort = serverPort;
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

    public FollowResponse getFollowing(String authToken, String username){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/" + username + "/following");
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
            System.out.println("Error occurred while retrieving " + username + "'s following info");
            e.printStackTrace();
        }
        return null;
    }

    public FollowResponse getFollowers(String authToken, String username){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/" + username + "/followers");
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
            System.out.println("Error occurred while retrieving " + username + "'s followers info");
            e.printStackTrace();
        }
        return null;
    }

    public StatusResponse getStory(String authToken, String username){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/" + username + "/story");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
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

}
