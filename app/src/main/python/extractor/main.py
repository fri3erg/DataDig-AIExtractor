
from extractors.general_extractors.custom_extractors.kid.insurance.kid.kid_extractor import DataExtractor
# from extractors.Derivati.Spot_KID_extractor import write_info
# from extractors.Derivati.Global_KID_extractor import GlobalExtractor
from extractors.models import Models
import logging
import base64
from PIL import Image
from io import BytesIO
import asyncio
import pandas as pd
from typing import Callable
from classes.Template import Template, TemplateField, TemplateTable
from test.variables import images
from dotenv import load_dotenv
import os


# Now use the 'api_key' and 'db_password' variables in your code



logging.basicConfig(filename="logging.log", encoding="utf-8", level=logging.DEBUG)




def main(base64_images : list , template: Template, progress_callback: Callable, model: str, language:str|None= None):
    """main loop, instanciate 10 async process(more causes errors) for 10 files at the time,
    puts results in an excel file

    Args:
        doc_folder (str): folder containing the pdf files
    """
    try:
        #env_setter = EnvVarSetter(tenant="insurance")
        #env_setter.configure_local_env_vars()
        
        images_data = [base64.b64decode(base64_image) for base64_image in base64_images]
        images = [Image.open(BytesIO(image_data)) for image_data in images_data]

        # testing
        batch_size = 5
        parallel = True
        # list all the pdf files in the folder
        print("START")

        extractions = {***REMOVED***
        try: 
            extractor= DataExtractor(images, template,progress_callback, language, model) 
            
            extractions= asyncio.run(extractor.process())
    
    
        except Exception as error:
            print("process_file error" + repr(error))


        # give request id
        # orders results
        results = pd.DataFrame(extractions)
        # ordered = [col for col in column_order[file_type] if col in results.columns]
        # results = results[ordered]
        #excel_path = os.path.join(doc_folder + ".xlsx")
        # saves results
        #results.to_excel(excel_path, header=True, index=False)
        print(results)

    except Exception as error:
        print("top level error:" + repr(error))




def create_test_template():
    """Creates a test instance of the Template class with sample data."""

    # Create sample TemplateField objects
    field1 = TemplateField(1, "Name", "Enter your full name", "", ["personal", "identification"],str,True)
    field2 = TemplateField(2, "Email", "Provide your email", "", ["contact", "personal"],str,True)
    field3 = TemplateField(3, "Date of Birth", "Your birthdate (YYYY-MM-DD)", "", ["personal", "date"],str,True)

    # Create sample TemplateTable objects
    table1 = TemplateTable(1, "Personal Information", ["personal", "info"], [field1, field2, field3])

    # Create the Template instance
    template = Template(
        1,
        "Basic Information Template",
        "Collects essential personal details",
        [field1, field2, field3],  # Fields directly associated with the template
        [],         # Tables within the template
        ["general", "personal"],
    )

    return template

def fake_callback(progress):
    print(progress)

***REMOVED***

    
    template :Template = create_test_template()

    # for root, dirs, files in os.walk(folder):
    # Check if there are PDF files in the current directory
    # if any(file.endswith(".pdf") for file in files):
    # Call your existing function to process PDFs in the folder
    main(images, template, fake_callback, "gpt-3.5-turbo")
