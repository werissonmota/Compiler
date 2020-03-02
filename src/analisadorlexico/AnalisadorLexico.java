/** *****************************************************************************
 * Autores: Aurélio Rocha Barreto, Werisson Mota
 * Componente Curricular: MI - Processadores de Linguagem de Programação EXA869- P02 - 2019.2.
 * Concluido em: 18/11/2019
 * Declaro que este código foi elaborado por nós de forma individual e não contém nenhum
 * trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 * apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 * de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 * do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ***************************************************************************************** */
package analisadorlexico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Aurelio, Werisson
 */
public class AnalisadorLexico {

    public ArrayList analiseLexica() throws FileNotFoundException, IOException {
        File dir = new File("input");
        File aFile[] = dir.listFiles();
        ArrayList<ArrayList> arrayDeArrays = new <ArrayList>ArrayList();
        ArrayList<Token> arrayDeTokens;
        Scanner scanner;
        Token token;
        ArrayList<Token> errosLexicos;       
        if(aFile.length == 0){
            System.out.println("Não há arquivos de entrada");
        }
        for (int i = 0; i < aFile.length; i++) {
            arrayDeTokens = new <Token>ArrayList();
            scanner = new Scanner(aFile[i]);
            token = scanner.getToken();
            errosLexicos = new <Token>ArrayList();          
            while (token != null) {

                if (!token.getTipo().equals("COM")) {
                    if (token.isValid()) {
                        scanner.setToken(token);
                        arrayDeTokens.add(token);
                        token = scanner.getToken();                        
                    } else {
                        errosLexicos.add(token);
                        token = scanner.getToken();
                    }
                } else {
                    token = scanner.getToken();
                }
            }
            arrayDeArrays.add(arrayDeTokens);
            if (errosLexicos.isEmpty()) {
                scanner.semErros();
            } else {
                scanner.setErros(errosLexicos.iterator());
            }
            scanner.fecharArquivos();
        }
       return arrayDeArrays;
    }

    

}
