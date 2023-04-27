plugins {
    id("wp.android.library")
    id("kotlinx-serialization")
    id("wp.android.hilt")
}
android {
    namespace = "com.comsuon.wp.core.data"
//    testOptions {
//        unitTests {
//            isIncludeAndroidResources = true
//            isReturnDefaultValues = true
//        }
//    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}