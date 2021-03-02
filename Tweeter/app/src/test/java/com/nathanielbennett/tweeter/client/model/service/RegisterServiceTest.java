package com.nathanielbennett.tweeter.client.model.service;

import com.nathanielbennett.tweeter.client.model.net.ServerFacade;
import com.nathanielbennett.tweeter.client.model.service.RegisterService;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class RegisterServiceTest {

    private RegisterRequest nullUsername;
    private RegisterRequest nullPassword;
    private RegisterRequest badRequest;
    private RegisterRequest goodRequest;
    private RegisterRequest nullFirstName;
    private RegisterRequest nullLastName;
    private RegisterRequest nullImage;

    private RegisterResponse badResponse;
    private RegisterResponse goodResponse;

    private RegisterService registerServiceSpy;
    private ServerFacade mockServerFacade;

    @BeforeEach
    public void setup() {

        nullUsername = new RegisterRequest("Joe", "Johansen", null, "mashedpotatoes", new byte[0]);
        nullPassword = new RegisterRequest("Joe", "Johansen", "joeyboy", null, new byte[0]);
        badRequest = new RegisterRequest("dummy", "User", "dummyUser", "dummyUser", new byte[0]);
        goodRequest = new RegisterRequest("Joe", "Johansen", "joeyboy", "mashedpotatoes", new byte[0]);
        nullFirstName = new RegisterRequest(null, "Johansen", "joeyboy", "password", new byte[0]);
        nullLastName = new RegisterRequest("Joe", null, "joeyboy", "password", new byte[0]);
        nullImage = new RegisterRequest("Joe", "Johansen", "joeyboy", "password", null);

        badResponse = new RegisterResponse("Username taken; please try another username");
        goodResponse = new RegisterResponse("success!");

        mockServerFacade = Mockito.mock(ServerFacade.class);
        registerServiceSpy = Mockito.spy(new RegisterService());

        Mockito.when(mockServerFacade.register(badRequest)).thenReturn(badResponse);
        Mockito.when(mockServerFacade.register(goodRequest)).thenReturn(goodResponse);
        Mockito.when(registerServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verifies that when a null request is passed into {@link RegisterService#register(RegisterRequest)} (RegisterRequest)}
     * a Null Pointer Exception is thrown.
     */
    @Test
    public void testRegister_nullRequest_throwsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            registerServiceSpy.register(null);
        });
    }

    /**
     * Verifies that when a request with a null username is passed into {@link RegisterService#register(RegisterRequest)}
     * a Null Pointer Exception is thrown.
     */
    @Test
    public void testRegister_nullUsername_correctResponse() throws IOException {
        Assertions.assertEquals("A Username is required to register (please enter)",
                registerServiceSpy.register(nullUsername).getMessage());
    }

    /**
     * Verifies that when a request with a null password is passed into {@link RegisterService#register(RegisterRequest)}
     * a Null Pointer Exception is thrown.
     */
    @Test
    public void testRegister_nullPassword_correctResponse() throws IOException {
        Assertions.assertEquals("A password is required to register (please enter)",
                registerServiceSpy.register(nullPassword).getMessage());
    }

    /**
     * Verifies that when a request with a null firstName is passed into {@link RegisterService#register(RegisterRequest)}
     * a Null Pointer Exception is thrown.
     */
    @Test
    public void testRegister_nullFirstName_correctResponse() throws IOException {
        Assertions.assertEquals("First name is required to register (please enter)",
                registerServiceSpy.register(nullFirstName).getMessage());
    }

    /**
     * Verifies that when a request with a null lastName is passed into {@link RegisterService#register(RegisterRequest)}
     * a Null Pointer Exception is thrown.
     */
    @Test
    public void testRegister_nullLastName_correctResponse() throws IOException {
        Assertions.assertEquals("Last name is required to register (please enter)",
                registerServiceSpy.register(nullLastName).getMessage());
    }

    /**
     * Verifies that when a request with a null photo is passed into {@link RegisterService#register(RegisterRequest)}
     * a Null Pointer Exception is thrown.
     */
    @Test
    public void testRegister_nullPicture_correctResponse() throws IOException {
        Assertions.assertEquals("A profile picture is required to register",
                registerServiceSpy.register(nullImage).getMessage());
    }

    /**
     * Verifies that when a bad request is passed into {@link RegisterService#register(RegisterRequest)}
     * the correct response is returned.
     */
    @Test
    public void testRegister_badRequest_correctResponse() throws IOException {
        Assertions.assertEquals(badResponse, registerServiceSpy.register(badRequest));
    }

    /**
     * Verifies that when a bad request is passed into {@link RegisterService#register(RegisterRequest)}
     * the correct response is returned.
     */
    @Test
    public void testRegister_goodRequest_correctResponse() throws IOException {
        Assertions.assertEquals(goodResponse, registerServiceSpy.register(goodRequest));
    }




}
