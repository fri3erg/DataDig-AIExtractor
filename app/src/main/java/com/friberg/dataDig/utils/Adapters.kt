package com.friberg.dataDig.utils

import com.friberg.dataDig.models.TemplateField
import com.friberg.dataDig.models.TemplateTable
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import java.lang.reflect.Type


/**
 * Template field list adapter
 *
 * @constructor Create empty Template field list adapter
 */
class TemplateFieldListAdapter : JsonSerializer<RealmList<TemplateField>>,
    JsonDeserializer<RealmList<TemplateField>> {

    override fun serialize(
        src: RealmList<TemplateField>,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return context.serialize(src.toList())
    ***REMOVED***

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RealmList<TemplateField> {
        val list = context.deserialize<List<TemplateField>>(json, object : TypeToken<List<TemplateField>>() {***REMOVED***.type)
        val realmList = realmListOf<TemplateField>()
        realmList.addAll(list)
        return realmList
    ***REMOVED***
***REMOVED***


/**
 * Template table list adapter
 *
 * @constructor Create empty Template table list adapter
 */
class TemplateTableListAdapter : JsonSerializer<RealmList<TemplateTable>>,
    JsonDeserializer<RealmList<TemplateTable>> {

    override fun serialize(
        src: RealmList<TemplateTable>,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return context.serialize(src.toList())
    ***REMOVED***

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RealmList<TemplateTable> {
        val list = context.deserialize<List<TemplateTable>>(json, object : TypeToken<List<TemplateTable>>() {***REMOVED***.type)
        val realmList = realmListOf<TemplateTable>()
        realmList.addAll(list)
        return realmList
    ***REMOVED***
***REMOVED***

/**
 * Realm list string adapter
 *
 * @constructor Create empty Realm list string adapter
 */
class RealmListStringAdapter : JsonSerializer<RealmList<String>>,
    JsonDeserializer<RealmList<String>> {

    override fun serialize(
        src: RealmList<String>,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val jsonArray = JsonArray()
        for (item in src) {
            jsonArray.add(item)
        ***REMOVED***
        return jsonArray
    ***REMOVED***

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RealmList<String> {
        val jsonArray = json.asJsonArray
        val realmList = realmListOf<String>()
        for (item in jsonArray) {
            if (item.isJsonPrimitive && item.asJsonPrimitive.isString) {
                realmList.add(item.asString)
            ***REMOVED*** else {
                // Handle other JSON element types if needed
                println("Unexpected JSON element type: ${item.javaClass***REMOVED***")
            ***REMOVED***
        ***REMOVED***
        return realmList
    ***REMOVED***
***REMOVED***




