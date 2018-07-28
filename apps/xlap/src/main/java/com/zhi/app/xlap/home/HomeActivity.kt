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

package com.zhi.app.xlap.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.zhi.app.xlap.R
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.BaseActivity
import com.zhi.common.widget.SimpleItem
import javax.inject.Inject

class HomeActivity : BaseActivity() {
    @Inject
    @field:ActivityContext
    lateinit var viewModelProvider: ViewModelProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: HomeActivityBinding = setContentView(this, R.layout.home_activity)
        binding.viewModule = viewModelProvider.get(HomeViewModel::class.java)

        val items = listOf(
            SimpleItem("foo", R.layout.home_view_foo),
            SimpleItem("bar", R.layout.home_view_bar)
        )
        findViewById<RecyclerView>(R.id.list).adapter = HomeAdapter(items)
    }
}

