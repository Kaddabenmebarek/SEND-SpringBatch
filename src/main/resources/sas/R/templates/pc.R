###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  PCSEQ = c($PCSEQ),
  PCTESTCD = c($CDPCTEST),
  PCTEST = c($PCTEST),
  PCCAT = c($PCCAT),
  PCORRES = c($PCORRES),
  PCORRESU = c($UPCORRES),
  PCSTRESC = c($PCSTRESC),
  PCSTRESN = c($PCSTRESN),
  PCSTRESU = c($PCSTRESU),
  PCNAM = c($PCNAM),
  PCSPEC = c($PCSPEC),
  PCBLFL = c($PCBLFL),
  PCLLOQ = c($PCLLOQ),
  PCDTC = c($PCDTC),
  PCRFTDTC = c($PCRFTDTC),
  PCNOMDY = c($PCNOMDY),
  PCSTAT = c($PCSTAT),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Organ Measurements'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['PCSEQ']])<-'Sequence Number'
label(data[['PCTESTCD']])<-'Test Short Name'
label(data[['PCTEST']])<-'Test Name'
label(data[['PCCAT']])<-'Test Category'
label(data[['PCORRES']])<-'Result or Findings as Collected'
label(data[['PCORRESU']])<-'Unit of the Original Result'
label(data[['PCSTRESC']])<-'Standardized Result in Character Format'
label(data[['PCSTRESN']])<-'Standardized Result in Numeric Format'
label(data[['PCSTRESU']])<-'Unit of the Standardized Result'
label(data[['PCNAM']])<-'Laboratory Name'
label(data[['PCSPEC']])<-'Specimen Material Type'
label(data[['PCBLFL']])<-'Baseline Flag'
label(data[['PCLLOQ']])<-'Lower Limit of Quantitation'
label(data[['PCDTC']])<-'Date/Time Organ Measured'
label(data[['PCRFTDTC']])<-'Date/Time of Reference Point'
label(data[['PCNOMDY']])<-'Nominal Study Day for Tabulations'
label(data[['PCSTAT']])<-'Completion Status'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)