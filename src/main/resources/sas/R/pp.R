require(XLConnect)
require(haven)
require(SASxport)
require(utils)
if(packageVersion("SASxport") < "1.5.7") {
	stop("version 1.5.7 or later of SASxport is requiered")
}

# Set output files
mainDir <- dirname('C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\pp_StudyData.xlsx')
# Read in XLSX file
df <- readWorksheetFromFile(file = 'C:\\Idorsia_WS\\SEND-SpringBatch\\project\\src\\main\\resources\\sas\\outputs\\pp_StudyData.xlsx',
		sheet=1,
		startRow = 1,
		endCol = 18)
df2 = df[-1,]
df2 = df2[-1,]

# for those that are num, transform to numeric
df2=transform(df2, PPSEQ = as.numeric(PPSEQ))
df2=transform(df2, PPSTRESN = as.numeric(PPSTRESN))
# df2=transform(df2, VISITDY = as.numeric(VISITDY))
df2=transform(df2, PPNOMDY = as.numeric(PPNOMDY))
# for those that are date, transform to date  
df2=transform(df2, PPRFTDTC = as.character.Date(PPRFTDTC))    

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
	SASformat(studyData$DOMAIN) <-"$2."	

	write_xpt(
		studyData, 
		path="pp.xpt", 
		version=5)

	aLine <- paste("Created file:  ", mainDir, "/",aStudy,"/pp.xpt",sep = "")
	print(aLine)
}
setwd(mainDir)