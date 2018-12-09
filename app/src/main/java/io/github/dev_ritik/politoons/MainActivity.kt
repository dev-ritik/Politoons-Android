package io.github.dev_ritik.politoons

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    //this will only be done upon the first usage.
    private val viewModel by lazy {
        Log.i(TAG, "viewModel lazy")
        val repository = provideRepository()
//        val factory = ToonViewModelFactory(repository)
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T1 : ViewModel> create(aClass: Class<T1>) =
                MyViewModel(repository) as T1
        }).get(MyViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "oncreate")


        viewModel.toon.observe(this, postObserver)


    }

    fun provideRepository(): ToonRepository? {
        Log.i(TAG, "provideRepository: ")
        return ToonRepository().getInstance()
    }

    private val postObserver = Observer<Politoon> {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        Log.i(TAG, "onChanged: ")
    }
}
