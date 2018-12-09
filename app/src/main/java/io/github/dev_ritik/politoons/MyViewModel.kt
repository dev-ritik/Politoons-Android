package io.github.dev_ritik.politoons


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log


class MyViewModel(private val mRepository: ToonRepository?) : ViewModel() {
    private val LOG_TAG = MyViewModel::class.java.simpleName

    // Weather forecast the user is looking at
    var toon: MutableLiveData<Politoon> = MutableLiveData()

    init {
        Log.i(LOG_TAG, "MyViewModel: ")
         mRepository?.fetch()
    }

}
