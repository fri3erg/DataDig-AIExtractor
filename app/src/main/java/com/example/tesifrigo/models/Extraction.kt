package com.example.tesifrigo.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

class Extraction: RealmObject {
    @PrimaryKey var id: ObjectId = ObjectId()
    var extractedFields: RealmList<ExtractionField> = realmListOf()
    var extractedTables: RealmList<ExtractionTable> = realmListOf()
    var extractionCosts: String = "" // Store as JSON string
    var exceptionsOccurred: RealmList<ExceptionOccurred> = realmListOf()
    var image: String = ""
    var template: Template? = null
    var format: String = ""
    var tags: RealmList<String> = realmListOf()

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
    var dataframe: String = "" // Store dataframe as JSON or another format
    var fields: RealmList<ExtractionTableRow> = realmListOf()
***REMOVED***

class ExtractionTableRow: RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var rowIndex: String = "" // Or an integer if rows are numbered
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
    var value: String = ""
    //var extraction: Extraction? = null // Required: Every field belongs to an extraction
***REMOVED***


@Serializable
data class Extracted (val title: String, val description: String, val tags: List<String>){
    constructor(): this("", "", listOf())
    ***REMOVED***



data class Options(
    var model: String,
    var language: String,
    var azure_ocr: Boolean,
    var getApiKey: (String) -> String?

) {
    constructor() : this("", "", false, {""***REMOVED***)
***REMOVED***
