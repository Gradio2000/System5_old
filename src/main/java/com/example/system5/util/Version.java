package com.example.system5.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Version {
    public static String getVersion(){
        URL url = Version.class.getClassLoader().getResource("properties.properties");
        String version = null;
        try {
            assert url != null;
            try (InputStream input = Files.newInputStream(Paths.get(url.toURI()))) {
                Properties prop = new Properties();
                prop.load(input);
                version = prop.getProperty("version");
            }
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
        return version;
    }
}
