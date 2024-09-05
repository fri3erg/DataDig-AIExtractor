from collections import defaultdict
from datetime import date
from pydantic import Field, create_model
from typing import Any, Dict, List, Optional, get_origin
from ...classes.Extracted import ExtractedField
from ...classes.Template import Template, TemplateTable, TemplateField
from ...configs.basic_tags import ExtractedTable


def select_desired_page(text, words_repr):
    """Select the page with the most occurrences of the words in words_repr.

    Args:
        text (lst): list of pages text
        words_repr (lst): list of words to look for in the pages provided

    Returns:
        str: page nindex with the most occurrences of the words in words_repr
    """
    counter = defaultdict(int)

    for i, page in enumerate(text):

        # Remove punctuation and replace \n with space
        content = page.lower().replace("\n", " ")
        for word in words_repr:
            # count how many times the word is in the page
            counter[str(i)] += content.count(word)

    # Page with most occurrences
    if len(counter) == 0:
        return 0, 0
    pg_number = max(counter, key=lambda i: counter[i])

    return pg_number, counter[pg_number]


def select_desired_table(tables, words_repr):
    """Select the table with the most occurrences of the words in words_repr.

    Args:
        tables (lst): list of df tables
        words_repr (lst): list of words to look for in the pages provided

    Returns:
        int or None: table index with the most occurrences of the words in words_repr,
                     or None if no words were found.
    """
    counter = defaultdict(int)

    for i, table in enumerate(tables):
        for word in words_repr:
            lower_word = word.lower()
            counter[str(i)] += table.applymap(lambda cell: str(cell).lower().count(lower_word)).sum().sum()

    # Check if counter is empty
    if len(counter) == 0:
        return None  # Return None if no words were found

    # Find the index with the highest count
    tb_number = max(counter.items(), key=lambda item: item[1])[0]

    return tb_number


def num_tokens_from_string(string: str | List[str], encoding_name: str = "gpt-4") -> int:
    """
    Returns the number of tokens in a given string using the specified encoding.

    Args:
        string (str): The input string to count tokens from.
        encoding_name (str, optional): The name of the encoding to use. Defaults to 'cl100k_base'.

    Returns:
        int: The number of tokens in the input string.
    """
    # encoding = tiktoken.get_encoding(encoding_name)
    # num_tokens = len(encoding.encode(string))
    # return num_tokens
    average_token_length = 4
    # Add a small buffer to account for potential special characters or word endings
    buffer = 0.1
    if isinstance(string, list):
        tokens = [int(len(s) * (1 + buffer) / average_token_length) for s in string]
        return sum(tokens)
    return int(len(string) * (1 + buffer) / average_token_length)




"""def is_in_text(pattern, text) -> bool:
    returns True if the pattern is found in the text

    Args:
        pattern (str): pattern to search
        text (str): text to search in

    Returns:
        bool: if the pattern is found in the text
    

    pattern = re.compile(pattern, re.IGNORECASE)
    ret = bool(pattern.search(text))
    return ret
"""

"""def search_in_pattern_in_text(pattern, text, pattern_inside):
    searches for a pattern inside a pattern in a text

    Args:
        pattern (str): initial pattern
        text (str): text to search in
        pattern_inside (str): pattern of the value to return

    Returns:
        str: value found
    
    match = re.search(pattern, text, re.IGNORECASE)
    if not match:
        return
    match_final = re.findall(pattern_inside, match.group(0), re.IGNORECASE)
    if not match_final:
        return
    return match_final[-1]
"""

"""def set_flag(extraction, pattern):
    sets a flag if the pattern is found in the extraction

    Args:
        extraction (str): to search in
        pattern (str): regex pattern

    Returns:
        bool: if the pattern is found in the extraction
    

    if is_in_text(pattern, extraction):
        extraction = True
    else:
        extraction = False

    return extraction
"""

"""def filter_list_by_pattern(extraction, pattern):
    filter the list leaving only the pattern

    Args:
        extraction (List): to clean
        pattern (str): regex pattern

    Returns:
        List: cleaned list
   

    searches = []
    for str in extraction:
        search = re.search(pattern, str)
        if search:
            searches.append(search.group(1))
    extraction = searches if searches else ["not found"]
    return extraction
 """

"""def extract_between(text, start, end):
    extracts the text between two strings

    Args:
        text (str): text to search in
        start (str): start of where to look
        end (str): end of where to look

    Returns:
        str: text in between
    
    pattern = f"(\\n)?\s*{re.escape(start)***REMOVED***\s*(\\n)?\s*(\S.*?)\s*(?={re.escape(end)***REMOVED***)"
    matches = re.findall(pattern, text, re.IGNORECASE)
    return matches[0][-1] if matches and matches[0] else None
"""

"""def extract_regex_text(pattern, value_to_search, page, boolean_to_check=None, str_to_check=None):
    Extracts the text from the page and checks if the regexes are present in the text.

    Returns:
        dict(): dictionary containing the callable
    
    if boolean_to_check is None:
        boolean_to_check = {***REMOVED***
    if str_to_check is None:
        str_to_check = {***REMOVED***
    try:
        text = getattr(page, "page_content", "").replace("\n", " ")
        for key in boolean_to_check.keys():
            boolean_to_check[key] = 1 if is_in_text(pattern[key], text) else 0

        for key in str_to_check.keys():
            found = search_in_pattern_in_text(pattern[key], text, value_to_search[key])
            if found:
                str_to_check[key] = found

    except Exception as error:
        print("extract_regex_text error" + repr(error))
        str_to_check = {
            key: (str_to_check[key] if str_to_check.get(key) is not None else "ERROR") for key in str_to_check.keys()
        ***REMOVED***

    return {**boolean_to_check, **str_to_check***REMOVED***
"""

"""def format_pages_num(arr):
    return the list as the function wants,

    Args:
        arr (_type_): _description_

    Returns:
        str: strified list
    
    if not arr:
        return None
    if isinstance(arr, str):
        return arr
    if isinstance(arr, int):
        return str(arr)

    arr = sorted(set(arr))  # Sort the array and remove duplicates
    ranges = []
    start = arr[0]
    end = arr[0]

    for i in range(1, len(arr)):
        if arr[i] - arr[i - 1] == 1:
            end = arr[i]
        else:
            if start == end:
                ranges.append(str(start))
            else:
                ranges.append(f"{start***REMOVED***-{end***REMOVED***")
            start = arr[i]
            end = arr[i]

    # Add the last range or number
    if start == end:
        ranges.append(str(start))
    else:
        ranges.append(f"{start***REMOVED***-{end***REMOVED***")

    return ",".join(ranges)


def check_valid(main_table, other_tables):
    return all(not main_table.equals(other) for other in other_tables) and isinstance(main_table, pd.DataFrame)

"""
"""
    def divide_regex(text):
        Divide a text in two parts using a regex.
    
        Args:
            text (str): text to divide
    
        Returns:
            tuple(str,str): tuple containing the two parts of the text
        
        # gets the 2 numbers
        divided = [x.group() for x in re.finditer("\d+[\.,]?\d*", text, re.IGNORECASE)]
        if divided.__len__() != 0:
            return divided
        return ["-"]
"""


"""def search_reg(search, text):
    Search a regex in a text and return the match if found.

    Args:
        language (str): language associated to regex
        regex (str): regex to search
        text (str): text to search in

    Returns:
        str: match if found
    
    match = re.search(search, text, re.IGNORECASE)
    return match is not None
"""

# TO REVIEW
"""def clean_response_regex(cleaner: dict, response, to_add=""):
    cleans a response using a regex

        Args:
        cleaner (dict): dict of keys and string of regex cleaner
        response (dict()| object): data to clean
        to_add (str, optional): string to add to the cleaned data. Defaults to "".
    Returns:
        dict()| object: data cleaned
    
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

"""
"""def clean_response_strips(strips, response):
    cleans a response using a list of strings to strip

    Args:
        strips ([str]): list of strings to strip
        response (str): data to clean

    Returns:
        str: data cleaned
    

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


exc_multiple_lines = []

"""
"""def regex_extract(searches: dict, table):
    extract via regex from the table

    Args:
        searches (dict()): dict of searches
        table (pandas.dataframe): table to search on
        costi (dict()): return dictionary

    Returns:
        dict(): dict with value extracted for each search
    
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
"""

"""def handle_exc(table, a, search):
    handles exceptions where table may be split in multiple lines, looks until next search is found

    Args:
        table (pd.dataframe): table to look into
        a (int): index we are at
        search (str): what we are looking for
        language (str): language of the document

    Returns:
        str: values found
    
    ret = ""
    # while not at the end and we didn't find the next search join the values
    while a < len(table.index) and (not search_reg(search, table.iloc[a, 0])):
        ret = " ".join([ret, str(table.iloc[a, -1])])
        a = a + 1
    return ret
"""

def create_pydantic_class(template: Template):
    """Creates a Pydantic model based on the provided Template object."""

    fields = {***REMOVED***
    for template_field in template.fields:
        pydantic_type = get_pydantic_type(template_field.type)

        description = template_field.extra_description or template_field.description or ""
        if template_field.required:
            description += " (required)"
        if template_field.type == "date":
            description += "this field searches for a date, (format: YYYY-MM-DD, numbers)"

        default = get_typed_default(template_field.default, pydantic_type)

        if template_field.list:
            pydantic_type = List[pydantic_type]

        # Use Optional for non-required fields
        if not template_field.required:
            pydantic_type = Optional[pydantic_type]

        # Create the Pydantic field
        fields[template_field.title] = (pydantic_type, Field(default=default, description=description))

    model = create_model("DynamicModel", **fields)
    return model


def create_intelligent_pydantic_class(template: Template):
    """Creates a Pydantic model based on the provided Template object."""

    fields = {***REMOVED***
    for template_field in template.fields:
        field_type: type = get_pydantic_type(template_field.type)
        description = ""
        if template_field.required:  # Assuming TemplateField has a 'required' attribute
            description = "(required)"
        if template_field.type == "date":
            description += " (format: YYYY-MM-DD)"
        default = get_typed_default(template_field.default, field_type)
        if template_field.list:
            field_type = List[field_type]
        fields[template_field.title] = (Optional[field_type], Field(description=description, default=default))

    model = create_model("DynamicModel", **fields)  # Use create_model for dynamic creation
    return model


def create_pydantic_table_class(template: TemplateTable):
    """Creates a Pydantic model based on the provided Template object."""
    if template.all or (template.rows== [] and template.columns == []):
        return ExtractedTable
    if template.rows == []:
        return pydantic_table_listed_column(template)
    if template.columns == []:
        return pydantic_table_listed_row(template)

    fields = {***REMOVED***
    for row in template.rows:
        for column in template.columns:
            type_from = next(
                (t for t in [row.type, column.type] if t and t != "Text"),
                "Text")    # Default if both are None or "Text"         
            field_type: type = get_pydantic_type(type_from)
            required = column.required or row.required
            description = f"row:{row.title***REMOVED***|column:{column.title***REMOVED***|"
            default = get_typed_default("N/A", field_type)
            if required:  # Assuming TemplateField has a 'required' attribute
                description += "(required)"
            fields[description] = (Optional[field_type] | str, Field(default=default, description=description))
    title_field = {
        "title": (str, Field(description="what the table is about", default="title", required=True)),
    ***REMOVED***
    fields.update(title_field)

    model = create_model("DynamicModel", **fields)  # Use create_model for dynamic creation
    return model


def pydantic_table_listed_row(template: TemplateTable):
    """Creates a Pydantic model based on the provided Template object. based on only the rows"""
    fields = {***REMOVED***
    for row in template.rows:
        type_from = row.type or "str"
        field_type: type = get_pydantic_type(type_from)
        required = row.required
        description = f"row:{row.title***REMOVED***"
        if required:  # Assuming TemplateField has a 'required' attribute
            description += "(required)"
        fields[description] = (Optional[List[field_type]], Field(default=[], description=description))
    title_field = {
        "title": (str, Field(description="what the table is about", default="title", required=True)),
    ***REMOVED***
    fields.update(title_field)

    model = create_model("DynamicModelTableRows", **fields)  # Use create_model for dynamic creation
    return model


def pydantic_table_listed_column(template: TemplateTable):
    """Creates a Pydantic model based on the provided Template object. based on only the column"""
    fields = {***REMOVED***
    for column in template.columns:
        type_from = column.type or "str"
        field_type: type = get_pydantic_type(type_from)
        required = column.required
        description = f"column:{column.title***REMOVED***"
        if required:  # Assuming TemplateField has a 'required' attribute
            description += "(required)"
        fields[description] = (Optional[List[field_type]], Field(default=[], description=description))
    title_field = {
        "title": (str, Field(description="what the table is about", default="title", required=True)),
    ***REMOVED***
    fields.update(title_field)

    model = create_model("DynamicModelTableColumns", **fields)  # Use create_model for dynamic creation
    return model


def get_pydantic_type(type: str | None) -> type:
    """Returns the Pydantic type that corresponds to the specified type string.

    Args:
        type: The type string to convert to a Pydantic type.

    Returns:
        type: The Pydantic type that corresponds to the specified type string.
    """
    if type == "Text":
        return str
    elif type == "Int":
        return int
    elif type == "Float":
        return float
    elif type == "Boolean":
        return bool
    elif type == "Date":
        return str
    else:
        return str


def get_typed_default(default: Optional[str], pydantic_type: type) -> Any:
    """
    Returns a typed default value based on the provided Pydantic type.

    Args:
        default (str): The default value to be typed.
        pydantic_type (type): The Pydantic type to which the default value should be converted.

    Returns:
        Any: The typed default value.
    """
    if default is None:
        return default
    elif pydantic_type == str:
        return default
    elif pydantic_type == int:
        try:
            return int(default)
        except ValueError:
            # Handle the conversion error (e.g., log it or return a specific default)
            print(f"Warning: Failed to convert '{default***REMOVED***' to int. Using 0 as default.")
            return -1

    elif pydantic_type == float:
        try:
            return float(default)
        except ValueError:
            # Handle the conversion error
            print(f"Warning: Failed to convert '{default***REMOVED***' to float. Using -1.0 as default.")
            return -1.0
    elif pydantic_type == bool:
        try:
            return bool(default)
        except ValueError:
            # Handle the conversion error
            print(f"Warning: Failed to convert '{default***REMOVED***' to bool. Using False as default.")
            return False
    elif pydantic_type == date:
        return default
    elif get_origin(pydantic_type) is list:
        return []
    else:
        return default


def extracted_from_pydantic(template: Template, tagged) -> List[ExtractedField]:
    """Extracts the fields from a tagged object.

    Args:
        tagged: Tagged object to extract fields from.

    Returns:
        List[ExtractedField]: List of extracted fields.
    """
    extracted = []
    for field in tagged.__fields__:
        value = getattr(tagged, field, "N/A")
        if isinstance(value, list):

            value = [str(v).replace("|", "\\") for v in value]
            value = "| ".join(value)
        else:
            value = str(value).replace("|", "\\")
        matching_template_field: TemplateField | None = next((tf for tf in template.fields if tf.title == field), None)
        if matching_template_field:
            extracted.append(ExtractedField(value=value, template_field=matching_template_field))
        else:
            print(f"Field '{field***REMOVED***' not found in template '{template.title***REMOVED***'")
    return extracted


def extracted_from_pydantic_table(
    tagged_data, template: TemplateTable
) -> tuple[Dict[str, Dict[str, ExtractedField]], str]:
    """Extracts structured data from a tagged Pydantic model into a matrix."""

    result_matrix: Dict[str, Dict[str, ExtractedField]] = {***REMOVED***

    title = getattr(tagged_data, "title", "title")
    if getattr(tagged_data, "title", None) is not None:
        del tagged_data.title
    # Initialize empty dicts for each row
    if template.all:
        return extracted_from_all(tagged_data), title

    if getattr(getattr(tagged_data, "__class__", None), "__name__", None) == "DynamicModelTableRows":
        return extracted_from_rows(tagged_data, template), title
    if getattr(getattr(tagged_data, "__class__", None), "__name__", None) == "DynamicModelTableColumns":
        return extracted_from_columns(tagged_data, template), title

    for row in template.rows:
        result_matrix[row.title] = {***REMOVED***

    # Parse field names and create ExtractedField instances
    for field_name, value in tagged_data.__dict__.items():
        # Extract row and column names from field name
        row_name, col_name, _ = field_name.split("|")
        row_name = row_name[4:]
        col_name = col_name[7:]

        # Find matching row and column in template
        matching_row = next((r for r in template.rows if r.title in row_name), None)
        matching_col = next((c for c in template.columns if c.title in col_name), None)

        if matching_row and matching_col:
            # Create ExtractedField instance
            extracted_field = ExtractedField(
                template_field=matching_col,  # Use the column as the template field
                value=value,
***REMOVED***

            # Store in result matrix
            result_matrix[matching_row.title][matching_col.title] = extracted_field

    return result_matrix, title


def extracted_from_all(tagged_data: ExtractedTable) -> Dict[str, Dict[str, ExtractedField]]:
    result_matrix: Dict[str, Dict[str, ExtractedField]] = {***REMOVED***

    # Iterate over rows and columns
    for row_idx, row in enumerate(tagged_data.table_data):
        row_name = f"row_{row_idx + 1***REMOVED***"  # Generate row name (e.g., "row_1", "row_2")
        result_matrix[row_name] = {***REMOVED***
        for col_idx, value in enumerate(row):
            col_name = f"col_{col_idx + 1***REMOVED***"  # Generate column name (e.g., "col_1", "col_2")
            result_matrix[row_name][col_name] = ExtractedField(
                template_field=None, value=value  # No template matching needed
***REMOVED***

    return result_matrix


def extracted_from_rows(tagged_data, template: TemplateTable) -> Dict[str, Dict[str, ExtractedField]]:
    result_matrix: Dict[str, Dict[str, ExtractedField]] = {***REMOVED***

    for field_name, value in tagged_data.__dict__.items():
        # Extract row and column names from field name
        row_name = field_name[4:]
        result_matrix[row_name] = {***REMOVED***

        # Find matching row and column in template
        matching_row = next((r for r in template.rows if r.title in row_name), None)
        if matching_row:
            # Create ExtractedField instance
            for idx, val in enumerate(value):
                result_matrix[row_name].update(
                    {
                        "col_ "
                        + str(idx + 1): ExtractedField(
                            template_field=matching_row,  # Use the column as the template field
                            value=val,
            ***REMOVED***
                    ***REMOVED***
    ***REMOVED***
    return result_matrix


def extracted_from_columns(tagged_data, template: TemplateTable) -> Dict[str, Dict[str, ExtractedField]]:

    result_matrix: Dict[str, Dict[str, ExtractedField]] = {***REMOVED***

    # Initialize rows in the result_matrix
    first_column_values = next(iter(tagged_data.__dict__.values()))
    for idx in range(len(first_column_values)):
        result_matrix["row_" + str(idx + 1)] = {***REMOVED***

    for field_name, value in tagged_data.__dict__.items():
        col_name = field_name[7:]
        matching_col = next((c for c in template.columns if c.title in col_name), None)
        if matching_col:
            for idx, val in enumerate(value):
                result_matrix["row_" + str(idx + 1)].update(
                    {
                        col_name: ExtractedField(
                            template_field=matching_col,
                            value=val,
            ***REMOVED***
                    ***REMOVED***
    ***REMOVED***

    return result_matrix


def sanitize_text(text: str) -> str:
    """
    Sanitizes a given text by escaping characters that are not allowed in JSON format.
    
    Args:
        text (str): The text to be sanitized.
        
    Returns:
        str: The sanitized text with necessary characters escaped for JSON compatibility.
    """
    
    dangerous_chars = {
        '"': '\\"',   # Escape double quotes
        '\\': '\\\\', # Escape backslashes
        '\n': '\\n',  # Escape newline
        '\r': '\\r',  # Escape carriage return
        '\t': '\\t',  # Escape tabs
        '\b': '\\b',  # Escape backspace
        '\f': '\\f'   # Escape form feed
    ***REMOVED***
    
    sanitized_text = ""
    
    # Replace dangerous characters with their escaped equivalents
    for char in text:
        if char in dangerous_chars:
            sanitized_text += dangerous_chars[char]
        elif ord(char) < 0x20:  # Exclude other control characters below ASCII 32
            continue
        else:
            sanitized_text += char

    return sanitized_text