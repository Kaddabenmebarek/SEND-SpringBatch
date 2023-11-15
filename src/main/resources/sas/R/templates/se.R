###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  SESEQ = c($SESEQ),
  ETCD = c($ETCD),
  SESTDTC = c($SESTDTC),
  SEENDTC = c($SEENDTC),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Subject Elements'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['SESEQ']])<-'Sequence Number'
label(data[['ETCD']])<-'Element Code'
label(data[['SESTDTC']])<-'Start Date/Time of Element'
label(data[['SEENDTC']])<-'End Date/Time of Element'

 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)