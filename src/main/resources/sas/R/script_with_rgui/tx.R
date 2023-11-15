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
                            endCol = 8)
df2 = df[-1,]
df2 = df2[-1,]
df2=transform(df2, TXSEQ = as.numeric(TXSEQ))
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
	SASformat(studyData$SET) <-"$46."
	SASformat(studyData$TXPARMCD) <-"$8."
	SASformat(studyData$TXPARM) <-"$26."
	SASformat(studyData$TXVAL) <-"$65."
	
	SASiformat(studyData$STUDYID) <-"$7."
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$SET) <-"$46."
	SASiformat(studyData$TXPARMCD) <-"$8."
	SASiformat(studyData$TXPARM) <-"$26."
	SASiformat(studyData$TXVAL) <-"$65."
	
	write_xpt(
		studyData, 
		path="tx.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/tx.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)