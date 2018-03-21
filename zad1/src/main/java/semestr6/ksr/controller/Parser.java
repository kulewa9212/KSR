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

    public Parser(ArtykulRepository artykulRepository, File reutFile,File ignoredWordsFile) throws FileNotFoundException {
        this.artykulRepository = artykulRepository;
        this.reutFile = reutFile;
        this.areaFlag = new String();
        this.artykul = new Artykul();
        this.next = new String();
        this.ignoredWordsList = prepareIgnoredWordsList(ignoredWordsFile);
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
            System.out.println("-------------------------------------------");
            areaFlag = "";
        }else if (areaFlag.equals("b")) {
            System.out.println(next);
            next=filterTheWord(next.toLowerCase());
            if(!next.equals("")) {
                artykul.addWordToBody(next);
                artykulRepository.addUniqeWord(next);
            }
        }else if (next.contains("<BODY>")) {
            next = next.split("<BODY>")[1].toLowerCase();
            artykul.addWordToBody(next );
            artykulRepository.addUniqeWord(next);
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
    private String filterTheWord (String next){

        for(String ignoredWord : ignoredWordsList){
            if (next.equalsIgnoreCase(ignoredWord)){
                return "";
            }
        }
        if(next.matches(".*\\d+.*")){
            System.out.println("----------------Is a NUmber!!!!-----------------");
            return "";
        }else if (next.equals("...")){
            return "";
        }else if (next.equals(".")){
            return "";
        }else if (next.equals(",")){
            return  "";
        }
        else if(next.contains(".")){
            System.out.println(next);
            return next.split("\\.")[0];
        }else if(next.contains(",")){
            return  next.split(",")[0];
        }

        return next;
    }

}

