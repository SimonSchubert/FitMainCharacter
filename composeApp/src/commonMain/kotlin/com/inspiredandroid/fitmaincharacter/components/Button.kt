package com.inspiredandroid.fitmaincharacter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inspiredandroid.fitmaincharacter.SoftSand
import com.inspiredandroid.fitmaincharacter.SportFontFamily

@Composable
fun LightButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    size: ButtonSize = ButtonSize.NORMAL,
    imagePainter: Painter? = null,
    isEnabled: Boolean = true,
    isVisible: Boolean = true,
) {
    FilledButton(
        modifier = modifier,
        onClick = onClick,
        text = text,
        backgroundColor = SoftSand,
        disabledBackgroundColor = SoftSand,
        contentColor = Color(0xFF0A0A0A),
        disabledContentColor = Color(0xFF0A0A0A),
        size = size,
        imagePainter = imagePainter,
        isEnabled = isEnabled,
        isVisible = isVisible,
    )
}

enum class ButtonSize(
    val minHeight: Dp,
    val fontSize: TextUnit,
    val horizontalSpacing: Dp,
    val verticalSpacing: Dp,
    val imageSpacing: Dp,
    val imageSize: Dp,
) {
    NORMAL(
        minHeight = 44.dp,
        fontSize = 16.sp,
        horizontalSpacing = 24.dp,
        verticalSpacing = 10.dp,
        imageSpacing = 10.dp,
        imageSize = 20.dp,
    ),
}

@Composable
private fun FilledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    backgroundColor: Color,
    disabledBackgroundColor: Color,
    contentColor: Color,
    disabledContentColor: Color,
    size: ButtonSize = ButtonSize.NORMAL,
    imagePainter: Painter? = null,
    isEnabled: Boolean = true,
    isVisible: Boolean = true,
) {
    Row(
        modifier = modifier
            .heightIn(min = size.minHeight)
            .alpha(if (isVisible) 1f else 0f)
            .background(
                color = if (isEnabled) backgroundColor else disabledBackgroundColor,
                shape = RoundedCornerShape(size = 100.dp),
            )
            .clip(RoundedCornerShape(size = 100.dp))
            .clickable(isVisible && isEnabled) { onClick() }
            .padding(horizontal = size.horizontalSpacing, vertical = size.verticalSpacing),
        horizontalArrangement = Arrangement.spacedBy(size.imageSpacing, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        imagePainter?.let {
            Image(
                modifier = Modifier.height(size.imageSize),
                painter = it,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            text = text,
            fontSize = size.fontSize,
            fontFamily = SportFontFamily(),
            fontWeight = FontWeight(600),
            color = if (isEnabled) contentColor else disabledContentColor,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
