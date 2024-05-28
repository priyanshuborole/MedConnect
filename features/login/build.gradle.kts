plugins {
    id(Plugins.library)
    id(Plugins.jetbrainsAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.hiltAndroid)
    id(Plugins.kotlinParcelize)
//    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.priyanshudev.login"
    compileSdk = ConfigurationData.compileSdk

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures{
        viewBinding = true
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

//    //firebase
//    implementation(platform(FirebaseDependencies.firebaseBom))
//    implementation(FirebaseDependencies.firebaseAuth)
//    implementation(FirebaseDependencies.firestore)
    //for Hilt
    implementation(HiltDependencies.hiltAndroid)
    kapt(HiltDependencies.hiltAndroidCompiler)

    implementation(project(Modules.common))
    implementation(project(Modules.doctor))
    implementation(project(Modules.patient))
    implementation(libs.play.services.auth)
}