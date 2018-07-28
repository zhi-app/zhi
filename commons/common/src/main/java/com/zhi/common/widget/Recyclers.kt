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

package com.zhi.common.widget

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * The [RecyclerAdapter] bound item.
 * *It use Item's Any Resource Id as ViewType.*
 */
data class SimpleItem(private val data: Any, private val viewType: Int = 0) : Model, ViewType {
    override fun getModel(): Any = data
    override fun getViewType(): Int = viewType
}

interface ViewType {
    fun getViewType(): Int
}

interface Model {
    fun getModel(): Any
}

/**
 * Callback defines to be called when [RecyclerAdapter] callback.
 */
interface RecyclerViewHolder {

    fun onViewBind(data: Any, position: Int) {}

    fun onViewBind(data: Any, position: Int, payload: MutableList<Any>) =
        onViewBind(data, position)

    fun onViewAttached() {}

    fun onViewDetached() {}

    fun onViewRecycled(result: Boolean): Boolean = false
}

abstract class RecyclerAdapter constructor(protected val items: List<Any> = emptyList()) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) =
        (items[position] as? ViewType)?.getViewType() ?: 0

    open fun getItemModel(position: Int): Any =
        items[position].let { (it as? Model)?.getModel() ?: it }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is RecyclerViewHolder) {
            holder.onViewBind(getItemModel(position), position)
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) =
        if (holder is RecyclerViewHolder) {
            holder.onViewBind(getItemModel(position), position, payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }

    override fun onViewRecycled(holder: ViewHolder) {
        if (holder is RecyclerViewHolder) {
            holder.onViewRecycled(true)
        } else {
            super.onViewRecycled(holder)
        }
    }

    override fun onFailedToRecycleView(holder: ViewHolder): Boolean =
        if (holder is RecyclerViewHolder) {
            holder.onViewRecycled(false)
        } else {
            super.onFailedToRecycleView(holder)
        }

    override fun onViewAttachedToWindow(holder: ViewHolder) =
        if (holder is RecyclerViewHolder) {
            holder.onViewAttached()
        } else {
            super.onViewAttachedToWindow(holder)
        }

    override fun onViewDetachedFromWindow(holder: ViewHolder) =
        if (holder is RecyclerViewHolder) {
            holder.onViewDetached()
        } else {
            super.onViewDetachedFromWindow(holder)
        }
}
