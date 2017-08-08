package com.josebigio.mediadownloader.mappers

import com.josebigio.mediadownloader.api.models.InfoResponse
import com.josebigio.mediadownloader.models.ItemInfo
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by josebigio on 8/6/17.
 */
class ItemInfoMapper @Inject constructor() {

    fun transform(infoResponse: InfoResponse): ItemInfo {
        val result = infoResponse.results
        val filteredDuration = result.duration.replace(Regex("\\D"),",")
        val durationArray = filteredDuration.split(Regex(",+"))
        var duration = result.duration
        val parser    = ISODateTimeFormat.dateTimeParser()
        val publication = parser.parseDateTime(result.publication)
        val formattedPublication = DateTimeFormat.forPattern("MM/dd/yyyy").print(publication)
        try {
            duration = "${durationArray[1]}h ${durationArray[2]}m ${durationArray[3]}s"
        }
        catch (e: IndexOutOfBoundsException) {
           Timber.d("Failed to parse duration")
        }
        return ItemInfo(result.title,result.description,duration,formattedPublication,result.thumbnails.high.url)
    }
}