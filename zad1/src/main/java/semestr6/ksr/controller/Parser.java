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

    }

    public void parse() throws FileNotFoundException {
        Scanner scanner = new Scanner(reutFile);

        while (scanner.hasNext()) {
            next = scanner.next();
            next = checkTopics(next);
            next = checkPlaces(next);
            next = checkBody(next);
            //System.out.print(artykul.getBody().toString());
        }
//        addRestWords();
    }z

    private List<String> prepareIgnoredWordsList(File ignoredWordFile) throws FileNotFoundException {
        List<String> newIgnoredWordsList=new ArrayList<String>();
        Scanner scanner = new Scanner(ignoredWordFile);
        while (scanner.hasNext()){
            newIgnoredWordsList.add( scanner.next());
        }
        return newIgnoredWordsList;
    }

    private String checkBody(String next) {


        if (next.contains("</BODY>")) {

            artykulRepository.addArtykul(artykul);
            //System.out.println(artykul.getBody().toString());
            artykul = new Artykul();
            for (String uniqeWord : artykulRepository.getUniqueWords())
            {
                artykul.addWordToBody(uniqeWord,true);
            }
            //System.out.println("-------------------------------------------");
            areaFlag = "";
        }else if (areaFlag.equals("b")) {
            //System.out.println(next);
            if(!next.equals("")) {
                {
                    TokenStream tokenStream = new StandardTokenizer(
                            Version.LUCENE_40, new StringReader(next));
                    tokenStream = new StopFilter(Version.LUCENE_40, tokenStream, EnglishAnalyzer.getDefaultStopSet());
                    tokenStream = new PorterStemFilter(tokenStream);

                    StringBuilder sb = new StringBuilder();
                    CharTermAttribute charTermAttr = tokenStream.getAttribute(CharTermAttribute.class);
                    try{
                        while (tokenStream.incrementToken()) {
                            if (sb.length() > 0) {
                                sb.append(" ");
                            }
                            sb.append(charTermAttr.toString());
                        }
                    }

                    catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                    //System.out.println(sb.toString());
                }

            }
            if(!next.equals("")) {
                artykul.addWordToBody(next,false);
                artykulRepository.addUniqeWord(next);
            }
        }else if (next.contains("<BODY>")) {
            next = next.split("<BODY>")[1].toLowerCase();
//            next=filterTheWord(next.toLowerCase());
            if(!next.equals("")) {
                {
                    TokenStream tokenStream = new StandardTokenizer(
                            Version.LUCENE_40, new StringReader(next));
                    tokenStream = new StopFilter(Version.LUCENE_40, tokenStream, EnglishAnalyzer.getDefaultStopSet());
                    tokenStream = new PorterStemFilter(tokenStream);

                    StringBuilder sb = new StringBuilder();
                    CharTermAttribute charTermAttr = tokenStream.getAttribute(CharTermAttribute.class);
                    try{
                        while (tokenStream.incrementToken()) {
                            if (sb.length() > 0) {
                                sb.append(" ");
                            }
                            sb.append(charTermAttr.toString());
                        }
                    }

                    catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                    System.out.println(sb.toString());
                }

                artykul.addWordToBody(next,false);
                artykulRepository.addUniqeWord(next);
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

