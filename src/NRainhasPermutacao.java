/**
 * @author Ana Paula, Osmar e Samuel
 *
 * Forca bruta por permutação.
 * 
 * O objetivo deste programa é encontrar o número de possibilidades para posicionar
 * N Rainhas em um tabuleiro de dimensões N x N.
 *
 * A entrada do programa é um número inteiro que define as dimensões do tabuleiro
 * e, consequentemente, o número de rainhas a serem posicionadas.
 *
 * A estratégia consiste em utilizar o método da Força Bruta  com permutação aplicando 
 * a técnica de busca em profundidade para gerar todas as posibilidades sem repetições
 * e depois verificar se esta possibilidade é válida.
 *
 */
public class NRainhasPermutacao {

    /**
     * Quantidade de solucoes encontradas ao final do algoritmo
     */
    private static int solucoes;

    /**
     * Habilita ou desabilida a saída dos dados de impressao
     */
    private static final boolean IMPRIMIRTABULEIRO = false;

    /**
     * Valida se a k-ésima rainha posicionada está sob ataque.
     * 
     * Uma rainha está sob ataque se há outra rainha na mesma linha, coluna ou 
     * diagonal onde esta se encontra.
     * 
     * Como as rainhas são adicionadas sempre na coluna seguinte, não há necessi-
     * dade de validar conflitos na mesma coluna.
     * 
     * Complexidade Theta(k)
     * 
     * @param R vetor das rainhas posicionadas. O elemento corresponde à
     * coluna e seu respectivo conteúdo corresponde à linha.
     * 
     * @param k linha do vetor a ser analisada
     *
     * @return true se a k-ésima rainha não estiver sob ataque das demais já 
     * posicionadas
     */
    public static boolean validaPosicao(int[] R, int k) {
                
        // Rainhas anteriormente posicionadas:
        for (int i=0; i<k; i++) {                               // Theta(k)
            // Se sob ataque na linha
            if (R[i]==R[k]) {                                   // Theta(k)
                return false;                                   // O(1)
            }
            
            // Se sob ataque na diagonal
            if (Math.abs(R[i]-R[k])==(k-i)) {                   // Theta(k)
             return false;                                      // O(1)
            }
        }        
        // Posição válida
        return true;                                            // Theta(1)
    }

    /**
     * Avalia todas as rainhas posicionadas.
     * 
     * Chamada o método valida posição para avaliação cada posição do 
     * vetor de rainhas.
     * 
     * Complexidade O(n^2)
     * 
     * @param R vetor das rainhas posicionadas. O elemento corresponde à
     * coluna e seu respectivo conteúdo corresponde à linha.
     * 
     * @return True ou False se existe alguma rainha em posição inválida.
     */
    public static boolean valida(int[] R) {  
        
        //Recupera a quantidade de rainhas
        int n = R.length;                                           // Theta(1)
        
        //Verifica se todas as rainhas estão em posições validas
        int k = 0;                                                  // Theta(1)        
                
        //Percorre as n posições, se encontrar uma posição inválida 
        //interrompe o laço
        while ((k < n ) && (validaPosicao(R, k))) {                 // n * Theta(1) + n * O(n)  = O(n^2)
           k = k + 1;                                               // n * O(1) = O(n)
        }
                
        //Se k==n, então necessariamente não houve posição inválida identificada
        if (k==n) {                                                 // Theta(1)
           return true;                                             // O(1)
        } else {
            //Se o while foi interrompido antes existe posição invalida
           return false;                                            // O(1)
        }        
    }

    /**
     * *************************************************
     * A função recursiva do método permutação().
     *
     * Cada instância é responsável por posicionar uma rainha na primeira linha
     * válida da coluna em análise, ou seja, sem que a mesma esteja sob ataque.
     *
     * Se uma rainha está em uma posição válida, então a mesma é posicionada e,
     * recursivamente, as rainhas seguintes são posicionadas.
     *
     * Complexidade Theta(n!)
     *
     * @param R vetor onde as rainhas serão inseridas
     * @param visitado vetor onde as posições usadas são marcadas
     * @param k coordenada da linha corrente onde a rainhas devera ser inserida
     */
    public static void permutacao(int[] R, int[] visitado, int k) {

        //Recupera a quantidade de rainhas
        int n = R.length;                                               // Theta(1)

        //Se k e igual da quantidade rainhas cheguei no final da linha
        if (k == n) {                                                   // Theta(1)
            //Avalia todas as rainhas colocadas 
            if (valida(R)) {                                            // O(n^2)
                imprimeSolucao(R);                                      // Theta(n^2)
            }
        } else {                                                        // 0
            //Percorre o vetor de rainhas           
            for (int i = 0; i < n; i++) {                               // Theta(n)
                //realiza a permutação somente para elementos não utilizados                
                if (visitado[i] == 0) {                                 // Theta(n)
                    visitado[i] = 1;                                    // O(n)
                    R[k] = i;                                           // O(n)
                    //Avança para próxima linha (k=k+1)                                   
                    permutacao(R, visitado, k + 1);                     // O(n) * T(k+1)
                    visitado[i] = 0;                                    // O(n)
                }
            }
        }
    }

    /**
     * Imprime as soluções do tabuleiro.
     * 
     * Percorre o tabuleiro exibindo as posições ocupadas pelas rainhas.
     *
     * Complexidade O(n^2)
     * 
     * @param R vetor das rainhas.
     */
    private static void imprimeSolucao(int[] R) {

        //Tamanho do Problema
        int n = R.length;                                               // Theta(1)

        //Incrementa o contador de soluções
        solucoes = solucoes + 1;                                        // Theta(1)

        if (IMPRIMIRTABULEIRO) {                                        // Theta(1)        
            
            System.out.println(" Solução número " + solucoes + ":");    // O(1)
            
            for (int i = 0; i < n; i++) {                               // O(n) 
                for (int j = 0; j < n; j++) {                           // n * O(n)
                    //Posição ocupada
                    if (R[j] == i) {                                    // O(1))
                        System.out.print(" " + i + " ");                // O(1)
                    } else {                                            // 0
                        System.out.print(" . ");                        // O(1) 
                    }
                }
                System.out.println(" ");                                // O(1)
            }
            System.out.println(" ");                                    // O(1)
        }
    }

    /**
     * Chamada do algoritmo.
     *
     * Provê o resultado da execução para cada problema resolvido, imprimindo em
     * tela detalhes estatísticos para cada caso e tempo global.
     *
     * @param listaProblemasASolucionar vetor contendo os problemas a serem
     * executados.
     * @param repeticoesTeste quantidade de repetições para cada problema.
     */
    private static void nRainhas(int[] listaProblemasASolucionar, int repeticoesTeste) {

        double tempoTotalDeTeste = 0;
        double mediaTempo;
        long tempoAcumulado;
        long tempo;

        //Realiza os testes para as quantidades das rainhas especificadas no vetor
        for (int problemaAtual = 0; problemaAtual < listaProblemasASolucionar.length; problemaAtual++) {

            int n = listaProblemasASolucionar[problemaAtual];
            int rainhas[] = new int[n];
            int usado[] = new int[n];

            System.out.println("-----------------------------------------------------------");
            System.out.println("Para " + n + " Rainhas \n");

            tempoAcumulado = 0;

            //Repete o teste para as vezes especificadas no vetor
            for (int testeAtual = 1; testeAtual <= repeticoesTeste; testeAtual++) {

                //Zera o numero de solucoes
                solucoes = 0;

                //Executa o garbage collector (gc) antes de cada teste
                System.gc();

                //Início da execução
                tempo = System.currentTimeMillis();

                /* se um elemento i estiver em uso, entao usado[i] == 1, caso contrario, usado[i] == 0. */
                for (int i = 0; i < n; i++) {
                    usado[i] = 0;
                }
                permutacao(rainhas, usado, 0);

                //Pega o tempo final do processamento da vez
                tempo = System.currentTimeMillis() - tempo;
                //Acumula o tempo do teste ao tempo final
                tempoAcumulado = tempoAcumulado + tempo;
                System.out.println("Resultado da " + testeAtual + "ª execução: " + tempo + " milisegundos");
            }

            mediaTempo = tempoAcumulado / repeticoesTeste;

            System.out.println("\nSoluções...: " + solucoes);
            System.out.println("Tempo Médio: " + mediaTempo + " milisegundos");
            System.out.println("Acumulado..: " + tempoAcumulado + " milisegundos");

            tempoTotalDeTeste = tempoTotalDeTeste + tempoAcumulado;
        }

        System.out.println("===========================================================");
        System.out.println("O tempo total do teste e " + tempoTotalDeTeste + " milisegundos.");
        System.out.println("===========================================================");
    }

    /**
     * Executa o algoritmo.
     *
     * Informações relevantes:
     *
     * listaProblemasASolucionar: vetor contendo os tamanhos a serem resolvidos.
     * repeticoesTeste: Quantidade de vezes que cada problema é solucionado.
     *
     * @param args
     */
    public static void main(String args[]) {

        // Vetor contendo os problemas a serem processados.
        // Cada elemento define a ordem do tabuleiro e, consequentemente, a 
        // quantidade de rainhas a serem posicionadas.
        int[] listaProblemasASolucionar = {4, 6, 8, 10 ,12, 14, 16, 18, 20, 22};

        // Quantidade de repetições do processamento
        // Útil para fins estatísticos.
        int repeticoesTeste = 1;

        System.out.println("Permutação");
        System.out.println("Executando N-Rainhas com " + repeticoesTeste + " repetições.\n");
        nRainhas(listaProblemasASolucionar, repeticoesTeste);
    }
}
