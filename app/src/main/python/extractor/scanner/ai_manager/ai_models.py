# import instructor.exceptions
from ...scanner.extractors.extractor_utils import num_tokens_from_string
import threading
from ...configs.cost_config import cost_per_token
from openai import OpenAI, AuthenticationError
from typing import List, Optional, Any, Dict
from ...classes.Extracted import ExtractionCosts
from langchain.prompts import PromptTemplate
import os
import asyncio

# import instructor
from langchain.chains import LLMChain
from langchain.llms.base import LLM

# from pydantic_core import ValidationError
from langchain.chat_models import ChatOpenAI  # use ChatOpenAI from the core library
from langchain.llms.base import LLM
from langchain.output_parsers import PydanticOutputParser

# from langchain_openai.chat_models.base import ChatOpenAI # use ChatOpenAI from the core library
from langchain.schema import BaseMessage

OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")
api_key = OPENAI_API_KEY


from typing import Any


class Models(LLM):
    """create an instance for each model to avoid loading it multiple times
    was a singleton but not variegated enough
    now holds static vars
    cant instantiate, will return the instance of a model if it exists, otherwise create it
    Args:
        model_name (str): name of the model to load
        group_id (int, optional): id of the group to which the request belongs. Defaults to -1.
        temperature (int, optional): temperature of the model. Defaults to 0.
    Returns:
        Model : instance of the model
    """

    _costs = {***REMOVED***
    _file_locks = {***REMOVED***
    _lock = threading.Lock()
    _instance: Optional["Models"] = None
    _models: Dict[str, Dict[float, ChatOpenAI]] = {***REMOVED***
    _lock = threading.Lock()
    _openAI_instance: Optional[OpenAI] = None

    def __new__(cls, model_name: str, temperature: float = 0.0, **kwargs):
        with cls._lock:
            if cls._instance is None:
                cls._instance = super().__new__(cls)
                cls._openAI_instance = OpenAI()
            return cls._instance

    def __init__(self, model_name: str, temperature: float = 0.0, **kwargs):
        if model_name not in self._models:
            self._models[model_name] = {***REMOVED***
        if temperature not in self._models[model_name]:
            self._models[model_name][temperature] = ChatOpenAI(
                model=model_name, temperature=temperature, **kwargs  # Include any additional keyword arguments
***REMOVED***
        if self._openAI_instance is None:
            self._openAI_instance = OpenAI()

    @property
    def _llm_type(self) -> str:
        return "openai-chat"

    def _call(
        self, prompt: str, stop: Optional[List[str]] = None, run_manager: Optional[Any] = None, **kwargs: Any
    ) -> str:
        # Extract model name and temperature from kwargs if provided
        model_name = kwargs.get("model_name", self.__class__.__name__)
        temperature = kwargs.get("temperature", 0.0)

        # Get the correct model instance
        model_instance: ChatOpenAI = self._models[model_name][temperature]

        messages: List[BaseMessage] = [
            BaseMessage(content=prompt, type="user"),
        ]

        response: BaseMessage = model_instance(messages, stop=stop)

        string_response = ""
        if isinstance(response.content, str):
            string_response = response.content
        else:
            string_response = response.content[0][0]

        return string_response

    @classmethod
    def tag(
        cls, prompt: PromptTemplate, schema, file_id: str, model: str = "gpt-4", temperature: float = 0.0
    ) -> tuple[Any, Optional[Exception]]:

        if model == "smart_mix":
            model = "gpt-4"
        llm: ChatOpenAI = cls(model, temperature)._models[model][temperature]

        """if cls._openAI_instance is None:
            cls._openAI_instance = OpenAI()
        output = ""
        try:
            client= instructor.from_openai(
                cls._openAI_instance)
            output = client.chat.completions.create(
                model=model,  # Or your preferred model
                messages=word_prompt,
                max_retries=3,  # Retry up to 3 times if validation fails
                response_model=schema
***REMOVED***
        except ValidationError as e:
            print("Validation Error:", e)
            raise e
        except Exception as e:
            print("Error in tag (GPT-4):", e)
            raise e
            
           else: 
        """
        # PydanticOutputParser for other models
        output_parser = PydanticOutputParser(pydantic_object=schema)
        error_occurred: Optional[Exception] = None
        try:
            chain = LLMChain(llm=llm, prompt=prompt, output_parser=output_parser)
            output = chain.run(schema=schema.schema_json())
        except AuthenticationError as auth_err:
            print("Authentication Error (Invalid API key?):", auth_err)
            error_occurred = auth_err
            output = schema()  # Create an empty instance on other errors"""
        except Exception as e:
            print("Error creating LLMChain:", e)
            output = schema()  # Create an empty instance on other errors"""
            error_occurred = e

        cls.calc_costs(file_id, model, inputs=[str(prompt)], outputs=[str(output)])
        return output, error_occurred

    # Updated extract() method
    @classmethod
    def extract(cls, file_id, model, prompt: PromptTemplate, pages, template, temperature=0) -> str:
        if model == "smart_mix":
            model = "gpt-3.5-turbo"
        llm: ChatOpenAI = cls(model, temperature)._models[model][temperature]  # Get the singleton instance
        try:
            chain = prompt | llm
        except AuthenticationError as auth_err:
            print("Authentication Error (Invalid API key?):", auth_err)
            raise ValueError("invalid OpenAI key")
        except Exception as e:
            print("Error in extract:", e)
            raise e

        response: BaseMessage = asyncio.run(chain.ainvoke({"context": pages, "template": template***REMOVED***))
        output = response.content if isinstance(response.content, str) else response.content[0][0]
        cls.calc_costs(file_id, model, inputs=[pages, str(prompt)], outputs=[output])
        return output

    @classmethod
    def calc_costs(cls, file_id, model, inputs=[], outputs=[]):
        try:
            if file_id not in cls._costs:
                cls._costs[file_id] = []
            if file_id not in cls._file_locks:
                cls._file_locks[file_id] = threading.Lock()
            lock = cls._file_locks[file_id]

            def add_cost(data_list, cost_type):
                for data in data_list:
                    with lock:
                        tokens = num_tokens_from_string(data)
                        cost = tokens * cost_per_token[cost_type][model]

                        # Find existing ExtractionCosts object for the model
                        existing_cost = next((c for c in cls._costs[file_id] if c.name == model), None)

                        if existing_cost:
                            existing_cost.tokens += tokens
                            existing_cost.cost += cost
                        else:
                            cls._costs[file_id].append(ExtractionCosts(model, tokens, cost, "EUR"))

            add_cost(inputs, "input")
            add_cost(outputs, "output")

        except Exception as error:
            print("ERROR in costs calc: {***REMOVED***".format(file_id) + repr(error))

    @classmethod
    def get_costs(cls, file_id):
        return cls._costs.get(file_id, [])

    @classmethod
    def clear_resources_file(cls, file_id):
        """clears the static resources of the object associated with the file_id, call after each file

        Args:
            file_id (str): id of the file to clear the resources from
        """
        if file_id in cls._file_locks:
            del cls._file_locks[file_id]
        if file_id in cls._costs:
            del cls._costs[file_id]
