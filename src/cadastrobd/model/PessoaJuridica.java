package cadastrobd.model;

public class PessoaJuridica extends Pessoa {
    //Campo
    private String CNPJ;
    
    //Construtor padrão
    public PessoaJuridica() {
        super();
    }
    
    //Construtor completo
    public PessoaJuridica(int IDPessoa, String NomePessoa, String Email, String Telefone,
            String Logradouro, String Cidade, String Estado, String CNPJ) {
        super(IDPessoa, NomePessoa, Email, Telefone, Logradouro, Cidade, Estado);
        this.CNPJ = CNPJ;
    }
    
    //Getter e setter
    public String getCNPJ() {
        return CNPJ;
    }
    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }
    
    //Método exibir que sobrescreve exibir na classe Pessoa
    @Override
    public void exibir() {
        System.out.println("DADOS DA PESSOA JURIDICA");
        super.exibir();
        System.out.println("CNPJ: " + CNPJ);
        System.out.println("______________________________________");
    }
}
