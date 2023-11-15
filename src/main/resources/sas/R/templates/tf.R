###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  TFSEQ = c($TFSEQ),
  TFSPID = c($TFSPID),
  TFTESTCD = c($CDTFTEST),
  TFTEST = c($TFTEST),
  TFSTRESC = c($TFSTRESC),
  TFRESCAT = c($TFRESCAT),
  TFSPEC = c($TFSPEC),
  TFDTHREL = c($TFDTHREL),
  TFDTC = c($TFDTC),
  TFDY = c($TFDY),
  TFDETECT = c($TFDETECT),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Organ Measurements'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['TFSEQ']])<-'Sequence Number'
label(data[['TFSPID']])<-'Sequence Number'
label(data[['TFTESTCD']])<-'Test Short Name'
label(data[['TFTEST']])<-'Test Name'
label(data[['TFSTRESC']])<-'Result or Findings as Collected'
label(data[['TFRESCAT']])<-'Standardized Result in Character Format'
label(data[['TFSPEC']])<-'Standardized Result in Numeric Format'
label(data[['TFDTHREL']])<-'Unit of the Standardized Result'
label(data[['TFDTC']])<-'Date/Time Measurement'
label(data[['TFDY']])<-'Study Day of Vital Signs Measurement'
label(data[['TFDETECT']])<-'Nominal Study Day for Tabulations'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)