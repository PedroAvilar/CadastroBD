package cadastro.model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import cadastro.model.util.ConectorBD;
import cadastrobd.model.PessoaFisica;
import cadastro.model.util.SequenceManager;

public class PessoaFisicaDAO {
    //Instância para ConectorBD
    private final ConectorBD conectorBD = new ConectorBD();
    
    //Método para retornar uma pessoa física pelo ID
    public PessoaFisica getPessoa(int idPessoa) throws SQLException {
        String sql = "SELECT p.*, pf.cpf "
                + "FROM Pessoas p "
                + "INNER JOIN PessoasFisicas pf "
                + "ON p.idPessoa = pf.Pessoas_idPessoa "
                + "WHERE p.idPessoa = ?";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        PessoaFisica pessoaFisica = null;
        try {
            conn = conectorBD.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idPessoa);
            rs = pst.executeQuery();
            if (rs.next()) {
                pessoaFisica = new PessoaFisica(
                    rs.getInt("idPessoa"),
                    rs.getString("nomePessoa"),
                    rs.getString("email"),
                    rs.getString("telefone"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("cpf")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar a pessoa fisica pelo id: " + e.getMessage());
            throw e;
        } finally {
            conectorBD.close(rs);
            conectorBD.close(pst);
            conectorBD.close(conn);
        }
        return pessoaFisica;
    }
    
    //Método para retornar todas as pessoas físicas do banco de dados
    public List<PessoaFisica> getPessoas() throws SQLException {
        String sql = "SELECT p.*, pf.cpf "
                + "FROM Pessoas p "
                + "INNER JOIN PessoasFisicas pf "
                + "ON p.idPessoa = pf.Pessoas_idPessoa";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<PessoaFisica> listaPf = new ArrayList<>();
        try {
            conn = conectorBD.getConnection();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                PessoaFisica pessoaFisica = new PessoaFisica(
                    rs.getInt("idPessoa"),
                    rs.getString("nomePessoa"),
                    rs.getString("email"),
                    rs.getString("telefone"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("cpf")
                );
                listaPf.add(pessoaFisica);
            }
        } catch (SQLException e){
            System.out.println("Erro ao buscar todas as pessoas fisicas: " + e.getMessage());
            throw e;
        } finally {
            conectorBD.close(rs);
            conectorBD.close(pst);
            conectorBD.close(conn);
        }
        return listaPf;
    }
    
    //Método para incluir uma pessoa física
    public void incluir(PessoaFisica pessoaFisica) throws SQLException {
        String sqlPessoa = "INSERT INTO Pessoas (idPessoa, nomePessoa, email, telefone, logradouro, cidade, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO PessoasFisicas (idPessoa, cpf) "
                + "VALUES (?,?)";
        Connection conn = null;
        PreparedStatement pstPessoa = null;
        PreparedStatement pstPessoaFisica = null;
        try {
            conn = conectorBD.getConnection();
            conn.setAutoCommit(false);
            int idPessoa = SequenceManager.getValue("sequenciaIdPessoa");
            //Incluindo na tabela Pessoas
            pstPessoa = conn.prepareStatement(sqlPessoa);
            pstPessoa.setInt(1, idPessoa);
            pstPessoa.setString(2, pessoaFisica.getNomePessoa());
            pstPessoa.setString(3, pessoaFisica.getEmail());
            pstPessoa.setString(4, pessoaFisica.getTelefone());
            pstPessoa.setString(5, pessoaFisica.getLogradouro());
            pstPessoa.setString(6, pessoaFisica.getCidade());
            pstPessoa.setString(7, pessoaFisica.getEstado());
            pstPessoa.executeUpdate();
            //Incluindo na tabela PessoasFisicas
            pstPessoaFisica = conn.prepareStatement(sqlPessoaFisica);
            pstPessoaFisica.setInt(1, idPessoa);
            pstPessoaFisica.setString(2, pessoaFisica.getCpf());
            pstPessoaFisica.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e2) {
                    System.out.println("Erro ao desfazer a inclusao: " + e2.getMessage());
                }
            }
            System.out.println("Erro ao incluir a pessoa fisica: " + e.getMessage());
            throw e;
        } finally {
            conectorBD.close(pstPessoaFisica);
            conectorBD.close(pstPessoa);
            conectorBD.close(conn);
        }
    }
        
    //Método para alterar uma pessoa física
    public void alterar(PessoaFisica pessoaFisica) throws SQLException {
        String sqlPessoa = "UPDATE Pessoas "
                + "SET nomePessoa = ?, email = ?, telefone = ?, logradouro = ?, cidade = ?, estado = ? "
                + "WHERE idPessoa = ?";
        String sqlPessoaFisica = "UPDATE PessoasFisicas "
                + "SET cpf = ? "
                + "WHERE idPessoa = ?";
        Connection conn = null;
        PreparedStatement pstPessoa = null;
        PreparedStatement pstPessoaFisica = null;
        try {
            conn = conectorBD.getConnection();
            conn.setAutoCommit(false);
            //Alterando na tabela Pessoas
            pstPessoa = conn.prepareStatement(sqlPessoa);
            pstPessoa.setString(1, pessoaFisica.getNomePessoa());
            pstPessoa.setString(2, pessoaFisica.getEmail());
            pstPessoa.setString(3, pessoaFisica.getTelefone());
            pstPessoa.setString(4, pessoaFisica.getLogradouro());
            pstPessoa.setString(5, pessoaFisica.getCidade());
            pstPessoa.setString(6, pessoaFisica.getEstado());
            pstPessoa.setInt(7, pessoaFisica.getIdPessoa());
            pstPessoa.executeUpdate();
            //Alterando na tabela PessoasFisicas
            pstPessoaFisica = conn.prepareStatement(sqlPessoaFisica);
            pstPessoaFisica.setString(1, pessoaFisica.getCpf());
            pstPessoaFisica.setInt(2, pessoaFisica.getIdPessoa());
            pstPessoaFisica.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e2) {
                    System.out.println("Erro ao desfazer a alteracao: " + e2.getMessage());
                }
            }
            System.out.println("Erro ao alterar a pessoa fisica: " + e.getMessage());
            throw e;
        } finally {
            conectorBD.close(pstPessoaFisica);
            conectorBD.close(pstPessoa);
            conectorBD.close(conn);
        }
    }
    
    //Método para excluir uma pessoa física
    public void excluir(int idPessoa) throws SQLException {
        String sqlPessoa = "DELETE FROM Pessoas "
                + "WHERE idPessoa = ?";
        String sqlPessoaFisica = "DELETE FROM PessoasFisicas "
                + "WHERE idPessoa = ?";
        Connection conn = null;
        PreparedStatement pstPessoa = null;
        PreparedStatement pstPessoaFisica = null;
        try {
            conn = conectorBD.getConnection();
            conn.setAutoCommit(false);
            //Deletando da tabela PessoasFisicas
            pstPessoaFisica = conn.prepareStatement(sqlPessoaFisica);
            pstPessoaFisica.setInt(1, idPessoa);
            pstPessoaFisica.executeUpdate();
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
            System.out.println("Erro ao excluir a pessoa fisica: " + e.getMessage());
            throw e;
        } finally {
            conectorBD.close(pstPessoaFisica);
            conectorBD.close(pstPessoa);
            conectorBD.close(conn);
        }
    }
}
