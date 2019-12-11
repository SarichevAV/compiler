package service;

import java.io.*;
import java.util.List;

public class Writer {
    private BufferedWriter bw;


    public void setPath(String path) throws IOException {
        File file = new File(path);
        bw = new BufferedWriter(new FileWriter(file));
    }

    public void writeString(String text, String path) throws IOException {
            setPath(path);
            bw.write(text);
            bw.flush();
    }

    public void writeList(List<String> text,String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String temp : text) {
            sb.append(temp);
            sb.append("\n");
        }
        writeString(sb.toString(),path);
    }
}
