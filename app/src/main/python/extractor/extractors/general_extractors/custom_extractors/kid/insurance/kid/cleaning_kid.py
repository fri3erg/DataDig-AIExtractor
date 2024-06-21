# regex for cleaning, matches things to keep
REGEX_NUMBERS_COMMA = "[+-]?(\s)?\d+((\s)?[,\.]?(\s)?\d+)*"  # example 1,000.00
REGEX_NUMBER = "[+-]?(\s)?\d+((\s)?\d+)*"  # example 1000
REGEX_DATE = "\d{1,2***REMOVED***\/\d{1,2***REMOVED***\/\d{2,4***REMOVED***|\d+\s[A-Za-z]+\s\d+"  # example 01/01/2022
REGEX_ISIN = "[A-Z]{2***REMOVED***[A-Z0-9]{9***REMOVED***[0-9]"  # example IT0000000000
CURRENCY = "[A-Z]{3***REMOVED***"  # example EUR
ANY = ".+"
ANYTHING_WITH_NUMBERS = ".*\d+.*"  # example dsjsd1000dsds


regex_cleaning = {
    "it": {
        "general_info": {
            "isin": "[A-Z0-9]{12***REMOVED***",
            "periodo_detenzione_raccomandato": ANY,
            "indicatore_sintetico_rischio": REGEX_NUMBER,
            "date": REGEX_DATE,
        ***REMOVED***,
        "performance": {
            "favorable_return": REGEX_NUMBERS_COMMA,
            "favorable_return_rhp": REGEX_NUMBERS_COMMA,
            "moderato_return": REGEX_NUMBERS_COMMA,
            "moderato_return_rhp": REGEX_NUMBERS_COMMA,
            "sfavorevole_return": REGEX_NUMBERS_COMMA,
            "sfavorevole_return_rhp": REGEX_NUMBERS_COMMA,
            "stress_return": REGEX_NUMBERS_COMMA,
            "stress_return_rhp": REGEX_NUMBERS_COMMA,
            "scenario_morte_1": "",
            "scenario_morte_rhp": "",
        ***REMOVED***,
        "performance_morte": {
            "scenario_morte_1": REGEX_NUMBERS_COMMA,
            "scenario_morte_rhp": REGEX_NUMBERS_COMMA,
            "periodo_detenzione_raccomandato": REGEX_NUMBER,
            "indicatore_sintetico_rischio": REGEX_NUMBER,
            "indicatore_sintetico_rischio_max": REGEX_NUMBER,
            "date": REGEX_DATE,
        ***REMOVED***,
        "riy": {
            "incidenza_costo_1": REGEX_NUMBERS_COMMA,
            "incidenza_costo_rhp": REGEX_NUMBERS_COMMA,
        ***REMOVED***,
        "costi_ingresso": {
            "costi_ingresso": REGEX_NUMBERS_COMMA,
            "costi_uscita": REGEX_NUMBERS_COMMA,
        ***REMOVED***,
        "costi_gestione": {
            "commissione_gestione": REGEX_NUMBERS_COMMA,
            "commissione_transazione": REGEX_NUMBERS_COMMA,
            "commissione_performance": REGEX_NUMBERS_COMMA,
        ***REMOVED***,
        "rhp": {
            "rhp": "\d+",
        ***REMOVED***,
    ***REMOVED***,
    "en": {***REMOVED***,
***REMOVED***
regex_search = {
    "it": {
        "costi_ingresso": ".{0,3***REMOVED***cost.{0,6***REMOVED***ingress.{0,5***REMOVED***",
        "costi_uscita": ".{0,3***REMOVED***cost.{0,6***REMOVED***uscit.{0,5***REMOVED***",
        "commissione_gestione": ".{0,3***REMOVED***co.{0,14***REMOVED***gestion.{0,5***REMOVED***",
        "commissione_transazione": ".{0,3***REMOVED***co.{0,14***REMOVED***transazion.{0,5***REMOVED***",
        "commissione_performance": ".{0,3***REMOVED***co.{0,14***REMOVED***performance.{0,5***REMOVED***",
        "costi_totali": ".{0,4***REMOVED***cost.{0,4***REMOVED***total.{0,5***REMOVED***",
        "incidenza": ".{0,7***REMOVED***ncidenz.{0,30***REMOVED***|.*RIY.*",
    ***REMOVED***,
    "en": {***REMOVED***,
***REMOVED***
strips_cleaning = {
    "it": {
        "general_info": [],
        "performance": [],
        "riy": [],
        "costi_ingresso": [],
        "costi_gestione": [],
        "market": [
            "\\n",
            "Tipo di investitore  al dettaglio",
            "Tipo di investitore al dettaglio",
            "Investitore al dettaglio cui si intende commercializzare il prodotto",
            "Investitore  al dettaglio  a cui è destinata  l'opzione  di investimento:",
            "Investitori al dettaglio a cui si intende commercializzare il prodotto",
            "Investitori al dettaglio cui si intende commercializzare il prodotto",
            "TIPO DI INVESTITORE AL DETTAGLIO",
            "INVESTITORI AL DETTAGLIO A CUI SI INTENDE COMMERCIALIZZARE IL PRODOTTO",
            "INVESTITORE AL DETTAGLIO A CUI SI INTENDE COMMERCIALIZZARE IL PRODOTTO",
        ],
        "text": ["su 7", "su7", "su  7"],
    ***REMOVED***,
    "en": {
        "performance": [],
        "riy": [],
    ***REMOVED***,
***REMOVED***
