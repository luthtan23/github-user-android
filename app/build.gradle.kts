plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {

    compileSdk = 34

    defaultConfig {
        namespace = "com.luthtan.github_user_android"
        applicationId = "com.luthtan.github_user_android"
        minSdk = 23
        versionCode =
            if (project.hasProperty("versionCode")) android.defaultConfig.versionCode else 20
        versionName =
            if (project.hasProperty("versionName")) android.defaultConfig.versionName else "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }

    //TODO hide all configuration
    flavorDimensions.add("env")
    productFlavors {
        create("development") {
            dimension = "env"
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "@string/app_name_dev")
            buildConfigField("String", "API_BASE_URL", "\"https://api.github.com/\"")
            buildConfigField("String", "VERSION_NAME", "\"1.0\"")
            buildConfigField("String", "VERSION_CODE", "\"20\"")
        }
        create("staging") {
            dimension = "env"
            applicationIdSuffix = ".staging"
            resValue("string", "app_name", "@string/app_name_staging")
            buildConfigField("String", "API_BASE_URL", "\"https://api.github.com/\"")
            buildConfigField("String", "VERSION_NAME", "\"1.0\"")
            buildConfigField("String", "VERSION_CODE", "\"20\"")
        }
        create("production") {
            dimension = "env"
            resValue("string", "app_name", "@string/app_name_prod")
            buildConfigField("String", "API_BASE_URL", "\"https://api.github.com/\"")
            buildConfigField("String", "VERSION_NAME", "\"1.0\"")
            buildConfigField("String", "VERSION_CODE", "\"20\"")
        }
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

// Utilize
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.22")

//retrofit & okhttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

//glide
    implementation("com.github.bumptech.glide:glide:4.13.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.0")

//third party
    implementation("com.github.dhaval2404:imagepicker:2.1") //picker
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0") //picker
    implementation("io.reactivex.rxjava3:rxjava:3.0.0") //picker

// Navigation
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.1")

// Dagger2
    implementation("com.google.dagger:hilt-android:2.46")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    annotationProcessor("com.google.dagger:hilt-android-compiler:2.46")

// Room
    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.room:room-ktx:2.4.2")
    implementation("androidx.room:room-rxjava3:2.4.2")
    implementation("androidx.room:room-common:2.4.2")
    annotationProcessor("androidx.room:room-compiler:2.4.2")
    annotationProcessor("androidx.room:room-compiler:2.4.2")

//logger
    implementation("com.jakewharton.timber:timber:5.0.0")
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    implementation("androidx.paging:paging-runtime:3.1.1")

    implementation("de.hdodenhof:circleimageview:3.1.0")

}
