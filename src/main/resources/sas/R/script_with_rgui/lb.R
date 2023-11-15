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
                            endCol = 20)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, LBSEQ = as.numeric(LBSEQ))
df2=transform(df2, LBSTRESN = as.numeric(LBSTRESN))
df2=transform(df2, VISITDY = as.numeric(VISITDY))
df2=transform(df2, LBDY = as.numeric(LBDY))
# for those that are date, transform to date  
df2=transform(df2, LBDTC = as.character.Date(LBDTC))      
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
	SASformat(studyData$LBTESTCD) <-"$8."
	SASformat(studyData$LBTEST) <-"$40."
	SASformat(studyData$LBCAT) <-"$15."
	SASformat(studyData$LBORRESU) <-"$13."
	SASformat(studyData$LBSTRESU) <-"$13."
	SASformat(studyData$LBSTAT) <-"$1."
	SASformat(studyData$LBREASND) <-"$1."
	SASformat(studyData$LBSPEC) <-"$25."
	SASformat(studyData$LBMETHOD) <-"$1."
	SASformat(studyData$LBBLFL) <-"$1."
	SASformat(studyData$LBDTC) <-"$10."
	
	SASiformat(studyData$STUDYID) <-"$7."
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$LBTESTCD) <-"$8."
	SASiformat(studyData$LBTEST) <-"$40."
	SASiformat(studyData$LBCAT) <-"$15."
	SASiformat(studyData$LBORRESU) <-"$13."
	SASiformat(studyData$LBSTRESU) <-"$13."
	SASiformat(studyData$LBSTAT) <-"$1."
	SASiformat(studyData$LBREASND) <-"$1."
	SASiformat(studyData$LBSPEC) <-"$25."
	SASiformat(studyData$LBMETHOD) <-"$1."
	SASiformat(studyData$LBBLFL) <-"$1."
	SASiformat(studyData$LBDTC) <-"$10."
	
	write_xpt(
		studyData, 
		path="lb.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/lb.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)