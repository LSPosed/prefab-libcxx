plugins {
    alias(libs.plugins.agp.lib)
    alias(libs.plugins.lsplugin.jgit)
    alias(libs.plugins.lsplugin.cmaker)
    `maven-publish`
    signing
}

val androidTargetSdkVersion: Int by rootProject.extra
val androidMinSdkVersion: Int by rootProject.extra
val androidBuildToolsVersion: String by rootProject.extra
val androidCompileSdkVersion: Int by rootProject.extra
val androidNdkVersion: String by rootProject.extra
val androidCmakeVersion: String by rootProject.extra

android {
    compileSdk = androidCompileSdkVersion
    ndkVersion = androidNdkVersion
    buildToolsVersion = androidBuildToolsVersion

    buildFeatures {
        buildConfig = false
        prefabPublishing = true
        androidResources = false
        prefab = true
    }

    packaging {
        jniLibs {
            excludes += "**.so"
        }
    }

    prefab {
        register("cxx") {
            headers = "jni/libcxx/include"
        }
    }

    defaultConfig {
        minSdk = androidMinSdkVersion
    }

    lint {
        abortOnError = true
        checkReleaseBuilds = false
    }

    externalNativeBuild {
        cmake {
            path = file("jni/CMakeLists.txt")
            version = androidCmakeVersion
        }
    }
    namespace = "org.lsposed.libcxx"
}

cmaker {
    default {
        abiFilters("armeabi-v7a", "arm64-v8a", "x86", "x86_64", "riscv64")
        arguments += "-DANDROID_STL=none"
        arguments += arrayOf(
            "-DANDROID_STL=none",
            "-DANDROID_SUPPORT_FLEXIBLE_PAGE_SIZES=ON",
            "-DANDROID_ALLOW_UNDEFINED_SYMBOLS=ON",
            "-DCMAKE_CXX_STANDARD=23",
            "-DCMAKE_C_STANDARD=23",
            "-DCMAKE_INTERPROCEDURAL_OPTIMIZATION=ON",
            "-DCMAKE_VISIBILITY_INLINES_HIDDEN=ON",
            "-DCMAKE_CXX_VISIBILITY_PRESET=hidden",
            "-DCMAKE_C_VISIBILITY_PRESET=hidden",
        )
    }
    buildTypes {
        
    }
}

group = "org.lsposed.libcxx"
version = androidNdkVersion

publish {
    githubRepo = "LSPosed/prefab-libcxx"
    publications("libcxx") {
	name.set("libcxx")
	description.set("libcxx")
	url.set("https://github.com/LSPosed/prefab-libcxx")
	licenses {
	    license {
		name.set("Apache v2.0")
		url.set("https://github.com/llvm/llvm-project/blob/main/LICENSE.TXT")
	    }
	}
	developers {
	    developer {
		name.set("LLVM")
		url.set("https://llvm.org/")
	    }
	}
	scm {
	    connection.set("scm:git:https://github.com/LSPosed/prefab-libcxx.git")
	    url.set("https://github.com/LSPosed/prefab-libcxx")
	}
    }
}
