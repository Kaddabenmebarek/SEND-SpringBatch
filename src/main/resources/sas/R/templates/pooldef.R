###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  USUBJID = c($USUBJID),
  POOLID = c($POOLID),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Pool Definition'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['POOLID']])<-'Pool Identifier'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)