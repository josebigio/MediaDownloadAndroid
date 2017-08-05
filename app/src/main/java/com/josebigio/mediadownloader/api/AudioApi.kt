package com.josebigio.mediadownloader.api

import com.josebigio.mediadownloader.api.models.InfoResponse
import com.josebigio.mediadownloader.models.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by josebigio on 7/30/17.
 */
interface AudioApi {
    @GET("/search")
    fun searchAudio(@Query("q") query: String): Observable<SearchResponse>

    @GET("/info")
    fun getInfo(@Query("id") id: String): Observable<InfoResponse>
}