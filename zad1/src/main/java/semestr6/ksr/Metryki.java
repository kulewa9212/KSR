/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestr6.ksr;

import semestr6.ksr.dom.Artykul;
import semestr6.ksr.repository.ArtykulRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ewa Kulesza
 */
public class Metryki {




    public String knn (ArtykulRepository artykulRepository, String metric, Artykul artykul){
        List<Integer> vector1 = new ArrayList<>();
        List<Integer> vector2 = new ArrayList<>();



        for(Artykul artykul1 : artykulRepository.getArtykulList()){
            artykul1.getBodyMapToCompare().clear();
            artykul.getBodyMapToCompare().clear();
            for(String key :  artykul.getBody().keySet()){
                artykul.getBodyMapToCompare().put(key ,artykul.getBody().get(key));
                if(artykul1.getBody().containsKey(key)){
                    artykul1.getBodyMapToCompare().put(key,artykul1.getBody().get(key));
                }else{
                    artykul1.getBodyMapToCompare().put(key,0);
                }

            }
            for(String key :  artykul1.getBody().keySet()){
                if(artykul.getBody().containsKey(key)){
                    artykul.getBodyMapToCompare().put(key,artykul.getBody().get(key));
                }else{
                    artykul.getBodyMapToCompare().put(key,0);
                }
                artykul1.getBodyMapToCompare().put(key,artykul1.getBody().get(key));

            }
            vector1.addAll( artykul.getBodyMapToCompare().values());
            vector2.addAll(artykul1.getBodyMapToCompare().values());
            System.out.println(artykul.getBodyMapToCompare());
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println(artykul1.getBodyMapToCompare());
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println(euklidean(vector1,vector2));
            System.out.println("--------------------------------------------------------------------------------------");
            vector1.clear();
            vector2.clear();

        }



        return null;
    }




    protected double euklidean(List<Integer> v1, List<Integer> v2) {
        double result = 0;
        for (int i = 0; i < v1.size(); i++) {
            result += Math.abs(v1.get(i) - v2.get(i)) * Math.abs(v1.get(i) - v2.get(i));
        }
        return Math.sqrt(result);
    }

    protected double taxi(double[] v1, double[] v2) {
        double result = 0;
        for (int i = 0; i < v1.length; i++) {
            result += Math.abs(v1[i] - v2[i]);
        }
        return result;
    }

    protected double manhattan(double[] v1, double[] v2) {
        double result = 0;
        for (int i = 0; i < v1.length; i++) {
            if (result < Math.abs(v1[i] - v2[i])) {
                result = Math.abs(v1[i] - v2[i]);
            }
        }
        return result;
    }

}
