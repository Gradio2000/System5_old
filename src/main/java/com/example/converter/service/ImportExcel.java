package com.example.converter.service;

import com.example.converter.model.Client;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ImportExcel {
    private static String filename;

    public static String getFilename() {
        return filename;
    }

    public static void importToExcel(List<Client> clientList) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("oisfl");

        int rownum = 0;
        Cell cell;
        Row row;

        row = sheet.createRow(rownum);

        Field[] fields = Client.class.getDeclaredFields();
        int i = 0;
        for (Field field : fields){
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(field.getName());
            i++;
        }


        for (Client client : clientList){
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(client.getPD_PRED());

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(client.getPD_SN());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(client.getDT_CLOSE());

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(client.getPD_SN2());

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(client.getN_SC());

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(client.getDT_ROJD());

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(client.getPD_KEM());

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(client.getDT_OPEN());

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue(client.getFIO());

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(client.getSLOVOBK());

            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue(client.getDT_REG());

            cell = row.createCell(11, CellType.STRING);
            cell.setCellValue(client.getPAN2());

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(client.getMS_ROJD());

            cell = row.createCell(13, CellType.STRING);
            cell.setCellValue(client.getPD_VDOC());

            cell = row.createCell(14, CellType.STRING);
            cell.setCellValue(client.getPD_KEM2());

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue(client.getPD_DT_VID());

            cell = row.createCell(16, CellType.STRING);
            cell.setCellValue(client.getDT_IBK());

            cell = row.createCell(17, CellType.STRING);
            cell.setCellValue(client.getPD_DT_VID2());

            cell = row.createCell(18, CellType.STRING);
            cell.setCellValue(client.getDT_VPAN());

            cell = row.createCell(19, CellType.STRING);
            cell.setCellValue(client.getPBK());

            cell = row.createCell(20, CellType.STRING);
            cell.setCellValue(client.getDPD());

            cell = row.createCell(21, CellType.STRING);
            cell.setCellValue(client.getTEL());

            cell = row.createCell(22, CellType.STRING);
            cell.setCellValue(client.getPD_VDOC2());

            cell = row.createCell(23, CellType.STRING);
            cell.setCellValue(client.getPAN());

            cell = row.createCell(24, CellType.STRING);
            cell.setCellValue(client.getDT_KPAN());

        }

        File file = File.createTempFile("oisfl",".xlsx", null);
        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        outFile.close();
        filename = file.getPath();
    }
}
