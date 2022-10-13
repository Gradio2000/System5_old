package com.example.converter.service;

import com.example.converter.model.Client;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Service {
    public static void createClientList(Map<Integer, Map<String, String>> fullMap) throws IOException, ClassNotFoundException {
        List<Client> clientList = new ArrayList<>();
        for (Map.Entry<Integer, Map<String, String>> entry : fullMap.entrySet()){
            Map<String, String> map = entry.getValue();
            ObjectMapper objectMapper = new ObjectMapper();
            String clientJSON = objectMapper.writeValueAsString(map);
            Client client = objectMapper.readValue(clientJSON, Client.class);
            clientList.add(client);
        }
        ImportExcel.importToExcel(clientList);
    }
}
