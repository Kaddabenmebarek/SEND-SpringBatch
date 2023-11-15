require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\om_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\om_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 14)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, OMSEQ = as.numeric(OMSEQ))
df2=transform(df2, OMSTRESN = as.numeric(OMSTRESN))
df2=transform(df2, OMDY = as.numeric(OMDY))
# for those that are date, transform to date  
df2=transform(df2, OMDTC = as.character.Date(OMDTC))    

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
	SASformat(studyData$OMTESTCD) <-"$6."
	SASformat(studyData$OMTEST) <-"$26."
	SASformat(studyData$OMORRES) <-"$6."
	SASformat(studyData$OMORRESU) <-"$1."
	SASformat(studyData$OMSTRESC) <-"$6."
	SASformat(studyData$OMSTRESU) <-"$1."
	SASformat(studyData$OMSPEC) <-"$31."
	SASformat(studyData$OMDTC) <-"$10."
	
	SASiformat(studyData$STUDYID) <-"$7."
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$OMTESTCD) <-"$6."
	SASiformat(studyData$OMTEST) <-"$26."
	SASiformat(studyData$OMORRES) <-"$6."
	SASiformat(studyData$OMORRESU) <-"$1."
	SASiformat(studyData$OMSTRESC) <-"$6."
	SASiformat(studyData$OMSTRESU) <-"$1."
	SASiformat(studyData$OMSPEC) <-"$31."
	SASiformat(studyData$OMDTC) <-"$10."	
	
	write_xpt(
		studyData, 
		path="om.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/om.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)