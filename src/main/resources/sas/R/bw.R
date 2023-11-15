require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\bw_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\bw_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 16)
# skipping first row
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
df2=transform(df2, BWSEQ = as.numeric(BWSEQ))
df2=transform(df2, BWSTRESN = as.numeric(BWSTRESN))
df2=transform(df2, VISITDY = as.numeric(VISITDY))
df2=transform(df2, BWDY = as.numeric(BWDY))
# for those that are date, transform to date  
df2=transform(df2, BWDTC = as.character.Date(BWDTC))
# set labels for each field 
Hmisc::label(df2)=df[2,]

studyList <- unique(df2$STUDYID)
for(aStudy in studyList){
	# Create subdirectory, Set output files
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
	SASformat(studyData$BWTESTCD) <-"$6."
	SASformat(studyData$BWTEST) <-"$20."
	SASformat(studyData$BWORRESU) <-"$1."
	SASformat(studyData$BWSTRESU) <-"$1."
	SASformat(studyData$BWBLFL) <-"$1."
	SASformat(studyData$BWDTC) <-"$10."
	SASiformat(studyData$STUDYID) <-"$7."	
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$11."
	SASiformat(studyData$BWTESTCD) <-"$6."
	SASiformat(studyData$BWTEST) <-"$20."
	SASiformat(studyData$BWORRESU) <-"$1."
	SASiformat(studyData$BWSTRESU) <-"$1."
	SASiformat(studyData$BWBLFL) <-"$1."
	SASiformat(studyData$BWDTC) <-"$10."	
	
	write_xpt(
		studyData, 
		path="bw.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/bw.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)