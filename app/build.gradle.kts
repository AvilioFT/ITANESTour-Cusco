plugins {
    alias(libs.plugins.android.application)
}

android {
    // namespace = cómo Android genera tu clase R. Debe ser tu paquete real.
    // Lo cambiamos de com.example (plantilla) a pe.senati (tu instituto + empresa ITANES).
    namespace = "pe.senati.itanes.tour"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        // applicationId = ID único en Play Store / dispositivo. Debe coincidir con namespace.
        applicationId = "pe.senati.itanes.tour"
        // minSdk 26 = Android 8.0+. Es REQUISITO de tu trabajo (pág. 1 del MD).
        // Con 24 te observarían en la sustentación.
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    // Lista de los 5 lugares (RecyclerView). ¿Para qué? Muestra el recorrido, base del caso ITANES.
    implementation(libs.androidx.recyclerview)
    // MVVM: ViewModel + LiveData. ¿Para qué? Sobrevive a rotación y separa UI de datos (pregunta guía 1).
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    // Retrofit + Gson. ¿Para qué? Consume tu PHP y convierte JSON a objetos Kotlin (pregunta guía + evidencia API).
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)
    // Glide. ¿Para qué? Carga fotos por URL sin que el APK pese (pregunta guía 2: caché + resize).
    implementation(libs.glide)
    // Coroutines. ¿Para qué? Llamadas de red/BD fuera del hilo principal (sin ANR).
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}