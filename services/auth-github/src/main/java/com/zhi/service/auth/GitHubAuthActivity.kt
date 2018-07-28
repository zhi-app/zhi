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

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class GitHubAuthActivity : Activity() {

    private var shouldClose = true
    private var redirectReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ACTION_REDIRECT == intent.action) {
            sendResult(Activity.RESULT_CANCELED)
            return
        }

        if (savedInstanceState == null) {
            CustomTabsIntent.Builder()
                .setShowTitle(true)
                .enableUrlBarHiding()
                .setToolbarColor(ContextCompat.getColor(this, R.color.design_default_color_primary))
                .build()
                .launchUrl(this, intent.getParcelableExtra(EXTRA_URL))
            shouldClose = false

            redirectReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    startActivity(
                        newIntent(this@GitHubAuthActivity)
                            .setAction(ACTION_REFRESH)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    )
                }
            }
            LocalBroadcastManager.getInstance(this).registerReceiver(
                redirectReceiver as BroadcastReceiver,
                IntentFilter(ACTION_REDIRECT)
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        when (intent.action) {
            ACTION_REFRESH -> {
                LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(ACTION_DESTROY))
                sendResult(Activity.RESULT_OK, intent)
            }
            ACTION_REDIRECT -> {
                sendResult(Activity.RESULT_OK, intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (shouldClose) {
            sendResult(RESULT_CANCELED)
        }
        shouldClose = true
    }

    private fun sendResult(resultCode: Int, resultIntent: Intent? = null) {
        redirectReceiver?.also {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(it)
            redirectReceiver = null
        }
        setResult(resultCode, resultIntent)
        finish()
    }

    companion object {
        internal const val RC_REDIRECT = 2
        internal const val EXTRA_URL = "extra_url"
        internal const val ACTION_REFRESH = "com.zhi.service.auth.ACTION_REFRESH"
        internal const val ACTION_REDIRECT = "com.zhi.service.auth.ACTION_REDIRECT"
        internal const val ACTION_DESTROY = "com.zhi.service.auth.ACTION_DESTROY"

        fun newIntent(context: Context) = Intent(context, GitHubAuthActivity::class.java)
    }
}