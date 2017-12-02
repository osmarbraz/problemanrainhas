/**
 *
 * @author Ana Paula, Osmar e Samuel
 *
 * O programa utiliza o metodo de Backtraking, mais especificamente, aplicando 
 * a técnica de busca em profundidade.
 * 
 * A computação inicia posicionando a primeira rainha. 
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
 *             Caso:
 *             
 *             - Seja encontrada uma posição válida, repete-se o procedimento para  
 *               a próxima coluna/rainha;
 *             
 *             - Todas as possíveis linhas da coluna analisada estejam atacadas,
 *               A rainha é retirada da coluna e retroage-se o teste à última 
 *               rainha anteriormente posicionada.
 *   
 */
public class ForcaBrutaBackTracking {

    /**
     * Quantidade de solucoes encontradas ao final do algoritmo
     */
    private static int solucoes;

    /**
     * Habilita ou desabilida a saida dos dados de impressao
     */
     private static boolean desabilidarImpressao = true;

    /**
     * Trata a saida de dados
     *
     * @param string a ser impressa
     */
    private static void println(String string) {
        if (!desabilidarImpressao) {
            System.out.println(string);
        }
    }

    /**
     * Trata a saida de dados.
     *
     * @param string
     */
    private static void print(String string) {
        if (!desabilidarImpressao) {
            System.out.print(string);
        }
    }

    /**
     * Transforma o vetor em uma string para escrever em tela.
     *
     * @param vet Vetor com os dados inteiros a serem transformados em string
     * @return Um literal com os dados do vetor separados por ;
     */
    private static String vetorToString(int[] vet) {
        String ret = "";
        if ((vet != null) && (vet.length > 0)) {
            for (int i = 0; i < vet.length; i++) {
                ret = ret + vet[i] + ";";
            }
            //retira o ultimo ; da string
            ret = ret.substring(0, ret.length() - 1);
        }
        return ret;
    }
    
    /**
     * Uma das propriedades da rainha e que nao pode haver outra rainha na linha
     * ou na coluna onde esta se encontra. Assim, na construcao do algoritmo de
     * solucao, nao se pode colocar uma rainha em uma posicao que esteja sendo
     * atacada. Esta mesma propriedade tambem vale para as diagonais em relacao
     * as rainha ja posicionadas.
     *
     * @param rainhas o vetor das rainhas
     * @param k linha do vetor a ser analisa
     *
     * @return true se a rainha [k] nao for atacada nas posicoes ja atacadas por
     * rainhas previamente inseridas
     */
    public static boolean valida(int[] rainhas, int k) {

        //Percorre o vetor de rainhas
        for (int i = 0; i < k; i++) {
            //Verifica se a rainha esta na mesma coluna
            if (rainhas[i] == rainhas[k]) {
                return false;
            }
            //Verifica se a rainha esta diagonal principal
            if ((rainhas[i] - rainhas[k]) == (k - i)) {
                return false;
            }
            //Verifica se a rainha esta na diagonal secundaria
            if ((rainhas[k] - rainhas[i]) == (k - i)) {
                return false;
            }

            // Código alternativo, pois faz verificao da diagonal principal e secundaria simultaneamente usando abs para tirar o sinal*/
            //if ( Math.abs(rainhas[i] - rainhas[qtdeRainha]) == (k - i)) {
            // return false;                
            //}
        }
        // Retorna que a solucao e valida
        return true;        
    }

    /**
     * *************************************************
     * A funcao recursiva do metodo backTracking(). Cada instancia do metodo e
     * responsavel por posicionar uma rainha na linha (em todas as colunas
     * possiveis). Se uma rainha pode ser posicionada, baseando-se nas suas
     * propriedades, sem que esta seja atacada pelas outras rainhas ja
     * posicionadas, entao esta rainha e' posicionada na posicao corrente e,
     * recursivamente, as rainhas seguintes sao posicionadas.
     *
     * @param rainhas o vetor onde as rainhas serao inseridas
     * @param k coordenada da linha corrente onde a rainhas devera ser inserida
     */
    public static void backTracking(int[] rainhas, int k) {

        //Recupera a quantidade de rainhas
        int qtdeRainha = rainhas.length;

        /* solucao completa */
        if (k == qtdeRainha) {
            //Imprime o tabuleiro quando encontrar a solucao
            imprime(rainhas);
            //Conta o numero de solucoes encontradas
            solucoes = solucoes + 1;
        } else {
            /* posiciona a rainha k + 1 */
            for (int i = 0; i < qtdeRainha; i++) {
                // Coloca uma nova rainha na posicao [k]                
                rainhas[k] = i;
                if (valida(rainhas, k)) {
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
        int qtdeRainha = rainhas.length;

        println(" Solucao numero " + (solucoes + 1) + ":");

        for (int i = 0; i < qtdeRainha; i++) {
            for (int j = 0; j < qtdeRainha; j++) {
                //Posicao ocupada
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

        //Quantidade de rainhas serem testadas
        int qtdeRainhasTeste[] = {16, 20 , 25};
        //Especifica o numero de vezes a se realizado com cada qtde de rainhas
        int repeticoesTeste[] = {1};

        //Declara o tempo total do teste
        double tempoTeste = 0;
        
        //Realiza os testes para as quantidades das rainhas especificadas no vetor
        for (int qtdeR = 0; qtdeR < qtdeRainhasTeste.length; qtdeR++) {

            int qtdeRainha = qtdeRainhasTeste[qtdeR];
            int rainhas[] = new int[qtdeRainha];

            //Realiza a repeticao do teste para a quantidade de rainhas    
            for (int qtdeT = 0; qtdeT < repeticoesTeste.length; qtdeT++) {

                println("Execuntando com " + qtdeRainha + " rainhas por " + repeticoesTeste[qtdeT] + " vezes.");
                
                //Zera o numero de solucoes
                solucoes = 0;

                //Declara o tempo final da repeticao
                long tempoFinal = 0;

                //Repete o teste para as vezes especificadas no vetor
                for (int qtdeV = 0; qtdeV < repeticoesTeste[qtdeT]; qtdeV++) {

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
                double mediaTempo = tempoFinal / repeticoesTeste[qtdeT];                
                System.out.println("O tempo medio para " + qtdeRainha + " rainhas, executando " + repeticoesTeste[qtdeT] + " é vezes é " + mediaTempo + " milisegundos com " + solucoes + " solucoes em " + repeticoesTeste[qtdeT] + " repeticoes");
                tempoTeste = tempoTeste + mediaTempo;
            }
        }
        System.out.println("O tempo total do teste e " + tempoTeste + " milisegundos.");
    }
}
