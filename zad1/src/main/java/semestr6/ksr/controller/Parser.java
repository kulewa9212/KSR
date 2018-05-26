package semestr6.ksr.controller;


import org.apache.lucene.analysis.hunspell.HunspellStemmer;
import semestr6.ksr.dom.Artykul;
import semestr6.ksr.repository.ArtykulRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Parser {

    public ArtykulRepository artykulRepository;
    private File reutFile;
    private String areaFlag;
    private Artykul artykul;
    private String next;
    private Set<String> ignoredWordsList;
    private List<String> nounsList;
    private List<String> simpleNounList;
    private List<String> adverbsList;
    private Map<String, Double> uniqeTopics;
    private Map<String, Double> uniquePlaces;
    private Extractor extractor;
    private Stemmer stemmer = new Stemmer();
    private String label;
    private Statistics statistics;
    private String[] args;
    private double c =0;
    public Parser(ArtykulRepository artykulRepository, File reutFile, File ignoredWordsFile, File nounsFile,
                  File simpleNounsFile, File adverbsFile, File topicsFile, String[] args) throws IOException {
        this.artykulRepository = artykulRepository;
        this.reutFile = reutFile;
        this.areaFlag = new String();
        this.artykul = new Artykul();
        this.next = new String();
        this.ignoredWordsList = prepareIgnoredWordsList(ignoredWordsFile);
//        this.nounsList = prepareIgnoredWordsList(nounsFile);
//        this.simpleNounList = prepareIgnoredWordsList(simpleNounsFile);
//        this.adverbsList = prepareIgnoredWordsList(adverbsFile);
        this.uniqeTopics = filluniqe(topicsFile);
        this.extractor = new Extractor();
        this.label = "";
        this.statistics = new Statistics();
        this.args = args;

    }

    public void parse() throws IOException {
        Scanner scanner = new Scanner(reutFile);
//        extractor.prepareCoreWords();
        while (scanner.hasNext()) {
            next = scanner.next();
            next = checkTopics(next);
            next = checkPlaces(next);
            next = checkBody(next);
            //System.out.print(artykul.getBody().toString());
        }

//        addRestWords();
    }

    private Set<String> prepareIgnoredWordsList(File ignoredWordFile) throws FileNotFoundException {
        Set<String> newIgnoredWordsList = new HashSet<>();
        Scanner scanner = new Scanner(ignoredWordFile);
        while (scanner.hasNext()) {
            newIgnoredWordsList.add(scanner.next());

        }
        return newIgnoredWordsList;
    }


    private String checkBody(String next) throws IOException {
        String places = "west-germany usa france uk canada japan";
        String stemm ="";
        if (next.contains("</BODY>")) {
            if (artykul.getPlaceString() != null && !artykul.getPlaceString().equals("null") ) {
                if (!places.contains(artykul.getPlaceString()) && args[5].equals("<PLACES>")) {
                    artykul = new Artykul();
                    areaFlag = "";
                } else {
                    String[] tokens = extractor.tokenizer(artykul.getBodyString().toLowerCase());
                    for (int i = 0; i < tokens.length; i++){
                        tokens[i]=tokens[i].replaceAll("[^A-Za-z0-9]","");
                    }
                    String[] tags = extractor.postTagger(tokens);
                    for (int i = 0; i < tokens.length; i++) {
//                        System.out.println(tokens[i] + " || " + tags[i] );
                        stemm = tokens[i];
                        stemm = tags(tags[i],stemm);
                        stemm = porter(stemm);
                        stemm = stopWords(stemm);
                        stemm = number(tags[i],stemm);
                        if(!stemm.equals("")){
                            artykul.addFeature(stemm, false);
                        }
                    }

                    artykulRepository.addArtykul(artykul);


                    artykul = new Artykul();
                    areaFlag = "";
                }
            } else {
                artykul = new Artykul();
                areaFlag = "";
            }
        } else if (areaFlag.equals("b")) {
            if (!next.equals("")) {
                artykul.addWordToBodyString(next);
            }
        } else if (next.contains("<BODY>")) {
            next = next.split("<BODY>")[1];

            if (!next.equals("")) {
                artykul.setBodyString(next);
            }
            areaFlag = "b";
        }
        return next;
    }

    private String number(String tag, String stemm ){
        if(tag.equals("CD")&& args[4].contains("NMBR")) {
            return tag;
        }
        return stemm;
    }
    private String tags(String tag, String stemm){
        if (!tag.equals("IN") && !tag.equals("PRP") && !tag.equals("DT") && !tag.equals("POS") && !tag.equals("PRP")
                && !tag.equals("RB") && !tag.equals("CC") && !tag.equals("JJ")&&!tag.equals("WRB") && args[4].contains("TG")) {
            return  stemm;
        }if(args[4].contains("TG")) {
            return "";
        }
        return stemm;
    }
    private String stopWords(String stemm){
        if (ignoredWordsList.contains(stemm) && args[4].contains("SW")){
            return "";
        }
        return stemm;
    }
    private String  porter(String stemm) throws IOException {
        if (args[4].contains("P")) {
            return stemmer.stem(stemm);
        }
        return stemm;
    }

    private String checkPlaces(String next) {
        if (next.contains(args[5]) && next.contains("<D>")) {
            if (next.split("<D>").length < 3) {
                next = next.split("<D>")[1].split("</D>")[0];
                artykul.setLabel(next);
                artykul.setPlaceString(next);
            } else {
                artykul.setPlaceString("null");
                artykul.setLabel("null");
//                System.out.println("Place: " + "null");
            }
        }

        return next;

    }

    private String checkTopics(String next) {
        if (next.contains("<Places>") && next.contains(("<D>"))) {
            if (next.split("<D>").length < 3) {
                next = next.split("<D>")[1].split("</D>")[0];
                artykul.setTopic(uniqeTopics.get(next));
//                System.out.println("Topic: " + next);
            }

        }

        return next;
    }

    Map<String, Double> filluniqe(File uniqe) throws FileNotFoundException {
        Map<String, Double> uniqeMap = new LinkedHashMap<String, Double>();
        Scanner scanner = new Scanner(uniqe);
        int i = 0;
        while (scanner.hasNext()) {
            String next = scanner.next();
            if (uniqeMap.get(next) == null) {
                uniqeMap.put(next, (double) i);
                i++;
            }
        }
        return uniqeMap;
    }


}

