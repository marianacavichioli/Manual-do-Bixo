package br.com.catijr.manualdobixo;

/**
 * Created by Marcelo on 16/03/2017.
 */

public class IndexItem {
    String title;
    String fileName;
    long id = 0;

    public IndexItem(String title,String fileName){
        this.title = title;
        this.fileName = fileName;
    }

    public IndexItem(String title){
        this.title = title;
        this.fileName = null;
    }
    public IndexItem(){
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public long getId(){
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }
}
