package com.example.tesifrigo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
class Extraction : RealmObject {
    @PrimaryKey
    @Expose(serialize = false, deserialize = false) // Exclude from serialization
    var id: ObjectId = ObjectId()

    @Expose
    var title: String = ""

    @Expose
    var extractedFields: RealmList<ExtractionField> = realmListOf()

    @Expose
    var extractedTables: RealmList<ExtractionTable> = realmListOf()

    @Expose
    var extractionCosts: String = ""

    @Expose
    var exceptionsOccurred: RealmList<ExceptionOccurred> = realmListOf()

    @Expose(serialize = false, deserialize = false) // Exclude from serialization
    var image: RealmList<String> = realmListOf()

    @Expose(serialize = false, deserialize = false)
    var template: Template? = null

    @Expose(serialize = false, deserialize = false) // Exclude from serialization
    var format: String = ""

    @Expose(serialize = false, deserialize = false) // Exclude from serialization
    var tags: RealmList<String> = realmListOf()

    @Expose(serialize = false, deserialize = false) // Exclude from serialization
    var fileUri: String? = null
***REMOVED***

class ExtractionTable : RealmObject {
    @PrimaryKey
    @Expose(serialize = false, deserialize = false) // Exclude from serialization
    var id: ObjectId = ObjectId()

    @Expose
    @SerializedName("template_table_title")  // Rename in JSON
    var templateTable: TemplateTable? = null

    @Expose
    var dataframe: String = ""

    @Expose
    var fields: RealmList<ExtractionTableRow> = realmListOf()
***REMOVED***

class ExtractionTableRow : RealmObject {
    @PrimaryKey
    @Expose(serialize = false, deserialize = false) // Exclude from serialization
    var id: ObjectId = ObjectId()

    @Expose
    var rowName: String = ""

    @Expose
    var fields: RealmList<ExtractionField> = realmListOf()
***REMOVED***

class ExceptionOccurred : RealmObject {
    @PrimaryKey
    @Expose(serialize = false, deserialize = false) // Exclude from serialization
    var id: ObjectId = ObjectId()

    @Expose
    var error: String = ""

    @Expose
    var errorType: String = ""

    @Expose
    var errorDescription: String = ""
***REMOVED***

class ExtractionField : RealmObject {
    @PrimaryKey
    @Expose(serialize = false, deserialize = false) // Exclude from serialization
    var id: ObjectId = ObjectId()

    @Expose
    @SerializedName("template_field_title")  // Rename in JSON
    var templateField: TemplateField? = null

    @Expose
    var value: String = ""
***REMOVED***





data class Options(
    var model: String,
    var language: String,
    var azureOcr: Boolean,
    var getApiKey: (String) -> String?,
    var format : String

) {
    constructor() : this("", "", false, {""***REMOVED***,"")
***REMOVED***
