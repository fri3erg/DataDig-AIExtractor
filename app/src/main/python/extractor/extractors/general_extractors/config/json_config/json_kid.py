NA = "N/A"


field_names = {
    "date": "cod_data",
    "isin": "cod_isin",
    "periodo_detenzione_raccomandato": "cod_rhp",
    "favorable_return": "cod_rsfav_1y",
    "favorable_return_rhp": "cod_rsfav_rhp",
    "indicatore_sintetico_rischio": "cod_sri",
    "moderato_return": "cod_rsmod_1y",
    "moderato_return_rhp": "cod_rsmod_rhp",
    "scenario_morte_1": "cod_smor_1y",
    "scenario_morte_rhp": "cod_smor_rhp",
    "sfavorevole_return": "cod_rssfav_1y",
    "sfavorevole_return_rhp": "cod_rssfav_rhp",
    "stress_return": "cod_rsstr_1y",
    "stress_return_rhp": "cod_rsstr_rhp",
    "incidenza_costo_1": "cod_riy_1y",
    "incidenza_costo_rhp": "cod_riy_rhp",
    "costi_ingresso": "cod_costi_ingresso",
    "costi_uscita": "cod_costi_uscita",
    "commissione_gestione": "cod_commissioni_di_gestione",
    "commissione_transazione": "cod_costi_di_transazione",
    "commissione_performance": "cod_commissioni_di_performance",
    "target_market": "cod_target_market",
***REMOVED***


renaming = {
    "file_name": "file_name",
    "date": "Data",
    "isin": "ISIN",
    "periodo_detenzione_raccomandato": "RHP (anni)",
    "favorable_return": "RSFAV 1Y %",
    "favorable_return_rhp": "RSFAV RHP %",
    "indicatore_sintetico_rischio": "SRI",
    "moderato_return": "RSMOD 1Y %",
    "moderato_return_rhp": "RSMOD RHP %",
    "scenario_morte_1": "SMOR 1Y (€)",
    "scenario_morte_rhp": "SMOR RHP (€)",
    "sfavorevole_return": "RSSFAV 1Y %",
    "sfavorevole_return_rhp": "RSSFAV RHP %",
    "stress_return": "RSSTR 1Y %",
    "stress_return_rhp": "RSSTR RHP %",
    "incidenza_costo_1": "RIY 1Y %",
    "incidenza_costo_rhp": "RIY RHP %",
    "costi_ingresso": "Costi ingresso",
    "costi_uscita": "Costi uscita",
    "commissione_gestione": "Commissioni di gestione",
    "commissione_transazione": "Costi di transazione",
    "commissione_performance": "Commissioni di performance",
    "target_market": "Target Market",
    "api_costs": "Costi API",
***REMOVED***


column_order = [
    "file_name",
    "Data",
    "ISIN",
    "RHP (anni)",
    "SRI",
    "RSSTR 1Y %",
    "RSSTR RHP %",
    "RSSFAV 1Y %",
    "RSSFAV RHP %",
    "RSMOD 1Y %",
    "RSMOD RHP %",
    "RSFAV 1Y %",
    "RSFAV RHP %",
    "SMOR 1Y (€)",
    "SMOR RHP (€)",
    "RIY 1Y %",
    "RIY RHP %",
    "Costi ingresso",
    "Costi uscita",
    "Commissioni di gestione",
    "Costi di transazione",
    "Commissioni di performance",
    "Target Market",
    "Costi API",
]

prepare_json = {
    "basic": """{{
        "file_path":"{path***REMOVED***",
        "extraction_cost":{{
            "total":{total***REMOVED***,
            "currency":"EUR",
            "models":{models***REMOVED***
            ***REMOVED******REMOVED***
        ***REMOVED******REMOVED***"""
***REMOVED***
sections = """
{
    "section0": {
        "name": "Informazioni di base",
        "list": ["cod_data", "cod_isin", "cod_rhp"]
    ***REMOVED***,
    "section1": {
        "name": "Performance",
        "list": ["cod_rsfav_1y", "cod_rsfav_rhp", "cod_sri", "cod_rsmod_1y", "cod_rsmod_rhp", "cod_smor_1y", "cod_smor_rhp", "cod_rssfav_1y", "cod_rssfav_rhp", "cod_rsstr_1y", "cod_rsstr_rhp"]
    ***REMOVED***,
    "section2": {
        "name": "Reduction In Yield",
        "list": ["cod_riy_1y", "cod_riy_rhp"]
    ***REMOVED***,
    "section3": {
        "name": "Costi e Commissioni",
        "list": ["cod_costi_ingresso", "cod_costi_uscita", "cod_commissioni_di_gestione", "cod_costi_di_transazione", "cod_commissioni_di_performance"]
    ***REMOVED***,
    "section4": {
        "name": "Target Market",
        "list": ["cod_target_market"]
    ***REMOVED***
***REMOVED***
"""

data_array = [
    "date",
    "isin",
    "periodo_detenzione_raccomandato",
    "favorable_return",
    "favorable_return_rhp",
    "indicatore_sintetico_rischio",
    "moderato_return",
    "moderato_return_rhp",
    "scenario_morte_1",
    "scenario_morte_rhp",
    "sfavorevole_return",
    "sfavorevole_return_rhp",
    "stress_return",
    "stress_return_rhp",
    "incidenza_costo_1",
    "incidenza_costo_rhp",
    "costi_ingresso",
    "costi_uscita",
    "commissione_gestione",
    "commissione_transazione",
    "commissione_performance",
    "target_market",
]

TRUE = "true"
FALSE = "false"

allow_null = {
    "date": FALSE,
    "isin": FALSE,
    "periodo_detenzione_raccomandato": FALSE,
    "favorable_return": FALSE,
    "favorable_return_rhp": FALSE,
    "indicatore_sintetico_rischio": FALSE,
    "moderato_return": FALSE,
    "moderato_return_rhp": FALSE,
    "scenario_morte_1": FALSE,
    "scenario_morte_rhp": FALSE,
    "sfavorevole_return": FALSE,
    "sfavorevole_return_rhp": FALSE,
    "stress_return": FALSE,
    "stress_return_rhp": FALSE,
    "incidenza_costo_1": TRUE,
    "incidenza_costo_rhp": FALSE,
    "costi_ingresso": TRUE,
    "costi_uscita": TRUE,
    "commissione_gestione": FALSE,
    "commissione_transazione": FALSE,
    "commissione_performance": TRUE,
    "target_market": FALSE,
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
    "date": DATE,
    "isin": STRING,
    "periodo_detenzione_raccomandato": INT,
    "favorable_return": FLOAT,
    "favorable_return_rhp": FLOAT,
    "indicatore_sintetico_rischio": INT,
    "moderato_return": FLOAT,
    "moderato_return_rhp": FLOAT,
    "scenario_morte_1": FLOAT,
    "scenario_morte_rhp": FLOAT,
    "sfavorevole_return": FLOAT,
    "sfavorevole_return_rhp": FLOAT,
    "stress_return": FLOAT,
    "stress_return_rhp": FLOAT,
    "incidenza_costo_1": FLOAT,
    "incidenza_costo_rhp": FLOAT,
    "costi_ingresso": FLOAT,
    "costi_uscita": FLOAT,
    "commissione_gestione": FLOAT,
    "commissione_transazione": FLOAT,
    "commissione_performance": FLOAT,
    "target_market": STRING,
***REMOVED***


PERCENT = "%"
EURO = "€"
YEARS = "anni"
CAPS = "CAPS"
model_of = {
    "date": NA,
    "isin": CAPS,
    "periodo_detenzione_raccomandato": YEARS,
    "favorable_return": PERCENT,
    "favorable_return_rhp": PERCENT,
    "indicatore_sintetico_rischio": YEARS,
    "moderato_return": PERCENT,
    "moderato_return_rhp": PERCENT,
    "scenario_morte_1": EURO,
    "scenario_morte_rhp": EURO,
    "sfavorevole_return": PERCENT,
    "sfavorevole_return_rhp": PERCENT,
    "stress_return": PERCENT,
    "stress_return_rhp": PERCENT,
    "incidenza_costo_1": PERCENT,
    "incidenza_costo_rhp": PERCENT,
    "costi_ingresso": PERCENT,
    "costi_uscita": PERCENT,
    "commissione_gestione": PERCENT,
    "commissione_transazione": PERCENT,
    "commissione_performance": PERCENT,
    "target_market": NA,
***REMOVED***


decimals_of = {
    "date": NA,
    "isin": NA,
    "periodo_detenzione_raccomandato": NA,
    "favorable_return": 2,
    "favorable_return_rhp": 2,
    "indicatore_sintetico_rischio": NA,
    "moderato_return": 2,
    "moderato_return_rhp": 2,
    "scenario_morte_1": 2,
    "scenario_morte_rhp": 2,
    "sfavorevole_return": 2,
    "sfavorevole_return_rhp": 2,
    "stress_return": 2,
    "stress_return_rhp": 2,
    "incidenza_costo_1": 2,
    "incidenza_costo_rhp": 2,
    "costi_ingresso": 2,
    "costi_uscita": 2,
    "commissione_gestione": 2,
    "commissione_transazione": 2,
    "commissione_performance": 2,
    "target_market": NA,
***REMOVED***


PERCENT_RANGE = []
ISIN_RANGE = []
DATE_RANGE = []

SRI_RANGE = []
NO_RANGE = []

range_of = {
    "date": DATE_RANGE,
    "isin": ISIN_RANGE,
    "periodo_detenzione_raccomandato": PERCENT_RANGE,
    "favorable_return": PERCENT_RANGE,
    "favorable_return_rhp": PERCENT_RANGE,
    "indicatore_sintetico_rischio": SRI_RANGE,
    "moderato_return": PERCENT_RANGE,
    "moderato_return_rhp": PERCENT_RANGE,
    "scenario_morte_1": NO_RANGE,
    "scenario_morte_rhp": NO_RANGE,
    "sfavorevole_return": PERCENT_RANGE,
    "sfavorevole_return_rhp": PERCENT_RANGE,
    "stress_return": PERCENT_RANGE,
    "stress_return_rhp": PERCENT_RANGE,
    "incidenza_costo_1": PERCENT_RANGE,
    "incidenza_costo_rhp": PERCENT_RANGE,
    "costi_ingresso": PERCENT_RANGE,
    "costi_uscita": PERCENT_RANGE,
    "commissione_gestione": PERCENT_RANGE,
    "commissione_transazione": PERCENT_RANGE,
    "commissione_performance": PERCENT_RANGE,
    "target_market": NO_RANGE,
***REMOVED***

names_of_fields_to_clean_dot = ["SMOR RHP (€)", "SMOR 1Y (€)"]
