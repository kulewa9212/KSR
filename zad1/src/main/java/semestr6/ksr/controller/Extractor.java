package semestr6.ksr.controller;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

import java.io.*;
import java.util.*;

public class Extractor {
    String path = new File(".").getCanonicalPath()+"/src/main/java/semestr6/ksr/files/";
    InputStream posModelIn = new FileInputStream(path+"en-pos-maxent.bin");
    POSModel posModel = new POSModel(posModelIn);
    POSTaggerME posTagger = new POSTaggerME(posModel);
    File lemmas = new File(path + "en-lemmatizer.dict");
    Stack<Double> simStack = new Stack<>();
    Stack<String> stringStack = new Stack<>();
    int nMax ;
    int nMin ;
    double sim;
    double sim1;
    int countEquals;
    int ns1;
    int ns2;
    String gram1 ="";
    String gram2 ="";
    Scanner scanner;
    public Extractor() throws IOException {

    }

    /**
     * This method is used to detect sentences in a paragraph/string
     * @throws InvalidFormatException
     * @throws IOException
     */
    public String[] sentenceDetect(String  bodyString) throws IOException,InvalidFormatException {
//        String path = new File(".").getCanonicalPath()+"/src/main/java/semestr6/ksr/files/";
//         refer to model file "en-sent,bin", available at link http://opennlp.sourceforge.net/models-1.5/

        //System.out.println(bodyString);
        InputStream is = new FileInputStream(path+"en-sent.bin");
        SentenceModel model = new SentenceModel(is);
        // feed the model to SentenceDetectorME class
        SentenceDetectorME sdetector = new SentenceDetectorME(model);
        // detect sentences in the paragraph
        String sentences[] = sdetector.sentDetect(bodyString);
        // print the sentences detected, to console
        for(int i=0;i<sentences.length-1;i++){
            System.out.println(sentences[i]);
            System.out.println("----------------------------------------");
        }
        is.close();
        return sentences;
    }


    public String[] tokenizer(String  bodyString) throws IOException {
       // String path = new File(".").getCanonicalPath()+"/src/main/java/semestr6/ksr/files/";
        InputStream modelIn = null;

        try {
            modelIn = new FileInputStream(path+"en-token.bin");
            TokenizerModel model = new TokenizerModel(modelIn);
            TokenizerME tokenizer = new TokenizerME(model);
            String tokens[] = tokenizer.tokenize(bodyString);
            double tokenProbs[] = tokenizer.getTokenProbabilities();

//            System.out.println("Token\t: Probability\n-------------------------------");
//            for(int i=0;i<tokens.length;i++){
//            //    System.out.println(tokens[i]+"\t: "+tokenProbs[i]);
//            }
            modelIn.close();
            return tokens;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void prepareCoreWords() throws IOException {
        //String path = new File(".").getCanonicalPath()+"/src/main/java/semestr6/ksr/files/";
        File lemmas = new File(path + "ignoredWordsList");
        Scanner scanner = new Scanner(lemmas);
        String next = "";

        Set<String> newLemmas = new HashSet<String>();
        while (scanner.hasNext()) {
            next = scanner.next().toLowerCase();
            newLemmas.add(next);
            next = scanner.next();
//            next = scanner.next();

        }
        try (FileWriter file = new FileWriter("lemma.txt")) {

            for (String next1 :newLemmas) {  // Note: added a i++
                file.write(next1+"\n");
                // file.close();   <---- NOPE: don't do this

            }
            file.close();
        }


    }
    public String[] postTagger(String[] tokens) throws IOException {


        String tags[] = posTagger.tag(tokens);
//        for(int i =0 ;i<tags.length;i++){
//            System.out.println(tokens[i]+"  = " + tags[i]);
//        }
        return tags;
    }






}
