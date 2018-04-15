package semestr6.ksr.dom;

import jdk.internal.org.objectweb.asm.tree.InnerClassNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Artykul {
    private Integer place;
    private Integer topic;
    private String bodyString;
    private Map<String , Integer> bodyMap ;
    private List<Integer> bodyVector ;

    public Artykul(){

        this.bodyMap = new LinkedHashMap<String, Integer>();
    }
    public Artykul(String place, String topic){

        this.bodyMap = new LinkedHashMap<String, Integer>();
    }

    public Integer getPlace() {
        return place;
    }


    public Integer getTopic() { return topic; }

    public Map<String, Integer> getBody() {
        return bodyMap;
    }

    public List<Integer> getBodyVector() {
        return bodyVector;
    }

    public void addWordToBodyMap(String word,Boolean zero){
        if (bodyMap.get(word)!=null&& zero==false){
            bodyMap.put(word,bodyMap.get(word)+1);
        }else if(bodyMap.get(word)==null&&zero==true){
            bodyMap.put(word, 0);
        }
        else if (zero==false) {
            bodyMap.put(word, 1);
        }
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public void setTopic(Integer topic) {
        this.topic = topic;
    }
    public void convertBodyToVector(){
        bodyVector = new ArrayList<Integer>();

        for (Integer number : bodyMap.values()){
            bodyVector.add(number);
        }
    }

    public String getBodyString() {
        return bodyString;
    }

    public void setBodyString(String bodyString) {
        this.bodyString = bodyString;
    }

    public  void addWordToBodyString(String next){
        bodyString = bodyString + " " + next;
    }

}
