NA = "N/A"


field_names = {
    "isin": "cod_isin",
    "description": "cod_description",
    "issuer_desc": "cod_issuer_desc",
    "conditional_protection": "cod_conditional_protection",
    "currency": "cod_currency",
    "strike_date": "cod_strike_date",
    "issue_date": "cod_issue_date",
    "expiry_date": "cod_expiry_date",
    "final_valuation_date": "cod_final_valuation_date",
    "nominal": "cod_nominal",
    "barrier_autocall": "cod_autocall_barrier",
    "memory": "cod_memory",
    "strike_level": "cod_strike_level",
    "autocall": "cod_autocall",
    "payment_callable_date": "cod_payment_callable_date",
    "instrument_description": "cod_instrument_description",
    "instrument_isin": "cod_instrument_isin",
    "instrument_bloombergcode": "cod_instrument_bloombergcode",
    "barrier_type": "cod_barrier_type",
    "unconditional_coupon": "cod_unconditional_coupon",
    "conditional_coupon": "cod_conditional_coupon",
    "payment_coupon_date": "cod_payment_coupon_date",
    "conditional_coupon_barrier": "cod_conditional_coupon_barrier",
    "observation_coupon_date": "cod_observation_coupon_date",
    "payment_autocall_date": "cod_payment_autocall_date",
    "observation_autocall_date": "cod_observation_autocall_date",
    "market": "cod_market",
    "issue_price_perc": "cod_issue_price_perc",
***REMOVED***


renaming = {
    "market": "Stock Exchange Listing",
    "isin": "ISIN",
    "description": "Final Terms for",
    "issue_price_perc": "Issue Price",
    "issuer_desc": "Issuer",
    "barrier_type": "Barrier Event",
    "autocall": "Early Redemption",
    "strike_level": "Strike",
    "memory": "Memory",
    "conditional_protection": "Barrier",
    "currency": "Settlement Currency",
    "strike_date": "Fixing Date",
    "issue_date": "Issue date",
    "expiry_date": "Maturity date",
    "final_valuation_date": "Final Valuation Date",
    "nominal": "Calculation amount",
    "uncoditional_protection": "Bonus Amount unconditional",
    "conditional_coupon": "Bonus Amount conditional",
    "barrier_autocall": "Redemption Level",
    "conditional_coupon_barrier": "Bonus Threshold",
    "observation_coupon_date": "Observation Date(s)",
    "payment_coupon_date": "Bonus Payment Date(s)",
    "payment_callable_date": "",
    "observation_autocall_date": "Valuation Date(s)",
    "payment_autocall_date": "Early Redemption Date(n)",
    "instrument_isin": "ISIN Underlying",
    "instrument_bloombergcode": "Bloomberg Symbol",
    "instrument_description": "Underlying",
***REMOVED***


sections = """
        {
  "section0": {
    "name": "Tabella First Info",
    "list": [
      "isin",
      "description",
      "issuer_desc"
    ]
  ***REMOVED***,
  "section1": {
    "name": "Tabella Main Info",
    "list": [
      "conditional_protection",
      "currency",
      "strike_date",
      "issue_date",
      "expiry_date",
      "final_valuation_date",
      "nominal",
      "barrier_autocall",
      "memory",
      "strike_level",
      "autocall",
      "payment_callable_date",
      "instrument_description",
      "instrument_isin",
      "instrument_bloombergcode"
    ]
  ***REMOVED***,
  "section2": {
    "name": "Tabella Main Info 2",
    "list": [
      "barrier_type",
      "unconditional_coupon",
      "conditional_coupon",
      "payment_coupon_date",
      "conditional_coupon_barrier",
      "observation_coupon_date",
      "payment_autocall_date",
      "observation_autocall_date"
    ]
  ***REMOVED***,
  "section3": {
    "name": "Tabella Deductable",
    "list": [
      "market",
      "issue_price_perc"
    ]
  ***REMOVED***
***REMOVED***

        """


data_array = [
    "isin",
    "description",
    "issuer_desc",  # TabellaFirstInfoVontobel
    "conditional_protection",
    "currency",
    "strike_date",
    "issue_date",
    "expiry_date",
    "final_valuation_date",
    "nominal",
    "barrier_autocall",
    "memory",
    "strike_level",
    "autocall",
    "payment_callable_date",
    "instrument_description",
    "instrument_isin",
    "instrument_bloombergcode",  # TabellaMainInfoVontobel
    "barrier_type",
    "unconditional_coupon",
    "conditional_coupon",
    "payment_coupon_date",
    "conditional_coupon_barrier",
    "observation_coupon_date",
    "payment_autocall_date",
    "observation_autocall_date",  # TabellaMainInfoVontobel2
    "market",
    "issue_price_perc",  # TabellaDeductableVontobel
]

TRUE = "true"
FALSE = "false"

allow_null = {
    "isin": FALSE,
    "description": FALSE,
    "issuer_desc": FALSE,
    "conditional_protection": FALSE,
    "currency": FALSE,
    "strike_date": FALSE,
    "issue_date": FALSE,
    "expiry_date": FALSE,
    "final_valuation_date": FALSE,
    "nominal": FALSE,
    "barrier_autocall": FALSE,
    "memory": FALSE,
    "strike_level": FALSE,
    "autocall": FALSE,
    "payment_callable_date": FALSE,
    "instrument_description": FALSE,
    "instrument_isin": FALSE,
    "instrument_bloombergcode": FALSE,
    "barrier_type": FALSE,
    "unconditional_coupon": FALSE,
    "conditional_coupon": FALSE,
    "payment_coupon_date": FALSE,
    "conditional_coupon_barrier": FALSE,
    "observation_coupon_date": FALSE,
    "payment_autocall_date": FALSE,
    "observation_autocall_date": FALSE,
    "market": FALSE,
    "issue_price_perc": FALSE,
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
    "issuer_desc": STRING,
    "conditional_protection": LIST_STRING,
    "currency": STRING,
    "strike_date": DATE,
    "issue_date": DATE,
    "expiry_date": DATE,
    "final_valuation_date": DATE,
    "nominal": FLOAT,
    "barrier_autocall": FLOAT,  # or LIST_FLOAT if considering the second declaration in the class
    "memory": FLOAT,
    "strike_level": LIST_FLOAT,
    "autocall": BOOL,
    "payment_callable_date": LIST_DATE,
    "instrument_description": LIST_STRING,
    "instrument_isin": LIST_STRING,
    "instrument_bloombergcode": LIST_STRING,
    "barrier_type": STRING,
    "unconditional_coupon": LIST_FLOAT,
    "conditional_coupon": LIST_FLOAT,
    "payment_coupon_date": LIST_DATE,
    "conditional_coupon_barrier": LIST_FLOAT,
    "observation_coupon_date": LIST_DATE,
    "payment_autocall_date": LIST_DATE,
    "observation_autocall_date": LIST_DATE,
    "market": STRING,
    "issue_price_perc": FLOAT,
***REMOVED***


PERCENT = "%"
EURO = "€"
YEARS = "anni"
CAPS = "CAPS"
model_of = {
    "isin": CAPS,
    "description": STRING,
    "issuer_desc": STRING,
    "conditional_protection": NA,
    "currency": STRING,
    "strike_date": DATE,
    "issue_date": DATE,
    "expiry_date": DATE,
    "final_valuation_date": DATE,
    "nominal": FLOAT,
    "barrier_autocall": FLOAT,  # Assuming FLOAT since barrier typically refers to a numerical threshold
    "memory": PERCENT,  # Memory features in financial products often relate to percentages
    "strike_level": FLOAT,
    "autocall": BOOL,  # Not in the provided constants, but BOOLEAN seems appropriate for a yes/no feature
    "payment_callable_date": DATE,  # Assuming a single date, but could be LIST_OF_DATES if multiple
    "instrument_description": STRING,
    "instrument_isin": CAPS,
    "instrument_bloombergcode": STRING,  # Assuming STRING, but specific constant not provided
    "barrier_type": STRING,  # Specific constant not provided, STRING as a default
    "unconditional_coupon": FLOAT,  # Assuming FLOAT since coupons often relate to numerical values
    "conditional_coupon": FLOAT,  # Same reasoning as unconditional_coupon
    "payment_coupon_date": DATE,
    "conditional_coupon_barrier": FLOAT,  # Assuming FLOAT since it's a barrier
    "observation_coupon_date": DATE,
    "payment_autocall_date": DATE,
    "observation_autocall_date": DATE,
    "market": STRING,  # Specific constant not provided, STRING as a default
    "issue_price_perc": PERCENT,  # Assuming PERCENT since it's a percentage value
***REMOVED***


decimals_of = {
    "isin": NA,
    "description": NA,
    "issuer_desc": NA,
    "conditional_protection": NA,
    "currency": NA,
    "strike_date": NA,
    "issue_date": NA,
    "expiry_date": NA,
    "final_valuation_date": NA,
    "nominal": 2,  # Financial amounts are often expressed with two decimal places
    "barrier_autocall": 2,  # Barrier levels in financial products typically have decimal precision
    "memory": 2,  # Percentages often have up to two decimal places
    "strike_level": 2,  # Financial thresholds often have decimal precision
    "autocall": NA,
    "payment_callable_date": NA,
    "instrument_description": NA,
    "instrument_isin": NA,
    "instrument_bloombergcode": NA,
    "barrier_type": NA,
    "unconditional_coupon": 2,  # Coupon values usually have decimal precision
    "conditional_coupon": 2,  # Same reasoning as unconditional_coupon
    "payment_coupon_date": NA,
    "conditional_coupon_barrier": 2,  # Barrier values typically have decimal precision
    "observation_coupon_date": NA,
    "payment_autocall_date": NA,
    "observation_autocall_date": NA,
    "market": NA,
    "issue_price_perc": 2,  # Percentages often have up to two decimal places
***REMOVED***


PERCENT_RANGE = []
ISIN_RANGE = []
DATE_RANGE = []

SRI_RANGE = []
NO_RANGE = []

range_of = {
    "isin": ISIN_RANGE,
    "description": NO_RANGE,
    "issuer_desc": NO_RANGE,
    "conditional_protection": NO_RANGE,
    "currency": NO_RANGE,
    "strike_date": DATE_RANGE,
    "issue_date": DATE_RANGE,
    "expiry_date": DATE_RANGE,
    "final_valuation_date": DATE_RANGE,
    "nominal": NO_RANGE,
    "barrier_autocall": NO_RANGE,
    "memory": PERCENT_RANGE,
    "strike_level": NO_RANGE,
    "autocall": NO_RANGE,
    "payment_callable_date": DATE_RANGE,
    "instrument_description": NO_RANGE,
    "instrument_isin": ISIN_RANGE,
    "instrument_bloombergcode": NO_RANGE,
    "barrier_type": NO_RANGE,
    "unconditional_coupon": PERCENT_RANGE,
    "conditional_coupon": PERCENT_RANGE,
    "payment_coupon_date": DATE_RANGE,
    "conditional_coupon_barrier": NO_RANGE,
    "observation_coupon_date": DATE_RANGE,
    "payment_autocall_date": DATE_RANGE,
    "observation_autocall_date": DATE_RANGE,
    "market": NO_RANGE,
    "issue_price_perc": PERCENT_RANGE,
***REMOVED***
