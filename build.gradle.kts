plugins {
    alias(libs.plugins.agp.lib).apply(false)
    alias(libs.plugins.lsplugin.publish)
}

val androidTargetSdkVersion by extra(36)
val androidMinSdkVersion by extra(21)
val androidBuildToolsVersion by extra("36.0.0")
val androidCompileSdkVersion by extra(36)
val androidNdkVersion by extra("29.0.13599879-beta2")
val androidCmakeVersion by extra("3.28.0+")
