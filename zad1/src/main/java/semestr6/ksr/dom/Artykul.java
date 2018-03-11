package semestr6.ksr.dom;

import java.util.List;

public class Artykul {
    private String place;
    private String body;
    private String topic;
    
    Artykul(String place,String body, String topic){
        this.place = place;
        this.topic = topic;
        this.body = body;

    }

    public String getPlace() {
        return place;
    }

    public String getBody() {
        return body;
    }

    public String getTopic() {
        return topic;
    }
}
