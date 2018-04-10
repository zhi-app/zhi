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

package com.zhi.module.camera;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;

public class Camera2Session extends CameraSession {
    /**
     * Running camera tasks in the background thread.
     */
    private final Handler mCameraHandler;

    public Camera2Session(@NonNull Bundle params, @NonNull Callback callback) {
        super(params, callback);

        final HandlerThread cameraThread = new HandlerThread("CameraThread");
        cameraThread.start();
        mCameraHandler = new Handler(cameraThread.getLooper());
    }
}
