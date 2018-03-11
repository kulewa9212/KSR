package semestr6.ksr.controller;

import semestr6.ksr.repository.ArtykulRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    private ArtykulRepository artykulRepository;
    private File file;
    boolean bodyFlag = false;

    public Parser(ArtykulRepository artykulRepository,File file){
        this.artykulRepository = artykulRepository;
        this.file = file;
    }
    public void parse() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext() == true) {
            String next = scanner.next();
            next=checkBODYspace(next);
            if (bodyFlag == true) {
                System.out.println(next);
        }

        }
    }
        private String checkBODYspace (String next ){
            if (
                next.contains("<BODY>")){
            bodyFlag=true;
            next=next.split("<BODY>")[1];
        }
            if(next.contains("</BODY>")){
                bodyFlag=false;
            }
            return next;
        }
    }

