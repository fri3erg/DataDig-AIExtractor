package com.example.tesifrigo.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Extraction: RealmObject {
    @PrimaryKey var id: ObjectId = ObjectId()
    var title: String = ""
    var shortDescription: String = ""
    var longDescription: String = ""
    var image: String = ""
    var type: String = ""
    var template: Template? = null


    /*constructor(title: String, shortDescription: String, longDescription: String, image: String, type: String) {
        this.title = title
        this.shortDescription = shortDescription
        this.longDescription = longDescription
        this.image = image
        this.type = type
    ***REMOVED****/
***REMOVED***

