/**
 * @author Ana Paula, Osmar e Samuel
 *
 * Forca bruta.
 * 
 * O programa utiliza o método da força bruta para gerar todas as soluções do problema.
 * Utiliza um vetor para armazenar as posições das rainhas.
 *
 */
public class NRainhasForcaBruta {

     /**
     * Quantidade de solucoes encontradas ao final do algoritmo
     */
    private static int totalSolucoes;

    /**
     * Controla impressão do tabuleiro
     */
     private static final boolean IMPRIMIRTABULEIRO = false;

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
     * *************************************************
     * A função recursiva do método força bruta. 
     * 
     * Cada instância é responsável por posicionar uma rainha na primeira linha 
     * válida da coluna em análise, ou seja, sem que a mesma esteja sob ataque. 
     * 
     * Se uma rainha está em uma posição válida, então a mesma é posicionada e, 
     * recursivamente, as rainhas seguintes são posicionadas.
     *
     * Ma
     * 
     * @param R vetor onde as rainhas serão inseridas
     * @param k coordenada da linha corrente onde a rainhas devera ser inserida
     */  
     public static void forcaBruta(int[] R, int k) {

        //Recupera a quantidade de rainhas
        int n = R.length;

        //Se k e igual da quantidade rainhas cheguei no final da linha
        if (k == n) {  
            //Avalia todas as rainhas colocadas 
            if (valida(R)) {
                imprimeSolucao(R);
           }
        } else {
            //Percorre o vetor de rainhas           
            for (int i = 0; i < n; i++) {
                //Avança para próxima linha (k=k+1)                                   
                R[k] = i;
                forcaBruta(R, k + 1);                
            }
        }
    }

    /**
     * Imprime as soluções: tabuleiro e o posicionamento das rainhas.
     *
     * @param R
     */
    private static void imprimeSolucao(int[] R) {

        // Tamanho do Problema
        int n = R.length;

        totalSolucoes++;

        if (IMPRIMIRTABULEIRO) {
            
            System.out.println(" Solução número " + totalSolucoes + ":");
            
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
     * Chamada do algoritmo.
     * 
     * Provê o resultado da execução para cada problema resolvido, imprimindo em 
     * tela detalhes estatísticos para cada caso e tempo global.
     * 
     * @param listaProblemasASolucionar vetor contendo os problemas a serem executados.
     * @param repeticoesTeste quantidade de repetições para cada problema.
    */
    private static void nRainhas(int[] listaProblemasASolucionar, int repeticoesTeste) {
        
        double tempoTotalDeTeste = 0;
        double mediaTempo;
        long tempoAcumulado;
        long tempo;
        
        //Realiza os testes para as quantidades das rainhas especificadas no vetor
        for (int problemaAtual = 0; problemaAtual < listaProblemasASolucionar.length; problemaAtual++) {

            int n = listaProblemasASolucionar[problemaAtual];
            int rainhas[] = new int[n];
            
            System.out.println("-----------------------------------------------------------");
            System.out.println("Para " + n + " Rainhas \n"); 
            
            tempoAcumulado = 0;
            
            //Repete o teste para as vezes especificadas no vetor
            for (int testeAtual = 1; testeAtual <= repeticoesTeste; testeAtual++) {
            
                //Zera o numero de solucoes
                totalSolucoes = 0;
                
                //Executa o garbage collector (gc) antes de cada teste
                System.gc();

                //Início da execução
                tempo = System.currentTimeMillis();

                forcaBruta(rainhas, 0);

                //Pega o tempo final do processamento da vez
                tempo = System.currentTimeMillis() - tempo;
                //Acumula o tempo do teste ao tempo final
                tempoAcumulado = tempoAcumulado + tempo;
                System.out.println("Resultado da " + testeAtual + "ª execução: " + tempo + " milisegundos");
            }

            mediaTempo = tempoAcumulado / repeticoesTeste;   

            System.out.println("\nSoluções...: " + totalSolucoes);
            System.out.println("Tempo Médio: " + mediaTempo + " milisegundos");
            System.out.println("Acumulado..: " + tempoAcumulado + " milisegundos");

            tempoTotalDeTeste = tempoTotalDeTeste + tempoAcumulado;
        }
        
        System.out.println("===========================================================");
        System.out.println("O tempo total do teste e " + tempoTotalDeTeste + " milisegundos.");
        System.out.println("===========================================================");
    }

    /**
     * Executa o algoritmo.
     * 
     * Informações relevantes:
     * 
     * listaProblemasASolucionar: vetor contendo os tamanhos a serem resolvidos.
     * repeticoesTeste: Quantidade de vezes que cada problema é solucionado.
     *
     * @param args
     */
    public static void main(String args[]) {
        
        // Vetor contendo os problemas a serem processados.
        // Cada elemento define a ordem do tabuleiro e, consequentemente, a 
        // quantidade de rainhas a serem posicionadas.
        int[] listaProblemasASolucionar = {4, 6, 8, 10 ,12, 14};
        
        // Quantidade de repetições do processamento
        // Útil para fins estatísticos.
        int repeticoesTeste = 1;
        
        System.out.println("Força Bruta");
        System.out.println("Executando N-Rainhas com " + repeticoesTeste + " repetições.\n"); 
        nRainhas(listaProblemasASolucionar, repeticoesTeste);

    }
}
