import re

from extractors.general_extractors.utils import divide_regex
from extractors.general_extractors.utils import search_reg
from pydantic import BaseModel, Field


# strips to cut from the text
exc_multiple_lines = ["commissione_gestione", "costi_totali", "incidenza"]


# TO REVIEW
def clean_response_regex(cleaner: dict, response, to_add=""):
    """cleans a response using a regex

        Args:
        cleaner (dict): dict of keys and string of regex cleaner
        response (dict()| object): data to clean
        to_add (str, optional): string to add to the cleaned data. Defaults to "".
    Returns:
        dict()| object: data cleaned
    """
    try:
        # dict of regexes

        for r in cleaner.items():
            if r[0] in dict(response):
                # match each and keep the match if found
                match = re.search(r[1], str(dict(response)[r[0]]))
                value = "-"
                if match and match.group(0) != "":
                    value = match.group(0)
                    if to_add != "":
                        value = "".join([value, to_add])
                # response can be object or dict
                if isinstance(response, dict):
                    response[r[0]] = value
                else:
                    setattr(response, r[0], value)
    except Exception as e:
        print("clean_response_regex error" + repr(e))

    return response


def clean_response_strips(strips, response):
    """cleans a response using a list of strings to strip

    Args:
        strips ([str]): list of strings to strip
        response (str): data to clean

    Returns:
        str: data cleaned
    """

    # cut strips from response
    for s in strips:
        while response.find(s) != -1:
            response = response.replace(s, "")
    # remove leading and trailing spaces
    while response[0] in [",", ":", ".", ";", " ", '"', "'", "\\n"]:
        response = response[1:]
    # replace is for json format
    response = response.replace('"', r"\"")
    return response


def regex_extract(searches: dict, table):
    """extract via regex from the table

    Args:
        searches (dict()): dict of searches
        table (pandas.dataframe): table to search on
        costi (dict()): return dictionary

    Returns:
        dict(): dict with value extracted for each search
    """
    ret = dict()
    intermediate = dict()
    # for each search find it via regex in first column and then extract the value in last column
    for key, search in searches.items():
        once = True
        intermediate.update(dict([(key, "")]))
        for a in range(1, len(table.index)):
            # if found in first column
            if search_reg(search, table.iloc[a, 0]):
                # handle multiple lines exception( exc_multiple_lines is a list of lists of strings, each list is a different exception)
                if key in exc_multiple_lines and once:
                    once = False
                    exc = handle_exc(
                        table,
                        a,
                        search,
        ***REMOVED***
                    intermediate.update(dict([(key, exc)]))
                    continue
                # add last column
                updated = " ".join([intermediate[key], str(table.iloc[a, -1])])
                intermediate.update(dict([(key, updated)]))

    # for each search divide the value in min and max and add to dict
    for field, value in intermediate.items():
        if field in searches:
            groups = divide_regex(value)
            # returning 0 and -1 takes care of all cases anyway
            ret.update(dict([(field + "_min", groups[0]), (field + "_max", groups[-1])]))
        else:
            ret.update(dict([(field, value)]))
    return ret


def handle_exc(table, a, search):
    """handles exceptions where table may be split in multiple lines, looks until next search is found

    Args:
        table (pd.dataframe): table to look into
        a (int): index we are at
        search (str): what we are looking for
        language (str): language of the document

    Returns:
        str: values found
    """
    ret = ""
    # while not at the end and we didn't find the next search join the values
    while a < len(table.index) and (not search_reg(search, table.iloc[a, 0])):
        ret = " ".join([ret, str(table.iloc[a, -1])])
        a = a + 1
    return ret


def create_pydantic_class(field_info):
    fields = {
        field['name']: (field['type'], Field(default_factory=lambda: field['default'], description = field.get('extra_description') or field['description']
))
        for field in field_info
    ***REMOVED***
    return type('DynamicModel', (BaseModel,), fields)