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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.BaseFragment
import com.zhi.common.util.Logs
import com.zhi.service.auth.databinding.AuthFragmentBinding
import javax.inject.Inject

class AuthFragment : BaseFragment() {

    @Inject
    @field:ActivityContext
    lateinit var viewModelProvider: ViewModelProvider
    @Inject
    @field:ActivityContext
    lateinit var authResource: AuthResource

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = viewModelProvider[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = AuthFragmentBinding.inflate(inflater, container, false).also {
        it.viewModel = authViewModel
        it.host = this@AuthFragment
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authResource.state.observe(this, Observer { resource ->
            when (resource) {
                AuthResource.State.LOADING -> {
                    Logs.d(this@AuthFragment, "Stat-Loading")
                }
                AuthResource.State.SUCCESS -> {
                    Logs.d(this@AuthFragment, "Stat-Success")
                }
                AuthResource.State.FAILURE -> {
                    Logs.d(this@AuthFragment, "Stat-Failure")
                }
            }
        })
    }

    companion object {
        fun newInstance(args: Bundle?) = AuthFragment().also {
            it.arguments = args ?: Bundle()
        }
    }
}