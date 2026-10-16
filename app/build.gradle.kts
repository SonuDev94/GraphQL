plugins {
    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.apollo)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.google.ksp)           // Apply KSP
    alias(libs.plugins.dagger)
    id("kotlin-parcelize")
//    kotlin("kapt")
}

android {
    namespace = "com.idemia.wa.graphql"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.idemia.wa.graphql"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

apollo{
    service("service"){
        packageName.set("com.idemia.wa.graphql")
        introspection{
            endpointUrl = "https://countries.trevorblades.com/"
            schemaFile.set(file("src/main/graphql/com/idemia/wa/graphql/schema.graphqls"))
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    // apollo
    implementation(libs.apollo.runtime)

    // dagger
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.compiler)
    // hilt-compose-navigation
     implementation(libs.hilt.compose.navigation)

    // navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization)
}