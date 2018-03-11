package semestr6.ksr;

import semestr6.ksr.controller.Parser;
import semestr6.ksr.repository.ArtykulRepository;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException


    {
        ArtykulRepository artykulRepository = new ArtykulRepository();
        File file = new File("/home/radoslaw/KSR/zad1/src/main/java/test.txt");

        System.out.println( "Hello World! xxxxxx" );

        Parser parser = new Parser(artykulRepository,file);
        parser.parse();
    }

}
