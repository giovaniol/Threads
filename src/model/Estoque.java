package  model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Estoque {
    private final List<Produto> listinha = Collections.synchronizedList(new ArrayList<Produto>());

    public boolean adicionarProduto(Produto produto) {
        return listinha.add(produto);
    }

    public boolean removerProduto(Produto produto, int quantidade) {
        if (produto != null){
            for (Produto p : listinha){
                if (p.getNome().equals(produto.getNome())){
                    p.setQuantidade(p.getQuantidade() - quantidade);
                    return true;
                }
            }

        }
        return false;
    }

    public boolean consultarProduto(Produto produto, int quantidade) {
        synchronized (listinha) {
            for (Produto p : listinha) {
                if (p.equals(produto)) {
                    return p.getQuantidade() >= quantidade ? true: false;
                }
            }
        }
        return false;
    }

    public boolean venderProduto(String produto, int quantidade, String nome) {
        synchronized (listinha) {
            for (Produto p : listinha) {
                if (p.getNome().equals(produto)) {
                    if (consultarProduto(p, quantidade)) {
                        removerProduto(p, quantidade);
                        System.out.println("Venda realizada: " + p.getNome() + "\nPara: " + nome + "\nQuantidade: " + quantidade + "\n");
                        return true;
                    } else {
                        System.out.println("Estoque insuficiente para o produto: " + p.getNome());
                        return false;
                    }
                }
            }
        }
        return false;
    }


    public void mostrarEstoque() {
        synchronized (listinha) {
            for (Produto p : listinha) {
                System.out.println(p.toString());
            }
        }
    }

    public static void main(String[] args) {
        Estoque estoque = new Estoque();

        estoque.adicionarProduto(new Produto("Creatina", 100, 10.0));

        System.out.println("Estoque inicial:");
        estoque.mostrarEstoque();


        Thread venda1 = new Thread(new Venda(estoque, "Creatina", 50));
        Thread venda2 = new Thread(new Venda(estoque, "Creatina", 50));
        Thread venda3 = new Thread(new Venda(estoque, "Creatina", 50));


        venda1.start();
        venda2.start();
        venda3.start();

        try {
            venda1.join();
            venda2.join();
            venda3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Estoque final:");
        estoque.mostrarEstoque();
    }
}

