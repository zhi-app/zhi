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

import android.os.Bundle
import com.zhi.common.app.BaseActivity

class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var authFragment =
            supportFragmentManager.findFragmentById(android.R.id.content) as? AuthFragment
        if (authFragment == null) {
            authFragment = AuthFragment.newInstance(intent.extras)
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, authFragment)
                .commitAllowingStateLoss()

        } else if (intent.extras != null) {
            authFragment.arguments?.putAll(intent.extras)
        }
    }
}