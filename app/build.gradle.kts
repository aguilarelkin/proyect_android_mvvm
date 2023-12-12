plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")//args seguros
}

android {
    namespace = "com.kto.employenexa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kto.employenexa"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "dataem", "Employe")
            buildConfigField("String", "BASE_URL", "\"https://newastro.vercel.app/\"")
        }
        getByName("debug") {
            isDebuggable = true
            resValue("string", "dataem", "[debug]Employe")
            buildConfigField("String", "BASE_URL", "\"https://newastro.vercel.app/\"")

        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    val navVersion = "2.7.5"
    val retroGson = "2.9.0"
    val okhtt = "4.3.1"
    val dagger = "2.48"
    //NavComponent
    implementation("androidx.navigation:navigation-fragment-ktx:${navVersion}")
    implementation("androidx.navigation:navigation-ui-ktx:${navVersion}")

    //DaggerHilt injection
    implementation("com.google.dagger:hilt-android:${dagger}")
    kapt("com.google.dagger:hilt-compiler:${dagger}")

    //Retrofit llamadas a apisrest
    implementation("com.squareup.retrofit2:retrofit:${retroGson}")
    implementation("com.squareup.retrofit2:converter-gson:${retroGson}")
    implementation("com.squareup.okhttp3:logging-interceptor:${okhtt}")

    //Picasso
    implementation("com.squareup.picasso:picasso:2.8")

    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //unitTesting
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("io.mockk:mockk:1.12.3")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-test
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")


    //UITesting
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    androidTestImplementation("androidx.fragment:fragment-testing:1.6.2")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.48")
}