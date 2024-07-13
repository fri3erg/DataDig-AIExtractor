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
