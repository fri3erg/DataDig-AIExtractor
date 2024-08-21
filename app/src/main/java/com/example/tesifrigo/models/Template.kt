package com.example.tesifrigo.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class Template : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var title: String = ""
    var description: String = ""
    var fields: RealmList<TemplateField> = realmListOf()
    var tables: RealmList<TemplateTable> = realmListOf()  // Add this line
    var tags: RealmList<String> = realmListOf()


***REMOVED***

class TemplateTable : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var title: String = ""
    var keywords: RealmList<String> = realmListOf()
    var description: String = ""
    var rows: RealmList<TemplateField> = realmListOf()
    var columns: RealmList<TemplateField> = realmListOf()
    var all : Boolean = false
***REMOVED***

class TemplateField : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var title: String = ""
    var description: String = ""
    var extraDescription: String = ""
    var type: String = ""
    var required: Boolean = false
    var intelligentExtraction: Boolean = false
    var default : String = ""
    var list : Boolean = false

***REMOVED***