package com.yill.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
public class EmailConstant {

    @Value("${EmailInformation.EMAIL_TITLE}")
    public String EMAIL_TITLE;

    @Value("${EmailInformation.Login.LOGIN_EMAIL_MESSAGE}")
    public String LOGIN_EMAIL_MESSAGE;

    @Value("${EmailInformation.Login.LOGIN_EMAIL_MESSAGE_TIME}")
    public String LOGIN_EMAIL_MESSAGE_TIME;

    @Value("${EmailInformation.FindPassword.FIND_PASSWORD_EMAIL_MESSAGE}")
    public String FIND_PASSWORD_EMAIL_MESSAGE;

    @Value("${EmailInformation.FindPassword.FIND_PASSWORD_URL}")
    public String FIND_PASSWORD_URL;

    @Value("${EmailInformation.FindPassword.FIND_PASSWORD_EMAIL_MESSAGE_TIME}")
    public String FIND_PASSWORD_EMAIL_MESSAGE_TIME;
}
