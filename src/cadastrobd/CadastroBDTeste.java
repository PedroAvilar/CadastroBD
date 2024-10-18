package cadastrobd;
import cadastro.model.PessoaFisicaDAO;
import cadastro.model.PessoaJuridicaDAO;
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import java.sql.SQLException;
import java.util.List;

public class CadastroBDTeste {

    public static void main(String[] args) throws SQLException {
        //Instâncias dos objetos DAO
        PessoaFisicaDAO pfDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pjDAO = new PessoaJuridicaDAO();
        
        try {
            //Instancia de pessoa física persistida no banco de dados
            PessoaFisica pf = new PessoaFisica(0, "Claudio Pontes", "claudio06@email.com",
                    "11955664489", "Rua Moraes, 17", "Lapa", "SP", "44888959610");
            pfDAO.incluir(pf);
            System.out.println("Pessoa fisica incluida com sucesso: ");
            pf.exibir();
            
            //Alterar os dados da pessoa física no banco de dados
            pf.setNomePessoa("Claudio Santos");
            pf.setLogradouro("Rua Moraes, 71");
            pfDAO.alterar(pf);
            System.out.println("Pessoa fisica alterada com sucesso: ");
            pf.exibir();
            
            //Consultar todas as pessoas físicas no banco de dados
            List<PessoaFisica> listaPf = pfDAO.getPessoas();
            System.out.println("Lista de todas as pessoas fisicas: ");
            for (PessoaFisica pessoa : listaPf) {
                pessoa.exibir();
            }
            
            //Excluir a pessoa física criada anteriormente
            pfDAO.excluir(pf.getIDPessoa());
            System.out.println("Pessoa fisica excluida com sucesso.");
            System.out.println("______________________________________");
            
            //Instancia de uma pessoa juridica persistida no banco de dados
            PessoaJuridica pj = new PessoaJuridica(0, "Sacola Verde", "sacver@email.com",
                    "3333445566", "Avenida Kimber, 5", "Rio Claro", "MG", "12345678508199");
            pjDAO.incluir(pj);
            System.out.println("Pessoa juridica incluida com sucesso: ");
            pj.exibir();
            
            //Alterar os dados da pessoa jurídica no banco de dados
            pj.setLogradouro("Rua Kimber, 5");
            pj.setCidade("Ceu Claro");
            pjDAO.alterar(pj);
            System.out.println("Pessoa juridica alterada com sucesso: ");
            pj.exibir();
            
            //Consultar todas as pessoas jurídicas no banco de dados
            List<PessoaJuridica> listaPj = pjDAO.getPessoas();
            System.out.println("Lista de todas as pessoas juridicas: ");
            for (PessoaJuridica pessoa : listaPj) {
                pessoa.exibir();
            }
            
            //Ecluir a pessoa jurídica criada anteriormente
            pjDAO.excluir(pj.getIDPessoa());
            System.out.println("Pessoa juridica excluida com sucesso.");
            System.out.println("______________________________________");
        } catch (SQLException e) {
            System.out.println("Erro durante os testes: " + e.getMessage());
        }
    }
}
