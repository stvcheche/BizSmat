pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        maven {
            setUrl("https://jitpack.io")
        }

        maven {
            setUrl("https://dl.bintray.com/google/flexbox-layout/")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven {
            setUrl("https://jitpack.io")
        }

        maven {
            setUrl("https://dl.bintray.com/google/flexbox-layout/")
        }
    }
}

rootProject.name = "BizSmat"
include(":app")
