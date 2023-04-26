plugins {
    id("wp.android.library")
    id("kotlinx-serialization")
    id("wp.android.hilt")
    id("wp.android.room")
}
android {
    namespace = "com.wp.core.data"
//    testOptions {
//        unitTests {
//            isIncludeAndroidResources = true
//            isReturnDefaultValues = true
//        }
//    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}