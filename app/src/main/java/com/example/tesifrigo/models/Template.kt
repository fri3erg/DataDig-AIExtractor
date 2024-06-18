package com.example.tesifrigo.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId



class Template: RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var title: String = ""
    var description: String = ""
    var fields: RealmList<TemplateField> = realmListOf()
    var tags: RealmList<String> = realmListOf()

***REMOVED***
class TemplateField : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var title: String= ""
    var description: String = ""
    var extraDescription: String = ""
    var tags: RealmList<String> = realmListOf()

***REMOVED***