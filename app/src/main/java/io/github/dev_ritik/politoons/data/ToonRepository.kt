package io.github.dev_ritik.politoons.data

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.github.dev_ritik.politoons.model.Politoon
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ToonRepository {

    var allToons: MutableLiveData<List<Politoon>> = MutableLiveData()

    private val LOG_TAG = ToonRepository::class.java.simpleName

    // For Singleton instantiation

    //    private var sInstance: ToonRepository? = null
    companion object {
        private val LOCK = Any()
        @Synchronized
        public fun getInstance(): ToonRepository? {
            Log.i("point", "Getting the repository$sInstance")
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance =
                            ToonRepository(
                            )
                    Log.i("point", "Made new repository")
                }
            }
            return sInstance

        }

        var sInstance: ToonRepository? = null
    }

    private var mInitialized = false
//    private val diskIO: Executor? = null

    @Synchronized
    public fun getInstance(
        allToons: MutableLiveData<List<Politoon>>
    ): ToonRepository? {
        Log.i(LOG_TAG, "Getting the repository$sInstance")
        if (sInstance == null) {
            synchronized(LOCK) {
                sInstance =
                        ToonRepository(
                        )
                Log.i(LOG_TAG, "Made new repository")
            }
        }
        return sInstance
    }

    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    @Synchronized
    private fun initializeData() {
        Log.i(LOG_TAG, "initializeData: ")
        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return
        mInitialized = true

        fetch()
    }

    fun fetch(): MutableLiveData<List<Politoon>> {

        Log.i(LOG_TAG, "fetch: ")
        val BASE_URL = "https://politoons.herokuapp.com"

        val ret: ToonsDataAPI = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ToonsDataAPI::class.java)

        val call = ret.data.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(fun(result: List<Politoon>?) {
                Log.i("Result", "There are ${result?.size} Java developers in Lagos")
                allToons.postValue(result)
                if (result != null) {
                    for (i in result) {
                        Log.i(LOG_TAG, "items : ${i.name}")
                    }
                }
            }, { error ->
                error?.printStackTrace()
            })

        return allToons
    }
}

//    private fun isFetchNeeded(): Boolean {
//        val today = SunshineDateUtils.getNormalizedUtcDateForToday()
//        val count = mWeatherDao.countAllFutureWeather(today)
//        return count < WeatherNetworkDataSource.NUM_DAYS
//    }
//
//    private fun startFetchWeatherService() {
//        Log.i("point", "startFetchWeatherService: ")
//        mWeatherNetworkDataSource.startFetchWeatherService()
//    }
//
//    fun getWeatherByDate(date: Date): LiveData<WeatherEntry> {
//        initializeData()
//        return mWeatherDao.getWeatherByDate(date)
//    }
//}