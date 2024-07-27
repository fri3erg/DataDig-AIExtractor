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