package com.example.sequoia

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val greetingsName : String = "Tara"

//    companion object {
//        @Suppress("UNCHECKED_CAST")
//        fun factory(
//            application: Application,
//        ): ViewModelProvider.Factory {
//            return object : ViewModelProvider.Factory {
//                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                    return MainViewModel(
//                        application = application,
//                    ) as T
//                }
//            }
//        }
//    }
}