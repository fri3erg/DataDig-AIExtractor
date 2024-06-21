from typing import List
from pydantic import BaseModel, Field, validator


NF = "not found"
NA = "N/A"


class TabellaFirstInfoVontobel(BaseModel):
    isin: str = Field(NF, description="ISIN")
    description: str = Field(NF, description="Title or description")
    issuer_desc: str = Field(NF, description="Issuer")


class TabellaMainInfoVontobel(BaseModel):
    conditional_protection: List[str] = Field([NF], description=" values of the field called: 'Barrier' ")
    currency: str = Field(NF, description="Settlement Currency")
    strike_date: str = Field(NF, description="Fixing Date (in %)")
    issue_date: str = Field(NF, description="Issue date")
    expiry_date: str = Field(NF, description="Maturity date")
    final_valuation_date: str = Field(NF, description="Final Valuation Date")
    nominal: str = Field(NF, description="Calculation amount or Nominal Amount")
    barrier_autocall: List[str] = Field([NF], description="Redemption Levels")
    memory: str = Field(NF, description="Memory")
    strike_level: List[str] = Field([NF], description="Strike")
    autocall: str = Field(NF, description="Early Redemption")
    payment_callable_date: List[str] = Field([NF], description="put default value")
    instrument_description: List[str] = Field([NF], description="Underlying title")
    instrument_isin: List[str] = Field([NF], description="ISIN Underlying")
    instrument_bloombergcode: List[str] = Field([NF], description="Bloomberg Symbol Underlying")


class TabellaMainInfoVontobel2(BaseModel):
    barrier_type: str = Field(NF, description="Barrier Event")
    coupon: List[str] = Field([NF], description="'Bonus Amount'")
    payment_coupon_date: List[str] = Field([NF], description="Bonus Payment Date")
    conditional_coupon_barrier: List[str] = Field([NF], description="Bonus Threshold, (all %)")  #
    observation_coupon_date: List[str] = Field([NA], description="Observation Dates")
    payment_autocall_date: List[str] = Field([NF], description="Early Redemption Dates")
    observation_autocall_date: List[str] = Field([NF], description="Valuation Dates")

    @validator(
        "payment_coupon_date",
        "observation_coupon_date",
        "payment_autocall_date",
        "observation_autocall_date",
        pre=True,
        each_item=False,
    )
    def ensure_list_of_str(cls, v, field):
        if isinstance(v, str):
            return [v]
        elif isinstance(v, list):
            return v
        else:
            raise ValueError(f"{field.name***REMOVED*** must be a list of strings")


class TabellaDeductableVontobel(BaseModel):
    market: str = Field(NF, description="Exchange Listing")
    issue_price_perc: str = Field(NF, description="Issue Price")
