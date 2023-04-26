plugins {
    id("wp.android.library")
    id("wp.android.room")
    id("wp.android.hilt")
}

android {
    namespace = "com.comsuon.wp.database"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
}