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

package com.zhi.service.messaging;

import android.support.annotation.RestrictTo;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.zhi.common.util.Logs;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class FirebaseJobService extends JobService {
    private static final String TAG = "FirebaseJobService";

    @Override
    public boolean onStartJob(JobParameters job) {
        Logs.d(TAG, "Performing long running task in scheduled job");
        // TODO(developer): add long running task here.
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Logs.d(TAG, "Performing stop running task in scheduled job");
        // TODO(developer): add stop running task here.
        return false;
    }
}
