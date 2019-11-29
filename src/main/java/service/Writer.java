package service;

import java.io.*;
import java.util.List;

public class Writer {
    private BufferedWriter bw;

    public void setPath(String path) throws IOException {
        File file = new File(path);
        bw = new BufferedWriter(new FileWriter(file));
    }

    public void write(String text) {
        try {
            bw.write(text);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(List<String> text) {
        for (String temp : text) {
            write(temp);
            write("\n");
        }
    }
}
