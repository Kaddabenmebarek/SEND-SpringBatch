require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\vs_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\vs_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 37)
df2 = df[-1,]
df2 = df2[-1,]

df2=transform(df2, VSSEQ = as.numeric(VSSEQ))
df2=transform(df2, VSSTRESN = as.numeric(VSSTRESN))
df2=transform(df2, VISITDY = as.numeric(VISITDY))
df2=transform(df2, VSDY = as.numeric(VSDY))
df2=transform(df2, VSENDY = as.numeric(VSENDY))
df2=transform(df2, VSNOMDY = as.numeric(VSNOMDY))
df2=transform(df2, VSTPTNUM = as.numeric(VSTPTNUM))
# for those that are date, transform to date  
df2=transform(df2, VSRFTDTC = as.character.Date(VSRFTDTC))  

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
		path="vs.xpt", 
		version=5)

	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/vs.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)