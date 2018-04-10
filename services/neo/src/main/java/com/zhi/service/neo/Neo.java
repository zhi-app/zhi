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

import android.os.Bundle;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;

import java.util.Set;

import javax.inject.Inject;

public final class Neo implements Loggable {
    @NonNull
    private final Set<Loggable> mLoggers;

    @Inject
    Neo(@NonNull Set<Loggable> loggers) {
        mLoggers = loggers;
    }

    @Override
    public void log(@AnyRes int event, @NonNull Bundle params) {
        for (Loggable logger : mLoggers) {
            logger.log(event, params);
        }
    }
}
