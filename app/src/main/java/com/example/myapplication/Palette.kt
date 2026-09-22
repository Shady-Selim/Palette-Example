package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.net.toUri

class PaletteScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                PaletteCompose()
            }
        }
    }
}

@Composable
fun PaletteCompose() {
    val context = LocalContext.current
    var selectedImageUri by rememberSaveable { mutableStateOf<String?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var palette by remember { mutableStateOf<Palette?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedImageUri = uri?.toString()
    }

    LaunchedEffect(selectedImageUri) {
        val uriString = selectedImageUri
        if (uriString == null) {
            bitmap = null
            palette = null
            return@LaunchedEffect
        }

        val decoded = withContext(Dispatchers.IO) {
            decodeBitmap(context, uriString.toUri())
        }
        bitmap = decoded
        palette = decoded?.let { Palette.Builder(it).generate() }
    }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.select_image)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            bitmap?.run {
                Image(
                    bitmap = asImageBitmap(),
                    contentDescription = stringResource(id = R.string.palette_selected_image),
                )
            }
            palette?.run {
                swatches.forEach { swatch ->
                    Box(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .height(dimensionResource(R.dimen.shape_height))
                            .clip(RectangleShape)
                            .background(Color(swatch.rgb))
                    ) {
                        Text(swatch.toString(), color = Color(swatch.bodyTextColor))
                    }
                }
                Text("By Swatch Name")
                Text("Muted")
                Row {
                    mutedSwatch?.let {
                        PaletteSwatchBox("Palette.mutedSwatch", it)
                    }
                    darkMutedSwatch?.let {
                        PaletteSwatchBox("Palette.darkMutedSwatch", it)
                    }
                    lightMutedSwatch?.let {
                        PaletteSwatchBox("Palette.lightMutedSwatch", it)
                    }
                }
                Text("Vibrant")
                Row {
                    vibrantSwatch?.let {
                        PaletteSwatchBox("Palette.vibrantSwatch", it)
                    }
                    darkVibrantSwatch?.let {
                        PaletteSwatchBox("Palette.darkVibrantSwatch", it)
                    }
                    lightVibrantSwatch?.let {
                        PaletteSwatchBox("Palette.lightVibrantSwatch", it)
                    }
                }
            }
        }
    }
}

@Composable
fun PaletteSwatchBox(txt: String, swatch: Palette.Swatch) {
    Box(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.swatch_padding))
            .height(dimensionResource(R.dimen.shape_height))
            .background(Color(swatch.rgb))
    ) {
        Text(txt, color = Color(swatch.bodyTextColor))
    }
}

private fun decodeBitmap(context: android.content.Context, uri: Uri): Bitmap? {
    return try {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            decoder.isMutableRequired = true
        }
    } catch (_: Exception) {
        null
    }
}
