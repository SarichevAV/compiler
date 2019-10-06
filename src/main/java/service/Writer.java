package service;

import java.io.*;

public class Writer {
    private File file;

    public Writer(String path) {
        file = new File(path);
    }

    public void write(String text) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(text);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
