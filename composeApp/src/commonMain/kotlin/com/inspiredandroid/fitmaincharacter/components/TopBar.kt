package com.inspiredandroid.fitmaincharacter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.inspiredandroid.fitmaincharacter.TitleStyle
import com.inspiredandroid.fitmaincharacter.WarmCamel
import org.jetbrains.compose.resources.painterResource
import sport.composeapp.generated.resources.Res
import sport.composeapp.generated.resources.ic_arrow_back

@Composable
fun TopBar(title: String, onNavigateBack: (() -> Unit)? = null) {
    Row(modifier = Modifier.height(64.dp), verticalAlignment = Alignment.CenterVertically) {
        if (onNavigateBack != null) {
            Box(Modifier.padding(12.dp).clickable { onNavigateBack.invoke() }) {
                Icon(
                    painterResource(Res.drawable.ic_arrow_back),
                    null,
                    Modifier.size(32.dp),
                    tint = WarmCamel,
                )
            }
        } else {
            Spacer(Modifier.width(48.dp))
        }
        Text(
            modifier = Modifier.weight(1f),
            text = title.uppercase(),
            style = TitleStyle(),
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.width(48.dp))
    }
}
