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
public class MetricsAndSimilars {




    public Double run (String metric,List<Double> v1, List<Double> v2){

        if (metric == "euklidean") { return euklidean(v1,v2); }
        if(metric == "taxi"){return taxi(v1,v2);}
        if(metric == "manhatan"){return manhatan(v1, v2);}

//
//
//            vector1.addAll( artykul.getFeaturesToCompare().values());
//            vector2.addAll(artykul1.getFeaturesToCompare().values());
//            System.out.println(artykul.getFeaturesToCompare());
//            System.out.println("--------------------------------------------------------------------------------------");
//            System.out.println(artykul1.getFeaturesToCompare());
//            System.out.println("--------------------------------------------------------------------------------------");
//            System.out.println("--------------------------------------------------------------------------------------");
//            System.out.println("--------------------------------------------------------------------------------------");
//            System.out.println(euklidean(vector1,vector2));
//            System.out.println("--------------------------------------------------------------------------------------");
//            vector1.clear();
//            vector2.clear();





        return null;
    }






    protected double euklidean(List<Double> v1, List<Double> v2) {
        double result = 0;
        for (int i = 0; i < v1.size(); i++) {
            result += Math.abs(v1.get(i) - v2.get(i)) * Math.abs(v1.get(i) - (double)v2.get(i));
        }
        return Math.sqrt(result);
    }

    protected double taxi(List<Double> v1, List<Double> v2) {
        double result = 0;
        for (int i = 0; i < v1.size(); i++) {
            result += Math.abs(v1.get(i) - v2.get(i));
        }
        return result;
    }

    protected double manhatan(List<Double> v1, List<Double> v2) {
        double result = 0;
        for (int i = 0; i < v1.size(); i++) {
            if (result < Math.abs(v1.get(i) - (double)v2.get(i))) {
                result = Math.abs(v1.get(i) - (double)v2.get(i));
            }
        }
        return result;
    }
//    protected double cossinus(List<Double> v1, List<Double>v2){
//        doubk
//
//
//
//        return 0;
//    }

}
