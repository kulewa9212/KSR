/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestr6.ksr;

import semestr6.ksr.controller.Extractor;
import semestr6.ksr.dom.Artykul;
import semestr6.ksr.repository.ArtykulRepository;

import java.io.IOException;
import java.util.*;

/**
 *
 * @author Ewa Kulesza
 */
public class MetricsAndSimilars {
private  double result=0;
    double counter;
    double denominator;
    double sum1;
    double sum2;
    double N;
    double maxSim;
    int nMax ;
    int nMin ;
    double sim;
    double sim1;
    int countEquals;
    int ns1;
    int ns2;
    String next;
    String gram1 ="";
    String gram2 ="";
    Stack<Double> simStack = new Stack<>();
    Stack<String> stringStack = new Stack<>();

    public Double run (String metric,List<Double> v1, List<Double> v2){

        if(metric.equals("euklidean")) { return euklidean(v1,v2); }
        if(metric.equals("taxi")){return taxi(v1,v2);}
        if(metric.equals("manhatan")){return manhatan(v1, v2);}
        if(metric.equals("simcossinus")){return cossinusAmplitude(v1, v2);}


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
//    public Double run (String metric,List<Double> v1, List<Double> v2){
//
//    }

    public Double run (String metric,Map<String, Double> v1, Map<String, Double> v2) {
        result = 0;
        denominator = 0;
        for(String entry : v1.keySet()){
            denominator = denominator + v1.get(entry);
            result= result + maxsimimalry(entry,v2.keySet())*v1.get(entry);
        }
        return result/denominator;
    }





    protected double euklidean(List<Double> v1, List<Double> v2) {
        result = 0;
        for (int i = 0; i < v1.size(); i++) {
            result += Math.abs(v1.get(i) - v2.get(i)) * Math.abs(v1.get(i) - v2.get(i));
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
        result = 0;
        for (int i = 0; i < v1.size(); i++) {
            if (result < Math.abs(v1.get(i) - (double) v2.get(i))) {
                result = Math.abs(v1.get(i) - (double) v2.get(i));
            }
        }
        return result;
    }
    protected double cossinusAmplitude(List<Double> v1, List<Double>v2){
        counter=0.0;
        denominator=0.0;
        sum1=0.0;
        sum2=0.0;
        for(int i=0; i<v1.size();i++){
            counter = counter + v1.get(i)*v2.get(i);
            sum1 = sum1 + v1.get(i)*v1.get(i);
            sum2 = sum2 + v2.get(i)*v2.get(i);

        }
        return  Math.abs(counter)/Math.sqrt(sum2*sum1);
    }
    protected double simSentences() {return 0.0;}

    public Double maxsimimalry(String string,Set<String> lemmasList) {



        next = "";
        sim=0;
        sim1=0;
        simStack.clear();
        stringStack.clear();
//        System.out.println("-----------------------------------------");
//        System.out.println(string);
//        System.out.println("-----------------------------------------");
        stringStack.push(string);


        for(String lemma :lemmasList){
            sim1 =calcRestrictNgram(1,3,string,lemma);
            if(sim1 > sim||simStack.empty()){
                simStack.push(sim1);
                stringStack.push(lemma);
                sim=sim1;
//                System.out.println(next +" = "+ sim);
            }
        }


        return simStack.pop();

    }
    private  Double calcRestrictNgram (int n1,int n2, String string1,String string2 ){
        ns1 = string1.toCharArray().length;
        ns2 = string2.toCharArray().length;
        if(n2>ns1) {
            n2 = ns1;
        }
        countEquals=0;
        if(ns1>ns2){ nMax=ns1;nMin=ns2;
        }else { nMax=ns2;nMin=ns1; }
        gram1 ="";
        gram2 ="";
        for(int i =n1 ;i<=n2;++i ){
            for(int j =0;j<=nMin-i;++j){
                if (string1.substring(j,j+i).equals(string2.substring(j,j+i))){
                    ++countEquals;
                }
            }
        }

        //  System.out.println(string1 +" | "+ string1 + " = " + result);
        return (double)(2*countEquals)/((nMax - n1 + 1)*(nMax - n1 + 2 )-( nMax - n2 + 1)*(nMax - n2));
    }
}
