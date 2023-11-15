require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\se_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\se_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 8)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, SESEQ = as.numeric(SESEQ))
# for those that are date, transform to date  
df2=transform(df2, SESTDTC = as.character.Date(SESTDTC))
df2=transform(df2, SEENDTC = as.character.Date(SEENDTC))  

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
	SASformat(studyData$ETCD) <-"$5."
	SASformat(studyData$ELEMENT) <-"$43."
	SASformat(studyData$SESTDTC) <-"$10."
	SASformat(studyData$SEENDTC) <-"$10."
	SASiformat(studyData$STUDYID) <-"$7."	
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$ETCD) <-"$5."
	SASiformat(studyData$ELEMENT) <-"$43."
	SASiformat(studyData$SESTDTC) <-"$10."
	SASiformat(studyData$SEENDTC) <-"$10."
	
	write_xpt(
		studyData, 
		path="se.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/se.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)