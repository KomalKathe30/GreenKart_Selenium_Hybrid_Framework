package utils;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.util.*;

public class ExcelUtils {

    public static List<String[]> getTestSteps(String testCase){

        List<String[]> steps = new ArrayList<>();

        try{
            FileInputStream fis = new FileInputStream("data/testdata.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            for(int i=1;i<=sheet.getLastRowNum();i++){

                if(sheet.getRow(i) == null) continue;

                String tc = sheet.getRow(i).getCell(0).getStringCellValue();

                if(tc.equalsIgnoreCase(testCase)){

                    String keyword = sheet.getRow(i).getCell(1).getStringCellValue();

                    String data = "";
                    Cell cell = sheet.getRow(i).getCell(2);

                    if(cell != null){
                        if(cell.getCellType() == CellType.NUMERIC){
                            data = String.valueOf((int)cell.getNumericCellValue()); // ✅ FIX
                        } else {
                            data = cell.toString();
                        }
                    }

                    steps.add(new String[]{keyword, data});
                }
            }

            wb.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return steps;
    }
}