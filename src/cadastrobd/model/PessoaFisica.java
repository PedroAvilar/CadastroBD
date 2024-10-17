package cadastrobd.model;

public class PessoaFisica extends Pessoa {
    //Campo
    private String cpf;
    
    //Construtor padrão
    public PessoaFisica() {
        super();
    }
    
    //Construtor completo
    public PessoaFisica(int idPessoa, String nomePessoa, String email, String telefone,
            String logradouro, String cidade, String estado, String cpf) {
        super(idPessoa, nomePessoa, email, telefone, logradouro, cidade, estado);
        this.cpf = cpf;
    }
    
    //Getter e setter
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    //Método exibir que sobrescreve exibir na classe Pessoa
    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CPF: " + cpf);
    }
}
