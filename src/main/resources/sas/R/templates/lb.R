###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  LBSEQ = c($LBSEQ),
  LBTESTCD = c($LBTESTCD),
  LBTEST = c($LBTEST),
  LBCAT = c($LBCAT),
  LBORRES = c($LBORRES),
  LBORRESU = c($LBORRESU),
  LBSTRESC = c($LBSTRESC),
  LBSTRESN = c($LBSTRESN),
  LBSTRESU = c($LBSTRESU),
  LBSTAT = c($LBSTAT),
  LBREASND = c($LBREASND),
  LBSPEC = c($LBSPEC),
  LBMETHOD = c($LBMETHOD),
  LBBLFL = c($LBBLFL),
  VISITDY = c($VISITDY),
  LBDTC = c($LBDTC),
  LBDY = c($LBDY),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Laboratory Test Results'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['LBSEQ']])<-'Sequence Number'
label(data[['LBTESTCD']])<-'Lab Test or Examination Short Name'
label(data[['LBTEST']])<-'Lab Test or Examination Name'
label(data[['LBCAT']])<-'Category for Lab Test'
label(data[['LBORRES']])<-'Result or Findings as Collected'
label(data[['LBORRESU']])<-'Unit of the Original Result'
label(data[['LBSTRESC']])<-'Standardized Result in Character Format'
label(data[['LBSTRESN']])<-'Standardized Result in Numeric Format'
label(data[['LBSTRESU']])<-'Unit of the Standardized Result'
label(data[['LBSTAT']])<-'Completion Status'
label(data[['LBREASND']])<-'Reason Not Done'
label(data[['LBSPEC']])<-'Specimen Material Type'
label(data[['LBBLFL']])<-'Baseline Flag'
label(data[['VISITDY']])<-'Planned Study Day of Collection'
label(data[['LBDTC']])<-'Date/Time of Specimen Collection'
label(data[['LBDY']])<-'Study Day of Specimen Collection'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)