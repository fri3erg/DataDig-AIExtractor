# regex for cleaning, matches things to keep
REGEX_NUMBERS_COMMA = "[+-]?(\s)?\d+((\s)?[,\.]?(\s)?\d+)*"  # example 1,000.00
REGEX_NUMBER = "[+-]?(\s)?\d+((\s)?\d+)*"  # example 1000
REGEX_DATE = "\d{1,2***REMOVED***\/\d{1,2***REMOVED***\/\d{2,4***REMOVED***|\d+\s[A-Za-z]+\s\d+"  # example 01/01/2022
CURRENCY = "[A-Z]{3***REMOVED***"  # example EUR
ANY = ".+"
ANYTHING_WITH_NUMBERS = ".*\d+.*"  # example dsjsd1000dsds


regex_cleaning = {
    "it": {
    ***REMOVED***,
    "en": {***REMOVED***,
***REMOVED***
regex_search = {
    "it": {
    ***REMOVED***,
    "en": {***REMOVED***,
***REMOVED***
strips_cleaning = {
    "it": {
    ***REMOVED***,
    "en": {
    ***REMOVED***,
***REMOVED***
