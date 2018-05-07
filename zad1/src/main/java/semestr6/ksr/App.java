package semestr6.ksr;

import semestr6.ksr.controller.ArtykulKnnPrepartor;
import semestr6.ksr.controller.Knn;
import semestr6.ksr.controller.Parser;
import semestr6.ksr.repository.ArtykulRepository;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 *
 */





public class App 
{

    public static void main( String[] args ) throws IOException


    {

        ArtykulRepository artykulRepository = new ArtykulRepository();
        String path = new File(".").getCanonicalPath()+"/src/main/java/semestr6/ksr/files/";
        File reutFile = new File(path + "reut2-001.sgm");
        File ignoredWordsFile = new File(path + "ignoredWordsList");
        File nounsFile = new File(path + "nouns.txt");
        File simpleNounsFile =  new File (path + "simpleNouns.txt");
        File adverbsFile = new File (path +"adverbs.txt");
        File topicsFile = new File ( path + "all-topics-strings.lc.txt");


        Parser parser = new Parser(artykulRepository,reutFile,ignoredWordsFile,nounsFile,simpleNounsFile,adverbsFile,topicsFile);
        parser.parse();
        //System.out.println(parser.artykulRepository.getUniqueWords());
        Map<String, Integer> map1 =sortByValue(parser.artykulRepository.getUniqueWords());
        System.out.println(map1);
        //lejakSystem.out.println(parser.artykulRepository.getArtykulList().get(4).getBodyMother().toString());
        System.out.println(parser.artykulRepository.getUniqueWords().size());
        ArtykulKnnPrepartor artykulKnnPrepartor = new ArtykulKnnPrepartor(parser.artykulRepository);
        Knn knn = new Knn("euklidean");
        knn.run(artykulKnnPrepartor.prepareData());


//        metryki.knn("asd",parser.artykulRepository.getArtykulList().get(4));


    }


    public static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
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
