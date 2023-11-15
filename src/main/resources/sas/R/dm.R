###Load Packages###
library(haven)
library(Hmisc)

data <- data.frame(
  STUDYID = c($STUDYID),
  DOMAIN = c($DOMAIN),
  USUBJID = c($USUBJID),
  SUBJID = c($SUBJID),
  RFSTDTC = c($RFSTDTC),
  RFENDTC = c($RFENDTC),
  AGE = c($AGE),
  AGEU = c($UAGE),
  SEX = c($SEX),
  SPECIES = c($SPECIES),  
  STRAIN = c($STRAIN),
  ARM = c($ARM),
  ARMCD = c($CDARM),  
  SETCD = c($SETCD),
  stringsAsFactors = FALSE)
  
##add labels##
label(data)<-'Demographics'
label(data[['STUDYID']])<-'Study Identifier'
label(data[['DOMAIN']])<-'Domain Abbreviation'
label(data[['USUBJID']])<-'Unique Subject Identifier'
label(data[['SUBJID']])<-'Subject Identifier for the Study'
label(data[['RFSTDTC']])<-'Subject Reference Start Date/Time'
label(data[['RFENDTC']])<-'Subject Reference End Date/Time'
#label(data[['RFXSTDTC']])<-'Date/Time of First Study Treatment'
#label(data[['RFXENDTC']])<-'Date/Time of Last Study Treatment'
#label(data[['SITEID']])<-'Study Site Identifier'
#label(data[['BRTHDTC']])<-'Date/Time of Birth'
label(data[['AGE']])<-'Age'
#label(data[['AGETXT']])<-'Age Range'
label(data[['AGEU']])<-'Age Unit'
label(data[['SEX']])<-'Sex'
label(data[['SPECIES']])<-'Species'
label(data[['STRAIN']])<-'Strain/Substrain'
#label(data[['SBSTRAIN']])<-'Strain/Substrain Details'
label(data[['ARM']])<-'Description of Planned Arm'
label(data[['ARMCD']])<-'Planned Arm Code'
label(data[['SETCD']])<-'Set Code'
 
write_xpt(data, path="$XPT-OUTPUT-PATH", version=5)