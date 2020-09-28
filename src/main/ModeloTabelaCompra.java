
package main;

import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;


public class ModeloTabelaCompra extends AbstractTableModel {
    private Vector<Produto> carrinhoCompra;
    private CompraGUI painel;
    
    public ModeloTabelaCompra(CompraGUI painel){
        this.carrinhoCompra = new Vector<>();
        this.painel = painel;
    }
    @Override
    public int getRowCount() {
        return carrinhoCompra.size(); //identifica quantos produtos existem no carrinho
    }

    @Override
    public int getColumnCount() { //número de colunas da tabela
        return 4;
    }

    @Override
    public Object getValueAt(int linha, int coluna) { //recebe informações sobre a linha e a coluna que estão sendo preenchidas
        Produto temporario = carrinhoCompra.get(linha);
        switch(coluna){
            case 0: 
                return temporario.getNome();
            case 1: 
                return temporario.getPreco();
            case 2: 
                return temporario.getQuantidade();
            case 3: 
                return temporario.getQuantidade() * temporario.getPreco();
            default: 
                return null;      
        }
    }
    //^^^métodos que tornam a classe abstrata
    
    
    public void addNovoProduto(Produto vendido){
        this.carrinhoCompra.add(vendido);
    }
    
    public void removeProdutoCarrinho(int indice){
        this.carrinhoCompra.remove(indice);
    }
    @Override
    public String getColumnName(int coluna){
        switch(coluna){
            case 0: 
                return "Nome";
            case 1: 
                return "Preço Unit.";
            case 2: 
                return "Quantidade";
            case 3: 
                return "Preço parcial";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        if (coluna == 2){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void setValueAt(Object novoValor, int linha, int coluna) {
        if(coluna == 2){
            //requisitando a senha de acesso do gerente para permitir a modificação de quantidades
            String senha = JOptionPane.showInputDialog(null, "Informe a senha do gerente", 
                    "Operação restrita", JOptionPane.INFORMATION_MESSAGE);
            if(senha != null && senha.equals("ifmg")){
                carrinhoCompra.get(linha).setQuantidade((int)novoValor);
                //a tabela e o valor da compra serão atualizados
                this.painel.atualizaQuantidades();
            }else{
                JOptionPane.showMessageDialog(null, "Senha digitada incorreta", "ERRO DE SENHA", JOptionPane.WARNING_MESSAGE);
            }
            
        }
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
        switch(coluna){
            case 0:
                return String.class;
            case 1:
                return Double.class;
            case 2: 
                return Integer.class;
            case 3:
                return Double.class;
            default:
                return null;
        }
    }
    
    public double calculaPrecoParcialCompra(){
        double valor = 0.0;
        //recalculando o valor do carrinho com os produtos atuais
        for(Produto p : carrinhoCompra){ //o mesmo que for (int i = 0; i < carrinhoCompra.size(); i++ 
            valor += p.getQuantidade() * p.getPreco(); //carrinhoCompra.get(i);
        }
        return valor;
    }

    public Vector<Produto> produtosCarrinho() {
        return carrinhoCompra;
    }
    
    public void limparCarrinho(){
        this.carrinhoCompra.clear();
    }
    
}
