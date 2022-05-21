package com.example.system5.service;

import com.example.system5.model.System5;
import org.springframework.stereotype.Service;

@Service
public class System5Service {
    public String getTotalMark(System5 system5){
        String[] massStr = new String[5];
        massStr[0] = system5.getRes1();
        massStr[1] = system5.getRes2();
        massStr[2] = system5.getRes3();
        massStr[3] = system5.getRes4();
        massStr[4] = system5.getRes5();

        int[] massInt = new int[5];
        for (int i = 0; i < massStr.length; i++) {
            int res = 0;
            switch (massStr[i]){
                case("A"):
                    res = 5;
                    break;
                case("B"):
                    res = 4;
                    break;
                case("C"):
                    res = 3;
                    break;
                case ("D"):
                    res = 2;
                    break;
                case ("E"):
                    res = 1;
                    break;
            }
            massInt[i] = res;
        }

        int avr = 0;
        for (int m : massInt){
            avr += m;
        }
        avr = avr / massInt.length;

        String result = null;
        switch (avr){
            case (1):
                result = "E";
                break;
            case (2):
                result = "D";
                break;
            case (3):
                result = "C";
                break;
            case (4):
                result = "B";
                break;
            case (5):
                result = "A";
                break;
        }
        return result;
    }
}
