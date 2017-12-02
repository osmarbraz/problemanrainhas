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
 * A cada nova rainha adicionada (sempre à direita da última), é realizado um 
 * teste para verificar se a mesma está "atacada" pelas demais rainhas anteriormente 
 * posicionadas no tabuleiro.
 * 
 * Caso a rainha em verificação esteja:
 *    
 *    Atacada: é feita uma nova tentativa de posicionamento, percorrendo uma a
 *             uma as possíveis posições na coluna.
 * 
 *             - Caso seja encontrada uma posição válida, repete-se o procedimento 
 *               para a próxima coluna/rainha;
 *             
 *             - Caso todas as possíveis linhas da coluna analisada estejam 
 *               atacadas, a rainha é retirada da coluna e retroage-se o teste à
 *               última rainha anteriormente posicionada.
 *   
 */
public class ForcaBrutaBackTracking {

    /**
     * Quantidade de solucoes encontradas ao final do algoritmo
     */
    private static int quantidadeDeSolucoes;

    /**
     * Habilita ou desabilida a saida dos dados de impressao
     */
     private static boolean desabilitarImpressao = true;

    /**
     * Trata a saida de dados
     *
     * @param string a ser impressa
     */
    private static void println(String string) {
        if (!desabilitarImpressao) {
            System.out.println(string);
        }
    }

    /**
     * Trata a saida de dados.
     *
     * @param string a ser impressa
     */
    private static void print(String string) {
        if (!desabilitarImpressao) {
            System.out.print(string);
        }
    }
    
    /**
     * Valida se a k-ésima rainha posicionada está sob ataque.
     * 
     * Uma rainha está sob ataque se há outra rainha na mesma linha, coluna ou 
     * diagonal onde esta se encontra.
     * 
     * @param rainhas o vetor das rainhas
     * @param k linha do vetor a ser analisa
     *
     * @return true se a k-ésima rainha não estiver sob ataque das demais já 
     * posicionadas
     */
    public static boolean validaPosicao(int[] rainhas, int k) {
        
        //Para cada uma das rainhas anteriormente posicionadas:
        for (int i = 0; i < k; i++) {
            
            //Verifica se a rainha k está na mesma coluna da rainha i
            if (rainhas[i] == rainhas[k]) {
                return false;
            }
            
            // Verifica se a rainha k está na mesma diagonal da rainha i
            if ( Math.abs(rainhas[i] - rainhas[k]) == (k - i)) {
             return false;                
            }
        }
        
        // Se solução é válida
        return true;        
    }

    /**
     * *************************************************
     * A funcao recursiva do metodo backTracking(). 
     * 
     * Cada instância do método é responsável por posicionar uma rainha na linha 
     * (em todas as colunas possiveis). 
     * 
     * Se uma rainha pode ser posicionada, baseando-se nas suas propriedades, 
     * sem que esta seja atacada pelas outras rainhas já posicionadas, entao esta 
     * rainha é posicionada na posição corrente e, recursivamente, as rainhas 
     * seguintes sao posicionadas.
     *
     * @param rainhas o vetor onde as rainhas serao inseridas
     * @param k coordenada da linha corrente onde a rainha devera ser inserida
     */
    public static void backTracking(int[] rainhas, int k) {

        int quantidadeDeRainhas = rainhas.length;

        if (k == quantidadeDeRainhas) {
            //Imprime o tabuleiro quando encontrar a solucao
            imprime(rainhas);
            quantidadeDeSolucoes++;
        } else {
            /* posiciona a rainha k + 1 */
            for (int i = 0; i < quantidadeDeRainhas; i++) {
                // Coloca uma nova rainha na posicao [k]                
                rainhas[k] = i;
                if (validaPosicao(rainhas, k)) {
                    //Avanca para a proxima linha
                    backTracking(rainhas, k + 1);
                }                
            }
        }
    }

    /**
     * Imprime o tabuleiro com as rainhas
     *
     * @param rainhas
     */
    private static void imprime(int rainhas[]) {

        //Recupera a quantidade de rainhas
        int tamanhoDoProblema = rainhas.length;

        println(" Solução número " + (quantidadeDeSolucoes + 1) + ":");

        for (int i = 0; i < tamanhoDoProblema; i++) {
            for (int j = 0; j < tamanhoDoProblema; j++) {
                //Posição ocupada
                if (rainhas[j] == i) {
                    print(" " + i + " ");
                } else {
                    print(" . ");
                }
            }
            println(" ");
        }
        println(" ");
    }

    /**
     * Executa o teste do agoritmo
     *
     * @param args
     */
    public static void main(String args[]) {
        
        System.out.println("BackTracking");

        // Vetor contendo os problemas a serem processados.
        // Cada elemento define a ordem do tabuleiro e, consequentemente, a 
        // quantidade de rainhas a serem posicionadas.
        int[] tamanhoDoProblema = {10, 12, 14};
        
        // Quantidade de repetições do processamento
        // Útil para fins estatísticos.
        int repeticoesTeste[] = {2};

        //Declara o tempo total do teste
        double tempoTeste = 0;
        
        //Realiza os testes para as quantidades das rainhas especificadas no vetor
        for (int qtdeR = 0; qtdeR < tamanhoDoProblema.length; qtdeR++) {

            int qtdeRainha = tamanhoDoProblema[qtdeR];
            int rainhas[] = new int[qtdeRainha];

            //Realiza a repeticao do teste para a quantidade de rainhas    
            for (int testeAtual = 0; testeAtual < repeticoesTeste.length; testeAtual++) {

                println("Executando com " + qtdeRainha + " rainhas por " + repeticoesTeste[testeAtual] + " vezes.");
                
                //Zera o numero de solucoes
                quantidadeDeSolucoes = 0;

                //Declara o tempo final da repeticao
                long tempoFinal = 0;

                //Repete o teste para as vezes especificadas no vetor
                for (int qtdeV = 0; qtdeV < repeticoesTeste[testeAtual]; qtdeV++) {
                    //Executa o gc antes de cada teste
                    System.gc();
                                        
                    //Início da execução
                    long tempo = System.currentTimeMillis();

                    //Executa a solucao do algoritmo
                    backTracking(rainhas, 0);

                    //Fim da execução
                    tempo = System.currentTimeMillis() - tempo;
                    
                    //Acumula o tempo do teste ao tempo final
                    tempoFinal = tempoFinal + tempo;
                }
                //Calcula a media do tempo
                double mediaTempo = tempoFinal / repeticoesTeste[testeAtual];                
                System.out.println("O tempo medio para " + qtdeRainha + " rainhas, executando " + repeticoesTeste[testeAtual] + " é vezes é " + mediaTempo + " milisegundos com " + quantidadeDeSolucoes + " solucoes em " + repeticoesTeste[testeAtual] + " repeticoes");
                tempoTeste = tempoTeste + mediaTempo;
            }
        }
        System.out.println("O tempo total do teste e " + tempoTeste + " milisegundos.");
    }
}
