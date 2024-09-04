package com.example.tesifrigo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId


class Extraction : RealmObject {
    @PrimaryKey
    @Expose(serialize = false, deserialize = false)
    var id: ObjectId = ObjectId()

    @Expose
    var title: String = ""

    @Expose
    var extractedFields: RealmList<ExtractionField> = realmListOf()

    @Expose
    var extractedTables: RealmList<ExtractionTable> = realmListOf()

    @Expose
    var extractionCosts: RealmList<ExtractionCosts> = realmListOf()

    @Expose
    var exceptionsOccurred: RealmList<ExceptionOccurred> = realmListOf()

    @Expose(serialize = false, deserialize = false)
    var image: RealmList<String> = realmListOf()

    @Expose(serialize = false, deserialize = false)
    var template: Template? = null

    @Expose(serialize = false, deserialize = false)
    var format: String = ""

    @Expose(serialize = false, deserialize = false)
    var tags: RealmList<String> = realmListOf()

    @Expose(serialize = false, deserialize = false)
    var fileUri: String? = null

    @Expose
    var language: String? = null

    @Expose
    var model: String? = null

    @Expose(serialize = false, deserialize = false)
    var extraImages: RealmList<String> = realmListOf()

    @Expose(serialize = false, deserialize = false)
    var review: Boolean = false // Whether the extraction has been reviewed
***REMOVED***

class ExtractionTable : RealmObject {
    @PrimaryKey
    @Expose(serialize = false, deserialize = false)
    var id: ObjectId = ObjectId()

    @Expose
    var title: String? = null

    @Expose
    @SerializedName("template_table_title")  // Rename in JSON
    var templateTable: TemplateTable? = null

    @Expose
    var dataframe: String =
        "" // not seen by the user, but useful in debugging what the table looks like in the backend

    @Expose
    var fields: RealmList<ExtractionTableRow> = realmListOf()
***REMOVED***

class ExtractionTableRow : RealmObject {
    @PrimaryKey
    @Expose(serialize = false, deserialize = false)
    var id: ObjectId = ObjectId()

    @Expose
    var rowName: String = ""

    @Expose
    var fields: RealmList<ExtractionField> = realmListOf()
***REMOVED***

class ExceptionOccurred : RealmObject {
    @PrimaryKey
    @Expose(serialize = false, deserialize = false)
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
    @Expose(serialize = false, deserialize = false)
    var id: ObjectId = ObjectId()

    @Expose
    @SerializedName("template_field_title")  // Rename in JSON
    var templateField: TemplateField? = null

    @Expose
    var value: String = "" // The extracted value
***REMOVED***


/**
 * Options
 *
 * @property model the ai model to use for extraction
 * @property language the language to use for extraction
 * @property azureOcr whether to use azure ocr
 * @property getApiKey a function to get the api key from backend
 * @property format the format of the extraction
 * @property resize whether to resize the image
 * @constructor Create empty Options
 */
data class Options(
    var model: String,
    var language: String,
    var azureOcr: Boolean,
    var getApiKey: (String) -> String?,
    var format: String,
    var resize: Boolean

) {
    constructor() : this("", "", false, { "" ***REMOVED***, "", true)
***REMOVED***


class ExtractionCosts : RealmObject {
    @PrimaryKey
    @Expose(serialize = false, deserialize = false)
    var id: ObjectId = ObjectId()

    @Expose
    var name: String = ""

    @Expose
    var cost: Float = 0f

    @Expose
    var currency: String = ""

    @Expose
    var tokens: Int = 0
***REMOVED***

@Serializable
data class Review(
    var rating: Int, var comment: String
)
