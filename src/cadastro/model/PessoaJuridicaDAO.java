package cadastro.model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import cadastro.model.util.ConectorBD;
import cadastrobd.model.PessoaJuridica;
import cadastro.model.util.SequenceManager;

public class PessoaJuridicaDAO {
    //Instância para ConectorBD
    private final ConectorBD conectorBD = new ConectorBD();
    
    //Método para retornar uma pessoa jurídica pelo ID
    public PessoaJuridica getPessoa(int IDPessoa) throws SQLException {
        String sql = "SELECT p.*, pj.CNPJ "
                + "FROM Pessoas p "
                + "INNER JOIN PessoasJuridicas pj "
                + "ON p.IDPessoa = pj.Pessoas_IDPessoa "
                + "WHERE p.IDPessoa = ?";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        PessoaJuridica pessoaJuridica = null;
        try {
            conn = conectorBD.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, IDPessoa);
            rs = pst.executeQuery();
            if (rs.next()) {
                pessoaJuridica = new PessoaJuridica(
                    rs.getInt("IDPessoa"),
                    rs.getString("NomePessoa"),
                    rs.getString("Email"),
                    rs.getString("Telefone"),
                    rs.getString("Logradouro"),
                    rs.getString("Cidade"),
                    rs.getString("Estado"),
                    rs.getString("CNPJ")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar a pessoa juridica pelo ID: " + e.getMessage());
            throw e;
        } finally {
            conectorBD.close(rs);
            conectorBD.close(pst);
            conectorBD.close(conn);
        }
        return pessoaJuridica;
    }
    
    //Método para retornar todas as pessoas jurídicas do banco de dados
    public List<PessoaJuridica> getPessoas() throws SQLException {
        String sql = "SELECT p.*, pj.CNPJ "
                + "FROM Pessoas p "
                + "INNER JOIN PessoasJuridicas pj "
                + "ON p.IDPessoa = pj.Pessoas_IDPessoa";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<PessoaJuridica> listaPj = new ArrayList<>();
        try {
            conn = conectorBD.getConnection();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                PessoaJuridica pessoaJuridica = new PessoaJuridica(
                        rs.getInt("IDPessoa"),
                        rs.getString("NomePessoa"),
                        rs.getString("Email"),
                        rs.getString("Telefone"),
                        rs.getString("Logradouro"),
                        rs.getString("Cidade"),
                        rs.getString("Estado"),
                        rs.getString("CNPJ")
                );
                listaPj.add(pessoaJuridica);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar todas as pessoas juridicas: " + e.getMessage());
            throw e;
        } finally {
            conectorBD.close(rs);
            conectorBD.close(pst);
            conectorBD.close(conn);
        }
        return listaPj;
    }
    
    //Método para incluir uma pessoa jurídica
    public void incluir(PessoaJuridica pessoaJuridica) throws SQLException {
        String sqlPessoa = "INSERT INTO Pessoas (IDPessoa, NomePessoa, Email, Telefone, Logradouro, Cidade, Estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO PessoasJuridicas (Pessoas_IDPessoa, CNPJ) "
                + "VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstPessoa = null;
        PreparedStatement pstPessoaJuridica = null;
        try {
            conn = conectorBD.getConnection();
            conn.setAutoCommit(false);
            int IDPessoa = SequenceManager.getValue("Sequencia_IDPessoa");
            pessoaJuridica.setIDPessoa(IDPessoa);
            //Incluindo na tabela Pessoas
            pstPessoa = conn.prepareStatement(sqlPessoa);
            pstPessoa.setInt(1, IDPessoa);
            pstPessoa.setString(2, pessoaJuridica.getNomePessoa());
            pstPessoa.setString(3, pessoaJuridica.getEmail());
            pstPessoa.setString(4, pessoaJuridica.getTelefone());
            pstPessoa.setString(5, pessoaJuridica.getLogradouro());
            pstPessoa.setString(6, pessoaJuridica.getCidade());
            pstPessoa.setString(7, pessoaJuridica.getEstado());
            pstPessoa.executeUpdate();
            //Incluindo na tabela PessoasJuridicas
            pstPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
            pstPessoaJuridica.setInt(1, IDPessoa);
            pstPessoaJuridica.setString(2, pessoaJuridica.getCNPJ());
            pstPessoaJuridica.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e2) {
                    System.out.println("Erro ao desfazer a inclusao: " + e2.getMessage());
                }
            }
            System.out.println("Erro ao incluir a pessoa juridica: " + e.getMessage());
            throw e;
        } finally {
            conectorBD.close(pstPessoaJuridica);
            conectorBD.close(pstPessoa);
            conectorBD.close(conn);
        }
    }
    
    //Método para alterar uma pessoa jurídica
    public void alterar(PessoaJuridica pessoaJuridica) throws SQLException {
        String sqlPessoa = "UPDATE Pessoas "
                + "SET NomePessoa = ?, Email = ?, Telefone = ?, Logradouro = ?, Cidade = ?, Estado = ? "
                + "WHERE IDPessoa = ?";
        String sqlPessoaJuridica = "UPDATE PessoasJuridicas "
                + "SET CNPJ = ? "
                + "WHERE Pessoas_IDPessoa = ?";
        Connection conn = null;
        PreparedStatement pstPessoa = null;
        PreparedStatement pstPessoaJuridica = null;
        try {
            conn = conectorBD.getConnection();
            conn.setAutoCommit(false);
            //Alterando na tabela Pessoas
            pstPessoa = conn.prepareStatement(sqlPessoa);
            pstPessoa.setString(1, pessoaJuridica.getNomePessoa());
            pstPessoa.setString(2, pessoaJuridica.getEmail());
            pstPessoa.setString(3, pessoaJuridica.getTelefone());
            pstPessoa.setString(4, pessoaJuridica.getLogradouro());
            pstPessoa.setString(5, pessoaJuridica.getCidade());
            pstPessoa.setString(6, pessoaJuridica.getEstado());
            pstPessoa.setInt(7, pessoaJuridica.getIDPessoa());
            pstPessoa.executeUpdate();
            //Alterando na tabela PessoasJuridicas
            pstPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
            pstPessoaJuridica.setString(1, pessoaJuridica.getCNPJ());
            pstPessoaJuridica.setInt(2, pessoaJuridica.getIDPessoa());
            pstPessoaJuridica.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e2) {
                    System.out.println("Erro ao desfazer a alteracao: " + e2.getMessage());
                }
            }
            System.out.println("Erro ao alterar a pessoa juridica: " + e.getMessage());
            throw e;
        } finally {
            conectorBD.close(pstPessoaJuridica);
            conectorBD.close(pstPessoa);
            conectorBD.close(conn);
        }
    }
    
    //Método para excluir uma pessoa jurídica
    public void excluir(int IDPessoa) throws SQLException {
        String sqlPessoa = "DELETE FROM Pessoas "
                + "WHERE IDPessoa = ?";
        String sqlPessoaJuridica = "DELETE FROM PessoasJuridicas "
                + "WHERE Pessoas_IDPessoa = ?";
        Connection conn = null;
        PreparedStatement pstPessoa = null;
        PreparedStatement pstPessoaJuridica = null;
        try {
            conn = conectorBD.getConnection();
            conn.setAutoCommit(false);
            //Deletando da tabela PessoasJuridicas
            pstPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
            pstPessoaJuridica.setInt(1, IDPessoa);
            pstPessoaJuridica.executeUpdate();
            //Deletando da tabela Pessoas
            pstPessoa = conn.prepareStatement(sqlPessoa);
            pstPessoa.setInt(1, IDPessoa);
            pstPessoa.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e2) {
                    System.out.println("Erro ao desfazer a exclusao: " + e2.getMessage());
                }
            }
            System.out.println("Erro ao excluir a pessoa juridica: " + e.getMessage());
            throw e;
        } finally {
            conectorBD.close(pstPessoaJuridica);
            conectorBD.close(pstPessoa);
            conectorBD.close(conn);
        }
    }
}
