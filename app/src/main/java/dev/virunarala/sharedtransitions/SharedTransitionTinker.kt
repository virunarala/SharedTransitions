package dev.virunarala.sharedtransitions

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ListItem(
    val id: Int,
    val name: String,
    val color: Color
)

val listItems = listOf (
    ListItem(id = 1, name = "One", color = Color.Gray),
    ListItem(id = 2, name = "Two", color = Color.Blue),
    ListItem(id = 3, name = "Three", color = Color.Red),
    ListItem(id = 4, name = "Four", color = Color.Cyan),
    ListItem(id = 5, name = "Five", color = Color.Green),
    ListItem(id = 6, name = "Six", color = Color.Magenta),
    ListItem(id = 7, name = "One", color = Color.Gray),
    ListItem(id = 8, name = "Two", color = Color.Blue),
    ListItem(id = 9, name = "Three", color = Color.Red),
    ListItem(id = 10, name = "Four", color = Color.Cyan),
    ListItem(id = 11, name = "Five", color = Color.Green),
    ListItem(id = 12, name = "Six", color = Color.Magenta),
    ListItem(id = 13, name = "One", color = Color.Gray),
    ListItem(id = 14, name = "Two", color = Color.Blue),
    ListItem(id = 15, name = "Three", color = Color.Red),
    ListItem(id = 16, name = "Four", color = Color.Cyan),
    ListItem(id = 17, name = "Five", color = Color.Green),
    ListItem(id = 18, name = "Six", color = Color.Magenta),
    ListItem(id = 19, name = "One", color = Color.Gray),
    ListItem(id = 20, name = "Two", color = Color.Blue),
    ListItem(id = 21, name = "Three", color = Color.Red),
    ListItem(id = 22, name = "Four", color = Color.Cyan),
    ListItem(id = 23, name = "Five", color = Color.Green),
    ListItem(id = 24, name = "Six", color = Color.Magenta),
    ListItem(id = 25, name = "One", color = Color.Gray),
    ListItem(id = 26, name = "Two", color = Color.Blue),
    ListItem(id = 27, name = "Three", color = Color.Red),
    ListItem(id = 28, name = "Four", color = Color.Cyan),
    ListItem(id = 29, name = "Five", color = Color.Green),
    ListItem(id = 30, name = "Six", color = Color.Magenta),
    ListItem(id = 31, name = "One", color = Color.Gray),
    ListItem(id = 32, name = "Two", color = Color.Blue),
    ListItem(id = 33, name = "Three", color = Color.Red),
    ListItem(id = 34, name = "Four", color = Color.Cyan),
)

val STATE_LIST = "state-list"
val STATE_DETAIL = "state-detail"


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionTinker(modifier: Modifier = Modifier) {

    SharedTransitionLayout(modifier) {

        var selectedItem by remember {
            mutableStateOf<ListItem>(listItems.first())
        }
        var currentState by remember {
            mutableStateOf(STATE_LIST)
        }

        val listState = rememberLazyListState()

        AnimatedContent(
            targetState = currentState,
            /*transitionSpec = {
                (fadeIn(animationSpec = tween(10000, delayMillis = 9000)) +
                        scaleIn(initialScale = 0.92f, animationSpec = tween(10000, delayMillis = 9000)))
                    .togetherWith(fadeOut(animationSpec = tween(10000)))
            }*/
        ) {
            when(it) {
                STATE_LIST -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(listItems) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        currentState = STATE_DETAIL
                                        selectedItem = it
                                    }
                            ) {

                                Image(
                                    painter = painterResource(id = R.drawable.ed_sheeran_equals),
                                    contentDescription = "Equals by Ed Sheeran",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .sharedElement(
                                            state = rememberSharedContentState(key = it.id.toString()),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                        .clip(RoundedCornerShape(20.dp))
                                )

                                Text(
                                    text = it.name,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(start = 24.dp)
                                )

                            }
                        }
                    }
                }

                STATE_DETAIL -> {
                    DetailItem(
                        selectedItem,
                        this@AnimatedContent,
                        onBack = {
                            currentState = STATE_LIST
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp)
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailItem(
    item: ListItem,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBack: () -> Unit,
    modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ed_sheeran_equals),
            contentDescription = "Equals by Ed Sheeran",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(300.dp, 400.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = item.id.toString()),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .clip(RoundedCornerShape(20.dp))
        )

        Text(
            text = item.name,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }

    BackHandler {
        onBack()
    }
}

@Preview(showBackground = true)
@Composable
private fun SharedTransitionTinkerPreview() {
    SharedTransitionTinker(modifier = Modifier.fillMaxSize())
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    SharedTransitionScope {
        AnimatedContent(targetState = 0) {
            val x = it
            DetailItem(
                item = listItems[2],
                animatedVisibilityScope = this@AnimatedContent,
                onBack = {  },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}