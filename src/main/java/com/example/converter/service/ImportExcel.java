package com.example.converter.service;

import com.example.converter.model.Client;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ImportExcel {
    public static void importToExcel(List<Client> clientList, Path path) throws IOException {
        WritableWorkbook myFirstWbook = Workbook.createWorkbook(new File("/Users/aleksejlaskin/Documents/3/oisfl.xlsx"));
        WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

    }
}
