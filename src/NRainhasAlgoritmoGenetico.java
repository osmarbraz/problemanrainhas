
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Ana Paula, Osmar e Samuel
 *
 */
public class NRainhasAlgoritmoGenetico {

    /**
     * Atributo do numero de soluções encontradas ao final do algoritmo
     */
    private static int solucoes;
    //private static int qtdeRainha;
    private static int maiorFitness;

    //Gerador de número aleatórios
    private static final Random RANDOMICO = new Random();

    //Habilita ou desabilita a saida dos dados de impressão
    private static final boolean IMPRIMIRTABULEIRO = false;
    
    /**
     * Carrega a população inicial de indivíduos do AG.
     *     
     * @param p quantidade de indivíduos da população inicial
     * @param n tamanho do indivíduo a ser gerado
     * @return Um conjunto de indivíduos gerados aleatóriamente
     */
    private static Set geraPopulacaoInicial(int p, int n) {
        Set populacao = new HashSet();
        while (populacao.size() < p) {
            //Gera um individuo
            int[] individuo = gerarIndividuo(n);                        
            //Adiciona o novo individuo a populacao
            populacao.add(individuo);
        }
        return populacao;
    }

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
    private static int[] proximaGeracao(Set populacao, double mutacao, int fitnessAtual, int n) {

        //Conjunto de novos indivíduos da próxima geração
        Set novaPopulacao = new HashSet();
        //Gera a nova populacao com base na população anteior        
        int tamanhoPopulacao = populacao.size();
        //Gera os novos invidiuos para a nova geracao
        while (novaPopulacao.size() < tamanhoPopulacao) {
            //Seleciona o primeiro individuo para realizar o crossover
            int individuo1[] = selecionarIndividuo(populacao, null, n);
            //Seleciona o segundo individuo diferente do anterior para realiar o crossover
            int individuo2[] = selecionarIndividuo(populacao, individuo1, n);
            //Gera o crossover entre individuo1 e individuo2 para gerar u novo indivíduo do cruzamento
            int novoIndividuo[] = crossover(individuo1, individuo2);
            //Verifica a probabilidade de realizar mutacao no filho
            if (RANDOMICO.nextDouble() <= mutacao) {
                //So realiza a mutacao se o ffitness for pior que o atual
                int ffitness = fitness(novoIndividuo);
                if (ffitness <= fitnessAtual) {
                    novoIndividuo = mutacao(novoIndividuo);
                }
            }
            //Adiciona o filho ao conjunto
            novaPopulacao.add(novoIndividuo);
        }
        // Adicionar dois dos melhores pais a população
        Object[] pais = populacao.toArray();
        int[] fitness = new int[pais.length];
        //Um numero baixo para o melhor fitness
        int melhorF = -1;
        for (int i = 0; i < pais.length; i++) {
            fitness[i] = fitness((int[]) pais[i]);
            //Localiza o melhor fitness
            if (melhorF < fitness[i]) {
                melhorF = fitness[i];
            }
        }
        populacao.clear();
        while (populacao.size() < 2) {
            for (int i = 0; i < fitness.length; i++) {
                //Adiciona os melhores a população
                if (fitness[i] == melhorF) {
                    populacao.add((int[]) pais[i]);
                }
                //Se adicionou 2 e sai
                if (populacao.size() == 2) {
                    break;
                }
            }
            //Decrementa o fitness para pegar o próximo
            melhorF--;
        }
        novaPopulacao.addAll(populacao);

        //Guarda o melhor indivíduo
        int[] melhorIndividuo = null;
        //Procura o melhor indivíduo na nova população
        Object[] pop = novaPopulacao.toArray();
        fitness = new int[pop.length];
        melhorF = -1;
        for (int i = 0; i < fitness.length; i++) {
            fitness[i] = fitness((int[]) pop[i]);
            if (melhorF < fitness[i]) {
                melhorF = fitness[i];
                melhorIndividuo = (int[]) pop[i];
            }
        }
        populacao.clear();
        while (populacao.size() < tamanhoPopulacao) {
            if (melhorF < 0) {
                break;
            }
            for (int i = 0; i < fitness.length; i++) {
                if (fitness[i] == melhorF && populacao.size() < tamanhoPopulacao) {
                    populacao.add((int[]) pop[i]);
                }
            }
            melhorF--;
        }
        return melhorIndividuo;
    }

    /**
     * Realiza a mutação em um indivíduo de forma aleatória.
     *
     * @param individuo Um individuo a sofrer mutação
     * @return um indivíduo com a mutação
     */
    private static int[] mutacao(int[] individuo) {
        //Seleciona a posicao da mutacao
        int posicao = RANDOMICO.nextInt(individuo.length);
        //Novo valor para a posicao selecionado
        int novovalor = RANDOMICO.nextInt(individuo.length);
        //Realiza a mutação na posição com o novoValor
        individuo[posicao] = novovalor;
        return individuo;
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
    private static int[] crossover(int[] individuo1, int[] individuo2) {
        //Cria o filho resultante do crossover
        int[] novo = new int[individuo1.length];
        //Seleciona o ponto de crossover
        int posicao = RANDOMICO.nextInt(individuo1.length);
        //Copia os genes do inicio ate a posicao do individuo1 para o filho
        for (int i = 0; i < posicao; i++) {
            novo[i] = individuo1[i];
        }
        //Copia os genes da posicao ate o final do individuo2 para o filho
        for (int i = posicao; i < individuo2.length; i++) {
            novo[i] = individuo2[i];
        }
        return novo;
    }

    /**
     * Seleciona um indivíduo da população aleatoriamente.
     *
     * @param populacao Conjunto da população
     * @param individuoBase para ser utilizado para nao selecionar
     * @param n tamanho do individuo a ser gerado
     * @return Um individuo selecionado aleatoramente da população
     */
    private static int[] selecionarIndividuo(Set populacao, int[] individuoBase, int n) {
        //Cria um vetor para receber um novo elemento
        int[] individuoSelecionado = new int[n];
        //Transforma a populacao em um vetor de objetos
        Object[] tmp = populacao.toArray();
        //Enquanto for igual a individuoBase seleciona outro indivíduo
        while (java.util.Arrays.equals(individuoSelecionado, individuoBase)) {
            //Seleciona uma posicao da população
            int i = RANDOMICO.nextInt((populacao.size()));
            //Recupera o indivíduo sorteado
            individuoSelecionado = (int[]) tmp[i];
        }
        return individuoSelecionado;
    }

    /**
     * Gera um indivíduo com n posições de forma aleatória.
     *
     * Gera rainhas repetidas
     *
     * @param n Tamanho do indivíduo
     * @return um indivíduo da populacao com repetição de rainha
     */
    private static int[] gerarIndividuo(int n) {
        //Inicializa o vetor de retorno
        int[] ret = new int[n];

        int i = 0;
        //Gera os genes de individuo de acordo com o tamanho do gene
        while (i < n) {
            //Gera um uma rainha aleatória
            ret[i] = RANDOMICO.nextInt(n);
            i = i + 1;
        }
        return ret;
    }
    
   /**
     * Função de avaliação do indivíduo, retorna a quantidade de rainhas a
     * salvo.
     * 
     * @param R vetor das rainhas posicionadas. O elemento corresponde à
     * coluna e seu respectivo conteúdo corresponde à linha.
     * 
     * @return  A quantidade de rainhas salvas em R.
     */
    public static int fitness(int[] R) {
        //Recupera a quantidade de rainhas
        int n = R.length;        
        int cont = 0;        
        //Verifica se todas as rainhas estão em posições validas
        for (int k = 0; k < n; k++) {
            //Verifica a quantidade de rainhas salvas
            if (validaPosicao(R, k)) {
                cont = cont + 1;
            }
        }
        return cont;
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
     * Executa as gerações do Algoritmo Genético
     *
     * @param n Quantidade de rainhas.
     * @param geracoes Quantidade de gerações a ser executado o algoritmo genético.
     * @param p Tamanho de população.
     * @param mutacao Percentual de probabilidade de mutação dos indivíduos.
     */
    public static void algoritmoGenetico(int n, int geracoes, int p, double mutacao) {

        //Define o maior fitness pela quantidade rainhas 
        maiorFitness = n;

        // gerar a população inicial dos individuos
        Set populacao = geraPopulacaoInicial(p, n);

        int[] melhorIndividuo = null;
        int melhorFitness = 0;
        int fitness = 0;
        int geracao = 0;
        int cont = 0;

        do {
            //Retorna o melhor indivíduo da população
            melhorIndividuo = proximaGeracao(populacao, mutacao, melhorFitness, n);
            //Retorna o fitness do melhor inidividuo da população
            fitness = fitness(melhorIndividuo);
            //Verifica se o fitness do melhor indivíduo é o melhor fitnesss
            if (fitness > melhorFitness) {
                mutacao = 0.10;
                cont = 0;
                melhorFitness = fitness;
            } else {
                cont = cont + 1;
                //Se não ocorrer aumento do fitness aumenta a probabilidade de mutação
                if (cont > 1000) {
                    mutacao = 0.30;
                } else if (cont > 2000) {
                    mutacao = 0.50;
                } else if (cont > 5000) {
                    //Limpa populacao
                    populacao.clear();
                    //Carrega uma nova população
                    populacao = geraPopulacaoInicial(p, n);
                    mutacao = 0.10;
                    melhorFitness = -1;
                }
            }
            geracao = geracao + 1;
            // Até que a geracao atinja o maximo de geraces ou alcance o maior fitness    
        } while ((geracao < geracoes) && (fitness != maiorFitness));

        //Estatisticas da execucao
        if (fitness == maiorFitness) {
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

                //Parâmetros do Algoritmo Genético
                //Quantidade de gerações
                int qtdGeracoes = 300000;
                //Tamanho da populacao
                int p = 20;
                //Probabilidade de mutação dos individuos
                double mutacao = 0.15;

                //Executa a solução do algoritmo
                algoritmoGenetico(n, qtdGeracoes, p, mutacao);
                
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