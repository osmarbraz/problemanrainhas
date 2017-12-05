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
    private static int quantidadeSolucoesEncontradas;

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
     * @param posicaoRainhas vetor das rainhas posicionadas. O elemento corresponde à
     * coluna e seu respectivo conteúdo corresponde à linha.
     * 
     * @param totalDeColunas linha do vetor a ser analisada
     *
     * @return true se a k-ésima rainha não estiver sob ataque das demais já 
     * posicionadas
     */
    public static boolean validaPosicao(int[] posicaoRainhas, int totalDeColunas) {
        
        // Rainhas anteriormente posicionadas:
        for (int colunaAtual = 0; colunaAtual < totalDeColunas; colunaAtual++) {
            
            // Se sob ataque na linha
            if (posicaoRainhas[colunaAtual] == posicaoRainhas[totalDeColunas]) {
                return false;
            }
            
            // Se sob ataque na diagonal
            if (Math.abs(posicaoRainhas[colunaAtual] - posicaoRainhas[totalDeColunas]) == (totalDeColunas - colunaAtual)) {
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
     * @param posicaoRainhas vetor das rainhas posicionadas. O elemento corresponde à
     * coluna e seu respectivo conteúdo corresponde à linha.
     * 
     * @param colunaEmAnalise coordenada da linha corrente onde a rainha devera ser inserida
     */
    public static void backTracking(int[] posicaoRainhas, int colunaEmAnalise) {

        int n = posicaoRainhas.length;

        if (colunaEmAnalise == n) {
            if (HABILITARIMPRESSAO) {
                imprimeTabuleiroSolucoes(posicaoRainhas);
            }
            quantidadeSolucoesEncontradas++;
        } else {
            /* posiciona a rainha k + 1 */
            for (int linhaAtual = 0; linhaAtual < n; linhaAtual++) {
                posicaoRainhas[colunaEmAnalise] = linhaAtual;
                //Avança para a próxima rainha/coluna
                if (validaPosicao(posicaoRainhas, colunaEmAnalise)) {
                    backTracking(posicaoRainhas, colunaEmAnalise + 1);
                }                
            }
        }
    }

    /**
     * Imprime as soluções: tabuleiro e o posicionamento das rainhas
     *
     * @param posicaoRainhas
     */
    private static void imprimeTabuleiroSolucoes(int[] posicaoRainhas) {

        //Recupera a quantidade de rainhas
        int tamanhoDoProblema = posicaoRainhas.length;

        System.out.println(" Solução número " + (quantidadeSolucoesEncontradas + 1) + ":");
        
        for (int i = 0; i < tamanhoDoProblema; i++) {
            for (int j = 0; j < tamanhoDoProblema; j++) {
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
                quantidadeSolucoesEncontradas = 0;
                
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
            System.out.println("\nSoluções...: " + quantidadeSolucoesEncontradas);
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
