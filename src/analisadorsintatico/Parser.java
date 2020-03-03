package analisadorsintatico;

import analisadorlexico.Token;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author Aurelio
 */
public class Parser {

    File arquivoSaida;
    ArrayList<Token> tokens;
    Stack<Token> pilhaTokens;
    String nomeArquivo;
    FileOutputStream fos;
    Token token;    


    public Parser(ArrayList<Token> a, int num) throws FileNotFoundException {        
        pilhaTokens = new <Token>Stack();
        this.tokens = a;
        this.arquivoSaida = new File("output sintatico" + "/" + "saida" + num + ".txt");
        this.fos = new FileOutputStream(arquivoSaida);
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
            semErrosSintaticos();
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
     * Método que escreve no arquivo caso não ocorra erros sintáticos
     *
     * @throws IOException
     */
    private void semErrosSintaticos() throws IOException {
        fos.write("Sucess! No syntactic errors".getBytes());
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
        fos.write("syntactic error: ".getBytes());
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
        fos.write("Syntactic error $ end of chain: ".getBytes());
        fos.write(erro.getBytes());
        fos.write("\n".getBytes());
    }

    /**
     * Método que utiliza o modo pânico para dar seguimento na varredura caso encontre um erro
     * @param tokenSinc
     */
    private void erro(String tokenSinc[]) {
        boolean verifica = false;
        while(token != null){
          for(String a: tokenSinc){
            if(token == null){
                break;
            }else if(token.getLexema().equals(a) || token.getTipo().equals(a)){
                verifica = true;
                break;
            }
          }
          if(token == null){
              break;
          }else if(verifica == true){
            break;  
          }else{
              token = proximoToken();
          }
        }
        
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
//********************** INICIO DOS PROCEDIMENTOS ***************************************

    private void start() throws IOException {

        globalValues();       
        functionsProcedures();
    }
//********************** GLOBAL VALUES *****************************************************
//                  DECLARAÇÃO DE VARIÁVEIS 

    private void globalValues() throws IOException {
        if (token == null) {
            setErro(" var or const expected");
            return;
        } else if (token.getLexema().equals("var")) {
            token = proximoToken();

            if (token == null) {
                setErro("{ expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();
                if (token == null) {
                    return;
                }
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro(new String[]{"}", "var","const","{"});
            }

            if (token == null) {
                setErro("} expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "} expected");
                //erro(new String[]{"}", "var","const","{"});
            }

            if (token == null) {
                setErro("const expected");
                return;
            } else if (token.getLexema().equals("const")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "const expected");
                //erro(new String[]{"}", "var","const","{"});
            }

            if (token == null) {
                setErro(" { expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                constValuesDeclaration();
                if (token == null) {
                    return;
                }
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro(new String[]{"}", "var","const","{"});
            }

            if (token == null) {
                setErro("} expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "} expected");
                //erro(new String[]{"}", "var","const","{"});
            }

        } else if (token.getLexema().equals("const")) {
            token = proximoToken();

            if (token == null) {
                setErro("{ expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                constValuesDeclaration();
                if (token == null) {
                    return;
                }
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro(new String[]{"}", "var","const","{"});
            }

            if (token == null) {
                setErro("} expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "} expected");
                //erro(new String[]{"}", "var","const","{"});
            }

            if (token == null) {
                setErro("var expected");
                return;
            } else if (token.getLexema().equals("var")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "var expected");
                //erro(new String[]{"}", "var","const","{"});
            }

            if (token == null) {
                setErro(" { expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();
                if (token == null) {
                    return;
                }
            } else {
                setErro(token.getLinha(), "} expected");
                //erro(new String[]{"}", "var","const","{"});
            }

            if (token == null) {
                setErro("} expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "} expected");
                //erro(new String[]{"}", "var","const","{"});
            }

        } else if (token.getLexema().equals("function") || token.getLexema().equals("procedures")) {
            // vazio
        } else {
            setErro(token.getLinha(), "var, const, function or procedures expected");
            //erro(new String[]{"}", "var","const","{"});
        }

    }

//********** CONST VALUES DECLARATION **********************************************************************
    private void constValuesDeclaration() throws IOException {
        if (token == null) {
            setErro(" Type expected");
            return;
        } else if (isType(token)) {
            token = proximoToken();
            constValuesAtribuition();
            constMoreAtribuition();

            if (token == null) {
                setErro(" ; expected");
                return;
            } else if (token.getLexema().equals(";")) {
                token = proximoToken();
                constValuesDeclaration();

            } else {
                setErro(token.getLinha(), " ; expected");
                //erro(new String[]{"}", "int","boolean","real","IDE","=",";"});
            }
        } else if (token.getLexema().equals("}")) {
            //vazio
        } else {
            setErro(token.getLinha(), " Type expected");
            //erro(new String[]{"}", "int","boolean","real","string","IDE","=",";"});
        }

    }
//********** CONST VALUES ATRIBUITION *****************************************************************

    private void constValuesAtribuition() throws IOException {
        if (token == null) {
            setErro(" IDE expected");
            return;
        } else if (token.getTipo().equals("IDE")) {
            token = proximoToken();
        } else {
            setErro(token.getLinha(), "IDE expected");
            //erro(new String[]{"NRO", "CDC","true","false","IDE",";"});
        }
        
        if (token == null) {
            setErro(" = expected");
        } else if (token.getLexema().equals("=")) {
            token = proximoToken();
            valueConst();
        } else {
            setErro(token.getLinha(), " = expected");
            //erro(new String[]{"}", "int","boolean","real","IDE","=",";"});
        }
    }

//********** VALUE CONST ******************************************************************************
    private void valueConst() throws IOException {
        if (token == null) {
            setErro(" value expected");
        } else if (token.getTipo().equals("NRO")) {
            token = proximoToken();
        } else if (token.getTipo().equals("CDC")) {
            token = proximoToken();
        } else if (token.getLexema().equals("true") || token.getLexema().equals("false")) {
            token = proximoToken();
        } else {
            setErro(token.getLinha(), "value const expected");
            //erro(new String[]{"}",";"});
        }
    }
//********** CONST MORE ATRIBUITION ********************************************************************

    private void constMoreAtribuition() throws IOException {
        if (token == null) {
            setErro("; expected");
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            constValuesAtribuition();
        } else if (token.getLexema().equals(";")) {
            //vazio
        } else {
            setErro(token.getLinha(), " ; expected");
            //erro(new String[]{"}",";"});
        }
    }
//********** VAR VALUES DECLARATION *********************************************************************

    private void varValuesDeclaration() throws IOException {
        if (token == null) {
            setErro(" Type expected");
            return;
        } else if (isType(token)) {
            token = proximoToken();
            varValuesAtribuition();            
            varMoreAtribuition();          

            if (token == null) {
                setErro(" ; expected");
            } else if (token.getLexema().equals(";")) {
                token = proximoToken();
                varValuesDeclaration();

            } else {
                setErro(token.getLinha(), " ; expected");
                //erro(new String[]{"}",";"});
            }
        } else if (token.getLexema().equals("typedef")) {
            token = proximoToken();
            if (token == null) {
                setErro(" identifier expected");
                return;
            } else if (token.getLexema().equals("struct")) {
                token = proximoToken();
                ideStruct();
                if (token == null) {
                    return;
                }
                varValuesDeclaration();

            } else {
                setErro(token.getLinha(), " struct expected");
                //erro(new String[]{"}",";"});   
            }

        } else if (token.getLexema().equals("struct")) {
            token = proximoToken();
            ideStruct();
            if (token == null) {
                return;
            }
            varValuesDeclaration();
        } else if (token.getLexema().equals("}")) {
            // vazio  
        } else {
            setErro(token.getLinha(), " Type var expected");
            //erro(new String[]{"}",";"});
        }

    }

//********** VAR VALUES ATRIBUITION *********************************************************************
    private void varValuesAtribuition() throws IOException {
        if (token == null) {
            setErro(" IDE expected");
        } else if (token.getTipo().equals("IDE")) {
            token = proximoToken();           
            arrayVerification();
        } else {
            setErro(token.getLinha(), " IDE expected");
            //erro(new String[]{"}",";"});
        }
    }
//********** VAR MORE ATRIBUITION ***********************************************************************

    private void varMoreAtribuition() throws IOException {
        if (token == null) {
            setErro(" ; expected");
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            varValuesAtribuition();
            if (token == null) {
                return;
            }
            varMoreAtribuition();
        } else if (token.getLexema().equals(";")) {
            // vazio           
        } else {
            setErro(token.getLinha(), "; expected");
            //erro(new String[]{"}",";"});
        }
    }
//********** VAR ARRAY VERIFICATION *********************************************************************     

    private void arrayVerification() throws IOException {
        if (token == null) {
            setErro(" [ expected");
            return;
        } else if (token.getLexema().equals("[")) {
            token = proximoToken();

            if (token == null) {
                setErro(" number expected");
                return;
            } else if (token.getTipo().equals("NRO")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "number expected");
                //erro(new String[]{"}",";"});
            }

            if (token == null) {
                setErro(" ] expected");
            } else if (token.getLexema().equals("]")) {
                token = proximoToken();
                arrayVerification();
            } else {
                setErro(token.getLinha(), " ] expected");
                //erro(new String[]{"}",";"});
            }
        } else if (token.getLexema().equals(",") || token.getLexema().equals(";")) {
            // vazio
        } else {
            setErro(token.getLinha(), "[ expected");
            //erro(new String[]{"}",";"});
        }

    }
//********** IDE STRUCT *********************************************************************************

    private void ideStruct() throws IOException {
        if (token == null) {
            setErro(" identifier expected");
        } else if (token.getTipo().equals("IDE")) {
            token = proximoToken();
            ideStruct2();
        } else {
            setErro(token.getLinha(), " IDE expected");
            //erro(new String[]{"}",";"});
        }
    }
//********** IDE STRUCT 2 *******************************************************************************

    private void ideStruct2() throws IOException {
        if (token == null) {
            setErro(" { or extends expected");
            return;
        } else if (token.getLexema().equals("{")) {
            token = proximoToken();

            if (token == null) {
                setErro(" var expected");
                return;
            } else if (token.getLexema().equals("var")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), " var expected");
                //erro(new String[]{"}",";"});
            }

            if (token == null) {
                setErro(" { expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();
                if (token == null) {
                    return;
                }
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro(new String[]{"}",";"});
            }

            if (token == null) {
                setErro(" } expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "} expected");
                //erro(new String[]{"}",";"});
            }

            if (token == null) {
                setErro(" } expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "} expected");
                //erro(new String[]{"}",";"});
            }
        } else if (token.getLexema().equals("extends")) {
            token = proximoToken();
            if (token == null) {
                setErro(" IDE expected");
                return;
            } else if (token.getTipo().equals("IDE")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), " IDE expected");
                //erro(new String[]{"}",";"});
            }

            if (token == null) {
                setErro(" { expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro(new String[]{"}",";"});
            }
            if (token == null) {
                setErro(" var expected");
                return;
            } else if (token.getLexema().equals("var")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "var expected");
                //erro(new String[]{"}",";"});
            }

            if (token == null) {
                setErro(" { expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();
                if (token == null) {
                    return;
                }
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro(new String[]{"}",";"});
            }

            if (token == null) {
                setErro(" } expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "} expected");
                //erro(new String[]{"}",";"});
            }

            if (token == null) {
                setErro(" } expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "} expected");
                //erro(new String[]{"}",";"});
            }
        }
    }

//********** FUNCTIONS PROCEDURES ***********************************************************************
    private void functionsProcedures() throws IOException {
        if (token == null) {
            //setErro("function or procedures expected"); ACHO QUE AQUI TRATA O VAZIO
            return;
        } else if (token.getLexema().equals("function")) {
            token = proximoToken();
            if (token == null) {
                setErro("Type expected");
                return;
            } else if (isType(token) || token.getTipo().equals("IDE")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "Type expected");
                //erro("Type"); 
            }
            if (token == null) {
                setErro("Identifier expected");
                return;
            } else if (token.getTipo().equals("IDE")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "Identifier expected");
                //erro("identifier");
            }
            if (token == null) {
                setErro("( expected");
                return;
            } else if (token.getLexema().equals("(")) {
                token = proximoToken();
                paramList();
                if (token == null) {
                    return;
                }
            } else {
                setErro(token.getLinha(), "( expected");
                //erro();
            }

            if (token == null) {
                setErro(") expected");
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), ") expected");
                //erro();
            }

            if (token == null) {
                setErro("{ expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro("function");
            }

            if (token == null) {
                setErro("var expected");
                return;
            } else if (token.getLexema().equals("var")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "var expected");
                //erro("function");
            }

            if (token == null) {
                setErro("{ expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();
                if (token == null) {
                    return;
                }
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro("function");
            }

            if (token == null) {
                setErro("} expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
                commands();
                returns();
                
            } else {
                setErro(token.getLinha(), "} expected");
                // erro("function");
            }

            if (token == null) {
                setErro("} expected");
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
                functionsProcedures();

            } else {
                setErro(token.getLinha(), "} expected");
                // erro("function");
            }
        } else if (token.getLexema().equals("procedure")) {
            token = proximoToken();
            if (token == null) {
                setErro("identifier expected");
                return;
            } else if (token.getTipo().equals("IDE") || token.getLexema().equals("start")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "identifier expected");
                // erro("function");
            }

            if (token == null) {
                setErro("( expected");
                return;
            } else if (token.getLexema().equals("(")) {
                token = proximoToken();
                paramList();
            } else {
                setErro(token.getLinha(), ") expected");
                // erro("function");
            }

            if (token == null) {
                setErro(") expected");
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), ") expected");
                //erro();
            }

            if (token == null) {
                setErro("{ expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro("function");
            }

            if (token == null) {
                setErro("var expected");
                return;
            } else if (token.getLexema().equals("var")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "var expected");
                //erro("function");
            }

            if (token == null) {
                setErro("{ expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                varValuesDeclaration();
                if (token == null) {
                    return;
                }
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro("function");
            }

            if (token == null) {
                setErro("} expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
                commands();
            } else {
                setErro(token.getLinha(), "} expected");
                // erro("function");
            }

            if (token == null) {
                setErro("} expected");
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
                functionsProcedures();
            } else {
                setErro(token.getLinha(), "} expected");
                // erro("function");
            }
        } else {
            setErro(token.getLinha(), "function or procedure expected");
            //erro("");
        }
    }
//*************** PARAM LIST **********************************************************************

    private void paramList() throws IOException {
        if (token == null) {
            setErro("Type expected");
            return;
        } else if (isType(token)) {
            token = proximoToken();
            if (token == null) {
                setErro("IDE expected");
                return;
            } else if (token.getTipo().equals("IDE")) {
                token = proximoToken();
                moreParam();
            } else {
                setErro(token.getLinha(), "Identifier expected");
                //erro(",");   
            }
        } else if (token.getLexema().equals(")")) {
            // vazio
        } else {
            setErro(token.getLinha(), "Type expected");
            //erro("Identifier");
        }

    }
//*************** MORE PARAM **********************************************************************

    private void moreParam() throws IOException {
        if (token == null) {
            setErro(") expected");
            return;
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            paramList();
        } else if (token.getLexema().equals(")")) {
            // vazio
        } else {
            setErro(token.getLinha(), ") expected");
            // erro("Type");
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
            setErro("expression expected");
            return;
        } else {
            relationalExp();
            if (token == null) {
                return;
            }
            optLogicalExp();
        }
    }
//*************** RETURNS **********************************************************************

    private void returns() throws IOException {
        if (token == null) {
            setErro("return expected");
            return;
        } else if (token.getLexema().equals("return")) {
            token = proximoToken();
            if (token == null) {
                setErro("return type expected");
                return;
            } else if (token.getLexema().equals("true") || token.getLexema().equals("false") || token.getTipo().equals("NRO")) {
                
                token = proximoToken();
            } else {
                relationalExp();
            }
        } else {
            setErro(token.getLinha(), "return expected");
            //erro();
        }

        if (token == null) {
            setErro("; expected");
        } else if (token.getLexema().equals(";")) {
            token = proximoToken();
        } else {
            setErro(token.getLinha(), "; expected");
            //erro();
        }

    }

//*************** IF STATEMANT **********************************************************************
    private void ifStatemant() throws IOException {

        if (token == null) {
            setErro("if expected");
            return;
        } else if (token.getLexema().equals("if")) {
            token = proximoToken();

            if (token == null) {
                setErro("( expected");
                return;
            } else if (token.getLexema().equals("(")) {
                token = proximoToken();
                commandsExp();
            } else {
                setErro(token.getLinha(), "( expected");
                //erro("");
            }

            if (token == null) {
                setErro(") expected");
                return;
            }
            if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), ") expected");
                //erro();
            }
            if (token == null) {
                setErro("then expected");
                return;
            } else if (token.getLexema().equals("then")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "then expected");
                //erro();
            }

            if (token == null) {
                setErro("{ expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                commands();
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro();
            }

            if (token == null) {
                setErro("} expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
                elseStatemant();
            } else {
                setErro(token.getLinha(), "} expected");
                //erro();
            }

        }
    }
//*************** ELSE STATEMANT ***********************************************************

    private void elseStatemant() throws IOException {

        if (token == null) {
            setErro("else expected");
            return;
        } else if (token.getLexema().equals("else")) {
            token = proximoToken();

            if (token == null) {
                setErro("{ expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                commands();
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro("");
            }

            if (token == null) {
                setErro("} expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "} expected");
                //erro();
            }

        } else if (token.getLexema().equals("return") || token.getLexema().equals("}")) {
            //vazio  
        } else {
            setErro(token.getLinha(), "else or } expected");
            //erro();
        }
    }
//*************** WHILE STATEMANT ***********************************************************

    private void whileStatemant() throws IOException {
        if (token == null) {
            setErro("while expected");
            return;
        } else if (token.getLexema().equals("while")) {
            token = proximoToken();
            if (token == null) {
                setErro("( expected");
                return;
            } else if (token.getLexema().equals("(")) {
                token = proximoToken();
                commandsExp();
            } else {
                setErro(token.getLinha(), "( expected");
                //erro("");
            }

            if (token == null) {
                setErro(") expected");
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), ") expected");
                //erro();
            }

            if (token == null) {
                setErro("{ expected");
                return;
            } else if (token.getLexema().equals("{")) {
                token = proximoToken();
                commands();
            } else {
                setErro(token.getLinha(), "{ expected");
                //erro();
            }

            if (token == null) {
                setErro("} expected");
                return;
            } else if (token.getLexema().equals("}")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "} expected");
                //erro();
            }
        }
    }
//*************** READ STATEMANT **********************************************************************

    private void readStatemant() throws IOException {
        if (token == null) {
            setErro("read expected");
            return;
        } else if (token.getLexema().equals("read")) {
            token = proximoToken();
        } else {
            setErro(token.getLinha(), "read expected");
            //erro();
        }

        if (token == null) {
            setErro("( expected");
            return;
        } else if (token.getLexema().equals("(")) {
            token = proximoToken();
            readParams();
        } else {
            setErro(token.getLinha(), "( expected");
            //erro();
        }

        if (token == null) {
            setErro(") expected");
            return;
        } else if (token.getLexema().equals(")")) {
            token = proximoToken();

        } else {
            setErro(token.getLinha(), ") expected");
            //erro();
        }

        if (token == null) {
            setErro("; expected");
            return;
        } else if (token.getLexema().equals(";")) {
            token = proximoToken();

        } else {
            setErro(token.getLinha(), "; expected");
            //erro();
        }

    }

    private void readParams() throws IOException {
        callVariable();
        moreReadParams();
    }

    private void moreReadParams() throws IOException {
        if (token == null) {
            setErro("more params or ) expected");
            return;
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            readParams();
        } else if (token.getLexema().equals(")")) {
            //vazio
        } else {
            setErro(token.getLinha(), "more params or ) expected");
            //erro();
        }

    }

//*************** PRINT STATEMANT **********************************************************
    private void printStatemant() throws IOException {
        if (token == null) {
            setErro("print expected");
            return;
        } else if (token.getLexema().equals("print")) {
            token = proximoToken();
        }

        if (token == null) {
            setErro("( expected");
            return;
        } else if (token.getLexema().equals("(")) {
            token = proximoToken();
            printParams();
        } else {
            setErro(token.getLinha(), "( expected");
            //erro();
        }

        if (token == null) {
            setErro(") expected");
            return;
        } else if (token.getLexema().equals(")")) {
            token = proximoToken();

        } else {
            setErro(token.getLinha(), ") expected");
            //erro();
        }

        if (token == null) {
            setErro("; expected");
            return;
        } else if (token.getLexema().equals(";")) {
            token = proximoToken();

        } else {
            setErro(token.getLinha(), "; expected");
            //erro();
        }

    }

    private void printParams() throws IOException {
        if (token == null) {
            setErro("print params expected");
            return;
        }
        printParam();
        morePrintParams();

    }

    private void printParam() throws IOException {
        if (token == null) {
            setErro("print params expected");
            return;
        } else if (token.getTipo().equals("CDC")) {
            token = proximoToken();
        } else if (token.getTipo().equals("IDE") || token.getLexema().equals("local") || token.getLexema().equals("global")) {
            callVariable();
        } else {
            setErro(token.getLinha(), "print params expected");
            //erro();
        }
    }

    private void morePrintParams() throws IOException {
        if (token == null) {
            setErro(" more print params or ) expected");
            return;
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            printParams();
        } else if (token.getLexema().equals(")")) {
            //vazio
        } else {
            setErro(token.getLinha(), " more print params or ) expected");
            //erro();
        }
    }
//*************** ASSIGNMENT **********************************************************************

    private void assignment() throws IOException {
        if (token == null) {
            setErro("expression expected");
            return;
        } else if (token.getTipo().equals("IDE") || token.getLexema().equals("local") || token.getLexema().equals("global")) {
            callVariable();
            if (token == null) {
                setErro("= expected");
                return;
            } else if (token.getLexema().equals("=")) {
                token = proximoToken();
                assign2();
            } else {
                setErro(token.getLinha(), "= expected");
                //erro();
            }

            if (token == null) {
                setErro("; expected");
                return;
            } else if (token.getLexema().equals(";")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "; expected");
                //erro();
            }

        } else if (token.getLexema().equals("global") || token.getLexema().equals("local")
                || token.getTipo().equals("IDE") || token.getLexema().equals("++") || token.getLexema().equals("--")
                || token.getTipo().equals("NRO") || token.getLexema().equals("true") || token.getLexema().equals("false")
                || token.getLexema().equals("!")) {
            unaryOp();

            if (token == null) {
                setErro("; expected");
                return;
            } else if (token.getLexema().equals(";")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "; expected");
                //erro();
            }
        } else {

        }

    }

    private void assign2() throws IOException {
        if (token == null) {
            setErro("expression expected");
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
            setErro(token.getLinha(), "expression identifier or string expected");
            //erro();
        }
    }

//*************** EXPRESSION **********************************************************************
    private void expression() throws IOException {
        if (token == null) {
            setErro("expression expected");
            return;
            // logical ou aritmetic
        }
        logicalExp();

    }

//*************** RELATIONAL EXPRESSION ***********************************************************    
    private void relationalExp() throws IOException {
        if (token == null) {
            setErro("expected");
            return;
        } else if (token.getLexema().equals("(")) {
            token = proximoToken();
            logicalExp();

            if (token == null) {
                setErro(") expected");
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), ") expected");
                //erro();
            }
        } else {
            aritmeticExp();
            possRelExp();
        }
    }

//*************** OPT LOGICAL EXPRESSION ***********************************************************   
    private void optLogicalExp() throws IOException {
        if (token == null) {
            setErro("&& or || expected");
            return;
        } else if (token.getLexema().equals("&&") || token.getLexema().equals("||")) {
            token = proximoToken();
            logicalExp();
        } else if (token.getLexema().equals(")")) {
            //vazio  
        } else {
            setErro(token.getLinha(), "&& or || expected");
            //erro();
        }
    }

//*************** LOGICAL EXPRESSION ***************************************************************     
    private void logicalExp() throws IOException {
        if (token == null) {
            setErro(" relational op expected");
            return;
        }
        relationalExp();
        optLogicalExp();
    }

//*************** POSS REL EXPRESSION ***************************************************************
    private void possRelExp() throws IOException {
        if (token == null) {
            setErro("!= == > < >= <= expected");
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
            setErro(token.getLinha(), "!= == > < >= <=  expected");
            //erro();
        }
    }

//*************** EQUALITY EXPRESSION ***************************************************************
    private void equalityExp() throws IOException {
        if (token == null) {
            setErro("> < >= <= expected");
            return;
        } else if (token.getLexema().equals("!=") || token.getLexema().equals("==")) {
            token = proximoToken();
            aritmeticExp();
            inequalityExp();
            equalityExp();
        } else if (token.getLexema().equals(")") || token.getLexema().equals("&&") || token.getLexema().equals("||")) {
            // vazio
        } else {
            setErro(token.getLinha(), "> < >= <= expected");
            //erro();
        }

    }

//*************** INEQUALITY EXPRESSION ***************************************************************
    private void inequalityExp() throws IOException {
        if (token == null) {
            setErro("> < >= <= expected");
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
            setErro(token.getLinha(), "> < >= <= expected");
            //erro();
        }
    }

//*************** ARITIMETIC EXPRESSION ***************************************************************
    private void aritmeticExp() throws IOException {
        if (token == null) {
            setErro("Unary OP, \"!\", number, booleans, modifiers, identifiers or \"(\" expected");
            return;
            //primeiro unary Op
        } else if (token.getLexema().equals("(")) {
            token = proximoToken();
            relationalExp();
            if (token == null) {
                setErro(") expected");
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), ") expected");
                //erro();
            }

        } else {
            operation();
            opSum();
        }
    }

//*************** OPERATION ***************************************************************************
    private void operation() throws IOException {
        if (token == null) {
            setErro("Unary OP, \"!\", number, booleans, modifiers, identifiers or \"(\" expected");
            return;
        }
        opUnary();
        opMultiplication();
    }

//*************** OP SUM  *****************************************************************************
    private void opSum() throws IOException {
        if (token == null) {
            setErro("+ or - expected");
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
            setErro(token.getLinha(), "+ or - expected");
            //erro();
        }
    }

//*************** OP MULTIPLICATION *******************************************************************
    private void opMultiplication() throws IOException {
        if (token == null) {
            setErro("/ or * expected");
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
            setErro(token.getLinha(), "/ or * expected");
            //erro();
        }
    }

//*************** OP UNARY ***************************************************************************
    private void opUnary() throws IOException {
        if (token == null) {
            setErro("Unary OP, \"!\", number, booleans, modifiers, identifiers or \"(\" expected");
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
                setErro(") expected");
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), ") expected");
                //erro();
            }
        } else {
            setErro(token.getLinha(), "Unary OP, \"!\", number, booleans, modifiers, identifiers or \"(\" expected");
            //erro();
        }
    }

//*************** UNARY OP ***************************************************************************
    private void unaryOp() throws IOException {
        if (token == null) {
            setErro("Unary OP, num, booleans, modifiers or identifiers expected");
            return;
        } else if (token.getLexema().equals("++") || token.getLexema().equals("--")) {
            token = proximoToken();
            finalValue();
        } else if (token.getLexema().equals("global") || token.getLexema().equals("local")
                || token.getTipo().equals("IDE") || token.getLexema().equals("true") || token.getLexema().equals("false")
                || token.getTipo().equals("NRO")) {
            finalValue();
            if (token == null) {
                setErro("++ or -- expected");
                return;
            } else if (token.getLexema().equals("++") || token.getLexema().equals("--")) {
                token = proximoToken();
            }
        } else if (token.getLexema().equals("!")) {
            token = proximoToken();
            callVariable();
        } else {
            setErro(token.getLinha(), "Unary OP, num, booleans, modifiers or identifiers expected");
            //erro();
        }

    }

//*************** FINAL VALUE ************************************************************************
    private void finalValue() throws IOException {
        if (token == null) {
            setErro("expected");
            return;
        }
        if (token.getLexema().equals("global") || token.getLexema().equals("local") || token.getTipo().equals("IDE")) {
            modifier();
        } else if (token.getTipo().equals("NRO") || token.getLexema().equals("true") || token.getLexema().equals("false")) {
            token = proximoToken();
        } else {
            setErro(token.getLinha(), "final value expected");
            //erro();
        }
    }

//*************** CALL VARIABLE **********************************************************************
    private void callVariable() throws IOException {
        if (token == null) {
            setErro("Identifier or Modifiers expected");
        }
        modifier();
        if (token == null) {
            return;
        }
        paths();
    }
//*************** MODIFIER ***************************************************************************

    private void modifier() throws IOException {
        if (token.getLexema().equals("global") || token.getLexema().equals("local")) {
            token = proximoToken();
            if (token == null) {
                setErro(". expected");
                return;
            } else if (token.getLexema().equals(".")) {
                token = proximoToken();
                if (token == null) {
                    setErro("IDE expected");
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
            setErro("expected ");
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
            setErro(token.getLinha(), "expected");
        }

    }

    private void struct() throws IOException {
        if (token == null) {
            setErro("");
            return;
        } else if (token.getLexema().equals(".")) {
            token = proximoToken();
        } else {
            setErro(token.getLinha(), ". expected");
            //erro();
        }

        if (token == null) {
            setErro("Identifier expected");
            return;
        } else if (token.getTipo().equals("IDE")) {
            token = proximoToken();
            paths();
        } else {
            setErro(token.getLinha(), "Identifier expected");
            //erro();
        }
    }

    private void matrAssign() throws IOException {
        if (token == null) {
            setErro("[ expected");
            return;
        } else if (token.getLexema().equals("[")) {
            token = proximoToken();
            if (token == null) {
                setErro("number or modifier expected");
                return;
            } else if (token.getTipo().equals("NRO")) {
                token = proximoToken();
            } else if (token.getLexema().equals("global") || token.getLexema().equals("local") || token.getTipo().equals("IDE")) {
                callVariable();
            } else {
                setErro(token.getLinha(), "number or modifier expected");
                //erro();
            }

            if (token == null) {
                setErro("] expected");
                return;
            } else if (token.getLexema().equals("]")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), "] expected");
                //erro();
            }
        } else {
            setErro(token.getLinha(), "[ expected");
            //erro();
        }
        paths();
    }
//************** CALL FUNCTIONS PROCEDURES ***************************************************

    private void callProcedureFunction() throws IOException {
        if (token == null) {
            setErro("identifier expected");
            return;
        } else if (token.getTipo().equals("IDE")) {
            token = proximoToken();
            if (token == null) {
                setErro("( expected");
                return;
            } else if (token.getLexema().equals("(")) {
                token = proximoToken();
                realParamList();
            } else {
                setErro(token.getLinha(), "( expected");
                //erro();
            }

            if (token == null) {
                setErro(") expected");
                return;
            } else if (token.getLexema().equals(")")) {
                token = proximoToken();
            } else {
                setErro(token.getLinha(), ") expected");
                //erro();
            }
        } else {
            setErro(token.getLinha(), "identifier expected");
            //erro();
        }
    }

    private void realParamList() throws IOException {
        if (token == null) {
            setErro("values param or modifiers expected");
            return;
        } else if (token.getLexema().equals("true") || token.getLexema().equals("false") || token.getTipo().equals("NRO")
                || token.getTipo().equals("CDC") || token.getTipo().equals("IDE") || token.getLexema().equals("global")
                || token.getLexema().equals("local")) {
            realParam();
            moreRealParam();

        } else if (token.getLexema().equals(")")) {
            //vazio           
        } else {
            setErro(token.getLinha(), "values param or modifiers expected");
            //erro();
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
            setErro(") expected");
            return;
        } else if (token.getLexema().equals(",")) {
            token = proximoToken();
            if (token == null) {
                setErro("values param or modifiers expected");
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
            setErro(token.getLinha(), ") expected");
            //erro();
        }
    }

    private void valueParam() throws IOException {
        if (token == null) {
            setErro(") or params expected");
            return;
        } else if (token.getTipo().equals("NRO")) {
            token = proximoToken();
        } else if (token.getTipo().equals("CDC")) {
            token = proximoToken();
        } else if (token.getLexema().equals("true") || token.getLexema().equals("false")) {
            token = proximoToken();
        } else {
            setErro(token.getLinha(), ") or params expected");
            //erro();
        }
    }
}
