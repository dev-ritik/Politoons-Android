package io.github.dev_ritik.politoons

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ToonRepository {
    private val LOG_TAG = ToonRepository::class.java.getSimpleName()

    // For Singleton instantiation
    private val LOCK = Any()

    //    private var sInstance: ToonRepository? = null
    companion object {
        var sInstance: ToonRepository? = null
    }

    private var mInitialized = false
//    private val diskIO: Executor? = null

    @Synchronized
    fun getInstance(

    ): ToonRepository? {
        Log.i(LOG_TAG, "Getting the repository$sInstance")
        if (sInstance == null) {
            synchronized(LOCK) {
                sInstance = ToonRepository(
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

    fun fetch() {
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
                if (result != null) {
                    for (i in result) {
                        Log.i(LOG_TAG, "items : ${i.name}")
                    }
                }
            }, { error ->
                error.printStackTrace()
            })

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