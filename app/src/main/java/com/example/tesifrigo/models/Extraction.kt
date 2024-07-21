package com.example.tesifrigo.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

class Extraction: RealmObject {
    @PrimaryKey var id: ObjectId = ObjectId()
    var templateTitle: String = ""
    var templateDescription: String = ""
    var extractedFields: RealmList<ExtractionField> = realmListOf()
    var extractedTables: RealmList<ExtractionTable> = realmListOf()
    var image: String = ""
    var template: Template? = null
    var format: String = ""
    var tags: RealmList<String> = realmListOf()
    var extractionCosts: String = "" // Store as JSON string
    var exceptionsOccurred: RealmList<ExceptionOccurred> = realmListOf()

    /*constructor(title: String, shortDescription: String, longDescription: String, image: String, type: String) {
        this.title = title
        this.shortDescription = shortDescription
        this.longDescription = longDescription
        this.image = image
        this.type = type
    ***REMOVED****/
***REMOVED***
class ExtractionTable : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var templateTable: TemplateTable? = null
    var keywords: RealmList<String> = realmListOf()
    var dataframe: String = "" // Store dataframe as JSON or another format
    var modelUsed: String = ""
    var extractionCost: String = "" // Store cost as JSON or another format
    var title: String = ""
    var fields: RealmList<ExtractionField> = realmListOf()
***REMOVED***


class ExceptionOccurred : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var error: String = ""
    var errorType: String = ""
    var errorDescription: String = ""
***REMOVED***

class ExtractionField : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var templateField: TemplateField? = null
    var title: String= ""
    var description: String = ""
    var extraDescription: String = ""
    var value: String = ""
    var type: String = ""
    var tags: RealmList<String> = realmListOf()
    var modelUsed: String = ""
    //var extraction: Extraction? = null // Required: Every field belongs to an extraction


***REMOVED***


@Serializable
data class Extracted (val title: String, val description: String, val tags: List<String>){
    constructor(): this("", "", listOf())
    ***REMOVED***



data class Options(val model: String, val language: String, val azure_ocr: Boolean){
    constructor(): this("", "", false)
***REMOVED***

