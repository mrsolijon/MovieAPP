package uz.mrsolijon.movieapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class MovieCastResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("id")
    val id: Int
)