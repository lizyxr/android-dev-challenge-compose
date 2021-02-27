/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.androiddevchallenge.ui.theme.MyTheme

private enum class Puppy(val puppyName: String, val details: String, val imageUrl: String) {
    PUPPY1(
        "PUPPY1",
        "PUPPY1 details",
        "https://images.unsplash.com/photo-1591160690555-5debfba289f0?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80"
    ),
    PUPPY2(
        "PUPPY2",
        "PUPPY2 details",
        "https://images.unsplash.com/photo-1593134257782-e89567b7718a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"
    ),
    PUPPY3(
        "PUPPY3",
        "PUPPY3 details",
        "https://images.unsplash.com/photo-1552053831-71594a27632d?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=624&q=80"
    ),
    PUPPY4(
        "PUPPY4",
        "PUPPY4 details",
        "https://images.unsplash.com/photo-1574293876203-8bded53be0f0?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1334&q=80"
    ),
    PUPPY5(
        "PUPPY5",
        "PUPPY5 details",
        "https://images.unsplash.com/photo-1530667912788-f976e8ee0bd5?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1500&q=80"
    )
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

/**
 * Shows the header label.
 *
 * @param title The title to be shown.
 */
@Composable
private fun Header(
    title: String
) {
    Text(
        text = title,
        modifier = Modifier.semantics { heading() },
        style = MaterialTheme.typography.h5
    )
}

@Composable
fun loadPicture(url: String): Bitmap? {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    Glide.with(LocalContext.current).asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    return bitmap
}

@Composable
fun MyApp() {
    val lazyListState = rememberLazyListState()
    var expandedName by remember { mutableStateOf<Puppy?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                }
            )
        }
    ) {
        Surface(color = MaterialTheme.colors.background) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
                state = lazyListState
            ) {
                item { Spacer(modifier = Modifier.height(16.dp)) }
                items(Puppy.values()) { puppy ->
                    PuppyRow(
                        name = puppy.puppyName,
                        details = puppy.details,
                        imageUrl = puppy.imageUrl,
                        expanded = expandedName == puppy,
                        onClick = {
                            expandedName = if (expandedName == puppy) null else puppy
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowSpacer(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

/**
 * Shows a row for one puppy.
 */
@Composable
private fun PuppyRow(
    name: String,
    details: String,
    imageUrl: String,
    expanded: Boolean,
    onClick: () -> Unit
) {
    RowSpacer(visible = expanded)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                // This `Column` animates its size when its content changes.
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color = MaterialTheme.colors.surface)
                    .padding(16.dp)
            ) {
                Surface(
                    modifier = Modifier.size(300.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                ) {
                    loadPicture(imageUrl).let {
                        when {
                            it != null -> {
                                Image(it.asImageBitmap(), name, contentScale = ContentScale.Crop)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.h6
            )
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = details,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
    RowSpacer(visible = expanded)
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
