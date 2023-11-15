require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\ma_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\ma_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 14)
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
	SASformat(studyData$MAREFID) <-"$2."
	SASformat(studyData$MATESTCD) <-"$8."
	SASformat(studyData$MATEST) <-"$34."
	SASformat(studyData$MAORRES) <-"$35."
	SASformat(studyData$MASTRESC) <-"$27."
	SASformat(studyData$MASPEC) <-"$13."
	SASformat(studyData$MAANTREG) <-"$1."
	SASformat(studyData$MALAT) <-"$9."
	SASformat(studyData$MADTC) <-"$10."
	
	SASiformat(studyData$STUDYID) <-"$7."	
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$MAREFID) <-"$2."
	SASiformat(studyData$MATESTCD) <-"$8."
	SASiformat(studyData$MATEST) <-"$34."
	SASiformat(studyData$MAORRES) <-"$35."
	SASiformat(studyData$MASTRESC) <-"$27."
	SASiformat(studyData$MASPEC) <-"$13."
	SASiformat(studyData$MAANTREG) <-"$1."
	SASiformat(studyData$MALAT) <-"$9."
	SASiformat(studyData$MADTC) <-"$10."	

	write_xpt(
		studyData, 
		path="ma.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/ma.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)