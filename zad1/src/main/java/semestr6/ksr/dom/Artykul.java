package semestr6.ksr.dom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Artykul extends Sample {
    private Double place;

    private String placeString;
    private Double topic;
    private String topicString;
    private String bodyString;
    private List<Double> bodyVector ;

    public Artykul(){
        this.features = new LinkedHashMap<String, Double>();
    }


    public Double getPlace() {
        return place;
    }


    public Double getTopic() { return topic; }

    public List<Double> getBodyVector() {
        return bodyVector;
    }

    public void addFeature(String word, Boolean zero){
        if (features.get(word)!=null&& zero==false){
            features.put(word, features.get(word)+1);
        }else if(features.get(word)==null&&zero==true){
            features.put(word, 0.0);
        }
        else if (zero==false) {
            features.put(word, 1.0);
        }
    }


    public void setPlace(Double place) {
        this.place = place;
    }

    public void setTopic(Double topic) {
        this.topic = topic;
    }
    public void convertBodyToVector(){
        bodyVector = new ArrayList<Double>();

        for (Double number : features.values()){
            bodyVector.add(number);
        }
    }
    public String getPlaceString() {
        return placeString;
    }

    public void setPlaceString(String placeString) {
        this.placeString = placeString;
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

    public void setBodyVector(List<Double> bodyVector) {
        this.bodyVector = bodyVector;
    }

    public String getTopicString() {
        return topicString;
    }

    public void setTopicString(String topicString) {
        this.topicString = topicString;
    }
}
