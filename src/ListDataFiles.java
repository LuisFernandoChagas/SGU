import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public interface ListDataFiles {
    public static boolean validateJavaDate(String strDate) {
        // validar data de nascimento;
        if (strDate.trim().equals("")) {
            return true;
        } else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
            sdfrmt.setLenient(false);
            try {
                Date javaDate = sdfrmt.parse(strDate);
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
    }

    public static String mostrarLista(ArrayList lista, int op) {
        // mostra todos os dados de salvos em um arraylist
        String res = "---- Lista de ";
        if (lista.size() > 0) {
            // opcao: 1 - universidades, 2 - cursos, 3 - alunos;
            if (op == 1) {
                res += "Universidade(s) ----\n";
                for (int i = 0; i < lista.size(); i++) {
                    Universidade un = (Universidade) lista.get(i);
                    res += un.toString() + "\n";
                }
            }

            if (op == 2) {
                res += "Matéria(s) ----\n";
                for (int i = 0; i < lista.size(); i++) {
                    Materia cr = (Materia) lista.get(i);
                    res += cr.toString() + "\n";
                }
            }

            if (op == 3) {
                res += "Alunos(s) ----\n";
                for (int i = 0; i < lista.size(); i++) {
                    Aluno al = (Aluno) lista.get(i);
                    res += al.toString() + "\n";
                }
            }

            return res;
        } else {
            return "não existem dados para mostrar aqui";
        }
    }

    /**
     * retorna os dados salvos no arraylist de universidades, cursos ou alunos em formato String
    */
     public static String retornarDadosFormatados(ArrayList lista, int op) {
        String res = "";
        if (lista.size() > 0) {
            // opcao: 1 - universidades, 2 - cursos, 3 - alunos;
            if (op == 1) {
                for (int i = 0; i < lista.size(); i++) {
                    Universidade un = (Universidade) lista.get(i);
                    res += un.getId() + "#"
                            + un.getNome() + "#"
                            + un.getSigla() + "#"
                            + un.getLocalizacao().getEndereco() + "#"
                            + un.getLocalizacao().getBairro() + "#"
                            + un.getLocalizacao().getCidade() + "#"
                            + un.getLocalizacao().getEstado() + "\n";
                }
            }

            if (op == 2) {
                for (int i = 0; i < lista.size(); i++) {
                    Materia cr = (Materia) lista.get(i);
                    res += cr.getId() + "#"
                            + cr.getNome() + "#"
                            + cr.getSigla() + "#"
                            + cr.getArea() + "#"
                            + cr.getUniversidade().getId()
                            + "\n";
                }
            }

            if (op == 3) {
                for (int i = 0; i < lista.size(); i++) {
                    Aluno al = (Aluno) lista.get(i);
                    res += al.getMatricula() + "#"
                            + al.getNome() + "#"
                            + al.getDataNascimento() + "#"
                            + al.getAnoIngresso() + "#"
                            + al.getSituacao() + "#"
                            + al.getUniversidade().getId() + "#"
                            + al.getCurso().getId()
                            + "\n";
                }
            }

            return res;
        } else {
            return "";
        }
    }

    /**
     * Retorna a quantidade de linhas em um arquivo
     */
    public static int lineCount(String path) {
        try {
            Path file = Paths.get(path);
            return (int) Files.lines(file).count();
        } catch (Exception e) {
            throw new LerArquivoException("Erro ao ler arquivos.");
        }
    }

    public static void inicializaUniversidades(String path, ArrayList<Universidade> listaUniversidade) {
        try {
            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String s = br.readLine();

            while (s != null) {
                String[] campos = s.split("#");

                Localizacao loc = new Localizacao(campos[3], campos[4], campos[5], campos[6]);
                Universidade fw = new Universidade(campos[1], campos[2], loc);
                fw.setId(Integer.parseInt(campos[0]));

                listaUniversidade.add(fw);

                s = br.readLine();
            }
            br.close();

            Universidade.quantidade = lineCount(path);
        } catch (Exception e) {
            throw new LerArquivoException("Erro ao ler arquivos.");
        }
    }

    public static void inicializaCurso(String path, ArrayList<Materia> listaCurso, ArrayList<Universidade> listaUniversidade) {
        try {
            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String s = br.readLine();


            while (s != null) {
                String[] campos = s.split("#");

                int uniId = Universidade.consulta(listaUniversidade, Integer.parseInt(campos[4]), false);
                Universidade fw = listaUniversidade.get(uniId);

                Materia cr = new Materia(campos[1], campos[2], campos[3], fw);
                cr.setId(Integer.parseInt(campos[0]));

                listaCurso.add(cr);

                s = br.readLine();
            }
            br.close();

            Materia.quantidade = lineCount(path);
        } catch (Exception e) {
            throw new LerArquivoException("Erro ao ler arquivos.");
        }
    }

    public static void inicializaAluno(String path, ArrayList<Aluno> listaAluno, ArrayList<Materia> listaCurso, ArrayList<Universidade> listaUniversidade) {
        try {
            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String s = br.readLine();


            while (s != null) {
                String[] campos = s.split("#");

                int uniId = Universidade.consulta(listaUniversidade, Integer.parseInt(campos[5]), false);
                int crId = Materia.consulta(listaCurso, Integer.parseInt(campos[6]), false);

                Universidade fw = listaUniversidade.get(uniId);
                Materia fc = listaCurso.get(crId);

                Aluno al = new Aluno(campos[1], campos[2], Integer.parseInt(campos[3]), Integer.parseInt(campos[4]), fw, fc);
                al.setMatricula(campos[0]);

                listaAluno.add(al);

                s = br.readLine();
            }
            br.close();

            Aluno.quantidade = lineCount(path);
        } catch (Exception e) {
            throw new LerArquivoException("Erro ao ler arquivos.");
        }
    }

    public static void inicializarDados(ArrayList<Universidade> listaUniversidade, ArrayList<Materia> listaCurso, ArrayList<Aluno> listaAluno) {
        // define caminhos para os arquivos utilizados (universidades, cursos e alunos);
        String pathUni = "src/universidades.txt";
        String pathCr = "src/materia.txt";
        String pathAl = "src/alunos.txt";

        // verifica se os arquivos existem;
        File tmpFUni = new File(pathUni);
        File tmpFCr = new File(pathCr);
        File tmpFAl = new File(pathAl);

        try {
            // inicializa dados de universidades
            if (tmpFUni.exists() == true) {
                inicializaUniversidades(pathUni, listaUniversidade);
            } else {
                // se !file.exists() => cria um novo arquivo;
                try {
                    File arquivo = new File(pathUni);
                    FileWriter f = new FileWriter(arquivo);
                    f.close();
                } catch (Exception e) {
                    throw new EscreverArquivoException("Erro ao inicializar arquivo.");
                }
            }

            // inicializa dados de cursos
            if (tmpFCr.exists() == true) {
                inicializaCurso(pathCr, listaCurso, listaUniversidade);
            } else {
                // se !file.exists() => cria um novo arquivo;
                try {
                    File arquivo = new File(pathCr);
                    FileWriter f = new FileWriter(arquivo);
                    f.close();
                } catch (Exception e) {
                    throw new EscreverArquivoException("Erro ao inicializar arquivo.");
                }
            }

            // inicializa dados de alunos
            if (tmpFAl.exists() == true) {
                inicializaAluno(pathAl, listaAluno, listaCurso, listaUniversidade);
            } else {
                // se !file.exists() => cria um novo arquivo;
                try {
                    File arquivo = new File(pathAl);
                    FileWriter f = new FileWriter(arquivo);
                    f.close();
                } catch (Exception e) {
                    throw new EscreverArquivoException("Erro ao inicializar arquivo.");
                }
            }
        } catch (Exception e) {
            throw new LerArquivoException("Erro ao ler arquivos.");
        }
    }

    public static void salvarDadosNoArquivo(ArrayList<Universidade> listaUniversidade, ArrayList<Materia> listaCurso, ArrayList<Aluno> listaAluno) {
        // define caminhos para os arquivos utilizados (universidades, cursos e alunos);
        String pathUni = "src/universidades.txt";
        String pathCr = "src/materia.txt";
        String pathAl = "src/alunos.txt";

        try {
            OutputStream os = new FileOutputStream(pathUni);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            // escreve no arquivo os dados de universidades
            bw.write(retornarDadosFormatados(listaUniversidade, 1));
            bw.close();

            os = new FileOutputStream(pathCr);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);

            // escreve no arquivo os dados de cursos
            bw.write(retornarDadosFormatados(listaCurso, 2));
            bw.close();

            os = new FileOutputStream(pathAl);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);

            // escreve no arquivo os dados de alunos
            bw.write(retornarDadosFormatados(listaAluno, 3));
            bw.close();
        } catch (Exception e) {
            throw new EscreverArquivoException("Erro ao salvar dados nos arquivos.");
        }
    }
}
