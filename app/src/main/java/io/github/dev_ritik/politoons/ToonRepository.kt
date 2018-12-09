package io.github.dev_ritik.politoons

import android.util.Log


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

//    private fun ToonRepository(
//    ): ??? {
//
//    }


    @Synchronized
    fun getInstance(

    ): ToonRepository? {
        Log.i(LOG_TAG, "Getting the repository" + sInstance)
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

    private fun fetch() {

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