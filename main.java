import java.util.Scanner;

class TicTacToe {

    static int game_count = 0;

    public static void main(String[] args) {

        GameOptions options = new GameOptions();

        while (true) {
            menu(options);
            play(options);

        } // while

    }

    public static final void println(Object obj) {
        System.out.println(obj);
    }

    public static final void print(Object obj) {
        System.out.print(obj);
    }

    public static void menu(GameOptions options) {
        char choice = 'N';
        Scanner scan = new Scanner(System.in);

        if (game_count > 0) {

            print("\nKeep playing, (Y, N)?: ");
            choice = scan.next().toUpperCase().charAt(0);

            if (choice != 'Y')
                bye();
        }

        game_count++;
        println("Enter (Q) at any prompt to quit");

        do {
            print("Enter a mark (X, O): ");
            options.userMark = scan.next().toUpperCase().charAt(0);

            if (options.userMark == 'Q') {
                bye();
            }

        } while (options.userMark != 'X' &&
                options.userMark != 'O' &&
                options.userMark != 'Q');
        //
        do {
            print("Enter (Y) to play first, (N) for computer: ");
            options.starter = scan.next().toUpperCase().charAt(0);

            if (options.starter == 'Q') {
                bye();
            }

        } while (options.starter != 'Y' &&
                options.starter != 'N' &&
                options.userMark != 'Q');

    } // menu

    public static void play(final GameOptions options) {

        TicTac tic = new TicTac();
        String cell = "";
        Winner winner = Winner.NONE;
        Scanner scan = new Scanner(System.in);

        tic.setMarks(options.userMark);

        if (options.starter == 'N') {
            tic.play();
        }

        tic.print();

        for (short i = 0; i < TicTac.CELLS; i++) {

            System.out.print("\nEnter a cell (A1, B2,...): ");
            cell = scan.next().toUpperCase();

            if (cell.charAt(0) == 'Q')
                bye();

            if (tic.play(cell)) {
                winner = tic.checkWinner();

                if (winner == Winner.NONE) {
                    tic.play();
                    winner = tic.checkWinner();
                }
                tic.print();

                if (winner == Winner.USER) {
                    println("You won !!");
                    return;
                } else if (winner == Winner.GAME) {
                    print("Computer won!!");
                    return;
                } else if (winner == Winner.DRAW) {
                    println("Draw. Neither won !!");
                    return;
                }
            } else {
                println("You entered a wrong cell: ");
                i--;
            }

        } // for
        tic.reset();
    } // play

    public static void bye() {
        println("Thank you for playing");
        System.exit(0);
    }

}
