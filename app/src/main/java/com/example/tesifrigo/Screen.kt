package com.example.tesifrigo

/**
 * Screens, for type safety and easy navigation
 *
 * @property route The route of the screen
 * @constructor Create empty Screen
 */
sealed class Screen(val route: String) {
    data object Camera : Screen("camera")
    data object Storage : Screen("storage")
    data object Settings : Screen("settings")
    data object Templates : Screen("templates")
    data object EditTemplate : Screen("editTemplate")
    data object SingleExtraction : Screen("singleExtraction")

    fun withArgs(vararg args: Pair<String, String>): String { //for routes with required arguments
        return buildString {
            append(route)
            args.forEach { (argName, argValue) ->
                append("/$argName=$argValue")
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun routeWithOptionalArgs(vararg args: Pair<String, String?>): String { //for routes with optional arguments
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