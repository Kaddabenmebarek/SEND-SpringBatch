###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  CLSEQ = c($CLSEQ),
  CLTESTCD = c($CLTESTCD),
  CLTEST = c($CLTEST),
  CLCAT = c($CLCAT),
  CLORRES = c($CLORRES),
  CLSTRESC = c($CLSTRESC),  
  CLLOC = c($CLLOC),
  VISITDY = c($VISITDY),
  CLDTC = c($CLDTC),  
  CLDY = c($CLDY),
  CLNOMDY = c($CLNOMDY),
  CLTPT = c($CLTPT),
  CLTPTNUM = c($NUMCLTPT),
  CLSTAT = c($CLSTAT),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Clinical Observations'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['CLSEQ']])<-'Sequence Number'
label(data[['CLTESTCD']])<-'Test Short Name'
label(data[['CLTEST']])<-'Test Name'
label(data[['CLCAT']])<-'Category for Clinical Observations'
label(data[['CLORRES']])<-'Result or Findings as Collected'
label(data[['CLSTRESC']])<-'Standardized Result in Character Format'
label(data[['CLLOC']])<-'Location of a Finding'
label(data[['VISITDY']])<-'Planned Study Day of Collection'
label(data[['CLDTC']])<-'Date/Time of Observation'
label(data[['CLDY']])<-'Study Day of Observation'
label(data[['CLNOMDY']])<-'Nominal Study Day for Tabulations'
label(data[['CLTPT']])<-'Planned Time Point Name'
label(data[['CLTPTNUM']])<-'Planned Time Point Number'
label(data[['CLSTAT']])<-'Completion Status'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)