
from datetime import date

import dotenv
import os
from regex import E
from extractor.classes.Extracted import Extracted
from extractor.classes.Options import ExceptionsExtracted, Options
# from extractors.Derivati.Spot_KID_extractor import write_info
# from extractors.Derivati.Global_KID_extractor import GlobalExtractor
import logging
import base64
import asyncio
from typing import Callable, List
from extractor.classes.Template import Template, TemplateField, TemplateTable
from extractor.scanner.extractors.main_extractor.extractor import MainExtractor
from extractor.test.variables import images
from extractor.configs.configs import keys_config


# Now use the 'api_key' and 'db_password' variables in your code







def main(base64_images : list,text : list[str], template: Template, progress_callback: Callable, options: Options):
    """main loop, instanciate 10 async process(more causes errors) for 10 files at the time,
    puts results in an excel file

    Args:
        doc_folder (str): folder containing the pdf files
    """
    exceptions_occurred: List[ExceptionsExtracted] = []
    try:
        #env_setter = EnvVarSetter(tenant="insurance")
        #env_setter.configure_local_env_vars()
        images: list[bytes] = [bytes(image, 'utf-8') for image in list(base64_images)]  
        # testing
        batch_size = 5
        parallel = True
        extractor:MainExtractor | None = None
        # list all the pdf files in the folder
        print("START")

        extraction: Extracted | None = None
        
        try:
            extractor= MainExtractor(images,text, template,progress_callback, options)
            
        except Exception as error:
            print("initialization error" + repr(error))
            exceptions_occurred.append(ExceptionsExtracted(error=error, error_location="initialization",error_description=repr(error)))
        
        try:
            if extractor:
                extraction= asyncio.run(extractor.process())
    
    
        except Exception as error:
            exceptions_occurred.append(ExceptionsExtracted(error=error, error_location="main phase",error_description=repr(error)))
            print("main phase error" + repr(error))


        # give request id
        # orders results
        #results = pd.DataFrame(extractions)
        # ordered = [col for col in column_order[file_type] if col in results.columns]
        # results = results[ordered]
        #excel_path = os.path.join(doc_folder + ".xlsx")
        # saves results
        #results.to_excel(excel_path, header=True, index=False)
        if extraction:
            extraction.add_exceptions(exceptions_occurred)
        print(extraction)
        return extraction

    except Exception as error:
        print("top level error:" + repr(error))
        return Extracted(template, exceptions=exceptions_occurred)

def create_test() -> tuple[Template, Options]:
    """Creates a test instance of the Template class with sample data."""

    # Create sample TemplateField objects
    field1 = TemplateField( "Name", "Enter your full name", "", ["personal", "identification"],str,True)
    field2 = TemplateField( "Email", "Provide your email", "", ["contact", "personal"],str,True)
    field3 = TemplateField( "Date of Birth", "Your birthdate (YYYY-MM-DD)", "", ["personal", "date"],date,True)

    # Create sample TemplateTable objects
    table1 = TemplateTable( "Personal Information", ["personal", "info"],"table that describes personal info", [field1, field2, field3], [field1, field2, field3])
    table2 = TemplateTable( "Contact Information", ["contact", "info"],"", [field1, field2],[field1, field2, field3])

    # Create the Template instance
    template = Template(
        "Basic Information Template",
        "Collects essential personal details",
        [field1, field2, field3],  # Fields directly associated with the template
        [table1,table2],         # Tables within the template
        ["general", "personal"],
    )
    
    option= Options(model="gpt-3.5-turbo",azure_ocr=True)

    return template, option

def fake_callback(progress):
    print(progress)
    
    
    
def main_kotlin(base64_images : list , text:list[str],template: Template, progress_callback: Callable, options: Options):
        
    from java import jclass

    class AndroidLogHandler(logging.Handler):
        def emit(self, record):
            Log = jclass('android.util.Log') # Get the android Log class
            
    logging.basicConfig(level=logging.DEBUG, handlers=[AndroidLogHandler()])
    
    for key, item in keys_config.items():
        print(options.get_api_key(item))
        os.environ[key] = options.get_api_key(item)
        print(key, os.environ[key])
    
    return main(base64_images,text, template, progress_callback, options)

***REMOVED***

    
    template, option= create_test()
    text=[
        "This is a test"
    ]
    
    
    logging.basicConfig(filename="logging.log", encoding="utf-8", level=logging.DEBUG)

    # for root, dirs, files in os.walk(folder):
    # Check if there are PDF files in the current directory
    # if any(file.endswith(".pdf") for file in files):
    # Call your existing function to process PDFs in the folder
    main(images,text, template, fake_callback, option)


        
        