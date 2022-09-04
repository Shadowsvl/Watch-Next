package com.heka.watchnext.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.heka.watchnext.R

val NunitoFamily = FontFamily(
    Font(resId = R.font.nunito_sans_light, weight = FontWeight.Light),
    Font(resId = R.font.nunito_sans, weight = FontWeight.Normal),
    Font(resId = R.font.nunito_sans, weight = FontWeight.Medium),
    Font(resId = R.font.nunito_sans_semibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.nunito_sans_bold, weight = FontWeight.Bold)
)

val Typography = Typography(
    defaultFontFamily = NunitoFamily,
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)