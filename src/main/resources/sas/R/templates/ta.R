###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  ARMCD = c($CDARM),
  ARM = c($ARM),
  TAETORD = c($TAETORD),
  ETCD = c($ETCD),
  ELEMENT = c($ELEMENT),
  TABRANCH = c($TABRANCH),
  EPOCH = c($EPOCH),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Trial Arms'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['ARMCD']])<-'Planned Arm Code'
label(data[['ARM']])<-'Description of Planned Arm'
label(data[['TAETORD']])<-'Order of Element within Arm'
label(data[['ETCD']])<-'Element Code'
label(data[['ELEMENT']])<-'Description of Element'
label(data[['TABRANCH']])<-'Branch'
label(data[['EPOCH']])<-'Trial Epoch'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)