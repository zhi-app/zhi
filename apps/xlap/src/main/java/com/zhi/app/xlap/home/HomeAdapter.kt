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

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zhi.app.xlap.R
import com.zhi.common.util.BaseRuntimeException
import com.zhi.common.widget.RecyclerAdapter
import com.zhi.common.widget.SimpleItem

class HomeAdapter(items: List<SimpleItem>) : RecyclerAdapter(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            R.layout.home_view_foo -> HomeFooViewHolder.newInstance(parent)
            R.layout.home_view_bar -> HomeBarViewHolder.newInstance(parent)
            else -> throw BaseRuntimeException("Unknown view type $viewType")
        }
}
