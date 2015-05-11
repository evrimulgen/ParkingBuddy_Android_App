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
import com.gc.materialdesign.views.ButtonRectangle;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignupFragment extends LoginFragmentBase implements View.OnClickListener {

    private EditText usernameField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private EditText emailField;
    private EditText nameField;
    private ButtonRectangle createAccountButton;
    private OnLoginSuccessListener onLoginSuccessListener;
    protected OnLoadingListener onLoadingListener;

    public static final String USERNAME = "com.example.parkingbuddy.signupFragment.USERNAME";
    public static final String PASSWORD = "com.example.parkingbuddy.signupFragment.PASSWORD";

    private LoginConfig config;
    private int minPasswordLength;

    private static final String LOG_TAG = "SignupFragment";
    private static final int DEFAULT_MIN_PASSWORD_LENGTH = 6;
    private static final String USER_OBJECT_NAME_FIELD = "name";

    public static SignupFragment newInstance(Bundle configOptions, String username, String password) {
        SignupFragment signupFragment = new SignupFragment();
        Bundle args = new Bundle(configOptions);
        args.putString(SignupFragment.USERNAME, username);
        args.putString(SignupFragment.PASSWORD, password);
        signupFragment.setArguments(args);
        return signupFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        config = LoginConfig.fromBundle(args, getActivity());

        minPasswordLength = DEFAULT_MIN_PASSWORD_LENGTH;
        if (config.getSignupMinPasswordLength() != null) {
            minPasswordLength = config.getSignupMinPasswordLength();
        }

        String username = (String) args.getString(USERNAME);
        String password = (String) args.getString(PASSWORD);

        View v = inflater.inflate(R.layout.activity_signup_fragment,
                parent, false);
        ImageView appLogo = (ImageView) v.findViewById(R.id.appIcon);
        usernameField = (EditText) v.findViewById(R.id.signup_username_input);
        passwordField = (EditText) v.findViewById(R.id.signup_password_input);
        confirmPasswordField = (EditText) v
                .findViewById(R.id.signup_confirm_password_input);
        emailField = (EditText) v.findViewById(R.id.signup_email_input);
        nameField = (EditText) v.findViewById(R.id.signup_name_input);
        createAccountButton = (ButtonRectangle) v.findViewById(R.id.btn_signup);


        usernameField.setText(username);
        passwordField.setText(password);

        if (appLogo != null && config.getAppLogo() != null) {
            appLogo.setImageResource(config.getAppLogo());
        }

        if (config.isLoginEmailAsUsername()) {
            usernameField.setHint(R.string.emailHint);
            usernameField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            if (emailField != null) {
                emailField.setVisibility(View.GONE);
            }
        }

        if (config.getSignupSubmitButtonText() != null) {
            createAccountButton.setText(config.getSignupSubmitButtonText().toString());
        }
        createAccountButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
    public void onClick(View v) {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String passwordAgain = confirmPasswordField.getText().toString();

        String email = null;
        if (config.isLoginEmailAsUsername()) {
            email = usernameField.getText().toString();
        } else if (emailField != null) {
            email = emailField.getText().toString();
        }

        String name = null;
        if (nameField != null) {
            name = nameField.getText().toString();
        }

        if (username.length() == 0) {
            if (config.isLoginEmailAsUsername()) {
                showToast(R.string.no_email_toast);
            } else {
                showToast(R.string.no_username_toast);
            }
        } else if (password.length() == 0) {
            showToast(R.string.no_password_toast);
        } else if (password.length() < minPasswordLength) {
            showToast(getResources().getQuantityString(
                    R.plurals.password_too_short_toast,
                    minPasswordLength, minPasswordLength));
        } else if (passwordAgain.length() == 0) {
            showToast(R.string.reenter_password_toast);
        } else if (!password.equals(passwordAgain)) {
            showToast(R.string.mismatch_confirm_password_toast);
            confirmPasswordField.selectAll();
            confirmPasswordField.requestFocus();
        } else if (email != null && email.length() == 0) {
            showToast(R.string.no_email_toast);
        } else if (name != null && name.length() == 0) {
            showToast(R.string.no_name_toast);
        } else {
            ParseObject.registerSubclass(ParseUser.class);
            ParseUser user = new ParseUser();

            // Set standard fields
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);

            // Set additional custom fields only if the user filled it out
            if (name.length() != 0) {
                user.put(USER_OBJECT_NAME_FIELD, name);
            }

            loadingStart(true);
            user.signUpInBackground(new SignUpCallback() {

                @Override
                public void done(ParseException e) {
                    if (isActivityDestroyed()) {
                        return;
                    }

                    if (e == null) {
                        loadingFinish();
                        signupSuccess();
                    } else {
                        loadingFinish();
                        if (e != null) {
                            switch (e.getCode()) {
                                case ParseException.INVALID_EMAIL_ADDRESS:
                                    showToast(R.string.invalid_email_toast);
                                    break;
                                case ParseException.USERNAME_TAKEN:
                                    showToast(R.string.username_taken_toast);
                                    break;
                                case ParseException.EMAIL_TAKEN:
                                    showToast(R.string.email_taken_toast);
                                    break;
                                default:
                                    showToast(R.string.signup_failed_unknown_toast);
                            }
                        }
                    }
                }
            });
        }
    }

    private void signupSuccess() {
        onLoginSuccessListener.onLoginSuccess();
    }

    protected String getLogTag() {
        return null;
    }
}
