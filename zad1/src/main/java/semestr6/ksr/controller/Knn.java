package semestr6.ksr.controller;

import semestr6.ksr.MetricsAndSimilars;
import semestr6.ksr.dom.DistanceResult;
import semestr6.ksr.dom.Sample;
import semestr6.ksr.repository.ArtykulRepository;
import semestr6.ksr.repository.SamplesRepository;

import java.util.*;

public class Knn {
    String[] metric;
    MetricsAndSimilars metricCalc;
    List<Double> vector1;
    List<Double> vector2;
    Map<String,Double> results = new HashMap<>();
    Map.Entry<String, Double> maxEntry = null;
    Statistics statistics;


    public Knn(String[] metric) {
        this.metric = metric;
        this.metricCalc = new MetricsAndSimilars();
        this.vector1 = new ArrayList<>();
        this.vector2 = new ArrayList<>();
        this.statistics = new Statistics();
    }
    private class DistanceComparator implements Comparator<DistanceResult>{

        @Override
        public int compare(DistanceResult o1, DistanceResult o2) {
            return o1.compareTo(o2);
        }
    }
    public void run(SamplesRepository samplesRepository) {

        for (Sample validSample : samplesRepository.getValidateList()) {
            for (Sample lerningSample : samplesRepository.getLerningList()) {
                validSample.getFeaturesToCompare().clear();
                lerningSample.getFeaturesToCompare().clear();
                if(!metric[2].equals("simsentences")){
                    for (String key : validSample.getFeatures().keySet()) {
                        validSample.getFeaturesToCompare().put(key, validSample.getFeatures().get(key));
                        if (lerningSample.getFeatures().containsKey(key)) {
                            lerningSample.getFeaturesToCompare().put(key, lerningSample.getFeatures().get(key));
                        } else {
                            lerningSample.getFeaturesToCompare().put(key, 0.0);
                        }
                    }
                    for (String key : lerningSample.getFeatures().keySet()) {
                        if (validSample.getFeatures().containsKey(key)) {
                            validSample.getFeaturesToCompare().put(key, validSample.getFeatures().get(key));
                        } else {
                            validSample.getFeaturesToCompare().put(key, 0.0);
                        }
                        lerningSample.getFeaturesToCompare().put(key, lerningSample.getFeatures().get(key));

                    }

                vector1.addAll(validSample.getFeaturesToCompare().values());
                vector2.addAll(lerningSample.getFeaturesToCompare().values());
//
//                vector1 = statistics.normalization(vector1,vector1.size());
//                vector2 = statistics.normalization(vector2,vector2.size());


//                System.out.println(vector1);
//                System.out.println(vector2);
//                System.out.println(distanceResult.getLabel()+"||"+distanceResult.getDistance());
                    validSample.getDistances().add(new DistanceResult(lerningSample.getLabel(), metricCalc.run(metric[2], vector1, vector2)));
                vector1.clear();
                vector2.clear();
                }else{
                    validSample.getDistances().add(new DistanceResult(lerningSample.getLabel(), metricCalc.run(metric[2], lerningSample.getFeatures(), validSample.getFeatures())));
                }

            }
            Collections.sort(validSample.getDistances());
            validSample.setKnnResult(calcResult(Integer.parseInt(metric[6]),validSample.getDistances()));
            validSample.setPertinence(checkResult(validSample.getLabel(),validSample.getKnnResult()));
            checkResult(validSample,samplesRepository);
//            System.out.println(validSample.getDistances().get(0).getLabel() +" || "+ validSample.getDistances().get(0).getDistance());
            validSample.getDistances().clear();

        }
        checkPresistance(samplesRepository);

    }

    private String calcResult (int numberOfNeighbors,List<DistanceResult> distances){

        results.clear();
        if(!metric[2].contains("sim")) {
            for (int i = 0; i < numberOfNeighbors; i++) {
                if (results.containsKey(distances.get(i).getLabel())) {
                    results.put(distances.get(i).getLabel(), results.get(distances.get(i).getLabel()) + 1);
                } else {
                    results.put(distances.get(i).getLabel(), 1.0);
                }
            }
        }else {
            for (int i = distances.size()-numberOfNeighbors; i < distances.size(); i++) {
                if (results.containsKey(distances.get(i).getLabel())) {
                    results.put(distances.get(i).getLabel(), results.get(distances.get(i).getLabel()) + 1);
                } else {
                    results.put(distances.get(i).getLabel(), 1.0);
                }
            }
        }
         maxEntry = null;


        for (Map.Entry<String, Double> entry : results.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }




        return maxEntry.getKey();
    }
    private boolean checkResult(String before, String after){
        if(before.equals(after)){
            return true;
        }
        return false;
    }

    private void checkResult(Sample validSample,SamplesRepository samplesRepository){
        if (validSample.isPertinence()){
            if(samplesRepository.getGoodResults().containsKey(validSample.getLabel())) {
                samplesRepository.getGoodResults().put(validSample.getLabel(),samplesRepository.getGoodResults().get(validSample.getLabel())+1);

            }else {
                samplesRepository.getGoodResults().put(validSample.getLabel(), 1);
            }
        }else{
            if(samplesRepository.getBadResults().containsKey(validSample.getLabel())) {
                samplesRepository.getBadResults().put(validSample.getLabel(),samplesRepository.getBadResults().get(validSample.getLabel())+1);
            }else {
                samplesRepository.getBadResults().put(validSample.getLabel(), 1);

            }

        }
    }

    private void checkPresistance(SamplesRepository samplesRepository){

        for (Map.Entry<String, Integer> entry : samplesRepository.getGoodResults().entrySet()) {
            Double result = 0.0;
            if (samplesRepository.getBadResults().get(entry.getKey()) != null) {
                result = (double) entry.getValue() / (samplesRepository.getBadResults().get(entry.getKey()) + entry.getValue());
                samplesRepository.getEfficiency().put(entry.getKey(),result);
            }else{
            result = (double) entry.getValue() / (entry.getValue());
            samplesRepository.getEfficiency().put(entry.getKey(),result);
            }

        }
//        System.out.println(samplesRepository.getEfficiency());
    }
}
