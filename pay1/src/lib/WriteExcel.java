package lib;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class WriteExcel{
	
	Sheet sh;
	Workbook wb; 
	
	public WriteExcel(String Path, String sheetname)
	{
				try
					{
						FileInputStream fis = new FileInputStream(Path);
						wb = WorkbookFactory.create(fis);
						sh = wb.getSheet(sheetname);
					}
				catch (Exception e)
					{
						System.out.println(e.getMessage());
					}
	}
  

   public void writeData(String Path, String sheetname, int userRow, int userCell, String message)
   {
	   Row row = sh.getRow(userRow);
	   /*CellStyle style = wb.createCellStyle();
	   Font font = wb.createFont();
	   font.setColor((short)8);
	   style.setFont(font);*/
    
	   try{
		  
		   Cell cell = row.getCell(userCell);
		  
		   //IF CELL IS BLANK
		   if(cell	==	null){
			   cell = row.createCell(userCell);
		   }

		   cell.setCellValue(message);
	   	}
	   catch (NullPointerException e) {
		   System.out.println("Null Pointer Exception !!!");
	   }
    

	   try {
		   FileOutputStream fos = new FileOutputStream(Path);
		   wb.write(fos);
		   fos.close();
	   }
	   catch (Exception e){
		   System.out.println(e.getMessage());
	   }
  }
  
   
  /*public void writeData1(String Path, String sheetname, int userRow, int userCell, String message)
  {
    Row row = sh.getRow(userRow);
    

    CellStyle style = wb.createCellStyle();
    Font font = wb.createFont();
    font.setColor((short)10);
    style.setFont(font);
    
    try
    {
      Cell cell = row.getCell(userCell);
      
      cell.setCellStyle(style);
      cell.setCellType(1);
      cell.setCellValue(message);
    }
    catch (NullPointerException e)
    {
      System.out.println("Null Pointer Exception !!!");
    }
    

    try
    {
      FileOutputStream fos = new FileOutputStream(Path);
      wb.write(fos);
      fos.close();
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }}*/
 

  public int getRowCount(int sheetIndex)
  {
    int count = wb.getSheetAt(sheetIndex).getLastRowNum();
    
    count++;
    
    return count;
  }
}