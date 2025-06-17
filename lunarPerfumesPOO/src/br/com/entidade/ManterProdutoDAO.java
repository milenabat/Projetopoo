/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;
import br.com.controle.Produto;
import br.com.controle.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Samil
 */
public class ManterProdutoDAO extends DAO {

    private PreparedStatement pst;

    // Cadastrar um novo produto
    public void cadastrarProduto(Produto p) {
        try {
            abrirBanco();
            String sql = "INSERT INTO produto(nome, tipo, valor) VALUES (?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, p.getNome());
            pst.setString(2, p.getTipo());
            pst.setDouble(3, p.getValor());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            fecharBanco();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        }
        
    }

    public void alterarProduto(Produto p) {
        try {
            abrirBanco();
            String sql = "UPDATE produto SET nome = ?, tipo = ?, valor = ? WHERE codigo = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, p.getNome());
            pst.setString(2, p.getTipo());
            pst.setDouble(3, p.getValor());
            pst.setInt(4, p.getCodigo());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Produto alterado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado para alteração.");
            }
            fecharBanco();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar produto: " + e.getMessage());
        }
    }

    public void deletarProduto(String nomeProduto) {
        try {
            abrirBanco();
            String sql = "DELETE FROM produto WHERE codigo = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, nomeProduto);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Produto deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado para deletar.");
            }
            fecharBanco();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar produto: " + e.getMessage());
        }
    }
    
     public Produto pesquisarPorCodigo(int codigo) throws Exception {
         Produto p = null;
        try {
            abrirBanco();
            String query = "select * FROM produto Where codigo=?";
            pst = (PreparedStatement) con.prepareStatement(query);
            pst.setInt(1, p.getCodigo());
            ResultSet tr = (ResultSet) pst.executeQuery();
            if (tr.next()) {
                p.setCodigo(tr.getInt("codigo"));
                p.setNome(tr.getString("nome"));
                p.setTipo(tr.getString("tipo"));
                p.setValor(tr.getInt("valor"));
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado! ");
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
     return p;
}

     
    public ArrayList<Produto> listarProdutos() {
        ArrayList<Produto> lista = new ArrayList<>();
        try {
            abrirBanco();
            String sql = "SELECT * FROM produto";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setCodigo(rs.getInt("codigo"));
                p.setNome(rs.getString("nome"));
                p.setTipo(rs.getString("tipo"));
                p.setValor(rs.getDouble("valor"));
                lista.add(p);
            }
            fecharBanco();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        }
        return lista;
    }

    public void deletar(Produto p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void cadastrarVendedor(Vendedor v) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Produto pesquisarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
