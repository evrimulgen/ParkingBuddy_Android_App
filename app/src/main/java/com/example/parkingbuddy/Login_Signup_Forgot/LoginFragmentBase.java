package com.example.parkingbuddy.Login_Signup_Forgot;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by madhavchhura on 5/10/15.
 */

/**
 * Base class with helper methods for fragments in Login UI.
 */
public class LoginFragmentBase extends Fragment {
    protected OnLoadingListener onLoadingListener;

    protected String getLogTag() {
        return null;
    }

    protected void showToast(int id) {
        showToast(getString(id));
    }

    protected void showToast(CharSequence text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    protected void loadingStart() {
        loadingStart(true);
    }

    protected void loadingStart(boolean showSpinner) {
        if (onLoadingListener != null) {
            onLoadingListener.onLoadingStart(showSpinner);
        }
    }

    protected void loadingFinish() {
        if (onLoadingListener != null) {
            onLoadingListener.onLoadingFinish();
        }
    }

    protected void debugLog(int id) {
        debugLog(getString(id));
    }

    protected void debugLog(String text) {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected boolean isActivityDestroyed() {
        FragmentActivity activity = getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity == null || activity.isDestroyed();
        } else {
            return activity == null || ((Login) activity).isDestroyed();
        }
    }
}