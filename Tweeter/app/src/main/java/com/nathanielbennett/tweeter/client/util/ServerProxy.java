package com.example.familymapclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import Request.*;
import Response.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerProxy {

    private String serverHost;
    private String serverPort;

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

    public Response Register(Request rr){
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
                return gson.fromJson(respData, MessageResponse.class);
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

    public Response Login(Request lr){
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
                return gson.fromJson(respData, MessageResponse.class);
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

    public MessageResponse clear(){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/clear");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.connect();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, MessageResponse.class);
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

    public GetPersonFromIDResponse getPersonByID(String authToken, String personID){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person/" + personID);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, GetPersonFromIDResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred while retrieving the current user's person info");
            e.printStackTrace();
        }
        return null;
    }

    public GetAllFamilyMembersResponse getAllPersonsForCurrentUser(String authToken){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, GetAllFamilyMembersResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred while retrieving username-associated people");
            e.printStackTrace();
        }
        return null;
    }

    public GetAllEventsForAllFamilyMembersResponse getAllEventsForCurrentUser(String authToken){
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                return gson.fromJson(respData, GetAllEventsForAllFamilyMembersResponse.class);
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error occurred while retrieving username-associated events");
            e.printStackTrace();
        }
        return null;
    }

}
