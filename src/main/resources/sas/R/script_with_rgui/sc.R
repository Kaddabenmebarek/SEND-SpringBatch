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
# for those that are num, transform to numeric
df2=transform(df2, SCSEQ = as.numeric(SCSEQ))
# for those that are date, transform to date  
#df2=transform(df2, SCDTC = as.character.Date(SCDTC))

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
	SASformat(studyData$SCTESTCD) <-"$30."	
	SASformat(studyData$SCTEST) <-"$60."	
	SASformat(studyData$SCORRES) <-"$200."	
	SASformat(studyData$SCSTRESC) <-"$200."

	SASiformat(studyData$STUDYID) <-"$7."
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."	
	SASiformat(studyData$SCTESTCD) <-"$30."	
	SASiformat(studyData$SCTEST) <-"$60."	
	SASiformat(studyData$SCORRES) <-"$200."	
	SASiformat(studyData$SCSTRESC) <-"$200."
	
	write_xpt(
		studyData, 
		path="sc.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/sc.xpt",sep = "")
	print(aLine)
}

setwd(mainDir)