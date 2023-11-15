###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  MISEQ = c($MISEQ),
  MIREFID = c($MIREFID),
  MITESTCD = c($MITESTCD),
  MITEST = c($MITEST),
  MIORRES = c($MIORRES),
  MISTRESC = c($MISTRESC),
  MISPEC = c($MISPEC),
  MISPCCND = c($MISPCCND),
  MISPCUFL = c($MISPCUFL),
  MISEV = c($MISEV),
  MIDTC = c($MIDTC),
  MIDY = c($MIDY),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Microscopic Findings'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['MISEQ']])<-'Sequence Number'
label(data[['MIREFID']])<-'Specimen Reference Identifier'
label(data[['MITESTCD']])<-'Microscopic Examination Short Name'
label(data[['MITEST']])<-'Microscopic Examination Name'
label(data[['MIORRES']])<-'Result or Findings as Collected'
label(data[['MISTRESC']])<-'Standardized Result in Character Format'
label(data[['MISPEC']])<-'Specimen Material Type'
label(data[['MISPCCND']])<-'Specimen Condition'
label(data[['MISPCUFL']])<-'Specimen Usability for the Test'
label(data[['MISEV']])<-'Severity'
label(data[['MIDTC']])<-'Date/Time'
label(data[['MIDY']])<-'Study Day'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)