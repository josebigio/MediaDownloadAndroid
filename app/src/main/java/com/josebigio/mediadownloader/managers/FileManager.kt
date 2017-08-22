package com.josebigio.mediadownloader.managers

import android.content.Context
import com.josebigio.mediadownloader.models.MediaFile
import com.josebigio.utils.RXUtils.RetryWithDelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.realm.Realm
import io.realm.RealmChangeListener
import timber.log.Timber


/**
* Created by josebigio on 8/9/17.
*/
class FileManager(val context: Context, val downloadManager: DownloadManager) {

    val progressMap = HashMap<String, BehaviorSubject<DownloadProgress>>()


    fun getOrCreateFile(fileId: String, filePath: String? = null): Observable<MediaFile> {

        return getRealm().switchMap({
            realmInstance ->
            var mediaFile: MediaFile? = realmInstance.where(MediaFile::class.java)
                    .equalTo("fileId", fileId)
                    .findFirst()
            if (mediaFile == null) {
                realmInstance.beginTransaction()
                val newMediaFile = realmInstance.createObject(MediaFile::class.java, fileId)
                newMediaFile.filePath = filePath
                realmInstance.commitTransaction()
                mediaFile = newMediaFile

            }
            Flowable.just(createUnmanaged(mediaFile!!))
        }).toObservable()
    }

    fun getAllFiles(): Observable<List<MediaFile>> {
        return getRealm().switchMap {
            realmInstance ->
            val result = ArrayList<MediaFile>()
            realmInstance.where(MediaFile::class.java)
                    .findAll().filter { mediaFile -> mediaFile.filePath != null }
                    .forEach({
                        mediaFile ->
                        result.add(createUnmanaged(mediaFile))
                    })
                     Flowable.just(result.toList())
            }.toObservable()
    }

    fun fetchAndSaveFile(videoId: String) {
        downloadManager.startAudioDownload(videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(3,1000))
                .subscribe(
                        {
                            (progress, filePath, isWaitingResponse) ->
                            if (filePath != null) {
                                updateOrCreateFile(fileId = videoId, filePath = filePath)
                            }
                            val downloadProgress: DownloadProgress
                            if (isWaitingResponse) {
                                downloadProgress = DownloadProgress(progress, true, true)
                            } else {
                                downloadProgress = DownloadProgress(progress, false, true)
                            }
                            getOrCreateProgressSubject(videoId).onNext(downloadProgress)
                        },
                        {
                            error ->
                            Timber.e("Error downloading file $videoId: $error", error)
                        },
                        {
                            getOrCreateProgressSubject(videoId).onNext(DownloadProgress(100, false, false))
                        }


                )
    }

    fun getDownloadProgressForFile(videoId: String): Observable<DownloadProgress> {
        return getOrCreateProgressSubject(videoId).observeOn(AndroidSchedulers.mainThread())
    }

    fun updateOrCreateFile(fileId: String, filePath: String? = null,
                           lastPlayedMilli: Long = -1, fileName: String? = null,
                           thumnailUrl: String? = null) {
        val realmInstance = Realm.getDefaultInstance()!!
        realmInstance.executeTransaction({
            realm ->
            var mediaFile: MediaFile? = realm.where(MediaFile::class.java)
                    .equalTo("fileId", fileId).findFirst()
            if (mediaFile == null) {
                mediaFile = MediaFile()
                mediaFile.fileId = fileId
            }
            mediaFile.filePath = filePath ?: mediaFile.filePath
            mediaFile.fileName = fileName ?: mediaFile.fileName
            mediaFile.thumnailUrl = thumnailUrl ?: mediaFile.thumnailUrl
            if (lastPlayedMilli >= 0) {
                mediaFile.lastPlayedMilli = lastPlayedMilli
            }
        })
    }


    private fun getOrCreateProgressSubject(videoId: String): BehaviorSubject<DownloadProgress> {
        if (!progressMap.containsKey(videoId)) {
            progressMap.put(videoId, BehaviorSubject.createDefault(DownloadProgress(0, false, false)))
        }
        return progressMap[videoId]!!
    }


    fun createUnmanaged(managed: MediaFile): MediaFile {
        return managed.copy()
    }

    private fun getRealm(): Flowable<Realm> {
        return Flowable.create({ emitter ->
            val realmObservable = Realm.getDefaultInstance()
            val listener = RealmChangeListener<Realm> { realm -> emitter?.onNext(realm) }
            emitter?.setDisposable(Disposables.fromRunnable {
                realmObservable.removeChangeListener(listener)
                realmObservable.close()
            })
            realmObservable.addChangeListener(listener)
            emitter?.onNext(realmObservable)
        }, BackpressureStrategy.LATEST)
    }

    data class DownloadProgress(val progress: Int, val waitingForResponse: Boolean, val downloadInProgress: Boolean)
}