package com.example.parkingbuddy.Login_Signup_Forgot;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.parkingbuddy.R;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by madhavchhura on 5/10/15.
 */
public class LoginFragment extends LoginFragmentBase {

    public interface LoginFragmentListener {
        public void onSignUpClicked(String username, String password);

        public void onLoginHelpClicked();

        public void onLoginSuccess();
    }

    private static final String LOG_TAG = "LoginFragment";
    private static final String USER_OBJECT_NAME_FIELD = "name";

    private View login;
    private EditText usernameField;
    private EditText passwordField;
    private ButtonFlat loginHelpButton;
    private ButtonRectangle loginButton, signupButton;
    private LoginFragmentListener loginFragmentListener;
    private OnLoginSuccessListener onLoginSuccessListener;

    private LoginConfig config;

    public static LoginFragment newInstance(Bundle configOptions) {
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(configOptions);
        return loginFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        config = LoginConfig.fromBundle(getArguments(), getActivity());

        View v = inflater.inflate(R.layout.activity_login,
                parent, false);
        ImageView appLogo = (ImageView) v.findViewById(R.id.appIcon);
        login = v.findViewById(R.id.login);
        usernameField = (EditText) v.findViewById(R.id.username);
        passwordField = (EditText) v.findViewById(R.id.password);
        loginHelpButton = (ButtonFlat) v.findViewById(R.id.btn_ForgetPass);
        loginButton = (ButtonRectangle) v.findViewById(R.id.btn_login);
        signupButton = (ButtonRectangle) v.findViewById(R.id.btn_signup);


        if (appLogo != null && config.getAppLogo() != null) {
            appLogo.setImageResource(config.getAppLogo());
        }
        if (allowLoginAndSignup()) {
            setUpLoginAndSignup();
        }
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof LoginFragmentListener) {
            loginFragmentListener = (LoginFragmentListener) activity;
        } else {
            throw new IllegalArgumentException(
                    "Activity must implemement LoginFragmentListener");
        }

        if (activity instanceof OnLoginSuccessListener) {
            onLoginSuccessListener = (OnLoginSuccessListener) activity;
        } else {
            throw new IllegalArgumentException(
                    "Activity must implemement OnLoginSuccessListener");
        }

        if (activity instanceof OnLoadingListener) {
            onLoadingListener = (OnLoadingListener) activity;
        } else {
            throw new IllegalArgumentException(
                    "Activity must implemement OnLoadingListener");
        }
    }

    @Override
    protected String getLogTag() {
        return LOG_TAG;
    }

    private void setUpLoginAndSignup() {
        login.setVisibility(View.VISIBLE);

        if (config.isLoginEmailAsUsername()) {
            usernameField.setHint(R.string.email_input_hint);
            usernameField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }

        if (config.getLoginButtonText() != null) {
            loginButton.setText((String) config.getLoginButtonText());
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                if (username.length() == 0) {
                    if (config.isLoginEmailAsUsername()) {
                        showToast(R.string.no_email_toast);
                    } else {
                        showToast(R.string.no_username_toast);
                    }
                } else if (password.length() == 0) {
                    showToast(R.string.no_password_toast);
                } else {
                    loadingStart(true);
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (isActivityDestroyed()) {
                                return;
                            }

                            if (user != null) {
                                loadingFinish();
                                loginSuccess();
                            } else {
                                loadingFinish();
                                if (e != null) {

                                    if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                        if (config.getLoginInvalidCredentialsToastText() != null) {
                                            showToast(config.getLoginInvalidCredentialsToastText());
                                        } else {
                                            showToast(R.string.parse_login_invalid_credentials_toast);
                                        }
                                        passwordField.selectAll();
                                        passwordField.requestFocus();
                                    } else {
                                        showToast(R.string.parse_login_failed_unknown_toast);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });

        if (config.getSignupButtonText() != null) {
            signupButton.setText((String) config.getSignupButtonText());
        }

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                loginFragmentListener.onSignUpClicked(username, password);
            }
        });

        if (config.getLoginHelpText() != null) {
            loginHelpButton.setText((String) config.getLoginHelpText());
        }

        loginHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFragmentListener.onLoginHelpClicked();
            }
        });
    }

    private boolean allowLoginAndSignup() {
        if (!config.isLoginEnabled()) {
            return false;
        }

        if (usernameField == null) {
            //debugLog(R.string.login_warning_layout_missing_username_field);
        }
        if (passwordField == null) {
            //debugLog(R.string.login_warning_layout_missing_password_field);
        }
        if (loginButton == null) {
            //debugLog(R.string.login_warning_layout_missing_login_button);
        }
        if (signupButton == null) {
           // debugLog(R.string.login_warning_layout_missing_signup_button);
        }
        if (loginHelpButton == null) {
           // debugLog(R.string.login_warning_layout_missing_login_help_button);
        }

        boolean result = (usernameField != null) && (passwordField != null)
                && (loginButton != null) && (signupButton != null)
                && (loginHelpButton != null);

        if (!result) {
           // debugLog(R.string.login_warning_disabled_username_password_login);
        }
        return result;
    }


    private void loginSuccess() {
        onLoginSuccessListener.onLoginSuccess();
    }

}
