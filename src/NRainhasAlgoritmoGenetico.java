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
    private static int qtdeRainha;
    private static int maiorFitness;
    
    //Gerador de número aleatórios
    private static final Random RANDOMICO = new Random();

    //Habilita ou desabilita a saida dos dados de impressão
    private static final boolean HABILITARIMPRESSAO = true;

    /**
     * Trata a saída de dados.
     *
     * @param string Uma string a ser impressa com uma nova linha após.
     */
    private static void println(String string) {
        if (!HABILITARIMPRESSAO) {
            System.out.println(string);
        }
    }

    /**
     * Trata a saída de dados.
     *
     * @param string Uma string a ser impressa.
     */
    private static void print(String string) {
        if (!HABILITARIMPRESSAO) {
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
     * Carrega a população inicial de indivíduos do AG.
     *
     * @param populacao Conjunto de indivíduos da população
     * @param tamanhoPopulacao quantidade de indivíduos da população
     * inicial
     */
    private static Set geraPopulacaoInicial(int tamanhoPopulacao ){
        Set populacao = new HashSet();
        while (populacao.size() < tamanhoPopulacao) {
            //Gera um individuo
            int[] individuo = gerarIndividuo();
            println("individuo=" + vetorToString(individuo));
            //Adiciona o novo individuo a populacao
            populacao.add(individuo);
        }
        return populacao;
    }

    /**
     * Imprime o tabuleiro da solução do problema das Rainhas
     *
     * @param rainhas vetor das rainhas
     */
    private static void imprime(int[] rainhas) {
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
     * Lógica do Algoritmo Genético mantendo os melhores na população. 
     * 
     * Retorna o melhor indivíduo da população.
     *
     * @param populacao População de individuos
     * @param probabilidadeMutacao Taxa de probabilidade de mutação da população
     */
    private static int[] proximaGeracao(Set populacao, double probabilidadeMutacao, int fitnessAtual) {
        
        //Conjunto de novos indivíduos da próxima geração
        Set novaPopulacao = new HashSet();
        //Gera a nova populacao com base na população anteior        
        int tamanhoPopulacao = populacao.size();
        //Gera os novos invidiuos para a nova geracao
        while (novaPopulacao.size() < tamanhoPopulacao) {
            //Seleciona o primeiro individuo para realizar o crossover
            int individuo1[] = selecionarIndividuo(populacao, null);
            //Seleciona o segundo individuo diferente do anterior para realiar o crossover
            int individuo2[] = selecionarIndividuo(populacao, individuo1);
            //Gera o crossover entre individuo1 e individuo2 para gerar u novo indivíduo do cruzamento
            int novoIndividuo[] = crossover(individuo1, individuo2);
            //Verifica a probabilidade de realizar mutacao no filho
            if (RANDOMICO.nextDouble() <= probabilidadeMutacao) {
                //So realiza a mutacao se o ffitness for pior que o atual
                int ffitness = avaliacaoIndividuo(novoIndividuo);
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
        //Um numero baixo para o menor fitness
        int melhorF = -1;
        for (int i = 0; i < pais.length; i++) {
            fitness[i] = avaliacaoIndividuo((int[]) pais[i]);
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
            fitness[i] = avaliacaoIndividuo((int[]) pop[i]);
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
     * @param individuo1 Indivíduo que fornece a 1a parte dos genes para o novo indivíduo
     * @param individuo2 Indivíduo que fornece a 2a parte dos genes para o novo indivíduo
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
     * @return Um individuo selecionado aleatoramente da população
     */
    private static int[] selecionarIndividuo(Set populacao, int[] individuoBase) {
        //Cria um vetor para receber um novo elemento
        int[] individuoSelecionado = new int[qtdeRainha];
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
     * @return um indivíduo da populacao com repetição de rainha.
     */
    private static int[] gerarIndividuo() {
        //Inicializa o vetor de retorno
        int[] ret = new int[qtdeRainha];

        int i = 0;
        //Gera os genes de individuo de acordo com o tamanho do gene
        while (i < qtdeRainha) {
            //Gera um uma rainha aleatória
            ret[i] = RANDOMICO.nextInt(qtdeRainha);
            i = i + 1;
        }
        return ret;
    }

    /**
     * NAO ESTA SENDO UTILIZADO
     * 
     * Gera um indivíduo com n posições de forma aleatória de acordo com a
     * quantidade de rainhas. 
     * 
     * Usa uma roleta para evitar indivíduos reptidos
     *
     * @return um indivíduo da população sem repetição de rainha.
     */
    private static int[] gerarIndividuoSemRepeticao() {
        //Inicializa o vetor de retorno
        int[] ret = new int[qtdeRainha];

        //Inicializa o vetor da roleta
        int[] roleta = new int[qtdeRainha];
        //Controla o tamanho da roleta
        int tamanhoRoleta = qtdeRainha;
        //Coloca as rainhas na roleta para serem sortedas
        for (int i = 0; i < qtdeRainha; i++) {
            //Soma 1 para que as rainhas comecem com 1
            roleta[i] = i;
        }
        int i = 0;
        //Gera os genes de individuo de acordo com o tamanho do gene
        while (i < qtdeRainha) {
            //Gera um numero aleatorio para selecionar um elemento da roleta
            int pos = RANDOMICO.nextInt(tamanhoRoleta);
            ret[i] = roleta[pos];

            //Retira elemento sortedo da roleta            
            for (int j = pos; j < tamanhoRoleta - 1; j++) {
                roleta[j] = roleta[j + 1];
            }
            //Decrementa a quantidade de elementos da roleta
            tamanhoRoleta = tamanhoRoleta - 1;

            i = i + 1;
        }
        return ret;
    }

    /**
     * Função de avaliacao do indivíduo, retorna a quantidade de rainhas a salvo.
     *
     * @param individuo Um indivíduo da população
     * @return a quantidade de rainhas salvas no indivíduo
     */
    public static int avaliacaoIndividuo(int[] individuo) {
        int ret = 0;
       
        // Verifica se as rainhas estao salvas
        // A quantidade rainhas não salvas retorno da função fitness
        for (int i = 0; i < qtdeRainha; i++) {
            if (validaPosicao(individuo, i)) {
                ret++;
            }
        }                    
        return ret;
    }
    
    /**
     * Valida se a k-ésima rainha posicionada está sob ataque.
     * 
     * Uma rainha está sob ataque se há outra rainha na mesma linha, coluna ou 
     * diagonal onde esta se encontra.
     * 
     * @param R o vetor das rainhas
     * @param k linha do vetor a ser analisa
     *
     * @return true se a k-ésima rainha não estiver sob ataque das demais já 
     * posicionadas
     */
    public static boolean validaPosicao(int[] R, int k) {
        
        //Para cada uma das rainhas anteriormente posicionadas:
        for (int i = 0; i < k; i++) {
            
            //Verifica se a rainha k está na mesma coluna da rainha i
            if (R[i] == R[k]) {
                return false;
            }
            
            // Verifica se a rainha k está na mesma diagonal da rainha i
            if ( Math.abs(R[i] - R[k]) == (k - i)) {
             return false;                
            }
        }
        
        // Se a posição é válida
        return true;        
    }
    
    /**
     * Executa as gerações do Algoritmo Genético
     * 
     * @param qRainha Quantidade de rainhas.
     * @param qtdeGeracoes Quantidade de gerações a ser executado o algoritmo genético.
     * @param tamanhoPopulacao Tamanho de população.
     * @param probabilidadeMutacao Percentual de probabilidade de mutação dos indivíduos.     
     */
    public static void algoritmoGenetico(int qRainha, int qtdeGeracoes, int tamanhoPopulacao, double probabilidadeMutacao) {

        //Define a quantidade rainhas        
        qtdeRainha = qRainha;
        //Define o maior fitness pela quantidade rainhas 
        maiorFitness = qtdeRainha;

        // gerar a populacao inicial com 10 individuos
        Set populacao = geraPopulacaoInicial(tamanhoPopulacao);
        
        int[] melhorIndividuo = null;
        int melhorFitness = 0;
        int fitness = 0;
        int geracao = 0;
        int contador = 0;
        
        do{            
            //Retorna o melhor indivíduo da população
            melhorIndividuo = proximaGeracao(populacao, probabilidadeMutacao, melhorFitness);
            //Retorna o fitness do melhor inidividuo da população
            fitness = avaliacaoIndividuo(melhorIndividuo);
            //Verifica se o fitness do melhor indivíduo é o melhor fitnesss
            if (fitness > melhorFitness) {
                probabilidadeMutacao = 0.10;
                contador = 0;
                melhorFitness = fitness;
            } else {
                contador++;
                //Se nao ocorrer aumento do fitness aumenta a probabilidade de mutação
                if (contador > 1000) {
                    probabilidadeMutacao = 0.30;
                } else if (contador > 2000) {
                    probabilidadeMutacao = 0.50;
                } else if (contador > 5000) {
                    //Limpa populacao
                    populacao.clear();
                    //Carrega uma nova população
                    populacao = geraPopulacaoInicial(tamanhoPopulacao);
                    probabilidadeMutacao = 0.10;
                    melhorFitness = -1;
                }
            }
            geracao = geracao + 1;
        // Ate que a geração atinja o maxiou ou alcance o maior fitness    
        }  while ((geracao < qtdeGeracoes) && (fitness != maiorFitness));
        
        //Estatisticas da execucao
        if (fitness == maiorFitness) {
            solucoes = solucoes + 1;
            println("Solucao encontrada em " + geracao + " geracoes");
            println("Solucao = " + vetorToString(melhorIndividuo));
            println("Fitness = " + avaliacaoIndividuo(melhorIndividuo));
        } else {
            println("Solucao nao encontrada após " + geracao + " geracoes");
            println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            println("Fitness = " + avaliacaoIndividuo(melhorIndividuo));            
        }
        println("Solucao");
        imprime(melhorIndividuo);
    }

    public static void main(String[] args) {

        System.out.println("Algoritmo Genetico");
        
        //Especifica a quantidade de rainhas serem testadas
        int qtdeRainhasTeste[] = {4};
        //Especifica o número de vezes a se realizado com cada qtde de rainhas
        int repeticoesTeste[] = {1};

        //Declara o tempo total do teste
        double tempoTeste = 0;
        
        //Realiza os testes para as quantidades das rainhas especificadas no vetor
        for (int qtdeR = 0; qtdeR < qtdeRainhasTeste.length; qtdeR++) {

            int qRainha = qtdeRainhasTeste[qtdeR];            

            //Realiza a repetição do teste para a quantidade de rainhas    
            for (int qtdeT = 0; qtdeT < repeticoesTeste.length; qtdeT++) {

                println("Execuntando com " + qRainha + " rainhas por " + repeticoesTeste[qtdeT] + " vezes.");
                
                //Zera o numero de solucoes
                solucoes = 0;

                //Declara o tempo final da repetição
                long tempoFinal = 0;

                //Repete o teste para as vezes especificadas no vetor
                for (int qtdeV = 0; qtdeV < repeticoesTeste[qtdeT]; qtdeV++) {

                    //Executa o gc antes de cada teste
                    System.gc();
                    
                    //Zera o tempo de início da vez
                    long tempo = 0;
                    
                    //Pega o tempo corrente
                    tempo = System.currentTimeMillis();

                    //Parâmetros do Algoritmo Genético
                    //Quantidade de gerações
                    int qtdeGeracoes = 300000;
                    //Tamanho da populacao
                    int tamanhoPopulacao = 20;
                    //Probabilidade de mutação dos individuos
                    double probabilidadeMutacao = 0.15;                   
                                        
                    //Executa a solução do algoritmo
                    algoritmoGenetico(qRainha, qtdeGeracoes, tamanhoPopulacao, probabilidadeMutacao);

                    //Pega o tempo final do processamento da vez
                    tempo = System.currentTimeMillis() - tempo;
                    //Acumula o tempo do teste ao tempo final
                    tempoFinal = tempoFinal + tempo;
                }
                //Calcula a média do tempo
                double mediaTempo = tempoFinal / repeticoesTeste[qtdeT];
                System.out.println("O tempo medio para " + qRainha + " rainhas, executando " + repeticoesTeste[qtdeT] + " é vezes é " + mediaTempo + " milisegundos com " + solucoes + " solucoes em " + repeticoesTeste[qtdeT] + " repeticoes");
                tempoTeste = tempoTeste + mediaTempo;
            }
        }
        System.out.println("O tempo total do teste e " + tempoTeste + " milisegundos.");
    }
}