object Versions {
    const val moshi_version = "1.14.0"
    const val kotlin = "1.5.21"
    const val navigation_safe_args_version = "2.6.0"
    const val hilt_version = "2.46.1"
    const val core_ktx_version = "1.10.1"
    const val appcompat_version = "1.6.1"
    const val google_material_version = "1.9.0"
    const val constraint_layout_version = "2.1.4"
    const val room_version = "2.5.2"
    const val coroutines_version = "1.7.1"
    const val navigation_version = "2.3.0"

    const val kotlinCompilerExtensionVersion = "1.4.5"

    const val fragment_ktx_version = "1.6.1"

    object Lifecycle {
        const val runtime_version = "2.3.1"
    }
}

object Classpath {
    const val gradle = "com.android.tools.build:gradle:7.0.2"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation_safe_args_version}"
    const val hiltAndroidGradle =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}"

    const val toolsR8 = "com.android.tools:r8:8.2.24"
}

object Plugins {
    const val application = "com.android.application"
    const val library = "com.android.library"
    const val jetbrainsAndroid = "org.jetbrains.kotlin.android"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"
    const val navigationSafeArgs = "androidx.navigation.safeargs"
    const val kotlinParcelize = "kotlin-parcelize"
    const val hiltAndroid = "dagger.hilt.android.plugin"
}

object AndroidxDependencies {

    const val roomCompiler = "androidx.room:room-compiler:${Versions.room_version}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room_version}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room_version}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.core_ktx_version}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout_version}"
    const val datastorePreferences = "androidx.datastore:datastore-preferences:1.0.0"

    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation_version}"
    const val navigationUIKtx =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation_version}"
    const val navigationUI = "androidx.navigation:navigation-ui:${Versions.navigation_version}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment:${Versions.navigation_version}"

    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx_version}"

    object Lifecycle {
        const val runtime =
            "androidx.lifecycle:lifecycle-runtime:${Versions.Lifecycle.runtime_version}"
    }
}

object KotlinDependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines_version}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"
}

object MaterialDesignDependencies {
    const val materialDesign =
        "com.google.android.material:material:${Versions.google_material_version}"
}

object HiltDependencies {
    const val hiltAndroid = "com.google.dagger:hilt-android:2.46"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:2.46"
}

object SquareUpDependencies {
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi_version}"
    const val moshiKotlinCodegen =
        "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi_version}"
}

object TestDependencies {
    const val annotation = "androidx.annotation:annotation:1.1.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    const val androidxJUnit = "androidx.test.ext:junit:1.1.2"
    const val jUnit = "junit:junit:4.13"
}