import json
import os
from AWSInteraction.EnvVarSetter import EnvVarSetter
from ExtractionHandler import ThreadedFunction
from extractors.general_extractors.custom_extractors.certificates.issuers.BNP.BNP_extractor import (
    BNPDerivatiKidExtractor,
)

from extractors.general_extractors.custom_extractors.certificates.issuers.Vontobel.Vontobel_extractor import (
    VontobelDerivatiKidExtractor,
)
from extractors.general_extractors.custom_extractors.kid.insurance.kid.kid_extractor import InsuranceKidExtractor
from extractors.general_extractors.custom_extractors.kid.insurance.gkid.gkid_extractor import InsuranceGKidExtractor
from extractors.general_extractors.custom_extractors.certificates.issuers.Leonteq.Leonteq_extractor import (
    LeonteqDerivatiKidExtractor,
)

# from extractors.Derivati.Spot_KID_extractor import write_info
# from extractors.Derivati.Global_KID_extractor import GlobalExtractor
from extractors.models import Models
import glob
import logging
import pandas as pd

logging.basicConfig(filename="logging.log", encoding="utf-8", level=logging.DEBUG)

"""
def process_file(file_path, file_type):
    instanciate a KidExtractor and process the file

    Args:
        file_path (str): path of pdf file to process

    Returns:
        dict(filename,dict()): dictionary containing the results for the file
        
    try:
        match file_type:
            case "kid":
                extractor = InsuranceKidExtractor(file_path)
            case "g-kid":
                extractor = InsuranceGKidExtractor(file_path)
            case "leonteq":
                extractor = LeonteqDerivatiKidExtractor(file_path)
            case "bnp":
                extractor = BNPDerivatiKidExtractor(file_path)
            case "vontobel":
                extractor = VontobelDerivatiKidExtractor(file_path)
            case _:
                raise ValueError("type not supported")
            
        return asyncio.run(extractor.process())
    
    
    except Exception as error:
        print("ERROR: {***REMOVED***".format(file_path) + repr(error))
        filename = os.path.splitext(os.path.basename(file_path))[0]
        return dict((filename, dict()))

"""


def main(doc_folder):
    """main loop, instanciate 10 async process(more causes errors) for 10 files at the time,
    puts results in an excel file

    Args:
        doc_folder (str): folder containing the pdf files
    """
    try:
        env_setter = EnvVarSetter(tenant="insurance")
        env_setter.configure_local_env_vars()

        # testing
        file_type = "vontobel"
        batch_size = 5
        parallel = False
        all_files = []
        # list all the pdf files in the folder
        print("START")
        all_files = glob.glob(os.path.join(doc_folder, "**\\*.pdf"), recursive=True)

        match file_type:
            case "kid":
                extractor = InsuranceKidExtractor  # bugged with performance,no prompt?
            case "g-kid":
                extractor = InsuranceGKidExtractor
            case "leonteq":
                extractor = LeonteqDerivatiKidExtractor
            case "bnp":
                extractor = BNPDerivatiKidExtractor  # bugged with performance,no prompt?
            case "vontobel":
                extractor = VontobelDerivatiKidExtractor
            case _:
                raise ValueError("type not supported")

        threads = {***REMOVED***
        extractions = {***REMOVED***
        if parallel:
            batches = [all_files[i : i + batch_size] for i in range(0, len(all_files), batch_size)]
            for files in batches:
                for idx, file_path in enumerate(files):
                    thread = ThreadedFunction(extractor, file_path)
                    threads[idx] = thread
                    thread.start()

                for _, thread in threads.items():
                    thread.join()

                for file_key, thread in threads.items():
                    # result to dict
                    dict_result = json.loads(thread.result)
                    dict_result["extraction_time"] = thread.total_runtime
                    extractions[file_key] = json.dumps(thread.result)

        else:
            for file_path in all_files:
                real_extractor = extractor(file_path)
                result = real_extractor.process()
                filename = os.path.splitext(os.path.basename(file_path))[0]
                extractions[filename] = json.dumps(result)

        # give request id
        Models.clear_resources_group(str(-1))
        # orders results
        results = pd.DataFrame(results)
        # ordered = [col for col in column_order[file_type] if col in results.columns]
        # results = results[ordered]
        excel_path = os.path.join(doc_folder + ".xlsx")
        # saves results
        results.to_excel(excel_path, header=True, index=False)
        print(results)

    except Exception as error:
        print("top level error:" + repr(error))


***REMOVED***
    folder = "kid\\documents\\testdoc\\Vontobel"

    # for root, dirs, files in os.walk(folder):
    # Check if there are PDF files in the current directory
    # if any(file.endswith(".pdf") for file in files):
    # Call your existing function to process PDFs in the folder
    main(folder)

    # main(folder)
