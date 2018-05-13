package semestr6.ksr.controller;


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
    private List<String> ignoredWordsList;
    private List<String> nounsList;
    private List<String> simpleNounList;
    private List<String> adverbsList;
    private Map<String, Double> uniqeTopics;
    private Map<String, Double> uniquePlaces;
    private Extractor extractor;
    private Stemmer stemmer = new Stemmer();
    private String label;
    private Statistics statistics;
    public Parser(ArtykulRepository artykulRepository, File reutFile, File ignoredWordsFile, File nounsFile,
                  File simpleNounsFile, File adverbsFile, File topicsFile) throws IOException {
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
        this.extractor = new Extractor();
        this.label = "";
        this.statistics = new Statistics();


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
        statistics.tfidf(artykulRepository);
//        addRestWords();
    }

    private List<String> prepareIgnoredWordsList(File ignoredWordFile) throws FileNotFoundException {
        List<String> newIgnoredWordsList = new ArrayList<String>();
        Scanner scanner = new Scanner(ignoredWordFile);
        while (scanner.hasNext()) {
            newIgnoredWordsList.add(scanner.next());

        }
        return newIgnoredWordsList;
    }


    private String checkBody(String next) throws IOException {
        String places = "west-germany usa france uk canada japan";

        if (next.contains("</BODY>")) {
            if (artykul.getPlaceString() != null && !artykul.getPlaceString().equals("null")) {
                if (!places.contains(artykul.getPlaceString())) {
                    artykul = new Artykul();
                    areaFlag = "";
                } else {
                    String[] tokens = extractor.tokenizer(artykul.getBodyString().toLowerCase());
                    String[] tags = extractor.postTagger(tokens);
                    for (int i = 0; i < tokens.length; i++) {

                        if (tags[i].equals("CD")) {
                            artykul.addFeature(tags[i], false);
                            artykulRepository.addUniqeWord(tags[i]);
                        } else if (!tags[i].equals("IN") && !tokens[i].equals(",")
                                && !tokens[i].equals(".") && !tags[i].equals("POS")
                                && !tags[i].equals("DT") && !tags[i].equals("-RRB-") && !tags[i].equals("-LRB-") && !tags[i].equals("PRP")
                                && !tags[i].equals("RB") && !tokens[i].equals("'d") && !tags[i].equals(":")) {
                            //PorterStemmer porterStemmer = new PorterStemmer();
                            //String stemm = porterStemmer.stemWord(tokens[i]);
                            String stemm = stemmer.stem(tokens[i]);
                            stemm = stemm.replaceAll("[^A-Za-z0-9]","");
                            if (!ignoredWordsList.contains(stemm)) {
                                //stemm = extractor.stemmWord(stemm,1);
                                if(!ignoredWordsList.contains(stemm)) {
                                    artykul.addFeature(stemm, false);
                                    artykulRepository.addUniqeWord(stemm);
                                }
                            }
                        }
                    }
                    System.out.println("----------------------------------------------------------------");


//                    Iterator<String> iter = artykul.getFeatures().keySet().iterator();
//                    while(iter.hasNext()) {
//                        String clg = iter.next();
//                        if (artykul.getFeatures().get(clg)<2){
//                            iter.remove();
//                        }
//                    }
                   // artykul.setFeatures(statistics.normalization(artykul.getFeatures(),artykul.getFeatures().size()));
                    System.out.println(artykul.getFeatures());

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

    private String checkPlaces(String next) {
        if (next.contains("<PLACES>") && next.contains("<D>")) {
            if (next.split("<D>").length < 3) {
                next = next.split("<D>")[1].split("</D>")[0];
                artykul.setLabel(next);
                artykul.setPlaceString(next);
                System.out.println("Place: " + next);
            } else {
                artykul.setPlaceString("null");
                artykul.setLabel("null");
                System.out.println("Place: " + "null");
            }
        }

        return next;

    }

    private String checkTopics(String next) {
        if (next.contains("<TOPICS>") && next.contains(("<D>"))) {
            if (next.split("<D>").length < 3) {
                next = next.split("<D>")[1].split("</D>")[0];
                artykul.setTopic(uniqeTopics.get(next));
                System.out.println("Topic: " + next);
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

