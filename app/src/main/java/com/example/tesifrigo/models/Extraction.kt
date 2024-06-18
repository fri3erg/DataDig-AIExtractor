package com.example.tesifrigo.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

class Extraction: RealmObject {
    @PrimaryKey var id: ObjectId = ObjectId()
    var title: String = ""
    var description: String = ""
    var image: String = ""
    var template: Template? = null
    var format: String = ""
    var fields: RealmList<ExtractionField> = realmListOf()
    var tags: RealmList<String> = realmListOf()


    /*constructor(title: String, shortDescription: String, longDescription: String, image: String, type: String) {
        this.title = title
        this.shortDescription = shortDescription
        this.longDescription = longDescription
        this.image = image
        this.type = type
    ***REMOVED****/
***REMOVED***
class ExtractionField : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var title: String= ""
    var description: String = ""
    var extraDescription: String = ""
    var extracted: String = ""
    var type: String = ""
    var tags: RealmList<String> = realmListOf()

***REMOVED***

@Serializable
data class Extracted (val title: String, val description: String, val tags: List<String>){
    constructor(): this("", "", listOf())



    ***REMOVED***
