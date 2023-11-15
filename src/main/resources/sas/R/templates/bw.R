###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  BWSEQ = c($BWSEQ),
  BWTESTCD = c($BWTESTCD),
  BWTEST = c($BWTEST),
  BWORRES = c($BWORRES),
  BWORRESU = c($UBWORRES),
  BWSTRESC = c($BWSTRESC),  
  BWSTRESN = c($BWSTRESN),
  BWSTRESU = c($BWSTRESU),
  BWBLFL = c($BWBLFL),  
  BWDTC = c($BWDTC),
  BWNOMDY = c($BWNOMDY),
  BWSTAT = c($BWSTAT),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Body Weight'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['BWSEQ']])<-'Sequence Number'
label(data[['BWTESTCD']])<-'Test Short Name'
label(data[['BWTEST']])<-'Test Name'
label(data[['BWORRES']])<-'Result or Findings as Collected'
label(data[['BWORRESU']])<-'Unit of the Original Result'
label(data[['BWSTRESC']])<-'Standardized Result in Character Format'
label(data[['BWSTRESN']])<-'Standardized Result in Numeric Format'
label(data[['BWSTRESU']])<-'Unit of the Standardized Result'
label(data[['BWBLFL']])<-'Baseline Flag'
label(data[['BWDTC']])<-'Date/Time Animal Weighed'
label(data[['BWNOMDY']])<-'Nominal Study Day for Tabulations'
label(data[['BWSTAT']])<-'Completion Status'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)