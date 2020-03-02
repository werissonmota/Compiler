/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsintatico;

import analisadorlexico.AnalisadorLexico;
import analisadorlexico.Token;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Aurelio
 */
public class AnalisadorSintatico {
   
    public void analiseSintatica() throws FileNotFoundException, IOException{
        AnalisadorLexico analisadorlexico = new AnalisadorLexico();
        ArrayList<ArrayList> arrayDeArrays = new <ArrayList>ArrayList();
        ArrayList<Token> arrayDeTokens;
        arrayDeArrays = analisadorlexico.analiseLexica();        
        Parser parser;        
        Iterator it = arrayDeArrays.iterator();
        int cont = 1;
        while(it.hasNext()){            
            arrayDeTokens = new <Token> ArrayList();
            arrayDeTokens = (ArrayList<Token>) it.next();            
            parser = new Parser(arrayDeTokens, cont);
            parser.run();
            cont ++;
        }
        
}
    public static void main(String[] args) throws FileNotFoundException, IOException {        
        AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico();
        analisadorSintatico.analiseSintatica();       
    }
}
