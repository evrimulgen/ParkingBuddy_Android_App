package com.example.parkingbuddy.Login_Signup_Forgot;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkingbuddy.R;
import com.gc.materialdesign.views.ButtonRectangle;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

/**
 * Created by madhavchhura on 5/10/15.
 */
public class ForgotPasswordFragment extends LoginFragmentBase
        implements View.OnClickListener{


    public interface ForgotPasswordSuccessListener {
        public void onLoginHelpSuccess();
    }

    private TextView instructionsTextView;
    private EditText emailField;
    private ButtonRectangle submitButton;
    private boolean emailSent = false;
    private ForgotPasswordSuccessListener onForgotPasswordSuccessListener;

    private LoginConfig config;

    private static final String LOG_TAG = "LoginHelpFragment";

    public static ForgotPasswordFragment newInstance(Bundle configOptions) {
        ForgotPasswordFragment loginHelpFragment = new ForgotPasswordFragment();
        loginHelpFragment.setArguments(configOptions);
        return loginHelpFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        config = LoginConfig.fromBundle(getArguments(), getActivity());

        View v = inflater.inflate(R.layout.fragment_forgot_password,
                parent, false);
        ImageView appLogo = (ImageView) v.findViewById(R.id.appIcon);
        instructionsTextView = (TextView) v
                .findViewById(R.id.login_help_instructions);
        emailField = (EditText) v.findViewById(R.id.login_help_email_input);
        submitButton = (ButtonRectangle) v.findViewById(R.id.btn_submit);

        if (appLogo != null && config.getAppLogo() != null) {
            appLogo.setImageResource(config.getAppLogo());
        }

        submitButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnLoadingListener) {
            onLoadingListener = (OnLoadingListener) activity;
        } else {
            throw new IllegalArgumentException(
                    "Activity must implemement OnLoadingListener");
        }

        if (activity instanceof ForgotPasswordSuccessListener) {
            onForgotPasswordSuccessListener = (ForgotPasswordSuccessListener) activity;
        } else {
            throw new IllegalArgumentException(
                    "Activity must implemement OnLoginHelpSuccessListener");
        }
    }

    @Override
    public void onClick(View v) {
        if (!emailSent) {
            String email = emailField.getText().toString();
            if (email.length() == 0) {
                showToast(R.string.no_email_toast);
            } else {
                loadingStart();
                ParseUser.requestPasswordResetInBackground(email,
                        new RequestPasswordResetCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (isActivityDestroyed()) {
                                    return;
                                }

                                loadingFinish();
                                if (e == null) {
                                    instructionsTextView
                                            .setText(R.string.login_help_email_sent);
                                    emailField.setVisibility(View.INVISIBLE);
                                    submitButton
                                            .setText(String.valueOf(R.string.login_help_login_again_button_label));
                                    emailSent = true;
                                } else {
                                    if (e.getCode() == ParseException.INVALID_EMAIL_ADDRESS ||
                                            e.getCode() == ParseException.EMAIL_NOT_FOUND) {
                                        showToast(R.string.invalid_email_toast);
                                    } else {
                                        showToast("Failed to request password reset, please try again");
                                    }
                                }
                            }
                        });
            }
        } else {
            onForgotPasswordSuccessListener.onLoginHelpSuccess();
        }
    }

    @Override
    protected String getLogTag() {
        return LOG_TAG;
    }
}