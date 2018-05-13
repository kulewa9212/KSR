package semestr6.ksr.controller;

import semestr6.ksr.dom.Artykul;
import semestr6.ksr.dom.Sample;
import semestr6.ksr.repository.ArtykulRepository;
import semestr6.ksr.repository.SamplesRepository;

import java.util.ArrayList;
import java.util.List;

public class ArtykulKnnPrepartor {
    ArtykulRepository artykulRepository;
    List<Sample> lerningList;
    List<Sample> validateList;
    SamplesRepository samplesRepository;

    public ArtykulKnnPrepartor(ArtykulRepository artykulRepository) {
        this.samplesRepository = new SamplesRepository();
        this.artykulRepository = artykulRepository;
        this.lerningList = new ArrayList<>();
        this.validateList = new ArrayList<>();

        prepareData();
    }
    public SamplesRepository prepareData() {

        for (int i = 0; i < artykulRepository.getArtykulList().size(); i++) {
            if (i < artykulRepository.getArtykulList().size() * 0.6) {
                lerningList.add(artykulRepository.getArtykulList().get(i));
            } else {
                validateList.add(artykulRepository.getArtykulList().get(i));
            }
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

