import java.util.Random;

/**
 *
 * @author Ana Paula, Osmar e Samuel
 *
 */
public class NRainhasSimulatedAnnealing {

    /**
     * Armazena o número de soluções encontradas ao final do algoritmo
     */
    private static int solucoes;

    /**
     * Gerador de número aleatórios para o algoritmo
     */
    private static final Random RANDOMICO = new Random();

    /**
     * Habilita ou desabilita a saida dos dados de impressão
     */
    private static final boolean IMPRIMIRTABULEIRO = false;
  
    /**
     * Imprime as soluções: tabuleiro e o posicionamento das rainhas.
     *
     * @param R vetor das rainhas.
     */
    private static void imprimeSolucao(int[] R) {

        //Tamanho do Problema
        int n = R.length;

        if (IMPRIMIRTABULEIRO) {

            System.out.println(" Solução número " + solucoes + ":");

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    //Posição ocupada
                    if (R[j] == i) {
                        System.out.print(" " + i + " ");
                    } else {
                        System.out.print(" . ");
                    }
                }
                System.out.println(" ");
            }
            System.out.println(" ");
        }
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
        //Gera os genes de individuo de acordo com o tamanho do gene
        while (i < n) {
            //Gera um uma rainha aleatória
            novoIndividuo[i] = RANDOMICO.nextInt(n);
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
        int n = R.length;                                           // Theta(1)
        int cont = 0;                                               // Theta(1)
        //Verifica se todas as rainhas estão em posições validas
        for (int k = 0; k < n; k++) {                               // Theta(1)
            //Verifica a quantidade de rainhas salvas
            if (validaPosicao(R, k)) {                              // n * O(n)
                cont = cont + 1;                                    // O(n)
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
     * Move uma rainha de uma coluna segundo a quantidade de delocamentos.
     * @param n Quantidade de rainhas.
     * @param coluna Coluna da rainha.
     * @param deslocamento Número de posições que deve ser deslocada a rainha.
     * @return A nova posição da rainha de acordo com o deslocamento.
     */
    public static int moverParaBaixo(int n, int coluna, int deslocamento){                
	coluna = coluna + deslocamento;
	
        //Verifica se está acima dos limites
	if (coluna>=n){
            coluna = coluna%n;
	}	
        return coluna;
    }
    
    /**
     * Gera um um vizinho a partir do indivíduo atual.
     * 
     * Aplica uma pertubação no indivíduo atual gerando um novo(vizinho).
     * 
     * @param R Um indíviuo que deve ser modificado.
     * @return Um novo indivíduo vizinho a R.
     */
    public static int[] geraVizinho(int R[]){
	
	int n = R.length;	
	int coluna = RANDOMICO.nextInt(n);
        int deslocamento = RANDOMICO.nextInt(n);
        
	 //Inicializa o vetor de retorno
        int[] novoIndividuo = new int[n];
        int i = 0;
        
        //Compia o tabuleiro
        while (i < n) {            
            novoIndividuo[i] = R[i];
            i = i + 1;
        }
        //novoIndividuo[coluna] = moverParaBaixo(n, novoIndividuo[coluna], deslocamento);       
        novoIndividuo[coluna] = deslocamento;       
			
	return novoIndividuo;
    }
    
    /**
     * Executa as iterações do Simulated Annealing.
     * 
     * Complexidade 
     *
     * @param n Quantidade de rainhas.
     * @param alfa Quantidade de iterações a ser executado simulação por anelamento.     
     * @param T0 Temperatura inicial.
     * @return Retorna o melhor indivíduo encontrado nas geracões
     */
    public static int[] simulatedAnnealing(int n, double alfa, double T0) {

        //Define o maior fitness pela quantidade rainhas posicionadas corretamente
        int melhorFitness = n;
        
        //temperatura inicial
        double T = T0;
                
        //Gera o candidato inicial
        int[] S = geraIndividuo(n);   
        
        //Calcula o custo do candidato inicial(S)          
        int C = fitness(S);
        
        //Enquanto a temperatura não é O ou não chegou no melhor
        while ((T > 0) && (C != melhorFitness)) {        
            
           //Gera o vizinho Si a partir de S
           int[] Si = geraVizinho(S);
        
           //Calcula o custo do vizinho Si   
           int Ci = fitness(Si);   
           
           //Calcula o delta do fitness
           int delta = Ci - C;
           
           //Se o vizinho tem custo menor 
           if (delta > 0){ 
                //Escolhe o vizinho (Si)
                S = Si;
                C = Ci;
            } else { 
                double P = Math.exp(delta/T);		
		double rand = Math.random();				
                // Escolhe o vizinho(Si)
		if(P > rand){ 
                    S = Si;
                    C = Ci;
		}
            }
            //Atualiza a temperatura
            T = T - alfa;      
        }
        //Retorna o melhor indivíduo encontrado nas gerações   
        return S;                                                     
    }
        
    /**
     * Faz a chamada do algoritmo Genetético e apresenta as estatísticas.
     *
     * @param n Quantidade de rainhas.
     * @param alfa Quantidade de iterações a ser executado na simulação.     
     * @param T0 Percentual de probabilidade de mutação dos indivíduos.
     */
    public static void executaSimulatedAnnealing(int n, double alfa, double T0) {      
        
        //Guarda o melhor indivíduo
        //Procura o menor indivíduo
        int[] melhorIndividuo = simulatedAnnealing(n, alfa, T0);

        if (valida(melhorIndividuo)) {
           //Incrementa o contador de soluções
            solucoes = solucoes + 1;
            //System.out.println("Solucao encontrada em " + geracao + " geracoes");
            //System.out.println("Solucao = " + vetorToString(melhorIndividuo));
            //System.out.println("Fitness = " + fitness(melhorIndividuo));
            //System.out.println("Solucao");
             imprimeSolucao(melhorIndividuo);
        } else {
            //System.out.println("Solucao nao encontrada após " + geracao + " geracoes");
            //System.out.println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            //System.out.println("Fitness = " + fitness(melhorIndividuo));
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
                        
            //Repete o teste para as vezes especificadas no vetor
            for (int testeAtual = 1; testeAtual <= repeticoesTeste; testeAtual++) {                            
                //Zera o contador de solucoes
                solucoes = 0;
                
                //Executa o garbage collector (gc) antes de cada teste
                System.gc();

                //Início da execução
                long tempo = System.currentTimeMillis();

                //Parâmetros do Simulated Annealing
                //Fator redução temperatura
                double alfa = 0.0001;
                
                //Temperatura inicial
                double T0 = 22;

                //Executa a solução da simulação
                executaSimulatedAnnealing(n, alfa, T0);
                
                //Pega o tempo final do processamento da vez
                tempo = System.currentTimeMillis() - tempo;
                
                //Acumula o tempo do teste ao tempo final
                tempoAcumulado = tempoAcumulado + tempo;
                
                //Acumula a soluções do teste
                solucoesAcumulado = solucoesAcumulado + solucoes;                
                System.out.println("Resultado da " + testeAtual + "ª execução: " + tempo + " milisegundos" + " com " + solucoes + " soluções");
            }
            //Calcula a média do tempo
            double mediaTempo = tempoAcumulado / (double) repeticoesTeste;   
            
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

        System.out.println("Simulated Annealing");
        
        // Vetor contendo os problemas a serem processados.
        // Cada elemento define a ordem do tabuleiro e, consequentemente, a 
        // quantidade de rainhas a serem posicionadas.
        int[] listaProblemasASolucionar = {4, 6, 8, 10, 12, 14, 16, 18 , 20, 22};

        // Quantidade de repetições do processamento
        // Útil para fins estatísticos.
        int repeticoesTeste = 10;

        System.out.println("Executando N-Rainhas com " + repeticoesTeste + " repetições.\n\n");
        nRainhas(listaProblemasASolucionar, repeticoesTeste);
    }
}