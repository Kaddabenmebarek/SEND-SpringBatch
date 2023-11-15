require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\pc_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\pc_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 27)
df2 = df[-1,]
df2 = df2[-1,]
# for those that are num, transform to numeric
  df2=transform(df2, PCSEQ = as.numeric(PCSEQ))
  df2=transform(df2, PCSTRESN = as.numeric(PCSTRESN))
  df2=transform(df2, PCLLOQ = as.numeric(PCLLOQ))
  df2=transform(df2, PCNOMDY = as.numeric(PCNOMDY))
  df2=transform(df2, PCTPTNUM = as.numeric(PCTPTNUM))
  df2=transform(df2, VISITDY = as.numeric(VISITDY))
# for those that are date, transform to date  
df2=transform(df2, PCDTC = as.character.Date(PCDTC))
#df2=transform(df2, PCRFTDTC = as.character.Date(PCRFTDTC))
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
	SASformat(studyData$USUBJID) <-"$10."
	SASformat(studyData$PCTESTCD) <-"$7."
	SASformat(studyData$PCTEST) <-"$20."
	SASformat(studyData$PCCAT) <-"$10."
	SASformat(studyData$PCORRES) <-"$20."
	SASformat(studyData$PCORRESU) <-"$5."
	SASformat(studyData$PCSTRESC) <-"$10."
	SASformat(studyData$PCSTRESU) <-"$5."
	SASformat(studyData$PCSTAT) <-"$20."
	SASformat(studyData$PCNAM) <-"$40."
	SASformat(studyData$PCSPEC) <-"$20."
	SASformat(studyData$PCMETHOD) <-"$20."
	SASformat(studyData$PCBLFL) <-"$3."
	SASformat(studyData$PCDTC) <-"$20."
	SASformat(studyData$PCNOMLBL) <-"$20."
	SASformat(studyData$PCTPT) <-"$10."
	SASformat(studyData$PCELTM) <-"$10."
	SASformat(studyData$PCTPTREF) <-"$20."
	
	SASiformat(studyData$STUDYID) <-"$7."
	SASiformat(studyData$DOMAIN) <-"$2."
	SASiformat(studyData$USUBJID) <-"$10."
	SASiformat(studyData$PCTESTCD) <-"$7."
	SASiformat(studyData$PCTEST) <-"$20."
	SASiformat(studyData$PCCAT) <-"$10."
	SASiformat(studyData$PCORRES) <-"$20."
	SASiformat(studyData$PCORRESU) <-"$5."
	SASiformat(studyData$PCSTRESC) <-"$10."
	SASiformat(studyData$PCSTRESU) <-"$5."
	SASiformat(studyData$PCSTAT) <-"$20."
	SASiformat(studyData$PCNAM) <-"$40."
	SASiformat(studyData$PCSPEC) <-"$20."
	SASiformat(studyData$PCMETHOD) <-"$20."
	SASiformat(studyData$PCBLFL) <-"$3."
	SASiformat(studyData$PCDTC) <-"$20."
	SASiformat(studyData$PCNOMLBL) <-"$20."
	SASiformat(studyData$PCTPT) <-"$10."
	SASiformat(studyData$PCELTM) <-"$10."
	SASiformat(studyData$PCTPTREF) <-"$20."
	
	write_xpt(
		studyData, 
		path="pc.xpt", 
		version=5)	
	
	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/pc.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)