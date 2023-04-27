plugins {
    id("wp.android.library")
    id("wp.android.library.compose")
}

android {
    namespace = "com.comsuon.wp.core.ui"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.runtime.livedata)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)

    debugApi(libs.androidx.compose.ui.tooling)

    implementation(project(":core:model"))
    implementation(libs.androidx.browser)
    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.kotlinx.datetime)
}
