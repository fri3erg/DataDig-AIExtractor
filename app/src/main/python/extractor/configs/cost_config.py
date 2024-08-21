# AZURE OPENAI COSTS
# https://azure.microsoft.com/en-us/pricing/details/cognitive-services/openai-service/


cost_per_token = {
    "input": {
        "gpt-3.5-turbo": 1.5e-6,  # $1.50 per 1 million tokens
        "gpt-3.5-turbo-16k": 3.0e-6,  # $3.00 per 1 million tokens
        "gpt-4": 3.0e-5,  # $30.00 per 1 million tokens
        "gpt-4-turbo": 1.0e-5,  # $10.00 per 1 million tokens
    ***REMOVED***,
    "output": {
        "gpt-3.5-turbo": 2.0e-6,  # $2.00 per 1 million tokens
        "gpt-3.5-turbo-16k": 4.0e-6,  # $4.00 per 1 million tokens
        "gpt-4": 6.0e-5,  # $60.00 per 1 million tokens
        "gpt-4-turbo": 3.0e-5,  # $30.00 per 1 million tokens
    ***REMOVED***,
    "azure": 0.01,
***REMOVED***
available_costs = [
    "gpt-4-turbo",
    "gpt-4",
    "gpt-3.5-turbo-16k",
    "gpt-3.5-turbo",
    "azure",
]
