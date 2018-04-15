package semestr6.ksr.controller;


import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import semestr6.ksr.dom.Artykul;
import semestr6.ksr.repository.ArtykulRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class Parser {

    public ArtykulRepository artykulRepository;
    private File reutFile;
    private String areaFlag;
    private Artykul artykul;
    private String next;
    private List<String> ignoredWordsList;
    private List<String> nounsList;
    private List<String> simpleNounList;
    private List<String> adverbsList;
    private Map<String,Integer> uniqeTopics;
    private Map<String,Integer> uniquePlaces;
    private Extractor extractor;

    public Parser(ArtykulRepository artykulRepository, File reutFile,File ignoredWordsFile,File nounsFile,
                  File simpleNounsFile, File adverbsFile, File topicsFile) throws FileNotFoundException {
        this.artykulRepository = artykulRepository;
        this.reutFile = reutFile;
        this.areaFlag = new String();
        this.artykul = new Artykul();
        this.next = new String();
        this.ignoredWordsList = prepareIgnoredWordsList(ignoredWordsFile);
        this.nounsList = prepareIgnoredWordsList(nounsFile);
        this.simpleNounList = prepareIgnoredWordsList(simpleNounsFile);
        this.adverbsList = prepareIgnoredWordsList(adverbsFile);
        this.uniqeTopics = filluniqe(topicsFile);
        this.extractor =  new Extractor();

    }

    public void parse() throws IOException {
        Scanner scanner = new Scanner(reutFile);
        //extractor.prepareCoreWords();
        while (scanner.hasNext()) {
            next = scanner.next();
            next = checkTopics(next);
            next = checkPlaces(next);
            next = checkBody(next);
            //System.out.print(artykul.getBody().toString());
        }
//        addRestWords();
    }

    private List<String> prepareIgnoredWordsList(File ignoredWordFile) throws FileNotFoundException {
        List<String> newIgnoredWordsList=new ArrayList<String>();
        Scanner scanner = new Scanner(ignoredWordFile);
        while (scanner.hasNext()){
            newIgnoredWordsList.add( scanner.next());
        }
        return newIgnoredWordsList;
    }

    private String checkBody(String next) throws IOException {


        if (next.contains("</BODY>")) {




            //extractor.sentenceDetect(artykul.getBodyString());}
            String[] tokens = extractor.tokenizer(artykul.getBodyString().toLowerCase());
            String[] tags = extractor.postTagger(tokens);
            //String[] lemmas = extractor.lemmatizer(tokens,tags);

            for (int i=0 ; i<tokens.length; i++){

                if(tags[i].equals("CD")){
                    artykul.addWordToBodyMap(tags[i],false);
                }
                else if(!tags[i].equals("IN") && !tokens[i].equals(",") && !tokens[i].equals(".") && !tags[i].equals("POS")
                        && !tags[i].equals("DT")&& !tags.equals("-RRB-")&& !tags.equals("-LRB-") && !tags.equals("PRP$")
                        && !tags.equals("RB") ){
                    artykul.addWordToBodyMap(extractor.stemmWord(tokens[i]),false);
                }
            }

            System.out.println("----------------------------------------------------------------");
            System.out.println(artykul.getBody());


            artykulRepository.addArtykul(artykul);



            artykul = new Artykul();


//            for (String uniqeWord : artykulRepository.getUniqueWords())
//            {
//             //   artykul.addWordToBodyMap(uniqeWord,true);
//            }
            //System.out.println("-------------------------------------------");
            areaFlag = "";
        }else if (areaFlag.equals("b")) {

            //System.out.println(next);
            if(!next.equals("")) {
                artykul.addWordToBodyString(next);
                //artykul.addWordToBodyMap(next,false);
                artykulRepository.addUniqeWord(next);
            }
        }else if (next.contains("<BODY>")) {
            next = next.split("<BODY>")[1];

//            next=filterTheWord(next.toLowerCase());
            if(!next.equals("")) {
                next = extractor.stemmWord(next);
                artykul.setBodyString(next);
            }
            areaFlag = "b";}
        return next;
    }

    private String checkPlaces(String next) {
        if (next.contains("<PLACES>")&& next.contains("<D>")) {
            if (next.split("<D>").length<3){
                next = next.split("<D>")[1].split("</D>")[0];
                artykul.setPlace(uniqeTopics.get(next));
                System.out.println("Place: "+next);
            }
        }

        return next;

    }

    private String checkTopics(String next) {
        if (next.contains("<TOPICS>")&& next.contains(("<D>"))) {
            if (next.split("<D>").length<3){
                next = next.split("<D>")[1].split("</D>")[0];
                artykul.setTopic(uniqeTopics.get(next));
                System.out.println("Topic: " + next);
            }

        }

    return next;
    }
    private String filterTheWord (String next) {

        if (next.matches(".*\\d+.*")) {
            //System.out.println("----------------Is a NUmber!!!!-----------------");
            return "";
        } else if (next.equals("...")) {
            return "";
        } else if (next.equals(".")) {
            return "";
        } else if (next.equals(",")) {
            return "";
        }
        if (next.contains(",")) {
            if (next.split(",").length < 2) {
                next = next.split(",")[0];
            }
        }
        if (next.contains(".")) {
            if (next.split("\\.").length < 2) {
                next = next.split("\\.")[0];
            }
        }
        if (next.contains("'s")) {
            next = next.split("'s")[0];

        }

        for (String noun : ignoredWordsList) {
            if (next.equals(noun.toLowerCase())) {
                return "";
            }
        }

        for (String noun : artykulRepository.getUniqueWords()) {
            if (next.equals(noun.toLowerCase())) {
                return next;
            }
        }

        for (String noun : simpleNounList) {
            if (next.equals(noun.toLowerCase())) {
                return next;
            }
        }
        for (String adverb : adverbsList) {
            if (next.equals(adverb.toLowerCase())) {
                return "";
            }
        }
        for (String noun : nounsList) {
            if (next.equals(noun.toLowerCase())) {
                return next;
            }
        }
        return "";
    }
//    void addRestWords(){
//        for(Artykul artykul1:artykulRepository.getArtykulList()){
//            for (String word:artykulRepository.getUniqueWords()){
//                artykul1.addWordToBody(word,true);
//            }
//            artykul1.convertBodyToMother();
//        }
//
//
//    }

    Map<String,Integer> filluniqe(File  uniqe) throws FileNotFoundException {
        Map<String,Integer> uniqeMap = new LinkedHashMap<String, Integer>();
        Scanner scanner = new Scanner(uniqe);
        int i =0;
        while (scanner.hasNext()){
            String next = scanner.next();
            if(uniqeMap.get(next)==null)
            {
                uniqeMap.put(next,i);
                i++;
            }
        }
        return  uniqeMap;
    }



}

