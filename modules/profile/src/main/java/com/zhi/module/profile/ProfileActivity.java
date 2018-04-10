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

package com.zhi.module.profile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.RestrictTo;

import com.zhi.common.app.BaseActivity;
import com.zhi.module.profile.databinding.ActivityProfileBinding;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class ProfileActivity extends BaseActivity {

    //    @Inject
    //    Optional<DataBindingComponent> mBindingComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityProfileBinding dataBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_profile, null);
        dataBinding.setViewModel(new ProfileViewModel(getApplication()));
    }
}