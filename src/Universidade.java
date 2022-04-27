import javax.swing.*;
import java.util.ArrayList;

public class Universidade {
    public static int quantidade = 0;
    private int id;

    private String nome, sigla;

    private Localizacao localizacao;

    public Universidade(String nome, String sigla, Localizacao localizacao) {
        this.id = (quantidade + 1);
        this.nome = nome;
        this.sigla = sigla;
        this.localizacao = localizacao;
        quantidade++;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public static int getQuantidade() {
        return quantidade;
    }

    public static void setQuantidade(int quantidade) {
        Universidade.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void inclusao(ArrayList<Universidade> lista, Universidade newUn) {
        if (Universidade.consulta(lista, newUn.getId(), false) == -1) {
            lista.add(newUn);
        } else {
            JOptionPane.showMessageDialog(null, "não foi possível incluir - id já existe");
        }
    }

    public static void exclusao(ArrayList<Universidade> lista, int unId) {
        if (Universidade.consulta(lista, unId, false) != -1) {
            lista.remove(Universidade.consulta(lista, unId, false));
        } else {
            JOptionPane.showMessageDialog(null, "não foi possível excluir - id não encontrado");
        }
    }

    public static void alteracao(ArrayList<Universidade> lista, Universidade altUn) {
        if (Universidade.consulta(lista, altUn.getId(), false) != -1) {
            exclusao(lista, altUn.getId());
            inclusao(lista, altUn);
        } else {
            JOptionPane.showMessageDialog(null, "não foi possível alterar - id não encontrado");
        }
    }

    public static int consulta(ArrayList<Universidade> lista, int id, boolean showP) {
        // retorna -1 se nao encontrar o id, se encontrar retorna o indice do objeto no arraylist
        int res = -1;
        if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                Universidade un = lista.get(i);
                if (un.getId() == id && showP == true) {
                    UsefullPanels.showLongTextMessageInDialog(un.toString());
                    res = i;
                }

                if (un.getId() == id && showP == false) {
                    res = i;
                }
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return "Id universidade: " + id + "\nNome universidade: " + nome + "\n" + localizacao + "\n";
    }
}
