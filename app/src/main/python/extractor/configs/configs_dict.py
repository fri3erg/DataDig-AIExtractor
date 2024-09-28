
# from openai.types.chat import ChatCompletionMessageParam
from langchain.prompts import PromptTemplate
from ..scanner.extractors.extractor_utils import sanitize_text


prompts = {
    "it": """Sei un estrattore di informazioni da testi
    Il tuo compito è analizzare il testo fornito ed estrarre i campi specificati nel TEMPLATE dal DOCUMENTO fornito.
    **Linee Guida:**
    lo scopo è restituire i campi richiesti se si trovano nel documento
    1. **Valori Esatti:** Riporta i valori esatti come appaiono nel DOCUMENTO, senza modifiche o interpretazioni.
    2. **Campi Multipli:** Se un campo appare più volte nel DOCUMENTO, rispetta il formato del campo, se non è una lista, ritorna il più probabile
    3. tutti i campi (Required) devono essere presenti, se non sono presenti, restituisci quello più probabie, ma senza inventare niente, se proprio non c'è nulla, restituisci il (Default)
    4. i campi (Optional) sono opzionali, se non ci sono, non è un problema e segnali con il (Default)
    5. Usa il (Default) SOLO se non trovi assolutamente nulla, i valori dovrebbero esistere nel documento
    **Inizia l'estrazione! credo in te!** 

            TEMPLATE:
            {template***REMOVED***
            DOCUMENTO:
            {context***REMOVED***""",
    "en": """You are an information extractor from texts.
        Your task is to analyze the provided text and extract the fields specified in the TEMPLATE from the provided DOCUMENT.
        **Guidelines:**
        The goal is to return the requested fields if they are found in the document.
        1. **Exact Values:** Report the exact values as they appear in the DOCUMENT, without modifications or interpretations.
        2. **Multiple Fields:** If a field appears multiple times in the DOCUMENT, respect the format of the field; if it is not a list, return the most likely one.
        3. All (Required) fields must be present. If they are not present, return the most likely one, but without inventing anything. If there is really nothing, return the (Default).
        4. (Optional) fields are optional. If they are not present, it is not a problem, and you signal it with the (Default).
        5. Use the (Default) ONLY if you do not find absolutely anything, the values should exist in the document
        **Start the extraction! I believe in you!**

        TEMPLATE:
        {template***REMOVED***
        DOCUMENT:
        {context***REMOVED***""",
    "es": """Eres un extractor de información de textos.
        Tu tarea es analizar el texto proporcionado y extraer los campos especificados en la PLANTILLA del DOCUMENTO proporcionado.
        **Directrices:**
        El objetivo es devolver los campos solicitados si se encuentran en el documento.
        1. **Valores Exactos:** Reporta los valores exactos tal como aparecen en el DOCUMENTO, sin modificaciones ni interpretaciones.
        2. **Campos Múltiples:** Si un campo aparece varias veces en el DOCUMENTO, respeta el formato del campo; si no es una lista, devuelve el más probable.
        3. Todos los campos (Required) deben estar presentes. Si no están presentes, devuelve el más probable, pero sin inventar nada. Si realmente no hay nada, devuelve el (Default).
        4. Los campos (Optional) son opcionales. Si no están presentes, no es un problema, y lo señalas con el (Default).
        5. Utiliza el (Default) SOLO si no encuentras absolutamente nada, los valores deben existir en el DOCUMENTO
        **¡Comienza la extracción! ¡Creo en ti!**

        PLANTILLA:
        {template***REMOVED***
        DOCUMENTO:
        {context***REMOVED***""",
    "de": """Du bist ein Informationsextraktor aus Texten.
        Deine Aufgabe ist es, den bereitgestellten Text zu analysieren und die in der VORLAGE angegebenen Felder aus dem bereitgestellten DOKUMENT zu extrahieren.
        **Richtlinien:**
        Das Ziel ist es, die angeforderten Felder zurückzugeben, wenn sie im Dokument gefunden werden.
        1. **Genaue Werte:** Gib die genauen Werte so zurück, wie sie im DOKUMENT erscheinen, ohne Änderungen oder Interpretationen.
        2. **Mehrere Felder:** Wenn ein Feld mehrmals im DOKUMENT erscheint, respektiere das Format des Feldes; wenn es sich nicht um eine Liste handelt, gib das wahrscheinlichste zurück.
        3. Alle (Required) Felder müssen vorhanden sein. Wenn sie nicht vorhanden sind, gib das wahrscheinlichste zurück, aber ohne etwas zu erfinden. Wenn wirklich nichts da ist, gib das (Default) zurück.
        4. (Optional) Felder sind optional. Wenn sie nicht vorhanden sind, ist das kein Problem, und du signalisierst es mit dem (Default).
        5. Verwende das (Default) nur, wenn du absolut nichts gefunden hast, die Werte sollten im DOKUMENT vorkommen
        **Starte die Extraktion! Ich glaube an dich!**

        VORLAGE:
        {template***REMOVED***
        DOKUMENT:
        {context***REMOVED***""",
    "fr": """Tu es un extracteur d'informations à partir de textes.
        Ta tâche consiste à analyser le texte fourni et à extraire les champs spécifiés dans le TEMPLATE à partir du DOCUMENT fourni.
        **Lignes directrices :**
        L'objectif est de renvoyer les champs demandés s'ils sont trouvés dans le document.
        1. **Valeurs exactes :** Rapporte les valeurs exactes telles qu'elles apparaissent dans le DOCUMENT, sans modifications ni interprétations.
        2. **Champs multiples :** Si un champ apparaît plusieurs fois dans le DOCUMENT, respecte le format du champ ; s'il ne s'agit pas d'une liste, renvoie le plus probable.
        3. Tous les champs (Required) doivent être présents. S'ils ne sont pas présents, renvoie le plus probable, mais sans rien inventer. S'il n'y a vraiment rien, renvoie le (Default).
        4. Les champs (Optional) sont facultatifs. S'ils ne sont pas présents, ce n'est pas un problème, et tu le signales avec le (Default).
        5. Utilise le (Default) UNIQUEMENT, si vous ne trouvez absolument aucune chose, les valeurs doivent exister dans le DOCUMENT
        **Commence l'extraction ! Je crois en toi !**

        TEMPLATE :
        {template***REMOVED***
        DOCUMENT :
        {context***REMOVED***""",
***REMOVED***

prompts_intelligent = {
"it": """
Il tuo compito: Estrarre i campi specificati nel TEMPLATE dal DOCUMENTO fornito.

Linee guida:

1. ** Priorità all'Estrazione Letterale:

2. ** La tua prima priorità è trovare i valori dei campi direttamente nel testo del DOCUMENTO. Se un campo è menzionato esplicitamente, riporta il suo valore esatto.
Presenze Multiple:

3. ** Se un campo appare più volte nel DOCUMENTO, restituisci tutti i valori rilevanti, spiegando brevemente il contesto di ciascuna occorrenza.
Campi Obbligatori:

4. ** Se il valore non è esplicitamente dichiarato nel testo:
semplicemente prova a indovinare, il contesto e le istruzioni dovrebbero essere usati per dedurre il valore, puoi anche usare le tue conoscenze,

**  Ricorda: **
L'obiettivo principale è estrarre informazioni dal DOCUMENTO, anche se richiede una certa interpretazione.
Utilizza il contesto e le istruzioni per dedurre i valori quando necessario.
Non aver paura di fare inferenze ragionevoli basate sulle informazioni disponibili.
TEMPLATE:

{template***REMOVED***

DOCUMENTO:

{context***REMOVED***
""",
    "en": """
Your Task: Extract the fields specified in the TEMPLATE from the provided DOCUMENT.

Guidelines:

1. ** Prioritize Literal Extraction:

2. ** Your first priority is to find the field values directly within the text of the DOCUMENT. If a field is explicitly mentioned, report its exact value.
Multiple Occurrences:

3. ** If a field appears multiple times in the DOCUMENT, return all relevant values, briefly explaining the context of each occurrence.
Required Fields:

4. ** If the value is not explicitly stated in the text:
just guess, context and instructions should be used to deduce the value, you can also use your own knowledge,

**  Remember: **
The primary goal is to extract information from the DOCUMENT, even if it requires some interpretation.
Use the context and instructions to deduce values when necessary.
Don't be afraid to make reasonable inferences based on the available information.
TEMPLATE:

{template***REMOVED***

DOCUMENT:

{context***REMOVED***
""",
    "es": """
Tu tarea: Extraer los campos especificados en la PLANTILLA del DOCUMENTO proporcionado.

Pautas:

1. ** Priorizar la extracción literal:

2. ** Tu primera prioridad es encontrar los valores de los campos directamente en el texto del DOCUMENTO. Si un campo se menciona explícitamente, informa su valor exacto.
Múltiples ocurrencias:

3. ** Si un campo aparece varias veces en el DOCUMENTO, devuelve todos los valores relevantes, explicando brevemente el contexto de cada ocurrencia.
Campos obligatorios:

4. ** Si el valor no se indica explícitamente en el texto:
simplemente adivina, el contexto y las instrucciones deben usarse para deducir el valor, también puedes usar tu propio conocimiento,

**  Recuerda: **
El objetivo principal es extraer información del DOCUMENTO, incluso si requiere cierta interpretación.
Utiliza el contexto y las instrucciones para deducir los valores cuando sea necesario.
No tengas miedo de hacer inferencias razonables basadas en la información disponible.
PLANTILLA:

{template***REMOVED***

DOCUMENTO:

{context***REMOVED***
""",
"de": """
Deine Aufgabe: Extrahiere die im TEMPLATE angegebenen Felder aus dem bereitgestellten DOKUMENT.

Richtlinien:

1. ** Priorisiere die wörtliche Extraktion:

2. ** Deine erste Priorität ist es, die Feldwerte direkt im Text des DOKUMENTS zu finden. Wenn ein Feld explizit erwähnt wird, gib seinen genauen Wert an.
Mehrfaches Vorkommen:

3. ** Wenn ein Feld mehrmals im DOKUMENT erscheint, gib alle relevanten Werte zurück und erkläre kurz den Kontext jedes Vorkommens.
Erforderliche Felder:

4. ** Wenn der Wert nicht explizit im Text angegeben ist:
rate einfach, Kontext und Anweisungen sollten verwendet werden, um den Wert abzuleiten, du kannst auch dein eigenes Wissen verwenden,

**  Denke daran: **
Das Hauptziel ist es, Informationen aus dem DOKUMENT zu extrahieren, auch wenn dies eine gewisse Interpretation erfordert.
Verwende den Kontext und die Anweisungen, um Werte abzuleiten, wenn nötig.
Scheue dich nicht, vernünftige Schlussfolgerungen auf der Grundlage der verfügbaren Informationen zu ziehen.
TEMPLATE:

{template***REMOVED***

DOKUMENT:

{context***REMOVED***
""",
    
    
    "fr": """
Votre tâche : Extraire les champs spécifiés dans le TEMPLATE à partir du DOCUMENT fourni.

Directives :

1. ** Priorité à l'extraction littérale :

2. ** Votre première priorité est de trouver les valeurs des champs directement dans le texte du DOCUMENT. Si un champ est mentionné explicitement, indiquez sa valeur exacte.
Occurrences multiples :

3. ** Si un champ apparaît plusieurs fois dans le DOCUMENT, retournez toutes les valeurs pertinentes, en expliquant brièvement le contexte de chaque occurrence.
Champs obligatoires :

4. ** Si la valeur n'est pas explicitement indiquée dans le texte :
devinez simplement, le contexte et les instructions doivent être utilisés pour déduire la valeur, vous pouvez également utiliser vos propres connaissances,

**  Rappelez-vous : **
L'objectif principal est d'extraire des informations du DOCUMENT, même si cela nécessite une certaine interprétation.
Utilisez le contexte et les instructions pour déduire les valeurs si nécessaire.
N'ayez pas peur de faire des inférences raisonnables basées sur les informations disponibles.
TEMPLATE :

{template***REMOVED***

DOCUMENT :

{context***REMOVED***
""",
***REMOVED***


keys_config = {
    "OPENAI_API_KEY": "API_KEY_1",
    "AZURE_FORM_RECOGNIZER_KEY": "API_KEY_2",
    "AZURE_FORM_RECOGNIZER_ENDPOINT": "API_KEY_3",
***REMOVED***


table_prompt = {
    "en": """You are an information extractor. Analyze the given text and extract values that match the provided Pydantic schema,
            This text is a table, analyze it well and extract values in accord to the row and column they are in.
            Be careful of whitespaces, punctuation and quotes, and escape the necessary characters, and try your best.
            Do all you can to respond with a JSON output even if the input is not valid         
        ALWAYS Respond with ONLY a SINGLE LINE valid JSON object that strictly adheres to the following schema,
       
            Respond with ONLY a valid JSON object that strictly adheres to the following schema:

            {schema***REMOVED***.
            """,
    "es": """Eres un extractor de información. Analiza el texto proporcionado y extrae los valores que coincidan con el esquema Pydantic suministrado.
            Este texto es una tabla, analízala bien y extrae los valores de acuerdo a la fila y columna en la que se encuentran.
            Ten cuidado con los espacios en blanco y la puntuación, y haz tu mejor esfuerzo.
            Responde con TODAS las instrucciones que se le puedan dar para que el JSON sea valido, incluso si el input no es valido
            Responde con SOLO una línea de texto que sea un JSON válido y que se adhiera estrictamente al siguiente esquema:
            Responde SOLAMENTE con un objeto JSON válido que se adhiera estrictamente al siguiente esquema:

            {schema***REMOVED***.
            """,
    "it": """Sei un estrattore di informazioni. Analizza il testo fornito ed estrai i valori che corrispondono allo schema Pydantic fornito.
            Questo testo è una tabella, analizzala bene ed estrai i valori in base alla riga e alla colonna in cui si trovano.
            Fai attenzione agli spazi bianchi e alla punteggiatura e fai del tuo meglio.
            Rispondi con TUTTE le istruzioni che ci possono essere da dare per ottenere un JSON valido, anche se l'input non è valido
            Rispondi SOLO con una riga di testo che sia un JSON valido e che aderisca strettamente al seguente schema:
            Rispondi SOLO con un oggetto JSON valido che aderisca strettamente al seguente schema:

            {schema***REMOVED***.
            """,
    "de": """Du bist ein Informationsextraktor. Analysiere den gegebenen Text und extrahiere Werte, die dem bereitgestellten Pydantic-Schema entsprechen.
            Dieser Text ist eine Tabelle, analysiere sie gut und extrahiere Werte entsprechend der Zeile und Spalte, in der sie sich befinden.
            Sei vorsichtig mit Leerzeichen und Satzzeichen und gib dein Bestes.
            Lass dir alle Forderungen, um mit einem JSON-Objekt zu antworten, auch wenn der Eingabe nicht valider ist
            Lass dir nur einen JSON-Objekt mit der Striktheit des folgenden Schemas
            Antworte NUR mit einem gültigen JSON-Objekt, das sich strikt an das folgende Schema hält:

            {schema***REMOVED***.
            """,
    "fr": """Vous êtes un extracteur d'informations. Analysez le texte donné et extrayez les valeurs qui correspondent au schéma Pydantic fourni.
            Ce texte est un tableau, analysez-le bien et extrayez les valeurs en fonction de la ligne et de la colonne dans lesquelles elles se trouvent.
            Faites attention aux espaces et à la ponctuation et faites de votre mieux.
            Renvoiez TOUTES les instructions que vous pouvez avoir pour obtenir un JSON valide, même si l'entrée n'est pas valide
            Renvoiez UNIQUEMENT avec une seule ligne de texte qui soit un JSON valide qui adhère strictement au schéma suivant :
            Répondez UNIQUEMENT avec un objet JSON valide qui adhère strictement au schéma suivant :

            {schema***REMOVED***.
            """,
***REMOVED***


system_prompt = {
    "en": """You are an information extractor. Analyze the given text and extract values that match the provided Pydantic schema :
                ALWAYS Respond with ONLY a SINGLE LINE valid JSON object that strictly adheres to the following schema,
                Ensure that the SINGLE LINE JSON object is well-formed with proper commas separating key-value pairs, as it will be parsed
                schema:
            {schema***REMOVED***
            """,
    "es": """Eres un extractor de información. Analiza el texto dado y extrae los valores que coincidan con el esquema Pydantic proporcionado. 
            RESPONDE con SOLO UNA SOLA LINEA de un objeto JSON válido que se adhiera estrictamente al siguiente esquema:
            Responde SOLAMENTE con un objeto JSON válido que se adhiera estrictamente al siguiente esquema:

            {schema***REMOVED***
            """,
    "it": """Sei un estrattore di informazioni. Analizza il testo fornito ed estrai i valori che corrispondono allo schema Pydantic fornito.
            RESPONDI con SOLO UNA SOLA LINEA di un oggetto JSON valido che aderisca strettamente al seguente schema:
            Rispondi SOLO con un oggetto JSON a SINGOLA LINEA valido che aderisca strettamente al seguente schema:

            {schema***REMOVED***.
            """,
    "de": """Du bist ein Informationsextraktor. Analysiere den gegebenen Text und extrahiere Werte, die dem bereitgestellten Pydantic-Schema entsprechen.
            RESPOND mit SOLAMENTE einem SINGLE LINE gueltigen JSON-Objekt, das sich strikt an das folgende Schema hält:
            Antworte NUR mit einem gültigen JSON-Objekt, das sich strikt an das folgende Schema hält:

            {schema***REMOVED***.
            """,
    "fr": """Vous êtes un extracteur d'informations. Analysez le texte donné et extrayez les valeurs qui correspondent au schéma Pydantic fourni.
            RESPOND avec SOLO UNA SOLA LINEA de un objet JSON valide qui adhère strictement au schéma suivant :
            Répondez UNIQUEMENT avec un objet JSON valide qui adhère strictement au schéma suivant :

            {schema***REMOVED***.
            """,
***REMOVED***

user_prompt = {
    "en": """
            Text to analyze: {text***REMOVED***
            """,
    "it": """
            Testo da analizzare: {text***REMOVED***
            """,
    "es": """
            Texto a analizar: {text***REMOVED***
            """,
    "de": """
            Text zum Analysieren: {text***REMOVED***
            """,
    "fr": """
            Texte à analyser: {text***REMOVED***
            """,
***REMOVED***


def create_language_tag_messages(text: str, language: str, is_table=False) -> PromptTemplate:
    """return [
        {
            "role": "system",
            "content": table_prompt[language] if table else system_prompt[language]
        ***REMOVED***,
        {
            "role": "user",
            "content": user_prompt[language].format(text=text)
        ***REMOVED***
    ]"""
    template = (table_prompt[language] if is_table else system_prompt[language]) + user_prompt[language].format(
        text=sanitize_text(text)
    )
    print("template:" ,template)
    return PromptTemplate(input_variables=["schema"], template=template)


desc_tabella = {
    "it": "descrizione della tabella=-> ",
    "en": "description of the table=-> ",
    "es": "descripción de la tabla=-> ",
    "fr": "description de la table=-> ",
    "de": "beschreibung der tabelle=-> ",
***REMOVED***


language_dict={
    "it": {
        "error": "Errori",
        "table": "Tabelle",
        "complex_info":"Informazioni complesse",
        "intelligent_info":"Informazioni interpretate",
    
    ***REMOVED***,
    "en": {
        "error": "Errors",
        "table": "Tables",
        "complex_info":"Complex Information",
        "intelligent_info":"Interpreted Information",
    ***REMOVED***,
    "es": {
        "error": "Errores",
        "table": "Tablas",
        "complex_info":"Información Compleja",
        "intelligent_info":"Información Interpretada",
    ***REMOVED***,
    "de": {
        "error": "Fehler",
        "table": "Tabellen",
        "complex_info":"Komplexe Informationen",
        "intelligent_info":"Interpretierte Informationen",
    ***REMOVED***,
    "fr": {
        "error": "Erreurs",
        "table": "Tableaux",
        "complex_info":"Informations Complexes",
        "intelligent_info":"Informations Interprétées",
    ***REMOVED***,
***REMOVED***