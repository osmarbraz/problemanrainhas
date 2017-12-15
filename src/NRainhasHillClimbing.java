
import java.util.Random;

/**
 *
 * @author Ana Paula, Osmar e Samuel
 */
public class NRainhasHillClimbing {

    /**
     * Atributo do número de soluções encontradas ao final do algoritmo
     */
    private static int solucoes;
   
    /**
     * Gerador de número aleatórios para o algoritmo
     */
    private static final Random RANDOMICO = new Random();

    /**
     * Habilita ou desabilida a saída dos dados de impressao
     */
    private static final boolean IMPRIMIRTABULEIRO = false;
   
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
    
     public static int moverParaBaixo(int n, int coluna, int deslocamento){                
	coluna = coluna + deslocamento;
	
        //Verifica se está acima dos limites
	if (coluna>=n){
            coluna = coluna%n;
	}	
        return coluna;
    }

    /**
     * Realiza a mutação em um indivíduo de forma aleatória
     *
     * @param individuo Um indivíduo a sofrer mutação
     * @return um indivíduo com a mutação
     */
    private static int[] mutacao(int[] individuo) {
        int n = individuo.length;
        //Seleciona a posição da mutação
        int posicao = RANDOMICO.nextInt(individuo.length);
        //Novo valor para a posicao selecionado
        int novovalor = RANDOMICO.nextInt(individuo.length);
        //Realiza a mutação na posição com o novoValor
        //individuo[posicao] = novovalor;
        individuo[posicao] = moverParaBaixo(n,individuo[posicao],novovalor);
        
        return individuo;
    }

    /**
     * Gera um indivíduo com n posições de forma aleatória.
     *
     * Gera n rainhas aleatórias(com repetição) para um tabuleiro.
     *
     * @param n Tamanho do indivíduo
     * @return um indivíduo da populacao com repetição de rainha
     */
    private static int[] geraIndividuo(int n) {
        
        //Inicializa o vetor de retorno
        int[] novoIndividuo = new int[n];
        int i = 0;
        
        //Gera os indivíduos de acordo com o tamanho do candidato
        while (i < n) {
            //Gera um uma rainha aleatória
            novoIndividuo[i] = new Random().nextInt(n);
            i = i + 1;
        }
        
        return novoIndividuo;
    }

    /**
     * Função de avaliação do indivíduo, retorna a quantidade de rainhas a
     * salvo.
     * 
     * Complexidade O(n^2)
     * 
     * @param R vetor das rainhas posicionadas. O elemento corresponde à
     * coluna e seu respectivo conteúdo corresponde à linha.
     * 
     * @return  A quantidade de rainhas salvas em R.
     */
    public static int fitness(int[] R) {
        
        //Recupera a quantidade de rainhas
        int n = R.length;                                           //Theta(1)
        int cont = 0;                                               //Theta(1)
        
        //Verifica se todas as rainhas estão em posições validas
        for (int k = 0; k < n; k++) {                               //Theta(n)
            //Verifica a quantidade de rainhas salvas
            if (validaPosicao(R, k)) {                              // n * O(n)
                cont = cont + 1;                                    // O(1)
            }
        }
        
        return cont;                                                // Theta(1)
    }

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
        int cont = 0;                                               // Theta(1)
        //Verifica se todas as rainhas estão em posições validas
        for (int k = 0; k < n; k++) {                               // Theta(n)
            if (validaPosicao(R, k)==false) {                       // n * O(n)
                cont = cont + 1;                                    // O(1)    
            }       
        }        
        
        return (cont==0);                                           // Theta(1)
    }

    /**
     * Algoritmo que executa as interações do algoritmo Hill Climbing.
     * 
     * Complexidade O(n^2)
     *
     * @param iteracoes Números de vezes a executar as interações no
     * algoritmo
     * @param n Quantidade de rainhas no tabuleiro
     * @return Retorna o melhor indivíduo encontrado nas interações
     */
    public static int[] hillClimbing(int iteracoes, int n) {
        //Gera o candidato inicial
        int[] candidato = geraIndividuo(n);                                 // Theta(n)
        //Calcula o custo do candidato inicial          
        int custoCandidato = fitness(candidato);                            // O(n^2)

        //Controla as interações 
        int i = 0;                                                          // Theta(1)
        //Para se chegar no número máximo de interacoes ou achar a solução
        while ((i < iteracoes) && (valida(candidato) == false)) {           // O(n^2)
            // Gera o proximo candidato aleatoriamente
            int[] vizinho = mutacao(candidato);                             // Theta(1)
            //Calcula o custo do novo vizinho                               
            int custoVizinho = fitness(vizinho);                            // O(n^2)
            // Verifica se é maior que o anterior   
            if (custoVizinho > custoCandidato) {                            // Theta(1)
                //Troca se o custo for maior
                candidato = vizinho;                                        // O(1)
            }
            //Avança para a próxima interação
            i = i + 1;                                                      // Theta(1)
        }
        //Retorna o melhor candidato encontrado nas iterações   
        return candidato;                                                   // Theta(1)
    }

    /**
     * Faz a chamada do algoritmo HillClimbing e apresenta as estatísticas.
     *
     * @param iteracoes Quantidade de iterações do algoritmo
     * @param n Quantidade de rainhas no tabuleiro
     */
    public static void executaHillClimbing(int iteracoes, int n) {
        //Guarda o melhor indivíduo
        //Procura o menor indivíduo
        int[] melhorIndividuo = hillClimbing(iteracoes, n);

        if (valida(melhorIndividuo)) {
            //Incrementa o contador de soluções
            solucoes = solucoes + 1;            
            //System.out.println("Solucao encontrada em " + interacaoSolucao + " interacoes");
            //System.out.println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            //System.out.println("Fitness  = " + fitness(melhorIndividuo));
            //System.out.println("Solucao:");
            imprimeSolucao(melhorIndividuo);
        } else {
            //System.out.println("Solucao não encontrada em " + interacaoSolucao + " interacoes");
            //System.out.println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            //System.out.println("Fitness  = " + fitness(melhorIndividuo));
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

        int iteracoes = 1000000;
        double tempoTotalDeTeste = 0;                
        long tempoAcumulado = 0;
        long solucoesAcumulado = 0;                
        
        //Realiza os testes para as quantidades das rainhas especificadas no vetor
        for (int problemaAtual = 0; problemaAtual < listaProblemasASolucionar.length; problemaAtual++) {

            int n = listaProblemasASolucionar[problemaAtual];
            
            System.out.println("-----------------------------------------------------------");
            System.out.println("Para " + n + " Rainhas \n");
            
            //Zera o tempo da execução da iteração
            tempoAcumulado = 0;
            //Zera o contador de solucoes da iteração
            solucoesAcumulado = 0;

            //Realiza a repetição do teste para a quantidade de rainhas    
            for (int testeAtual = 1; testeAtual <= repeticoesTeste; testeAtual++) {               
                
                //Zera o numero de solucoes
                solucoes = 0;             

                //Executa o gc antes de cada teste
                System.gc();

                //Início da execução
                long tempo = System.currentTimeMillis();

                //Executa a solução do algoritmo         
                executaHillClimbing(iteracoes, n);

                //Pega o tempo final do processamento da vez
                tempo = System.currentTimeMillis() - tempo;
                
                //Acumula o tempo do teste ao tempo final
                tempoAcumulado = tempoAcumulado + tempo;
                
                //Acumula a soluções do teste
                solucoesAcumulado = solucoesAcumulado + solucoes;                                
                System.out.println("Resultado da " + testeAtual + "ª execução: " + tempo + " milisegundos" + " com " + solucoes + " soluções");
            }
            //Calcula a média do tempo
            double mediaTempo = tempoAcumulado / (double)repeticoesTeste;            
            
            //Calcula a média de solucoes
            double mediaSolucoes = solucoesAcumulado / (double)repeticoesTeste;   

            System.out.println("\nSoluções Média: " + mediaSolucoes + " soluções");
            System.out.println("Tempo Médio...: " + mediaTempo + " milisegundos");
            System.out.println("Acumulado.....: " + tempoAcumulado + " milisegundos");

            tempoTotalDeTeste = tempoTotalDeTeste + tempoAcumulado;
        }
    
        System.out.println("===========================================================");
        System.out.println("O tempo total do teste e " + tempoTotalDeTeste + " milisegundos.");
        System.out.println("===========================================================");
    }
    
    public static void main(String[] args) {

        System.out.println("HillClimbing");
        
        // Vetor contendo os problemas a serem processados.
        // Cada elemento define a ordem do tabuleiro e, consequentemente, a 
        // quantidade de rainhas a serem posicionadas.
        int[] listaProblemasASolucionar = {4, 6, 8, 10 ,12, 14, 16, 18, 20, 22};
        
        // Quantidade de repetições do processamento
        // Útil para fins estatísticos.
        int repeticoesTeste = 10;
                
        System.out.println("Executando N-Rainhas com " + repeticoesTeste + " repetições.\n"); 
        nRainhas(listaProblemasASolucionar, repeticoesTeste);
    }
}