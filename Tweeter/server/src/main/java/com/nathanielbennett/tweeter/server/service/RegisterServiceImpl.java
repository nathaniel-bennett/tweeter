package com.nathanielbennett.tweeter.server.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.RegisterService;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;
import com.nathanielbennett.tweeter.server.dao.UserDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.exceptions.HandleTakenException;
import com.nathanielbennett.tweeter.server.exceptions.WeakPasswordException;
import com.nathanielbennett.tweeter.server.model.StoredUser;
import com.nathanielbennett.tweeter.server.util.PasswordHasher;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

public class RegisterServiceImpl implements RegisterService {

    String bucket_name = "cs340nccimagebucket";

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (request == null) {
            throw new BadRequestException("Message body missing or malformed");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new BadRequestException("Registration request missing username");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new WeakPasswordException("You must create a password to register");
        }

        if (request.getPassword().length() < 8) {
            throw new WeakPasswordException("Password must be at least 8 characters long");
        }

        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            throw new BadRequestException("First name missing from registration request");
        }

        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            throw new BadRequestException("Last name missing from registration request");
        }

        if (request.getImage() == null || request.getImage().length == 0) {
            throw new BadRequestException("A profile picture is required in order to register");
        }

        UserDAO userDAO = getUserDAO();

        StoredUser existingUser = userDAO.getUser(request.getUsername());
        if (existingUser != null) {
            throw new HandleTakenException("Requested user handle is taken; please try another.");
        }



        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();

        String bucket_key = UUID.randomUUID().toString();

        try {
            InputStream byteStream = new ByteArrayInputStream(request.getImage());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.addUserMetadata("content-type", "image/png");

            PutObjectRequest objectRequest = new PutObjectRequest(bucket_name, bucket_key, byteStream, metadata);
            objectRequest.setKey(request.getUsername());

            PutObjectResult response = s3.putObject(objectRequest);


        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            throw new DataAccessException("S3 Bucket could not be accessed to store image");
        }

        StoredUser storedUser = requestToStoredUser(request);

        userDAO.createUser(storedUser);

        User user = storedUser.toUser();
        AuthTokenDAO authTokenDAO = getAuthTokenDAO();
        AuthToken authToken = authTokenDAO.createToken(request.getUsername());
        if (authToken == null) {
            throw new DataAccessException("Couldn't create auth token...");
        }

        return new RegisterResponse(user, authToken);
    }

    /**
     * Returns an instance of {@link UserDAO}. Allows mocking of the UserDAO class
     * for testing purposes. All usages of UserDAO should get their UserDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    public StoredUser requestToStoredUser(RegisterRequest request) {
        PasswordHasher hasher = new PasswordHasher();
        String hashedPassword = hasher.hash(request.getPassword());
        String imageLocation = "https://" + bucket_name + ".s3-us-west-2.amazonaws.com/" + request.getUsername();
        return new StoredUser(request.getFirstName(), request.getLastName(), hashedPassword, request.getUsername(), imageLocation, 0, 0);
    }
}
