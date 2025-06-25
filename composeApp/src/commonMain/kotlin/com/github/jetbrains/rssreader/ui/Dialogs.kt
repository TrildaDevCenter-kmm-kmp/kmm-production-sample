package com.github.jetbrains.rssreader.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.jetbrains.rssreader.entity.Feed
import org.jetbrains.compose.resources.stringResource
import rssreader.composeapp.generated.resources.Res
import rssreader.composeapp.generated.resources.rss_feed_url
import rssreader.composeapp.generated.resources.add
import rssreader.composeapp.generated.resources.remove

@Composable
fun AddFeedDialog(
    onAdd: (String) -> Unit,
    onDismiss: () -> Unit
) = Dialog(
    onDismissRequest = onDismiss
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        val input = remember { mutableStateOf(TextFieldValue()) }
        Text(text = stringResource(Res.string.rss_feed_url))
        TextField(
            maxLines = 3,
            value = input.value,
            onValueChange = { input.value = it }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                onAdd(
                    input.value.text.replace("http://", "https://")
                )
            }
        ) {
            Text(text = stringResource(Res.string.add))
        }
    }
}

@Composable
fun DeleteFeedDialog(
    feed: Feed,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) = Dialog(
    onDismissRequest = onDismiss
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = feed.sourceUrl)
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = { onDelete() }
        ) {
            Text(text = stringResource(Res.string.remove))
        }
    }
}