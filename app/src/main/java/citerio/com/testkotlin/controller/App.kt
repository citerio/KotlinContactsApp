package citerio.com.testkotlin.controller

import android.app.Application
import citerio.com.testkotlin.model.MyObjectBox
import io.objectbox.BoxStore

/**
 * Created by Jose Ricardo on 15/04/2018.
 */
class App : Application() {

    lateinit var boxStore: BoxStore
        private set

    override fun onCreate() {
        super.onCreate()

        //        if (EXTERNAL_DIR) {
        //            // Example how you could use a custom dir in "external storage"
        //            // (Android 6+ note: give the app storage permission in app info settings)
        //            File directory = new File(Environment.getExternalStorageDirectory(), "objectbox-notes");
        //            boxStore = MyObjectBox.builder().androidContext(App.this).directory(directory).build();
        //        } else {
        // This is the minimal setup required on Android
        boxStore = MyObjectBox.builder().androidContext(this).build()
        //        }
    }

}