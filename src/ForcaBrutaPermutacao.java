
/**
 * @author Ana Paula, Osmar e Samuel
 *
 * Forca bruta por permutacao*
 * O programa utiliza o método de permutação para gerar todas as soluções do problema.
 * Utiliza um vetor para armazenar as posições das rainhas.
 *
 */
public class ForcaBrutaPermutacao {

     /**
     * Quantidade de solucoes encontradas ao final do algoritmo
     */
    private static int quantidadeSolucoes;

    /**
     * Habilita ou desabilida a saida dos dados de impressao
     */
     private static final boolean HABILITARIMPRESSAO = false;

    /**
     * Valida se a k-ésima rainha posicionada está sob ataque.
     * 
     * Uma rainha está sob ataque se há outra rainha na mesma linha, coluna ou 
     * diagonal onde esta se encontra.
     * 
     * Como as rainhas são adicionadas sempre na coluna seguinte, não há necessi-
     * dade de validar conflitos na mesma coluna.
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
        for (int i=0; i<k; i++) {
            
            // Se sob ataque na linha
            if (R[i]==R[k]) {
                return false;
            }
            
            // Se sob ataque na diagonal
            if (Math.abs(R[i]-R[k])==(k-i)) {
             return false;                
            }
        }
        
        // Posição válida
        return true;        
    }

    /**
     * *************************************************
     * A funcao recursiva do metodo permutacao(). 
     * 
     * Cada instancia do metodo e
     * responsavel por posicionar uma rainha na linha (em todas as colunas
     * possiveis). Se uma rainha pode ser posicionada, baseando-se nas suas
     * propriedades, sem que esta seja atacada pelas outras rainhas ja
     * posicionadas, entao esta rainha e' posicionada na posicao corrente e,
     * recursivamente, as rainhas seguintes sao posicionadas.
     *
     * @param R o vetor onde as rainhas serao inseridas
     * @param visitado o vetor onde as posicoes usadas sao marcadas
     * @param k coordenada da linha corrente onde a rainhas devera ser inserida
     */
    public static void permutacao(int[] R, int[] visitado, int k) {

        //Recupera a quantidade de rainhas
        int n = R.length;

        //Se k e igual da quantidade rainhas cheguei no final da linha
        if (k == n) {
            if (validaPosicao(R, k-1)) {
                //Imprime o tabuleiro quando encontrar a solucao
                if (HABILITARIMPRESSAO) {
                    imprimeTabuleiro(R);
                }
                //Conta o numero de solucoes encontradas
                quantidadeSolucoes++;
            }
        } else {
            //Percorre o vetor de rainhas
            for (int i = 0; i < n; i++) {
                //realiza a permutacao somente para elementos não utilizados
                if (visitado[i] == 0) {
                    visitado[i] = 1;
                    R[k] = i;
                    //Avanca para proxima linha (k=k+1)
                    permutacao(R, visitado, k + 1);
                    visitado[i] = 0;
                }
            }
        }
    }

    /**
     * Imprime as soluções: tabuleiro e o posicionamento das rainhas
     *
     * @param posicaoRainhas
     */
    private static void imprimeTabuleiro(int[] posicaoRainhas) {

        // Tamanho do Problema
        int n = posicaoRainhas.length;

        System.out.println(" Solução número " + (quantidadeSolucoes + 1) + ":");
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //Posição ocupada
                if (posicaoRainhas[j] == i) {
                    System.out.print(" " + i + " ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println(" ");
        }
        System.out.println(" ");
    }

     /**
     * Chamada do algoritmo.
     * 
     * Provê o resultado da execução para cada problema resolvido, imprimindo em 
     * tela detalhes estatísticos para cada caso e tempo global.
     * 
     * @param listaProblemasASolucionar vetor contendo os problemas a serem executados.
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
                quantidadeSolucoes = 0;
                
                //Executa o garbage collector (gc) antes de cada teste
                System.gc();

                //Início da execução
                tempo = System.currentTimeMillis();

                /* se um elemento i estiver em uso, entao used[i] == 1, caso contrario, used[i] == 0. */
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

            System.out.println("\nSoluções...: " + quantidadeSolucoes);
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
        int[] listaProblemasASolucionar = {4, 6};
        
        // Quantidade de repetições do processamento
        // Útil para fins estatísticos.
        int repeticoesTeste = 2;
        
        System.out.println("Permutação");
        System.out.println("Executando N-Rainhas com " + repeticoesTeste + " repetições.\n\n"); 
        nRainhas(listaProblemasASolucionar, repeticoesTeste);

    }
}
