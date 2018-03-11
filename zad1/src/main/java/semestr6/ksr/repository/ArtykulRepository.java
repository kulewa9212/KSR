package semestr6.ksr.repository;

import semestr6.ksr.dom.Artykul;

import java.util.ArrayList;
import java.util.List;

public class ArtykulRepository {
    private List<Artykul> artykulList;
    private List<String> uniqeProperties;
    private List<String> uniqueWords;
    public ArtykulRepository(){
        this.artykulList=new ArrayList<Artykul>();
        this.uniqeProperties=new ArrayList<String>();
        this.uniqueWords=new ArrayList<String>();
        }
}
