###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  DSSEQ = c($DSSEQ),
  DSTERM = c($DSTERM),
  DSDECOD = c($DSDECOD),
  DSUSCHFL = c($DSUSCHFL),  
  DSSTDTC = c($DSSTDTC),
  DSNOMDY = c($DSNOMDY),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Disposition'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['DSSEQ']])<-'Sequence Number'
label(data[['DSTERM']])<-'Reported Term for the Disposition Event'
label(data[['DSDECOD']])<-'Standardized Disposition Term'
label(data[['DSUSCHFL']])<-'Unscheduled Flag'
label(data[['DSSTDTC']])<-'Date/Time of Disposition'
label(data[['DSNOMDY']])<-'Nominal Study Day for Tabulations'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)