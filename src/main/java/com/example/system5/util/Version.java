package com.example.system5.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Version {
    public static String getVersion(){
        String version = null;
        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/properties.properties"))) {
            Properties prop = new Properties();
            prop.load(input);
            version = prop.getProperty("version");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return version;
    }
}
