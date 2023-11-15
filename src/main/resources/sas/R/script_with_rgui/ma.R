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
                            endCol = 12)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, MASEQ = as.numeric(MASEQ))
df2=transform(df2, MADY = as.numeric(MADY))
# for those that are date, transform to date  
df2=transform(df2, MADTC = as.character.Date(MADTC))

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
	SASformat(studyData$MAREFID) <-"$4."
	SASformat(studyData$MATESTCD) <-"$20."
	SASformat(studyData$MATEST) <-"$34."
	SASformat(studyData$MAORRES) <-"$35."
	SASformat(studyData$MASTRESC) <-"$27."
	SASformat(studyData$MASPEC) <-"$30."
	SASformat(studyData$MADTC) <-"$10."
	
	SASiformat(studyData$STUDYID) <-"$7."	
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$MAREFID) <-"$4."
	SASiformat(studyData$MATESTCD) <-"$20."
	SASiformat(studyData$MATEST) <-"$34."
	SASiformat(studyData$MAORRES) <-"$35."
	SASiformat(studyData$MASTRESC) <-"$27."
	SASiformat(studyData$MASPEC) <-"$30."
	SASiformat(studyData$MADTC) <-"$10."	

	write_xpt(
		studyData, 
		path="ma.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/ma.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)