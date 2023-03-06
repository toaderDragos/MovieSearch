package com.dragos.moviesearch.data
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// An elegant way to parse JSON is to split the object according to the API call, instead of using cumbersome Jason parser functions
@Parcelize
data class Movie(
    @SerializedName("show")
    val show: Show
) : Parcelable

@Parcelize
data class Show(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("runtime")
    val runTime: String,
    @SerializedName("premiered")
    val premiered: String,
    @SerializedName("officialSite")
    val officialSite: String,
    @SerializedName("rating")
    val rating: Average,
    @SerializedName("network")
    val network: MovieBroadcastingNetwork,
    @SerializedName("externals")
    val externals: ExternalAppsIds,
    @SerializedName("image")
    val image: Image1,
    @SerializedName("summary")
    val summary: String
    ): Parcelable

@Parcelize
data class Image1(
    @SerializedName("medium")
    val medium: String
): Parcelable

@Parcelize
data class ExternalAppsIds (
    @SerializedName("tvrage")
    val tvrage: String,
    @SerializedName("thetvdb")
    val thetvdb: String,
    @SerializedName("imdb")
    val imdb: String
): Parcelable

@Parcelize
data class MovieBroadcastingNetwork (
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: Country
): Parcelable

@Parcelize
data class Country (
    @SerializedName("name")
    val name: String
): Parcelable

@Parcelize
data class Average (
    @SerializedName("average")
    val average: String
): Parcelable