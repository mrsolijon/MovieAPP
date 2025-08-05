package uz.mrsolijon.movieapp.utils

import uz.mrsolijon.movieapp.R
import android.content.Context
import androidx.appcompat.app.ActionBar.LayoutParams
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup



fun ChipGroup.addChip(label: String, context: Context) {
    Chip(context).apply {
        text = label
        isClickable = false
        isCheckable = false
        isCheckedIconVisible = true
        setChipStartPaddingResource(R.dimen.dimension_12dp)
        setChipEndPaddingResource(R.dimen.dimension_12dp)
        chipCornerRadius=resources.getDimension(R.dimen.dimension_16dp)
        includeFontPadding = false
        width = LayoutParams.WRAP_CONTENT
        height = LayoutParams.WRAP_CONTENT
        addView(this)
    }
}

fun configureGenresChipGroup(
    genres: List<String>,
    chipGroup: ChipGroup,
    context: Context
) {
    genres.forEach { genre ->
        chipGroup.addChip(
            genre, context
        )
    }

}