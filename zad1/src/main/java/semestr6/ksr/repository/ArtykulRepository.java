package semestr6.ksr.repository;

import semestr6.ksr.dom.Artykul;

import java.util.*;

public class ArtykulRepository extends SamplesRepository{
    private List<Artykul> artykulList;
    private Set<String> uniqePlaces;
    private Map<String,Integer> uniqeTopics;
    private Map<String,Double> uniqueWords;


    public ArtykulRepository(){
        this.artykulList = new ArrayList<Artykul>();
        this.uniqePlaces = new LinkedHashSet<String>();
        this.uniqeTopics = new LinkedHashMap<String, Integer>();
        this.uniqueWords = new HashMap<>();



        }
        public void addArtykul(Artykul artykul){
        artykulList.add(artykul);
        }
        public void addUniqeWord(String word){

            if (uniqueWords.get(word)!=null){
                uniqueWords.put(word,uniqueWords.get(word)+1.0);
            }
            else  {
                uniqueWords.put(word, 1.0);
            }
        }

    public Map<String,Double> getUniqueWords() {
        return uniqueWords;
    }

    public void adduniqeWords(Map<String,Double> artykulBody){
        for(Map.Entry<String,Double> entry : artykulBody.entrySet()){
            if(uniqueWords.get(entry.getKey())!=null){
                uniqueWords.put(entry.getKey(), uniqueWords.get(entry.getKey()) + entry.getValue());
            }else{
                uniqueWords.put(entry.getKey(),entry.getValue());
            }
        }
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
