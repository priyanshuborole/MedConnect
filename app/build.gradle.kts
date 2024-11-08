plugins {
    id(Plugins.application)
    id(Plugins.jetbrainsAndroid)
    id(Plugins.googleService)
    id(Plugins.kotlinKapt)
    id(Plugins.hiltAndroid)
//    id(Plugins.kotlinKapt)
//    id(Plugins.navigationSafeArgs)
//    id(Plugins.kotlinParcelize)
//    id(Plugins.hiltAndroid)
}

android {
    namespace = "com.priyanshudev.medconnect"
    compileSdk = ConfigurationData.compileSdk

    defaultConfig {
        applicationId = "com.priyanshudev.medconnect"
        minSdk = 26
        targetSdk = ConfigurationData.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.androidxJUnit)
    androidTestImplementation(TestDependencies.espresso)


    //for Hilt
    implementation(HiltDependencies.hiltAndroid)
    kapt(HiltDependencies.hiltAndroidCompiler)

    // compose

    implementation(project(Modules.login))
    implementation(project(Modules.common))
    implementation(project(Modules.doctor))
    implementation(project(Modules.patient))


}