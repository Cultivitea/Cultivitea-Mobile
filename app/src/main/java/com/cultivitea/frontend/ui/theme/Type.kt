package com.cultivitea.frontend.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.cultivitea.frontend.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val inter = GoogleFont("Inter")
val epilogue = GoogleFont("Epilogue")

val fontFamilyInter = FontFamily(
    Font(googleFont = inter, fontProvider = provider)
)
val fontFamilyEpilogue = FontFamily(
//    Font(googleFont = epilogue, fontProvider = provider)
    androidx.compose.ui.text.font.Font(R.font.epilogue_black, FontWeight.Black),
    androidx.compose.ui.text.font.Font(R.font.epilogue_bold, FontWeight.Bold),
    androidx.compose.ui.text.font.Font(R.font.epilogue_light, FontWeight.Light),
    androidx.compose.ui.text.font.Font(R.font.epilogue_medium, FontWeight.Medium),
    androidx.compose.ui.text.font.Font(R.font.epilogue_regular, FontWeight.Normal),
    androidx.compose.ui.text.font.Font(R.font.epilogue_semibold, FontWeight.SemiBold),
    androidx.compose.ui.text.font.Font(R.font.epilogue_thin, FontWeight.Thin)

)

// Set of Material typography styles to start with
val Typography = Typography(
    //Field Labels
    labelSmall = TextStyle(
        fontFamily = fontFamilyInter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryGreen
    ),

    // Subtitle
    titleMedium = TextStyle(
        fontFamily = fontFamilyEpilogue,
        fontWeight = FontWeight.Bold,
        fontSize = 54.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.15.sp,
        color = PrimaryGreen
    ),


    // Discussion Title
    titleLarge = TextStyle(
        fontFamily = fontFamilyInter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.15.sp,
        color = PrimaryGreen
    ),

    titleSmall = TextStyle(
        fontFamily = fontFamilyEpilogue,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.15.sp,
        color = SecondaryBrown
    ),

    bodySmall = TextStyle(
        fontFamily = fontFamilyEpilogue,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = SecondaryBrown
    ),

    labelMedium = TextStyle(
        fontFamily = fontFamilyInter,
        fontSize = 10.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryOrange
    ),

    bodyMedium = TextStyle(
        fontFamily = fontFamilyInter,
        fontSize = 6.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = GrayInput
    ),

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)