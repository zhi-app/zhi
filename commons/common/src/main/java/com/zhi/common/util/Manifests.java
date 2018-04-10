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

package com.zhi.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.content.pm.PackageManager.GET_META_DATA;

public final class Manifests {

    @Nullable
    public static String getMetaString(@NonNull Context context, @NonNull String key) {
        String res = null;
        try {
            final ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), GET_META_DATA);
            res = appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Nullable
    public static Long getMetaLong(@NonNull Context context, @NonNull String key) {
        Long res = null;
        try {
            final ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), GET_META_DATA);
            res = appInfo.metaData.getLong(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

    private Manifests() {
        // LEFT-DO-NOTHING
    }
}
