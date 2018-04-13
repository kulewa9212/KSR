package semestr6.ksr.repository;

import semestr6.ksr.dom.Artykul;

import java.util.*;

public class ArtykulRepository {
    private List<Artykul> artykulList;
    private Set<String> uniqePlaces;
    private Map<String,Integer> uniqeTopics;
    private Set<String> uniqueWords;

    public ArtykulRepository(){
        this.artykulList=new ArrayList<Artykul>();
        this.uniqePlaces=new LinkedHashSet<String>();
        this.uniqeTopics = new LinkedHashMap<String, Integer>();
        this.uniqueWords=new LinkedHashSet<String>();

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

    public void setUniqePlaces(Set<String> uniqePlaces) {
        this.uniqePlaces = uniqePlaces;
    }

    public Map<String, Integer> getUniqeTopics() {
        return uniqeTopics;
    }

    public void setUniqeTopics(Map<String, Integer> uniqeTopics) {
        this.uniqeTopics = uniqeTopics;
    }

    public Set<String> getUniqePlaces() {
        return uniqePlaces;
    }


}
