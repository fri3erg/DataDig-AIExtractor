# LLM EXTRACTOR :it:

## Introduction

uses llm calls to openapi and azure to extract informations from documents, specifically differents type of kids of different issuers.
uses general prompt extraction on text and pydantic tagging on results and on extracted tables from azure api document-layout service


## Table of Contents

- [Introduction](#introduction)
- [Basic Structure](#basic-structure)
- [Contact](#contact)
- [DEVS](#devs)

## Basic Structure

generally divided in 3 parts, the first 2 run in parallel different tasks 
1. extracts tables and everything that can be extracted directly
2. extracts from the tables found




### Important Points

- tables are cached in self.di_table_pages ( as azure gives all tables in pages check not just the one you ask)
- fill_table asks azure directly for multiple pages(fills cache), faster but more costly if not all pages used
- if adding more issuers, pls use already developed tools if they exist, regex shortcuts exists in the utils files and llm extractors tools exist in llm_functions.py or extractor.py,
- good luck :finnadie:

## Contact

Feel free to reach out to me via:

- **Email:** [elia.fri3erg@gmail.com](mailto:elia.fri3erg@gmail.com)
- **LinkedIn:** [elia.friberg](https://www.linkedin.com/in/elia-friberg-021a90295/)
- **Whatsapp:** [@elia.friberg](+393924123304)

## DEVS

Elia Friberg

