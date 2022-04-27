import javax.swing.*;
import java.util.ArrayList;

public class Materia {
    public static int quantidade = 0;
    private int id;

    private String nome, sigla, area;

    private Universidade universidade;

    public Materia(String nome, String sigla, String area, Universidade universidade) {
        this.id = (quantidade + 1);
        this.nome = nome;
        this.sigla = sigla;
        this.area = area;
        this.universidade = universidade;
        quantidade++;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Universidade getUniversidade() {
        return universidade;
    }

    public void setUniversidade(Universidade universidade) {
        this.universidade = universidade;
    }

    public static int getQuantidade() {
        return quantidade;
    }

    public static void setQuantidade(int quantidade) {
        Materia.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void inclusao(ArrayList<Materia> lista, Materia newCr) {
        if (Materia.consulta(lista, newCr.getId(), false) == -1) {
            lista.add(newCr);
        } else {
            JOptionPane.showMessageDialog(null, "não foi possível incluir - id já existe");
        }
    }

    public static void exclusao(ArrayList<Materia> lista, int crId) {
        if (Materia.consulta(lista, crId, false) != -1) {
            lista.remove(Materia.consulta(lista, crId, false));
        } else {
            JOptionPane.showMessageDialog(null, "não foi possível excluir - id não encontrado");
        }
    }

    public static void alteracao(ArrayList<Materia> lista, Materia altCr) {
        if (Materia.consulta(lista, altCr.getId(), false) != -1) {
            exclusao(lista, altCr.getId());
            inclusao(lista, altCr);
        } else {
            JOptionPane.showMessageDialog(null, "não foi possível alterar - id não encontrado");
        }
    }

    public static int consulta(ArrayList<Materia> lista, int id, boolean showP) {
        // retorna -1 se nao encontrar o id, se encontrar retorna o indice do objeto no arraylist
        int res = -1;
        if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                Materia cr = lista.get(i);
                if (cr.getId() == id && showP == true) {
                    UsefullPanels.showLongTextMessageInDialog(cr.toString());
                    res = i;
                }

                if (cr.getId() == id && showP == false) {
                    res = i;
                }
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return "Id matéria: " + id + "\nNome matéria: " + nome
                + "\nSigla matéria: " + sigla + "\nArea matéria: " + area
                + "\nUniversidade Id: " + universidade.getId() + "\n";
    }
}
