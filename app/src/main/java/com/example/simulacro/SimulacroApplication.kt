package com.example.simulacro

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Punto de entrada de la app a nivel proceso (vive más que cualquier Activity).
 *
 * `@HiltAndroidApp` arranca el grafo de inyección de dependencias de Hilt.
 * Tiene que estar declarada en el AndroidManifest como `android:name`.
 */
@HiltAndroidApp
class SimulacroApplication : Application()
