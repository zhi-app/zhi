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
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.zhi.service.auth.GitHubAuthActivity.Companion.ACTION_DESTROY
import com.zhi.service.auth.GitHubAuthActivity.Companion.ACTION_REDIRECT
import com.zhi.service.auth.GitHubAuthActivity.Companion.RC_REDIRECT

class GitHubAuthRedirectActivity : Activity() {

    private var closeReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivityForResult(
            GitHubAuthActivity.newIntent(this)
                .setAction(ACTION_REDIRECT)
                .putExtra(GitHubAuthActivity.EXTRA_URL, intent.dataString)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP),
            RC_REDIRECT
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(
                Intent(ACTION_REDIRECT)
                    .putExtra(GitHubAuthActivity.EXTRA_URL, intent.dataString)
            )
            closeReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    this@GitHubAuthRedirectActivity.finish()
                }
            }
            LocalBroadcastManager.getInstance(this).registerReceiver(
                closeReceiver as BroadcastReceiver,
                IntentFilter(ACTION_DESTROY)
            )
        }
    }

    override fun onDestroy() {
        closeReceiver?.also {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(it)
            closeReceiver = null
        }
        super.onDestroy()
    }
}