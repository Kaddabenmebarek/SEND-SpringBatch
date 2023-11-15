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
                            endCol = 14)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, AGE = as.numeric(AGE))
# for those that are date, transform to date  
df2=transform(df2, RFSTDTC = as.character.Date(RFSTDTC))  
df2=transform(df2, RFENDTC = as.character.Date(RFENDTC))

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
	SASformat(studyData$RFSTDTC) <-"$10."
	SASformat(studyData$RFENDTC) <-"$10."
	SASformat(studyData$AGEU) <-"$5."
	SASformat(studyData$SEX) <-"$1."
	SASformat(studyData$SPECIES) <-"$10."
	SASformat(studyData$STRAIN) <-"$40."
	SASformat(studyData$ARMCD) <-"$5."
	SASformat(studyData$ARM) <-"$60."
	SASformat(studyData$SETCD) <-"$5."
	
	SASiformat(studyData$STUDYID) <-"$7."
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$RFSTDTC) <-"$10."
	SASiformat(studyData$RFENDTC) <-"$10."
	SASiformat(studyData$AGEU) <-"$5."
	SASiformat(studyData$SEX) <-"$1."
	SASiformat(studyData$SPECIES) <-"$10."
	SASiformat(studyData$STRAIN) <-"$40."
	SASformat(studyData$ARMCD) <-"$5."
	SASiformat(studyData$ARM) <-"$60."	
	SASiformat(studyData$SETCD) <-"$5."
	
	write_xpt(
		studyData, 
		path="dm.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/dm.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)