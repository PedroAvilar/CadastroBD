package cadastrobd.model;

//Classe Pessoa
public class Pessoa {
    //Campos
    private int IDPessoa;
    private String NomePessoa;
    private String Email;
    private String Telefone;
    private String Logradouro;
    private String Cidade;
    private String Estado;
    
    //Construtor padrão
    public Pessoa() { }
    
    //Construtor completo
    public Pessoa (int IDPessoa, String NomePessoa, String Email, String Telefone,
            String Logradouro, String Cidade, String Estado) {
        this.IDPessoa = IDPessoa;
        this.NomePessoa = NomePessoa;
        this.Email = Email;
        this.Telefone = Telefone;
        this.Logradouro = Logradouro;
        this.Cidade = Cidade;
        this.Estado = Estado;
    }
    
    //Getters e setters
    public int getIDPessoa() {
        return IDPessoa;
    }
    public void setIDPessoa(int IDPessoa) {
        this.IDPessoa = IDPessoa;
    }
    public String getNomePessoa() {
        return NomePessoa;
    }
    public void setNomePessoa(String NomePessoa) {
        this.NomePessoa = NomePessoa;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getTelefone() {
        return Telefone;
    }
    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }
    public String getLogradouro() {
        return Logradouro;
    }
    public void setLogradouro(String Logradouro) {
        this.Logradouro = Logradouro;
    }
    public String getCidade() {
        return Cidade;
    }
    public void setCidade(String Cidade) {
        this.Cidade = Cidade;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String Estado) {
        this.Estado = Estado;
    }
    
    //Método para exibir os dados no console
    public void exibir() {
        System.out.println("ID Pessoa: " + IDPessoa);
        System.out.println("Nome: " + NomePessoa);
        System.out.println("E-mail: " + Email);
        System.out.println("Telefone: " + Telefone);
        System.out.println("Logradouro: " + Logradouro);
        System.out.println("Cidade: " + Cidade);
        System.out.println("Estado: " + Estado);
    }
}
