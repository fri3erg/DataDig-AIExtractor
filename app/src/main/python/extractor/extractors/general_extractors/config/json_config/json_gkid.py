NA = "N/A"


field_names = {
    "date": "cod_data",
    "periodo_detenzione_raccomandato": "cod_rhp",
    "indicatore_sintetico_rischio_min": "cod_sri_min",
    "indicatore_sintetico_rischio_max": "cod_sri_max",
    "incidenza_costo_eur_1_min": "cod_riy_eur_1_min",
    "incidenza_costo_eur_1_max": "cod_riy_eur_1_max",
    "incidenza_costo_eur_2_min": "cod_riy_eur_2_min",
    "incidenza_costo_eur_2_max": "cod_riy_eur_2_max",
    "incidenza_costo_eur_rhp_min": "cod_riy_eur_rhp_min",
    "incidenza_costo_eur_rhp_max": "cod_riy_eur_rhp_max",
    "incidenza_costo_perc_1_min": "cod_riy_perc_1_min",
    "incidenza_costo_perc_1_max": "cod_riy_perc_1_max",
    "incidenza_costo_perc_2_min": "cod_riy_perc_2_min",
    "incidenza_costo_perc_2_max": "cod_riy_perc_2_max",
    "incidenza_costo_perc_rhp_min": "cod_riy_perc_rhp_min",
    "incidenza_costo_perc_rhp_max": "cod_riy_perc_rhp_max",
    "costi_ingresso_gkid_min": "cod_costi_ingresso_min",
    "costi_ingresso_gkid_max": "cod_costi_ingresso_max",
    "costi_uscita_gkid_min": "cod_costi_uscita_min",
    "costi_uscita_gkid_max": "cod_costi_uscita_max",
    "commissione_gestione_gkid_min": "cod_commissioni_di_gestione_min",
    "commissione_gestione_gkid_max": "cod_commissioni_di_gestione_max",
    "commissione_transazione_gkid_min": "cod_costi_di_transazione_min",
    "commissione_transazione_gkid_max": "cod_costi_di_transazione_max",
    "commissione_performance_gkid_min": "cod_commissioni_di_performance_min",
    "commissione_performance_gkid_max": "cod_commissioni_di_performance_max",
    "target_market": "cod_target_market",
***REMOVED***


renaming = {
    "file_name": "NOME_FILE",
    "date": "Data",
    "periodo_detenzione_raccomandato": "RHP (anni)",
    "indicatore_sintetico_rischio_min": "SRI MIN",
    "indicatore_sintetico_rischio_max": "SRI MAX",
    "incidenza_costo_eur_1_min": "RIY 1Y EUR MIN",
    "incidenza_costo_eur_1_max": "RIY 1Y EUR MAX",
    "incidenza_costo_eur_2_min": "RIY RHP/2 EUR MIN",
    "incidenza_costo_eur_2_max": "RIY RHP/2 EUR MAX",
    "incidenza_costo_eur_rhp_min": "RIY RHP EUR MIN",
    "incidenza_costo_eur_rhp_max": "RIY RHP EUR MAX",
    "incidenza_costo_perc_1_min": "RIY 1Y % MIN",
    "incidenza_costo_perc_1_max": "RIY 1Y % MAX",
    "incidenza_costo_perc_2_min": "RIY RHP/2 % MIN",
    "incidenza_costo_perc_2_max": "RIY RHP/2 % MAX",
    "incidenza_costo_perc_rhp_min": "RIY RHP % MIN",
    "incidenza_costo_perc_rhp_max": "RIY RHP % MAX",
    "costi_ingresso_gkid_min": "Costi ingresso MIN",
    "costi_ingresso_gkid_max": "Costi ingresso MAX",
    "costi_uscita_gkid_min": "Costi uscita MIN",
    "costi_uscita_gkid_max": "Costi uscita MAX",
    "commissione_gestione_gkid_min": "Commissioni di gestione MIN",
    "commissione_gestione_gkid_max": "Commissioni di gestione MAX",
    "commissione_transazione_gkid_min": "Costi di transazione MIN",
    "commissione_transazione_gkid_max": "Costi di transazione MAX",
    "commissione_performance_gkid_min": "Commissioni di performance MIN",
    "commissione_performance_gkid_max": "Commissioni di performance MAX",
    "target_market": "Target Market",
    "api_costs": "Costi API",
***REMOVED***

transform = {
    "costi_totali_min": "incidenza_costo_eur_rhp_min",
    "costi_totali_max": "incidenza_costo_eur_rhp_max",
    "incidenza_min": "incidenza_costo_perc_rhp_min",
    "incidenza_max": "incidenza_costo_perc_rhp_max",
***REMOVED***


column_order = [
    "NOME_FILE",
    "Data",
    "RHP (anni)",
    "SRI MIN",
    "SRI MAX",
    "RIY 1Y % MIN",
    "RIY 1Y % MAX",
    "RIY RHP/2 % MIN",
    "RIY RHP/2 % MAX",
    "RIY RHP % MIN",
    "RIY RHP % MAX",
    "RIY 1Y EUR MIN",
    "RIY 1Y EUR MAX",
    "RIY RHP/2 EUR MIN",
    "RIY RHP/2 EUR MAX",
    "RIY RHP EUR MIN",
    "RIY RHP EUR MAX",
    "Costi ingresso MIN",
    "Costi ingresso MAX",
    "Costi uscita MIN",
    "Costi uscita MAX",
    "Commissioni di gestione MIN",
    "Commissioni di gestione MAX",
    "Costi di transazione MIN",
    "Costi di transazione MAX",
    "Commissioni di performance MIN",
    "Commissioni di performance MAX",
    "Target Market",
    "Costi API",
]


sections = """
        {
            "section0": {
                "name": "Informazioni di base",
                "list": [
                    "date",
                    "periodo_detenzione_raccomandato",
                    "indicatore_sintetico_rischio_min",
                    "indicatore_sintetico_rischio_max"
                ]
            ***REMOVED***,
            "section1": {
                "name": "Incidenza dei Costi",
                "list": [
                    "incidenza_costo_eur_1_min",
                    "incidenza_costo_eur_1_max",
                    "incidenza_costo_eur_2_min",
                    "incidenza_costo_eur_2_max",
                    "incidenza_costo_eur_rhp_min",
                    "incidenza_costo_eur_rhp_max",
                    "incidenza_costo_perc_1_min",
                    "incidenza_costo_perc_1_max",
                    "incidenza_costo_perc_2_min",
                    "incidenza_costo_perc_2_max",
                    "incidenza_costo_perc_rhp_min",
                    "incidenza_costo_perc_rhp_max"
                ]
            ***REMOVED***,
            "section2": {
                "name": "Costi e Commissioni",
                "list": [
                    "costi_ingresso_gkid_min",
                    "costi_ingresso_gkid_max",
                    "costi_uscita_gkid_min",
                    "costi_uscita_gkid_max",
                    "commissione_gestione_gkid_min",
                    "commissione_gestione_gkid_max",
                    "commissione_transazione_gkid_min",
                    "commissione_transazione_gkid_max",
                    "commissione_performance_gkid_min",
                    "commissione_performance_gkid_max"
                ]
            ***REMOVED***,
            "section3": {
                "name": "Target Market",
                "list": [
                    "target_market"
                ]
        ***REMOVED***
    ***REMOVED***
    """

data_array = [
    "date",
    "periodo_detenzione_raccomandato",
    "indicatore_sintetico_rischio_min",
    "indicatore_sintetico_rischio_max",
    "incidenza_costo_eur_1_min",
    "incidenza_costo_eur_1_max",
    "incidenza_costo_eur_2_min",
    "incidenza_costo_eur_2_max",
    "incidenza_costo_eur_rhp_min",
    "incidenza_costo_eur_rhp_max",
    "incidenza_costo_perc_1_min",
    "incidenza_costo_perc_1_max",
    "incidenza_costo_perc_2_min",
    "incidenza_costo_perc_2_max",
    "incidenza_costo_perc_rhp_min",
    "incidenza_costo_perc_rhp_max",
    "costi_ingresso_gkid_min",
    "costi_ingresso_gkid_max",
    "costi_uscita_gkid_min",
    "costi_uscita_gkid_max",
    "commissione_gestione_gkid_min",
    "commissione_gestione_gkid_max",
    "commissione_transazione_gkid_min",
    "commissione_transazione_gkid_max",
    "commissione_performance_gkid_min",
    "commissione_performance_gkid_max",
    "target_market",
]

TRUE = "true"
FALSE = "false"

allow_null = {
    "date": FALSE,
    "periodo_detenzione_raccomandato": FALSE,
    "indicatore_sintetico_rischio_min": FALSE,
    "indicatore_sintetico_rischio_max": FALSE,
    "incidenza_costo_eur_1_min": TRUE,
    "incidenza_costo_eur_1_max": FALSE,
    "incidenza_costo_eur_2_min": FALSE,
    "incidenza_costo_eur_2_max": FALSE,
    "incidenza_costo_eur_rhp_min": FALSE,
    "incidenza_costo_eur_rhp_max": FALSE,
    "incidenza_costo_perc_1_min": TRUE,
    "incidenza_costo_perc_1_max": FALSE,
    "incidenza_costo_perc_2_min": FALSE,
    "incidenza_costo_perc_2_max": FALSE,
    "incidenza_costo_perc_rhp_min": FALSE,
    "incidenza_costo_perc_rhp_max": FALSE,
    "costi_ingresso_gkid_min": TRUE,
    "costi_ingresso_gkid_max": FALSE,
    "costi_uscita_gkid_min": TRUE,
    "costi_uscita_gkid_max": FALSE,
    "commissione_gestione_gkid_min": FALSE,
    "commissione_gestione_gkid_max": FALSE,
    "commissione_transazione_gkid_min": FALSE,
    "commissione_transazione_gkid_max": FALSE,
    "commissione_performance_gkid_min": TRUE,
    "commissione_performance_gkid_max": FALSE,
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
    "periodo_detenzione_raccomandato": INT,
    "indicatore_sintetico_rischio_min": INT,
    "indicatore_sintetico_rischio_max": INT,
    "incidenza_costo_eur_1_min": "",
    "incidenza_costo_eur_1_max": FLOAT,
    "incidenza_costo_eur_2_min": FLOAT,
    "incidenza_costo_eur_2_max": FLOAT,
    "incidenza_costo_eur_rhp_min": FLOAT,
    "incidenza_costo_eur_rhp_max": FLOAT,
    "incidenza_costo_perc_1_min": FLOAT,
    "incidenza_costo_perc_1_max": FLOAT,
    "incidenza_costo_perc_2_min": FLOAT,
    "incidenza_costo_perc_2_max": FLOAT,
    "incidenza_costo_perc_rhp_min": FLOAT,
    "incidenza_costo_perc_rhp_max": FLOAT,
    "costi_ingresso_gkid_min": FLOAT,
    "costi_ingresso_gkid_max": FLOAT,
    "costi_uscita_gkid_min": FLOAT,
    "costi_uscita_gkid_max": FLOAT,
    "commissione_gestione_gkid_min": FLOAT,
    "commissione_gestione_gkid_max": FLOAT,
    "commissione_transazione_gkid_min": FLOAT,
    "commissione_transazione_gkid_max": FLOAT,
    "commissione_performance_gkid_min": FLOAT,
    "commissione_performance_gkid_max": FLOAT,
    "target_market": STRING,
***REMOVED***


PERCENT = "%"
EURO = "€"
YEARS = "anni"
CAPS = "CAPS"

model_of = {
    "date": NA,
    "periodo_detenzione_raccomandato": YEARS,
    "indicatore_sintetico_rischio_min": YEARS,
    "indicatore_sintetico_rischio_max": YEARS,
    "incidenza_costo_eur_1_min": EURO,
    "incidenza_costo_eur_1_max": EURO,
    "incidenza_costo_eur_2_min": EURO,
    "incidenza_costo_eur_2_max": EURO,
    "incidenza_costo_eur_rhp_min": EURO,
    "incidenza_costo_eur_rhp_max": EURO,
    "incidenza_costo_perc_1_min": PERCENT,
    "incidenza_costo_perc_1_max": PERCENT,
    "incidenza_costo_perc_2_min": PERCENT,
    "incidenza_costo_perc_2_max": PERCENT,
    "incidenza_costo_perc_rhp_min": PERCENT,
    "incidenza_costo_perc_rhp_max": PERCENT,
    "costi_ingresso_gkid_min": EURO,
    "costi_ingresso_gkid_max": EURO,
    "costi_uscita_gkid_min": EURO,
    "costi_uscita_gkid_max": EURO,
    "commissione_gestione_gkid_min": PERCENT,
    "commissione_gestione_gkid_max": PERCENT,
    "commissione_transazione_gkid_min": PERCENT,
    "commissione_transazione_gkid_max": PERCENT,
    "commissione_performance_gkid_min": PERCENT,
    "commissione_performance_gkid_max": PERCENT,
    "target_market": NA,
***REMOVED***


decimals_of = {
    "date": NA,
    "periodo_detenzione_raccomandato": NA,
    "indicatore_sintetico_rischio_min": NA,
    "indicatore_sintetico_rischio_max": NA,
    "incidenza_costo_eur_1_min": 2,
    "incidenza_costo_eur_1_max": 2,
    "incidenza_costo_eur_2_min": 2,
    "incidenza_costo_eur_2_max": 2,
    "incidenza_costo_eur_rhp_min": 2,
    "incidenza_costo_eur_rhp_max": 2,
    "incidenza_costo_perc_1_min": 2,
    "incidenza_costo_perc_1_max": 2,
    "incidenza_costo_perc_2_min": 2,
    "incidenza_costo_perc_2_max": 2,
    "incidenza_costo_perc_rhp_min": 2,
    "incidenza_costo_perc_rhp_max": 2,
    "costi_ingresso_gkid_min": 2,
    "costi_ingresso_gkid_max": 2,
    "costi_uscita_gkid_min": 2,
    "costi_uscita_gkid_max": 2,
    "commissione_gestione_gkid_min": 2,
    "commissione_gestione_gkid_max": 2,
    "commissione_transazione_gkid_min": 2,
    "commissione_transazione_gkid_max": 2,
    "commissione_performance_gkid_min": 2,
    "commissione_performance_gkid_max": 2,
    "target_market": NA,
***REMOVED***


PERCENT_RANGE = []
ISIN_RANGE = []
DATE_RANGE = []

SRI_RANGE = []
NO_RANGE = []

range_of = {
    "date": DATE_RANGE,
    "periodo_detenzione_raccomandato": PERCENT_RANGE,
    "indicatore_sintetico_rischio_min": PERCENT_RANGE,
    "indicatore_sintetico_rischio_max": PERCENT_RANGE,
    "incidenza_costo_eur_1_min": PERCENT_RANGE,
    "incidenza_costo_eur_1_max": PERCENT_RANGE,
    "incidenza_costo_eur_2_min": PERCENT_RANGE,
    "incidenza_costo_eur_2_max": PERCENT_RANGE,
    "incidenza_costo_eur_rhp_min": PERCENT_RANGE,
    "incidenza_costo_eur_rhp_max": PERCENT_RANGE,
    "incidenza_costo_perc_1_min": PERCENT_RANGE,
    "incidenza_costo_perc_1_max": PERCENT_RANGE,
    "incidenza_costo_perc_2_min": PERCENT_RANGE,
    "incidenza_costo_perc_2_max": PERCENT_RANGE,
    "incidenza_costo_perc_rhp_min": PERCENT_RANGE,
    "incidenza_costo_perc_rhp_max": PERCENT_RANGE,
    "costi_ingresso_gkid_min": PERCENT_RANGE,
    "costi_ingresso_gkid_max": PERCENT_RANGE,
    "costi_uscita_gkid_min": PERCENT_RANGE,
    "costi_uscita_gkid_max": PERCENT_RANGE,
    "commissione_gestione_gkid_min": PERCENT_RANGE,
    "commissione_gestione_gkid_max": PERCENT_RANGE,
    "commissione_transazione_gkid_min": PERCENT_RANGE,
    "commissione_transazione_gkid_max": PERCENT_RANGE,
    "commissione_performance_gkid_min": PERCENT_RANGE,
    "commissione_performance_gkid_max": PERCENT_RANGE,
    "target_market": NO_RANGE,
***REMOVED***
