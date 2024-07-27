from collections import defaultdict
import os
import re
from tempfile import NamedTemporaryFile
import uuid
from langchain_community.document_loaders import PyPDFLoader
from pydantic import BaseModel, Field, create_model
from typing import Dict, List, Optional
from PIL import Image
from io import BytesIO
from ...classes.Extracted import ExtractedField
from ...classes.Template import Template, TemplateTable, TemplateField
import pytesseract
from pdf2image import convert_from_bytes
from langchain_community.document_loaders import UnstructuredExcelLoader
#import tiktoken
import pandas as pd


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
        if page == "":
            continue

        # Remove punctuation and replace \n with space
        content = page.lower().replace("\n", " ")
        for word in words_repr:
            # count how many times the word is in the page
            counter[str(i)] += content.count(word)

    # Page with most occurrences
    pg_number = max(counter, key=lambda page: counter[page])

    return pg_number, counter[pg_number]


def select_desired_table(tables, words_repr):
    """Select the table with the most occurrences of the words in words_repr.

    Args:
        tables (lst): list of df tables
        words_repr (lst): list of words to look for in the pages provided

    Returns:
        int: table index with the most occurrences of the words in words_repr
    """
    counter = defaultdict(int)
    # Search all the tables
    for i, table in enumerate(tables):
        for word in words_repr:
            # print(word)
            # print(table.apply(lambda col:col.str.count(word, flags=re.IGNORECASE)).sum().sum())

            counter[str(i)] += table.apply(lambda col: col.str.count(word, flags=re.IGNORECASE)).sum().sum()

    # Page with most occurrences
    tb_number = max(counter, key=counter.get)
    return tb_number


def select_desired_table_only_header(tables, words_repr):  # change from normal is that it only looks at the header
    """Select the table with the most occurrences of the words in words_repr.

    Args:
        tables (lst): list of df tables
        words_repr (lst): list of words to look for in the pages provided

    Returns:
        int: table index with the most occurrences of the words in words_repr
    """
    new_tables = [table.iloc[0].to_frame() for table in tables]

    counter = defaultdict(int)
    # Search all the tables
    for i, table in enumerate(new_tables):
        for word in words_repr:
            # print(word)
            # print(table.apply(lambda col:col.str.count(word, flags=re.IGNORECASE)).sum().sum())

            counter[str(i)] += table.apply(lambda col: col.str.count(word, flags=re.IGNORECASE)).sum().sum()

    # Page with most occurrences
    tb_number = max(counter, key=counter.get)
    return tb_number


# unused
def upload_df_as_excel(df: pd.DataFrame):
    """Upload DF as excel file for LargeLanguageModel analysis.

    Args:
        df (pd.DataFrame): dataframe to upload

    Returns:
        str: path of the uploaded file
    """

    if os.environ.get("ENV") == "local":
        tmp_path = "tmp"
    else:
        tmp_path = "/tmp"
    # Modify empty cells with " " to avoid upload errors
    df = df.replace(to_replace="", value=" ")
    df.fillna(" ", inplace=True)

    # Save table to excel and upload it back
    random_file_name = str(uuid.uuid4()) + ".xlsx"
    save_name = os.path.join(tmp_path, random_file_name)
    df.to_excel(save_name, index=False, header=False)
    loader = UnstructuredExcelLoader(save_name)
    loaded_table = loader.load()
    os.remove(save_name)

    return loaded_table


def num_tokens_from_string(string: str|List[str], encoding_name: str = "gpt-4") -> int:
    """
    Returns the number of tokens in a given string using the specified encoding.

    Args:
        string (str): The input string to count tokens from.
        encoding_name (str, optional): The name of the encoding to use. Defaults to 'cl100k_base'.

    Returns:
        int: The number of tokens in the input string.
    """
    #encoding = tiktoken.get_encoding(encoding_name)
    #num_tokens = len(encoding.encode(string))
    #return num_tokens
    average_token_length = 4
    # Add a small buffer to account for potential special characters or word endings
    buffer = 0.1  
    if isinstance(string, list):
        tokens = [int(len(s) * (1 + buffer) / average_token_length) for s in string]
        return sum(tokens)
    return int(len(string) * (1 + buffer) / average_token_length)


def is_more_number(text):
    """Check if a text in the page is composed  more than 30% of numbers

    Args:
        text (str): text to check

    Returns:
        Bool: if the text is composed more than 30% of numbers
    """

    letter_count = sum(1 for char in text if char.isalpha())
    number_count = sum(1 for char in text if char.isnumeric())
    ratio = number_count / (number_count + letter_count + 1)
    if ratio > 0.3:
        return True
    else:
        return False


def _decode_first_condtition(text):
    """Decode the first condition

    Args:
        text (str): text to decode

    Returns:
        str: decoded text
    """
    return "".join(
        char
        for char in text
        if char.isalnum() or char.isspace() or char in [",", ".", "€", "%", ":", ";", "(", ")", "-", "/"]
    )


def _decode_second_condition(text):
    """Decode the second condition

    Args:
        text (str): text to decode

    Returns:
        str: decoded text
    """
    content = text.split("/")
    return "".join([chr(int(code)) for code in content if code and 32 <= int(code) <= 110000000])


def get_document_text_old(images):
    """returns the text in the page

    Args:
        file_name (path): _description_

    Returns:
        _type_: _description_
    """
    try:
        loader = PyPDFLoader(images)
        pages = loader.load()
        del loader

        # Decoding conditions
        first_page = pages[0].page_content
        first_encoded_condition = first_page.count("\x00") > 100
        second_encoded_condition = is_more_number(first_page)
        # Decode
        for page in pages:
            content = page.page_content
            if first_encoded_condition:
                page.page_content = _decode_first_condtition(content)
            elif second_encoded_condition:
                page.page_content = _decode_second_condition(content)
        return pages

    except Exception as error:
        print("get document text error" + repr(error))
        raise error




def get_document_text(images: list[bytes],language:str|None) -> List[str]:
    """Extracts text from a list of PIL Image objects or bytes (base64 images).

    Args:
        images: List of PIL Image objects or bytes representing base64 encoded images.

    Returns:
        str: Concatenated text extracted from all images.
    """
    is_chaquopy=False
    all_text = []
    try:
        import chaquopy  # Try importing Chaquopy
        print("here chaquopy")
        tess_exe_path =  os.environ.get("TESSDATA_PREFIX") or ""
        pytesseract.pytesseract.tesseract_cmd = tess_exe_path
        print(tess_exe_path)
        is_chaquopy=True
    except ImportError:
        # Not running in Chaquopy (e.g., standalone script)
        tess_exe_path = None  # Use the default Tesseract data path
        pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'
    tessdata_dir_config = f'--tessdata-dir "{tess_exe_path***REMOVED***/tessdata"' if tess_exe_path else ''

    if not is_chaquopy:
        for image in images:
            try:
                # Handle different image input types
                if isinstance(image, bytes):  # Directly handle bytes
                    print(image)
                    image = Image.open(BytesIO(image))
                    print("not here")
                    print(f"image format {image.format***REMOVED***")
                    if image.format != 'PNG':  # Example: converting to PNG
                        image = image.convert('PNG')
                elif not isinstance(image, Image.Image):
                    raise TypeError(
                        "Unsupported image type. Expected PIL Image or bytes (base64 image)."
        ***REMOVED***

                # ... (Your image preprocessing logic here) ...
                # (e.g., grayscale conversion, thresholding, etc.)

                # Perform OCR
                all_text.append(pytesseract.image_to_string(image, config=tessdata_dir_config, lang=language))

            except Exception as e:
                print(f"Error processing image: {e***REMOVED***")
                all_text.append("")  # Add empty string if an error occurs
    else:
        for image_bytes in images:
            print("here")
            with NamedTemporaryFile(suffix=".png") as temp:  # Assuming PNG format
                temp.write(image_bytes)
                temp.flush()
                print("here")
                text = pytesseract.image_to_string(temp.name, lang=language, config=tessdata_dir_config)
                all_text.append(text)

    return all_text



def get_document_text_pdf(file_path: str) -> List[str]:
    images:list[bytes] = convert_from_bytes(open(file_path, "rb").read())
    return get_document_text(images,"it")


def is_in_text(pattern, text) -> bool:
    """returns True if the pattern is found in the text

    Args:
        pattern (str): pattern to search
        text (str): text to search in

    Returns:
        bool: if the pattern is found in the text
    """

    pattern = re.compile(pattern, re.IGNORECASE)
    ret = bool(pattern.search(text))
    return ret


def search_in_pattern_in_text(pattern, text, pattern_inside):
    """searches for a pattern inside a pattern in a text

    Args:
        pattern (str): initial pattern
        text (str): text to search in
        pattern_inside (str): pattern of the value to return

    Returns:
        str: value found
    """
    match = re.search(pattern, text, re.IGNORECASE)
    if not match:
        return
    match_final = re.findall(pattern_inside, match.group(0), re.IGNORECASE)
    if not match_final:
        return
    return match_final[-1]


def set_flag(extraction, pattern):
    """sets a flag if the pattern is found in the extraction

    Args:
        extraction (str): to search in
        pattern (str): regex pattern

    Returns:
        bool: if the pattern is found in the extraction
    """

    if is_in_text(pattern, extraction):
        extraction = True
    else:
        extraction = False

    return extraction


def filter_list_by_pattern(extraction, pattern):
    """filter the list leaving only the pattern

    Args:
        extraction (List): to clean
        pattern (str): regex pattern

    Returns:
        List: cleaned list
    """

    searches = []
    for str in extraction:
        search = re.search(pattern, str)
        if search:
            searches.append(search.group(1))
    extraction = searches if searches else ["not found"]
    return extraction


def extract_between(text, start, end):
    """extracts the text between two strings

    Args:
        text (str): text to search in
        start (str): start of where to look
        end (str): end of where to look

    Returns:
        str: text in between
    """
    pattern = f"(\\n)?\s*{re.escape(start)***REMOVED***\s*(\\n)?\s*(\S.*?)\s*(?={re.escape(end)***REMOVED***)"
    matches = re.findall(pattern, text, re.IGNORECASE)
    return matches[0][-1] if matches and matches[0] else None


def extract_regex_text(pattern, value_to_search, page, boolean_to_check={***REMOVED***, str_to_check={***REMOVED***):
    """Extracts the text from the page and checks if the regexes are present in the text.

    Returns:
        dict(): dictionary containing the callable
    """
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


def format_pages_num(arr):
    """return the list as the function wants,

    Args:
        arr (_type_): _description_

    Returns:
        str: strified list
    """
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


def divide_regex(text):
    """Divide a text in two parts using a regex.

    Args:
        text (str): text to divide

    Returns:
        tuple(str,str): tuple containing the two parts of the text
    """
    # gets the 2 numbers
    divided = [x.group() for x in re.finditer("\d+[\.,]?\d*", text, re.IGNORECASE)]
    if divided.__len__() != 0:
        return divided
    return ["-"]


def search_reg(search, text):
    """Search a regex in a text and return the match if found.

    Args:
        language (str): language associated to regex
        regex (str): regex to search
        text (str): text to search in

    Returns:
        str: match if found
    """
    match = re.search(search, text, re.IGNORECASE)
    return match is not None



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

exc_multiple_lines=[]
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


def create_pydantic_class(template: Template):
    """Creates a Pydantic model based on the provided Template object."""

    fields = {***REMOVED***
    for template_field in template.fields:
        field_type = template_field.type or str
        description=template_field.extra_description or template_field.description
        if template_field.required:  # Assuming TemplateField has a 'required' attribute
            description +=" ,should be present,check well"
        fields[template_field.title] = (Optional[field_type]| str, Field(default='N/A',description=description))


    model = create_model("DynamicModel", **fields)  # Use create_model for dynamic creation
    return model

def create_pydantic_table_class(template: TemplateTable):
    """Creates a Pydantic model based on the provided Template object."""

    fields = {***REMOVED***
    for  row in template.rows:
        for column in template.columns:
            
            field_type:type = row.type or column.type or str
            required= column.required or row.required
            description=f"row:{row.title***REMOVED***|column:{column.title***REMOVED***|"
            
            if required:  # Assuming TemplateField has a 'required' attribute
                description +=",should be present,check well"
            fields[description] = (Optional[field_type]| str, Field(default="N/A",description=description))


    model = create_model("DynamicModel", **fields)  # Use create_model for dynamic creation
    return model

def create_intelligent_pydantic_class(template: Template):
    """Creates a Pydantic model based on the provided Template object."""

    fields = {***REMOVED***
    description= ""
    for template_field in template.fields:
        field_type:type = template_field.type or str
        if template_field.required:  # Assuming TemplateField has a 'required' attribute
            description="should be present,check well"
        fields[template_field.title] = (Optional[field_type], Field(description=description))


    model = create_model("DynamicModel", **fields)  # Use create_model for dynamic creation
    return model



def extracted_from_pydantic(self, tagged) -> List[ExtractedField]:
    """Extracts the fields from a tagged object.

    Args:
        tagged: Tagged object to extract fields from.

    Returns:
        List[ExtractedField]: List of extracted fields.
    """
    extracted = []
    for field in tagged.__fields__:
        value = getattr(tagged, field)
        matching_template_field:TemplateField | None = next(
            (tf for tf in self.template.fields if tf.title == field), None
        )  
        if matching_template_field:
            extracted.append(ExtractedField(value=value, template_field=matching_template_field))
        else:
            print(f"Field '{field***REMOVED***' not found in template '{self.template.title***REMOVED***'")
    return extracted


def extracted_from_pydantic_table(self,tagged_data, template: TemplateTable) -> Dict[str, Dict[str, ExtractedField]]:
    """Extracts structured data from a tagged Pydantic model into a matrix."""

    result_matrix: Dict[str, Dict[str, ExtractedField]] = {***REMOVED***

    # Initialize empty dicts for each row
    for row in template.rows:
        result_matrix[row.title] = {***REMOVED***

    # Parse field names and create ExtractedField instances
    for field_name, value in tagged_data.__fields__.items():  
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

    return result_matrix
    


def get_object_by_title(obj_list, target_title):
    """
    Finds and returns the first object in a list that has the specified title.

    Args:
        obj_list: The list of objects to search through.
        target_title: The title string to match.

    Returns:
        The object with the matching title, or None if no match is found.
    """
    for obj in obj_list:
        if hasattr(obj, 'title') and obj.title == target_title:
            return obj
    return None
