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

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
import android.net.Uri
import androidx.core.content.FileProvider
import com.zhi.common.R
import com.zhi.common.app.AppCreateCallback
import com.zhi.common.app.MainProcess
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import java.io.File
import javax.inject.Singleton

@Module
object FileProvidersModule {
    @JvmStatic
    @Provides
    @IntoSet
    @Singleton
    @MainProcess
    fun appCreateFileProvidersCallback() = object : AppCreateCallback {
        override fun onCreate(application: Application) {
            FileProviders.apply {
                application.resources.also { res ->
                    authority = res.getString(R.string.common_file_provider_authority)
                }
            }
        }
    }
}

object FileProviders {

    internal var authority = ""

    /**
     * Return a content URI for a given {@link File}
     */
    fun getUriForFile(context: Context, file: File): Uri =
        try {
            FileProvider.getUriForFile(context, authority, file)
        } catch (e: Throwable) {
            try {
                Uri.fromFile(file)
            } catch (e: Throwable) {
                Uri.EMPTY
            }
        }!!

    fun share(context: Context, file: File, intent: Intent) {
        getUriForFile(context, file).also { uri ->
            intent.data = uri
            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }
}