prompts = {
    "it": {
        "general_info_certificati": """Dal documento seguente, estrai 
            - ISIN
            - descrizione completa del certificato, appare scritta come certificato ... in VALUTA ... su .... , con valuta che può essere EUR o USD
            - emittente o ideatore del certificato, compare dopo: Ideatore:
            DOCUMENTO:
            {context***REMOVED***""",
        "cedola_str": """
            Dal documento seguente, estrai esattamente come riportato il pezzo di testo
            mi interessa tutto il denso testo numerato che comprende date e costi e che usa il carattere '▪' come spaziatore.
            potrebbe non esistere, in quel caso ritorna N/A
            DOCUMENTO:
            {context***REMOVED***""",
    ***REMOVED***,
    "en": {***REMOVED***,
***REMOVED***


word_representation = {
    "it": {
        "cedola": [
            "cedola",
            "cedola",
            "data di osservazione della cedola",
            "data di pagamento della cedola condizionale",
            "importo della cedola condizionale",
        ],
        "sottostanti": ["bloomberg ticker", "ticker", "sottostante", "isin"],
        "main_info": ["valuta del prodotto", "performance peggiore", "modalità di pagamento"],
        "sottostanti_table": ["bloomberg ticker", "ticker", "sottostante", "isin"],
    ***REMOVED***,
    "en": {***REMOVED***,
***REMOVED***

header_mappings = {
    "Data.+realizzazion": "data_realizzazione_cedola",  #
    "Data.+osservazion.+cedol.+": "coupon_valuation_date",
    "Data.+osservazion.+autocal.+": "autocall_valuation_date",
    "Autocal.+Trigge": "barrier_autocall",
    "Data.+Pagament": "coupon_payment_date",
    "Import.+": "importo_cedola",  # condizionale o certa
    "Giorno.+monitor": "callable_valuation_date",
    "Data.+rimbors.+": "autocall_payment_date",
***REMOVED***


regex_callable = {
    "autocall": "autocallable|rimbors.{1,5***REMOVED***anticipat",
    "callable": "softcallable|diritt.{1,4***REMOVED***non.{0,3***REMOVED***obblig",
    "memory": "effett.{1,5***REMOVED***memori",
    "putable": "putable",
***REMOVED***
