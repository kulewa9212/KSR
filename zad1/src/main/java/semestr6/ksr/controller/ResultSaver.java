package semestr6.ksr.controller;

import semestr6.ksr.repository.ArtykulRepository;
import semestr6.ksr.repository.SamplesRepository;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class    ResultSaver {
    long time;
    long time1;
    long time2;
    ArtykulRepository artykulRepository;
    SamplesRepository samplesRepository;
    String path;
    String[] args;
    String unikalne;
    String najmniej;
    String najwiecej;
    Map<String, Double> map1;

    public ResultSaver(long time,long time1,long time2, ArtykulRepository artykulRepository, SamplesRepository samplesRepository, String path, String[] args, Map<String, Double> map1 ) {
        this.time = time;
        this.artykulRepository = artykulRepository;
        this.samplesRepository = samplesRepository;
        this.path = path;
        this.args = args;
        this.time1 = time1;
        this.time2 = time2;
        this.map1 =map1;

    }
    public void runn() throws IOException {
        int size =samplesRepository.getLerningList().size()+samplesRepository.getValidateList().size();
        int goodresults=0;
        int badresults=0;

        for(Map.Entry<String, Integer> entry: samplesRepository.getGoodResults().entrySet()){
            goodresults = goodresults + samplesRepository.getGoodResults().get(entry.getKey());
        }
        for(Map.Entry<String, Integer> entry: samplesRepository.getBadResults().entrySet()){
            badresults = badresults + samplesRepository.getBadResults().get(entry.getKey());
        }
        double efectivity =  ((double)goodresults)/((double)goodresults + badresults)*100;
        String filepath =path + "results/KNN_results_" + String.valueOf(LocalTime.now()) + Arrays.toString(args) + ".txt";
        Files.createFile(Paths.get(filepath));
        PrintWriter printWriter = new PrintWriter(filepath);
        if(artykulRepository.getUniqueWords()!=null) {
            printWriter.print("Parametry wejściowe: \n" +
                    Arrays.toString(args) + "\n\n" +
                    "Wyniki: \n" +
                    "Sklasyfikowane próbki: " + size + " \n" +
                    "Zbiór uczący: " + samplesRepository.getLerningList().size() + "\n" +
                    "Zbiór walidacyjny: " + samplesRepository.getValidateList().size() + "\n" +
                    "Ilość unikalnych słów: " + map1.size() + "\n" +
                    "Najmniejsza liczność słowa: " + map1.values().toArray()[0] + "\n" +
                    "Najwieksza liczność słowa: " + map1.values().toArray()[map1.values().toArray().length - 1] + "\n" +
                    "Prawidłowo sklasyfikowanych: " + efectivity + "% \n" +
                    "Sczegóły klasyfikacj : " + samplesRepository.getEfficiency() + "\n" +
                    "Total time: " + time + "   extract time: " + time1 + "  KNN Time: " + time2 + "\n");
        }else{
            printWriter.print("Parametry wejściowe: \n" +
                    Arrays.toString(args) + "\n\n" +
                    "Wyniki: \n" +
                    "Sklasyfikowane próbki: " + size + " \n" +
                    "Zbiór uczący: " + samplesRepository.getLerningList().size() + "\n" +
                    "Zbiór walidacyjny: " + samplesRepository.getValidateList().size() + "\n" +
                    "Prawidłowo sklasyfikowanych: " + efectivity + "% \n" +
                    "Sczegóły klasyfikacj : " + samplesRepository.getEfficiency() + "\n" +
                    "Total time: " + time + "   extract time: " + time1 + "  KNN Time: " + time2 + "\n");

        }
        printWriter.close();

    }
}
