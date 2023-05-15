plugins {
    id("wp.android.library")
    id("wp.android.hilt")
}

android {
    namespace = "com.comsuon.wp.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
}