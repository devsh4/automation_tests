package lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class WriteExcel {
	
	Sheet sh;
	Workbook wb;
	
		public WriteExcel(String Path, String sheetname)
	 {
	
	try
	
		{	
	
			FileInputStream fis=new FileInputStream(Path);
			wb=WorkbookFactory.create(fis);
			sh=wb.getSheet(sheetname);
		} 
	
	catch (Exception e)
		{
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} 
	
	 }
		
		public void writeData(String Path, String sheetname, String str, int pos)
		{
		
		Row row=sh.createRow(pos);
		
		Cell cell=row.createCell(0);
		
		cell.setCellType(cell.CELL_TYPE_STRING);
		
		cell.setCellValue(""+str);
		
		try 
		{
			FileOutputStream fos=new FileOutputStream(Path);
			wb.write(fos);
			fos.close();
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		} 
	}
		
		public int getRowCount(String sheetname)
		{
			 int count=wb.getSheet(sheetname).getLastRowNum();
			 count=count+1;
			 return count;	
			
		}

		
}
