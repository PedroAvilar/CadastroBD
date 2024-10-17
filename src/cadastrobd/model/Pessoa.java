package cadastrobd.model;

//Classe Pessoa
public class Pessoa {
    //Campos
    private int idPessoa;
    private String nomePessoa;
    private String email;
    private String telefone;
    private String logradouro;
    private String cidade;
    private String estado;
    
    //Construtor padrão
    public Pessoa() { }
    
    //Construtor completo
    public Pessoa (int idPessoa, String nomePessoa, String email, String telefone,
            String logradouro, String cidade, String estado) {
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
        this.email = email;
        this.telefone = telefone;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
    }
    
    //Getters e setters
    public int getIdPessoa() {
        return idPessoa;
    }
    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }
    public String getNomePessoa() {
        return nomePessoa;
    }
    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    //Método para exibir os dados no console
    public void exibir() {
        System.out.println("ID Pessoa: " + idPessoa);
        System.out.println("Nome: " + nomePessoa);
        System.out.println("E-mail: " + email);
        System.out.println("Telefone: " + telefone);
        System.out.println("Logradouro: " + logradouro);
        System.out.println("Cidade: " + cidade);
        System.out.println("Estado: " + estado);
    }
}
