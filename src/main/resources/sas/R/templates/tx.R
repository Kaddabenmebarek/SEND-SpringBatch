###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  SETCD = c($CDSET),
  SET = c($SET),
  TXSEQ = c($TXSEQ),
  TXPARMCD = c($TXPARMCD),
  TXPARM = c($TXPARM),
  TXVAL = c($TXVAL),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Trial Elements'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['SETCD']])<-'Set Code'
label(data[['SET']])<-'Set Description'
label(data[['TXSEQ']])<-'Sequence Number'
label(data[['TXPARMCD']])<-'Trial Set Parameter Short Name'
label(data[['TXPARM']])<-'Trial Set Parameter'
label(data[['TXVAL']])<-'Trial Set Parameter Value'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)