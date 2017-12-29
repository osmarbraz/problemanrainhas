/**
 * @author Ana Paula, Osmar e Samuel
 *
 * Algoritmo Gen�tico
 * 
 * O programa utiliza algoritmo Gen�tico para gerar encontrar uma solu��o para 
 * um tabuleiro contendo n rainhas.
 * 
 */
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NRainhasAlgoritmoGenetico {

    /**
     * Armazena o n�mero de solu��es encontradas ao final do algoritmo
     */
    private static int solucoes;

    /**
     * Gerador de n�mero aleat�rios para o algoritmo
     */
    private static final Random RANDOMICO = new Random();

     /**
     * Habilita ou desabilita a saida dos dados de impress�o
     */
    private static final boolean IMPRIMIRTABULEIRO = false;
    
    /**
     * Carrega a popula��o inicial de indiv�duos do AG. 
     *     
     * @param p quantidade de indiv�duos da popula��o inicial
     * @param n tamanho do indiv�duo a ser gerado
     * @return Um conjunto de indiv�duos gerados aleat�riamente
     */
    private static Set geraPopulacaoInicial(int p, int n) {            // Theta(n)
        Set populacao = new HashSet();                                 // Theta(1)
        while (populacao.size() < p) {                                 // Theta(1) - p � uma constante 
            //Gera um individuo
            int[] individuo = geraIndividuo(n);                        // Theta(n)
            //Adiciona o novo individuo a populacao
            populacao.add(individuo);                                  // Theta(1) - p � uma constante
        }
        return populacao;                                              // Theta(1)
    }

    /**
     * Imprime as solu��es do tabuleiro.
     * 
     * Percorre o tabuleiro exibindo as posi��es ocupadas pelas rainhas.
     *
     * Complexidade O(n^2)
     * 
     * @param R vetor das rainhas.
     */
    private static void imprimeSolucao(int[] R) {                       // O(n^2)

        //Tamanho do Problema
        int n = R.length;                                               // Theta(1)

        if (IMPRIMIRTABULEIRO) {                                        // Theta(1)        
            
            System.out.println(" Solu��o n�mero " + solucoes + ":");    // O(1)
            
            for (int i = 0; i < n; i++) {                               // O(n)
                for (int j = 0; j < n; j++) {                           // n * O(n)
                    //Posi��o ocupada
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
     * L�gica do Algoritmo Gen�tico mantendo os melhores na popula��o.
     *
     * Retorna o melhor indiv�duo da popula��o.
     *
     * @param populacao Popula��o de individuos
     * @param mutacao Taxa de probabilidade de muta��o da popula��o
     * @param fitnessAtual Fitness da gera��o atual
     * @param n Tamanho dos indiv�duos da popula��o
     * @return O melhor indiv�duo da popula��o
     */
    private static int[] proximaGeracao(Set populacao, double mutacao, int fitnessAtual, int n) {          // O(n^2)

        //Conjunto de novos indiv�duos da pr�xima gera��o
        Set novaPopulacao = new HashSet();                                                                 // Theta(1)
        //Gera a nova populacao com base na popula��o anteior        
        int tamanhoPopulacao = populacao.size();                                                           // Theta(1)
        //Gera os novos invidiuos para a nova geracao
        while (novaPopulacao.size() < tamanhoPopulacao) {                                                  // Theta(1) - tamanhoPopulacao � uma constante
            //Seleciona o primeiro individuo para realizar o crossover
            int individuo1[] = selecionarIndividuo(populacao, null, n);                                    // Theta(1)
            //Seleciona o segundo individuo diferente do anterior para realiar o crossover
            int individuo2[] = selecionarIndividuo(populacao, individuo1, n);                              // Theta(1)
            //Gera o crossover entre individuo1 e individuo2 para gerar u novo indiv�duo do cruzamento
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
        // Adicionar dois dos melhores pais a popula��o
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
                //Adiciona os melhores a popula��o
                if (fitness[i] == melhorF) {                                                               // Theta(1)
                    populacao.add((int[]) pais[i]);                                                        // O(1)
                }
                //Se adicionou 2 e sai
                if (populacao.size() == 2) {                                                               // Theta(1)
                    break;                                                                                 // O(1)
                }
            }
            //Decrementa o fitness para pegar o pr�ximo
            melhorF--;                                                                                     // Theta(1)
        }
        novaPopulacao.addAll(populacao);                                                                   // Theta(1)

        //Guarda o melhor indiv�duo
        int[] melhorIndividuo = null;                                                                      // Theta(1)
        //Procura o melhor indiv�duo na nova popula��o
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
	
        //Verifica se est� acima dos limites
	if (coluna>=n){                                                         // Theta(1)
            coluna = coluna%n;                                                  // O(1)
	}	
        return coluna;                                                          // Theta(1)
    }
    
    /**
     * Realiza a muta��o em um indiv�duo de forma aleat�ria.
     *
     * @param individuo Um individuo a sofrer muta��o
     * @return um indiv�duo com a muta��o
     */
    private static int[] mutacao(int[] individuo) {                             // Theta(1)
        int n = individuo.length;                                               // Theta(1)
        //Seleciona a posicao da mutacao
        int posicao = RANDOMICO.nextInt(individuo.length);                      // Theta(1)
        //Novo valor para a posicao selecionado
        int novovalor = RANDOMICO.nextInt(individuo.length);                    // Theta(1)
        //Realiza a muta��o na posi��o com o novoValor
        individuo[posicao] = novovalor;                                         // Theta(1)
        //individuo[posicao] = moverParaBaixo(n,individuo[posicao],novovalor);
        return individuo;                                                       // Theta(1)
    }

    /**
     * Realiza o crossover entre dois individuos da popula��o.
     *
     * @param individuo1 Indiv�duo que fornece a 1a parte dos genes para o novo
     * indiv�duo
     * @param individuo2 Indiv�duo que fornece a 2a parte dos genes para o novo
     * indiv�duo
     * @return um indiv�duo resultante do crossover
     */
    private static int[] crossover(int[] individuo1, int[] individuo2) {        // Theta(n)
        //Cria o filho resultante do crossover
        int[] novo = new int[individuo1.length];                                // Theta(1)
        //Seleciona o ponto de crossover
        int posicao = RANDOMICO.nextInt(individuo1.length);                     // Theta(1)
        //Copia os genes do inicio ate a posicao do individuo1 para o filho
        for (int i = 0; i < posicao; i++) {                                     // Theta(n) - posicao � o tamanho do indiv�duo, ou seja, n
            novo[i] = individuo1[i];                                            // Theta(n)
        }
        //Copia os genes da posicao ate o final do individuo2 para o filho
        for (int i = posicao; i < individuo2.length; i++) {                     // Theta(n) - posicao � o tamanho do indiv�duo, ou seja, n
            novo[i] = individuo2[i];                                            // Theta(n)
        }
        return novo;                                                            // Theta(1)
    }

    /**
     * Seleciona um indiv�duo da popula��o aleatoriamente.
     *
     * @param populacao Conjunto da popula��o
     * @param individuoBase para ser utilizado para nao selecionar
     * @param n tamanho do individuo a ser gerado
     * @return Um individuo selecionado aleatoramente da popula��o
     */
    private static int[] selecionarIndividuo(Set populacao, int[] individuoBase, int n) {   // Theta(1)
        //Cria um vetor para receber um novo elemento
        int[] individuoSelecionado = new int[n];                                            // Theta(1)
        //Transforma a populacao em um vetor de objetos
        Object[] tmp = populacao.toArray();                                                 // Theta(1)
        //Enquanto for igual a individuoBase seleciona outro indiv�duo
        while (java.util.Arrays.equals(individuoSelecionado, individuoBase)) {              // Theta(1) - Sorteio � feito at� que o novo indiv�duo n�o seja igual ao atual a tend�ncia � n�o repetir o while
            //Seleciona uma posicao da popula��o
            int i = RANDOMICO.nextInt((populacao.size()));                                  // Theta(1)
            //Recupera o indiv�duo sorteado
            individuoSelecionado = (int[]) tmp[i];                                          // Theta(1)
        }
        return individuoSelecionado;                                                        // Theta(1)
    }

    /**
     * Gera um indiv�duo com n posi��es de forma aleat�ria.
     *
     * Gera n rainhas aleat�rias(com repeti��o) para um tabuleiro.
     *
     * @param n Tamanho do indiv�duo
     * @return um indiv�duo da populacao com repeti��o de rainha
     */
    private static int[] geraIndividuo(int n) {                          // Theta(n)
        //Inicializa o vetor de retorno
        int[] novoIndividuo = new int[n];                                // Theta(1)
        int i = 0;                                                       // Theta(1)
        //Gera os genes de individuo de acordo com o tamanho do gene
        while (i < n) {                                                  // Theta(n)
            //Gera um uma rainha aleat�ria
            novoIndividuo[i] = RANDOMICO.nextInt(n);                     // Theta(n)
            i = i + 1;                                                   // Theta(n)
        }
        return novoIndividuo;                                            // Theta(1)
    }        
    
   /**
     * Fun��o de avalia��o do indiv�duo, retorna a quantidade de rainhas a
     * salvo.
     * 
     * Complexidade O(n^2)
     * 
     * @param R vetor das rainhas posicionadas. O elemento corresponde �
     * coluna e seu respectivo conte�do corresponde � linha.
     * 
     * @return  A quantidade de rainhas salvas em R.
     */
    public static int fitness(int[] R) {                            // O(n^2)
        //Recupera a quantidade de rainhas
        int n = R.length;                                           // Theta(1)
        int cont = 0;                                               // Theta(1)
        //Verifica se todas as rainhas est�o em posi��es validas
        for (int k = 0; k < n; k++) {                               // Theta(n)
            //Verifica a quantidade de rainhas salvas
            if (validaPosicao(R, k)) {                              // n * Theta(n)
                cont = cont + 1;                                    // n* O(1)
            }
        }
        return cont;                                                // Theta(1)
    }

    /**
     * Valida se a k-�sima rainha posicionada est� sob ataque.
     * 
     * Uma rainha est� sob ataque se h� outra rainha na mesma linha, coluna ou 
     * diagonal onde esta se encontra.
     * 
     * Como as rainhas s�o adicionadas sempre na coluna seguinte, n�o h� necessi-
     * dade de validar conflitos na mesma coluna.
     * 
     * Complexidade Theta(k)
     * 
     * @param R vetor das rainhas posicionadas. O elemento corresponde �
     * coluna e seu respectivo conte�do corresponde � linha.
     * 
     * @param k linha do vetor a ser analisada
     *
     * @return true se a k-�sima rainha n�o estiver sob ataque das demais j� 
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
        // Posi��o v�lida
        return true;                                            // Theta(1)
    }
    
    /**
     * Avalia todas as rainhas posicionadas.
     * 
     * Chamada o m�todo valida posi��o para avalia��o cada posi��o do 
     * vetor de rainhas.
     * 
     * Complexidade O(n^2)
     * 
     * @param R vetor das rainhas posicionadas. O elemento corresponde �
     * coluna e seu respectivo conte�do corresponde � linha.
     * 
     * @return True ou False se existe alguma rainha em posi��o inv�lida.
     */
    public static boolean valida(int[] R) {                         // O(n^2)
        
        //Recupera a quantidade de rainhas
        int n = R.length;                                           // Theta(1)
        int cont = 0;                                               // Theta(1)
        //Verifica se todas as rainhas est�o em posi��es validas
        for (int k = 0; k < n; k++) {                               // Theta(n)
            if (validaPosicao(R, k)==false) {                       // n * O(n) = O(n^2)
                cont = cont + 1;                                    // O(1)    
            }       
        }        
        return (cont==0);                                           // Theta(1)
    }

    /**
     * Executa as gera��es do Algoritmo Gen�tico.
     * 
     * Complexidade O(n^2)
     *
     * @param n Quantidade de rainhas.
     * @param geracoes Quantidade de gera��es a ser executado o algoritmo gen�tico.
     * @param p Tamanho de popula��o.
     * @param mutacao Percentual de probabilidade de muta��o dos indiv�duos.
     * @return Retorna o melhor indiv�duo encontrado nas gerac�es
     */
    public static int[] algoritmoGenetico(int n, int geracoes, int p, double mutacao) { // O(n^2)

        //Define o maior fitness pela quantidade rainhas posicionadas corretamente
        int maiorFitness = n;                                                       // Theta(1)

        // gerar a popula��o inicial dos individuos
        Set populacao = geraPopulacaoInicial(p, n);                                     // Theta(n)

        //Armazena o melhor individuo de todas as gera��es
        int[] melhorIndividuo = null;                                                   // Theta(1)
        //Armazena o melhor fitness da gera��o atual
        int fitness = 0;                                                            // Theta(1)
        //Armazena o melhor fitness de todas as gera��es
        int melhorFitness = 0;                                                      // Theta(1)
        //Conta o n�mero de gera��es
        int i = 0;                                                                      // Theta(1)
        int cont = 0;                                                                   // Theta(1)
         
        do {                                                                            // 0
            //Retorna o melhor indiv�duo da popula��o
            melhorIndividuo = proximaGeracao(populacao, mutacao, melhorFitness, n);     // O(n^2)
            //Retorna o fitness do melhor inidividuo da popula��o
            fitness = fitness(melhorIndividuo);                                         // O(n^2)
            //Verifica se o fitness do melhor indiv�duo � o melhor fitnesss
            if (fitness > melhorFitness) {                                          // Theta(1)
                mutacao = 0.10;                                                     // O(1)
                melhorFitness = fitness;                                            // O(1)
                cont = 0;                                                           // O(1)
            } else {                                                                // 0
                cont = cont + 1;                                                    // Theta(1)    
                //Se n�o ocorrer aumento do fitness aumenta a probabilidade de muta��o
                if (cont > 1000) {                                                      // Theta(1)
                    mutacao = 0.30;                                                     // O(1)
                } else if (cont > 2000) {                                               // Theta(1)
                    mutacao = 0.50;                                                     // O(1)
                } else if (cont > 5000) {                                               // Theta(1)
                    //Limpa populacao                                           
                    populacao.clear();                                                  // O(1)
                    //Carrega uma nova popula��o        
                    populacao = geraPopulacaoInicial(p, n);                             // Theta(n)
                    mutacao = 0.10;                                                     // O(1)
                    melhorFitness = -1;                                                 // O(1)
                }
            }
            i = i + 1;                                                              // Theta(1)
        // At� que a geracao atinja o maximo de geraces ou alcance o maior fitness    
        } while ((i < geracoes) && (melhorFitness != maiorFitness));                // Theta(1)
        //Retorna o melhor indiv�duo encontrado nas gera��es   
        return melhorIndividuo;                                                         // Theta(1)
    }
        
    /**
     * Faz a chamada do algoritmo Genet�tico e apresenta as estat�sticas.
     *
     * @param n Quantidade de rainhas.
     * @param geracoes Quantidade de gera��es a ser executado o algoritmo gen�tico.
     * @param p Tamanho de popula��o.
     * @param mutacao Percentual de probabilidade de muta��o dos indiv�duos.
     */
    public static void executaAlgoritmoGenetico(int n, int geracoes, int p, double mutacao) { // O(n^2)   
        
        //Guarda o melhor indiv�duo
        //Procura o menor indiv�duo
        int[] melhorIndividuo = algoritmoGenetico(n, geracoes, p, mutacao);                   // O(n^2)

        if (valida(melhorIndividuo)) {                                                        // O(n^2)
           //Incrementa o contador de solu��es
            solucoes = solucoes + 1;
            //System.out.println("Solucao encontrada em " + geracao + " geracoes");
            //System.out.println("Solucao = " + vetorToString(melhorIndividuo));
            //System.out.println("Fitness = " + fitness(melhorIndividuo));
            //System.out.println("Solucao");
             imprimeSolucao(melhorIndividuo);                                                 // O(n^2)
        } else {                                                                              // 0
            //System.out.println("Solucao nao encontrada ap�s " + geracao + " geracoes");
            //System.out.println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            //System.out.println("Fitness = " + fitness(melhorIndividuo));
        }      
    }

    /**
     * Chamada do algoritmo.
     *
     * Prov� o resultado da execu��o para cada problema resolvido, imprimindo em
     * tela detalhes estat�sticos para cada caso e tempo global.
     *
     * @param listaProblemasASolucionar vetor contendo os problemas a serem
     * executados.
     * @param repeticoesTeste quantidade de repeti��es para cada problema.
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
            
            //Zera o tempo da execu��o da itera��o
            tempoAcumulado = 0;                                                                             // Theta(1)
            //Zera o contador de solucoes da itera��o 
            solucoesAcumulado = 0;                                                                          // Theta(1)
                        
            //Repete o teste para as vezes especificadas no vetor
            for (int testeAtual = 1; testeAtual <= repeticoesTeste; testeAtual++) {                         // Theta(1) -- repeticoesTeste � uma constante
                //Zera o contador de solucoes
                solucoes = 0;                                                                               // Theta(1)
                
                //Executa o garbage collector (gc) antes de cada teste
                System.gc();                                                                                // ???

                //In�cio da execu��o
                long tempo = System.currentTimeMillis();                                                    // ???

                //Par�metros do Algoritmo Gen�tico
                //Quantidade de gera��es
                int qtdGeracoes = 300000;                                                                   // Theta(1)
                //Tamanho da populacao
                int p = 20;                                                                                 // Theta(1)
                //Probabilidade de muta��o dos individuos
                double mutacao = 0.15;                                                                      // Theta(1)

                //Executa a solu��o do algoritmo
                executaAlgoritmoGenetico(n, qtdGeracoes, p, mutacao);                                       // O(n^2)
                
                //Pega o tempo final do processamento da vez
                tempo = System.currentTimeMillis() - tempo;                                                 // ???                         
                
                //Acumula o tempo do teste ao tempo final
                tempoAcumulado = tempoAcumulado + tempo;                                                    // Theta(1)
                
                //Acumula a solu��es do teste
                solucoesAcumulado = solucoesAcumulado + solucoes;                                           // Theta(1)
                System.out.println("Resultado da " + testeAtual + "� execu��o: " + tempo + 
                                   " milisegundos" + " com " + solucoes + " solu��es");                     // Theta(1)
            }
            //Calcula a m�dia do tempo
            double mediaTempo = tempoAcumulado / (double) repeticoesTeste;                                  // Theta(1)   
            
            //Calcula a m�dia de solucoes
            double mediaSolucoes = solucoesAcumulado / (double)repeticoesTeste;                             // Theta(1)

            System.out.println("\nSolu��es M�dia: " + mediaSolucoes + " solu��es");                         // Theta(1)
            System.out.println("Tempo M�dio...: " + mediaTempo + " milisegundos");                          // Theta(1)
            System.out.println("Acumulado.....: " + tempoAcumulado + " milisegundos");                      // Theta(1)

            tempoTotalDeTeste = tempoTotalDeTeste + tempoAcumulado;                                         // Theta(1)
        }        
        System.out.println("===========================================================");                  // Theta(1)
        System.out.println("O tempo total do teste e " + tempoTotalDeTeste + " milisegundos.");             // Theta(1)
        System.out.println("===========================================================");                  // Theta(1)
    }

    public static void main(String[] args) {

        System.out.println("Algoritmo Gen�tico");
        
        // Vetor contendo os problemas a serem processados.
        // Cada elemento define a ordem do tabuleiro e, consequentemente, a 
        // quantidade de rainhas a serem posicionadas.
        int[] listaProblemasASolucionar = {4, 6, 8, 10 ,12, 14, 16, 18, 20, 22};

        // Quantidade de repeti��es do processamento
        // �til para fins estat�sticos.
        int repeticoesTeste = 10;

        System.out.println("Executando N-Rainhas com " + repeticoesTeste + " repeti��es.\n\n");
        nRainhas(listaProblemasASolucionar, repeticoesTeste);
    }
}
