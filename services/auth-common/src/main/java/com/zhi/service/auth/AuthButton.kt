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

package com.zhi.service.auth

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.TextViewCompat

/**
 * Auth button that supports using vector drawables with {@code authIcon}
 */
class AuthButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {

    init {
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AuthButton)
            val authIconDrawable =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    a.getDrawable(R.styleable.AuthButton_authIcon)
                } else {
                    a.getResourceId(R.styleable.AuthButton_authIcon, -1)
                        .takeIf { resId ->
                            resId != -1
                        }
                        ?.let { resId ->
                            AppCompatResources.getDrawable(context, resId)
                        }
                }
            authIconDrawable?.let {
                TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    this@AuthButton,
                    authIconDrawable, null, null, null
                )
            }
            a.recycle()
        }
    }
}