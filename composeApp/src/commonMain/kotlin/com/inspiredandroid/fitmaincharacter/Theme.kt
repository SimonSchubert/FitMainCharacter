package com.inspiredandroid.fitmaincharacter

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fitmaincharacter.composeapp.generated.resources.Res
import fitmaincharacter.composeapp.generated.resources.RussoOne_Regular
import org.jetbrains.compose.resources.Font

val ScreenBackground = Color(0xff121212)
val CardBackground = Color.White.copy(alpha = 0.06f)
val TileBackground = Color.White.copy(alpha = 0.02f)
val WarmCamel = Color(0xffbf9669)
val SoftSand = Color(0xffe7cca9)

@Composable
fun TitleStyle() = TextStyle(
    color = WarmCamel,
    fontFamily = SportFontFamily(),
    fontSize = MaterialTheme.typography.h4.fontSize,
    fontWeight = MaterialTheme.typography.h4.fontWeight,
)

@Composable
fun SmallTextStyle() = TextStyle(
    color = SoftSand,
    fontFamily = SportFontFamily(),
    fontSize = 14.sp,
    fontWeight = FontWeight.Normal,
)

@Composable
fun MediumTextStyle() = TextStyle(
    color = SoftSand,
    fontFamily = SportFontFamily(),
    fontSize = MaterialTheme.typography.h6.fontSize,
    fontWeight = MaterialTheme.typography.h6.fontWeight,
)

@Composable
fun LargeCountdownStyle() = TextStyle(
    color = WarmCamel,
    fontFamily = SportFontFamily(),
    fontSize = 48.sp,
    fontWeight = FontWeight.Bold,
)

@Composable
fun SportFontFamily(): FontFamily {
    val regular = Font(Res.font.RussoOne_Regular, FontWeight.W400)

    return remember {
        FontFamily(regular)
    }
}
