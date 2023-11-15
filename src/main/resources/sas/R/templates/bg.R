###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  BGSEQ = c($BGSEQ),
  BGTESTCD = c($BGTESTCD),
  BGTEST = c($BGTEST),
  BGORRES = c($BGORRES),
  BGORRESU = c($UBGORRES),
  BGSTRESC = c($BGSTRESC),
  BGSTRESN = c($BGSTRESN),
  BGSTRESU = c($BGSTRESU),
  BGDTC = c($BGDTC),
  BGENDTC = c($BGENDTC),
  BGDY = c($BGDY),
  BGENDY = c($BGENDY),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Body Weight'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['BGSEQ']])<-'Sequence Number'
label(data[['BGTESTCD']])<-'Test Short Name'
label(data[['BGTEST']])<-'Test Name'
label(data[['BGORRES']])<-'Result or Findings as Collected'
label(data[['BGORRESU']])<-'Unit of the Original Result'
label(data[['BGSTRESC']])<-'Standardized Result in Character Format'
label(data[['BGSTRESN']])<-'Standardized Result in Numeric Format'
label(data[['BGSTRESU']])<-'Unit of the Standardized Result'
label(data[['BGDTC']])<-'Date/Time Animal Weighed'
label(data[['BGENDTC']])<-'End Date/Time Animal Weighed'
label(data[['BGDY']])<-'Study Day of Start of Interval'
label(data[['BGENDY']])<-'Study Day of End of Weight Interval'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)