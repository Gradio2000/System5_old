package com.example.converter.service;

import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LoadFile {
    public static void loadFile(MultipartFile file) throws IOException, ClassNotFoundException {
        File newFile = File.createTempFile("temp", null, null);
        file.transferTo(newFile);

        BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(newFile.toPath()), "WINDOWS-1251"));
        String line;
        Map<Integer, Map<String, String>> fullMap = new HashMap<>();
        Map<String, String> propertyMap = null;
        Integer i = 0;
        boolean notSaved = false;
        while ((line = reader.readLine()) != null) {
            if (line.contains("<MESSAGE TYPE=\"3\"") || line.contains("COUNT_OPEN") || line.contains("</DATA>") ||
             line.contains("SABS") || line.contains("DATA FROM")){
                continue;
            }
            if (line.contains("<MESSAGE TYPE=\"1\"")){
                propertyMap = new HashMap<>();
                notSaved = true;
                continue;
            }
            else if (line.contains("/>")){
                if (!notSaved){
                    continue;
                }
                fullMap.put(i, propertyMap);
                i++;
                notSaved = false;
                continue;
            }
            String[] mass;
            mass = line.split("=");
            assert propertyMap != null;
            propertyMap.put(mass[0], mass[1].replaceAll("\"", ""));
        }
        Service.createClientList(fullMap);
    }
}
