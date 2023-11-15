###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  ETCD = c($ETCD),
  ELEMENT = c($ELEMENT),
  TESTRL = c($TESTRL),
  TEENRL = c($TEENRL),
  TEDUR = c($TEDUR),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Trial Elements'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['ETCD']])<-'Element Code'
label(data[['ELEMENT']])<-'Description of Element'
label(data[['TESTRL']])<-'Rule for Start of Element'
label(data[['TEENRL']])<-'Rule for End of Element'
label(data[['TEDUR']])<-'Planned Duration of Element'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)