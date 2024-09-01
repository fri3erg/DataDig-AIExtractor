import io
import os
from extractor.classes.Extracted import Extracted, ExceptionsExtracted
from extractor.classes.Options import Options
import logging
import base64
from typing import List
from extractor.classes.Template import Template, TemplateField, TemplateTable
from extractor.scanner.extractors.main_extractor.extractor import MainExtractor
from extractor.configs.configs_dict import keys_config
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
    tags : List[str] =[]
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
            progress_callback(0.25)

        except Exception as error:
            print("initialization error:" + repr(error))
            exceptions_occurred.append(
                ExceptionsExtracted(error=error, error_location="initialization", error_description=repr(error))
***REMOVED***

        try:
            if extractor:
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
            if extractor:
                tags=extractor.get_tags()
            if exceptions_occurred:
                tags+=["errors occurred"]

            extraction = Extracted(template, exceptions=exceptions_occurred, tags=tags)
        
        for table in extraction.extracted_tables:
            for key, value in table.fields.items():
                print("row: ", key)
                for key1, value1 in value.items():
                    print("column: ", key1)
                    print("value:", value1.value)
                
            
        return extraction

    except Exception as error:
        print("top level error:" + repr(error))
        return Extracted(template, exceptions=exceptions_occurred)


def create_test() -> tuple[Template, Options]:
    """Creates a test instance of the Template class with sample data."""

    # Create sample TemplateField objects
    field1 = TemplateField(
        id="1",
        title="title",
        description="",
        type="Text",
        list= False,
        default="N/A",
        required=True,
        intelligent_extraction=False,
    )
    field2 = TemplateField(
        id="1",
        title="year of the setting",
        description="",
        type="Number",
        list= False,
        default="-1",
        required=True,
        intelligent_extraction=False,
    )
    field3 = TemplateField(
        id="1",
        title="probability of the uprising",
        description="",
        type="Float",
        list= False,
        default="30",
        required=True,
        intelligent_extraction=False,
    )
    field4 = TemplateField(
        id="1",
        title="date( precise) of the revealing of AGI 1",
        description="full date",
        type="Date",
        list= False,
        default="08/12/2024",
        required=True,
        intelligent_extraction=False,
    )
    field5 = TemplateField(
        id="1",
        title="if there is potential for the uprising",
        description="",
        type="Boolean",
        list= False,
        default="False",
        required=True,
        intelligent_extraction=False,
    )
    field6 = TemplateField(
        id="1",
        title="book suggestion",
        description="",
        type="Text",
        list= True,
        default="[]",
        required=True,
        intelligent_extraction=False,
    )
    field7 = TemplateField(
        id="7",
        title="possible",
        description="is the uprising possible?",
        type="Text",
        list= False,
        default="N/A",
        required=True,
        intelligent_extraction=True,
    )

    # Create sample TemplateTable objects
    table1 = TemplateTable(
        "1", "Personal Information", ["ciao", "dd"], "table that describes personal info", [field1, field2, field3], [], True
    )
    table2 = TemplateTable("2", "Contact Information", ["contact", "info"], "", [], [field1, field2, field3], True)

    # Create the Template instance
    template = Template(
        "1",
        "Ai uprising",
        "text about the ai uprising",
        [field1, field2, field3, field4, field5, field6, field7],  # Fields directly associated with the template
        tables=[table1, table2],  # Tables directly associated with the template
    )

    def fake_keys(progress):
        return progress

    option = Options(model="smart_mix", language="auto-detect", azure_ocr=True, get_api_key=fake_keys)

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

def reduce_image_size(base64_str, target_size_mb=4, initial_quality=100, min_quality=20, max_width=4200, max_height=4200):
    """
    Reduces the size of an image to fit within a target size in megabytes.
    Dynamically calculates scaling factor and quality at each step.

    Args:
        base64_str (str): The base64 encoded string of the image.
        target_size_mb (float, optional): The target size in megabytes. Defaults to 4.
        initial_quality (int, optional): The initial quality of the image. Defaults to 85.
        min_quality (int, optional): The minimum quality of the image. Defaults to 10.
        max_width (int, optional): The maximum width of the image. Defaults to 4200.
        max_height (int, optional): The maximum height of the image. Defaults to 4200.

    Returns:
        str: The base64 encoded string of the resized image.
    """

    image_data = base64.b64decode(base64_str)
    image = Image.open(io.BytesIO(image_data))
    image_format = image.format
    width, height = image.size
    print(f"Original image dimensions: {width***REMOVED***x{height***REMOVED***")

    # Check if image exceeds Azure's maximum dimensions
    if width > max_width or height > max_height:
        scaling_factor = min(max_width / width, max_height / height)
        new_width = int(width * scaling_factor)
        new_height = int(height * scaling_factor)
        image = image.resize((new_width, new_height), Image.Resampling.LANCZOS)
        print(f"Resized image to conform to dimension limits: {new_width***REMOVED***x{new_height***REMOVED***")

    # Recalculate the size of the image in MB after resizing for dimensions
    buffer = io.BytesIO()
    image.save(buffer, format=image_format, quality=initial_quality)
    image_data = buffer.getvalue()
    current_size_mb = len(image_data) / (1024 * 1024)


    while current_size_mb > target_size_mb:
        # Estimate the required quality to reach the target size (conservative)
        estimated_quality = min(0.9,max(min_quality, int(initial_quality * (target_size_mb / current_size_mb) * 0.9)))  # 0.9 factor for conservatism

        # Calculate scaling factor to further reduce size if needed
        scaling_factor = min(0.9,max(0.5, (target_size_mb / current_size_mb) ** 0.5))  # Square root for area-based scaling

        width, height = image.size
        new_width = int(width * scaling_factor)
        new_height = int(height * scaling_factor)
        image = image.resize((new_width, new_height), Image.Resampling.LANCZOS)

        buffer = io.BytesIO()
        image.save(buffer, format=image_format, quality=estimated_quality)
        image_data = buffer.getvalue()
        current_size_mb = len(image_data) / (1024 * 1024)
        print(f"Reducing size with quality={estimated_quality***REMOVED*** and dimensions=({new_width***REMOVED***, {new_height***REMOVED***), size={current_size_mb***REMOVED*** MB")

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

