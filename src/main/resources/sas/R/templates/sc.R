###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  SCSEQ = c($SCSEQ),
  SCTESTCD = c($SCTESTCD),
  SCTEST = c($SCTEST),
  SCORRES = c($SCORRES),
  SCSTRESC = c($SCSTRESC),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Subject Characteristics'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['SCSEQ']])<-'Sequence Number'
label(data[['SCTESTCD']])<-'Subject Characteristic Short Name'
label(data[['SCTEST']])<-'Subject Characteristic'
label(data[['SCORRES']])<-'Result or Findings as Collected'
label(data[['SCSTRESC']])<-'Standardized Result in Character Format'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)