package com.idorsia.research.send.writers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.idorsia.research.send.processors.Constants;

public class DataExcelWriter {

    private Workbook workbook;
    private CellStyle dataCellStyle;
    private int currRow = 0;
	
    public void addHeaders(Sheet sheet, String[] header) {
        Workbook wb = sheet.getWorkbook();
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);
        Row row = sheet.createRow(0);
        int col = 0;
        for (String h : header) {
            Cell cell = row.createCell(col);
            cell.setCellValue(h);
            cell.setCellStyle(style);
            col++;
        }
        this.currRow++;
    }
    
    public void addDescriptions(Sheet sheet, String[] descriptions) {
        Workbook wb = sheet.getWorkbook();
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);
        Row row = sheet.createRow(2);
        int col = 0;
        for (String d : descriptions) {
            Cell cell = row.createCell(col);
            cell.setCellValue(d);
            cell.setCellStyle(style);
            col++;
        }
        this.currRow++;
    }    
 
    public void initDataStyle() {
        dataCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        dataCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        dataCellStyle.setFont(font);
    }
 
    public void createStringCell(Row row, String val, int col) {
        Cell cell = row.createCell(col);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        if(val == null) {
        	cell.setCellValue("");
        }else {
        	cell.setCellValue(val);
        }
    }
 
    public void createNumericCell(Row row, Double val, int col) {
        Cell cell = row.createCell(col);
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(val);
    }
    
    public void createDateCell(Sheet sheet, Row row, Object val, int col) {
    	Workbook wb = sheet.getWorkbook();
    	CellStyle cellStyle = wb.createCellStyle();
    	CreationHelper createHelper = wb.getCreationHelper();
    	cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(Constants.SD_FORMAT_MIN));
    	Cell cell = row.createCell(col);
    	SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT);
        Date d=null;
        try {
            d= sdf.parse((String) val);
        } catch (ParseException e) {
            d=null;
            e.printStackTrace();
        }    	
    	cell.setCellValue(d);
    	cell.setCellStyle(cellStyle);
    }

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public CellStyle getDataCellStyle() {
		return dataCellStyle;
	}

	public void setDataCellStyle(CellStyle dataCellStyle) {
		this.dataCellStyle = dataCellStyle;
	}

	public int getCurrRow() {
		return currRow;
	}

	public void setCurrRow(int currRow) {
		this.currRow = currRow;
	}
    
}
