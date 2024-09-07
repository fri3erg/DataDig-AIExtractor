
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
Il tuo compito è estrarre i campi specificati nel TEMPLATE E ISTRUZIONI dal DOCUMENTO fornito.

**Linee guida:**

L'obiettivo è restituire i campi richiesti se sono presenti nel documento OPPURE, se non esplicitamente indicati, dedurre o interpretare in modo intelligente i loro valori in base al contesto e alle istruzioni fornite per ciascun campo.

Giustifica le tue risposte in modo conciso.

1. **Valori Esatti**: Se il campo richiesto è menzionato direttamente nel DOCUMENTO, riporta il suo valore esatto così come appare. Leggi attentamente le istruzioni per capire cosa cercare e come estrarlo.

2. **Presenze Multiple**: Se un campo appare più volte nel DOCUMENTO, restituisci tutti i valori rilevanti nella stringa, spiegando brevemente il contesto in cui si trova ciascun valore.

3. **Campi Obbligatori**: Tutti i campi obbligatori devono essere presenti nell'output.
   * Se il valore di un campo obbligatorio non è esplicitamente dichiarato nel DOCUMENTO, utilizza la tua comprensione del contesto e le istruzioni del campo per fare un'ipotesi o un'interpretazione plausibile del suo valore più probabile. 
   * Utilizza il valore (Default) solo se non hai assolutamente idea di cosa fare.

4. **Campi Opzionali**: I campi opzionali non sono obbligatori.
   * Se il valore di un campo opzionale non è presente o non può essere dedotto dal DOCUMENTO, indica chiaramente che non è presente o, se applicabile, utilizza il valore (Default).

5. **Istruzioni come Contesto**: Le istruzioni per ciascun campo (titolo e descrizione) forniscono un contesto cruciale per l'interpretazione. Utilizza questo contesto per prendere decisioni informate sul valore del campo, anche se non è direttamente dichiarato nel testo.

**Ricorda, l'obiettivo non è solo estrarre informazioni letterali, ma comprendere il significato generale del DOCUMENTO e fornire interpretazioni approfondite dove necessario.**

**Inizia l'estrazione! Credo in te!**

TEMPLATE E ISTRUZIONI:
{template***REMOVED***
DOCUMENTO:
{context***REMOVED***
""",
    "en": """
Your task is to extract the fields specified in the TEMPLATE AND INSTRUCTIONS from the provided DOCUMENT.

**Guidelines:**

The goal is to return the requested fields if they are found in the document OR, if not explicitly stated, intelligently deduce or interpret their values based on the context and the instructions provided for each field.

Justify your answers concisely.

1. **Exact Values**: If the requested field is directly mentioned in the DOCUMENT, report its exact value as it appears. Carefully read the instructions to understand what to look for and how to extract it.

2. **Multiple Occurrences**: If a field appears multiple times in the DOCUMENT, return all relevant values in the string, briefly explaining the context in which each value is found.

3. **Required Fields**: All required fields must be present in the output. 
   * If a required field's value is not explicitly stated in the DOCUMENT, use your understanding of the context and the field's instructions to make an educated guess or interpretation of its most likely value. 
   * Only if there's absolutely no basis for inference, use the provided (Default) value.

4. **Optional Fields**: Optional fields are not mandatory. 
   * If an optional field's value is not present or cannot be inferred from the DOCUMENT, clearly state that it's not present or, if applicable, use the (Default) value.

5. **Instructions as Context**: The instructions for each field (title and description) provide crucial context for interpretation. Utilize this context to make informed decisions about the field's value, even if it's not directly stated in the text. 

**Remember, the goal is not just to extract verbatim information but to understand the DOCUMENT's overall meaning and provide insightful interpretations where necessary.** 

**Start the extraction! I believe in you!**

TEMPLATE AND INSTRUCTIONS:
{template***REMOVED***
DOCUMENT:
{context***REMOVED***
""",
    "es": """
Tu tarea es extraer los campos especificados en la PLANTILLA E INSTRUCCIONES del DOCUMENTO proporcionado.

**Directrices:**

El objetivo es devolver los campos solicitados si se encuentran en el documento O, si no se indican explícitamente, deducir o interpretar de forma inteligente sus valores en función del contexto y las instrucciones proporcionadas para cada campo.

Justifica tus respuestas de manera concisa.

1. **Valores Exactos**: Si el campo solicitado se menciona directamente en el DOCUMENTO, informa su valor exacto tal como aparece. Lee atentamente las instrucciones para comprender qué buscar y cómo extraerlo.

2. **Múltiples Ocurrencias**: Si un campo aparece varias veces en el DOCUMENTO, devuelve todos los valores relevantes en la cadena, explicando brevemente el contexto en el que se encuentra cada valor.

3. **Campos Obligatorios**: Todos los campos obligatorios deben estar presentes en la salida.
   * Si el valor de un campo obligatorio no se indica explícitamente en el DOCUMENTO, utiliza tu comprensión del contexto y las instrucciones del campo para hacer una suposición o interpretación fundamentada de su valor más probable. 
   * Utiliza el valor (Por Defecto) solo si no tienes absolutamente ninguna idea de qué hacer.

4. **Campos Opcionales**: Los campos opcionales no son obligatorios.
   * Si el valor de un campo opcional no está presente o no se puede inferir del DOCUMENTO, indica claramente que no está presente o, si corresponde, utiliza el valor (Por Defecto).

5. **Instrucciones como Contexto**: Las instrucciones para cada campo (título y descripción) proporcionan un contexto crucial para la interpretación. Utiliza este contexto para tomar decisiones informadas sobre el valor del campo, incluso si no está directamente indicado en el texto.

**Recuerda, el objetivo no es solo extraer información literal, sino comprender el significado general del DOCUMENTO y proporcionar interpretaciones perspicaces cuando sea necesario.**

**¡Comienza la extracción! ¡Creo en ti!**

PLANTILLA E INSTRUCCIONES:
{template***REMOVED***
DOCUMENTO:
{context***REMOVED***
""",
    "de": """
Deine Aufgabe ist es, die im TEMPLATE UND ANWEISUNGEN angegebenen Felder aus dem bereitgestellten DOKUMENT zu extrahieren.

**Richtlinien:**

Das Ziel ist es, die angeforderten Felder zurückzugeben, wenn sie im Dokument gefunden werden ODER, falls nicht explizit angegeben, ihre Werte intelligent basierend auf dem Kontext und den für jedes Feld bereitgestellten Anweisungen abzuleiten oder zu interpretieren.

Begründe deine Antworten präzise.

1. **Exakte Werte**: Wenn das angeforderte Feld direkt im DOKUMENT erwähnt wird, gib seinen genauen Wert so an, wie er erscheint. Lies die Anweisungen sorgfältig durch, um zu verstehen, wonach du suchen musst und wie du es extrahieren kannst.

2. **Mehrfaches Vorkommen**: Wenn ein Feld mehrmals im DOKUMENT erscheint, gib alle relevanten Werte in der Zeichenkette zurück und erkläre kurz den Kontext, in dem jeder Wert gefunden wird.

3. **Erforderliche Felder**: Alle erforderlichen Felder müssen in der Ausgabe vorhanden sein.
   * Wenn der Wert eines erforderlichen Feldes im DOKUMENT nicht explizit angegeben ist, verwende dein Verständnis des Kontexts und der Feldanweisungen, um eine fundierte Vermutung oder Interpretation seines wahrscheinlichsten Wertes abzugeben.
   * Verwende den Wert (Standard) nur, wenn du absolut keine Ahnung hast, was zu tun ist.

4. **Optionale Felder**: Optionale Felder sind nicht obligatorisch.
   * Wenn der Wert eines optionalen Feldes nicht vorhanden ist oder nicht aus dem DOKUMENT abgeleitet werden kann, gib klar an, dass er nicht vorhanden ist oder verwende gegebenenfalls den Wert (Standard).

5. **Anweisungen als Kontext**: Die Anweisungen für jedes Feld (Titel und Beschreibung) liefern einen entscheidenden Kontext für die Interpretation. Nutze diesen Kontext, um fundierte Entscheidungen über den Wert des Feldes zu treffen, auch wenn er nicht direkt im Text angegeben ist.

**Denke daran, das Ziel ist nicht nur, wörtliche Informationen zu extrahieren, sondern die Gesamtbedeutung des DOKUMENTS zu verstehen und bei Bedarf aufschlussreiche Interpretationen zu geben, wenn noetig.**

**Beginne mit der Extraktion!**

**Ich bin dir dabei!**

VORLAGE UND ANWEISUNGEN:
{template***REMOVED***
DOKUMENT:
{context***REMOVED***
""",
    
    
    "fr": """
Votre tâche consiste à extraire les champs spécifiés dans le MODÈLE ET LES INSTRUCTIONS à partir du DOCUMENT fourni.

**Directives:**

L'objectif est de renvoyer les champs demandés s'ils sont présents dans le document OU, s'ils ne sont pas explicitement indiqués, de déduire ou d'interpréter intelligemment leurs valeurs en fonction du contexte et des instructions fournies pour chaque champ.

Justifiez vos réponses de manière concise.

1. **Valeurs Exactes** : Si le champ demandé est directement mentionné dans le DOCUMENT, indiquez sa valeur exacte telle qu'elle apparaît. Lisez attentivement les instructions pour comprendre ce qu'il faut chercher et comment l'extraire.

2. **Occurrences Multiples** : Si un champ apparaît plusieurs fois dans le DOCUMENT, renvoyez toutes les valeurs pertinentes dans la chaîne, en expliquant brièvement le contexte dans lequel chaque valeur se trouve.

3. **Champs Obligatoires** : Tous les champs obligatoires doivent être présents dans la sortie.
   * Si la valeur d'un champ obligatoire n'est pas explicitement indiquée dans le DOCUMENT, utilisez votre compréhension du contexte et les instructions du champ pour faire une supposition ou une interprétation éclairée de sa valeur la plus probable. 
   * Utilisez la valeur (Par Défaut) uniquement si vous n'avez absolument aucune idée de quoi faire.

4. **Champs Facultatifs** : Les champs facultatifs ne sont pas obligatoires.
   * Si la valeur d'un champ facultatif n'est pas présente ou ne peut pas être déduite du DOCUMENT, indiquez clairement qu'elle n'est pas présente ou, si applicable, utilisez la valeur (Par Défaut).

5. **Instructions comme Contexte** : Les instructions pour chaque champ (titre et description) fournissent un contexte crucial pour l'interprétation. Utilisez ce contexte pour prendre des décisions éclairées sur la valeur du champ, même si elle n'est pas directement indiquée dans le texte.

**N'oubliez pas que l'objectif n'est pas seulement d'extraire des informations textuelles, mais de comprendre le sens global du DOCUMENT et de fournir des interprétations pertinentes si nécessaire.**

**Commencez l'extraction ! Je crois en vous !**

MODÈLE ET INSTRUCTIONS :
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
    print("template" ,template)
    return PromptTemplate(input_variables=["schema"], template=template)


desc_tabella = {
    "it": "descrizione della tabella=-> ",
    "en": "description of the table=-> ",
    "es": "descripción de la tabla=-> ",
    "fr": "description de la table=-> ",
    "de": "beschreibung der tabelle=-> ",
***REMOVED***
