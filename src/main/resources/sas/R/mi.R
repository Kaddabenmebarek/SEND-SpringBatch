require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\mi_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\mi_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 15)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, MISEQ = as.numeric(MISEQ))
df2=transform(df2, MIDY = as.numeric(MIDY))
# for those that are date, transform to date  
df2=transform(df2, MIDTC = as.character.Date(MIDTC))    

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
	SASformat(studyData$MIREFID) <-"$2."
	SASformat(studyData$MITESTCD) <-"$8."
	SASformat(studyData$MITEST) <-"$34."
	SASformat(studyData$MIORRES) <-"$35."
	SASformat(studyData$MISTRESC) <-"$27."
	SASformat(studyData$MISPEC) <-"$13."
	SASformat(studyData$MISPCCND) <-"$1."
	SASformat(studyData$MISPCUFL) <-"$1."
	SASformat(studyData$MISEV) <-"$6."
	SASformat(studyData$MIDTC) <-"$10."
	
	SASiformat(studyData$STUDYID) <-"$7."	
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$MIREFID) <-"$2."
	SASiformat(studyData$MITESTCD) <-"$8."
	SASiformat(studyData$MITEST) <-"$34."
	SASiformat(studyData$MIORRES) <-"$35."
	SASiformat(studyData$MISTRESC) <-"$27."
	SASiformat(studyData$MISPEC) <-"$13."
	SASiformat(studyData$MISPCCND) <-"$1."
	SASiformat(studyData$MISPCUFL) <-"$1."
	SASiformat(studyData$MISEV) <-"$6."
	SASiformat(studyData$MIDTC) <-"$10."	
	
	write_xpt(
		studyData, 
		path="mi.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/mi.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)