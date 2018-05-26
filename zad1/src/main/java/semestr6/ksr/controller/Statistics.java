package semestr6.ksr.controller;

import semestr6.ksr.dom.Artykul;
import semestr6.ksr.dom.Sample;
import semestr6.ksr.repository.ArtykulRepository;
import semestr6.ksr.repository.SamplesRepository;

import java.util.*;

public class Statistics {

    public Statistics() {

    }

    double getMean(List<Double> data,int size) {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }

    double getVariance(List<Double> data,int size) {
        double mean = getMean(data,size);
        double temp = 0;
        for(double a :data)
            temp += (a-mean)*(a-mean);
        return temp/(size-1);
    }

    public double getStdDev(List<Double> data,int size) {
        return Math.sqrt(getVariance(data,size));
    }

//    public double median() {
//        Collections.sort(data);
//
//        if (data.size() % 2 == 0) {
//            return (data.get((data.size() / 2) - 1) + data.get(data.size() / 2)) / 2.0;
//        }
//        return data.get(data.size() / 2);
//    }

    public Map<String,Double> normalization(Map<String,Double> data,int size ){
        List<Double> dataValues = new ArrayList<>();
        dataValues.addAll(data.values());
        double mean = getMean( dataValues,size);
        double stdDev = getStdDev(dataValues,size);
        double result = 0.0;
        Map<String,Double> normData = new LinkedHashMap<>();
        for(String x : data.keySet()){
            result =(data.get(x)-mean)/stdDev;
//            if(result>=-2&&result<=2){
                normData.put(x,result);
//            }

        }
        return normData;
    }

    public void tfidf(ArtykulRepository artykulRepository){
        int d = artykulRepository.getUniqueWords().size();
        Map<String,Double> newMap = new LinkedHashMap<>();
        Double result=0.0;
        Double count =0.0;
        Map<String,Double> wordsCounts = new LinkedHashMap<>();
        for(String key : artykulRepository.getUniqueWords().keySet()) {
            count=0.0;
            for (Artykul artlykul1 : artykulRepository.getArtykulList()) {
                if (artlykul1.getFeatures().containsKey(key)) {
                    count = count + 1.0;
                }
            }
            wordsCounts.put(key,count);
        }

        for(Artykul artykul : artykulRepository.getArtykulList()){
            for(String key  :artykul.getFeatures().keySet()){
                if(artykulRepository.getUniqueWords().get(key) != null){
                    result = (artykul.getFeatures().get(key) / artykulRepository.getUniqueWords().get(key)) * (d / wordsCounts.get(key));
                }else{
                    if(wordsCounts.get(key)!=null) {

                        result = (artykul.getFeatures().get(key)) * d ;

                    }

                }
                newMap.put(key,result);
                result=0.0;
            }
            artykul.setFeatures(newMap);
            newMap= new LinkedHashMap<>();
        }


    }
    public void tfidf1(ArtykulRepository artykulRepository){
        int d = artykulRepository.getUniqueWords().size();
        Map<String,Double> newMap = new LinkedHashMap<>();
        Double result=0.0;
        Double count =0.0;
        Map<String,Double> wordsCounts = new LinkedHashMap<>();


        for(Artykul artykul : artykulRepository.getArtykulList()){
            for(String key  :artykul.getFeatures().keySet()){
                result = artykul.getFeatures().get(key)/(artykulRepository.getUniqueWords().get(key)*artykulRepository.getUniqueWords().get(key));

                newMap.put(key,result);
            }
            artykul.setFeatures(newMap);
            newMap= new LinkedHashMap<>();
        }


    }


}