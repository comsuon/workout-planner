plugins {
    id("wp.android.library")
}

android {
    namespace = "com.comsuon.wp.common"
}

dependencies {
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.viewModelCompose)
}