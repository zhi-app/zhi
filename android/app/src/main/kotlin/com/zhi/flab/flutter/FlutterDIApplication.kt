package com.zhi.flab.flutter

import com.zhi.common.app.BaseApplication
import io.flutter.view.FlutterMain

abstract class FlutterDIApplication : BaseApplication() {

    override fun onCreate() {
        FlutterMain.startInitialization(this)
        super.onCreate()
    }
}