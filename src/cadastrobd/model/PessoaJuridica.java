package cadastrobd.model;

public class PessoaJuridica extends Pessoa {
    //Campo
    private String cnpj;
    
    //Construtor padrão
    public PessoaJuridica() {
        super();
    }
    
    //Construtor completo
    public PessoaJuridica(int idPessoa, String nomePessoa, String email, String telefone,
            String logradouro, String cidade, String estado, String cnpj) {
        super(idPessoa, nomePessoa, email, telefone, logradouro, cidade, estado);
        this.cnpj = cnpj;
    }
    
    //Getter e setter
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    //Método exibir que sobrescreve exibir na classe Pessoa
    @Override
    public void exibir() {
        System.out.println("DADOS DA PESSOA JURIDICA");
        super.exibir();
        System.out.println("CNPJ: " + cnpj);
        System.out.println("-------------------------");
    }
}
