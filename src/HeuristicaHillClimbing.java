
import java.util.Random;

/**
 *
 * @author Ana Paula, Osmar e Samuel
 */
public class HeuristicaHillClimbing {

    /**
     * Atributo do número de soluções encontradas ao final do algoritmo
     */
    private static int solucoes;
    private static int interacaoSolucao;

    private static final Random RANDOMICO = new Random();

    //Habilita ou desabilida a saida dos dados de impressao
    private static final boolean DESABILITARIMPRESSAO = true;

    /**
     * Trata a saída de dados
     *
     * @param string
     */
    private static void println(String string) {
        if (!DESABILITARIMPRESSAO) {
            System.out.println(string);
        }
    }

    /**
     * Trata a saída de dados
     *
     * @param string
     */
    private static void print(String string) {
        if (!DESABILITARIMPRESSAO) {
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
     * Imprime o tabuleiro da solução do problema das Rainhas
     *
     * @param rainhas vetor das rainhas
     */
    private static void imprime(int[] rainhas) {
        for (int i = 0; i < rainhas.length; i++) {
            for (int j = 0; j < rainhas.length; j++) {
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
     * Realiza a mutação em um indivíduo de forma aleatória
     *
     * @param individuo Um indivíduo a sofrer mutação
     * @return um indivíduo com a mutacao
     */
    private static int[] mutacao(int[] individuo) {
        //Seleciona a posição da mutação
        int posicao = RANDOMICO.nextInt(individuo.length);
        //Novo valor para a posicao selecionado
        int novovalor = RANDOMICO.nextInt(individuo.length);
        //Realiza a mutação na posição com o novoValor
        individuo[posicao] = novovalor;
        return individuo;
    }

    /**
     * Carrega a população inicial de indivíduos
     *
     * @param tamanhoIndividuo quantidade de individuos do conjunto inicial
     */
    private static int[] geraPopulacaoInicial(int tamanhoIndividuo) {
        //Inicializa o vetor de retorno
        int[] ret = new int[tamanhoIndividuo];
        int i = 0;
        //Gera os indivíduos de acordo com o tamanho do candidato
        while (i < tamanhoIndividuo) {
            //Gera um uma rainha aleatória
            ret[i] = new Random().nextInt(tamanhoIndividuo);
            i = i + 1;
        }
        return ret;
    }

    /**
     * Função de avaliação do indivíduo, retorna a quantidade de rainhas a
     * salvo.
     *
     * @param individuo Uma solução a ser verificada
     * @return a quantidade de rainhas salvas no indivíduo
     */
    public static int funcaoFitness(int[] individuo) {
      
        // Verifica se as rainhas estao salvas
        // A quantidade rainhas na salvas e o retorno da função fitness
        int ret = 0;
        for (int i = 0; i < individuo.length; i++) {
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
        // Se a posição é válida
        return true;        
    }
   
    /**
     * Verifica se o indivíduo e uma solução do problema
     *
     * @param individuo Uma solução a ser verificada
     * @return true se o indivíduo e uma solução do problema
     */
    public static boolean verificaSolucao(int[] individuo) {
       
        //Verifica se todas as rainhas estao em posições validas
        int cont = 0;
        for (int i = 0; i < individuo.length; i++) {         
            if (validaPosicao(individuo, i)==false) {
                cont++;
            }       
        }        
        return (cont==0);
    }

    /**
     * Algoritmo que executa as interações do algoritmo Hill Climbing
     *
     * @param qtdeInteracoes Números de vezes a executar as interações no algoritmo
     * @param qtdeRainha Quantidade de rainhas no tabuleiro
     * @return Retorna o melhor indivíduo encontrado nas interações
     */
    public static int[] hillClimbing(int qtdeInteracoes, int qtdeRainha) {
        //Gera o candidato inicial
        int[] candidato = geraPopulacaoInicial(qtdeRainha);
        //Calcula o custo do candidato inicial
        int custoCandidato = funcaoFitness(candidato);

        //Controla as interações 
        int interacao = 0;
        //Pàra se chegar no número máximo de interacoes ou achar a solução
        while ((interacao < qtdeInteracoes) && (verificaSolucao(candidato) == false)) {
            // Gera o proximo candidato aleatoriamente
            int[] vizinho = mutacao(candidato);
            //Calcula o custo do novo vizinho
            int custoVizinho = funcaoFitness(vizinho);
            // Verifica se é maior que o anterior
            if (custoVizinho >= custoCandidato) {
                //Troca se o custo for maior
                candidato = vizinho;
            }
            //Avança para a próxima interação
            interacao = interacao + 1;
        }
        //Armazena a interação que encontrou a solução
        interacaoSolucao = interacao;
        //Retorna o candidato
        return candidato;
    }

    /**
     * Faz a chamada do algoritmo e Hill Climbine apresenta as estatisticas.
     * 
     * @param qtdeInteracoes
     * @param qtdeRainha 
     */    
    public static void algoritmoHillClimbing(int qtdeInteracoes, int qtdeRainha) {
        //Guarda em que interação ocorreu a solução
        interacaoSolucao = -1;
        //Guarda o melhor indivíduo

        //Procura o menor indivíduo
        int[] melhorIndividuo = hillClimbing(qtdeInteracoes, qtdeRainha);

        if (verificaSolucao(melhorIndividuo)) {
            solucoes = solucoes + 1;
            println("Solucao encontrada em " + interacaoSolucao + " interacoes");
            println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            println("Fitness  = " + funcaoFitness(melhorIndividuo));
        } else {
            println("Solucao nao encontrada em " + interacaoSolucao + " interacoes");
            println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            println("Fitness  = " + funcaoFitness(melhorIndividuo));
        }
        println("Solucao:");
        imprime(melhorIndividuo);
    }

    public static void main(String[] args) {

        System.out.println("HillClimbing");
        
        //Especifica a quantidade de rainhas serem testadas
        int qtdeRainhasTeste[] = {4, 6, 8, 10};
        //Especifica o numero de vezes a se realizado com cada qtde de rainhas
        int repeticoesTeste[] = {10};

        //Parâmetros do algoritmo genético
        //Quantidade de geracoes
        int qtdeIteracoes = 1000000;

        //Declara o tempo total do teste
        double tempoTeste = 0;

        //Realiza os testes para as quantidades das rainhas especificadas no vetor
        for (int qtdeR = 0; qtdeR < qtdeRainhasTeste.length; qtdeR++) {

            int qtdeRainha = qtdeRainhasTeste[qtdeR];

            //Realiza a repetição do teste para a quantidade de rainhas    
            for (int qtdeT = 0; qtdeT < repeticoesTeste.length; qtdeT++) {

                println("Execuntando com " + qtdeRainha + " rainhas por " + repeticoesTeste[qtdeT] + " vezes.");

                //Zera o número de soluções
                solucoes = 0;

                //Declara o tempo final da repetição
                long tempoFinal = 0;

                //Repete o teste para as vezes especificadas no vetor
                for (int qtdeV = 0; qtdeV < repeticoesTeste[qtdeT]; qtdeV++) {

                    //Executa o gc antes de cada teste
                    System.gc();

                    //Pega o tempo corrente
                    long tempo = System.currentTimeMillis();

                    //Executa a solução do algoritmo         
                    algoritmoHillClimbing(qtdeIteracoes, qtdeRainha);

                    //Pega o tempo final do processamento da vez
                    tempo = System.currentTimeMillis() - tempo;
                    //Acumula o tempo do teste ao tempo final
                    tempoFinal = tempoFinal + tempo;
                }
                //Calcula a média do tempo
                double mediaTempo = tempoFinal / repeticoesTeste[qtdeT];
                System.out.println("O tempo medio para " + qtdeRainha + " rainhas, executando " + repeticoesTeste[qtdeT] + " é vezes é " + mediaTempo + " milisegundos com " + solucoes + " solucoes em " + repeticoesTeste[qtdeT] + " repeticoes");
                tempoTeste = tempoTeste + mediaTempo;
            }
        }
        System.out.println("O tempo total do teste e " + tempoTeste + " milisegundos.");
    }
}