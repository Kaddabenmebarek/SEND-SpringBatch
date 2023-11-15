require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\tf_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file= 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\tf_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 24)
df2 = df[-1,]
df2 = df2[-1,] 
# for those that are num, transform to numeric
df2=transform(df2, TFSEQ = as.numeric(TFSEQ))
df2=transform(df2, TFDY = as.numeric(TFDY))
df2=transform(df2, TFDETECT = as.numeric(TFDETECT))
# for those that are date, transform to date  
df2=transform(df2, TFDTC = as.character.Date(TFDTC)) 

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
		path="tf.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/tf.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)