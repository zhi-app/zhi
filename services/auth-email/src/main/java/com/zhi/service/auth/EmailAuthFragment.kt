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
import androidx.lifecycle.ViewModelProvider
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.FragmentContext
import com.zhi.service.auth.email.databinding.AuthEmailFragmentBinding
import javax.inject.Inject

class EmailAuthFragment : BaseFragment() {

    @Inject
    @field:FragmentContext
    lateinit var viewModelProvider: ViewModelProvider

    private lateinit var authViewModel: EmailAuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = viewModelProvider[EmailAuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = AuthEmailFragmentBinding.inflate(inflater, container, false).also {
        it.viewModel = authViewModel
        it.host = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.showHintPicker(this)
    }

    fun sendEmail() {
        authViewModel.sendEmail(
            "zzf.xlap@gmail.com",
            "123456"
        )
    }

    fun createEmail() {
        authViewModel.createEmail(
            "zzf.xlap@gmail.com",
            "123456"
        )
    }
}