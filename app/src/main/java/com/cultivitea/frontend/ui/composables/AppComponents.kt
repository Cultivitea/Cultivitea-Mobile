package com.cultivitea.frontend.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import com.cultivitea.frontend.R

@Composable
fun ClickableAuthText(onNavigateToRegister: () -> Unit, hyperlink: Int, description: Int) {
    Row {
        Text(
            text = stringResource(id = description),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = stringResource(id = hyperlink),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.clickable { onNavigateToRegister() }
        )
    }
}