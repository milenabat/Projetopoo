package br.com.entidade;

import br.com.controle.Vendedor;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManterVendedorDAO {

    private Connection con;
    private PreparedStatement pst;

    private void abrirBanco() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lunarperfumespoo", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir conexão: " + e.getMessage());
        }
    }

    private void fecharBanco() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
        }
    }

    public void inserir(Vendedor v) throws Exception {
        try {
            abrirBanco();
            String query = "INSERT INTO vendedor(nome, email) VALUES (?, ?)";
            pst = con.prepareStatement(query);
            pst.setString(1, v.getNome());
            pst.setString(2, v.getEmail());
            pst.execute();
            fecharBanco();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir vendedor: " + e.getMessage());
        }
    }

    public void deletar(Vendedor v) {
        try {
            abrirBanco();
            String query = "DELETE FROM vendedor WHERE codigo = ?";
            pst = con.prepareStatement(query);
            pst.setDouble(1, v.getCodigo());
            pst.execute();
            fecharBanco();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar vendedor: " + e.getMessage());
        }
    }

    public ArrayList<Vendedor> PesquisarTudo() throws Exception {
        ArrayList<Vendedor> vendedores = new ArrayList<>();
        try {
            abrirBanco();
            String query = "SELECT * FROM vendedor";
            pst = con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            while (tr.next()) {
                Vendedor v = new Vendedor();
                v.setCodigo(tr.getDouble("codigo"));
                v.setNome(tr.getString("nome"));
                v.setEmail(tr.getString("email"));
                vendedores.add(v);
            }
            fecharBanco();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar vendedores: " + e.getMessage());
        }
        return vendedores;
    }

    public void PesquisarRegistro(Vendedor v) throws Exception {
        try {
            abrirBanco();
            String query = "SELECT * FROM vendedor WHERE codigo = ?";
            pst = con.prepareStatement(query);
            pst.setDouble(1, v.getCodigo());
            ResultSet tr = pst.executeQuery();
            if (tr.next()) {
                v.setCodigo(tr.getDouble("codigo"));
                v.setNome(tr.getString("nome"));
                v.setEmail(tr.getString("email"));
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado!");
            }
            fecharBanco();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar registro: " + e.getMessage());
        }
    }

    public void AlterarVendedor(Vendedor v) throws Exception {
        try {
            abrirBanco();
            String query = "UPDATE vendedor SET nome = ?, email = ? WHERE codigo = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, v.getNome());
            pst.setString(2, v.getEmail());
            pst.setDouble(3, v.getCodigo());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vendedor alterado com sucesso!");
            fecharBanco();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar vendedor: " + e.getMessage());
        }
    }

    public ArrayList<Vendedor> listarVendedor() {
        ArrayList<Vendedor> listar = new ArrayList<>();
        try {
            abrirBanco();
            String sql = "SELECT * FROM vendedor";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Vendedor v = new Vendedor();
                v.setCodigo(rs.getDouble("codigo"));
                v.setNome(rs.getString("nome"));
                v.setEmail(rs.getString("email"));
                listar.add(v); // corrigido aqui
            }
            fecharBanco();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar vendedor: " + e.getMessage());
        }
        return listar;
    }

    public void cadastrar(Vendedor v) {
        // Se quiser, você pode fazer essa função chamar o inserir()
        try {
            inserir(v);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar vendedor: " + e.getMessage());
        }
    }
}
