package iscteiul.ista;

import iscteiul.ista.battleship.Fleet;
import iscteiul.ista.battleship.Tasks;

/**
 * Classe principal da aplicação Discoveries Battleship Game.
 * Serves de ponto de entrada para a execução e teste das tarefas do jogo.
 *
 * @author britoeabreu
 * @author adrianolopes
 * @author miguelgoulao
 */
public class App
{
    /**
     * Ponto de entrada principal do programa.
     * Executa as tarefas configuradas para a simulação do jogo.
     *
     * @param args Argumentos da linha de comandos passados durante a execução.
     */
    public static void main( String[] args )
    {

        System.out.printf("\n***  Battleship Game ***\n");

        // Tasks.taskA();
        Tasks.taskB();
        //	Tasks.taskC();
        //	Tasks.taskD();
    }
}
