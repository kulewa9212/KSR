package semestr6.ksr.dom;

import jdk.internal.org.objectweb.asm.tree.InnerClassNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Artykul {
    private Integer place;
    private Integer topic;
    private Map<String , Integer> body ;



    private List<Integer> bodyMother ;

    public Artykul(){

        this.body = new LinkedHashMap<String, Integer>();
    }
    public Artykul(String place, String topic){

        this.body = new LinkedHashMap<String, Integer>();
    }

    public Integer getPlace() {
        return place;
    }


    public Integer getTopic() { return topic; }

    public Map<String, Integer> getBody() {
        return body;
    }

    public List<Integer> getBodyMother() {
        return bodyMother;
    }

    public void addWordToBody(String word,Boolean zero){
        if (body.get(word)!=null&& zero==false){
            body.put(word,body.get(word)+1);
        }else if(body.get(word)==null&&zero==true){
            body.put(word, 0);
        }
        else if (zero==false) {
            body.put(word, 1);
        }
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public void setTopic(Integer topic) {
        this.topic = topic;
    }
    public void convertBodyToMother(){
        bodyMother = new ArrayList<Integer>();

        for (Integer number : body.values()){
            bodyMother.add(number);
        }
    }

}
