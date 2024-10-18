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
    public PessoaJuridica getPessoa(int idPessoa) throws SQLException {
        String sql = "SELECT p.*, pj.cnpj "
                + "FROM Pessoas p "
                + "INNER JOIN PessoasJuridicas pj "
                + "ON p.idPessoa = pj.Pessoas_idPessoa "
                + "WHERE p.idPessoa = ?";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        PessoaJuridica pessoaJuridica = null;
        try {
            conn = conectorBD.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idPessoa);
            rs = pst.executeQuery();
            if (rs.next()) {
                pessoaJuridica = new PessoaJuridica(
                    rs.getInt("idPessoa"),
                    rs.getString("nomePessoa"),
                    rs.getString("email"),
                    rs.getString("telefone"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("cnpj")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar a pessoa juridica pelo id: " + e.getMessage());
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
        String sql = "SELECT p.*, pj.cnpj "
                + "FROM Pessoas p "
                + "INNER JOIN PessoasJuridicas pj "
                + "ON p.idPessoa = pj.Pessoas_idPessoa";
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
                        rs.getInt("idPessoa"),
                        rs.getString("nomePessoa"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("cnpj")
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
        String sqlPessoa = "INSERT INTO Pessoas (idPessoa, nomePessoa, email, telefone, logradouro, cidade, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO PessoasJuridicas (idPessoa, cnpj) "
                + "VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstPessoa = null;
        PreparedStatement pstPessoaJuridica = null;
        try {
            conn = conectorBD.getConnection();
            conn.setAutoCommit(false);
            int idPessoa = SequenceManager.getValue("sequenciaIdPessoa");
            //Incluindo na tabela Pessoas
            pstPessoa = conn.prepareStatement(sqlPessoa);
            pstPessoa.setInt(1, idPessoa);
            pstPessoa.setString(2, pessoaJuridica.getNomePessoa());
            pstPessoa.setString(3, pessoaJuridica.getEmail());
            pstPessoa.setString(4, pessoaJuridica.getTelefone());
            pstPessoa.setString(5, pessoaJuridica.getLogradouro());
            pstPessoa.setString(6, pessoaJuridica.getCidade());
            pstPessoa.setString(7, pessoaJuridica.getEstado());
            pstPessoa.executeUpdate();
            //Incluindo na tabela PessoasJuridicas
            pstPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
            pstPessoaJuridica.setInt(1, idPessoa);
            pstPessoaJuridica.setString(2, pessoaJuridica.getCnpj());
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
                + "SET nomePessoa = ?, email = ?, telefone = ?, logradouro = ?, cidade = ?, estado = ? "
                + "WHERE idPessoa = ?";
        String sqlPessoaJuridica = "UPDATE PessoasJuridicas "
                + "SET cnpj = ? "
                + "WHERE idPessoa = ?";
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
            pstPessoa.setInt(7, pessoaJuridica.getIdPessoa());
            pstPessoa.executeUpdate();
            //Alterando na tabela PessoasJuridicas
            pstPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
            pstPessoaJuridica.setString(1, pessoaJuridica.getCnpj());
            pstPessoaJuridica.setInt(2, pessoaJuridica.getIdPessoa());
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
    public void excluir(int idPessoa) throws SQLException {
        String sqlPessoa = "DELETE FROM Pessoas "
                + "WHERE idPessoa = ?";
        String sqlPessoaJuridica = "DELETE FROM PessoasJuridicas "
                + "WHERE idPessoa = ?";
        Connection conn = null;
        PreparedStatement pstPessoa = null;
        PreparedStatement pstPessoaJuridica = null;
        try {
            conn = conectorBD.getConnection();
            conn.setAutoCommit(false);
            //Deletando da tabela PessoasJuridicas
            pstPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
            pstPessoaJuridica.setInt(1, idPessoa);
            pstPessoaJuridica.executeUpdate();
            //Deletando da tabela Pessoas
            pstPessoa = conn.prepareStatement(sqlPessoa);
            pstPessoa.setInt(1, idPessoa);
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
