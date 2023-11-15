###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  VSSEQ = c($VSSEQ),
  VSTESTCD = c($CDVSTEST),
  VSTEST = c($VSTEST),
  VSORRES = c($VSORRES),
  VSORRESU = c($UVSORRES),
  VSSTRESC = c($VSSTRESC),
  VSSTRESN = c($VSSTRESN),
  VSSTRESU = c($VSSTRESU),
  VSBLFL = c($VSBLFL),
  VSDTC = c($VSDTC),
  VSDY = c($VSDY),
  VSNOMDY = c($VSNOMDY),
  VSSTAT = c($VSSTAT),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Organ Measurements'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['VSSEQ']])<-'Sequence Number'
label(data[['VSTESTCD']])<-'Test Short Name'
label(data[['VSTEST']])<-'Test Name'
label(data[['VSORRES']])<-'Result or Findings as Collected'
label(data[['VSORRESU']])<-'Unit of the Original Result'
label(data[['VSSTRESC']])<-'Standardized Result in Character Format'
label(data[['VSSTRESN']])<-'Standardized Result in Numeric Format'
label(data[['VSSTRESU']])<-'Unit of the Standardized Result'
label(data[['VSBLFL']])<-'Baseline Flag'
label(data[['VSDTC']])<-'Date/Time Measurement'
label(data[['VSDY']])<-'Study Day of Vital Signs Measurement'
label(data[['VSNOMDY']])<-'Nominal Study Day for Tabulations'
label(data[['VSSTAT']])<-'Completion Status'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)