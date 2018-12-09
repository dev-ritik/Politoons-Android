package io.github.dev_ritik.politoons.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.github.dev_ritik.politoons.data.ToonRepository
import io.github.dev_ritik.politoons.model.Politoon


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
        viewModel.toon?.observe(this, postObserver)

    }

    private fun provideRepository(): ToonRepository? {
        Log.i(TAG, "provideRepository: ")
        return ToonRepository.getInstance()
    }

    private val postObserver = Observer<List<Politoon>> {
        Log.i(TAG, "onChanged: " + it?.size)
        for (i in it!!) {
            Log.i(TAG, "${i.party}")
        }
    }
}
