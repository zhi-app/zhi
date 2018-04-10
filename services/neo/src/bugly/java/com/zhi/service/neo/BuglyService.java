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

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;
import com.zhi.common.BuildConfig;
import com.zhi.common.app.AppCreateCallback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.inject.Inject;

class BuglyService implements AppCreateCallback {
    @Inject
    BuglyService() {
    }

    @Override
    public void onCreate(@NonNull Application application) {
        final Context appContext = application.getApplicationContext();
        final String packageName = appContext.getPackageName();
        final String processName = getProcessName(android.os.Process.myPid());
        final CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(appContext);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppReportDelay(appContext.getResources().getInteger(R.integer.bugly_app_report_delays_ms));
        CrashReport.initCrashReport(application, strategy);
        CrashReport.setIsDevelopmentDevice(appContext, BuildConfig.DEBUG);
    }

    @Nullable
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
