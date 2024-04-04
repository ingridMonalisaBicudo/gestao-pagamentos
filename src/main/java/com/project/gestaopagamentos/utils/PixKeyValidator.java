package com.project.gestaopagamentos.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PixKeyValidator {

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static final String PHONE_REGEX = "^\\d{11}$";

    public static boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidPhone(String phoneNumber){
        return phoneNumber.matches(PHONE_REGEX);
    }

}
