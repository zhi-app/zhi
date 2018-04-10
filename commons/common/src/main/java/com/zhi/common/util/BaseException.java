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

import android.util.AndroidException;

/**
 * Base class for all checked exceptions thrown by the Android frameworks.
 */
public class BaseException extends AndroidException {

    public BaseException() {
    }

    public BaseException(String name) {
        super(name);
    }

    public BaseException(String name, Throwable cause) {
        super(name, cause);
    }

    public BaseException(Exception cause) {
        super(cause);
    }
}
