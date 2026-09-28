package iscteiul.ista.battleship;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe utilitária que contém a lógica de execução e os modos de teste (tarefas)
 * para o jogo Batalha Naval (Battleship).
 * <p>
 * Inclui rotinas para leitura e construção de navios, frotas, posições e simulação
 * de rodadas de disparos via consola.
 * </p>
 *
 * @author fba
 */
public class Tasks {

    /**
     * Logger para o registo de mensagens e eventos da aplicação.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Número de disparos efetuados por cada rodada de ataques ("rajada").
     */
    private static final int NUMBER_SHOTS = 3;

    /**
     * Mensagem de despedida apresentada ao encerrar a execução.
     */
    private static final String GOODBYE_MESSAGE = "Bons ventos!";

    /** Comando do utilizador para criar e inicializar uma nova frota. */
    private static final String NOVAFROTA = "nova";

    /** Comando do utilizador para encerrar a tarefa / desistir do jogo. */
    private static final String DESISTIR = "desisto";

    /** Comando do utilizador para efetuar uma rodada de disparos (rajada). */
    private static final String RAJADA = "rajada";

    /** Comando do utilizador para visualizar no mapa os tiros válidos efetuados. */
    private static final String VERTIROS = "ver";

    /** Comando do utilizador para exibir a posição real de todos os navios (batota). */
    private static final String BATOTA = "mapa";

    /** Comando do utilizador para consultar o estado atual da frota. */
    private static final String STATUS = "estado";


    /////////////////////////////////////////////////////////////////////////////
    // A partir daqui encontram-se tarefas de teste incremental da aplicação.
    /////////////////////////////////////////////////////////////////////////////

    /**
     * Tarefa A: Testa a criação individual de navios.
     * <p>
     * Lê sucessivos navios do scanner e, para cada um, lê um conjunto de posições 
     * e verifica se o navio as ocupa ou não.
     * </p>
     */
    public static void taskA() {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            Ship s = readShip(in);
            if (s != null) {
                for (int i = 0; i < NUMBER_SHOTS; i++) {
                    Position p = readPosition(in);
                    LOGGER.info("{} {}", p, s.occupies(p));
                }
            }
        }
    }

    /**
     * Tarefa B: Testa a construção e gestão básica de frotas.
     * <p>
     * Aceita comandos da consola para criar frotas ({@code "nova"}) ou verificar 
     * o seu estado ({@code "estado"}) até que o utilizador desista ({@code "desisto"}).
     * </p>
     */
    public static void taskB() {
        Scanner in = new Scanner(System.in);
        IFleet fleet = null;
        String command = in.next();
        while (!command.equals(DESISTIR)) {
            switch (command) {
                case NOVAFROTA:
                    fleet = buildFleet(in);
                    break;
                case STATUS:
                    if (fleet != null)
                        fleet.printStatus();
                    break;
                default:
                    LOGGER.info("Que comando é esse??? Repete lá ...");
            }
            command = in.next();
        }
        LOGGER.info(GOODBYE_MESSAGE);
    }

    /**
     * Tarefa C: Testa a criação de frotas adicionando o comando de visualização completa ("batota").
     * <p>
     * Permite criar a frota, consultar o estado e exibir o mapeamento direto no logger
     * através do comando {@code "mapa"}.
     * </p>
     */
    public static void taskC() {
        Scanner in = new Scanner(System.in);
        IFleet fleet = null;
        String command = in.next();
        while (!command.equals(DESISTIR)) {
            switch (command) {
                case NOVAFROTA:
                    fleet = buildFleet(in);
                    break;
                case STATUS:
                    if (fleet != null)
                        fleet.printStatus();
                    break;
                case BATOTA:
                    LOGGER.info(fleet);
                    break;
                default:
                    LOGGER.info("Que comando é esse??? Repete lá ...");
            }
            command = in.next();
        }
        LOGGER.info(GOODBYE_MESSAGE);
    }

    /**
     * Tarefa D: Simula o fluxo completo do jogo (combate).
     * <p>
     * Suporta a criação de frotas e jogos, consulta de estado, visualização da frota,
     * visualização de tiros válidos no tabuleiro e realização de rodadas de disparos ({@code "rajada"}).
     * </p>
     */
    public static void taskD() {
        Scanner in = new Scanner(System.in);
        IFleet fleet = null;
        IGame game = null;
        String command = in.next();
        while (!command.equals(DESISTIR)) {
            switch (command) {
                case NOVAFROTA:
                    fleet = buildFleet(in);
                    game = new Game(fleet);
                    break;
                case STATUS:
                    if (fleet != null)
                        fleet.printStatus();
                    break;
                case BATOTA:
                    if (fleet != null)
                        game.printFleet();
                    break;
                case RAJADA:
                    if (game != null) {
                        firingRound(in, game);

                        LOGGER.info("Hits: {} Inv: {} Rep: {} Restam {} navios.", game.getHits(), game.getInvalidShots(),
                                game.getRepeatedShots(), game.getRemainingShips());
                        if (game.getRemainingShips() == 0)
                            LOGGER.info("Maldito sejas, Java Sparrow, eu voltarei, glub glub glub...");
                    }
                    break;
                case VERTIROS:
                    if (game != null)
                        game.printValidShots();
                    break;
                default:
                    LOGGER.info("Que comando é esse??? Repete ...");
            }
            command = in.next();
        }
        LOGGER.info(GOODBYE_MESSAGE);
    }

    /**
     * Constrói uma frota completa a partir dos dados introduzidos pelo utilizador.
     *
     * @param in O {@link Scanner} utilizado para ler as definições dos navios.
     * @return A frota {@link Fleet} construída com os navios adicionados com sucesso.
     */
    static Fleet buildFleet(Scanner in) {
        assert in != null;

        Fleet fleet = new Fleet();
        int i = 0; // i representa o total de navios criados com sucesso

        while (i <= Fleet.FLEET_SIZE) {
            IShip s = readShip(in);
            if (s != null) {
                boolean success = fleet.addShip(s);
                if (success)
                    i++;
                else
                    LOGGER.info("Falha na criacao de {} {} {}", s.getCategory(), s.getBearing(), s.getPosition());
            } else {
                LOGGER.info("Navio desconhecido!");
            }
        }
        LOGGER.info("{} navios adicionados com sucesso!", i);
        return fleet;
    }

    /**
     * Lê os dados referentes a um navio da consola (tipo, posição inicial e orientação),
     * constrói o respetivo objeto e devolve-o.
     *
     * @param in O {@link Scanner} a utilizar para a leitura.
     * @return O navio {@link Ship} instanciado, ou {@code null} se o tipo/dados forem inválidos.
     */
    static Ship readShip(Scanner in) {
        String shipKind = in.next();
        Position pos = readPosition(in);
        char c = in.next().charAt(0);
        Compass bearing = Compass.charToCompass(c);
        return Ship.buildShip(shipKind, bearing, pos);
    }

    /**
     * Lê um par de coordenadas (linha e coluna) da consola e devolve a posição correspondente.
     *
     * @param in O {@link Scanner} a utilizar para a leitura.
     * @return A posição {@link Position} lida.
     */
    static Position readPosition(Scanner in) {
        int row = in.nextInt();
        int column = in.nextInt();
        return new Position(row, column);
    }

    /**
     * Executa uma rodada de ataques (constituída por 3 disparos) sobre a frota no contexto do jogo.
     *
     * @param in   O {@link Scanner} a utilizar para ler as posições dos tiros.
     * @param game O jogo {@link IGame} em curso no qual os tiros serão efetuados.
     */
    static void firingRound(Scanner in, IGame game) {
        for (int i = 0; i < NUMBER_SHOTS; i++) {
            IPosition pos = readPosition(in);
            IShip sh = game.fire(pos);
            if (sh != null)
                LOGGER.info("Mas... mas... {}s nao sao a prova de bala? :-(", sh.getCategory());
        }
    }
}