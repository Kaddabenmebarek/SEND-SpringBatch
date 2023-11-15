###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  EXSEQ = c($EXSEQ),
  EXTRT = c($EXTRT),
  EXDOSE = c($EXDOSE),
  EXDOSU = c($UEXDOS),
  EXDORFRM = c($EXDORFRM),
  EXDOSFRQ = c($EXDOSFRQ),
  EXROUTE = c($EXROUTE),
  EXLOT = c($EXLOT),
  EXTRTV = c($VEXTRT),
  EXVAMT = c($EXVAMT),
  EXVAMTU = c($UEXVAMT),
  EXSTDTC = c($EXSTDTC),
  EXSDY = c($EXSDY),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Exposure'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['EXSEQ']])<-'Sequence Number'
label(data[['EXTRT']])<-'Name of Actual Treatment'
label(data[['EXDOSE']])<-'Dose per Administration'
label(data[['EXDOSU']])<-'Dose Units'
label(data[['EXDORFRM']])<-'Dose Form'
label(data[['EXDOSFRQ']])<-'Dosing Frequency Per Interval'
label(data[['EXROUTE']])<-'Route of Administration'
label(data[['EXLOT']])<-'Lot Number'
label(data[['EXTRTV']])<-'Treatment Vehicle'
label(data[['EXVAMT']])<-'Amount Administered'
label(data[['EXVAMTU']])<-'Amount Administered Units'
label(data[['EXSTDTC']])<-'Start Date/Time of Treatment'
label(data[['EXSDY']])<-'Study Day of Start of Treatment'

write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)