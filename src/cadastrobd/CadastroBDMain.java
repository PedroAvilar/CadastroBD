package cadastrobd;
import cadastro.model.PessoaFisicaDAO;
import cadastro.model.PessoaJuridicaDAO;
import java.util.InputMismatchException;
import java.util.Scanner;
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import java.sql.SQLException;

public class CadastroBDMain {
    private static Scanner scanner = new Scanner(System.in);
    
    //Método para tratamento de exceção de leitura de números
    private static int entradaInt(String mensagem) {
        int valor = -1;
        while (valor == -1) {
            System.out.print(mensagem);
            try {
                valor = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Insira um numero inteiro.");
                scanner.nextLine();
            }
        }
        return valor;
    }
    
    //Método auxiliar para exibir mensagens e receber dados do usuário
    private static String entradaString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
    
    public static void main(String[] args) {
        //Instâncias dos objetos DAO
        PessoaFisicaDAO pfDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pjDAO = new PessoaJuridicaDAO();
        
        //Laço que mantém o programa em execução
        while (true) {
            System.out.println("======================================");
            System.out.println("MENU");
            System.out.println("1 - Incluir Pessoa");
            System.out.println("2 - Alterar Pessoa");
            System.out.println("3 - Excluir Pessoa");
            System.out.println("4 - Buscar pelo ID");
            System.out.println("5 - Exibir Todos");
            System.out.println("0 - Finalizar Programa");
            System.out.println("======================================");
            int opcao = entradaInt("Digite o numero da opcao: ");
         
            switch (opcao) {
                //Incluir
                case 1: {
                    System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
                    String tipoIncluir = entradaString("Tipo de pessoa: ");
                    if (tipoIncluir.equalsIgnoreCase("F")) {  //Pessoa Física
                        System.out.println("Insira os dados da pessoa fisica.\n");
                        //Capturando e inserindo pessoa física
                        int id = 0;  //ID será gerado pelo SequenceManager
                        String nome = entradaString("Nome: ");
                        String email = entradaString("E-mail: ");
                        String telefone = entradaString("Telefone: ");
                        String logradouro = entradaString("Logradouro: ");
                        String cidade = entradaString("Cidade: ");
                        String estado = entradaString("Estado: ");
                        String cpf = entradaString("CPF: ");
                        try {
                            PessoaFisica pf = new PessoaFisica(id, nome, email, 
                                    telefone, logradouro, cidade, estado, cpf);
                            pfDAO.incluir(pf);
                            System.out.println("Pessoa fisica incluida com sucesso.\n");
                        } catch (SQLException e) {
                            System.out.println("Erro ao incluir pf pela classe principal: " + e.getMessage());
                        }
                    } else if (tipoIncluir.equalsIgnoreCase("J")) {  //Pessoa Jurídica
                        System.out.println("Insira os dados da pessoa juridica.\n");
                        //Capturando e inserindo pessoa jurídica
                        int id = 0;  //ID será gerado pelo SequenceManager
                        String nome = entradaString("Nome: ");
                        String email = entradaString("E-mail: ");
                        String telefone = entradaString("Telefone: ");
                        String logradouro = entradaString("Logradouro: ");
                        String cidade = entradaString("Cidade: ");
                        String estado = entradaString("Estado: ");
                        String cnpj = entradaString("CNPJ: ");
                        try {
                            PessoaJuridica pj = new PessoaJuridica(id, nome, email, 
                                    telefone, logradouro, cidade, estado, cnpj);
                            pjDAO.incluir(pj);
                            System.out.println("Pessoa juridica incluida com sucesso.\n");
                        } catch (SQLException e) {
                            System.out.println("Erro ao incluir pj pela classe principal: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Opcao invalida.");
                    }
                } break;
                
                //Alterar
                case 2: {
                    System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
                    String tipoAlterar = entradaString("Tipo de pessoa: ");
                    if (tipoAlterar.equalsIgnoreCase("F")) {  //Pessoa Física
                        try {
                            int id = entradaInt("Digite o ID da pessoa fisica para alterar: ");
                            PessoaFisica pf = pfDAO.getPessoa(id);
                            //Exibe os dados atuais e exige os novos
                            if (pf != null) {
                                System.out.println("DADOS ATUAIS: ");
                                pf.exibir();
                                System.out.println("Insira os novos dados.");
                                pf.setNomePessoa(entradaString("Novo nome: "));
                                pf.setEmail(entradaString("Novo e-mail: "));
                                pf.setTelefone(entradaString("Novo telefone: "));
                                pf.setLogradouro(entradaString("Novo logradouro: "));
                                pf.setCidade(entradaString("Nova cidade: "));
                                pf.setEstado(entradaString("Novo estado: "));
                                pf.setCPF(entradaString("Novo CPF: "));
                                pfDAO.alterar(pf);
                                System.out.println("Pessoa fisica alterada com sucesso.\n");
                            } else {
                                System.out.println("Nao foi encontrada uma pessoa fisica com o ID " + id);
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao alterar pf na classe principal: " + e.getMessage());
                        }
                    } else if (tipoAlterar.equalsIgnoreCase("J")) {  //Pessoa Jurídica
                        try {
                            int id = entradaInt("Digite o ID da pessoa juridica para alterar: ");
                            PessoaJuridica pj = pjDAO.getPessoa(id);
                            //Exibe os dados atuais e exige os novos
                            if (pj != null) {
                                System.out.println("Dados atuais: ");
                                pj.exibir();
                                System.out.println("Insira os novos dados.\n");
                                pj.setNomePessoa(entradaString("Novo nome: "));
                                pj.setEmail(entradaString("Novo e-mail: "));
                                pj.setTelefone(entradaString("Novo telefone: "));
                                pj.setLogradouro(entradaString("Novo logradouro: "));
                                pj.setCidade(entradaString("Nova cidade: "));
                                pj.setEstado(entradaString("Novo estado: "));
                                pj.setCNPJ(entradaString("Novo CNPJ: "));
                                pjDAO.alterar(pj);
                                System.out.println("Pessoa juridica alterada com sucesso.\n");
                            } else {
                                System.out.println("Nao foi encontrada uma pessoa juridica com o ID " + id);
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao alterar pj na classe principal: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Opcao invalida.");
                    }
                } break;
                
                //Excluir
                case 3: {
                    System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
                    String tipoExcluir = entradaString("Tipo de pessoa: ");
                    if (tipoExcluir.equalsIgnoreCase("F")) {  //Pessoa Física
                        try {
                            int id = entradaInt("Digite o ID da pessoa fisica para excluir: ");
                            PessoaFisica pf = pfDAO.getPessoa(id);
                            if (pf != null) {
                                pfDAO.excluir(id);
                                System.out.println("Pessoa fisica excluida com sucesso.\n");
                            } else {
                                System.out.println("ID não encontrado.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao excluir pf na classe principal: " + e.getMessage());
                        }
                    } else if (tipoExcluir.equalsIgnoreCase("J")) {  //Pessoa Jurídica
                        try {
                            int id = entradaInt("Digite o ID da pessoa juridica para excluir: ");
                            PessoaJuridica pj  = pjDAO.getPessoa(id);
                            if (pj != null) {
                                pjDAO.excluir(id);
                                System.out.println("Pessoa juridica excluida com sucesso.\n");
                            } else {
                                System.out.println("ID não encontrado.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao excluir pj na classe principal: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Opcao invalida.");
                    }
                } break;
                
                //Buscar
                case 4: {
                    System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
                    String tipoBuscar = entradaString("Tipo de pessoa: ");
                    if (tipoBuscar.equalsIgnoreCase("F")) {  //Pessoa Física
                        try {
                            int id = entradaInt("Digite o ID da pessoa fisica para exibir: ");
                            PessoaFisica pf = pfDAO.getPessoa(id);
                            if (pf != null) {
                                System.out.println("DADOS DA PESSOA FISICA\n");
                                pf.exibir();
                            } else {
                                System.out.println("Pessoa fisica nao encontrada.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao buscar pf na classe principal: " + e.getMessage());
                        }
                    } else if (tipoBuscar.equalsIgnoreCase("J")) {  //Pessoa Jurídica
                        try {
                            int id = entradaInt("Digite o ID da pessoa juridica para exibir: ");
                            PessoaJuridica pj = pjDAO.getPessoa(id);
                            if (pj != null) {
                                System.out.println("DADOS DA PESSOA JURIDICA\n");
                                pj.exibir();
                            } else {
                                System.out.println("Pessoa juridica nao encontrada.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao buscar pj na classe principal: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Opcao invalida.");
                    }
                } break;
                
                //Exibir todos
                case 5: {
                    System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
                    String tipoExibirTodos = entradaString("Tipo de pessoa: ");
                    if (tipoExibirTodos.equalsIgnoreCase("F")) {  //Pessoa Física
                        try {
                            System.out.println("DADOS DAS PESSOAS FISICAS\n");
                            pfDAO.getPessoas().forEach(pf -> pf.exibir());
                        } catch (SQLException e) {
                            System.out.println("Erro ao exibir todas pf na classe principal: " + e.getMessage());
                        }
                    } else if (tipoExibirTodos.equalsIgnoreCase("J")) {  //Pessoa Jurídica
                        try {
                            System.out.println("DADOS DAS PESSOAS JURIDICAS\n");
                            pjDAO.getPessoas().forEach(pj -> pj.exibir());
                        } catch (SQLException e) {
                            System.out.println("Erro ao exibir todas pj na classe principal: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Opcao invalida.");
                    }
                } break;
                
                //Finalizar o programa
                case 0: {
                    System.out.println("Programa finalizado.");
                    scanner.close();
                    System.exit(0);
                } break;
                
                default: {
                    System.out.println("Opcao invalida. Tente novamente.");
                }
            }
        }
    }
}
