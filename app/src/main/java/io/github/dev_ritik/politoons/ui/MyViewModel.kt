package io.github.dev_ritik.politoons.ui


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.github.dev_ritik.politoons.data.ToonRepository
import io.github.dev_ritik.politoons.model.Politoon


class MyViewModel(private val mRepository: ToonRepository?) : ViewModel() {
    private val LOG_TAG = MyViewModel::class.java.simpleName

    var toon: MutableLiveData<List<Politoon>>? = null
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        Log.i(LOG_TAG, "MyViewModel: ")
        toon = mRepository?.fetch(this.errorMessage)
    }

}
