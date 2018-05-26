package semestr6.ksr.repository;


import semestr6.ksr.dom.Sample;

import java.util.*;

public class SamplesRepository {
    Set<Sample> lerningList;
    Set<Sample> validateList;
    Map<String,Integer> goodResults;
    Map<String,Integer>  badResults;
    Map<String,Double> efficiency;

    public  SamplesRepository(Set<Sample> lerningList, Set<Sample> validateList){
        this.lerningList = lerningList;
        this.validateList = validateList;
    }

    public SamplesRepository() {
        this.lerningList = new HashSet<>();
        this.validateList = new HashSet<>();
        this.goodResults = new HashMap<>();
        this.badResults = new HashMap<>();
        this.efficiency = new HashMap<>();

    }

    public Set<Sample> getLerningList() {
        return lerningList;
    }

    public void setLerningList(Set<Sample> lerningList) {
        this.lerningList = lerningList;
    }

    public Set<Sample> getValidateList() {
        return validateList;
    }

    public void setValidateList(Set<Sample> validateList) {
        this.validateList = validateList;
    }

    public Map<String, Integer> getGoodResults() {
        return goodResults;
    }

    public void setGoodResults(Map<String, Integer> goodResults) {
        this.goodResults = goodResults;
    }

    public Map<String, Integer> getBadResults() {
        return badResults;
    }

    public void setBadResults(Map<String, Integer> badResults) {
        this.badResults = badResults;
    }

    public Map<String, Double> getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Map<String, Double> efficiency) {
        this.efficiency = efficiency;
    }
}
