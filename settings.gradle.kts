pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            ***REMOVED***
        ***REMOVED***
        mavenCentral()
        gradlePluginPortal()

    ***REMOVED***
***REMOVED***
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io")  ***REMOVED*** // Using uri()

    ***REMOVED***
***REMOVED***

rootProject.name = "TesiFrigo"
include(":app")
 