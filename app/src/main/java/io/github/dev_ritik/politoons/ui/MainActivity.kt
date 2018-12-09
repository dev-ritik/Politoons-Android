package io.github.dev_ritik.politoons.ui

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import io.github.dev_ritik.politoons.R
import io.github.dev_ritik.politoons.data.ToonRepository
import io.github.dev_ritik.politoons.model.Politoon
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var mAdapter: ToonsAdapter
    private var mLayoutManager: LinearLayoutManager? = null
    private var mPosition = RecyclerView.NO_POSITION

    val TAG = "MainActivity"

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
        setContentView(R.layout.activity_main)
        Log.i(TAG, "oncreate")
        mAdapter = ToonsAdapter()

        mRecyclerView.adapter = mAdapter

        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager

        viewModel.toon?.observe(this, postObserver)
        viewModel.errorMessage.observe(this, errorObserver)

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
        progressBar.visibility = View.INVISIBLE
        mAdapter.swapData(it)
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0
        mRecyclerView.smoothScrollToPosition(mPosition)

    }
    private val errorObserver = Observer<String> {
        Log.i(TAG, "onChanged: $it")
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}