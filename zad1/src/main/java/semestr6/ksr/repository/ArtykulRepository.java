package semestr6.ksr.repository;

import semestr6.ksr.dom.Artykul;

import java.util.*;

public class ArtykulRepository {
    private List<Artykul> artykulList;
    private Set<String> uniqePlaces;
    private List<Artykul> lerningList;
    private List<Artykul> testList;
    private Map<String,Integer> uniqeTopics;
    private Map<String,Integer> uniqueWords;

    public ArtykulRepository(){
        this.artykulList = new ArrayList<Artykul>();
        this.uniqePlaces = new LinkedHashSet<String>();
        this.uniqeTopics = new LinkedHashMap<String, Integer>();
        this.uniqueWords = new HashMap<>();
        this.lerningList = new ArrayList<>();
        this.testList = new ArrayList<>();

        }
        public void addArtykul(Artykul artykul){
        artykulList.add(artykul);
        }
        public void addUniqeWord(String word){

            if (uniqueWords.get(word)!=null){
                uniqueWords.put(word,uniqueWords.get(word)+1);
            }
            else  {
                uniqueWords.put(word, 1);
            }
        }

    public Map<String,Integer> getUniqueWords() {
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

    public List<Artykul> getLerningList() { return lerningList; }

    public void setLerningList(List<Artykul> lerningList) { this.lerningList = lerningList; }

    public List<Artykul> getTestList() { return testList; }

    public void setTestList(List<Artykul> testList) { this.testList = testList; }
}
