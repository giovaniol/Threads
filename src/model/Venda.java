package model;

public class Venda implements Runnable {
    private final Estoque estoque;
    private final String produto;
    private final int quantidade;
    private  String nome;

    public Venda(Estoque estoque, String produto, int quantidade) {
        this.estoque = estoque;
        this.produto = produto;
        this.quantidade = quantidade;

    }

    @Override
    public void run() {
        this.nome = Thread.currentThread().getName();


        if (estoque.venderProduto(produto, quantidade, nome)) {

            System.out.println("\nA " + nome + " realizou a compra.");
        } else {
            System.out.println("\nA " + nome + " não pôde realizar a compra.");
        }
    }




}

