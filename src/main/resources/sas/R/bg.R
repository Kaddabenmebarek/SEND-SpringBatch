require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\bg_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\bg_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 19)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, BGSEQ = as.numeric(BGSEQ))
df2=transform(df2, BGSTRESN = as.numeric(BGSTRESN))  
df2=transform(df2, BGDY = as.numeric(BGDY))
df2=transform(df2, BGENDY = as.numeric(BGENDY))
# for those that are date, transform to date  
df2=transform(df2, BGDTC = as.character.Date(BGDTC))
df2=transform(df2, BGENDTC = as.character.Date(BGENDTC))
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
	SASformat(studyData$BGTESTCD) <-"$6."
	SASformat(studyData$BGTEST) <-"$20."
	SASformat(studyData$BGORRESU) <-"$1."
	SASformat(studyData$BGSTRESU) <-"$1."
	SASformat(studyData$BGDTC) <-"$10."
	SASformat(studyData$BGENDTC) <-"$10."
	SASiformat(studyData$STUDYID) <-"$7."	
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$BGTESTCD) <-"$6."
	SASiformat(studyData$BGTEST) <-"$20."
	SASiformat(studyData$BGORRESU) <-"$1."
	SASiformat(studyData$BGSTRESU) <-"$1."
	SASiformat(studyData$BGDTC) <-"$10."
	SASiformat(studyData$BGENDTC) <-"$10."
	
	write_xpt(
		studyData, 
		path="bg.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/bg.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)