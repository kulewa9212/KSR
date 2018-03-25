package semestr6.ksr.controller;

import semestr6.ksr.dom.Artykul;
import semestr6.ksr.repository.ArtykulRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public Parser(ArtykulRepository artykulRepository, File reutFile,File ignoredWordsFile,File nounsFile,
                  File simpleNounsFile, File adverbsFile) throws FileNotFoundException {
        this.artykulRepository = artykulRepository;
        this.reutFile = reutFile;
        this.areaFlag = new String();
        this.artykul = new Artykul();
        this.next = new String();
        this.ignoredWordsList = prepareIgnoredWordsList(ignoredWordsFile);
        this.nounsList = prepareIgnoredWordsList(nounsFile);
        this.simpleNounList = prepareIgnoredWordsList(simpleNounsFile);
        this.adverbsList = prepareIgnoredWordsList(adverbsFile);

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
        addRestWords();
    }

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
            next=filterTheWord(next.toLowerCase());
            if(!next.equals("")) {
                artykul.addWordToBody(next,false);
                artykulRepository.addUniqeWord(next);
            }
        }else if (next.contains("<BODY>")) {
            next = next.split("<BODY>")[1].toLowerCase();
            next=filterTheWord(next.toLowerCase());
            if(!next.equals("")) {
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
                artykul.setPlace(next);
                System.out.println("Place: "+next);
            }
        }

        return next;

    }

    private String checkTopics(String next) {
        if (next.contains("<TOPICS>")&& next.contains(("<D>"))) {
            if (next.split("<D>").length<3){
                next = next.split("<D>")[1].split("</D>")[0];
                artykul.setTopic(next);
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
    void addRestWords(){
        for(Artykul artykul1:artykulRepository.getArtykulList()){
            for (String word:artykulRepository.getUniqueWords()){
                artykul1.addWordToBody(word,true);
            }
            artykul1.convertBodyToMother();
        }


    }

}

