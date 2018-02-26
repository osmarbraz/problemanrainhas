/**
 * @author Ana Paula, Osmar e Samuel
 *
 * Algoritmo Genético
 * 
 * O programa utiliza algoritmo Genético para gerar encontrar uma solução para 
 * um tabuleiro contendo n rainhas.
 * 
 */
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NRainhasAlgoritmoGenetico {

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
     * Carrega a população inicial de indivíduos do AG. 
     *     
     * @param p quantidade de indivíduos da população inicial
     * @param n tamanho do indivíduo a ser gerado
     * @return Um conjunto de indivíduos gerados aleatóriamente
     */
    private static Set geraPopulacaoInicial(int p, int n) {            // Theta(n)
        Set populacao = new HashSet();                                 // Theta(1)
        while (populacao.size() < p) {                                 // Theta(1) - p é uma constante 
            //Gera um individuo
            int[] individuo = geraIndividuo(n);                        // Theta(n)
            //Adiciona o novo individuo a populacao
            populacao.add(individuo);                                  // Theta(1) - p é uma constante
        }
        return populacao;                                              // Theta(1)
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
    private static void imprimeSolucao(int[] R) {                       // O(n^2)

        //Tamanho do Problema
        int n = R.length;                                               // Theta(1)

        if (IMPRIMIRTABULEIRO) {                                        // Theta(1)        
            
            System.out.println(" Solução número " + solucoes + ":");    // O(1)
            
            for (int i = 0; i < n; i++) {                               // O(n)
                for (int j = 0; j < n; j++) {                           // n * O(n)
                    //Posição ocupada
                    if (R[j] == i) {                                    // n^2 * O(1)
                        System.out.print(" " + i + " ");                // n^2 * O(1)
                    } else {                                            // 0
                        System.out.print(" . ");                        // n^2 * O(1) 
                    }
                }
                System.out.println(" ");                                // n* O(1)
            }
            System.out.println(" ");                                    // O(1)
        }
    }

    /**
     * Lógica do Algoritmo Genético mantendo os melhores na população.
     *
     * Retorna o melhor indivíduo da população.
     *
     * @param populacao População de individuos
     * @param mutacao Taxa de probabilidade de mutação da população
     * @param fitnessAtual Fitness da geração atual
     * @param n Tamanho dos indivíduos da população
     * @return O melhor indivíduo da população
     */
    private static int[] proximaGeracao(Set populacao, double mutacao, int fitnessAtual, int n) {          // O(n^2)

        //Conjunto de novos indivíduos da próxima geração
        Set novaPopulacao = new HashSet();                                                                 // Theta(1)
        //Gera a nova populacao com base na população anteior        
        int tamanhoPopulacao = populacao.size();                                                           // Theta(1)
        //Gera os novos invidiuos para a nova geracao
        while (novaPopulacao.size() < tamanhoPopulacao) {                                                  // Theta(1) - tamanhoPopulacao � uma constante
            //Seleciona o primeiro individuo para realizar o crossover
            int individuo1[] = selecionarIndividuo(populacao, null, n);                                    // Theta(1)
            //Seleciona o segundo individuo diferente do anterior para realiar o crossover
            int individuo2[] = selecionarIndividuo(populacao, individuo1, n);                              // Theta(1)
            //Gera o crossover entre individuo1 e individuo2 para gerar u novo indivíduo do cruzamento
            int novoIndividuo[] = crossover(individuo1, individuo2);                                       // Theta(n)                                
            //Verifica a probabilidade de realizar mutacao no filho
            if (RANDOMICO.nextDouble() <= mutacao) {                                                       // Theta(1)
                //So realiza a mutacao se o ffitness for pior que o atual
                int ffitness = fitness(novoIndividuo);                                                     // O(n^2)
                if (ffitness <= fitnessAtual) {                                                            // Theta(1)
                    novoIndividuo = mutacao(novoIndividuo);                                                // Theta(1)
                }
            }
            //Adiciona o filho ao conjunto
            novaPopulacao.add(novoIndividuo);                                                              // Theta(1)
        }
        // Adicionar dois dos melhores pais a população
        Object[] pais = populacao.toArray();                                                               // Theta(1)
        int[] fitness = new int[pais.length];                                                              // Theta(1)
        //Um numero baixo para o melhor fitness
        int melhorF = -1;                                                                                  // Theta(1)
        for (int i = 0; i < pais.length; i++) {                                                            // Theta(1) - Quantidade de pais, constante
            fitness[i] = fitness((int[]) pais[i]);                                                         // O(n^2)
            //Localiza o melhor fitness
            if (melhorF < fitness[i]) {                                                                    // Theta(1)
                melhorF = fitness[i];                                                                      // O(1)
            }
        }
        populacao.clear();                                                                                 // Theta(1)
        while (populacao.size() < 2) {                                                                     // Theta(1)
            for (int i = 0; i < fitness.length; i++) {                                                     // Theta(1)
                //Adiciona os melhores a população
                if (fitness[i] == melhorF) {                                                               // Theta(1)
                    populacao.add((int[]) pais[i]);                                                        // O(1)
                }
                //Se adicionou 2 e sai
                if (populacao.size() == 2) {                                                               // Theta(1)
                    break;                                                                                 // O(1)
                }
            }
            //Decrementa o fitness para pegar o próximo
            melhorF--;                                                                                     // Theta(1)
        }
        novaPopulacao.addAll(populacao);                                                                   // Theta(1)

        //Guarda o melhor indivíduo
        int[] melhorIndividuo = null;                                                                      // Theta(1)
        //Procura o melhor indivíduo na nova população
        Object[] pop = novaPopulacao.toArray();                                                            // Theta(1)
        fitness = new int[pop.length];                                                                     // Theta(1)
        melhorF = -1;                                                                                      // Theta(1)
        for (int i = 0; i < fitness.length; i++) {                                                         // Theta(1)
            fitness[i] = fitness((int[]) pop[i]);                                                          // Theta(1)
            if (melhorF < fitness[i]) {                                                                    // Theta(1)
                melhorF = fitness[i];                                                                      // O(1)
                melhorIndividuo = (int[]) pop[i];                                                          // O(1)
            }
        }
        populacao.clear();                                                                                 // Theta(1)
        while (populacao.size() < tamanhoPopulacao) {                                                      // Theta(1)
            if (melhorF < 0) {                                                                             // Theta(1)
                break;                                                                                     // O(1)
            }
            for (int i = 0; i < fitness.length; i++) {                                                     // Theta(1)
                if (fitness[i] == melhorF && populacao.size() < tamanhoPopulacao) {                        // Theta(1)
                    populacao.add((int[]) pop[i]);                                                         // O(1)
                }
            }
            melhorF--;                                                                                     // Theta(1)
        }
        return melhorIndividuo;                                                                            // Theta(1)
    }

    public static int moverParaBaixo(int n, int coluna, int deslocamento){      // Theta(1)             
	coluna = coluna + deslocamento;                                         // Theta(1)
	
        //Verifica se está acima dos limites
	if (coluna>=n){                                                         // Theta(1)
            coluna = coluna%n;                                                  // O(1)
	}	
        return coluna;                                                          // Theta(1)
    }
    
    /**
     * Realiza a mutação em um indivíduo de forma aleatória.
     *
     * @param individuo Um individuo a sofrer mutação
     * @return um indivíduo com a mutação
     */
    private static int[] mutacao(int[] individuo) {                             // Theta(1)
        int n = individuo.length;                                               // Theta(1)
        //Seleciona a posicao da mutacao
        int posicao = RANDOMICO.nextInt(individuo.length);                      // Theta(1)
        //Novo valor para a posicao selecionado
        int novovalor = RANDOMICO.nextInt(individuo.length);                    // Theta(1)
        //Realiza a mutação na posição com o novoValor
        individuo[posicao] = novovalor;                                         // Theta(1)
        //individuo[posicao] = moverParaBaixo(n,individuo[posicao],novovalor);
        return individuo;                                                       // Theta(1)
    }

    /**
     * Realiza o crossover entre dois individuos da população.
     *
     * @param individuo1 Indivíduo que fornece a 1a parte dos genes para o novo
     * indivíduo
     * @param individuo2 Indivíduo que fornece a 2a parte dos genes para o novo
     * indivíduo
     * @return um indivíduo resultante do crossover
     */
    private static int[] crossover(int[] individuo1, int[] individuo2) {        // Theta(n)
        //Cria o filho resultante do crossover
        int[] novo = new int[individuo1.length];                                // Theta(1)
        //Seleciona o ponto de crossover
        int posicao = RANDOMICO.nextInt(individuo1.length);                     // Theta(1)
        //Copia os genes do inicio ate a posicao do individuo1 para o filho
        for (int i = 0; i < posicao; i++) {                                     // Theta(n) - posição é o tamanho do indivíduo, ou seja, n
            novo[i] = individuo1[i];                                            // Theta(n)
        }
        //Copia os genes da posicao ate o final do individuo2 para o filho
        for (int i = posicao; i < individuo2.length; i++) {                     // Theta(n) - posição é o tamanho do indivíduo, ou seja, n
            novo[i] = individuo2[i];                                            // Theta(n)
        }
        return novo;                                                            // Theta(1)
    }

    /**
     * Seleciona um indivíduo da população aleatoriamente.
     *
     * @param populacao Conjunto da população
     * @param individuoBase para ser utilizado para nao selecionar
     * @param n tamanho do individuo a ser gerado
     * @return Um individuo selecionado aleatóriamente da população
     */
    private static int[] selecionarIndividuo(Set populacao, int[] individuoBase, int n) {   // Theta(1)
        //Cria um vetor para receber um novo elemento
        int[] individuoSelecionado = new int[n];                                            // Theta(1)
        //Transforma a populacao em um vetor de objetos
        Object[] tmp = populacao.toArray();                                                 // Theta(1)
        //Enquanto for igual a individuoBase seleciona outro indivíduo
        while (java.util.Arrays.equals(individuoSelecionado, individuoBase)) {              // Theta(1) - Sorteio é feito até que o novo indivíduo não seja igual ao atual a tend�ncia � não repetir o while
            //Seleciona uma posicao da população
            int i = RANDOMICO.nextInt((populacao.size()));                                  // Theta(1)
            //Recupera o indivíduo sorteado
            individuoSelecionado = (int[]) tmp[i];                                          // Theta(1)
        }
        return individuoSelecionado;                                                        // Theta(1)
    }

    /**
     * Gera um indivíduo com n posições de forma aleatória.
     *
     * Gera n rainhas aleatórias(com repetição) para um tabuleiro.
     *
     * @param n Tamanho do indivíduo
     * @return um indivíduo da populacao com repetição de rainha
     */
    private static int[] geraIndividuo(int n) {                          // Theta(n)
        //Inicializa o vetor de retorno
        int[] novoIndividuo = new int[n];                                // Theta(1)
        int i = 0;                                                       // Theta(1)
        //Gera os genes de individuo de acordo com o tamanho do gene
        while (i < n) {                                                  // Theta(n)
            //Gera um uma rainha aleatória
            novoIndividuo[i] = RANDOMICO.nextInt(n);                     // Theta(n)
            i = i + 1;                                                   // Theta(n)
        }
        return novoIndividuo;                                            // Theta(1)
    }        
    
   /**
     * Função de avaliação do indivíduo, retorna a quantidade de rainhas a
     * salvo.
     * 
     * Complexidade O(n^2)
     * 
     * @param R vetor das rainhas posicionadas. O elemento corresponde a
     * coluna e seu respectivo conteúdo corresponde a linha.
     * 
     * @return  A quantidade de rainhas salvas em R.
     */
    public static int fitness(int[] R) {                            // O(n^2)
        //Recupera a quantidade de rainhas
        int n = R.length;                                           // Theta(1)
        int cont = 0;                                               // Theta(1)
        //Verifica se todas as rainhas estão em posições validas
        for (int k = 0; k < n; k++) {                               // Theta(n)
            //Verifica a quantidade de rainhas salvas
            if (validaPosicao(R, k)) {                              // n * Theta(n)
                cont = cont + 1;                                    // n* O(1)
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
     * @param R vetor das rainhas posicionadas. O elemento corresponde a
     * coluna e seu respectivo conteúdo corresponde a linha.
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
     * @param R vetor das rainhas posicionadas. O elemento corresponde a
     * coluna e seu respectivo conteúdo corresponde a linha.
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
     * Executa as gerações do Algoritmo Genético.
     * 
     * Complexidade O(n^2)
     *
     * @param n Quantidade de rainhas.
     * @param geracoes Quantidade de gerações a ser executado o algoritmo genético.
     * @param p Tamanho de população.
     * @param mutacao Percentual de probabilidade de mutação dos indivíduos.
     * @return Retorna o melhor indivíduo encontrado nas gerações
     */
    public static int[] algoritmoGenetico(int n, int geracoes, int p, double mutacao) { // O(n^2)

        //Define o maior fitness pela quantidade rainhas posicionadas corretamente
        int maiorFitness = n;                                                       // Theta(1)

        // gerar a população inicial dos individuos
        Set populacao = geraPopulacaoInicial(p, n);                                     // Theta(n)

        //Armazena o melhor individuo de todas as gerações
        int[] melhorIndividuo = null;                                               // Theta(1)
        //Armazena o melhor fitness da geração atual
        int fitness = 0;                                                            // Theta(1)
        //Armazena o melhor fitness de todas as gerações
        int melhorFitness = 0;                                                      // Theta(1)
        //Conta o número de gerações
        int i = 0;                                                                  // Theta(1)
        int cont = 0;                                                               // Theta(1)
         
        do {                                                                        // 0
            //Retorna o melhor indivíduo da população
            melhorIndividuo = proximaGeracao(populacao, mutacao, melhorFitness, n); // O(n^2)
            //Retorna o fitness do melhor inidividuo da população
            fitness = fitness(melhorIndividuo);                                     // O(n^2)
            //Verifica se o fitness do melhor indivíduo é o melhor fitnesss
            if (fitness > melhorFitness) {                                          // Theta(1)
                mutacao = 0.10;                                                     // O(1)
                melhorFitness = fitness;                                            // O(1)
                cont = 0;                                                           // O(1)
            } else {                                                                // 0
                cont = cont + 1;                                                    // Theta(1)    
                //Se não ocorrer aumento do fitness aumenta a probabilidade de mutação
                if (cont > 1000) {                                                  // Theta(1)
                    mutacao = 0.30;                                                 // O(1)
                } else if (cont > 2000) {                                           // Theta(1)
                    mutacao = 0.50;                                                 // O(1)
                } else if (cont > 5000) {                                           // Theta(1)
                    //Limpa populacao                                           
                    populacao.clear();                                              // O(1)
                    //Carrega uma nova população        
		    populacao = geraPopulacaoInicial(p, n);                         // Theta(n)
                    mutacao = 0.10;                                                 // O(1)
                    melhorFitness = -1;                                             // O(1)
                }
            }
            i = i + 1;                                                              // Theta(1)
        // Até que a geracao atinja o maximo de geraces ou alcance o maior fitness    
        } while ((i < geracoes) && (melhorFitness != maiorFitness));                // Theta(1)
        //Retorna o melhor indivíduo encontrado nas gerações   
        return melhorIndividuo;                                                     // Theta(1)
    }
        
    /**
     * Faz a chamada do algoritmo Genético e apresenta as estatísticas.
     *
     * @param n Quantidade de rainhas.
     * @param geracoes Quantidade de gerações a ser executado o algoritmo genético.
     * @param p Tamanho de população.
     * @param mutacao Percentual de probabilidade de mutação dos indivíduos.
     */
    public static void executaAlgoritmoGenetico(int n, int geracoes, int p, double mutacao) { // O(n^2)   
        
        //Guarda o melhor indivíduo
        //Procura o menor indivíduo
        int[] melhorIndividuo = algoritmoGenetico(n, geracoes, p, mutacao);                   // O(n^2)

        if (valida(melhorIndividuo)) {                                                        // O(n^2)
           //Incrementa o contador de soluções
            solucoes = solucoes + 1;
            //System.out.println("Solucao encontrada em " + geracao + " geracoes");
            //System.out.println("Solucao = " + vetorToString(melhorIndividuo));
            //System.out.println("Fitness = " + fitness(melhorIndividuo));
            //System.out.println("Solucao");
             imprimeSolucao(melhorIndividuo);                                                 // O(n^2)
        } else {                                                                              // 0
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
    private static void nRainhas(int[] listaProblemasASolucionar, int repeticoesTeste) {                    // O(n^2)
   
        double tempoTotalDeTeste = 0;                                                                       // Theta(1)
        long tempoAcumulado = 0;                                                                            // Theta(1)
        long solucoesAcumulado = 0;                                                                         // Theta(1)
        
        //Realiza os testes para as quantidades das rainhas especificadas no vetor
        for (int problemaAtual = 0; problemaAtual < listaProblemasASolucionar.length; problemaAtual++) {    // Theta(1) -- ListaProblemasASolucionar.length � uma constante

            int n = listaProblemasASolucionar[problemaAtual];                                               // Theta(1)
            
            System.out.println("-----------------------------------------------------------");              // Theta(1)
            System.out.println("Para " + n + " Rainhas \n");                                                // Theta(1)
            
            //Zera o tempo da execução da iteração
            tempoAcumulado = 0;                                                                             // Theta(1)
            //Zera o contador de solucoes da iteração 
            solucoesAcumulado = 0;                                                                          // Theta(1)
                        
            //Repete o teste para as vezes especificadas no vetor
            for (int testeAtual = 1; testeAtual <= repeticoesTeste; testeAtual++) {                         // Theta(1) -- repeticoesTeste � uma constante
                //Zera o contador de solucoes
                solucoes = 0;                                                                               // Theta(1)
                
                //Executa o garbage collector (gc) antes de cada teste
                System.gc();                                                                                // ???

                //Início da execução
                long tempo = System.currentTimeMillis();                                                    // ???

                //Parâmetros do Algoritmo Genético
                //Quantidade de gerações
                int qtdGeracoes = 300000;                                                                   // Theta(1)
                //Tamanho da populacao
                int p = 20;                                                                                 // Theta(1)
                //Probabilidade de mutação dos individuos
                double mutacao = 0.05;                                                                      // Theta(1)

                //Executa a solução do algoritmo
                executaAlgoritmoGenetico(n, qtdGeracoes, p, mutacao);                                       // O(n^2)
                
                //Pega o tempo final do processamento da vez
                tempo = System.currentTimeMillis() - tempo;                                                 // ???                         
                
                //Acumula o tempo do teste ao tempo final
                tempoAcumulado = tempoAcumulado + tempo;                                                    // Theta(1)
                
                //Acumula a soluções do teste
                solucoesAcumulado = solucoesAcumulado + solucoes;                                           // Theta(1)
                System.out.println("Resultado da " + testeAtual + "ª execução: " + tempo + 
                                   " milisegundos" + " com " + solucoes + " soluções");                     // Theta(1)
            }
            //Calcula a média do tempo
            double mediaTempo = tempoAcumulado / (double) repeticoesTeste;                                  // Theta(1)   
            
            //Calcula a média de solucoes
            double mediaSolucoes = solucoesAcumulado / (double)repeticoesTeste;                             // Theta(1)

            System.out.println("\nSoluções Média: " + mediaSolucoes + " soluções");                         // Theta(1)
            System.out.println("Tempo Médio...: " + mediaTempo + " milisegundos");                          // Theta(1)
            System.out.println("Acumulado.....: " + tempoAcumulado + " milisegundos");                      // Theta(1)

            tempoTotalDeTeste = tempoTotalDeTeste + tempoAcumulado;                                         // Theta(1)
        }        
        System.out.println("===========================================================");                  // Theta(1)
        System.out.println("O tempo total do teste e " + tempoTotalDeTeste + " milisegundos.");             // Theta(1)
        System.out.println("===========================================================");                  // Theta(1)
    }

    public static void main(String[] args) {

        System.out.println("Algoritmo Genético");
        
        // Vetor contendo os problemas a serem processados.
        // Cada elemento define a ordem do tabuleiro e, consequentemente, a 
        // quantidade de rainhas a serem posicionadas.
        int[] listaProblemasASolucionar = {4, 6, 8, 10 ,12, 14, 16, 18, 20, 22};

        // Quantidade de repetições do processamento
        // Útil para fins estatísticos.
        int repeticoesTeste = 10;

        System.out.println("Executando N-Rainhas com " + repeticoesTeste + " repetições.\n\n");
        nRainhas(listaProblemasASolucionar, repeticoesTeste);
    }
}
