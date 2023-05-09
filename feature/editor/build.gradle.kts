plugins {
    id("wp.android.feature")
    id("wp.android.library.compose")
}

android {
    namespace = "com.comsuon.wp.feature.editor"
}

dependencies {
    implementation(libs.androidx.constraint.compose)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}