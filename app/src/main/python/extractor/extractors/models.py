from .azure.openai import azure_openai_model
import threading
from langchain.chains.openai_functions.tagging import create_tagging_chain_pydantic
import tiktoken
from .general_extractors.config.cost_config import cost_per_token
from langchain.chains import LLMChain
import openai
from langchain.schema import (
    AIMessage,
    HumanMessage,
    SystemMessage
)
from langchain.llms.base import LLM
from typing import List, Optional, Any, Dict
from langchain.prompts import ChatPromptTemplate
from langchain_community.llms import OpenAI as LangChainOpenAI
from langchain.chat_models import ChatOpenAI # use ChatOpenAI from the core library
import os

import openai
import threading
import tiktoken
from langchain.llms.base import LLM
from langchain.schema import SystemMessage
from langchain.prompts import ChatPromptTemplate

OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")
openai.api_key = OPENAI_API_KEY 


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
    
    def __new__(cls, model_name: str, temperature: float = 0.0, **kwargs):
        with cls._lock:
            if cls._instance is None:
                cls._instance = super().__new__(cls)
            return cls._instance

    def __init__(self, model_name: str, temperature: float = 0.0, **kwargs):
        if model_name not in self._models:
            self._models[model_name] = {***REMOVED***
        if temperature not in self._models[model_name]:
            model_kwargs = {
                "model_name": model_name,
                "temperature": temperature,
                **kwargs  # Include any additional keyword arguments
            ***REMOVED***
            self._models[model_name][temperature] = ChatOpenAI(
                model_name=model_name,
                temperature=temperature,
                **kwargs  # Include any additional keyword arguments
***REMOVED***

    @property
    def _llm_type(self) -> str:
        return "openai-chat" 

    def _call(self, prompt: str, stop: Optional[List[str]] = None, run_manager: Optional[Any] = None, **kwargs: Any) -> str:
        # Extract model name and temperature from kwargs if provided
        model_name = kwargs.get("model_name", self.__class__.__name__)  
        temperature = kwargs.get("temperature", 0.0)

        # Get the correct model instance
        model_instance = self._models[model_name][temperature]

        response = model_instance(prompt, stop=stop)
                
        return response or ""


    
    @classmethod
    def tag(cls, text, schema, file_id, model="gpt-3.5-turbo", temperature=0):
        llm = cls(model, temperature)._models[model][temperature]
        prompt = ChatPromptTemplate.from_template(
            "Extract information from the following text based on this schema:\n\n{schema***REMOVED***\n\nText:{text***REMOVED***"
        )
        chain = LLMChain(llm=llm, prompt=prompt)
        try:
            output = chain.run(schema=schema, text=text)
        except Exception as e:
            print("Error in tag:", e)
            output = schema
        cls.calc_costs(file_id, model, inputs=[text], outputs=[output])
        return output  
    
    # Updated extract() method
    @classmethod
    def extract(cls, file_id, model, prompt, pages, template, temperature=0):
        llm = cls(model, temperature)  # Get the singleton instance
        chain = LLMChain(llm=llm, prompt=prompt)
        response = chain.run(context=pages, template=template)
        cls.calc_costs(file_id, model, inputs=[pages, prompt], outputs=[response])
        return response
    
    @classmethod
    def calc_costs(cls, file_id, model, inputs=[], outputs=[]):
        """
        calculates and modifies the costs of the chain for that file

        called by the extract and tag method, locks the cost variables while it adds the cost of input and output of the calls

        Args:
            model (str): model you are using
            file_id (str): id of the file you are processing for threading and saving costs
            inputs (list, optional): input strings. Defaults to [].
            outputs (list, optional): output strings. Defaults to [].
        """
        try:
            if file_id not in cls._costs:
                cls._costs.update({file_id: {***REMOVED******REMOVED***)
            if file_id not in cls._file_locks:
                cls._file_locks.update({file_id: threading.Lock()***REMOVED***)
            lock = cls._file_locks[file_id]
            encoding = tiktoken.encoding_for_model(model)
            for i in inputs:
                with lock:
                    cls._costs[file_id][model]["tokens"] = cls._costs[file_id].setdefault(model, {***REMOVED***).get(
                        "tokens", 0
        ***REMOVED*** + (len(encoding.encode(str(i))))
                    cls._costs[file_id][model]["cost"] = (
                        cls._costs[file_id][model].get("tokens", 0) * cost_per_token["input"][model]
        ***REMOVED***
            for o in outputs:
                with lock:
                    cls._costs[file_id][model]["tokens"] = cls._costs[file_id].setdefault(model, {***REMOVED***).get(
                        "tokens", 0
        ***REMOVED*** + (len(encoding.encode(str(o))))
                    cls._costs[file_id][model]["cost"] = (
                        cls._costs[file_id][model].get("tokens", 0) * cost_per_token["output"][model]
        ***REMOVED***
        except Exception as error:
            print("ERROR in costs calc: {***REMOVED***".format(file_id) + repr(error))

    @classmethod
    def get_costs(cls, file_id):
        """returns the costs of the file

        Args:
            file_id (str): id of the file to get the cost from

        Returns:
            dict: API costs
        """
        ret = cls._costs.get(file_id, {***REMOVED***)
        return ret
    
    
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
