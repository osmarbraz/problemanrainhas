
import java.util.Random;

/**
 *
 * @author Ana Paula, Osmar e Samuel
 */
public class NRainhasHillClimbing {

    /**
     * Atributo do número de soluções encontradas ao final do algoritmo
     */
    private static indt totalSolucoes;
    private static int interacaoSolucao;

    //Gerador de número aleatórios
    private static final Random RANDOMICO = new Random();

    //Habilita ou desabilita a saída dos dados de impressao
    private static final boolean IMPRIMIRTABULEIRO = true;

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
     * Imprime as soluções: tabuleiro e o posicionamento das rainhas.
     *
     * @param posicaoRainhas
     */
    private static void imprimeSolucao(int[] posicaoRainhas) {

        // Tamanho do Problema
        int n = posicaoRainhas.length;

        totalSolucoes++;

        System.out.println(" Solução número " + totalSolucoes + ":");
        if (IMPRIMIRTABULEIRO) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
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
    }

    /**
     * Realiza a mutação em um indivíduo de forma aleatória
     *
     * @param individuo Um indivíduo a sofrer mutação
     * @return um indivíduo com a mutação
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
     * Gera um indivíduo aleatóriamente.
     *
     * @param tamanhoIndividuo O tamanho do indivíduo a ser gerado.
     * @return Um vetor com um novo indivíduo gerado aleatoriamente.
     */
    private static int[] geraIndividuo(int tamanhoIndividuo) {
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
            if (Math.abs(R[i] - R[k]) == (k - i)) {
                return false;
            }
        }
        // Se a posição é válida
        return true;
    }

    /**
     * Verifica se o indivíduo e uma solução do problema
     *
     * @param individuo Uma solução a ser verificada.
     * @return true se o indivíduo e uma solução do problema.
     */
    public static boolean verificaSolucao(int[] individuo) {

        //Verifica se todas as rainhas estão em posições validas
        int cont = 0;
        for (int i = 0; i < individuo.length; i++) {
            if (validaPosicao(individuo, i) == false) {
                cont++;
            }
        }
        return (cont == 0);
    }

    /**
     * Algoritmo que executa as interações do algoritmo Hill Climbing
     *
     * @param qtdeInteracoes Números de vezes a executar as interações no
     * algoritmo
     * @param qtdeRainha Quantidade de rainhas no tabuleiro
     * @return Retorna o melhor indivíduo encontrado nas interações
     */
    public static int[] hillClimbing(int qtdeInteracoes, int qtdeRainha) {
        //Gera o candidato inicial
        int[] candidato = geraIndividuo(qtdeRainha);
        //Calcula o custo do candidato inicial
        int custoCandidato = funcaoFitness(candidato);

        //Controla as interações 
        int interacao = 0;
        //Para se chegar no número máximo de interacoes ou achar a solução
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
     * Faz a chamada do algoritmo e HillClimbing apresenta as estatísticas.
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
            totalSolucoes = totalSolucoes + 1;
            System.out.println("Solucao encontrada em " + interacaoSolucao + " interacoes");
            System.out.println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            System.out.println("Fitness  = " + funcaoFitness(melhorIndividuo));
        } else {
            System.out.println("Solucao não encontrada em " + interacaoSolucao + " interacoes");
            System.out.println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            System.out.println("Fitness  = " + funcaoFitness(melhorIndividuo));
        }
        System.out.println("Solucao:");
        imprimeTabuleiro(melhorIndividuo);
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

        //Parâmetros do algoritmo genético
        //Quantidade de geracoes
        int qtdeIteracoes = 1000000;

        //Declara o tempo total do teste
        double tempoTeste = 0;

        //Realiza os testes para as quantidades das rainhas especificadas no vetor
        for (int problemaAtual = 0; problemaAtual < listaProblemasASolucionar.length; problemaAtual++) {

            int qtdeRainha = listaProblemasASolucionar[problemaAtual];

            //Realiza a repetição do teste para a quantidade de rainhas    
            for (int testeAtual = 0; testeAtual < repeticoesTeste; testeAtual++) {

                System.out.println("Execuntando com " + qtdeRainha + " rainhas por " + testeAtual + " vezes.");

                totalSolucoes = 0;

                long tempoFinal = 0;

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
            double mediaTempo = tempoFinal / repeticoesTeste;
            System.out.println("O tempo medio para " + qtdeRainha + " rainhas, executando " + repeticoesTeste + " é vezes é " + mediaTempo + " milisegundos com " + totalSolucoes + " solucoes em " + repeticoesTeste + " repeticoes");
            tempoTeste = tempoTeste + mediaTempo;
    }

    System.out.println("O tempo total do teste e " + tempoTeste + " milisegundos.");

}
    
    public static void main(String[] args) {

        // Vetor contendo os problemas a serem processados.
        // Cada elemento define a ordem do tabuleiro e, consequentemente, a 
        // quantidade de rainhas a serem posicionadas.
        int[] listaProblemasASolucionar = {4};
        
        // Quantidade de repetições do processamento
        // Útil para fins estatísticos.
        int repeticoesTeste = 1;
        
        System.out.println("HillC");
        System.out.println("Executando N-Rainhas com " + repeticoesTeste + " repetições.\n\n"); 
        nRainhas(listaProblemasASolucionar, repeticoesTeste);

    }
}
