require(XLConnect)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}
# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\cv_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\cv_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 37)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, CVSEQ = as.numeric(CVSEQ))
df2=transform(df2, CVSTRESN = as.numeric(CVSTRESN))
df2=transform(df2, CVDY = as.numeric(CVDY))
df2=transform(df2, CVENDY = as.numeric(CVENDY))
df2=transform(df2, CVNOMDY = as.numeric(CVNOMDY))
df2=transform(df2, CVTPTNUM = as.numeric(CVTPTNUM))
# for those that are date, transform to date  
df2=transform(df2, CVDTC = as.character.Date(CVDTC))
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
		path="cv.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/cv.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)