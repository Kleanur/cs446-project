package com.example.sequoia

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel constructor(application: Application) :
    AndroidViewModel(application) {

    protected val context: Application
        get() = getApplication()
}