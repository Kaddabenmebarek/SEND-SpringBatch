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
                            endCol = 9)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, DSSEQ = as.numeric(DSSEQ))
df2=transform(df2, VISITDY = as.numeric(VISITDY))
df2=transform(df2, DSSTDY = as.numeric(DSSTDY))
#df2=transform(df2, DSNOMDY = as.numeric(DSNOMDY))
# for those that are date, transform to date  
df2=transform(df2, DSSTDTC = as.character.Date(DSSTDTC))  

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
	SASformat(studyData$DSTERM) <-"$16."
	SASformat(studyData$DSDECOD) <-"$18."
	SASformat(studyData$DSSTDTC) <-"$10."
	
	SASiformat(studyData$STUDYID) <-"$7."
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$DSTERM) <-"$16."
	SASiformat(studyData$DSDECOD) <-"$18."
	SASiformat(studyData$DSSTDTC) <-"$10."	
	
	write_xpt(
		studyData, 
		path="ds.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/ds.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)