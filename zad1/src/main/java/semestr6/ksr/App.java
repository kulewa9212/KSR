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
        String path = new File(".").getCanonicalPath();
        File file = new File(path + "/src/main/java/semestr6/ksr/reut2-000.sgm");

        System.out.println( "Hello World! xxxxxx" );

        Parser parser = new Parser(artykulRepository,file);
        parser.parse();
        System.out.println(parser.artykulRepository.getUniqueWords());
        System.out.println(parser.artykulRepository.getArtykulList().get(0).getBody().toString());
    }

}
