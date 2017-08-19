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
        val parser    = ISODateTimeFormat.dateTimeParser()
        val publication = parser.parseDateTime(result.publication)
        val formattedPublication = DateTimeFormat.forPattern("MM/dd/yyyy").print(publication)
        return ItemInfo(result.title,result.description,result.duration.replace("PT","").toLowerCase(),formattedPublication,result.thumbnails.high.url)
    }
}