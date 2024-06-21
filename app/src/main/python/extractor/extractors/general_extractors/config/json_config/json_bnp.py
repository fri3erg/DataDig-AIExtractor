NA = "N/A"


field_names = {
    "isin": "cod_isin",
    "description": "cod_description",
    "currency": "cod_currency",
    "issuer_desc": "cod_issuer_desc",
    "strike_date": "cod_data_strike",
    "issue_date": "cod_issue_date",
    "expiry_date": "cod_data_liquidazione",
    "final_valuation_date": "cod_data_valutazione_finale",
    "leva_cedolare": "cod_leva_cedolare",
    "nominal": "cod_importo_nozionale",
    "market": "cod_market",
    "unconditional_protection": "cod_protezione_incondizionata",
    "barrier": "cod_barriera",
    "barrier_type": "cod_tipo_barriera",
    "callable": "cod_liquidazione_anticipata",
    "autocall": "cod_autocallability",
    "unconditional_coupon": "cod_premio_incondizionato",
    "conditional_coupon": "cod_premio_condizionato",
    "memory": "cod_memory",
    "autocall": "cod_scadenza_anticipata",
    "conditional_coupon_barrier": "cod_barriera_premio_condizionato",
    "cap": "cod_livello_cap",
    "airbag": "cod_airbag",
    "issue_price_perc": "cod_prezzo_emissione",
    "instrument_isin": "cod_isin_strumento",
    "instrument_bloombergcode": "cod_codice_bloomberg",
    "instrument_description": "cod_descrizione_strumento",
    "observation_coupon_date": "cod_data_osservazione_premio",
    "payment_coupon_date": "cod_data_pagamento_premio",
    "unconditional_coupon": "cod_premio_incondizionato",
    "conditional_coupon": "cod_premio_condizionato",
    "payment_callable_date": "cod_data_pagamento_anticipato",
    "observation_autocall_date": "cod_data_osservazione_autocall",
    "barrier_autocall": "cod_barriera_autocall",
    "payment_autocall_date": "cod_data_pagamento_autocall",
    "value_autocall": "cod_valore_autocall",
    "periodo_detenzione_raccomandato": "cod_periodo_detenzione",
    "indicatore_sintetico_rischio": "cod_indicatore_rischio",
    "importo_minimo": "cod_importo_minimo",
    "leva_airbag": "cod_leva_airbag",
***REMOVED***


renaming = {
    "isin": "Codice Isin",
    "description": "Descrizione",
    "currency": "Valuta del prodotto",
    "issuer_desc": "issuer_desc",
    "strike_date": "Data di Strike",
    "issue_date": "Data di Emissione",
    "expiry_date": "Data di Liquidazione",
    "final_valuation_date": "Data di Valutazione dell'Importo di Liquidazione",
    "leva_cedolare": "Leva Cedolare",
    "nominal": "Importo Nozionale",
    "market": "market",
    "unconditional_protection": "Protezione",
    "barrier": "Barriera",
    "barrier_type": "Tipo barriera",
    "callable": "Liquidazione Anticipata Facoltativa",
    "autocall": "Autocallability",
    "unconditional_coupon": "Premio/i",
    "conditional_coupon": "Premio/i Condizionato/i",
    "memory": "Effetto memoria",
    "autocall": "Scadenza Anticipata Automatica",
    "conditional_coupon_barrier": "Barriera/e per il Versamento del Premio/i Condizionato/i",
    "cap": "Livello Cap",
    "airbag": "airbag",
    "issue_price_perc": "Prezzo di Emissione",
    "instrument_isin": "ISIN",
    "instrument_bloombergcode": "Codice Bloomberg",
    "instrument_description": "underlying_description",
    "observation_coupon_date": "Data/e di Valutazione del/i Premio/i Condizionato/i",
    "payment_coupon_date": "Data/e di Pagamento del/i Premio/i",
    "unconditional_coupon": "Premio/i",
    "conditional_coupon": "Premio/i Condizionato/i",
    "payment_callable_date": "Data di Liquidazione Anticipata Facoltativa",
    "observation_autocall_date": "Data/e di Valutazione dell'Importo di Liquidazione (rimborso) Anticipato",
    "barrier_autocall": "Barriera/e per la Scadenza Anticipata",
    "payment_autocall_date": "Data di Scadenza Anticipata",
    "value_autocall": "Premio/I di Uscita",
    "periodo_detenzione_raccomandato": "Periodo di detenzione raccomandato",
    "indicatore_sintetico_rischio": "Indicatore sintetico di rischio",
    "importo_minimo": "Importo Minimo",
    "leva_airbag": "Leva Airbag",
***REMOVED***


sections = """
        {
    "section0": {
      "name": "Informazioni di base",
      "list": [
        "periodo_detenzione_raccomandato",
        "indicatore_sintetico_rischio"
      ]
    ***REMOVED***,
    "section1": {
      "name": "Informazioni Principali",
      "list": [
        "isin",
        "issuer_desc",
        "currency",
        "strike_date",
        "issue_date",
        "expiry_date",
        "final_valuation_date",
        "nominal",
        "market",
        "barrier",
        "issue_price_perc"
      ]
    ***REMOVED***,
    "section2": {
      "name": "Dettagli Sottostante",
      "list": [
        "instrument_description",
        "instrument_bloombergcode",
        "instrument_isin"
      ]
    ***REMOVED***,
    "section3": {
      "name": "Informazioni sui Premi",
      "list": [
        "observation_coupon_date",
        "payment_coupon_date",
        "conditional_coupon_barrier",
        "unconditional_coupon",
        "conditional_coupon"
      ]
    ***REMOVED***,
    "section4": {
      "name": "Informazioni sulla Scadenza",
      "list": [
        "payment_callable_date",
        "observation_autocall_date",
        "barrier_autocall",
        "payment_autocall_date",
        "value_autocall"
      ]
    ***REMOVED***
  ***REMOVED***
        """


data_array = [
    "isin",
    "description",
    "currency",
    "issuer_desc",
    "strike_date",
    "issue_date",
    "expiry_date",
    "final_valuation_date",
    "leva_cedolare",
    "nominal",
    "market",
    "unconditional_protection",
    "barrier",
    "barrier_type",
    "callable",
    "autocall",
    "unconditional_coupon",
    "conditional_coupon",
    "memory",
    "autocall",
    "conditional_coupon_barrier",
    "cap",
    "airbag",
    "issue_price_perc",
    "instrument_isin",
    "instrument_bloombergcode",
    "instrument_description",
    "observation_coupon_date",
    "payment_coupon_date",
    "unconditional_coupon",
    "conditional_coupon",
    "payment_callable_date",
    "observation_autocall_date",
    "barrier_autocall",
    "payment_autocall_date",
    "value_autocall",
    "periodo_detenzione_raccomandato",
    "indicatore_sintetico_rischio",
    "importo_minimo",
    "leva_airbag",
]

TRUE = "true"
FALSE = "false"

allow_null = {
    "isin": FALSE,
    "description": FALSE,
    "currency": FALSE,
    "issuer_desc": FALSE,
    "strike_date": FALSE,
    "issue_date": FALSE,
    "expiry_date": FALSE,
    "final_valuation_date": FALSE,
    "leva_cedolare": FALSE,
    "nominal": FALSE,
    "market": TRUE,
    "unconditional_protection": FALSE,
    "barrier": FALSE,
    "barrier_type": FALSE,
    "callable": FALSE,
    "autocall": FALSE,
    "unconditional_coupon": FALSE,
    "conditional_coupon": FALSE,
    "memory": FALSE,
    "autocall": FALSE,
    "conditional_coupon_barrier": FALSE,
    "cap": FALSE,
    "airbag": FALSE,
    "issue_price_perc": FALSE,
    "instrument_isin": FALSE,
    "instrument_bloombergcode": FALSE,
    "instrument_description": FALSE,
    "observation_coupon_date": FALSE,
    "payment_coupon_date": FALSE,
    "unconditional_coupon": FALSE,
    "conditional_coupon": FALSE,
    "payment_callable_date": FALSE,
    "observation_autocall_date": FALSE,
    "barrier_autocall": FALSE,
    "payment_autocall_date": FALSE,
    "value_autocall": FALSE,
    "periodo_detenzione_raccomandato": FALSE,
    "indicatore_sintetico_rischio": FALSE,
    "importo_minimo": FALSE,
    "leva_airbag": FALSE,
***REMOVED***

# extra info for json
INT = "Integer"
FLOAT = "Float"
STRING = "String"
DATE = "Date"
BOOL = "Boolean"
LIST_STRING = "List of String"
LIST_DATE = "List of Date"
LIST_FLOAT = "List of Float"

type_of = {
    "isin": STRING,
    "description": STRING,
    "currency": STRING,
    "issuer_desc": STRING,
    "strike_date": DATE,
    "issue_date": DATE,
    "expiry_date": DATE,
    "final_valuation_date": DATE,
    "leva_cedolare": FLOAT,
    "nominal": FLOAT,
    "market": STRING,
    "unconditional_protection": STRING,  # Potrebbe essere BOOLEAN se si intende vero/falso
    "barrier": FLOAT,  # Se si intende una percentuale o un valore numerico
    "barrier_type": STRING,
    "callable": STRING,  # Potrebbe essere BOOLEAN se si intende vero/falso
    "autocall": STRING,  # Potrebbe essere BOOLEAN se si intende vero/falso
    "unconditional_coupon": FLOAT,
    "conditional_coupon": FLOAT,
    "memory": STRING,  # Potrebbe essere BOOLEAN se si intende vero/falso
    "autocall": STRING,  # Potrebbe essere BOOLEAN se si intende vero/falso
    "conditional_coupon_barrier": FLOAT,  # Se si intende una percentuale o un valore numerico
    "cap": FLOAT,
    "airbag": FLOAT,
    "issue_price_perc": FLOAT,
    "instrument_isin": STRING,
    "instrument_bloombergcode": STRING,
    "instrument_description": STRING,
    "observation_coupon_date": DATE,
    "payment_coupon_date": DATE,
    "unconditional_coupon": FLOAT,
    "conditional_coupon": FLOAT,
    "payment_callable_date": DATE,
    "observation_autocall_date": DATE,
    "barrier_autocall": FLOAT,  # Se si intende una percentuale o un valore numerico
    "payment_autocall_date": DATE,
    "value_autocall": FLOAT,
    "periodo_detenzione_raccomandato": INT,
    "indicatore_sintetico_rischio": INT,
    "importo_minimo": FLOAT,
    "leva_airbag": FLOAT,
***REMOVED***


PERCENT = "%"
EURO = "€"
YEARS = "anni"
CAPS = "CAPS"
model_of = {
    "isin": CAPS,
    "description": NA,
    "currency": EURO,
    "issuer_desc": NA,
    "strike_date": NA,
    "issue_date": NA,
    "expiry_date": NA,
    "final_valuation_date": NA,
    "leva_cedolare": PERCENT,
    "nominal": EURO,
    "market": NA,
    "unconditional_protection": NA,  # Potrebbe essere PERCENT se si intende una percentuale
    "barrier": PERCENT,  # Se si intende una percentuale
    "barrier_type": NA,
    "callable": NA,
    "autocall": NA,
    "unconditional_coupon": PERCENT,
    "conditional_coupon": PERCENT,
    "memory": NA,
    "autocall": NA,
    "conditional_coupon_barrier": PERCENT,  # Se si intende una percentuale
    "cap": PERCENT,
    "airbag": PERCENT,
    "issue_price_perc": PERCENT,
    "instrument_isin": CAPS,
    "instrument_bloombergcode": CAPS,
    "instrument_description": NA,
    "observation_coupon_date": NA,
    "payment_coupon_date": NA,
    "unconditional_coupon": PERCENT,
    "conditional_coupon": PERCENT,
    "payment_callable_date": NA,
    "observation_autocall_date": NA,
    "barrier_autocall": PERCENT,  # Se si intende una percentuale
    "payment_autocall_date": NA,
    "value_autocall": PERCENT,
    "periodo_detenzione_raccomandato": YEARS,
    "indicatore_sintetico_rischio": NA,
    "importo_minimo": EURO,
    "leva_airbag": PERCENT,
***REMOVED***


decimals_of = {
    "isin": NA,
    "description": NA,
    "currency": NA,
    "issuer_desc": NA,
    "strike_date": NA,
    "issue_date": NA,
    "expiry_date": NA,
    "final_valuation_date": NA,
    "leva_cedolare": 2,
    "nominal": 2,
    "market": NA,
    "unconditional_protection": 2,  # Se si intende una percentuale, altrimenti NA
    "barrier": 2,  # Se si intende una percentuale o un valore numerico
    "barrier_type": NA,
    "callable": NA,
    "autocall": NA,
    "unconditional_coupon": 2,
    "conditional_coupon": 2,
    "memory": NA,
    "autocall": NA,
    "conditional_coupon_barrier": 2,  # Se si intende una percentuale o un valore numerico
    "cap": 2,
    "airbag": 2,
    "issue_price_perc": 2,
    "instrument_isin": NA,
    "instrument_bloombergcode": NA,
    "instrument_description": NA,
    "observation_coupon_date": NA,
    "payment_coupon_date": NA,
    "unconditional_coupon": 2,
    "conditional_coupon": 2,
    "payment_callable_date": NA,
    "observation_autocall_date": NA,
    "barrier_autocall": 2,  # Se si intende una percentuale o un valore numerico
    "payment_autocall_date": NA,
    "value_autocall": 2,
    "periodo_detenzione_raccomandato": NA,
    "indicatore_sintetico_rischio": NA,
    "importo_minimo": 2,
    "leva_airbag": 2,
***REMOVED***


PERCENT_RANGE = []
ISIN_RANGE = []
DATE_RANGE = []

SRI_RANGE = []
NO_RANGE = []

range_of = {
    "isin": ISIN_RANGE,
    "description": NO_RANGE,
    "currency": NO_RANGE,
    "issuer_desc": NO_RANGE,
    "strike_date": DATE_RANGE,
    "issue_date": DATE_RANGE,
    "expiry_date": DATE_RANGE,
    "final_valuation_date": DATE_RANGE,
    "leva_cedolare": PERCENT_RANGE,
    "nominal": NO_RANGE,
    "market": NO_RANGE,
    "unconditional_protection": PERCENT_RANGE,  # Se si intende una percentuale
    "barrier": PERCENT_RANGE,  # Se si intende una percentuale o un valore numerico
    "barrier_type": NO_RANGE,
    "callable": NO_RANGE,
    "autocall": NO_RANGE,
    "unconditional_coupon": PERCENT_RANGE,
    "conditional_coupon": PERCENT_RANGE,
    "memory": NO_RANGE,
    "autocall": NO_RANGE,
    "conditional_coupon_barrier": PERCENT_RANGE,  # Se si intende una percentuale
    "cap": PERCENT_RANGE,
    "airbag": PERCENT_RANGE,
    "issue_price_perc": PERCENT_RANGE,
    "instrument_isin": ISIN_RANGE,
    "instrument_bloombergcode": NO_RANGE,
    "instrument_description": NO_RANGE,
    "observation_coupon_date": DATE_RANGE,
    "payment_coupon_date": DATE_RANGE,
    "unconditional_coupon": PERCENT_RANGE,
    "conditional_coupon": PERCENT_RANGE,
    "payment_callable_date": DATE_RANGE,
    "observation_autocall_date": DATE_RANGE,
    "barrier_autocall": PERCENT_RANGE,  # Se si intende una percentuale
    "payment_autocall_date": DATE_RANGE,
    "value_autocall": PERCENT_RANGE,
    "periodo_detenzione_raccomandato": NO_RANGE,
    "indicatore_sintetico_rischio": SRI_RANGE,
    "importo_minimo": NO_RANGE,
    "leva_airbag": PERCENT_RANGE,
***REMOVED***
