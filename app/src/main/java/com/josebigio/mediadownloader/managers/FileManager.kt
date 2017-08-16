package com.josebigio.mediadownloader.managers

import android.content.Context
import com.josebigio.mediadownloader.models.MediaFile
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

    val progressMap = HashMap<String,BehaviorSubject<DownloadProgress>>()


    fun getOrCreateFile(fileId: String, filePath: String? = null): Observable<MediaFile> {

        return getRealm().switchMap({
            realmInstance ->
            var mediaFile: MediaFile? = realmInstance.where(MediaFile::class.java).equalTo("fileId", fileId).findFirst()
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

    private fun updateFileForDownload(fileId: String, filePath: String) {
        val realmInstance = Realm.getDefaultInstance()!!
        val mediaFile = MediaFile()
        mediaFile.fileId = fileId
        mediaFile.filePath = filePath
        realmInstance.executeTransaction({
            realm ->
            realm.copyToRealmOrUpdate(mediaFile)
        })
    }

    fun fetchAndSaveFile(videoId: String) {
        downloadManager.startAudioDownload(videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        {
                            (progress, filePath, isWaitingResponse) ->
                            Timber.d("about to update file")
                            if(filePath != null) {
                                updateFileForDownload(videoId, filePath)
                            }
                            var downloadProgress: DownloadProgress
                            if(isWaitingResponse) {
                                downloadProgress = DownloadProgress(progress,true,true)
                            }
                            else {
                                downloadProgress = DownloadProgress(progress,false,true)
                            }
                            getOrCreateProgressSubject(videoId).onNext(downloadProgress)
                        },
                        {
                            error ->
                            Timber.e("Error downloading file $videoId: $error", error)
                        },
                        {
                            getOrCreateProgressSubject(videoId).onNext(DownloadProgress(100,false,false))
                        }


        )
    }

    fun getDownloadProgressForFile(videoId: String): Observable<DownloadProgress> {
        return getOrCreateProgressSubject(videoId).observeOn(AndroidSchedulers.mainThread())
    }

    private fun getOrCreateProgressSubject(videoId: String): BehaviorSubject<DownloadProgress> {
        if(!progressMap.containsKey(videoId)) {
            progressMap.put(videoId, BehaviorSubject.createDefault(DownloadProgress(0,false,false)))
        }
        return progressMap[videoId]!!
    }

    fun createUnmanaged(managed: MediaFile): MediaFile {
        val unmanaged = MediaFile()
        unmanaged.filePath = managed.filePath
        unmanaged.fileId = managed.fileId
        return unmanaged
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

    data class DownloadProgress(val progress:Int, val waitingForResponse:Boolean, val downloadInProgress: Boolean)
}