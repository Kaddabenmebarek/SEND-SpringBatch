require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\pm_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\pm_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 23)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, PMSEQ = as.numeric(PMSEQ))
df2=transform(df2, PMSTRESN = as.numeric(PMSTRESN))
df2=transform(df2, VISITDY = as.numeric(VISITDY))
df2=transform(df2, PMDY = as.numeric(PMDY))
df2=transform(df2, PMNOMDY = as.numeric(PMNOMDY))
# for those that are date, transform to date  
df2=transform(df2, PMDTC = as.character.Date(PMDTC))    

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
		path="pm.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/pm.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)