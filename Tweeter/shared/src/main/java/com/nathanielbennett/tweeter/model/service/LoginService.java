package com.nathanielbennett.tweeter.model.service;

import java.io.IOException;

import com.nathanielbennett.tweeter.model.net.TweeterRemoteException;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException;
}
