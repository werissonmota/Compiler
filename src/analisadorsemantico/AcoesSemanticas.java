/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsemantico;

import analisadorlexico.Token;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author Aurelio
 */
public class AcoesSemanticas {
    
    File arquivoSaida;
    ArrayList<Token> tokens;
    Stack<Token> pilhaTokens;
    String nomeArquivo;
    FileOutputStream fos;
    Token token;    
    HashMap<String, Object> tabSimbolos;
    Stack<String> escopo;
    String escopoTemp;
    ArrayList paramsStrTemporarios;

    public AcoesSemanticas(ArrayList<Token> arrayDeTokens, int num, HashMap <String,Object>tabSimbolos) throws FileNotFoundException {        
        pilhaTokens = new <Token>Stack();
        this.tabSimbolos = tabSimbolos;
        this.tokens = arrayDeTokens;
        this.arquivoSaida = new File("output semantico" + "/" + "saida" + num + ".txt");
        this.fos = new FileOutputStream(arquivoSaida);
        this.escopo = new <String>Stack();
        this.escopo.push("global");
        
        
        Iterator it = tabSimbolos.keySet().iterator();
        while(it.hasNext()){
            String d;
            d = (String) it.next();
            System.out.println(d);
        } 
        
    }
    
    
    
    public void run() throws IOException {
        // passar array de tokens para a pilha   
        Collections.reverse(tokens);
        Iterator it = tokens.iterator();
        while (it.hasNext()) {
            Token t = (Token) it.next();
            pilhaTokens.push((Token) t);
        }
        token = proximoToken();
        start();
        if (arquivoSaida.length() == 0) {
            semErrosSemanticos();
        }
        fechaArquivos();
    }
    
    
    /**
     * Método que devolve o proximo token a ser verificado
     *
     * @return Token a ser verificado
     */
    private Token proximoToken() {
        return (pilhaTokens.isEmpty()) ? null : pilhaTokens.pop();
    }

    /**
     * Método que escreve no arquivo caso não ocorra erros semânticos
     *
     * @throws IOException
     */
    private void semErrosSemanticos() throws IOException {
        fos.write("Sucess! No semantic errors".getBytes());
    }

    /**
     * Método que fecha o arquivo de saida
     *
     * @throws IOException
     */
    private void fechaArquivos() throws IOException {
        fos.close();
    }

    /**
     * Método que escreve o erro no arquivo de saída
     *
     * @param linha linha do respectivo erro
     * @param erro mensaem de erro
     * @throws IOException
     */
    private void setErro(int linha, String erro) throws IOException {
        fos.write("Line ".getBytes());
        fos.write(String.valueOf(linha).getBytes());
        fos.write(" ".getBytes());
        fos.write("semantic error: ".getBytes());
        fos.write(erro.getBytes());
        fos.write("\n".getBytes());
    }

    /**
     * Método que escreve o erro no arquivo de saída
     *
     * @param erro mensagem de erro a ser escrita
     * @throws IOException
     */
    private void setErro(String erro) throws IOException {
        fos.write("Semantic error: ".getBytes());
        fos.write(erro.getBytes());
        fos.write("\n".getBytes());
    }

    /**
     * Método que verfica se um token é um tipo primitivo
     *
     * @param t token a ser analisado
     * @return true se for primitivo e falso caso contrário
     */
    private boolean isType(Token t) {
        return (t.getLexema().equals("int") || t.getLexema().equals("real") || t.getLexema().equals("boolean") || t.getLexema().equals("string")) ? true : false;

    }
    
    private boolean varExist(String id, String escopo){
        tabSimbolos.get(escopo);
        return true;
    }
    
    private String parametros(ArrayList e){
        String aux = "";
        Iterator it = e.iterator();
        while(it.hasNext()){
            aux = aux+(String)it.next();
        }
        return aux;
    }
//********************** INICIO DOS PROCEDIMENTOS ***************************************

    private void start() throws IOException {
        System.out.println("oooooooooooooooooooooooi");
        globalValues();       
        functionsProcedures();
    }
//********************** GLOBAL VALUES *****************************************************
//                  DECLARAÇÃO DE VARIÁVEIS 

    private void globalValues() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("var")) {
            token = proximoToken();
            System.out.println("oooooooooooooooooooooooi");
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();                
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("const")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                constValuesDeclaration();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

        } else if (token.getLexema().equals("const")) {
            token = proximoToken();

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                constValuesDeclaration();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("var")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

        } else if (token.getLexema().equals("function") || token.getLexema().equals("procedures")) {
            // vazio
        } else {
            setErro(token.getLinha(), "var, const, function or procedures expected");
        }

    }

//********** CONST VALUES DECLARATION **********************************************************************
    private void constValuesDeclaration() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (isType(token)) {
            token = proximoToken();
            constValuesAtribuition();
            constMoreAtribuition();

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals(";")) {
                token = proximoToken();
                constValuesDeclaration();

            } else {
                //erro sintatico
            }
        } else if (token.getLexema().equals("}")) {
            //vazio
        } else {
            //erro sintatico
        }

    }
//********** CONST VALUES ATRIBUITION *****************************************************************

    private void constValuesAtribuition() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getTipo().equals("IDE")) {
            token = proximoToken();
        } else {
            //erro sintatico
        }
        
        if (token == null) {
            //erro sintatico
        } else if (token.getLexema().equals("=")) {
            token = proximoToken();
            valueConst();
        } else {
           //erro sintatico
        }
    }

//********** VALUE CONST ******************************************************************************
    private void valueConst() throws IOException {
        if (token == null) {
           //erro sintatico
        } else if (token.getTipo().equals("NRO")) {
            token = proximoToken();
        } else if (token.getTipo().equals("CDC")) {
            token = proximoToken();
        } else if (token.getLexema().equals("true") || token.getLexema().equals("false")) {
            token = proximoToken();
        } else {
            //erro sintatico
        }
    }
//********** CONST MORE ATRIBUITION ********************************************************************

    private void constMoreAtribuition() throws IOException {
        if (token == null) {
          //erro sintatico
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            constValuesAtribuition();
        } else if (token.getLexema().equals(";")) {
            //vazio
        } else {
            //erro sintatico
        }
    }
//********** VAR VALUES DECLARATION *********************************************************************

    private void varValuesDeclaration() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (isType(token)) {
            token = proximoToken();
            varValuesAtribuition();            
            varMoreAtribuition();          

            if (token == null) {
                //erro sintatico
            } else if (token.getLexema().equals(";")) {
                token = proximoToken();
                varValuesDeclaration();

            } else {
                //erro sintatico
            }
        } else if (token.getLexema().equals("typedef")) {
            token = proximoToken();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("struct")) {
                token = proximoToken();
                ideStruct();
                varValuesDeclaration();
            } else {
                //erro sintatico
            }

        } else if (token.getLexema().equals("struct")) {
            token = proximoToken();
            ideStruct();        
            varValuesDeclaration();
        } else if (token.getLexema().equals("}")) {
            // vazio  
        } else {
            //erro sintatico
  
        }

    }

//********** VAR VALUES ATRIBUITION *********************************************************************
    private void varValuesAtribuition() throws IOException {
        if (token == null) {
            //erro sintatico
        } else if (token.getTipo().equals("IDE")) {
            if(! varExist(token.getLexema(), escopo.peek())){
               setErro(token.getLinha(),"Var "+token.getLexema()+"has already been declared in the scope: "+escopo.peek()); 
            }
            token = proximoToken();           
            arrayVerification();
        } else {
            //erro sintatico            
        }
    }
//********** VAR MORE ATRIBUITION ***********************************************************************

    private void varMoreAtribuition() throws IOException {
        if (token == null) {
            //erro sintatico
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            varValuesAtribuition();
            varMoreAtribuition();
        } else if (token.getLexema().equals(";")) {
            // vazio           
        } else {
            //erro sintatico
        }
    }
//********** VAR ARRAY VERIFICATION *********************************************************************     

    private void arrayVerification() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("[")) {
            token = proximoToken();

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getTipo().equals("NRO")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
            } else if (token.getLexema().equals("]")) {
                token = proximoToken();
                arrayVerification();
            } else {
                //erro sintatico
            }
        } else if (token.getLexema().equals(",") || token.getLexema().equals(";")) {
            // vazio
        } else {
            //erro sintatico
        }

    }
//********** IDE STRUCT *********************************************************************************

    private void ideStruct() throws IOException {
        if (token == null) {
            //erro sintatico
        } else if (token.getTipo().equals("IDE")) {
            token = proximoToken();
            ideStruct2();
        } else {
            //erro sintatico
        }
    }
//********** IDE STRUCT 2 *******************************************************************************

    private void ideStruct2() throws IOException {
        if (token == null) {
           //erro sintatico
            return;
        } else if (token.getLexema().equals("{")) {
            token = proximoToken();

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("var")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();
            } else {
               //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }
        } else if (token.getLexema().equals("extends")) {
            token = proximoToken();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getTipo().equals("IDE")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("var")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }
        }
    }

//********** FUNCTIONS PROCEDURES ***********************************************************************
    private void functionsProcedures() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("function")) {
            token = proximoToken();
            if (token == null) {
                //erro sintatico
                return;
            } else if (isType(token) || token.getTipo().equals("IDE")) {                
                token = proximoToken();
            } else {
                //erro sintatico
            }
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getTipo().equals("IDE")) {
                escopoTemp = token.getLexema();
                token = proximoToken();
            } else {
                //erro sintatico
            }
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("(")) {
                token = proximoToken();
                paramsStrTemporarios = new ArrayList();
                paramList();
                escopo.push(escopoTemp+parametros(paramsStrTemporarios)); 
                System.out.println(escopo.peek());
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("var")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
                commands();
                returns();
                
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
                functionsProcedures();

            } else {
                //erro sintatico
            }
        } else if (token.getLexema().equals("procedure")) {
            token = proximoToken();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getTipo().equals("IDE") || token.getLexema().equals("start")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("(")) {
                token = proximoToken();
                paramsStrTemporarios = new ArrayList();
                paramList();
                escopo.push(escopoTemp+parametros(paramsStrTemporarios));
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("var")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
                commands();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
                functionsProcedures();
            } else {
                //erro sintatico
            }
        } else {
            //erro sintatico
        }
    }
//*************** PARAM LIST **********************************************************************

    private void paramList() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (isType(token)) {
            paramsStrTemporarios.add("@"+token.getLexema());
            token = proximoToken();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getTipo().equals("IDE")) {
                token = proximoToken();
                moreParam();
            } else {
                //erro sintatico   
            }
        } else if (token.getLexema().equals(")")) {
            // vazio
        } else {
            //erro sintatico
        }

    }
//*************** MORE PARAM **********************************************************************

    private void moreParam() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            paramList();
        } else if (token.getLexema().equals(")")) {
            // vazio
        } else {
            //erro sintatico
        }
    }
//*************** COMMANDS **********************************************************************

    private void commands() throws IOException {
        if (token == null) {
            setErro("Commands expected");
            return;
        } else if (token.getLexema().equals("if")) {
            ifStatemant();
            commands();
        } else if (token.getLexema().equals("while")) {
            whileStatemant();
            commands();
        } else if (token.getLexema().equals("read")) {
            readStatemant();
            commands();
        } else if (token.getLexema().equals("print")) {
            printStatemant();
            commands();
        } else if (token.getTipo().equals("IDE") || token.getLexema().equals("local") || token.getLexema().equals("global")) {
            assignment();
            commands();
        } else if (token.getLexema().equals("}") || token.getLexema().equals("return")) {
            //vazio
        }
    }
//*************** COMMANDS EXPRESSIONS  **********************************************************************

    private void commandsExp() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else {
            relationalExp();          
            optLogicalExp();
        }
    }
//*************** RETURNS **********************************************************************

    private void returns() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("return")) {
            token = proximoToken();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("true") || token.getLexema().equals("false") || token.getTipo().equals("NRO")) {
                
                token = proximoToken();
            } else {
                relationalExp();
            }
        } else {
            //erro sintatico
        }

        if (token == null) {
            //erro sintatico
        } else if (token.getLexema().equals(";")) {
            token = proximoToken();
        } else {
            //erro sintatico
        }

    }

//*************** IF STATEMANT **********************************************************************
    private void ifStatemant() throws IOException {

        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("if")) {
            token = proximoToken();

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("(")) {
                token = proximoToken();
                commandsExp();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            }
            if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("then")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                commands();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
                elseStatemant();
            } else {
                //erro sintatico
            }

        }
    }
//*************** ELSE STATEMANT ***********************************************************

    private void elseStatemant() throws IOException {

        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("else")) {
            token = proximoToken();

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                commands();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

        } else if (token.getLexema().equals("return") || token.getLexema().equals("}")) {
            //vazio  
        } else {
            //erro sintatico
        }
    }
//*************** WHILE STATEMANT ***********************************************************

    private void whileStatemant() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("while")) {
            token = proximoToken();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("(")) {
                token = proximoToken();
                commandsExp();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                commands();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }
        }
    }
//*************** READ STATEMANT **********************************************************************

    private void readStatemant() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("read")) {
            token = proximoToken();
        } else {
            //erro sintatico
        }

        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("(")) {
            token = proximoToken();
            readParams();
        } else {
            //erro sintatico
        }

        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(")")) {
            token = proximoToken();

        } else {
            //erro sintatico
        }

        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(";")) {
            token = proximoToken();

        } else {
            //erro sintatico
        }

    }

    private void readParams() throws IOException {
        callVariable();
        moreReadParams();
    }

    private void moreReadParams() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            readParams();
        } else if (token.getLexema().equals(")")) {
            //vazio
        } else {
            //erro sintatico
        }

    }

//*************** PRINT STATEMANT **********************************************************
    private void printStatemant() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("print")) {
            token = proximoToken();
        }

        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("(")) {
            token = proximoToken();
            printParams();
        } else {
            //erro sintatico
        }

        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(")")) {
            token = proximoToken();

        } else {
            //erro sintatico
        }

        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(";")) {
            token = proximoToken();

        } else {
            //erro sintatico
        }

    }

    private void printParams() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        }
        printParam();
        morePrintParams();

    }

    private void printParam() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getTipo().equals("CDC")) {
            token = proximoToken();
        } else if (token.getTipo().equals("IDE") || token.getLexema().equals("local") || token.getLexema().equals("global")) {
            callVariable();
        } else {
            //erro sintatico
        }
    }

    private void morePrintParams() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            printParams();
        } else if (token.getLexema().equals(")")) {
            //vazio
        } else {
            //erro sintatico
        }
    }
//*************** ASSIGNMENT **********************************************************************

    private void assignment() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getTipo().equals("IDE") || token.getLexema().equals("local") || token.getLexema().equals("global")) {
            callVariable();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("=")) {
                token = proximoToken();
                assign2();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
            } else if (token.getLexema().equals(";")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

        } else if (token.getLexema().equals("global") || token.getLexema().equals("local")
                || token.getTipo().equals("IDE") || token.getLexema().equals("++") || token.getLexema().equals("--")
                || token.getTipo().equals("NRO") || token.getLexema().equals("true") || token.getLexema().equals("false")
                || token.getLexema().equals("!")) {
            unaryOp();

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals(";")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }
        } else {

        }

    }

    private void assign2() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getTipo().equals("IDE") || token.getLexema().equals("global") || token.getLexema().equals("local")) {
            token = proximoToken();
        } else if (token.getTipo().equals("CDC")) {
            token = proximoToken();
        } else if (token.getTipo().equals("IDE") || token.getLexema().equals("++") || token.getLexema().equals("--")
                || token.getLexema().equals("true") || token.getLexema().equals("false")
                || token.getLexema().equals("!")) {
            expression();
        } else if (token.getTipo().equals("NRO")) {
            token = proximoToken();
        } else {
            //erro sintatico
        }
    }

//*************** EXPRESSION **********************************************************************
    private void expression() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
            // logical ou aritmetic
        }
        logicalExp();

    }

//*************** RELATIONAL EXPRESSION ***********************************************************    
    private void relationalExp() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("(")) {
            token = proximoToken();
            logicalExp();

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }
        } else {
            aritmeticExp();
            possRelExp();
        }
    }

//*************** OPT LOGICAL EXPRESSION ***********************************************************   
    private void optLogicalExp() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("&&") || token.getLexema().equals("||")) {
            token = proximoToken();
            logicalExp();
        } else if (token.getLexema().equals(")")) {
            //vazio  
        } else {
            //erro sintatico
        }
    }

//*************** LOGICAL EXPRESSION ***************************************************************     
    private void logicalExp() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        }
        relationalExp();
        optLogicalExp();
    }

//*************** POSS REL EXPRESSION ***************************************************************
    private void possRelExp() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(">") || token.getLexema().equals("<") || token.getLexema().equals(">=")
                || token.getLexema().equals("<=")) {
            token = proximoToken();
            aritmeticExp();
            inequalityExp();

        } else if (token.getLexema().equals("!=") || token.getLexema().equals("==")) {
            token = proximoToken();
            aritmeticExp();
            inequalityExp();
            equalityExp();

        } else {
            //erro sintatico
        }
    }

//*************** EQUALITY EXPRESSION ***************************************************************
    private void equalityExp() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("!=") || token.getLexema().equals("==")) {
            token = proximoToken();
            aritmeticExp();
            inequalityExp();
            equalityExp();
        } else if (token.getLexema().equals(")") || token.getLexema().equals("&&") || token.getLexema().equals("||")) {
            // vazio
        } else {
            //erro sintatico
        }

    }

//*************** INEQUALITY EXPRESSION ***************************************************************
    private void inequalityExp() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(">") || token.getLexema().equals("<") || token.getLexema().equals(">=")
                || token.getLexema().equals("<=")) {
            token = proximoToken();
            aritmeticExp();
            inequalityExp();
            equalityExp();
        } else if (token.getLexema().equals(")") || token.getLexema().equals("&&") || token.getLexema().equals("||")
                || token.getLexema().equals("==") || token.getLexema().equals("!=")) {
            // vazio
        } else {
            //erro sintatico
        }
    }

//*************** ARITIMETIC EXPRESSION ***************************************************************
    private void aritmeticExp() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
            //primeiro unary Op
        } else if (token.getLexema().equals("(")) {
            token = proximoToken();
            relationalExp();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }

        } else {
            operation();
            opSum();
        }
    }

//*************** OPERATION ***************************************************************************
    private void operation() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        }
        opUnary();
        opMultiplication();
    }

//*************** OP SUM  *****************************************************************************
    private void opSum() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("+") || token.getLexema().equals("-")) {
            token = proximoToken();
            operation();
            opSum();
        } else if (token.getLexema().equals("global") || token.getLexema().equals("local")
                || token.getTipo().equals("IDE") || token.getLexema().equals("++") || token.getLexema().equals("--")
                || token.getTipo().equals("NRO") || token.getLexema().equals("true") || token.getLexema().equals("false")
                || token.getLexema().equals("!") || token.getLexema().equals("==") || token.getLexema().equals("!=")
                || token.getLexema().equals(">") || token.getLexema().equals("<") || token.getLexema().equals(">=")
                || token.getLexema().equals("<=") || token.getLexema().equals(")") || token.getLexema().equals(";")) {

            //vazio           
        } else {
            //erro sintatico
        }
    }

//*************** OP MULTIPLICATION *******************************************************************
    private void opMultiplication() throws IOException {
        if (token == null) {
            //erro sintatico
            return;

        } else if (token.getLexema().equals("/") || token.getLexema().equals("*")) {
            token = proximoToken();
            opUnary();
            opMultiplication();
        } else if (token.getLexema().equals("+") || token.getLexema().equals("-")
                || token.getLexema().equals("==") || token.getLexema().equals("!=")
                || token.getLexema().equals("/") || token.getLexema().equals("*")
                || token.getLexema().equals(")")) {
            //vazio
        } else {
            //erro sintatico
        }
    }

//*************** OP UNARY ***************************************************************************
    private void opUnary() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("global") || token.getLexema().equals("local")
                || token.getTipo().equals("IDE") || token.getLexema().equals("++") || token.getLexema().equals("--")
                || token.getTipo().equals("NRO") || token.getLexema().equals("true") || token.getLexema().equals("false")
                || token.getLexema().equals("!")) {

            if (token.getLexema().equals("global") || token.getLexema().equals("local") || token.getTipo().equals("NRO")
                    || token.getLexema().equals("true") || token.getLexema().equals("false")) {
                finalValue();
                if (token.getLexema().equals("++") || token.getLexema().equals("--")) {
                    token = proximoToken();
                }
            } else {
                unaryOp();
            }

        } else if (token.getLexema().equals("(")) {
            aritmeticExp();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }
        } else {
            //erro sintatico
        }
    }

//*************** UNARY OP ***************************************************************************
    private void unaryOp() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("++") || token.getLexema().equals("--")) {
            token = proximoToken();
            finalValue();
        } else if (token.getLexema().equals("global") || token.getLexema().equals("local")
                || token.getTipo().equals("IDE") || token.getLexema().equals("true") || token.getLexema().equals("false")
                || token.getTipo().equals("NRO")) {
            finalValue();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("++") || token.getLexema().equals("--")) {
                token = proximoToken();
            }
        } else if (token.getLexema().equals("!")) {
            token = proximoToken();
            callVariable();
        } else {
            //erro sintatico
        }

    }

//*************** FINAL VALUE ************************************************************************
    private void finalValue() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        }
        if (token.getLexema().equals("global") || token.getLexema().equals("local") || token.getTipo().equals("IDE")) {
            modifier();
        } else if (token.getTipo().equals("NRO") || token.getLexema().equals("true") || token.getLexema().equals("false")) {
            token = proximoToken();
        } else {
            //erro sintatico
        }
    }

//*************** CALL VARIABLE **********************************************************************
    private void callVariable() throws IOException {
        if (token == null) {
            //erro sintatico
        }
        modifier();
        paths();
    }
//*************** MODIFIER ***************************************************************************

    private void modifier() throws IOException {
        if (token.getLexema().equals("global") || token.getLexema().equals("local")) {
            token = proximoToken();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals(".")) {
                token = proximoToken();
                if (token == null) {
                    //erro sintatico
                    return;
                } else if (token.getTipo().equals("IDE")) {
                    token = proximoToken();
                }
            }
        } else if (token.getTipo().equals("IDE")) {
            token = proximoToken();
        }
    }

    private void paths() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(".")) {
            struct();
        } else if (token.getLexema().equals("[")) {
            matrAssign();
        } else if (token.getLexema().equals(",") || token.getLexema().equals("=") || token.getLexema().equals("++")
                || token.getLexema().equals("--") || token.getLexema().equals(";") || token.getLexema().equals("]")
                || token.getLexema().equals(")") || token.getLexema().equals("*") || token.getLexema().equals("/")) {
            //vazio
        } else {
            //erro sintatico
        }

    }

    private void struct() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(".")) {
            token = proximoToken();
        } else {
            //erro sintatico
        }

        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getTipo().equals("IDE")) {
            token = proximoToken();
            paths();
        } else {
            //erro sintatico
        }
    }

    private void matrAssign() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("[")) {
            token = proximoToken();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getTipo().equals("NRO")) {
                token = proximoToken();
            } else if (token.getLexema().equals("global") || token.getLexema().equals("local") || token.getTipo().equals("IDE")) {
                callVariable();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("]")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }
        } else {
            //erro sintatico
        }
        paths();
    }
//************** CALL FUNCTIONS PROCEDURES ***************************************************

    private void callProcedureFunction() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getTipo().equals("IDE")) {
            token = proximoToken();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("(")) {
                token = proximoToken();
                realParamList();
            } else {
                //erro sintatico
            }

            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                //erro sintatico
            }
        } else {
            //erro sintatico
        }
    }

    private void realParamList() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals("true") || token.getLexema().equals("false") || token.getTipo().equals("NRO")
                || token.getTipo().equals("CDC") || token.getTipo().equals("IDE") || token.getLexema().equals("global")
                || token.getLexema().equals("local")) {
            realParam();
            moreRealParam();

        } else if (token.getLexema().equals(")")) {
            //vazio           
        } else {
            //erro sintatico
        }
    }

    private void realParam() throws IOException {
        if (token == null) {
            setErro("( expected");
            return;
        } else if (token.getLexema().equals("true") || token.getLexema().equals("false") || token.getTipo().equals("NRO")
                || token.getTipo().equals("CDC")) {
            valueParam();
        } else if (token.getTipo().equals("IDE") || token.getLexema().equals("global") || token.getLexema().equals("local")) {
            callVariable();
        } else {
            setErro("");
            //erro();
        }
    }

    private void moreRealParam() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            if (token == null) {
                //erro sintatico
                return;
            } else if (token.getLexema().equals("true") || token.getLexema().equals("false") || token.getTipo().equals("NRO")
                    || token.getTipo().equals("CDC") || token.getTipo().equals("IDE") || token.getLexema().equals("global")
                    || token.getLexema().equals("local")) {
                realParam();
                moreRealParam();
            }
        } else if (token.getLexema().equals(")")) {
            //vazio
        } else {
            //erro sintatico
        }
    }

    private void valueParam() throws IOException {
        if (token == null) {
            //erro sintatico
            return;
        } else if (token.getTipo().equals("NRO")) {
            token = proximoToken();
        } else if (token.getTipo().equals("CDC")) {
            token = proximoToken();
        } else if (token.getLexema().equals("true") || token.getLexema().equals("false")) {
            token = proximoToken();
        } else {
            //erro sintatico
        }
    }
}
