package com.josebigio.mediadownloader.views;

import com.josebigio.mediadownloader.api.ApiManager;
import com.josebigio.mediadownloader.models.SearchResponse;
import com.josebigio.mediadownloader.views.adapters.SearchItem;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by josebigio on 8/1/17.
 */

public class DeleteMe  {

    ApiManager apiManager;

    public void Dude() {
        apiManager.searchVideo("kik").subscribe();
    }
}
