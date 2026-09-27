# Palette Example

A small Android sample app that extracts prominent colors from an image using the [AndroidX Palette library](https://developer.android.com/reference/kotlin/androidx/palette/graphics/Palette) and displays the results in a Jetpack Compose UI.

Pick a photo from the device gallery, and the app decodes the bitmap, builds a `Palette`, and shows every swatch plus named groups (muted, vibrant, and their light/dark variants).

You can find more description and examples of its usage in this [article](https://www.linkedin.com/pulse/android-system-palette-available-everyone-shady-yehia-selim-msc-mba-iqhne/)

## Features

- **Photo picker** — Select any image via the system photo picker (`PickVisualMedia`).
- **Palette generation** — Uses `Palette.Builder` on a software-backed, mutable bitmap suitable for palette analysis.
- **Swatch UI** — Lists all swatches and labeled rows for muted and vibrant families.
- **Compose state** — Selected image URI is stored with `rememberSaveable`; bitmap and palette are rebuilt in `LaunchedEffect` after rotation or process recreation.

p.s.: in this sample project rememberSaveable is enough. If you later add caching, repositories, or more complex state, a ViewModel would make more sense.

## Project sample video

https://github.com/user-attachments/assets/06e5e84a-1389-4cee-b9dc-935d1da2c878

## Tech stack

| Area | Choice |
|------|--------|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Color extraction | `androidx.palette:palette-ktx` |
| Min SDK | 28 |
| Target / compile SDK | 37 |
| JDK | 17 |

## Requirements

- [Android Studio](https://developer.android.com/studio) (recent version with Compose support)
- JDK 17
- Android device or emulator running API 28+

## Getting started

1. Clone the repository:

   ```bash
   git clone https://github.com/Shady-Selim/Palette-Example.git
   cd Palette-Example
   ```

2. Open the project in Android Studio and let Gradle sync finish.

3. Run the **app** configuration on a device or emulator.

From the command line:

```bash
./gradlew assembleDebug
```

The debug APK is produced under `app/build/outputs/apk/debug/`.

## Usage

1. Launch **Palette Demo**.
2. Tap the floating action button (search icon) to open the image picker.
3. Choose an image.
4. Scroll to view the full swatch list and the named muted/vibrant swatches.

## Project structure

```
app/src/main/java/com/example/myapplication/
├── Palette.kt              # Activity, main Compose screen, bitmap decoding
└── ui/theme/Theme.kt       # Material 3 theme
```

`PaletteScreen` is the launcher activity and hosts `PaletteCompose()`, which owns picker registration, state, and palette UI.

## How palette extraction works

1. The picked content `Uri` is saved as a string (`rememberSaveable`).
2. On a background dispatcher, `ImageDecoder` loads the image with a software allocator and a mutable bitmap.
3. `Palette.Builder(bitmap).generate()` produces swatches; the UI reads `swatches`, `mutedSwatch`, `vibrantSwatch`, and related properties.

For design guidance on using palette colors in apps, see [Extract colors from an image](https://developer.android.com/training/material/images-colors).

## License

No license required, it is an open source sample project.
