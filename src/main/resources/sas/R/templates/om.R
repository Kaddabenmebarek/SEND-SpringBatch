###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  OMSEQ = c($OMSEQ),
  OMTESTCD = c($CDOMTEST),
  OMTEST = c($OMTEST),
  OMORRES = c($OMORRES),
  OMORRESU = c($UOMORRES),
  OMSTRESC = c($OMSTRESC),
  OMSTRESN = c($OMSTRESN),
  OMSTRESU = c($OMSTRESU),
  OMSPEC = c($OMSPEC),
  OMDTC = c($OMDTC),
  OMDY = c($OMDY),
  OMNOMDY = c($OMNOMDY),
  OMSTAT = c($OMSTAT),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Organ Measurements'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['OMSEQ']])<-'Sequence Number'
label(data[['OMTESTCD']])<-'Test Short Name'
label(data[['OMTEST']])<-'Test Name'
label(data[['OMORRES']])<-'Result or Findings as Collected'
label(data[['OMORRESU']])<-'Unit of the Original Result'
label(data[['OMSTRESC']])<-'Standardized Result in Character Format'
label(data[['OMSTRESN']])<-'Standardized Result in Numeric Format'
label(data[['OMSTRESU']])<-'Unit of the Standardized Result'
label(data[['OMSPEC']])<-'Specimen Material Type'
label(data[['OMDTC']])<-'Date/Time Organ Measured'
label(data[['OMDY']])<-'Study Day of Measurement'
label(data[['OMNOMDY']])<-'Nominal Study Day for Tabulations'
label(data[['OMSTAT']])<-'Completion Status'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)