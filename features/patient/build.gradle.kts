@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(Plugins.library)
    id(Plugins.jetbrainsAndroid)
    //id("org.jetbrains.kotlin.android")
    //id(Plugins.googleService)
    id(Plugins.kotlinKapt)
    id(Plugins.hiltAndroid)
    id(Plugins.kotlinParcelize)
}

android {
    namespace = "com.priyanshudev.patient"
    compileSdk = ConfigurationData.compileSdk

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       // consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(AndroidxDependencies.coreKtx)
    implementation(AndroidxDependencies.appcompat)
    implementation(MaterialDesignDependencies.materialDesign)
    implementation(AndroidxDependencies.constraintLayout)
    implementation(AndroidxDependencies.fragmentKtx)
    implementation(AndroidxDependencies.navigationFragmentKtx)
    implementation(AndroidxDependencies.navigationUIKtx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.androidxJUnit)
    androidTestImplementation(TestDependencies.espresso)


    //for Hilt
    implementation(HiltDependencies.hiltAndroid)
    kapt(HiltDependencies.hiltAndroidCompiler)

    // for compose
    implementation(AndroidxDependencies.Compose.activity)
    implementation(platform(AndroidxDependencies.Compose.bom))
    implementation(AndroidxDependencies.Compose.ui)
    implementation(AndroidxDependencies.Compose.graphics)
    implementation(AndroidxDependencies.Compose.material)
    implementation(AndroidxDependencies.Compose.toolingPreview)
    implementation(AndroidxDependencies.Compose.navigation)
    implementation(AndroidxDependencies.Compose.viewModel)
    implementation(AndroidxDependencies.Compose.hiltNavigation)

    implementation(project(Modules.common))
}