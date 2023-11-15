require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\fw_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\fw_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 15)
df2 = df[-1,]
df2 = df2[-1,]

# for those that are num, transform to numeric
df2=transform(df2, FWSEQ = as.numeric(FWSEQ))
df2=transform(df2, FWSTRESN = as.numeric(FWSTRESN))
df2=transform(df2, FWDY = as.numeric(FWDY))
df2=transform(df2, FWENDY = as.numeric(FWENDY))
# for those that are date, transform to date  
df2=transform(df2, FWDTC = as.character.Date(FWDTC))
df2=transform(df2, FWENDTC = as.character.Date(FWENDTC))

Hmisc::label(df2)=df[2,]
studyList <- unique(df2$STUDYID)
for(aStudy in studyList){
	setwd(mainDir)
	if (file.exists(aStudy)){
		setwd(file.path(mainDir, aStudy))
	} else {
		dir.create(file.path(mainDir, aStudy))
		setwd(file.path(mainDir, aStudy))
	}
	studyData <- subset(df2, STUDYID==aStudy,keepNA=FALSE)
	
	SASformat(studyData$STUDYID) <-"$7."
	SASformat(studyData$DOMAIN) <-"$2."
	SASformat(studyData$USUBJID) <-"$11."
#	SASformat(studyData$POOLID) <-"$10."
	SASformat(studyData$FWTESTCD) <-"$7."
	SASformat(studyData$FWTEST) <-"$40."
	SASformat(studyData$FWORRES) <-"$4."
	SASformat(studyData$FWORRESU) <-"$2."
	SASformat(studyData$FWSTRESC) <-"$4."
	SASformat(studyData$FWSTRESU) <-"$15."	
	SASformat(studyData$FWDTC) <-"$10."
	SASformat(studyData$FWENDTC) <-"$10."	
	
	SASiformat(studyData$STUDYID) <-"$7."
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
#	SASiformat(studyData$POOLID) <-"$10."
	SASiformat(studyData$FWTESTCD) <-"$7."
	SASiformat(studyData$FWTEST) <-"$40."
	SASiformat(studyData$FWORRES) <-"$4."
	SASiformat(studyData$FWORRESU) <-"$2."
	SASiformat(studyData$FWSTRESC) <-"$4."
	SASiformat(studyData$FWSTRESU) <-"$15."	
	SASiformat(studyData$FWDTC) <-"$10."
	SASiformat(studyData$FWENDTC) <-"$10."	
	
	write_xpt(
		studyData, 
		path="fw.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/fw.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)