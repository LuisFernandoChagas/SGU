import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Aluno extends Pessoa {
    public static int quantidade = 0;
    private String matricula;

    // situacao: 1 - ativo, 2 - inativo;

    private int anoIngresso, situacao;

    private Universidade universidade;
    private Materia curso;

    public Aluno(String nome, String dataNascimento, int anoIngresso, int situacao) {
        super(nome, dataNascimento);
        this.anoIngresso = anoIngresso;
        this.situacao = situacao;
    }

    public Aluno(String nome, String dataNascimento, int anoIngresso, int situacao, Universidade universidade, Materia curso) {
        super(nome, dataNascimento);
        this.matricula = gerarMatricula();
        this.anoIngresso = anoIngresso;
        this.situacao = situacao;
        this.universidade = universidade;
        this.curso = curso;
        quantidade++;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getAnoIngresso() {
        return anoIngresso;
    }

    public void setAnoIngresso(int anoIngresso) {
        this.anoIngresso = anoIngresso;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public Universidade getUniversidade() {
        return universidade;
    }

    public void setUniversidade(Universidade universidade) {
        this.universidade = universidade;
    }

    public Materia getCurso() {
        return curso;
    }

    public void setCurso(Materia curso) {
        this.curso = curso;
    }

    protected String gerarMatricula() {
        Calendar cal = Calendar.getInstance();

        // nº de matricula formado por: ano + (mes + 10) + (quantidade + 1) + (dia do mes + 10);
        // e.g. 2021 + (10 + 5) + (3 + 1) + (22 + 10) = 202115432
        return cal.get(Calendar.YEAR) + "" + cal.get(Calendar.MONTH + 10) + "" + (quantidade + 1)
                + "" + (cal.get(Calendar.DAY_OF_MONTH) + 10);
    }

    public static void inclusao(ArrayList<Aluno> lista, Aluno newAl) {
        if (Aluno.consulta(lista, newAl.getMatricula(), false) == -1) {
            lista.add(newAl);
        } else {
            JOptionPane.showMessageDialog(null, "não foi possível incluir - matrícula já existe");
        }
    }

    public static void exclusao(ArrayList<Aluno> lista, String alMat) {
        if (Aluno.consulta(lista, alMat, false) != -1) {
            lista.remove(Aluno.consulta(lista, alMat, false));
        } else {
            JOptionPane.showMessageDialog(null, "não foi possível excluir - matrícula não encontrada");
        }
    }

    public static void alteracao(ArrayList<Aluno> lista, Aluno altAl) {
        if (Aluno.consulta(lista, altAl.getMatricula(), false) != -1) {
            exclusao(lista, altAl.getMatricula());
            inclusao(lista, altAl);
        } else {
            JOptionPane.showMessageDialog(null, "não foi possível alterar - matrícula não encontrada");
        }
    }

    public static int consulta(ArrayList<Aluno> lista, String matricula, boolean showP) {
        // retorna -1 se nao encontrar o id, se encontrar retorna o indice do objeto no arraylist
        int res = -1;
        if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                Aluno al = lista.get(i);
                if (al.getMatricula().equals(matricula) && showP == true) {
                    UsefullPanels.showLongTextMessageInDialog(al.toString());
                    res = i;
                }

                if (al.getMatricula().equals(matricula) && showP == false) {
                    res = i;
                }
            }
        }
        return res;
    }

    @Override
    public String toString() {
        String situ = "";

        situ = situacao == 1 ? "ativo" : "inativo";

        return "Matricula: " + matricula + "\nNome: " + this.getNome()
                + "\nAno de ingresso: " + anoIngresso
                + "\nData de nascimento: " + this.getDataNascimento()
                + "\nSituacao: " + situ
                + "\nUniversidade Id: " + universidade.getId()
                + "\nCurso Id: " + curso.getId() + "\n";
    }
}
