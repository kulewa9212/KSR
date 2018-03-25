package semestr6.ksr.repository;

import semestr6.ksr.dom.Artykul;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ArtykulRepository {
    private List<Artykul> artykulList;
    private Set<String> uniqePlaces;
    private Set<String> uniqeTopics;
    private Set<String> uniqueWords;
    private List<String> ignoredWords;
    public ArtykulRepository(){
        this.artykulList=new ArrayList<Artykul>();
        this.uniqePlaces=new LinkedHashSet<String>();
        this.uniqeTopics = new LinkedHashSet<String>();
        this.uniqueWords=new LinkedHashSet<String>();
        this.ignoredWords = new ArrayList<String>();

        }
        public void addArtykul(Artykul artykul){
        artykulList.add(artykul);
        }
        public void addUniqeWord(String word){
        uniqueWords.add(word);
        }

    public Set<String> getUniqueWords() {
        return uniqueWords;
    }

    public List<Artykul> getArtykulList() {
        return artykulList;
    }
}
