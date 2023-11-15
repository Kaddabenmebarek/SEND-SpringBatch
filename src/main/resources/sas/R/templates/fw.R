###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  POOLID = c($POOLID),
  FWSEQ = c($FWSEQ),
  FWTESTCD = c($FWTESTCD),
  FWTEST = c($FWTEST),
  FWORRES = c($FWORRES),
  FWORRESU = c($UFWORRES),
  FWSTRESC = c($FWSTRESC),
  FWSTRESN = c($FWSTRESN),
  FWSTRESU = c($FWSTRESU),  
  FWDTC = c($FWDTC),
  FWENDTC = c($FWENDTC),
  FWDY = c($FWDY),
  FWENDY = c($FWENDY),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Food and Water Consumption'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['POOLID']])<-'Pool Identifier'
label(data[['FWSEQ']])<-'Sequence Number'
label(data[['FWTESTCD']])<-'Food/Water Consumption Short Name'
label(data[['FWTEST']])<-'Food/Water Consumption Name'
label(data[['FWORRES']])<-'Result or Findings as Collected'
label(data[['FWORRESU']])<-'Unit of the Original Result'
label(data[['FWSTRESC']])<-'Standardized Result in Character Format'
label(data[['FWSTRESN']])<-'Standardized Result in Numeric Format'
label(data[['FWSTRESU']])<-'Unit of the Standardized Result'
label(data[['FWDTC']])<-'Start Date/Time of Observation'
label(data[['FWENDTC']])<-'End Date/Time of Observation'
label(data[['FWDY']])<-'Study Day of Start of Observation'
label(data[['FWENDY']])<-'Study Day of End of Observation'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)