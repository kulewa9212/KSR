package semestr6.ksr.repository;


import semestr6.ksr.dom.Sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SamplesRepository {
    List<Sample> lerningList;
    List<Sample> validateList;
    Map<String,Integer> goodResults;
    Map<String,Integer>  badResults;
    Map<String,Double> efficiency;

    public  SamplesRepository(List<Sample> lerningList, List<Sample> validateList){
        this.lerningList = lerningList;
        this.validateList = validateList;
    }

    public SamplesRepository() {
        this.lerningList = new ArrayList<>();
        this.validateList = new ArrayList<>();
        this.goodResults = new HashMap<>();
        this.badResults = new HashMap<>();
        this.efficiency = new HashMap<>();

    }

    public List<Sample> getLerningList() {
        return lerningList;
    }

    public void setLerningList(List<Sample> lerningList) {
        this.lerningList = lerningList;
    }

    public List<Sample> getValidateList() {
        return validateList;
    }

    public void setValidateList(List<Sample> validateList) {
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
