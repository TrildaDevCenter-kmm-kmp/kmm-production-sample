package com.github.jetbrains.rssreader.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.jetbrains.rssreader.app.FeedAction
import com.github.jetbrains.rssreader.app.FeedStore
import com.github.jetbrains.rssreader.domain.RssFeed
import org.jetbrains.compose.resources.vectorResource
import rssreader.composeapp.generated.resources.Res
import rssreader.composeapp.generated.resources.ic_add

@Composable
fun FeedList(store: FeedStore) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val state = store.observeState().collectAsState()
        val showAddDialog = remember { mutableStateOf(false) }
        val feedForDelete = remember<MutableState<RssFeed?>> { mutableStateOf(null) }
        FeedItemList(feeds = state.value.feeds) {
            feedForDelete.value = it
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .navigationBarsPadding()
                .imePadding(),
            onClick = { showAddDialog.value = true }
        ) {
            Image(
                imageVector = vectorResource(Res.drawable.ic_add),
                modifier = Modifier.align(Alignment.Center),
                contentDescription = null
            )
        }
        if (showAddDialog.value) {
            AddFeedDialog(
                onAdd = {
                    store.dispatch(FeedAction.Add(it))
                    showAddDialog.value = false
                },
                onDismiss = {
                    showAddDialog.value = false
                }
            )
        }
        feedForDelete.value?.let { feed ->
            DeleteFeedDialog(
                feed = feed,
                onDelete = {
                    store.dispatch(FeedAction.Delete(feed.sourceUrl))
                    feedForDelete.value = null
                },
                onDismiss = {
                    feedForDelete.value = null
                }
            )
        }
    }
}

@Composable
fun FeedItemList(
    feeds: List<RssFeed>,
    onClick: (RssFeed) -> Unit
) {
    LazyColumn {
        itemsIndexed(feeds) { i, feed ->
            if (i == 0) Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            FeedItem(feed) { onClick(feed) }
        }
    }
}

@Composable
fun FeedItem(
    feed: RssFeed,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .clickable(onClick = onClick, enabled = !feed.isDefault)
            .padding(16.dp)
    ) {
        FeedIcon(feed = feed)
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            if(feed.channel?.title != null) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = feed.channel.title
                )
            }
            if (feed.channel?.description != null) {
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = feed.channel.description
                )
            }
        }
    }
}
