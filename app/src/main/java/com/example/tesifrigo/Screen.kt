package com.example.tesifrigo

sealed class Screen(val route:String) {
    object Camera: Screen("camera")
    object Storage: Screen("storage")
    object Settings: Screen("settings")
    object Templates: Screen("templates")
    object EditTemplate: Screen("editTemplate")
    object SingleExtraction: Screen("singleExtraction")

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