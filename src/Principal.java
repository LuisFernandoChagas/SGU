import javax.swing.*;
import java.util.ArrayList;

public class Principal {
    public static void main(String[] args) {
        ArrayList<Universidade> listaUniversidades = new ArrayList<>();
        ArrayList<Materia> listaCursos = new ArrayList<>();
        ArrayList<Aluno> listaAlunos = new ArrayList<>();

        ListDataFiles.inicializarDados(listaUniversidades, listaCursos, listaAlunos);

        String baseMenu = "---- Gestão Acadêmica ----\n" +
                "1 - inclusao  (universidades | matéria | alunos)\n" +
                "2 - exclusao  (universidades | matéria | alunos)\n" +
                "3 - alteracao (universidades | matéria | alunos)\n" +
                "4 - consulta  (universidades | matéria | alunos)\n" +
                "5 - listagem  (universidades | matéria | alunos)\n" +
                "0 - sair";

        try {
            int op = Integer.parseInt(JOptionPane.showInputDialog(null, baseMenu));
            while (op != 0) {
                if (op == 1) {
                    String subMenu = "---- Gestão Acadêmica - Cadastro ----\n" +
                            "1 - cadastro de universidades\n2 - cadastro de matéria\n3 - cadastro de alunos\n0 - voltar";

                    int subOp = Integer.parseInt(JOptionPane.showInputDialog(null, subMenu));

                    while (subOp != 0) {
                        if (subOp == 1) {
                            String auxText = "---- Cadastro de Universidades - Localização ----\n";
                            String auxText2 = "---- Cadastro de Universidades - Dados ----\n";

                            String auxEndec = JOptionPane.showInputDialog(null, auxText + "Digite a rua: ");
                            String auxBairro = JOptionPane.showInputDialog(null, auxText + "Digite o bairro/setor: ");
                            String auxCidade = JOptionPane.showInputDialog(null, auxText + "Digite a cidade: ");
                            String auxEstado = JOptionPane.showInputDialog(null, auxText + "Digite o estado: ");

                            if (auxEndec != null && auxBairro != null && auxCidade != null && auxEstado != null) {
                                Localizacao loc = new Localizacao(auxEndec, auxBairro, auxCidade, auxEstado);

                                String auxNomeUniv = JOptionPane.showInputDialog(null,
                                        auxText2 + "Digite o nome completo da Universidade: ");
                                String auxSiglaUniv = JOptionPane.showInputDialog(null,
                                        auxText2 + "Digite a sigla da Universidade: ");

                                if (auxNomeUniv != null && auxSiglaUniv != null) {
                                    Universidade univ = new Universidade(auxNomeUniv, auxSiglaUniv, loc);

                                    Universidade.inclusao(listaUniversidades, univ);
                                } else {
                                    JOptionPane.showMessageDialog(null, "dados digitados da universidade inválidos");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "dados digitados de localizacao inválidos");
                            }
                        } else if (subOp == 2 && listaUniversidades.size() > 0) {
                            String auxText = "---- Cadastro de Matéria ----\n";

                            String auxNome = JOptionPane.showInputDialog(null, auxText + "Digite o nome completo da matéria: ");
                            String auxSigla = JOptionPane.showInputDialog(null, auxText + "Digite a sigla da matéria: ");
                            String auxArea = JOptionPane.showInputDialog(null, auxText + "Digite a área da matéria: ");

                            if (auxNome != null && auxSigla != null && auxArea != null) {
                                int auxOpcao = Integer.parseInt(UsefullPanels.showAndGetLongTextMessageInDialog(
                                        ListDataFiles.mostrarLista(listaUniversidades, 1) + "\nDigite o Id da universidade escolhida: "));

                                int selecId = Universidade.consulta(listaUniversidades, auxOpcao, false);
                                if (selecId != -1) {
                                    Universidade unEsc = listaUniversidades.get(selecId);
                                    Materia cr = new Materia(auxNome, auxSigla, auxArea, unEsc);

                                    Materia.inclusao(listaCursos, cr);
                                } else {
                                    JOptionPane.showMessageDialog(null, "id escolhido inválido");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "dados digitados de curso inválidos");
                            }
                        } else if (subOp == 3 && listaUniversidades.size() > 0 && listaCursos.size() > 0) {
                            String auxText = "---- Cadastro de Alunos ----\n";

                            String auxNome = JOptionPane.showInputDialog(null, auxText + "Digite o nome do aluno: ");
                            String auxDtNasc = JOptionPane.showInputDialog(null, auxText + "Digite a data de nascimento do aluno (dd/mm/aaaa): ");
                            int auxAnoIngresso = Integer.parseInt(JOptionPane.showInputDialog(null, auxText + "Digite o ano de ingresso do aluno: "));
                            int auxSituacao = Integer.parseInt(JOptionPane.showInputDialog(null, auxText
                                    + "Digite a situacao do aluno (1 - ativo, 2 - inativo): "));

                            if (auxNome != null && ListDataFiles.validateJavaDate(auxDtNasc) && auxAnoIngresso > 1900 && (auxSituacao == 1 || auxSituacao == 2)) {
                                int auxOpcaoUni = Integer.parseInt(UsefullPanels.showAndGetLongTextMessageInDialog(
                                        ListDataFiles.mostrarLista(listaUniversidades, 1) + "\nDigite o Id da universidade escolhida: "));

                                String res = "Universidade(s) ----\n";
                                for (int i = 0; i < listaCursos.size(); i++) {
                                    Materia cr = (Materia) listaCursos.get(i);
                                    if (cr.getUniversidade().getId() == auxOpcaoUni) {
                                        res += cr.toString() + "\n";
                                    }
                                }

                                int auxOpcaoCurso = Integer.parseInt(UsefullPanels.showAndGetLongTextMessageInDialog(res + "\nDigite o Id do curso escolhido: "));

                                int selecUniId = Universidade.consulta(listaUniversidades, auxOpcaoUni, false);
                                int selecCursoId = Materia.consulta(listaCursos, auxOpcaoCurso, false);

                                if (selecUniId != -1 && selecCursoId != -1) {
                                    Universidade unEsc = listaUniversidades.get(selecUniId);
                                    Materia crEsc = listaCursos.get(selecCursoId);
                                    Aluno al = new Aluno(auxNome, auxDtNasc, auxAnoIngresso, auxSituacao, unEsc, crEsc);

                                    Aluno.inclusao(listaAlunos, al);
                                } else {
                                    JOptionPane.showMessageDialog(null, "id da matéria ou universidade escolhidos e inválido");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "dados digitados do aluno inválidos");
                            }
                        } else if (subOp == 0) {
                            break;
                        }
                        ListDataFiles.salvarDadosNoArquivo(listaUniversidades, listaCursos, listaAlunos);
                        subOp = Integer.parseInt(JOptionPane.showInputDialog(null, subMenu));
                    }
                    ListDataFiles.salvarDadosNoArquivo(listaUniversidades, listaCursos, listaAlunos);
                } else if (op == 2) {
                    String subMenu = "---- Gestao Acadêmica - Exclusão ----\n" +
                            "1 - excluir universidade\n2 - excluir matéria\n3 - excluir aluno\n0 - voltar";
                    int subOp = Integer.parseInt(JOptionPane.showInputDialog(null, subMenu));

                    while (subOp != 0) {
                        if (subOp == 1) {
                            String auxText = "---- Exclusão de Universidade ----\n";

                            int auxId = Integer.parseInt(JOptionPane.showInputDialog(null, auxText + "Digite somente o id da universidade: "));
                            Universidade.exclusao(listaUniversidades, auxId);

                            for (int i = 0; i < listaCursos.size(); i++) {
                                Materia auxCr = listaCursos.get(i);
                                if (auxCr.getUniversidade().getId() == auxId) {
                                    Materia.exclusao(listaCursos, auxCr.getId());
                                }
                            }

                            for (int i = 0; i < listaAlunos.size(); i++) {
                                Aluno auxAl = listaAlunos.get(i);
                                if (auxAl.getUniversidade().getId() == auxId) {
                                    Aluno.exclusao(listaAlunos, auxAl.getMatricula());
                                }
                            }
                        } else if (subOp == 2) {
                            String auxText = "---- Exclusão de Matéria ----\n";

                            int auxId = Integer.parseInt(JOptionPane.showInputDialog(null, auxText + "Digite somente o id do matéria: "));
                            Materia.exclusao(listaCursos, auxId);

                            for (int i = 0; i < listaAlunos.size(); i++) {
                                Aluno auxAl = listaAlunos.get(i);
                                if (auxAl.getCurso().getId() == auxId) {
                                    Aluno.exclusao(listaAlunos, auxAl.getMatricula());
                                }
                            }
                        } else if (subOp == 3) {
                            String auxText = "---- Exclusão de Aluno ----\n";

                            String auxMatric = JOptionPane.showInputDialog(null, auxText + "Digite somente a matricula do aluno: ");
                            Aluno.exclusao(listaAlunos, auxMatric);
                        } else if (subOp == 0) {
                            break;
                        }
                        ListDataFiles.salvarDadosNoArquivo(listaUniversidades, listaCursos, listaAlunos);
                        subOp = Integer.parseInt(JOptionPane.showInputDialog(null, subMenu));
                    }
                    ListDataFiles.salvarDadosNoArquivo(listaUniversidades, listaCursos, listaAlunos);
                } else if (op == 3) {
                    String subMenu = "---- Gestao Acadêmica - Alteração ----\n" +
                            "1 - alterar dados de universidade\n2 - alterar dados de matéria\n3 - alterar dados de aluno\n0 - voltar";
                    int subOp = Integer.parseInt(JOptionPane.showInputDialog(null, subMenu));

                    while (subOp != 0) {
                        if (subOp == 1) {
                            String auxText = "---- Alteração de Universidade - Localizacao ----\n";
                            String auxText2 = "---- Alteração de Universidade - Dados ----\n";

                            int auxIdUni = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o id da universidade: "));
                            if (Universidade.consulta(listaUniversidades, auxIdUni, false) != -1) {
                                String auxEndec = JOptionPane.showInputDialog(null, auxText + "Digite a rua: ");
                                String auxBairro = JOptionPane.showInputDialog(null, auxText + "Digite o bairro/setor: ");
                                String auxCidade = JOptionPane.showInputDialog(null, auxText + "Digite a cidade: ");
                                String auxEstado = JOptionPane.showInputDialog(null, auxText + "Digite o estado: ");

                                if (auxEndec != "" && auxBairro != "" && auxCidade != "" && auxEstado != "") {
                                    Localizacao loc = new Localizacao(auxEndec, auxBairro, auxCidade, auxEstado);

                                    String auxNomeUniv = JOptionPane.showInputDialog(null,
                                            auxText2 + "Digite o nome da Universidade: ");
                                    String auxSiglaUniv = JOptionPane.showInputDialog(null,
                                            auxText2 + "Digite a sigla da Universidade: ");

                                    if (auxNomeUniv != "" && auxSiglaUniv != "") {
                                        Universidade univ = new Universidade(auxNomeUniv, auxSiglaUniv, loc);
                                        univ.setId(auxIdUni);

                                        Universidade.alteracao(listaUniversidades, univ);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "dados digitados da universidade inválidos");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "dados digitados de localizacao inválidos");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "id da universidade não foi encontrado");
                            }
                        } else if (subOp == 2) {
                            String auxText = "---- Alteração de Matéria - Dados ----\n";

                            int auxIdCr = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o id da matéria: "));
                            if (Materia.consulta(listaCursos, auxIdCr, false) != -1) {
                                String auxNome = JOptionPane.showInputDialog(null, auxText + "Digite o nome completo da matéria: ");
                                String auxSigla = JOptionPane.showInputDialog(null, auxText + "Digite a sigla da matéria: ");
                                String auxArea = JOptionPane.showInputDialog(null, auxText + "Digite a area da matéria: ");

                                if (auxNome != "" && auxSigla != "" && auxArea != "") {
                                    Materia auxCr = listaCursos.get(Materia.consulta(listaCursos, auxIdCr, false));
                                    Universidade unEsc = listaUniversidades.get(auxCr.getUniversidade().getId());
                                    Materia cr = new Materia(auxNome, auxSigla, auxArea, unEsc);
                                    cr.setId(auxIdCr);

                                    Materia.alteracao(listaCursos, cr);
                                } else {
                                    JOptionPane.showMessageDialog(null, "dados digitados de matéria inválidos");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "id do matéria não foi encontrado");
                            }
                        } else if (subOp == 3) {
                            String auxText = "---- Alteração de Alunos - Dados ----\n";

                            String auxAlMatr = (JOptionPane.showInputDialog(null, "Digite somente a matrícula do aluno: "));
                            if (Aluno.consulta(listaAlunos, auxAlMatr, false) != -1) {
                                String auxNome = JOptionPane.showInputDialog(null, auxText + "Digite o nome do aluno: ");
                                String auxDtNasc = JOptionPane.showInputDialog(null, auxText + "Digite a data de nascimento do aluno (dd/mm/aaaa): ");
                                int auxAnoIngresso = Integer.parseInt(JOptionPane.showInputDialog(null, auxText + "Digite o ano de ingresso do aluno: "));
                                int auxSituacao = Integer.parseInt(JOptionPane.showInputDialog(null, auxText
                                        + "Digite a situacao do aluno (1 - ativo, 2 - inativo): "));

                                if (auxNome != "" && ListDataFiles.validateJavaDate(auxDtNasc) && auxAnoIngresso > 1900 && (auxSituacao == 1 || auxSituacao == 2)) {
                                    Aluno auxAl = listaAlunos.get(Aluno.consulta(listaAlunos, auxAlMatr, false));
                                    int selecUniId = Universidade.consulta(listaUniversidades, auxAl.getUniversidade().getId(), false);
                                    int selecCursoId = Materia.consulta(listaCursos, auxAl.getCurso().getId(), false);

                                    Universidade unEsc = listaUniversidades.get(selecUniId);
                                    Materia crEsc = listaCursos.get(selecCursoId);

                                    Aluno al = new Aluno(auxNome, auxDtNasc, auxAnoIngresso, auxSituacao);

                                    al.setUniversidade(unEsc);
                                    al.setCurso(crEsc);
                                    al.setMatricula(auxAlMatr);

                                    Aluno.alteracao(listaAlunos, al);
                                } else {
                                    JOptionPane.showMessageDialog(null, "dados digitados do aluno inválidos");
                                }
                            }
                        } else if (subOp == 0) {
                            break;
                        }
                        ListDataFiles.salvarDadosNoArquivo(listaUniversidades, listaCursos, listaAlunos);
                        subOp = Integer.parseInt(JOptionPane.showInputDialog(null, subMenu));
                    }
                    ListDataFiles.salvarDadosNoArquivo(listaUniversidades, listaCursos, listaAlunos);
                } else if (op == 4) {
                    String subMenu = "---- Gestao Acadêmica - Consulta ----\n" +
                            "1 - consultar universidade\n2 - consultar matérias\n3 - consultar alunos\n0 - voltar";
                    int subOp = Integer.parseInt(JOptionPane.showInputDialog(null, subMenu));

                    while (subOp != 0) {
                        if (subOp == 1) {
                            int auxSelecUni = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o Id da universidade: "));

                            Universidade.consulta(listaUniversidades, auxSelecUni, true);
                        } else if (subOp == 2) {
                            int auxSelecCurso = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o Id da matéria: "));

                            Materia.consulta(listaCursos, auxSelecCurso, true);
                        } else if (subOp == 3) {
                            String auxSelecAl = JOptionPane.showInputDialog(null, "Digite a matrícula do aluno: ");

                            Aluno.consulta(listaAlunos, auxSelecAl, true);
                        } else if (subOp == 0) {
                            break;
                        }
                        subOp = Integer.parseInt(JOptionPane.showInputDialog(null, subMenu));
                    }
                } else if (op == 5) {
                    String subMenu = "---- Gestao Acadêmica - Listagem ----\n" +
                            "1 - listar todas universidades\n2 - listar todas as matérias\n3 - listar todos alunos\n0 - voltar";
                    int subOp = Integer.parseInt(JOptionPane.showInputDialog(null, subMenu));

                    while (subOp != 0) {
                        if (subOp == 1) {
                            UsefullPanels.showLongTextMessageInDialog(ListDataFiles.mostrarLista(listaUniversidades, 1));
                        } else if (subOp == 2) {
                            UsefullPanels.showLongTextMessageInDialog(ListDataFiles.mostrarLista(listaCursos, 2));
                        } else if (subOp == 3) {
                            UsefullPanels.showLongTextMessageInDialog(ListDataFiles.mostrarLista(listaAlunos, 3));
                        } else if (subOp == 0) {
                            break;
                        }

                        subOp = Integer.parseInt(JOptionPane.showInputDialog(null, subMenu));
                    }
                } else if (op == 0) {
                    System.exit(1);
                }

                op = Integer.parseInt(JOptionPane.showInputDialog(null, baseMenu));
            }
        } catch (Exception e) {
            throw new EntradaFalhaException("Erro ao ler a entrada do usuário.");
        }
    }
}
