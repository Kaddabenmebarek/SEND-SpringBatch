require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\cl_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\cl_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 16)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, CLSEQ = as.numeric(CLSEQ))
#  df2=transform(df2, CLREFID = as.numeric(CLREFID))
df2=transform(df2, VISITDY = as.numeric(VISITDY))
df2=transform(df2, CLDY = as.numeric(CLDY))
df2=transform(df2, CLTPTNUM = as.numeric(CLTPTNUM))
# for those that are date, transform to date  
df2=transform(df2, CLDTC = as.character.Date(CLDTC))
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
	SASformat(studyData$CLREFID) <-"$4."
	SASformat(studyData$CLTESTCD) <-"$2."
	SASformat(studyData$CLTEST) <-"$13."
	SASformat(studyData$CLCAT) <-"$14."
	SASformat(studyData$CLORRES) <-"$6."
	SASformat(studyData$CLSTRESC) <-"$6."
	SASformat(studyData$CLLOC) <-"$1."
	SASformat(studyData$CLDTC) <-"$19."
	SASformat(studyData$CLTPT) <-"$24."
	SASiformat(studyData$STUDYID) <-"$7."
	SASiformat(studyData$DOMAIN) <-"$2."	
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$CLREFID) <-"$4."
	SASiformat(studyData$CLTESTCD) <-"$2."
	SASiformat(studyData$CLTEST) <-"$13."
	SASiformat(studyData$CLCAT) <-"$14."
	SASiformat(studyData$CLORRES) <-"$6."
	SASiformat(studyData$CLSTRESC) <-"$6."
	SASiformat(studyData$CLLOC) <-"$1."
	SASiformat(studyData$CLDTC) <-"$19."
	SASiformat(studyData$CLTPT) <-"$24."
	
	write_xpt(
		studyData, 
		path="cl.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/cl.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)