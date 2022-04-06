package hu.jm.newyorktimes.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NYTimesResult(
    var copyright: String?,
    var num_results: Int?,
    var results: List<Result>,
    var status: String?
)
@JsonClass(generateAdapter = true)
data class Result(
    var abstract: String?,
    var adx_keywords: String?,
    var asset_id: Long?,
    var byline: String,
    var column: Any?,
    var des_facet: List<String>?,
    var eta_id: Int?,
    var geo_facet: List<String>?,
    var id: Long?,
    var media: List<Media>,
    var nytdsection: String?,
    var org_facet: List<String>?,
    var per_facet: List<String>?,
    var published_date: String?,
    var section: String?,
    var source: String?,
    var subsection: String?,
    var title: String,
    var type: String?,
    var updated: String,
    var uri: String?,
    var url: String
)
@JsonClass(generateAdapter = true)
data class Media(
    var approved_for_syndication: Int?,
    var caption: String?,
    var copyright: String?,
    @Json(name = "media-metadata")
    var mediaMetadata: List<MediaMetadata?>?,
    var subtype: String?,
    var type: String?
)
@JsonClass(generateAdapter = true)
data class MediaMetadata(
    var format: String?,
    var height: Int?,
    var url: String,
    var width: Int?
)