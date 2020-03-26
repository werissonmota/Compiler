/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsintatico;

import analisadorlexico.Token;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Aurelio
 */
public class ItensSemantico {
    private ArrayList<HashMap<String, Object>> tabelasDeSimbolos;
    private ArrayList<ArrayList<Token>> arryDeArrays;
    
    public ItensSemantico(ArrayList <HashMap<String, Object>> tabelasDeSimbolos, ArrayList<ArrayList<Token>> arryDeArrays){
      this.arryDeArrays = arryDeArrays;
      this.tabelasDeSimbolos = tabelasDeSimbolos;
    }

   


    /**
     * @return the arryDeArrays
     */
    public ArrayList<ArrayList<Token>> getArryDeArrays() {
        return arryDeArrays;
    }

    /**
     * @return the tabelasDeSimbolos
     */
    public ArrayList<HashMap<String, Object>> getTabelasDeSimbolos() {
        return tabelasDeSimbolos;
    }
}
