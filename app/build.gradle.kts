import com.google.samples.apps.wp.WpBuildType

plugins {
    id("wp.android.application")
    id("wp.android.application.compose")
    id("wp.android.application.flavors")
    id("wp.android.hilt")
}

android {
    defaultConfig {
        applicationId = "com.comsuon.workoutplanner"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = WpBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = WpBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:model"))

    //navigation
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["nav_version"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["nav_version"]}")

    //compose
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation ("androidx.compose.material:material-icons-extended:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling:1.0.0-beta09")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha06")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation ("androidx.compose.runtime:runtime-livedata:${rootProject.extra["compose_version"]}")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02")

    //hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hilt_version"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hilt_version"]}")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-alpha03")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")

    //Test
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
}