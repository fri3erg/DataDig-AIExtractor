import re
from typing import Dict, List, Literal, Optional
from classes.Extracted import ExtractedField
from classes.Template import Template, TemplateField, TemplateTable
from extractors.general_extractors.utils import divide_regex
from extractors.general_extractors.utils import search_reg
from pydantic import BaseModel, Field, create_model


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
            extracted.append(ExtractedField(value=value, template_field=matching_template_field, model_used=self.options.model))
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
                model_used=self.options.model
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
