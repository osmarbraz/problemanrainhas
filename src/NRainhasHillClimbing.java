
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
    private static int interacaoSolucao;

    //Gerador de número aleatórios
    private static final Random RANDOMICO = new Random();

    //Habilita ou desabilita a saída dos dados de impressao
    private static final boolean IMPRIMIRTABULEIRO = false;

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
     * @param R vetor das rainhas.
     */
    private static void imprimeSolucao(int[] R) {

        // Tamanho do Problema
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
        int[] novoIndividuo = new int[tamanhoIndividuo];
        int i = 0;
        //Gera os indivíduos de acordo com o tamanho do candidato
        while (i < tamanhoIndividuo) {
            //Gera um uma rainha aleatória
            novoIndividuo[i] = new Random().nextInt(tamanhoIndividuo);
            i = i + 1;
        }
        return novoIndividuo;
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
        for (int i = 0; i < n; i++) {
            //Verifica a quantidade de rainhas salvas
            if (validaPosicao(R, i)) {
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
     * Avalia todas as rainhas posicionadas.
     * 
     * @param R vetor das rainhas posicionadas. O elemento corresponde à
     * coluna e seu respectivo conteúdo corresponde à linha.
     * 
     * @return True ou False se existe alguma rainha em posição inválida.
     */
    public static boolean valida(int[] R) {               
        //Recupera a quantidade de rainhas
        int n = R.length;
        int cont = 0;
        //Verifica se todas as rainhas estão em posições validas
        for (int i = 0; i < n; i++) {         
            if (validaPosicao(R, i)==false) {
                cont = cont + 1;
            }       
        }        
        return (cont==0);
    }

    /**
     * Algoritmo que executa as interações do algoritmo Hill Climbing
     *
     * @param iteracoes Números de vezes a executar as interações no
     * algoritmo
     * @param n Quantidade de rainhas no tabuleiro
     * @return Retorna o melhor indivíduo encontrado nas interações
     */
    public static int[] hillClimbing(int iteracoes, int n) {
        //Gera o candidato inicial
        int[] candidato = geraIndividuo(n);
        //Calcula o custo do candidato inicial
        int custoCandidato = fitness(candidato);

        //Controla as interações 
        int i = 0;
        //Para se chegar no número máximo de interacoes ou achar a solução
        while ((i < iteracoes) && (valida(candidato) == false)) {
            // Gera o proximo candidato aleatoriamente
            int[] vizinho = mutacao(candidato);
            //Calcula o custo do novo vizinho
            int custoVizinho = fitness(vizinho);
            // Verifica se é maior que o anterior
            if (custoVizinho >= custoCandidato) {
                //Troca se o custo for maior
                candidato = vizinho;
            }
            //Avança para a próxima interação
            i = i + 1;
        }
        //Armazena a interação que encontrou a solução
        interacaoSolucao = i;
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

        if (valida(melhorIndividuo)) {
            //Incrementa o contador de soluções
            solucoes = solucoes + 1;
            //System.out.println("Solucao encontrada em " + interacaoSolucao + " interacoes");
            //System.out.println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            //System.out.println("Fitness  = " + fitness(melhorIndividuo));
        } else {
            //System.out.println("Solucao não encontrada em " + interacaoSolucao + " interacoes");
            //System.out.println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            //System.out.println("Fitness  = " + fitness(melhorIndividuo));
        }
        //System.out.println("Solucao:");
        //imprimeSolucao(melhorIndividuo);
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
                algoritmoHillClimbing(iteracoes, n);

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
        int[] listaProblemasASolucionar = {4, 6, 8, 10 ,12, 14};
        
        // Quantidade de repetições do processamento
        // Útil para fins estatísticos.
        int repeticoesTeste = 10;
                
        System.out.println("Executando N-Rainhas com " + repeticoesTeste + " repetições.\n"); 
        nRainhas(listaProblemasASolucionar, repeticoesTeste);

    }
}