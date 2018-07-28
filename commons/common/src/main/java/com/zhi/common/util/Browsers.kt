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

package com.zhi.common.util

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.autofill.AutofillManager
import androidx.browser.customtabs.CustomTabsService

object Browsers {

    private lateinit var avaiableChromePackage: String

//    fun isCustomTabsallowed(): Boolean {
//
//    }

    fun getChromePackage(context: Context): String {
        if (!Browsers::avaiableChromePackage.isInitialized) {
            val intent = Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION)
            context.packageManager.queryIntentServices(intent, 0)?.let { services ->
                val chromes = arrayOf(
                    "com.android.chrome",
                    "com.chrome.beta",
                    "com.chrome.dev"
                )
                avaiableChromePackage = ""
                services.forEach { info ->
                    info.serviceInfo?.takeIf { chromes.contains(it.packageName) }?.let {
                        avaiableChromePackage = it.packageName
                        return@forEach
                    }
                }
            }
        }
        return avaiableChromePackage
    }

    fun isAutofillAvaiable(context: Context) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // Autofill Framework is only available on Android O and higher
            false
        } else {
            context.getSystemService<AutofillManager>(AutofillManager::class.java)
                ?.let { it.isAutofillSupported && it.isEnabled } ?: false
        }
}


