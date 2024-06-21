prompts = {
    "it": {
        "general_info_gkid": """Dal documento seguente, estrai 
            - Periodo di detenzione raccomandato 
            - indicatore sintetico di rischio minimo o peggiore(da:)(il primo)
            - indicatore sintetico di rischio massimo o migliore(a:)(il secondo)
            - Data di realizzazione del documento
            DOCUMENTO:
            {context***REMOVED***""",
        "market_gkid": """"Dal documento seguente cita ciò che si dice sugli investitori al dettaglio cui si intende commercializzare il prodotto
        ---ritorna solamente la citazione niente introduzione
        ---dovrebbero essere multiple lunghe frasi
        ---ritorna solamente ciò che è riportato nel documento non rifrasare, non puoi aggiungere niente,non voglio introduzione, fornisci la risposta esatta
        ---se non trovi la citazione, la frase da cercare potrebbe essere leggermente diversa
        

            DOCUMENTO:
            {context***REMOVED***""",
    ***REMOVED***,
    "en": {***REMOVED***,
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
