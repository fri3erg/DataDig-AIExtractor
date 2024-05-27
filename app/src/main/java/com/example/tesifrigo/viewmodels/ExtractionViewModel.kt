package com.example.tesifrigo.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tesifrigo.MyApp
import com.example.tesifrigo.model.Extraction
import com.example.tesifrigo.model.Field
import com.example.tesifrigo.model.Template
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class ExtractionViewModel: ViewModel(){
    private  val realm =MyApp.realm

    val extractions = realm
        .query<Extraction>()
        .asFlow()
        .map {
            it.list.toList()
        ***REMOVED***
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList())

    init {
        createSampleExtraction()
    ***REMOVED***

    private fun createSampleExtraction() {
        viewModelScope.launch {
            realm.write {

                val extractionToDelete = query<Extraction>().find()
                delete(extractionToDelete)


                var template1= Template().apply {
                    title = "Sample Template"
                    description = "This is a sample template"
                ***REMOVED***


                val extraction1 = Extraction().apply {
                    title = "Sample Extraction"
                    shortDescription = "This is a sample extraction"
                    longDescription = "This is a sample extraction with a long description"
                    image = "https://www.example.com/image.jpg"
                    type= "cvs"
                    template = template1
                ***REMOVED***
                val extraction2 = Extraction().apply {
                    title = "Sample Extraction 2"
                    shortDescription = "This is a sample extraction 2"
                    longDescription = "This is a sample extraction 2 with a long description"
                    image = "https://www.example.com/image2.jpg"
                    type= "cvs"
                    template = template1
                ***REMOVED***
                val extraction3 = Extraction().apply {
                    title = "Sample Extraction 3"
                    shortDescription = "This is a sample extraction 3"
                    longDescription = "This is a sample extraction 3 with a long description"
                    image = "https://www.example.com/image3.jpg"
                    type= "json"
                    template = template1
                ***REMOVED***

                copyToRealm(extraction1)
                copyToRealm(extraction2)
                copyToRealm(extraction3)

            ***REMOVED***
        ***REMOVED***

    ***REMOVED***
    fun updateExtraction(extraction: Extraction){
        viewModelScope.launch {
            realm.write {
                copyToRealm(extraction, updatePolicy = UpdatePolicy.ALL)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    fun deleteExtraction(id:String){
        viewModelScope.launch {
            realm.write {
                    val extractionToDelete = query<Extraction>("id == $0", ObjectId(id)).find().first()
                    // Delete the parent object (deletes all embedded objects)
                    delete(extractionToDelete)
                ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***