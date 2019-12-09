package service;

import java.io.*;

public class Reader {

    public String readFile(String path) {
        File file = new File(path);
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            int temp;
            while ((temp = br.read()) != -1) {
                sb.append((char) temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
