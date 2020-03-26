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
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Aurelio
 */
public class AnalisadorSintatico {
   
    public ItensSemantico analiseSintatica() throws FileNotFoundException, IOException{        
        ArrayList<HashMap<String,Object>> arraysDeTabelaDeSimbolos = new <HashMap<String,Object>> ArrayList();
        HashMap<String,Object> tabSimbolos;
        AnalisadorLexico analisadorlexico = new AnalisadorLexico();
        ArrayList<ArrayList<Token>> arrayDeArrays = new <ArrayList<Token>>ArrayList();
        ArrayList<Token> arrayDeTokens;
        arrayDeArrays = analisadorlexico.analiseLexica();        
        Parser parser;        
        Iterator it = arrayDeArrays.iterator();
        int cont = 1;
        while(it.hasNext()){            
            arrayDeTokens = new <Token> ArrayList();
            arrayDeTokens = (ArrayList<Token>) it.next();            
            parser = new Parser(arrayDeTokens, cont);
            tabSimbolos = parser.run();
            arraysDeTabelaDeSimbolos.add(tabSimbolos);
            cont ++;
        }
        return new ItensSemantico(arraysDeTabelaDeSimbolos, arrayDeArrays);
}
    
}
