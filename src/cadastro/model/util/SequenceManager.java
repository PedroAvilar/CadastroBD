package cadastro.model.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceManager {

    // Método para obter o próximo valor de uma sequência
    public static int getValue(String sequenciaNome) throws SQLException {
        String sql = "SELECT NEXT VALUE FOR " + sequenciaNome;
        int nextValue = -1;
        
        //Instância para ConectorBD
        ConectorBD conectorBD = new ConectorBD();
        
        //Acesso e retorno do banco de dados
        try (Connection conn = conectorBD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                nextValue = rs.getInt(1);
            } else {
                throw new SQLException("Nenhum valor retornado para a sequência: " + sequenciaNome);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter valor da sequência: " + e.getMessage());
            throw e;
        }
        return nextValue;
    }
}
