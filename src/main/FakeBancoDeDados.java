
package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FakeBancoDeDados {
    private static Vector<Produto> produtos;
    //leitura das informações da planilha do excel
    private static void cargaArquivo(){
        //ajuste na criação do vetor de produtos estático
        if(produtos == null){
            produtos = new Vector<>();
        }else{
            produtos.clear();
        }
        File arquivoCsv = new File("C:\\Users\\Yasminn\\Documents\\1 - Main\\EAD IFMG\\Tópicos em Desenvolvimento de Software\\arquivos.csv");
        try{
            //estruturas de leitura do arquivo
            FileReader marcaLeitura = new FileReader (arquivoCsv);
            BufferedReader buffLeitura = new BufferedReader(marcaLeitura);
            //******ler cada linha******
            //lendo a primeira linha descartando o cabeçalho
            buffLeitura.readLine();
            String linha = buffLeitura.readLine();
            while(linha != null){
                //lendo as linhas seguintes até o final do arquivo
                String infos[] = linha.split(";");
                int cod = Integer.parseInt(infos[0]);
                String nome = infos[1];
                double preco = Double.parseDouble(infos[2]);
                int qnt = Integer.parseInt(infos[3]);
                //adição dos produtos no vetor dinâmico
                produtos.add(new Produto(cod, nome, preco, qnt));
                //lendo a próxima linha
                linha = buffLeitura.readLine();
            }
            //Liberando o arquivo para outros processos.
            buffLeitura.close();
        }catch(FileNotFoundException ex){
            System.err.println("Arquivo especificado não existe.");
        }catch(IOException e){
            System.err.println("Arquivo corrompido");
        }
    }
    public static Produto consultaProdutoCod(int cod){
        //o arquivo é carregado se ainda não tiver carregado anteriormente
        if(produtos == null){
            cargaArquivo();
        }
        
        for(Produto prodI:produtos){ //o mesmo que for(int i = 0; i < produtos.size(); i++);
            if(prodI.getCodigo() == cod){  //if(produtos.get(i).getCodigo() == cod)
                return prodI;
            }
        }
        //não existe produto com o código especificado
        return null;
    }
    public static void atualizaArquivo(){
        File arquivo = new File("C:\\Users\\Yasminn\\Documents\\1 - Main\\EAD IFMG\\Tópicos em Desenvolvimento de Software\\arquivos.csv");
        try {
            FileWriter escritor = new FileWriter(arquivo);
            BufferedWriter buffEscrita = new BufferedWriter(escritor);
            for(int i = 0; i < produtos.size();i++){
                buffEscrita.write(produtos.get(i)+"\n"); //a chamada do toString é desnecessária, mas pode ser feita.
            }
            buffEscrita.flush();
            buffEscrita.close();
        } catch (IOException ex) {
            System.err.println("Dispositivo com falha");
        }
    }
}
