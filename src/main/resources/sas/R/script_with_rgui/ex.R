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
                            endCol = 16)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, EXSEQ = as.numeric(EXSEQ))
df2=transform(df2, EXDOSE = as.numeric(EXDOSE))
df2=transform(df2, EXVAMT = as.numeric(EXVAMT))
df2=transform(df2, EXSTDY = as.numeric(EXSTDY))
# for those that are date, transform to date  
df2=transform(df2, EXSTDTC = as.character.Date(EXSTDTC))    
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
	SASformat(studyData$EXTRT) <-"$30."
	SASformat(studyData$EXDOSU) <-"$5."
	SASformat(studyData$EXDOSFRM) <-"$15."
	SASformat(studyData$EXDOSFRQ) <-"$2."
	SASformat(studyData$EXROUTE) <-"$11."
	SASformat(studyData$EXLOT) <-"$9."
	SASformat(studyData$EXTRTV) <-"$90."
	SASformat(studyData$EXVAMTU) <-"$5."
	SASformat(studyData$EXSTDTC) <-"$10."
	SASiformat(studyData$STUDYID) <-"$7."
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$EXTRT) <-"$30."
	SASiformat(studyData$EXDOSU) <-"$5."
	SASiformat(studyData$EXDOSFRM) <-"$15."
	SASiformat(studyData$EXDOSFRQ) <-"$2."
	SASiformat(studyData$EXROUTE) <-"$11."
	SASiformat(studyData$EXLOT) <-"$9."
	SASiformat(studyData$EXTRTV) <-"$90."
	SASiformat(studyData$EXVAMTU) <-"$5."
	SASiformat(studyData$EXSTDTC) <-"$10."
	
	write_xpt(
		studyData, 
		path="ex.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/ex.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)