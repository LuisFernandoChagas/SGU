public class Localizacao {
    private String endereco, bairro, cidade, estado;

    public Localizacao(String endereco, String bairro, String cidade, String estado) {
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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

    @Override
    public String toString() {
        return "Rua: " + endereco + "\nBairro/Setor: " + bairro
                + "\nCidade: " + cidade + "\nEstado: " + estado;
    }
}
