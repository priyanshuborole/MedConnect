// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io")
            // r8 maven
            url = uri("https://storage.googleapis.com/r8-releases/raw")
        }
    }
    dependencies {

        classpath(Classpath.gradle)
        classpath(Classpath.kotlin)
        classpath(Classpath.navigationSafeArgs)
        classpath(Classpath.hiltAndroidGradle)
        // r8 version
        classpath(Classpath.toolsR8)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}