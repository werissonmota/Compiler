package analisadorlexico;

/**
 *
 * @author Aurelio, Werisson
 */
public class Token {
    private int linha;
    private String tipo,lexema;
    private boolean isValid;
    
    public Token(){
        
    }
    
    public Token(String tipo,String lexema, int linha, boolean isValid){
        this.linha = linha;
        this.tipo = tipo;
        this.lexema = lexema;
        this.isValid = isValid;
    }

    /**
     * @return the linha
     */
    public int getLinha() {
        return linha;
    }

    /**
     * @param linha the linha to set
     */
    public void setLinha(int linha) {
        this.linha = linha;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the lexema
     */
    public String getLexema() {
        return lexema;
    }

    /**
     * @param lexema the lexema to set
     */
    public void setLexema(String lexema) {
        this.lexema = lexema;
    }
    
    public boolean isValid(){
        return this.isValid;
    }
    
   
    
}
