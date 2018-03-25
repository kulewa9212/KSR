package semestr6.ksr;

import semestr6.ksr.controller.Parser;
import semestr6.ksr.repository.ArtykulRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

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
        File reutFile = new File(path + "reut2-000.sgm");
        File ignoredWordsFile = new File(path + "ignoredWordsList");
        File nounsFile = new File(path + "nouns.txt");
        File simpleNounsFile =  new File (path + "simpleNouns.txt");
        File adverbsFile = new File (path +"adverbs.txt");


        System.out.println( "Hello World! xxxxxx" );

        Parser parser = new Parser(artykulRepository,reutFile,ignoredWordsFile,nounsFile,simpleNounsFile,adverbsFile);
        parser.parse();
        System.out.println(parser.artykulRepository.getUniqueWords());
        System.out.println(parser.artykulRepository.getArtykulList().get(4).getBodyMother().toString());
        System.out.println(parser.artykulRepository.getUniqueWords().size());
    }

}
