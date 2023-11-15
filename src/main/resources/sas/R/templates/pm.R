###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  PMSEQ = c($PMSEQ),
  PMSPID = c($PMSPID),
  PMTESTCD = c($CDPMTEST),
  PMTEST = c($PMTEST),
  PMORRES = c($PMORRES),
  PMORRESU = c($UPMORRES),
  PMSTRESC = c($PMSTRESC),
  PMSTRESN = c($PMSTRESN),
  PMSTRESU = c($PMSTRESU),
  PMLOC = c($PMLOC),
  PMDTC = c($PMDTC),
  PMDY = c($PMDY),
  PMNOMDY = c($PMNOMDY),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Palpable Masses'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['PMSEQ']])<-'Sequence Number'
label(data[['PMSPID']])<-'Mass Identifier'
label(data[['PMTESTCD']])<-'Test Short Name'
label(data[['PMTEST']])<-'Test Name'
label(data[['PMORRES']])<-'Result or Findings as Collected'
label(data[['PMORRESU']])<-'Unit of the Original Result'
label(data[['PMSTRESC']])<-'Standardized Result in Character Format'
label(data[['PMSTRESN']])<-'Standardized Result in Numeric Format'
label(data[['PMSTRESU']])<-'Unit of the Standardized Result'
label(data[['PMLOC']])<-'Location of a Finding'
label(data[['PMDTC']])<-'Date/Time of Observation'
label(data[['PMDY']])<-'Study Day of Observation'
label(data[['PMNOMDY']])<-'Nominal Study Day for Tabulations'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)