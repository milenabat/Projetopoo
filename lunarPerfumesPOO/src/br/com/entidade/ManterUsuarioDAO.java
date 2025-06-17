/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Produto;
import br.com.controle.Usuario;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author laboratorio
 */
public class ManterUsuarioDAO extends DAO{
    public void inserir(Usuario u) throws Exception {
    try {
    abrirBanco();                      
    String query = "INSERT INTO usuario(codigo,nome,email,telefone) "
            + "values(null,?,?,?)";
    pst=(PreparedStatement) con.prepareStatement(query);
    pst.setString(1, u.getNome());
    pst.setString(2, u.getEmail());
	pst.setString(3, u.getTelefone());
    pst.execute();
    fecharBanco();
    } catch (Exception e) {
        System.out.println("Erro " + e.getMessage());
    }
    }
    
    public void deletar(Usuario u) throws Exception, Exception{
	try {
	abrirBanco();
	String query = "DELETE FROM usuario WHERE codigo = ?";
	pst = (PreparedStatement) con.prepareStatement(query);
	pst.setDouble(1, u.getCodigo());
	pst.execute();
        JOptionPane.showMessageDialog(null, "Usuario deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	fecharBanco();
		
	} catch (Exception e) {
		System.out.println("Erro " + e.getMessage());
	}
    }
    
    public ArrayList<Usuario> PesquisarTudo() throws Exception {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            abrirBanco();
            String query = "select * FROM usuario";
            pst = (PreparedStatement) con.prepareStatement(query);
            ResultSet tr = (ResultSet) pst.executeQuery();
            Usuario u;
            while (tr.next()) {
                u = new Usuario();
                u.setCodigo(tr.getDouble("codigo"));
                u.setNome(tr.getString("nome"));
                usuarios.add(u);
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
        return usuarios;
    }
    
    public void PesquisarRegistro(Usuario u) throws Exception {
        try {
            abrirBanco();
            String query = "select * FROM usuario Where codigo=?";
            pst = (PreparedStatement) con.prepareStatement(query);
            pst.setDouble(1, u.getCodigo());
            ResultSet tr = (ResultSet) pst.executeQuery();
            if (tr.next()) {
                u.setCodigo(tr.getDouble("codigo"));
                u.setNome(tr.getString("nome"));
                u.setEmail(tr.getString("email"));
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado! ");
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
        
    }
    public void AlterarUsuario(Usuario u) throws Exception {
        abrirBanco();
        String query = "UPDATE usuario set nome = ?, email = ? where codigo = ?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, u.getNome());
        pst.setString(2, u.getEmail());
        pst.setDouble(3, u.getCodigo());
        pst.executeUpdate();
    }
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        try {
            abrirBanco();
            String sql = "SELECT * FROM usuario";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setCodigo(rs.getDouble("codigo"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
               u.setTelefone(rs.getString("telefone"));
                lista.add(u);
            }
            fecharBanco();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar usuario: " + e.getMessage());
        }
        return lista;
    }
    
}
