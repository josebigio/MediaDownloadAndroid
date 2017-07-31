package com.josebigio.mediadownloader.api

import com.josebigio.mediadownloader.models.SearchResponse
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by josebigio on 7/30/17.
 */
@Singleton
class ApiManager @Inject constructor(val audioApi: AudioApi) {

    fun searchVideo(query: String ): Observable<SearchResponse> {
        return audioApi.searchAudio(query)
    }
}