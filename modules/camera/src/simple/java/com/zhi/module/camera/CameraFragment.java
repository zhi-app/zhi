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

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zhi.common.app.BaseFragment;

public class CameraFragment extends BaseFragment {
    private static final String TAG = "CameraFragment";

    public static final int RC_CAPTURE_IMAGE = 0x01;

    @NonNull
    static CameraFragment getInstance(@NonNull FragmentManager fm) {
        CameraFragment f = (CameraFragment) fm.findFragmentByTag(TAG);
        if (f == null) {
            f = new CameraFragment();
            fm.beginTransaction().add(f, TAG).commit();
        }
        return f;
    }

    public CameraFragment() {
        // LEFT-DO-NOTHING
    }

    public void takePicture(@NonNull Context context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, RC_CAPTURE_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CAPTURE_IMAGE) {
        }
    }
}
