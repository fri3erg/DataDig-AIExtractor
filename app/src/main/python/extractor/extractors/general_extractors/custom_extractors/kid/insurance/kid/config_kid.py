prompts = {
    "it": {
        "general_info": """Dal documento seguente, estrai 
            - Periodo di detenzione raccomandato o per quanto tempo si presuppone di detenere il prodotto(anni), converti in anni se necessario
            - indicatore sintetico di rischio
            - Data di realizzazione del documento
            DOCUMENTO:
            {context***REMOVED***""",
        "performance1y": """Considerando la seguente tabella,estrai qual'è il rendimento percentuale dei seguenti scenari:
            - STRESS
            - SFAVOREVOLE
            - MODERATO
            - FAVOREVOLE 
            a 1 anno?
            --ritorna solo 4 valori, non di più non di meno
            TABELLA:
                {context***REMOVED***?""",
        "performancerhp": """Considerando la seguente tabella,estrai il rendimento percentuale dei seguenti scenari:
            - STRESS
            - SFAVOREVOLE
            - MODERATO
            - FAVOREVOLE 
            a {rhp***REMOVED*** anni?
            --ritorna solo i valori dopo {rhp***REMOVED*** anni

            TABELLA:
                {context***REMOVED***?""",
        "performancemorte": """Considerando la seguente tabella fornita,
        -dopo o nella la sezione scenario di morte o decesso di evento assicurato
        estrai i possibili rimborsi ai beneficiari al netto dei costi
        -se trovi 3 valori, ritorna solo il primo e l'ultimo
        -ritorna sempre 2 valori, non di più non di meno
 
            TABELLA:
                {context***REMOVED***?""",
        "market": """"Dal documento seguente cita ciò che si dice sugli investitori al dettaglio cui si intende commercializzare il prodotto
        ---ritorna solamente la citazione niente introduzione
        ---dovrebbero essere multiple lunghe frasi
        ---ritorna solamente ciò che è riportato nel documento non rifrasare, non puoi aggiungere niente,non voglio introduzione, fornisci la risposta esatta
        ---se non trovi la citazione, la frase da cercare potrebbe essere leggermente diversa
        

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
    "en": {
        "general_info": """From the following document, extract:
            - ISIN
            - Recommended holding period
            - Synthetic risk indicator

            DOCUMENT:
            {context***REMOVED***""",
        "performance": """Considering the following table, extract both the percentage return and the monetary amount in the following scenarios:
            - STRESS
            - UNFAVORABLE
            - MODERATE
            - FAVORABLE
            both 1 year and {rhp***REMOVED*** years?

            TABLE:
                {context***REMOVED***?""",
        "riy": "",
        "market": """From the following document extract only the quote following (but do not add the phrase to the response)this phrase: 
        -the retail investors to whom we intend to market the product
        ---skip your introduction just say the quote
        ---return only what is specified on the document, do not rephrase, do not add anything, be precise
        ---if you cant find it, the phrase could be slightly different

            DOCUMENTO:
            {context***REMOVED***""",
    ***REMOVED***,
***REMOVED***


word_representation = {
    "it": {
        "performance": ["moderato", "sfavorevole", "favorevole", "stress", "possibile rimborso al"],
        "riy": ["costi totali", "riy"],
        "riy_perc_gkid": ["costi totali", "riy"],
        "costi_ingresso": ["costi di ingresso", "costi di uscita"],
        "costi_gestione": [
            "commissioni di gestione",
            "costi di transazione",
            "commissioni di performance",
            "costi amministrativi",
        ],
    ***REMOVED***,
    "en": {
        "performance": [
            "moderate",
            "unfavorable",
            "favorable",
            "stress",
            "might get back",
        ],
        "riy": ["total costs", "annual cost"],
    ***REMOVED***,
***REMOVED***
