plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.sonarqube)
    id("jacoco")
}

android {
    namespace = "com.bryantcoding.zooappcompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bryantcoding.zooappcompose"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableUnitTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.md"
        }
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

sonar {
    properties {
        property("sonar.projectKey", "ZooAppCompose")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.login", "sqp_6d4f3ecca472e7546b665d9aaa9e5c7205fb9160")

        // 指定程式碼所在位置
        property("sonar.sources", "src/main/java")

        // 指定要排除的檔案
        property("sonar.coverage.exclusions", "**/generated/**, **/*_Factory.kt, **/*_Impl.kt")

        // 告知 SonarQube 使用 Jacoco 分析 Kotlin
        property("sonar.kotlin.coveragePlugin", "jacoco")

        // Jacoco 的 XML 報告路徑
        property("sonar.coverage.jacoco.xmlReportPaths", "app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

tasks.register<JacocoReport>("jacocoTestReport") {
    description ="Jacocoreport"
    group = "Jacocoreport"
    dependsOn("testDebugUnitTest") // 先跑單元測試

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))
    classDirectories.setFrom(
        fileTree("build/tmp/kotlin-classes/debug") {
            exclude("**/generated/**", "**/*_Factory.kt", "**/*_Impl.kt")
        }
    )
    executionData.setFrom(fileTree("build") {
        include(
            "app/build/**/jacoco/testDebugUnitTest.exec",
            "app/build/jacoco/testDebugUnitTest.exec"
        )
    })
}

project.extensions.configure<JacocoPluginExtension> {
    toolVersion = "0.8.11"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
    implementation(libs.androidx.benchmark.common)
    implementation(libs.core)
    implementation(libs.androidx.ui.test.junit4)
    implementation(libs.androidx.ui.test.junit4.android)
    implementation(libs.androidx.ui.test.manifest)
    implementation(libs.mockk)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.coil.compose)
    implementation(libs.navigation.compose)
    implementation(libs.navigation.test)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.core.testing)
    implementation(libs.turbine)

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

}