package com.example.tesifrigo.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class Field : RealmObject {
    var id: ObjectId = ObjectId()
    var title: String= ""
    var description: String = ""
    var extraDescription: String = ""
    var tags: RealmList<String> = realmListOf()

***REMOVED***

class Template: RealmObject {
    var id: ObjectId = ObjectId()
    var title: String = ""
    var description: String = ""
    var fields: RealmList<Field> = realmListOf()
    var tags: RealmList<String> = realmListOf()

***REMOVED***
