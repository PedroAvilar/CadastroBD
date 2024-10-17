package cadastro.model.util;
import java.sql.*;

public class ConectorBD {
    private Connection conn;
    private PreparedStatement pst;
    private ResultSet rs;
    
    //Constantes para a conexão com o banco de dados
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=loja;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "loja";
    private static final String PASSWORD = "loja";
    
    //Método para retornar a conexão
    public Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        return conn;
    }
    
    //Método para retornar um objeto do tipo PreparedStatement
    public PreparedStatement getPrepared(String sql) throws SQLException {
        pst = getConnection().prepareStatement(sql);
        return pst;
    }
    
    //Método para retornar o ResultSet relacionado a uma consulta
    public ResultSet getSelect(String sql) throws SQLException {
        pst = getPrepared(sql);
        rs = pst.executeQuery();
        return rs;
    }
    
    //Métodos para fechar os recursos
    public void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar o Statement: " + e);
            }
        }
    }
    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar o ResultSet: " + e);
            }
        }
    }
    public void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e);
            }
        }
    }
}
