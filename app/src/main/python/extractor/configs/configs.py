prompts = {
    "it": """Il tuo compito è estrarre i campi specificati nel TEMPLATE dal DOCUMENTO fornito. Restituisci tutti i campi che trovi, anche se non sono presenti nel TEMPLATE. 
    **Linee Guida:**
    lo scopo è restituire i campi richiesti se si trovano nel documento
    1. **Valori Esatti:** Riporta i valori esatti come appaiono nel DOCUMENTO, senza modifiche o interpretazioni.
    2. **Campi Multipli:** Se un campo appare più volte nel DOCUMENTO, rispetta il formato del campo , se non è una lista, ritorna il più probabile
    3. tutti i campi Required devono essere presenti, se non sono presenti, restituisci quello più probabie, ma senza inventare niente, se proprio non c'è nulla, restituisci "N/A"
    4. i campi Optional sono opzionali, se non ci sono, non è un problema e segnali "not found"
    **Inizia l'estrazione! credo in te!** 

            TEMPLATE:
            {template***REMOVED***
            DOCUMENTO:
            {context***REMOVED***""",
    "en": """""",
***REMOVED***

prompts_intelligent= {
    "it": """Il tuo compito è estrarre i campi specificati nel TEMPLATE E ISTRUZIONI dal DOCUMENTO fornito. Restituisci tutti i campi che trovi, anche se non sono presenti nel TEMPLATE. 
    **Linee Guida:**
    lo scopo è restituire i campi richiesti se si trovano nel documento o/e cercare di estrarli in modo intelligente usando le istruzioni fornite per ogni campo
    giustifica le tue risposte, ma non essere eccesivamente verboso
    1. **Valori Esatti:** Riporta i valori esatti come appaiono nel DOCUMENTO, e leggi bene le istruzioni per capire cosa cercare e come estrarlo
    2. **Campi Multipli:** Se un campo appare più volte nel DOCUMENTO, ritornali tutti nella stringa esplicitando il contesto in cui si trovano
    3. tutti i campi Required devono essere presenti, se non sono presenti, restituisci quello che pensi più probabile, se proprio non c'è nulla, spiega perchè non è stato trovato
    4. i campi Optional sono opzionali, se non ci sono, non è un problema
    **Inizia l'estrazione! credo in te!** 

            TEMPLATE E ISTRUZIONI:
            {template***REMOVED***
            DOCUMENTO:
            {context***REMOVED***""",
    "en": """""",
***REMOVED***


word_representation = {
    "it": {
        "riy_perc_gkid": ["costi totali", "riy"],
        "costi_ingresso_gkid": ["costi di ingresso", "costi di uscita"],
        "costi_gestione_gkid": [
            "commissioni di gestione",
            "costi di transazione",
            "commissioni di performance",
            "costi amministrativi",
        ],
    ***REMOVED***,
    "en": {***REMOVED***,
***REMOVED***



keys_config={
    "OPENAI_API_KEY" : "API_KEY_1",
    "AZURE_FORM_RECOGNIZER_ENPOINT": "API_KEY_2",
    "AZURE_FORM_RECOGNIZER_KEY": "API_KEY_3",
***REMOVED***


template_prompt= {
    "en":         "Extract information from the following text based on this schema:\n\n{schema***REMOVED***\n\nText:{text***REMOVED***\n\n"
        "Please ensure your response strictly adheres to the schema.\n"
        "remember to adhere to the enums in the schema, use one of the allowed values listed in the schema."
        "there could be possibilities where all the fields or some are missing, in that case use their default values\n\n",
    "it":         "Estrai informazioni dal testo seguente in base a questo schema:\n\n{schema***REMOVED***\n\nTesto:{text***REMOVED***\n\n"
        "Assicurati di rispondere in modo strutturato rispetto al schema.\n"
        "ricorda di aderire alle enums nel schema, usare uno dei valori consentiti nell'enum.\n"
        "c'e' una possibilità che tutti i campi o alcuni siano mancanti, in quel caso usa i valori di default\n\n",
    "es":         "Extrae información del siguiente texto basado en este esquema:\n\n{schema***REMOVED***\n\nTexto:{text***REMOVED***\n\n"
        "Por favor asegúrese de que su respuesta se ajuste al esquema.\n"
        "recuerde que debe aderir a los enums en el esquema, use uno de los valores permitidos en el enum.\n"
        "hay una posibilidad que todos los campos o algunos estén ausentes, en ese caso use sus valores por defecto\n\n",
    "fr":         "Extraire des informations du texte suivant en fonction de ce schéma :\n\n{schema***REMOVED***\n\nTexte:{text***REMOVED***\n\n"
        "Veuillez vous assurer que votre rÃ©ponse se conforme au schéma.\n"
        "Rappelons que tous les champs ou certains sont absents, dans ce cas, utilisez leurs valeurs par défaut\n\n",
    "de":         "Erstelle Informationen aus dem folgenden Text basierend auf diesem Schema:\n\n{schema***REMOVED***\n\nText:{text***REMOVED***\n\n"
        "Bitte stellen Sie sicher, dass Ihre Antwort den Schema entspricht.\n"
        "merken Sie sich, dass alle Felder oder einige fehlen, in diesem Fall verwenden Sie ihren Standardwerten\n\n", 
          
***REMOVED***


desc_tabella={
    "it": "descrizione della tabella=-> ",
    "en": "description of the table=-> ",
    "es": "descripción de la tabla=-> ",
    "fr": "description de la table=-> ",
    "de": "beschreibung der tabelle=-> ",
***REMOVED***