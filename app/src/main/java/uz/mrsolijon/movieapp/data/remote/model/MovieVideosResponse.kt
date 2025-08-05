package uz.mrsolijon.movieapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class MovieVideosResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val videos: List<Video>
)