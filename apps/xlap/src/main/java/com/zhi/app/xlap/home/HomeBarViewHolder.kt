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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zhi.app.xlap.R
import com.zhi.common.widget.RecyclerViewHolder

class HomeBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    RecyclerViewHolder {

    override fun onViewBind(data: Any, position: Int) {
    }

    companion object {
        fun newInstance(parent: ViewGroup): HomeBarViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.home_view_bar, parent, false)
            return HomeBarViewHolder(view)
        }
    }
}