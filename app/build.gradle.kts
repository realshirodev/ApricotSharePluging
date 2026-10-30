plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.apricotsharepluging"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        setProperty("archivesBaseName", "ApricotSharePluging")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    compileOnly("org.godotengine:godot:4.7.2.stable")
    implementation("androidx.core:core:1.17.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

tasks.register<Copy>("copyPluginAars") {
    from(layout.buildDirectory.dir("outputs/aar"))
    include("ApricotSharePluging-debug.aar", "ApricotSharePluging-release.aar")
    into(layout.projectDirectory.dir("../godot-addon/addons/apricot_share/bin"))
}

tasks.register<Copy>("copyPluginAarsToGame") {
    from(layout.buildDirectory.dir("outputs/aar"))
    include("ApricotSharePluging-debug.aar", "ApricotSharePluging-release.aar")
    into(layout.projectDirectory.dir("../../../../addons/apricot_share/bin"))
}

tasks.named("assemble").configure {
    finalizedBy("copyPluginAars", "copyPluginAarsToGame")
}