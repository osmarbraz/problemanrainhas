/**
 *
 * @author Ana Paula, Osmar e Samuel
 *
 * O objetivo deste programa é encontrar o número de possibilidades para posicionar 
 * N Rainhas em um tabuleiro de dimensões N x N.
 * 
 * A entrada do programa é um número inteiro que define as dimensões do tabuleiro
 * e, consequentemente, o número de rainhas a serem posicionadas.
 * 
 * A estratégia consiste em utilizar o método de Backtraking, mais especificamente, 
 * aplicando a técnica de busca em profundidade.
 * 
 * A computação inicia adicionando-se a primeira rainha na primeira linha e coluna 
 * do tabuleiro e então segue o seguinte padrão:
 * 
 * Uma rainha somente pode ser adicionada ao tabuleiro se todas as posicionadas 
 * não estão sob ataque.
 * 
 * A cada nova rainha adicionada (sempre na coluna à direita da  última rainha 
 * adicionada), é realizado um teste para verificar se a mesma está sob ataque de 
 * alguma rainha já posicionadas no tabuleiro.
 * 
 * Caso a rainha em verificação esteja:
 *    
 *    Atacada: é feita uma nova tentativa de posicionamento, percorrendo uma a
 *             uma as possíveis posições na coluna, ou seja, variando, a linha.
 * 
 *             - Caso seja encontrada uma posição válida, repete-se o procedimento 
 *               para a próxima coluna/rainha;
 *             
 *             - Caso todas as possíveis linhas da coluna analisada estejam 
 *               atacadas, a rainha é retirada da coluna e retroage-se o teste à
 *               última rainha anteriormente posicionada, buscando uma posição 
 *               distinta para a mesma.
 *   
 */
public class ForcaBrutaBackTracking {

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
     * A função recursiva do método backTracking(). 
     * 
     * Cada instância é responsável por posicionar uma rainha na primeira linha 
     * válida da coluna em análise, ou seja, sem que a mesma esteja sob ataque. 
     * 
     * Se uma rainha está em uma posição válida, então a mesma é posicionada e, 
     * recursivamente, as rainhas seguintes são posicionadas.
     *
     * @param R vetor das rainhas posicionadas. O elemento corresponde à
     * coluna e seu respectivo conteúdo corresponde à linha.
     * 
     * @param k coordenada da linha corrente onde a rainha devera ser inserida
     */
    public static void backTracking(int[] R, int k) {

        int n = R.length;

        if (k == n) {
            if (HABILITARIMPRESSAO) {
                imprimeTabuleiro(R);
            }
            quantidadeSolucoes++;
        } else {
            /* posiciona a rainha k + 1 */
            for (int i=0; i<n; i++) {
                R[k] = i;
                //Avança para a próxima rainha/coluna
                if (validaPosicao(R, k)) {
                    backTracking(R, k + 1);
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
    private static void nRainhas (int[] listaProblemasASolucionar, int repeticoesTeste) {
        
        double tempoTotalDeTeste = 0;
        double mediaTempo;
        long tempoAcumulado;
        long tempo;
        
        //Realiza os testes para as quantidades das rainhas especificadas no vetor
        for (int problemaAtual = 0; problemaAtual < listaProblemasASolucionar.length; problemaAtual++) {

            int n = listaProblemasASolucionar[problemaAtual];
            int rainhas[] = new int[n];

            System.out.println("-----------------------------------------------------------");
            System.out.println("Para " + n + " Rainhas \n"); 
            
            //Declara o tempo final da repeticao
            tempoAcumulado = 0;

            //Repete o teste para as vezes especificadas no vetor
            for (int testeAtual = 1; testeAtual <= repeticoesTeste; testeAtual++) {
            
                //Zera o numero de solucoes
                quantidadeSolucoes = 0;
                
                //Executa o garbage collector (gc) antes de cada teste
                System.gc();

                //Início da execução
                tempo = System.currentTimeMillis();
                
                backTracking(rainhas, 0);

                //Fim da execução
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
        System.out.println("Tempo Global...: " + tempoTotalDeTeste + " milisegundos.");
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
        
        System.out.println("BackTracking");
        System.out.println("Executando N-Rainhas com " + repeticoesTeste + " repetições.\n\n"); 
        nRainhas(listaProblemasASolucionar, repeticoesTeste);

    }
}
