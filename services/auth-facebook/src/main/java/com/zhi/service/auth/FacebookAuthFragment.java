/*
 * Copyright [2018] [zhi]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhi.service.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.zhi.common.app.BaseFragment;
import com.zhi.common.util.Logs;

public class FacebookAuthFragment extends BaseFragment implements FacebookCallback<LoginResult> {
    private static final String TAG = Logs.makeLogTag("FacebookAuthFragment");

    private CallbackManager mCallbackManager;

    public FacebookAuthFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_auth_facebook, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();

        final LoginButton loginButton = view.findViewById(R.id.auth_sign_in);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);
        // Callback registration
        loginButton.registerCallback(mCallbackManager, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(resultCode, requestCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Logs.d(TAG, "Login Success");
        final AccessToken token = loginResult.getAccessToken();
        Logs.d(TAG, "AccessToken is %1$s", token);
        Logs.d(TAG, "Recently granted permissions: %1$s", loginResult.getRecentlyGrantedPermissions());
        Logs.d(TAG, "Recently denied permissions: %1$s", loginResult.getRecentlyDeniedPermissions());
    }

    @Override
    public void onCancel() {
        Logs.d(TAG, "Login Canceled");
    }

    @Override
    public void onError(FacebookException error) {
        error.printStackTrace();
        Logs.d(TAG, "Login Error happened %1$s", error);
    }
}
