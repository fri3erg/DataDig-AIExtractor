from typing import List
from pydantic import BaseModel, Field

NF = "not found"
NA = "N/A"


class TabellaCedola(BaseModel):
    coupon_valuation_date: List[str] = Field([NA], description="Data di Osservazione della Cedola")
    liv_attiv_cedola: List[str] = Field([NA], description="Coupon Trigger Level o Livello di Attivazione della Cedola")
    coupon_payment_date: List[str] = Field([NF], description="Data di Pagamento")
    importo_cedola: List[str] = Field([NF], description="Importo della Cedola")
    autocall_valuation_date: List[str] = Field([NF], description="Data di Osservazione Autocall")
    barrier_autocall: List[str] = Field([NF], description="Autocall Trigger Level o Livello di Attivazione Autocall")
    autocall_payment_date: List[str] = Field([NF], description="Data di Rimborso Anticipato")


class TabellaSottostanti(BaseModel):
    underlying_description: List[str] = Field([NF], description="Sottostante")
    underlying_type: List[str] = Field([NF], description="tipo")
    underlying_stockexchange: List[str] = Field([NF], description="Borsa di Riferimento")
    underlying_bloombergcode: List[str] = Field([NF], description="Bloomberg Ticker")
    underlying_isin: List[str] = Field([NF], description="ISIN")
    fixing_eur: List[str] = Field([NF], description="Livello di Fixing Iniziale, in EUR o USD")
    barriera_eur: List[str] = Field([NA], description="Livello Barriera, in EUR o USD")
    coupon_eur: List[str] = Field(
        [NA], description="Coupon Trigger Level o Livello di Attivazione della Cedola, in EUR o USD"
    )
    underlying_strike_level: List[str] = Field([NA], description="Strike Level")
    cap_level: List[str] = Field([NA], description="Livello Cap")


class TabellaMainInfo(BaseModel):
    currency: str = Field(NF, description="Valuta del prodotto")
    issue_date: str = Field(NF, description="Data di Emissione")
    periodo: str = Field(NF, description="Ultimo Giorno/Periodo di Negoziazione")
    expiry_day: str = Field(NF, description="Data di Rimborso")
    nominal: str = Field(NF, description="Prezzo di Emissione")
    liv_fix_fin: str = Field(NA, description="Livello di Fixing Finale")
    market: str = Field(NF, description="Quotazione di borsa")
    perf_peg: str = Field(NA, description="Performance Peggiore")
    inv_min: str = Field(NF, description="Lotto Minimo di Negoziazione/ Investimento minimo")
    unconditional_protection: str = Field(NA, description="Protezione del Capitale")
    strike_date: str = Field(NF, description="Data del Fixing Iniziale")
    data_negoziazione: str = Field(NF, description="Prima Data di Negoziazione di Mercato")
    final_valuation_date: str = Field(NA, description="Data del Fixing Finale")
    liv_fix_ini: str = Field(NA, description="Livello di Fixing Iniziale")
    mod_pagamento: str = Field(NF, description="Modalità di Pagamento")
    tasso_cedola_cond: str = Field(NA, description="Tasso della Cedola Condizionale")
    unconditional_coupon: str = Field(NA, description="Cedola")
    rischio_cambio: str = Field(NA, description="Rischio di Cambio")
    importo_rimborso: str = Field(NA, description="Importo di Rimborso Massimo")
    importo_protezione_capitale: str = Field(NA, description="Importo di Protezione del Capitale")
    leva_cedolare: str = Field(NA, description="Partecipazione")


class InformazioniBaseLeonteq(BaseModel):
    isin: str = Field(NF, description="codice ISIN")
    description: str = Field(NF, description="descrizione completa del certificato")
    issuer_desc: str = Field(NF, description="ideatore del certificato")


class TabellaSottostantiHeader(BaseModel):
    liv_fixing_iniziale: str = Field(NA, description="Livello di Fixing iniziale in percentuale")
    liv_att_cedola_perc: str = Field(NA, description="Coupon Trigger in percentuale")
    underlying_strike_level: str = Field(NA, description="Strike Level in percentuale")
    barrier: str = Field(NA, description="Livello Barriera, in percentuale")
    livello_cap: str = Field(NA, description="Livello Cap, in percentuale")


class CedolaStr(BaseModel):
    cedola: str = Field(NA, description="il testo")
