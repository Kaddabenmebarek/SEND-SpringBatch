require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
myFile <- choose.files(caption = "Select XLS file",multi=F,filters=cbind('.xls or xlsx files','*.xls;*.xlsx'))
mainDir <- dirname(myFile)
# Read in XLSX file
df <- readWorksheetFromFile(myFile,
                            sheet=1,
                            startRow = 1,
                            endCol = 7)
df2 = df[-1,]
df2 = df2[-1,] 
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
	SASformat(studyData$ETCD) <-"$5."
	SASformat(studyData$ELEMENT) <-"$43."
	SASformat(studyData$TESTRL) <-"$62."
	SASformat(studyData$TEENRL) <-"$30."
	SASformat(studyData$TEDUR) <-"$4."
	
	SASiformat(studyData$STUDYID) <-"$7."	
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$ETCD) <-"$5."
	SASiformat(studyData$ELEMENT) <-"$43."
	SASiformat(studyData$TESTRL) <-"$62."
	SASiformat(studyData$TEENRL) <-"$30."
	SASiformat(studyData$TEDUR) <-"$4."	
	
	write_xpt(
		studyData, 
		path="te.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/te.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)