package analisadorlexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Iterator;

/**
 *
 * @author Aurelio, Werisson
 */
public class Scanner {

    private FileReader fr;
    private BufferedReader br;
    private PushbackReader pr;
    private String nomeArquivo;
    private File arquivoSaida;
    private FileOutputStream fos;
    private int pos;
   

    /**
     * Construtor
     *
     * @param arquivo arquivo a ser analisado
     * @throws FileNotFoundException
     */
    public Scanner(File arquivo) throws FileNotFoundException {
        this.fr = new FileReader(arquivo);
        this.br = new BufferedReader(fr);
        this.pr = new PushbackReader(br);
        this.nomeArquivo = arquivo.getName();
        String nomeSaida[] = nomeArquivo.split("entrada");
        this.arquivoSaida = new File("output lexico" + "/" + "saida" + nomeSaida[1]);
        this.fos = new FileOutputStream(arquivoSaida);
        this.pos = 0;
    }

    /**
     * Método que obtém um carctere do arquivo
     *
     * @return caractere
     * @throws IOException
     */
    public Character obterCaractere() throws IOException {
        Character c = null;
        int i = pr.read();
        if (i != -1) {
            c = (char) i;
        }
        return c;
    }

    /**
     * Método que devolve o caractere para que possa ser lido novamente pois não
     * foi incluido ao token
     *
     * @param c caracter a ser devolvido
     * @throws IOException
     */
    public void devolverCaracter(Character c) throws IOException {

        this.pr.unread(c);
    }

    /**
     * Método que seta o token no arquivo de saída
     *
     * @param token
     * @throws java.io.IOException
     */
    public void setToken(Token token) throws IOException {
        fos.write(String.valueOf(token.getLinha()).getBytes());
        fos.write(" ".getBytes());
        fos.write(token.getTipo().getBytes());
        fos.write(" ".getBytes());
        fos.write(token.getLexema().getBytes());
        fos.write("\n".getBytes());
    }

    /**
     * Método que fecha os arquivos e buffers
     *
     * @throws IOException
     */
    public void fecharArquivos() throws IOException {
        fos.close();
        fr.close();
        br.close();
        pr.close();
        fos.close();
    }

    /**
     * Método que classifica se o lexema faz parte de alguma palavra reservada
     *
     * @param lexema lexema a ser analisado
     * @return retorna String tipo de classificação do token Palavra reservada
     * ou Identificador
     */
    public String classificar(String lexema) {

        if (lexema.equals("var") || lexema.equals("cont") || lexema.equals("typedef") || lexema.equals("struct")
                || lexema.equals("extends") || lexema.equals("procedure") || lexema.equals("function") || lexema.equals("start")
                || lexema.equals("return") || lexema.equals("if") || lexema.equals("else") || lexema.equals("then")
                || lexema.equals("while") || lexema.equals("read") || lexema.equals("print") || lexema.equals("int")
                || lexema.equals("real") || lexema.equals("boolean") || lexema.equals("string") || lexema.equals("true")
                || lexema.equals("false") || lexema.equals("global") || lexema.equals("local")) {
            return "PRE";
        }
        return "IDE";
    }

    /**
     * Método que seta mensagem de sucesso no final do arquivo caso não contenha
     * erros léxicos
     *
     * @throws java.io.IOException
     */
    public void semErros() throws IOException {
        fos.write("\n".getBytes());
        fos.write("Sem erros Léxicos".getBytes());
    }

    /**
     * Método que seta os erros léxicos no fim do arquivo
     *
     * @param it Iterador do array de Tokens com erro
     * @throws IOException
     */
    public void setErros(Iterator it) throws IOException {
        fos.write("\n".getBytes());
        while (it.hasNext()) {
            Token aux = (Token) it.next();
            this.setToken(aux);
        }
    }
    /**
     * Método que verifica se um caractere está definido entre [a - z] e [A - Z]
     * @param c caractere a ser analisado
     * @return true para esta contido, false para não está contido
     */
    public boolean isLetter(Character c){
        if((int)c >= 65 && (int)c <= 90){
            return true;
        }else if((int)c >= 97 && (int)c <= 122){
            return true;
        }
        return false;
    }
    /**
     * Método que verifica se um caractere está entre 32 e 126 na tabela ASCII (execto ASCII 34)
     * @param c cacartere a ser analisado
     * @return true para esta contido, false para não esta contido
     */
    public boolean isSimbol(Character c){
        if((int)c >= 32 && (int)c <= 33){
            return true;
        }else if((int)c >= 35 && (int)c <= 126){
            return true;
        }else if((int) c >= 10){            
            return true;
        } 
        return false;        
    }

    /**
     * Método que retorna um token pronto ou mal formado
     *
     * @return Token
     * @throws IOException
     */
    public Token getToken() throws IOException {

        Token token = null;
        Character c;
        String lexema = new String();

        do {
            c = this.obterCaractere();
            if (c == null) {
                break;
            }
            if ((int) c == 10) {
                pos++;
            }
            // ******** IDENTIFICADORES **********          
            if (this.isLetter(c)){
                lexema = lexema + c;
                c = this.obterCaractere();
                if (c == null) {
                    token = new Token(classificar(lexema), lexema, pos + 1, true);
                    break;
                } else {
                    while (this.isLetter(c) || Character.isDigit(c) || c == '_') {
                        lexema = lexema + c;
                        c = this.obterCaractere();
                        if (c == null) {
                            break;
                        }
                    }
                    if (c == null) {
                        token = new Token(classificar(lexema), lexema, pos + 1, true);
                        break;
                    } else {
                        this.devolverCaracter(c);
                        token = new Token(classificar(lexema), lexema, pos + 1, true);
                        break;
                    }
                }
            }

            // ******* NÚMERO NEGATIVO ************
            if (c == '-') {
                lexema = lexema + c;
                c = this.obterCaractere();
                if (c != null) {
                    if (c == '-') {
                        // é operador aritimético
                        lexema = lexema + c;
                        token = new Token("ART", lexema, pos + 1, true);
                        break;
                    } else if ((int) c == 9 || (int) c == 32) {
                        // Espaço em branco
                        c = this.obterCaractere();
                        while ((int) c == 9 || (int) c == 32) {
                            c = this.obterCaractere();
                        }

                        if (Character.isDigit(c)) {
                            lexema = lexema + c;
                            c = this.obterCaractere();
                            while (c != null && Character.isDigit(c)) {
                                lexema = lexema + c;
                                c = this.obterCaractere();
                            }
                            if (c != null) {
                                if (c == '.') {
                                    lexema = lexema + c;
                                    c = this.obterCaractere();
                                    if (c != null) {
                                        if (Character.isDigit(c)) {
                                            lexema = lexema + c;
                                            c = this.obterCaractere();
                                            while (c != null && Character.isDigit(c)) {
                                                lexema = lexema + c;
                                                c = this.obterCaractere();
                                            }
                                            if (c == null) {
                                                token = new Token("NRO", lexema, pos + 1, true);
                                                break;
                                            } else {
                                                devolverCaracter(c);
                                                token = new Token("NRO", lexema, pos + 1, true);
                                                break;
                                            }

                                        } else {
                                            //Numero mal formado                            
                                            token = new Token("NroMF", lexema, pos + 1, false);
                                            break;
                                        }
                                    } else {
                                        // Numero mal formado fim de arquivo
                                        token = new Token("NroMF", lexema, pos + 1, false);
                                        break;
                                    }
                                } else {
                                    // é numero sem ponto
                                    this.devolverCaracter(c);
                                    token = new Token("NRO", lexema, pos + 1, true);
                                    break;
                                }

                            } else {
                                // Número sem ponto fim de arquivo
                                token = new Token("NRO", lexema, pos + 1, true);
                                break;
                            }
                        } else {
                            // é operador aritimético
                            this.devolverCaracter(c);
                            token = new Token("ART", lexema, pos + 1, true);
                            break;
                        }

                        // Não tem espaço entre - e o dígito        
                    } else if (Character.isDigit(c)) {
                        lexema = lexema + c;
                        c = this.obterCaractere();
                        while (c != null && Character.isDigit(c)) {
                            lexema = lexema + c;
                            c = this.obterCaractere();
                        }
                        if (c == null) {
                            token = new Token("NRO", lexema, pos + 1, true);
                            break;
                        } else if (c == '.') {
                            lexema = lexema + c;
                            c = this.obterCaractere();
                            if (c != null) {
                                if (Character.isDigit(c)) {
                                    lexema = lexema + c;
                                    c = this.obterCaractere();
                                    while (c != null && Character.isDigit(c)) {
                                        lexema = lexema + c;
                                        c = this.obterCaractere();
                                    }

                                    token = new Token("NRO", lexema, pos + 1, true);
                                    break;
                                } else {
                                    //Numero mal formado
                                    lexema = lexema + c;
                                    token = new Token("NroMF", lexema, pos + 1, false);
                                    break;
                                }
                            } else {
                                //Numero mal formado fim de arquivo                  
                                token = new Token("NroMF", lexema, pos + 1, false);
                                break;
                            }
                        } else {
                            // numero sem ponto
                            this.devolverCaracter(c);
                            token = new Token("NRO", lexema, pos + 1, true);
                            break;
                        }

                    } else if (!Character.isDigit(c)) {
                        // é operador aritimético
                        this.devolverCaracter(c);
                        token = new Token("ART", lexema, pos + 1, true);
                        break;
                    }
                } else {
                    // operador aritimético fim de arquivo
                    token = new Token("ART", lexema, pos + 1, true);
                    break;
                }
                // ********* NUMERO POSITIVO
            } else if (Character.isDigit(c)) {
                lexema = lexema + c;
                c = this.obterCaractere();

                while (c != null && Character.isDigit(c)) {
                    lexema = lexema + c;
                    c = this.obterCaractere();
                }
                if (c != null) {
                    if (c == '.') {
                        lexema = lexema + c;
                        c = this.obterCaractere();
                        if (c != null) {
                            if (Character.isDigit(c)) {
                                lexema = lexema + c;
                                c = this.obterCaractere();
                                while (c != null && Character.isDigit(c)) {
                                    lexema = lexema + c;
                                    c = this.obterCaractere();
                                }
                                if (c == null) {
                                    token = new Token("NRO", lexema, pos + 1, true);
                                    break;
                                } else {
                                    devolverCaracter(c);
                                    token = new Token("NRO", lexema, pos + 1, true);
                                    break;
                                }

                            } else {
                                //Numero mal formado
                                lexema = lexema + c;
                                token = new Token("NroMF", lexema, pos + 1, false);
                                break;
                            }
                        } else {
                            //Numero mal formado fim de arquivo                    
                            token = new Token("NroMF", lexema, pos + 1, false);
                            break;
                        }
                    } else {
                        // é numero sem ponto
                        this.devolverCaracter(c);
                        token = new Token("NRO", lexema, pos + 1, true);
                        break;
                    }
                } else {
                    // é numero sem ponto fim de arquivo                             
                    token = new Token("NRO", lexema, pos + 1, true);
                    break;
                }
                // ********* OPERADORES ARITIMÉTICOS
            } else if (c == '+') {
                lexema = lexema + c;
                c = this.obterCaractere();
                if (c != null) {
                    if (c == '+') {
                        lexema = lexema + c;
                        token = new Token("ART", lexema, pos + 1, true);
                        break;
                    } else {
                        this.devolverCaracter(c);
                        token = new Token("ART", lexema, pos + 1, true);
                        break;
                    }
                } else {
                    // + fim de arquivo   
                    token = new Token("ART", lexema, pos + 1, true);
                    break;
                }
            } else if (c == '*') {
                lexema = lexema + c;
                token = new Token("ART", lexema, pos + 1, true);
                break;
            } else if (c == '/') {
                lexema = lexema + c;
                c = this.obterCaractere();
                // ******** COMENTÁRIO DE LINHA
                if (c != null) {
                    if (c == '/') {
                        lexema = lexema + c;
                        while (c != null && (int) c != 10) {
                            c = this.obterCaractere();
                            lexema = lexema + c;
                        }
                        token = new Token("COM", lexema, pos + 1, true);
                        break;

                    } else if (c == '*') {
                        // COMENTÁRIO DE BLOCO
                        lexema = lexema + c;
                        c = this.obterCaractere();
                        while (c != null) {                       

                            if (c == '*') {
                                lexema = lexema + c;
                                c = this.obterCaractere();
                                if (c == '/') {
                                    lexema = lexema + c;
                                    token = new Token("COM", lexema, pos + 1, true);
                                    break;
                                }else{
                                 lexema = lexema + c;
                                }
                            }else{
                                lexema = lexema + c;
                            }
                            c = this.obterCaractere();
                        }
                        if (lexema.charAt(lexema.length() - 1) == '/') {

                            if (lexema.charAt(lexema.length() - 2) == '*') {
                                break;
                            } else {
                                // comentário de bloco mal formado
                                token = new Token("CoMF", lexema, pos + 1, false);
                                break;
                            }
                        } else {
                            // comentário de bloco mal formado
                            token = new Token("CoMF", lexema, pos + 1, false);
                            break;
                        }

                    } else {
                        // é operador de divisão
                        this.devolverCaracter(c);
                        token = new Token("ART", lexema, pos + 1, true);
                        break;
                    }
                } else {
                    // Operador de divisão fim de arquivo
                    token = new Token("ART", lexema, pos + 1, true);
                    break;
                }
            } else if (c == '=') {
                lexema = lexema + c;
                c = this.obterCaractere();
                if (c != null) {
                    if (c == '=') {
                        lexema = lexema + c;
                        token = new Token("ART", lexema, pos + 1, true);
                        break;
                    } else {
                        this.devolverCaracter(c);
                        token = new Token("ART", lexema, pos + 1, true);
                        break;
                    }
                } else {
                    // aritimético "=" fim de arquivo
                    token = new Token("ART", lexema, pos + 1, true);
                    break;
                }
                // *********** OPERADORES RELACIONAIS ***************
            } else if (c == '!') {
                lexema = lexema + c;
                c = this.obterCaractere();
                if (c != null) {
                    if (c == '=') {
                        lexema = lexema + c;
                        token = new Token("REL", lexema, pos + 1, true);
                        break;
                    } else {
                        //OP Lógico "!"
                        this.devolverCaracter(c);
                        token = new Token("LOG", lexema, pos + 1, true);
                        break;
                    }
                } else {
                    // OP Lógico fim de arquivo
                    token = new Token("LOG", lexema, pos + 1, true);
                    break;
                }
            } else if (c == '<' || c == '>') {
                lexema = lexema + c;
                c = this.obterCaractere();
                if (c != null) {
                    if (c == '=') {
                        lexema = lexema + c;
                        token = new Token("REL", lexema, pos + 1, true);
                        break;
                    } else {
                        this.devolverCaracter(c);
                        token = new Token("REL", lexema, pos + 1, true);
                        break;
                    }

                } else {
                    // fim de arquivo
                    token = new Token("REL", lexema, pos + 1, true);
                    break;
                }
                // ************ OPERADORES LÓGICOS 
            } else if (c == '&') {
                lexema = lexema + c;
                c = this.obterCaractere();
                if (c != null) {
                    if (c == '&') {
                        lexema = lexema + c;
                        token = new Token("LOG", lexema, pos + 1, true);
                        break;
                    } else {
                        // é símbolo
                        this.devolverCaracter(c);
                        token = new Token("SIM", lexema, pos + 1, false);
                        break;
                    }
                } else {
                    // é simbolo final de arquivo
                    token = new Token("SIM", lexema, pos + 1, false);
                    break;
                }
            } else if (c == '|') {
                lexema = lexema + c;
                c = this.obterCaractere();
                if (c != null) {
                    if (c == '|') {
                        lexema = lexema + c;
                        token = new Token("LOG", lexema, pos + 1, true);
                        break;
                    } else {
                        // somente um "|" é considerado símbolo
                        this.devolverCaracter(c);
                        token = new Token("SIM", lexema, pos + 1, false);
                        break;
                    }

                } else {
                    // somente um "|" é considerado símbolo fim de arquivo
                    token = new Token("SIM", lexema, pos + 1, false);
                    break;
                }
                // ********** DELIMITADORES
            } else if (c == ';' || c == ',' || c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}' || c == '.') {

                lexema = lexema + c;
                token = new Token("DEL", lexema, pos + 1, true);
                break;

                // ************ CADEIA DE CARACTERES  
            }else if (c == '"'){
                int cont, cont1;
                cont = 0;
                cont1 = 1;
                lexema = lexema + c;
                c = this.obterCaractere();
                while (c != null){                 
                  if(this.isSimbol(c)){                     
                    if ((int) c == 92) {
                        cont = 1;
                        lexema = lexema + c;
                        c = this.obterCaractere();
                        if (c == '"') {
                            cont = 0;
                            lexema = lexema + c;
                            c = this.obterCaractere();
                        } else {
                            cont1 = 0;
                        }
                    }                   
                    if (c == '"') {                        
                        if (cont == 0 || cont1 == 0) {
                            lexema = lexema + c;
                            token = new Token("CDC", lexema, pos + 1, true);
                            break;
                        }
                    }
                }else{                      
                   lexema = lexema + c;
                   token = new Token("CMF", lexema, pos + 1, false);
                   break;
                  }
                    lexema = lexema + c;
                    c = this.obterCaractere();
                    if (c == null) {
                        break;
                    }
                }
                if (c == null) {
                    token = new Token("CMF", lexema, pos + 1, false);
                }
                break;
                //*********** SÍMBOLOS
            } else if ((int) c == 126 || (int) c == 94 || (int) c == 64 || (int) c == 63 || (int) c == 58 || (int) c == 37
                    || (int) c == 36 || (int) c == 35 || (int) c == 92 ||(int) c == 96 || (int) c == 95 || (int) c == 46
                    || (int) c == 39) {
                lexema = lexema + c;
                token = new Token("SIM", lexema, pos + 1, false);
                break;
            }else if((int)c >= 127 && (int)c <= 255){
                lexema = lexema + c;
                token = new Token("SIM", lexema, pos + 1, false);
                break;
            }

        } while (c != null);

        return token;

    }

}
