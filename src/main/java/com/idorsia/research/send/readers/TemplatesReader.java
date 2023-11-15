package com.idorsia.research.send.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.idorsia.research.send.domain.Col;
import com.idorsia.research.send.processors.Constants;

@Component("templatesReader")
@Scope("step")
public class TemplatesReader implements ItemReader<Map<String, LinkedList<Col>>> {

	private StepExecution stepExecution;
	private String input_root_path;
	private static final String TEMPLATEDONE = "templatedone";

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		try {
			this.stepExecution = stepExecution;
			FileInputStream fi = new FileInputStream(Constants.APP_PROPERTIES_PATH);
			ResourceBundle rb = new PropertyResourceBundle(fi);
			this.input_root_path = rb.getString("input_root_path");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("resource")
	public Map<String, LinkedList<Col>> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(TEMPLATEDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(TEMPLATEDONE)) {
			return null;
		}
		Map<String, LinkedList<Col>> res = new HashMap<String, LinkedList<Col>>();
		try {
			File inputFolder = new File(input_root_path);
			File[] templateFiles = inputFolder.listFiles();
			for (File template : templateFiles) {
				String domain = StringUtils.substringBefore(template.getName(), ".");
				LinkedList<Col> colList = new LinkedList<Col>();
				FileInputStream fis = new FileInputStream(template);
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				XSSFSheet sheet = wb.getSheetAt(0);
				Iterator<Row> itr = sheet.iterator();
				boolean firstRow = true;
				while (itr.hasNext()) {
					Row row = itr.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String cellValue = "";
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							cellValue = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								DateFormat df = new SimpleDateFormat(Constants.SD_FORMAT);
								Date date = cell.getDateCellValue();
								cellValue = df.format(date);
							} else {
								cellValue = String.valueOf(cell.getNumericCellValue());
								if (cell.getColumnIndex() == getColnum(colList, "USUBJID")) {
									cellValue = String.valueOf((int) cell.getNumericCellValue());
								}
							}
							break;
						default:
						}
						if (firstRow) {
							Col col = new Col();
							col.setHeader(cellValue);
							col.setRows(new LinkedList<String>());
							col.setColNum(cell.getColumnIndex());
							colList.add(col);
						} else {
							if(cellValue != null && !cellValue.isBlank())
								getCol(colList, cell.getColumnIndex()).getRows().add(cellValue);
						}
					}
					firstRow = false;
				}
				res.put(domain, colList);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		stepExecution.getJobExecution().getExecutionContext().put(TEMPLATEDONE, true);
		return res;
	}

	private Col getCol(List<Col> colSet, Integer colIdx) {
		for (Col col : colSet) {
			if (col.getColNum() == colIdx)
				return col;
		}
		return null;
	}

	private Integer getColnum(List<Col> colSet, String header) {
		for (Col col : colSet) {
			if (col.getHeader().equals(header))
				return col.getColNum();
		}
		return null;
	}

}
