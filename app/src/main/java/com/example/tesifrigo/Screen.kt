package com.example.tesifrigo

sealed class Screen(val route:String) {
    data object Camera: Screen("camera")
    data object Storage: Screen("storage")
    data object Settings: Screen("settings")
    data object Templates: Screen("templates")
    data object EditTemplate: Screen("editTemplate")
     data object SingleExtraction: Screen("singleExtraction")

    fun withArgs(vararg args: Pair<String, String>): String {
        return buildString {
            append(route)
            args.forEach { (argName, argValue) ->
                    append("/$argName=$argValue")
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    fun routeWithOptionalArgs(vararg args: Pair<String, String?>): String {
        return buildString {
            append(route)
            args.forEach { (argName, argValue) ->
                if (argValue != null) {
                    append("?$argName=$argValue")
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***



***REMOVED***