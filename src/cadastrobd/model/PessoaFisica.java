package cadastrobd.model;

public class PessoaFisica extends Pessoa {
    //Campo
    private String CPF;
    
    //Construtor padrão
    public PessoaFisica() {
        super();
    }
    
    //Construtor completo
    public PessoaFisica(int IDPessoa, String NomePessoa, String Email, String Telefone,
            String Logradouro, String Cidade, String Estado, String CPF) {
        super(IDPessoa, NomePessoa, Email, Telefone, Logradouro, Cidade, Estado);
        this.CPF = CPF;
    }
    
    //Getter e setter
    public String getCPF() {
        return CPF;
    }
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
    
    //Método exibir que sobrescreve exibir na classe Pessoa
    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CPF: " + CPF);
        System.out.println("______________________________________");
    }
}
