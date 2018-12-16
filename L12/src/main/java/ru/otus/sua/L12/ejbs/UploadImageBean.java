package ru.otus.sua.L12.ejbs;


import org.omnifaces.util.Utils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class UploadImageBean implements Serializable {

    private Part file;
    private byte[] content;
    private boolean isUploaded;

    public void read() throws IOException {
        content = Utils.toByteArray(file.getInputStream());
        isUploaded = true;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public byte[] getContent() {
        return content;
    }

    public byte[] getContentAndReset() {
        byte[] ret = content;
        content = null;
        file = null;
        isUploaded = false;
        return ret;
    }


    public boolean isUploaded(){
        return isUploaded;
    }
}