plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.hiltkspviewbindingtestapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hiltkspviewbindingtestapp"
        minSdk = 26
        targetSdk = 34
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

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")
}

// Apply workaround for KSP classes not being wired correctly
androidComponents {
  onVariants(
    selector().all(),
    { variant ->
      afterEvaluate {
        // This is a workaround for https://issuetracker.google.com/301244513 which depends on
        // internal
        // implementations of the android gradle plugin and the ksp gradle plugin which might change
        // in the future
        // in an unpredictable way. This workaround should be removed once the issue is fixed.
        val buildConfigTask =
          project.tasks.findByName("generate" + variant.name.capitalize() + "BuildConfig") as?
            com.android.build.gradle.tasks.GenerateBuildConfig
        val dataBindingTask =
          project.tasks.findByName("dataBindingGenBaseClasses" + variant.name.capitalize()) as?
            com.android.build.gradle.internal.tasks.databinding.DataBindingGenBaseClassesTask
        if (buildConfigTask != null) {
          project.tasks.getByName("ksp" + variant.name.capitalize() + "Kotlin") {
            (this as org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool<*>).setSource(
              buildConfigTask.sourceOutputDir
            )
          }
        }
        if (dataBindingTask != null) {
          project.tasks.getByName("ksp" + variant.name.capitalize() + "Kotlin") {
            (this as org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool<*>).setSource(
              dataBindingTask.sourceOutFolder
            )
          }
        }
      }
    }
  )
}
