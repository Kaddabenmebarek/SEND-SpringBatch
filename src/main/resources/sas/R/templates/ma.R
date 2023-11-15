###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  MASEQ = c($MASEQ),
  MAREFID = c($MAREFID),
  MATESTCD = c($MATESTCD),
  MATEST = c($MATEST),
  MAORRES = c($MAORRES),
  MASTRESC = c($MASTRESC),
  MASPEC = c($MASPEC),
  MAANTREG = c($MAANTREG),
  MALAT = c($MALAT),
  MADTC = c($MADTC),
  MADY = c($MADY),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Macroscopic Findings'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['MASEQ']])<-'Sequence Number'
label(data[['MAREFID']])<-'Specimen Reference Identifier'
label(data[['MATESTCD']])<-'Macroscopic Examination Short Name'
label(data[['MATEST']])<-'Macroscopic Examination Name'
label(data[['MAORRES']])<-'Result or Findings as Collected'
label(data[['MASTRESC']])<-'Standardized Result in Character Format'
label(data[['MASPEC']])<-'Specimen Material Type'
label(data[['MAANTREG']])<-'Anatomical Region of Specimen'
label(data[['MALAT']])<-'Specimen Laterality within Subject'
label(data[['MADTC']])<-'Date/Time'
label(data[['MADY']])<-'Study Day'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)