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

package com.zhi.service.neo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;

import com.zhi.common.app.AppContext;
import com.zhi.common.util.Logs;

import javax.inject.Inject;

class FirebaseService implements Loggable {

    @NonNull
    @AppContext
    private final Context mAppContext;

    @Inject
    FirebaseService(@NonNull @AppContext Context appContext) {
        mAppContext = appContext;
    }

    @Override
    public void log(@AnyRes int event, @NonNull Bundle params) {
        Logs.d(mAppContext.getString(event), "params is %1$s", params);
    }
}
