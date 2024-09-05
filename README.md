# DATADIG :it:

## Table of Contents

- [Introduction](#introduction)
- [Tech Stack](#tech-stack)
- [Key Features](#key-features)
- [Working With DataDig](#working-with-datadig)
- [Extraction Object Model](#extraction-object-model)
- [Contact](#contact)
- [DEVS](#devs)

## Introduction

kotlin application that allows for customizable extraction from documents such as images(jpg, jpeg, png), and pdfs using AI (openAI API and Azure document intelligence API).
Keys get added by the user and the templates used to extract are fully customizable by the user.
App also comes with amples useful templates and provides files of the extractions in various formats( txt, csv, xml, json)

## Key Features

- **Template Management:**  Create, edit, and delete templates to define the structure and rules for extracting specific data points from documents.
- **Camera Integration:** Capture images directly within the app or select existing images from the device gallery for extraction.
- **Background Extraction:**  A foreground service performs the extraction process in the background, allowing users to continue using the app.
- **Extraction History:** View and manage the results of previous extractions.
- **Secure API Key Storage:** Sensitive API keys are stored securely using encryption.
- **Multilingual Support:** The app is localized to support 5 languages.

## Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture:** Model-View-ViewModel (MVVM)
- **Navigation:**  NavHost and NavController
- **State Management:**  ViewModel with StateFlow
- **Dependency Injection:** Hilt
- **Local Database:** Realm
- **Background Processing:** Foreground Service
- **Service-UI Communication:**  Repository pattern
- **Security:** EncryptedSharedPreferences and MasterKey for API key storage
- **Localization:** Support for 5 languages
- **OCR:** TextRecognition library

## Working With DataDig

If you are using DataDig for your company you can handle the flow of data from the extraction in 2 ways:

- **Implicit Intent** (recommended)

    > Make yourself an android app that can receive the implicit intent: Intent(Intent.ACTION_VIEW) that contains the Uri to the extraction file

    1. Register your app to handle the `ACTION_VIEW` intent in your `AndroidManifest.xml`.
    2. When the user clicks on the extracted file (json or xml recommended) within DataDig, your app will appear as an option to open it.
    3. Retrieve the file path from the provided `Uri`.
    4. Parse the file contents.
    5. Update your app's database or perform further processing on the extracted information.

    *This approach offers a seamless way to directly integrate DataDig's output into your app's workflow.*

- **Sharing**

   1. DataDig provides the option to share extracted data in JSON or XML format.
   2. Use this shared data to import it into other systems, perform additional analysis, or any other desired actions.

   *Please ensure secure handling of the shared data if it contains sensitive information.*  

## Extraction Object Model

This section outlines the classes used to represent the results of a data extraction process in DataDig.
(useful for parsing)

### `Extraction`

The primary class holding the extracted data.

- **`title`** (String):  Title or name associated with the extraction.
- **`extractedFields`** (List<[`ExtractionField`](#extractionfield)>):  List of extracted fields and their values.
- **`extractedTables`** (List<[`ExtractionTable`](#extractiontable)>):  List of extracted tables.
- **`extractionCosts`** (List<[`ExtractionCosts`](#extractioncosts)>): Cost breakdown of the extraction (model usage, etc.).
- **`exceptionsOccurred`** (List<[`ExceptionOccurred`](#exceptionoccurred)>): Any exceptions that occurred during extraction.
- **`language`** (String, optional): Language detected in the document (if applicable).
- **`model`** (String, optional): Model used for the extraction.

### `ExtractionTable`

Represents a single extracted table.

- **`title`** (String, optional): Title of the extracted table.
- **`template_table_title`** (String): The title of the template table used for extraction.
- **`dataframe`** (String): Internal representation of the table data (not exposed to the user, this is useful to undestand what the LLM saw when extracting).
- **`fields`** (List<[`ExtractionTableRow`](#extractiontablerow)>): Rows within the extracted table.

### `ExtractionTableRow`

Represents a row within an extracted table.

- **`rowName`** (String): Name or identifier for the row.
- **`fields`** (List<[`ExtractionField`](#extractionfield)>): Fields and their values within this row.

### `ExceptionOccurred`

Details about any exceptions encountered during extraction.

- **`error`** (String): Error message.
- **`errorType`** (String): Type or category of the error.
- **`errorDescription`** (String): Additional description of the error.

### `ExtractionField`

Represents a single extracted field.

- **`template_field_title`** (String):  Title of the template field used for extraction
- **`value`** (String): Extracted value for this field.

### `ExtractionCosts`

Breakdown of the costs associated with the extraction process.

- **`name`** (String):  Name of the cost component (e.g., "Model Usage").
- **`cost`** (Float):  Cost in the specified currency.
- **`currency`** (String): Currency code (e.g., "USD").
- **`tokens`** (Int): Number of tokens used (if applicable to the cost model).

## Contact

Feel free to reach out to me via:

- **Email:** [elia.fri3erg@gmail.com](mailto:elia.fri3erg@gmail.com)
- **LinkedIn:** [elia.friberg](https://www.linkedin.com/in/elia-friberg-021a90295/)
- **Whatsapp:** [@elia.friberg](+393924123304)

## DEVS

Elia Friberg
