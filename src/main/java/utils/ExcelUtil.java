package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.formula.functions.Rows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sus.api.scm.BaseSetUp;


public class ExcelUtil {

	public ArrayList<String> getData(String testcase, String sheetname) throws IOException {
		ArrayList<String> ar = new ArrayList<String>();
		
		FileInputStream fis = new FileInputStream(BaseSetUp.testDataPath + "\\TestData.xlsx");
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int iSheetCount = workbook.getNumberOfSheets();

		for (int i = 0; i <iSheetCount; i++) {
			if (workbook.getSheetAt(i).getSheetName().equalsIgnoreCase(sheetname)) {
				XSSFSheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rows = sheet.iterator();
				Row firstRow = rows.next();

				int k = 0;
				int column = 0;

				Iterator<Cell> cells = firstRow.cellIterator();
				while (cells.hasNext()) {
					Cell value = cells.next();
					if (value.getStringCellValue().equalsIgnoreCase("TestCases")) {
						column = k;
					}
					k++;
				}
	
				while (rows.hasNext()) {
					Row r = rows.next();
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testcase)) {

						Iterator<Cell> cv = r.cellIterator();

						while (cv.hasNext()) {

							Cell c = cv.next();
							if (c.getCellTypeEnum() == CellType.STRING) {
								ar.add(c.getStringCellValue());
							} else {

								ar.add(NumberToTextConverter.toText(c.getNumericCellValue()));

							}
						}
					}

				}
			}

		}
		return ar;
	}

}
