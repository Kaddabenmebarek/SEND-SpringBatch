require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\eg_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\eg_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 44)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, EGSEQ = as.numeric(EGSEQ))
df2=transform(df2, EGSTRESN = as.numeric(EGSTRESN))
df2=transform(df2, VISITDY = as.numeric(VISITDY))
df2=transform(df2, EGDY = as.numeric(EGDY))
df2=transform(df2, EGENDY = as.numeric(EGENDY))
df2=transform(df2, EGNOMDY = as.numeric(EGNOMDY))
df2=transform(df2, EGTPTNUM = as.numeric(EGTPTNUM))

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
	SASformat(studyData$DOMAIN) <-"$2."	

	write_xpt(
		studyData, 
		path="eg.xpt", 
		version=5)

	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/eg.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)