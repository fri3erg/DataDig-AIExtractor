from io import BytesIO
import io
import os
from threading import Thread
from extractor.classes.Extracted import Extracted
from extractor.classes.Options import ExceptionsExtracted, Options
import logging
import base64
from typing import List
from extractor.classes.Template import Template, TemplateField, TemplateTable
from extractor.scanner.extractors.main_extractor.extractor import MainExtractor
from extractor.configs.configs import keys_config
from PIL import Image


# Now use the 'api_key' and 'db_password' variables in your code


global_progress = 0.25


def main(encoded_images: list, text: list[str], template: Template, options: Options):
    """main loop, instanciate 10 async process(more causes errors) for 10 files at the time,
    puts results in an excel file

    Args:
        doc_folder (str): folder containing the pdf files
    """
    exceptions_occurred: List[ExceptionsExtracted] = []
    try:
        images: list[bytes] = [base64.b64decode(image) for image in list(encoded_images)]

        # images: list[bytes] = [bytes(image, 'utf-8') for image in list(encoded_images)]
        # testing
        extractor: MainExtractor | None = None
        # list all the pdf files in the folder
        print("START")

        extraction: Extracted | None = None

        try:
            extractor = MainExtractor(images, text, template, options)
            progress_callback(0.1)

        except Exception as error:
            print("initialization error:" + repr(error))
            exceptions_occurred.append(
                ExceptionsExtracted(error=error, error_location="initialization", error_description=repr(error))
***REMOVED***

        try:
            if extractor:
                progress_callback(0.2)
                progress = extractor.first_stage()
                progress_callback(progress or 0.6)
                extractor.second_stage()
                progress_callback(0.95)
                extraction = extractor.end_phase()
                progress_callback(0.99)

        except Exception as error:
            exceptions_occurred.append(
                ExceptionsExtracted(error=error, error_location="main phase", error_description=repr(error))
***REMOVED***
            print("main phase error" + repr(error))

        if extraction:
            extraction.add_exceptions(exceptions_occurred)
        else:
            extraction = Extracted(template, exceptions=exceptions_occurred)
        print(extraction)
        return extraction

    except Exception as error:
        print("top level error:" + repr(error))
        return Extracted(template, exceptions=exceptions_occurred)


def create_test() -> tuple[Template, Options]:
    """Creates a test instance of the Template class with sample data."""

    # Create sample TemplateField objects
    field1 = TemplateField("1", "a", "", "", "float", False, False)
    field2 = TemplateField("2", "b", "", "", "str", False, True, True)
    field3 = TemplateField("3", "c", "", "", "date", True, True)

    # Create sample TemplateTable objects
    table1 = TemplateTable(
        "1", "Personal Information", [], "table that describes personal info", [field1, field2, field3], [], True
    )
    table2 = TemplateTable("2", "Contact Information", ["contact", "info"], "", [], [field1, field2, field3], True)

    # Create the Template instance
    template = Template(
        "1",
        "Basic Information Template",
        "Collects essential personal details",
        [field1, field2, field3],  # Fields directly associated with the template
        [table1, table2],  # Tables within the template
    )

    def fake_keys(progress):
        return progress

    option = Options(model="gpt-4", language="auto-detect", azure_ocr=True, get_api_key=fake_keys)

    return template, option


def fake_callback(progress):
    print(progress)


def progress_callback(value: float):
    global global_progress
    global_progress = value


def main_kotlin(base64_images: list, text: list[str], template: Template, options: Options):

    from java import jclass

    class AndroidLogHandler(logging.Handler):
        def emit(self, record):
            Log = jclass("android.util.Log")  # Get the android Log class

    logging.basicConfig(level=logging.DEBUG, handlers=[AndroidLogHandler()])

    for key, item in keys_config.items():
        os.environ[key] = options.get_api_key(item) or ""
    if options.resize:
        base64_images= [reduce_image_size(image) for image in base64_images]

    return main(base64_images, text, template, options)


def reduce_image_size(base64_str, target_size_mb=4, initial_quality=85, min_quality=10):
    # Decode base64 string to bytes
    image_data = base64.b64decode(base64_str)
    image = Image.open(io.BytesIO(image_data))
    image_format = image.format
    # Calculate the current size of the image in MB
    current_size_mb = len(image_data) / (1024 * 1024)

    # If already below target size, return original image
    if current_size_mb <= target_size_mb:
        print(f"Image is already below {target_size_mb***REMOVED*** MB")
        return base64_str

    # Reduce quality and/or dimensions
    for quality in range(initial_quality, min_quality - 1, -5):
        # Resize if still too large, by reducing the size by 10% at each iteration
        while current_size_mb > target_size_mb:
            width, height = image.size
            image = image.resize((int(width * 0.9), int(height * 0.9)), Image.Resampling.LANCZOS )
            
            # Convert image to bytes with the new quality
            buffer = io.BytesIO()
            image.save(buffer, format=image_format, quality=quality)
            image_data = buffer.getvalue()
            current_size_mb = len(image_data) / (1024 * 1024)
            print(f"Reducing size with quality={quality***REMOVED*** and dimensions=({width*0.9***REMOVED***, {height*0.9***REMOVED***), size={current_size_mb***REMOVED*** MB")

        # Stop if image is now below target size
        if current_size_mb <= target_size_mb:
            break

    # Encode image back to base64
    return base64.b64encode(image_data).decode()

***REMOVED***

    template, option = create_test()
    text = ["this is a long text that will be extracted"]
    base64_image = []
    with open("extractor\\test\\table.jpg", "rb") as image_file:  # Open in binary mode
        image_data = image_file.read()
        base64_image = [base64.b64encode(image_data).decode("utf-8")]
        
    base64_image= [reduce_image_size(image) for image in base64_image]


    logging.basicConfig(filename="logging.log", encoding="utf-8", level=logging.DEBUG)

    # for root, dirs, files in os.walk(folder):
    # Check if there are PDF files in the current directory
    # if any(file.endswith(".pdf") for file in files):
    # Call your existing function to process PDFs in the folder
    main(base64_image, text, template, option)

