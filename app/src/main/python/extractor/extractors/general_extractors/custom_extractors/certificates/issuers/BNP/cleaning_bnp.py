REGEX_PERC = r"\d{0,3***REMOVED***[,\.]?\d{1,3***REMOVED***\s?%"

# regex for cleaning, matches things to keep
REGEX_NUMBERS_COMMA = "[+-]?(\s)?\d+((\s)?[,\.]?(\s)?\d+)*"
REGEX_NUMBER = "[+-]?(\s)?\d+((\s)?\d+)*"
REGEX_DATE = "\d{1,2***REMOVED***\/\d{1,2***REMOVED***\/\d{2,4***REMOVED***|\d+\s[A-Za-z]+\s\d+"
REGEX_ISIN = "[A-Z]{2***REMOVED***[A-Z0-9]{9***REMOVED***[0-9]"
CURRENCY = "[A-Z]{3***REMOVED***"
ANY = ".+"
ANYTHING_WITH_NUMBERS = ".*\d+.*"


regex_cleaning = {
    "it": {
        "general_info_certificati": {
            "isin": REGEX_ISIN,
            "description": ANY,
            "issuer_desc": ANY,
        ***REMOVED***,
        "bnp_main": {
            "currency": CURRENCY,
            "strike_date": ANYTHING_WITH_NUMBERS,
            "issue_date": ANYTHING_WITH_NUMBERS,
            "expiry_date": ANYTHING_WITH_NUMBERS,
            "final_valuation_date": ANYTHING_WITH_NUMBERS,
            "nominal": ANYTHING_WITH_NUMBERS,
            "market": ANYTHING_WITH_NUMBERS,
            "barrier": ANYTHING_WITH_NUMBERS,
            "conditional_coupon_barrier": ANYTHING_WITH_NUMBERS,
            "issue_price_perc": ANYTHING_WITH_NUMBERS,
            "observation_coupon_date": ANYTHING_WITH_NUMBERS,
            "payment_coupon_date": ANYTHING_WITH_NUMBERS,
            "unconditional_coupon": ANYTHING_WITH_NUMBERS,
            "conditional_coupon": ANYTHING_WITH_NUMBERS,
            "payment_callable_date": ANYTHING_WITH_NUMBERS,
            "observation_autocall_date": ANYTHING_WITH_NUMBERS,
            "barrier_autocall": ANYTHING_WITH_NUMBERS,
            "payment_autocall_date": ANYTHING_WITH_NUMBERS,
            "value_autocall": ANYTHING_WITH_NUMBERS,
        ***REMOVED***,
        "vontobel_main": {
            "conditional_protection": ANYTHING_WITH_NUMBERS,
            "currency": CURRENCY,
            "strike_date": ANYTHING_WITH_NUMBERS,
            "issue_date": ANYTHING_WITH_NUMBERS,
            "expiry_date": ANYTHING_WITH_NUMBERS,
            "final_valuation_date": ANYTHING_WITH_NUMBERS,
            "nominal": ANYTHING_WITH_NUMBERS,
            "barrier_autocall": ANYTHING_WITH_NUMBERS,
            "memory": ANYTHING_WITH_NUMBERS,
            "strike_level": ANYTHING_WITH_NUMBERS,
            "autocall": ANYTHING_WITH_NUMBERS,
            "payment_callable_date": ANYTHING_WITH_NUMBERS,
            "instrument_description": ANYTHING_WITH_NUMBERS,
            "instrument_isin": ANYTHING_WITH_NUMBERS,
            "instrument_bloombergcode": ANYTHING_WITH_NUMBERS,
            "barrier_type": ANYTHING_WITH_NUMBERS,
            "unconditional_coupon": ANYTHING_WITH_NUMBERS,
            "conditional_coupon": ANYTHING_WITH_NUMBERS,
            "payment_coupon_date": ANYTHING_WITH_NUMBERS,
            "conditional_coupon_barrier": ANYTHING_WITH_NUMBERS,
            "observation_coupon_date": ANYTHING_WITH_NUMBERS,
            "payment_autocall_date": ANYTHING_WITH_NUMBERS,
            "observation_autocall_date": ANYTHING_WITH_NUMBERS,
        ***REMOVED***,
    ***REMOVED***,
    "en": {***REMOVED***,
***REMOVED***
check_for = {
    "importo_minimo": REGEX_PERC,
    "leva_cedolare": REGEX_PERC,
    "cap": REGEX_PERC,
    "leva_airbag": REGEX_PERC,
***REMOVED***
regex_callable = {
    "callable": r"Scadenza\s*Anticipata\s*Opzionale",
    "autocall": r"Scadenza\s*Anticipata\s*Automatica",
    "unconditional_protection": r"quest.\s*prodott.\s*offre\s*.{0,5***REMOVED***\s*protezione\s*totale\s*.{0,10***REMOVED***Importo\s*Nozionale\s*.{0,5***REMOVED***\s*scadenza",
    "memory": r"Tutt.\s*.{0,5***REMOVED***\s*Premi\s*Condizionat.\s*.{0,5***REMOVED***\s*versat.\s*.{0,5***REMOVED***\s*accumuleranno\s*.{0,5***REMOVED***\s*saranno\s*versat.\s*solo\s*.{0,5***REMOVED***\s*.{0,5***REMOVED***\s*Condizion.\s*del\s*Premio\s*Condizionato\s*verrà\s*soddisfatta\s*successivamente",
    "barrier_type": r"corrspondenz.\s*.{0,5***REMOVED***\s*data\s*.{0,5***REMOVED***\s*Valutazione\s*.{0,10***REMOVED***importo\s*.{0,5***REMOVED***\s*Liqidazione,\s*.{0,5***REMOVED***\s*prezzo\s*.{0,5***REMOVED***\s*Riferimento\s*Finale\s*.{0,5***REMOVED***\s*inferiroe\s*.{0,5***REMOVED***\s*barriera",
    "importo_minimo": r"tuttavia\s*.{0,5***REMOVED***importo\s*minimo\s*.{0,5***REMOVED***\s*pari\s*.{0,5***REMOVED***\s*\d{0,3***REMOVED***[,\.]?\d{1,2***REMOVED***\s?%\s*.{0,10***REMOVED***Importo\s*Nozionale",
    "leva_cedolare": r"(aumentato\s*.{0,5***REMOVED***\s*\d{0,3***REMOVED***[,\.]?\d{1,2***REMOVED***\s?%\s*.{0,5***REMOVED***\s*performance\s*.{0,5***REMOVED***\s*Sottostante\s*)|(più\s*.{0,5***REMOVED***\s*importo\s*commisurato\s*.{0,5***REMOVED***\s*\d{0,3***REMOVED***[,\.]?\d{1,2***REMOVED***\s?%\s*.{0,5***REMOVED***\s*Performance)",
    "cap": r"\s*Riferimento\s*Finale\s*.{0,25***REMOVED***\s*pari\s*.{0,5***REMOVED***\s*superiore\s*.{0,5***REMOVED***\s*.{5,20***REMOVED***\s*Prezzo\s*.{0,5***REMOVED***\s*Riferimento\s*Iniziale\s*.{0,20***REMOVED***\s*contant.{0,10***REMOVED***\d{0,3***REMOVED***[,\.]?\d{1,2***REMOVED***\s?%.{0,10***REMOVED***importo\s*nozional",
    "leva_airbag": r"meno\s*.{0,5***REMOVED***\s*importo\s*commisurato\s*.{0,10***REMOVED***\s*.{0,5***REMOVED***\s*Performance\s*.{0,5***REMOVED***\s*Sottostante",
***REMOVED***
