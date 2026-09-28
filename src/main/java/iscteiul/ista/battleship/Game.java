package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma partida do jogo Batalha Naval (Battleship).
 * Gerencia a frota, os disparos efetuados pelos jogadores e as estatísticas
 * do jogo (tiros válidos, inválidos, repetidos, acertos e navios afundados).
 *
 * @author fba
 */
public class Game implements IGame {

    /**
     * A frota de navios associada a esta partida.
     */
    private IFleet fleet;

    /**
     * Lista de posições onde já foram efetuados disparos válidos.
     */
    private List<IPosition> shots;

    /**
     * Contador de disparos efetuados fora dos limites do tabuleiro.
     */
    private Integer countInvalidShots;

    /**
     * Contador de disparos efetuados em posições previamente atingidas.
     */
    private Integer countRepeatedShots;

    /**
     * Contador de disparos válidos que atingiram algum navio.
     */
    private Integer countHits;

    /**
     * Contador de navios que foram completamente afundados.
     */
    private Integer countSinks;

    /**
     * Constrói uma nova partida de Batalha Naval com a frota especificada.
     * Inicializa os contadores de estatísticas e a lista de disparos.
     *
     * @param fleet A frota de navios a ser utilizada no jogo.
     */
    public Game(IFleet fleet) {
        shots = new ArrayList<>();
        countInvalidShots = 0;
        countRepeatedShots = 0;
        countHits = 0;
        countSinks = 0;
        this.fleet = fleet;
    }

    /**
     * Realiza um disparo na posição especificada.
     * <p>
     * Se o tiro for inválido ou repetido, incrementa os respetivos contadores.
     * Se for um tiro novo e válido, regista o disparo na frota e verifica se
     * atingiu ou afundou algum navio.
     * </p>
     *
     * @param pos A posição {@link IPosition} onde o disparo é direcionado.
     * @return O navio {@link IShip} afundado se o tiro causar o seu afundamento;
     *         {@code null} se o tiro falhar, for inválido/repetido ou apenas atingir um navio sem o afundar.
     */
    @Override
    public IShip fire(IPosition pos) {
        if (!validShot(pos)) {
            countInvalidShots++;
        } else { // tiro válido!
            if (repeatedShot(pos)) {
                countRepeatedShots++;
            } else {
                shots.add(pos);
                IShip s = fleet.shipAt(pos);
                if (s != null) {
                    s.shoot(pos);
                    countHits++;
                    if (!s.stillFloating()) {
                        countSinks++;
                        return s;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Obtém a lista de todas as posições onde foram efetuados disparos válidos.
     *
     * @return Uma lista de objetos {@link IPosition} correspondentes aos tiros válidos.
     */
    @Override
    public List<IPosition> getShots() {
        return shots;
    }

    /**
     * Obtém o número total de disparos repetidos efetuados durante a partida.
     *
     * @return O número de tiros repetidos.
     */
    @Override
    public int getRepeatedShots() {
        return this.countRepeatedShots;
    }

    /**
     * Obtém o número total de disparos inválidos (fora do tabuleiro) efetuados.
     *
     * @return O número de tiros inválidos.
     */
    @Override
    public int getInvalidShots() {
        return this.countInvalidShots;
    }

    /**
     * Obtém o número total de disparos que atingiram com sucesso um navio.
     *
     * @return O número de acertos (hits).
     */
    @Override
    public int getHits() {
        return this.countHits;
    }

    /**
     * Obtém o número total de navios que já foram afundados.
     *
     * @return O número de navios afundados.
     */
    @Override
    public int getSunkShips() {
        return this.countSinks;
    }

    /**
     * Obtém o número de navios da frota que ainda se encontram a flutuar.
     *
     * @return O número de navios restantes.
     */
    @Override
    public int getRemainingShips() {
        List<IShip> floatingShips = fleet.getFloatingShips();
        return floatingShips.size();
    }

    /**
     * Verifica se a posição fornecida se encontra dentro dos limites válidos do tabuleiro.
     *
     * @param pos A posição {@link IPosition} a validar.
     * @return {@code true} se a posição estiver dentro do tabuleiro; {@code false} caso contrário.
     */
    private boolean validShot(IPosition pos) {
        return (pos.getRow() >= 0 && pos.getRow() <= Fleet.BOARD_SIZE && pos.getColumn() >= 0
                && pos.getColumn() <= Fleet.BOARD_SIZE);
    }

    /**
     * Verifica se a posição fornecida já foi alvo de um disparo anterior.
     *
     * @param pos A posição {@link IPosition} a verificar.
     * @return {@code true} se o tiro for repetido; {@code false} caso contrário.
     */
    private boolean repeatedShot(IPosition pos) {
        for (int i = 0; i < shots.size(); i++) {
            if (shots.get(i).equals(pos)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Desenha e imprime na consola a representação do tabuleiro de jogo, marcando 
     * as posições indicadas com um determinado carater.
     *
     * @param positions A lista de posições {@link IPosition} a serem assinaladas.
     * @param marker    O carater a ser utilizado para representar as posições (ex: 'X', '#').
     */
    public void printBoard(List<IPosition> positions, Character marker) {
        char[][] map = new char[Fleet.BOARD_SIZE][Fleet.BOARD_SIZE];

        for (int r = 0; r < Fleet.BOARD_SIZE; r++) {
            for (int c = 0; c < Fleet.BOARD_SIZE; c++) {
                map[r][c] = '.';
            }
        }

        for (IPosition pos : positions) {
            map[pos.getRow()][pos.getColumn()] = marker;
        }

        for (int row = 0; row < Fleet.BOARD_SIZE; row++) {
            for (int col = 0; col < Fleet.BOARD_SIZE; col++) {
                System.out.print(map[row][col]);
            }
            System.out.println();
        }
    }

    /**
     * Imprime na consola o tabuleiro de jogo exibindo as posições dos disparos 
     * válidos que já foram efetuados, marcados com o carater 'X'.
     */
    public void printValidShots() {
        printBoard(getShots(), 'X');
    }

    /**
     * Imprime na consola o tabuleiro de jogo exibindo todas as posições ocupadas 
     * pelos navios da frota, marcadas com o carater '#'.
     */
    public void printFleet() {
        List<IPosition> shipPositions = new ArrayList<IPosition>();

        for (IShip s : fleet.getShips()) {
            shipPositions.addAll(s.getPositions());
        }

        printBoard(shipPositions, '#');
    }
}
