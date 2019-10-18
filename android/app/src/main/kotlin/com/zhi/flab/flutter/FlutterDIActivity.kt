package com.zhi.flab.flutter

import android.os.Bundle
import android.os.PersistableBundle
import dagger.android.AndroidInjection
import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant

abstract class FlutterDIActivity : FlutterActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState, persistentState)
        GeneratedPluginRegistrant.registerWith(this)
    }
}