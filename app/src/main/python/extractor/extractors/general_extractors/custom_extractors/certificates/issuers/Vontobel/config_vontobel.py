prompts = {
    "it": {***REMOVED***,
    "en": {
        "first_info_vontobel": """
            from the following document, extract:
            -title, found after: "Final Terms for"
            -the ISIN
            -Issuer
            DOCUMENT:
            {context***REMOVED***
            """,
        "main_info_vontobel": """
            from the following document, extract:
            -Settlement Currency
            -Fixing Date
            -Issue date
            -Maturity date
            -Final Valuation Date
            -Calculation amount or Nominal Amount
            -Redemption Level, can be multiple
            -Barrier (in %)
            -Memory
            -Strike (in %), can be multiple
            -Early Redemption
            -Underlying title, often right after 'Underlying'
            -ISIN Underlying, in the section 'Underlying'
            -Bloomberg Symbol, in the section 'Underlying'
            DOCUMENT:
            {context***REMOVED***
            """,
        "main_info_vontobel_2": """
            from the following document, extract:
            -Bonus Amount
            -Bonus Threshold
            -Barrier Event
            -Observation Dates, can be a lot
            -Bonus Payment Date(s)
            -Valuation Date (s)
            -Early Redemption Date (n) 
            DOCUMENT:
            {context***REMOVED***
            """,
        "deductables_vontobel": """
            from the following document, extract:
            -Issue Price
            -Exchange Listing , often under 'Stock Exchange Listing'
            DOCUMENT:
            {context***REMOVED***
            """,
    ***REMOVED***,
***REMOVED***
