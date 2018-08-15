package com.gmail.insta.service;

import com.gmail.insta.forms.LoginForm;
import com.gmail.insta.transfer.TokenDto;

public interface LoginService {
    TokenDto login(LoginForm loginForm);
}
