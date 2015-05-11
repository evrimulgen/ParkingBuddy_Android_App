package com.example.parkingbuddy.Login_Signup_Forgot;

import android.content.Context;
import android.os.Bundle;


import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * Created by madhavchhura on 5/10/15.
 */

/**
 * Configurations for the LoginActivity.
 */
public class LoginConfig {

    public static final String LOGIN_ENABLED = "true";
    public static final String LOGIN_BUTTON_TEXT = "Log in";
    public static final String SIGNUP_BUTTON_TEXT = "Sign up";
    public static final String LOGIN_HELP_TEXT = "Forgot Password";
    public static final String LOGIN_INVALID_CREDENTIALS_TOAST_TEXT = "";
    public static final String LOGIN_EMAIL_AS_USERNAME = "";
    public static final String SIGNUP_MIN_PASSWORD_LENGTH = "6";
    public static final String SIGNUP_SUBMIT_BUTTON_TEXT = "Create Account";


    private static final String LOG_TAG = "com.example.LoginConfig";

    // Use boxed types so that we can differentiate between a setting not set,
    // versus its default value.  This is useful for merging options set from code
    // with options set by activity metadata.
    private Integer appLogo;
    private Boolean loginEnabled = true;
    private CharSequence loginButtonText;
    private CharSequence signupButtonText;
    private CharSequence loginHelpText;
    private CharSequence LoginInvalidCredentialsToastText;
    private Boolean LoginEmailAsUsername;
    private Integer SignupMinPasswordLength;
    private CharSequence SignupSubmitButtonText;


    public Integer getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(Integer appLogo) {
        this.appLogo = appLogo;
    }

    public boolean getLoginEnabled() {
        if (loginEnabled != null) {
            return loginEnabled;
        } else {
            return false;
        }
    }

    public void setLoginEnabled(boolean LoginEnabled) {
        this.loginEnabled = LoginEnabled;
    }

    public CharSequence getLoginButtonText() {
        return loginButtonText;
    }

    public void setLoginButtonText(CharSequence LoginButtonText) {
        this.loginButtonText = LoginButtonText;
    }

    public CharSequence getSignupButtonText() {
        return signupButtonText;
    }

    public void setSignupButtonText(CharSequence SignupButtonText) {
        this.signupButtonText = SignupButtonText;
    }

    public CharSequence getLoginHelpText() {
        return loginHelpText;
    }

    public void setLoginHelpText(CharSequence LoginHelpText) {
        this.loginHelpText = LoginHelpText;
    }

    public CharSequence getLoginInvalidCredentialsToastText() {
        return LoginInvalidCredentialsToastText;
    }

    public void setLoginInvalidCredentialsToastText(
            CharSequence LoginInvalidCredentialsToastText) {
        this.LoginInvalidCredentialsToastText = LoginInvalidCredentialsToastText;
    }

    public boolean isLoginEmailAsUsername() {
        if (LoginEmailAsUsername != null) {
            return LoginEmailAsUsername;
        } else {
            return false;
        }
    }

    public void setLoginEmailAsUsername(boolean LoginEmailAsUsername) {
        this.LoginEmailAsUsername = LoginEmailAsUsername;
    }

    public Integer getSignupMinPasswordLength() {
        return SignupMinPasswordLength;
    }

    public void setSignupMinPasswordLength(Integer SignupMinPasswordLength) {
        this.SignupMinPasswordLength = SignupMinPasswordLength;
    }

    public CharSequence getSignupSubmitButtonText() {
        return SignupSubmitButtonText;
    }

    public void setSignupSubmitButtonText(
            CharSequence SignupSubmitButtonText) {
        this.SignupSubmitButtonText = SignupSubmitButtonText;
    }

    /**
     * Converts this object into a Bundle object. For options that are not
     * explicitly set, we do not include them in the Bundle so that this bundle
     * can be merged with any default configurations and override only those keys
     * that are explicitly set.
     *
     * @return The Bundle object containing configurations.
     */
    public Bundle toBundle() {
        Bundle bundle = new Bundle();


        if (loginEnabled != null) {
            bundle.putBoolean(LOGIN_ENABLED, loginEnabled);
        }
        if (loginButtonText != null) {
            bundle.putCharSequence(LOGIN_BUTTON_TEXT, loginButtonText);
        }
        if (signupButtonText != null) {
            bundle.putCharSequence(SIGNUP_BUTTON_TEXT, signupButtonText);
        }
        if (loginHelpText != null) {
            bundle.putCharSequence(LOGIN_HELP_TEXT, loginHelpText);
        }
        if (LoginInvalidCredentialsToastText != null) {
            bundle.putCharSequence(LOGIN_INVALID_CREDENTIALS_TOAST_TEXT,
                    LoginInvalidCredentialsToastText);
        }
        if (LoginEmailAsUsername != null) {
            bundle.putBoolean(LOGIN_EMAIL_AS_USERNAME,
                    LoginEmailAsUsername);
        }
        if (SignupMinPasswordLength != null) {
            bundle.putInt(SIGNUP_MIN_PASSWORD_LENGTH,
                    SignupMinPasswordLength);
        }
        if (SignupSubmitButtonText != null) {
            bundle.putCharSequence(SIGNUP_SUBMIT_BUTTON_TEXT,
                    SignupSubmitButtonText);
        }
        return bundle;
    }

    /**
     * Constructs a LoginConfig object from a bundle. Unrecognized keys are
     * ignored.
     * <p/>
     * This can be used to pass an LoginConfig object between activities, or
     * to read settings from an activity's meta-data in Manefest.xml.
     *
     * @param bundle
     *     The Bundle representation of the LoginConfig object.
     * @param context
     *     The context for resolving resource IDs.
     * @return The LoginConfig instance.
     */
    public static LoginConfig fromBundle(Bundle bundle, Context context) {
        LoginConfig config = new LoginConfig();
        Set<String> keys = bundle.keySet();


        if (keys.contains(LOGIN_ENABLED)) {
            config.setLoginEnabled(bundle.getBoolean(LOGIN_ENABLED));
        }
        if (keys.contains(LOGIN_BUTTON_TEXT)) {
            config.setLoginButtonText(bundle.getCharSequence(LOGIN_BUTTON_TEXT));
        }
        if (keys.contains(SIGNUP_BUTTON_TEXT)) {
            config.setSignupButtonText(bundle.getCharSequence(SIGNUP_BUTTON_TEXT));
        }
        if (keys.contains(LOGIN_HELP_TEXT)) {
            config.setLoginHelpText(bundle.getCharSequence(LOGIN_HELP_TEXT));
        }
        if (keys.contains(LOGIN_INVALID_CREDENTIALS_TOAST_TEXT)) {
            config.setLoginInvalidCredentialsToastText(bundle
                    .getCharSequence(LOGIN_INVALID_CREDENTIALS_TOAST_TEXT));
        }
        if (keys.contains(LOGIN_EMAIL_AS_USERNAME)) {
            config.setLoginEmailAsUsername(bundle.getBoolean(LOGIN_EMAIL_AS_USERNAME));
        }
        if (keys.contains(SIGNUP_MIN_PASSWORD_LENGTH)) {
            config.setSignupMinPasswordLength(bundle.getInt(SIGNUP_MIN_PASSWORD_LENGTH));
        }
        if (keys.contains(SIGNUP_SUBMIT_BUTTON_TEXT)) {
            config.setSignupSubmitButtonText(bundle.getCharSequence(SIGNUP_SUBMIT_BUTTON_TEXT));
        }

        return config;
    }

    private static Collection<String> stringArrayToCollection(String[] array) {
        if (array == null) {
            return null;
        }
        return Arrays.asList(array);
    }

    public boolean isLoginEnabled() {
        return loginEnabled;
    }
}