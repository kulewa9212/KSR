package semestr6.ksr.dom;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Artykul {
    private String place;
    private String topic;
    private Map<String , Integer> body ;

    public Artykul(){
        this.place= "";
        this.topic= "";
        this.body = new LinkedHashMap<String, Integer>();
    }
    public Artykul(String place, String topic){
        this.place = place;
        this.topic = topic;
        this.body = new LinkedHashMap<String, Integer>();
    }

    public String getPlace() {
        return place;
    }


    public String getTopic() {
        return topic;
    }

    public Map<String, Integer> getBody() {
        return body;
    }

    public void addWordToBody(String word){
        if (body.get(word)!=null){
            body.put(word,body.get(word)+1);
        }else {
            body.put(word, 1);
        }
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}
