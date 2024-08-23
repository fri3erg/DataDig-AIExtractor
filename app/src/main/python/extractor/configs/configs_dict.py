
# from openai.types.chat import ChatCompletionMessageParam
from langchain.prompts import PromptTemplate


prompts = {
    "it": """Sei un estrattore di informazioni da testi
    Il tuo compito è analizzare il testo fornito ed estrarre i campi specificati nel TEMPLATE dal DOCUMENTO fornito.
    **Linee Guida:**
    lo scopo è restituire i campi richiesti se si trovano nel documento
    1. **Valori Esatti:** Riporta i valori esatti come appaiono nel DOCUMENTO, senza modifiche o interpretazioni.
    2. **Campi Multipli:** Se un campo appare più volte nel DOCUMENTO, rispetta il formato del campo, se non è una lista, ritorna il più probabile
    3. tutti i campi (Required) devono essere presenti, se non sono presenti, restituisci quello più probabie, ma senza inventare niente, se proprio non c'è nulla, restituisci il (Default)
    4. i campi (Optional) sono opzionali, se non ci sono, non è un problema e segnali con il (Default)
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
        **Commence l'extraction ! Je crois en toi !**

        TEMPLATE :
        {template***REMOVED***
        DOCUMENT :
        {context***REMOVED***""",
***REMOVED***

prompts_intelligent = {
    "it": """Il tuo compito è estrarre i campi specificati nel TEMPLATE E ISTRUZIONI dal DOCUMENTO fornito.
    **Linee Guida:**
    lo scopo è restituire i campi richiesti se si trovano nel documento o/e cercare di estrarli in modo intelligente usando le istruzioni fornite per ogni campo
    giustifica le tue risposte, ma non essere eccessivamente verboso
    1. **Valori Esatti:** Riporta i valori esatti come appaiono nel DOCUMENTO, e leggi bene le istruzioni per capire cosa cercare e come estrarlo
    2. **Campi Multipli:** Se un campo appare più volte nel DOCUMENTO, ritornali tutti nella stringa esplicitando il contesto in cui si trovano
    3. tutti i campi (Required) devono essere presenti, se non sono presenti, restituisci quello che pensi più probabile, se proprio non c'è nulla, restituisci il (Default)
    4. i campi (Optional) sono opzionali, se non ci sono, non è un problema, spiega che non sono presenti o ritorna il (Default)
    **Inizia l'estrazione! credo in te!** 

            TEMPLATE E ISTRUZIONI:
            {template***REMOVED***
            DOCUMENTO:
            {context***REMOVED***""",
    "en": """Your task is to extract the fields specified in the TEMPLATE AND INSTRUCTIONS from the provided DOCUMENT.
    **Guidelines:**
    The goal is to return the requested fields if they are found in the document and/or try to extract them intelligently using the instructions provided for each field.
    Justify your answers, but don't be overly verbose.
    1. **Exact Values:** Report the exact values as they appear in the DOCUMENT, and read the instructions carefully to understand what to look for and how to extract it.
    2. **Multiple Fields:** If a field appears multiple times in the DOCUMENT, return them all in the string, explaining the context in which they are found.
    3. All (Required) fields must be present. If they are not present, return what you think is most likely. If there is really nothing, return the (Default).
    4. (Optional) fields are optional. If they are not there, it is not a problem. Explain that they are not present or return the (Default).
    **Start the extraction! I believe in you!**

            TEMPLATE AND INSTRUCTIONS:
            {template***REMOVED***
            DOCUMENT:
            {context***REMOVED***""",
    "es": """Tu tarea es extraer los campos especificados en la PLANTILLA E INSTRUCCIONES del DOCUMENTO proporcionado.
    **Directrices:**
    El objetivo es devolver los campos solicitados si se encuentran en el documento o/e intentar extraerlos de manera inteligente utilizando las instrucciones proporcionadas para cada campo.
    Justifica tus respuestas, pero no seas excesivamente verboso.
    1. **Valores Exactos:** Informa los valores exactos tal como aparecen en el DOCUMENTO y lee atentamente las instrucciones para comprender qué buscar y cómo extraerlo
    2. **Campos Múltiples:** Si un campo aparece varias veces en el DOCUMENTO, devuélvelos todos en la cadena, explicando el contexto en el que se encuentran
    3. Todos los campos (Required) deben estar presentes. Si no están presentes, devuelve lo que creas que es más probable. Si realmente no hay nada, devuelve el (Default)
    4. Los campos (Optional) son opcionales. Si no están allí, no es un problema. Explica que no están presentes o devuelve el (Default)
    **¡Comienza la extracción! ¡Creo en ti!**

            PLANTILLA E INSTRUCCIONES:
            {template***REMOVED***
            DOCUMENTO:
            {context***REMOVED***""",
    "de": """Deine Aufgabe ist es, die in der VORLAGE UND ANWEISUNGEN angegebenen Felder aus dem bereitgestellten DOKUMENT zu extrahieren
    **Richtlinien:**
    Das Ziel ist es, die angeforderten Felder zurückzugeben, wenn sie im Dokument gefunden werden, und/oder zu versuchen, sie intelligent zu extrahieren, indem die für jedes Feld bereitgestellten Anweisungen verwendet werden
    Begründe deine Antworten, aber sei nicht übermäßig ausführlich
    1. **Genaue Werte:** Gib die genauen Werte so an, wie sie im DOKUMENT erscheinen, und lies die Anweisungen sorgfältig durch, um zu verstehen, wonach gesucht und wie es extrahiert werden soll.
    2. **Mehrere Felder:** Wenn ein Feld mehrmals im DOKUMENT erscheint, gib sie alle in der Zeichenfolge zurück und erkläre den Kontext, in dem sie gefunden werden
    3. Alle (Required) Felder müssen vorhanden sein. Wenn sie nicht vorhanden sind, gib das zurück, was du für am wahrscheinlichsten hältst. Wenn wirklich nichts da ist, gib den (Default) zurück
    4. (Optional) Felder sind optional. Wenn sie nicht da sind, ist es kein Problem. Erkläre, dass sie nicht vorhanden sind, oder gib den (Default) zurück
    **Starte die Extraktion! Ich glaube an dich!**

            VORLAGE UND ANWEISUNGEN:
            {template***REMOVED***
            DOKUMENT:
            {context***REMOVED***""",
    "fr": """Votre tâche consiste à extraire les champs spécifiés dans le MODÈLE ET LES INSTRUCTIONS à partir du DOCUMENT fourni
    **Directives:**
    L'objectif est de renvoyer les champs demandés s'ils se trouvent dans le document et/ou d'essayer de les extraire intelligemment en utilisant les instructions fournies pour chaque champ
    Justifiez vos réponses, mais ne soyez pas trop verbeux
    1. **Valeurs exactes:** Indiquez les valeurs exactes telles qu'elles apparaissent dans le DOCUMENT et lisez attentivement les instructions pour comprendre ce qu'il faut rechercher et comment l'extraire
    2. **Champs multiples:** Si un champ apparaît plusieurs fois dans le DOCUMENT, renvoyez-les tous dans la chaîne en expliquant le contexte dans lequel ils se trouvent
    3. Tous les champs (Required) doivent être présents. S'ils ne sont pas présents, renvoyez ce que vous pensez être le plus probable. S'il n'y a vraiment rien, renvoyez la valeur (Default)
    4. Les champs (Optional) sont facultatifs. S'ils ne sont pas présents, ce n'est pas un problème. Expliquez qu'ils ne sont pas présents ou renvoyez la valeur (Default)
    **Commencez l'extraction! Je crois en vous!**

            MODÈLE ET INSTRUCTIONS:
            {template***REMOVED***
            DOCUMENT:
            {context***REMOVED***""",
***REMOVED***


keys_config = {
    "OPENAI_API_KEY": "API_KEY_1",
    "AZURE_FORM_RECOGNIZER_KEY": "API_KEY_2",
    "AZURE_FORM_RECOGNIZER_ENDPOINT": "API_KEY_3",
***REMOVED***


table_prompt = {
    "en": """You are an information extractor. Analyze the given text and extract values that match the provided Pydantic schema,
            This text is a table, analyze it well and extract values in accord to the row and column they are in.
            Be careful of whitespaces and punctuation and try your best.
            Do all you can to respond with a JSON output even if the input is not valid                
            Respond with ONLY a valid JSON object that strictly adheres to the following schema:

            {schema***REMOVED***.
            """,
    "es": """Eres un extractor de información. Analiza el texto proporcionado y extrae los valores que coincidan con el esquema Pydantic suministrado.
            Este texto es una tabla, analízala bien y extrae los valores de acuerdo a la fila y columna en la que se encuentran.
            Ten cuidado con los espacios en blanco y la puntuación, y haz tu mejor esfuerzo.
            Responde SOLAMENTE con un objeto JSON válido que se adhiera estrictamente al siguiente esquema:

            {schema***REMOVED***.
            """,
    "it": """Sei un estrattore di informazioni. Analizza il testo fornito ed estrai i valori che corrispondono allo schema Pydantic fornito.
            Questo testo è una tabella, analizzala bene ed estrai i valori in base alla riga e alla colonna in cui si trovano.
            Fai attenzione agli spazi bianchi e alla punteggiatura e fai del tuo meglio.
            Rispondi SOLO con un oggetto JSON valido che aderisca strettamente al seguente schema:

            {schema***REMOVED***.
            """,
    "de": """Du bist ein Informationsextraktor. Analysiere den gegebenen Text und extrahiere Werte, die dem bereitgestellten Pydantic-Schema entsprechen.
            Dieser Text ist eine Tabelle, analysiere sie gut und extrahiere Werte entsprechend der Zeile und Spalte, in der sie sich befinden.
            Sei vorsichtig mit Leerzeichen und Satzzeichen und gib dein Bestes.
            Antworte NUR mit einem gültigen JSON-Objekt, das sich strikt an das folgende Schema hält:

            {schema***REMOVED***.
            """,
    "fr": """Vous êtes un extracteur d'informations. Analysez le texte donné et extrayez les valeurs qui correspondent au schéma Pydantic fourni.
            Ce texte est un tableau, analysez-le bien et extrayez les valeurs en fonction de la ligne et de la colonne dans lesquelles elles se trouvent.
            Faites attention aux espaces et à la ponctuation et faites de votre mieux.
            Répondez UNIQUEMENT avec un objet JSON valide qui adhère strictement au schéma suivant :

            {schema***REMOVED***.
            """,
***REMOVED***


system_prompt = {
    "en": """You are an information extractor. Analyze the given text and extract values that match the provided Pydantic schema :
                ALWAYS Respond with ONLY a valid JSON object that strictly adheres to the following schema:

            {schema***REMOVED***
            """,
    "es": """Eres un extractor de información. Analiza el texto dado y extrae los valores que coincidan con el esquema Pydantic proporcionado. 
            Responde SOLAMENTE con un objeto JSON válido que se adhiera estrictamente al siguiente esquema:

            {schema***REMOVED***
            """,
    "it": """Sei un estrattore di informazioni. Analizza il testo fornito ed estrai i valori che corrispondono allo schema Pydantic fornito.
            Rispondi SOLO con un oggetto JSON valido che aderisca strettamente al seguente schema:

            {schema***REMOVED***.
            """,
    "de": """Du bist ein Informationsextraktor. Analysiere den gegebenen Text und extrahiere Werte, die dem bereitgestellten Pydantic-Schema entsprechen.
            Antworte NUR mit einem gültigen JSON-Objekt, das sich strikt an das folgende Schema hält:

            {schema***REMOVED***.
            """,
    "fr": """Vous êtes un extracteur d'informations. Analysez le texte donné et extrayez les valeurs qui correspondent au schéma Pydantic fourni.
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
        text=text
    )
    return PromptTemplate(input_variables=["schema"], template=template)


desc_tabella = {
    "it": "descrizione della tabella=-> ",
    "en": "description of the table=-> ",
    "es": "descripción de la tabla=-> ",
    "fr": "description de la table=-> ",
    "de": "beschreibung der tabelle=-> ",
***REMOVED***
