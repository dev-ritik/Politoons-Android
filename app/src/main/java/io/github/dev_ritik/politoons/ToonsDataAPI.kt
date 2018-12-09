package io.github.dev_ritik.politoons


import io.reactivex.Observable
import retrofit2.http.GET

interface ToonsDataAPI {

    @get:GET("myapp/api/")
    val data: Observable<List<Politoon>>
}
