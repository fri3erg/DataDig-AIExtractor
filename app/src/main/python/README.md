# LLM EXTRACTOR    :it:

## Table of Contents

- [Introduction](#introduction)
- [Basic Structure](#basic-structure)
- [Important Points](#important-points-exclamation)
- [Contact](#contact-mailbox_with_mail)
- [DEVS](#devs)

## Introduction

uses llm calls to openapi and azure to extract informations from documents.
uses general prompt extraction on text and pydantic tagging on results and on extracted tables from azure api document-layout service

## Basic Structure  

generally divided in 3 parts, the first 2 run in parallel different tasks

1. extracts tables and everything that can be extracted directly
2. extracts from the tables found

### Important Points :exclamation:

- tables are cached in self.di_table_pages ( as azure gives all tables in pages check not just the one you ask)
- fill_table asks azure directly for multiple pages(fills cache), faster but more costly if not all pages used, also update all text found with Azure OCR ones which is better
- dependencies depends from the chaquopy avaiables ones, instructor is impossible due to pydantic<2.0 so json parsing is needed
- images get shrunk to fit 4mb for azure free trial

## Contact :mailbox_with_mail:

Good luck :vulcan_salute:
Feel free to reach out to me via:

- **Email:** [elia.fri3erg@gmail.com](mailto:elia.fri3erg@gmail.com)
- **LinkedIn:** [elia.friberg](https://www.linkedin.com/in/elia-friberg-021a90295/)
- **Whatsapp:** [@elia.friberg](+393924123304)

## DEVS

Elia Friberg :finnadie:
