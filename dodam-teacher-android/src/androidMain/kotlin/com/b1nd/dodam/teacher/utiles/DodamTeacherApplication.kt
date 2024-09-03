package com.b1nd.dodam.teacher.utiles

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.b1nd.dodam.teacher.getAsyncImageLoader

class DodamTeacherApplication: Application(), SingletonImageLoader.Factory {
    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return getAsyncImageLoader(this)
    }
}