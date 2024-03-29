package com.nathanielbennett.tweeter.server;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class testMain {

    public static void main(String[] args) {

    }

    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-west-2")
            .build();
    DynamoDB db = new DynamoDB(client);
    Table table = db.getTable("user");

    public boolean createUser(RegisterRequest rr) {
        try{
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("alias", rr.getUsername())
                            .withString("first_name", rr.getFirstName())
                            .withString("last_name", rr.getLastName())
                            .withString("imageURL", "https://i.imgur.com/OvMZBs9.jpg")
                            .withString("password", rr.getPassword())
                            .withInt("follower_count", 0)
                            .withInt("followee_count", 0));
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public User getUser(String alias) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", alias);
        Item outcome = null;
        User user = new User();
        try{
            outcome = table.getItem(spec);
            user.setFirstName(outcome.getString("first_name"));
            user.setLastName(outcome.getString("last_name"));
            user.setImageUrl(outcome.getString("imageURL"));
            user.setFollowerCount(outcome.getInt("follower_count"));
            user.setFolloweeCount(outcome.getInt("followee_count"));
            user.setAlias(alias);
            user.setImageBytes(imageToByteArray(outcome.getString("imageURL")));
        } catch (Exception e){
            e.printStackTrace();
        }
        if (user.getAlias() != null){
            return user;
        }
        return null;
    }

    private byte[] imageToByteArray(String url) throws IOException {
        URL u = new URL(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (InputStream is = u.openStream()) {
            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
            int n;

            while ((n = is.read(byteChunk)) > 0) {
                baos.write(byteChunk, 0, n);
            }
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %s: %s", u.toExternalForm(), e.getMessage());
            e.printStackTrace();
            // Perform any other exception handling that's appropriate.
        }
        return baos.toByteArray();
    }


}
