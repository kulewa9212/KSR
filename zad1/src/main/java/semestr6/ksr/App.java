package semestr6.ksr;

import semestr6.ksr.controller.ArtykulKnnPrepartor;
import semestr6.ksr.controller.Knn;
import semestr6.ksr.controller.Parser;
import semestr6.ksr.controller.ResultSaver;
import semestr6.ksr.repository.ArtykulRepository;
import semestr6.ksr.repository.SamplesRepository;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * args[0] - rodzaj pobieranych danych (text || dataset)
 * args[1] - nazwa pluku do pobrania, ktory znajduje sie w folderze files
 * args[2] - odleglość/podobieństwo (euklidean || taxi || manhatan || -
 * args[3] - rodzaj ekstrakcji cech (TF || TFIDF)
 * args[4] - rodzaj stemizacji P - porter, SW - stop words, TG -usunięcie czesci słów,
 *           NMBR - zamiana liczb na wspólny znacznik "CD" (np P-SW-TG-NMBR)
 * args[5] - etykieta <PLACES> || <TOPICS>
 * args[6] - knn
 *
 *
 */





public class App 
{

    public static void main( String[] args ) throws IOException


    {
        long startTime = System.currentTimeMillis();
        ArtykulRepository artykulRepository = new ArtykulRepository();
        String path = new File(".").getCanonicalPath()+"/src/main/java/semestr6/ksr/files/";
        File reutFile = new File(path + args[1]);
        File ignoredWordsFile = new File(path + "ignoredWordsList");
        File nounsFile = new File(path + "nouns.txt");
        File simpleNounsFile =  new File (path + "simpleNouns.txt");
        File adverbsFile = new File (path + "adverbs.txt");
        File topicsFile = new File ( path + "all-topics-strings.lc.txt");
        SamplesRepository samplesRepository = new SamplesRepository();
        System.out.println(args[1]);
        Map<String, Double> map1 = new LinkedHashMap<>() ;
        if(args[0].equals("text")) {
            Parser parser = new Parser(artykulRepository, reutFile, ignoredWordsFile, nounsFile, simpleNounsFile, adverbsFile, topicsFile,args);
            parser.parse();
            System.out.println(parser.artykulRepository.getUniqueWords().size());
            ArtykulKnnPrepartor artykulKnnPrepartor = new ArtykulKnnPrepartor(parser.artykulRepository, args);
            samplesRepository = artykulKnnPrepartor.prepareData();
            map1 =sortByValue(parser.artykulRepository.getUniqueWords());
            System.out.println(map1);

        }
            //System.out.println(parser.artykulRepository.getUniqueWords());
        //lejakSystem.out.println(parser.artykulRepository.getArtykulList().get(4).getBodyMother().toString());
        long startKnnTime = System.currentTimeMillis();
        Knn knn = new Knn(args);
        knn.run(samplesRepository);
        long stopTime = System.currentTimeMillis();
        long ekstractTime = startKnnTime - startTime;
        long knnTime = stopTime - startKnnTime;
        long runTime = stopTime - startTime;
        ResultSaver resultSaver = new ResultSaver(runTime,ekstractTime,knnTime,artykulRepository,samplesRepository,path,args,map1);
        resultSaver.runn();
//        metryki.knn("asd",parser.artykulRepository.getArtykulList().get(4));


    }


    public static Map<String, Double> sortByValue(Map<String, Double> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
        }
    }
}
