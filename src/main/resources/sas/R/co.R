require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\co_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\co_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 13)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, COSEQ = as.numeric(COSEQ))
df2=transform(df2, CODY = as.numeric(CODY))

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
		path="co.xpt", 
		version=5)

	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/co.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)