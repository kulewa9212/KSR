package semestr6.ksr.controller;

import semestr6.ksr.dom.Artykul;
import semestr6.ksr.dom.Sample;
import semestr6.ksr.repository.ArtykulRepository;
import semestr6.ksr.repository.SamplesRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArtykulKnnPrepartor {
    ArtykulRepository artykulRepository;
    Set<Sample> lerningList;
    Set<Sample> validateList;
    SamplesRepository samplesRepository;
    Statistics statistics;
    String[] args;

    public ArtykulKnnPrepartor(ArtykulRepository artykulRepository, String[] args) {
        this.samplesRepository = new SamplesRepository();
        this.artykulRepository = artykulRepository;
        this.lerningList = new HashSet<>();
        this.validateList = new HashSet<>();
        this.statistics = new Statistics();
        this.args = args;
    }
    public SamplesRepository prepareData() {

        for (int i = 0; i < artykulRepository.getArtykulList().size(); i++) {
            if (i < artykulRepository.getArtykulList().size() * 0.6) {
                lerningList.add(artykulRepository.getArtykulList().get(i));
                artykulRepository.adduniqeWords(artykulRepository.getArtykulList().get(i).getFeatures());
            } else {
                artykulRepository.adduniqeWords(artykulRepository.getArtykulList().get(i).getFeatures());
                validateList.add(artykulRepository.getArtykulList().get(i));
            }
        }
        if(args[3].equals("TFIDF1")) {
            statistics.tfidf1(artykulRepository);
        }else if(args[3].equals("TFIDF")){
            statistics.tfidf(artykulRepository);
        }
        samplesRepository.setLerningList(lerningList);
        samplesRepository.setValidateList(validateList);
        return samplesRepository;
    }


// dokonczyc preparator do artykułuy , stworzyc specjalny obiekt do obliczania metryk oraz
//        repozytorium ktory te metryki przechowywuje nastepnie napisac klase która wykonuje nam knn

//
//            vector1.addAll(artykul.getBodyMapToCompare().values());
//            vector2.addAll(artykul1.getBodyMapToCompare().values());
//        for(Artykul artykul1 : artykulRepository.getArtykulList()){
//            artykul1.getFeaturesToCompare().clear();
//            artykul.getBodyMapToCompare().clear();
//            for(String key :  artykul.getBody().keySet()){
//                artykul.getBodyMapToCompare().put(key ,artykul.getBody().get(key));
//                if(artykul1.getBody().containsKey(key)){
//                    artykul1.getFeaturesToCompare().put(key,artykul1.getBody().get(key));
//                }else{
//                    artykul1.getFeaturesToCompare().put(key,0);
//                }
//
//            }
//            for(String key :  artykul1.getBody().keySet()){
//                if(artykul.getBody().containsKey(key)){
//                    artykul.getBodyMapToCompare().put(key,artykul.getBody().get(key));
//                }else{
//                    artykul.getBodyMapToCompare().put(key,0);
//                }
//                artykul1.getFeaturesToCompare().put(key,artykul1.getBody().get(key));
//
//            }

    }

