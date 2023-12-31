import java.util.Random;

enum Mark {
    
    EMPTY(' '),
    NOUGHT('O'),
    CROSS('X');

    private char mark;

    private Mark(char mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {

        return String.valueOf(mark);
    }

}

enum Winner {
    NONE,
    GAME,
    USER,
    DRAW
}

class GameOptions {

    public char userMark;
    public char gameMark;
    public char starter;
}

class TicTac {

    private Mark[][] grid;
    private final char[] cols;
    private Mark userMark;
    private Mark gameMark;
    //
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final int CELLS = 9;
    //

    public TicTac() {

        grid = new Mark[ROWS][COLS];
        cols = new char[] { 'A', 'B', 'C' };

        for (int n = 0; n < ROWS; ++n)
            for (int m = 0; m < COLS; ++m)
                grid[n][m] = Mark.EMPTY;
    }

    public String rightPad(String str, char pad, int padLen) {

        var sb = new StringBuffer(str);
        //
        for (int index = 0; index < padLen; index++) {
            sb.append(pad);
        }
        return sb.toString();
    }

    private Winner markToWinner(Mark mark) {
        Winner winner = Winner.NONE;

        switch (mark) {
            case CROSS:
                winner = userMark == Mark.CROSS ? Winner.USER : Winner.GAME;
                break;
            case NOUGHT:
                winner = userMark == Mark.NOUGHT ? Winner.USER : Winner.GAME;
                break;
            case EMPTY:
                winner = Winner.NONE;
                break;
            default:
                break;
        }
        return winner;
    }

    private int getCol(char val) {

        int col = 0;

        for (int n = 0; n < COLS; ++n)
            if (cols[n] == val) {
                col = n;
                break;
            }
        return col;
    }

    public void play() {

        int count = 0;

        Random rand = new Random();

        while (count < CELLS) {

            int row = rand.nextInt(ROWS);
            int col = rand.nextInt(COLS);
            //
            Mark mark = grid[row][col];

            if (mark == Mark.EMPTY) {
                grid[row][col] = gameMark;
                break;
            }
            count++;
        }
    }

    public boolean play(final String cell) {

        int col = getCol(cell.toUpperCase().charAt(0));
        int row = Integer.parseInt(cell.substring(1));

        row--; // matrix row

        if (col < 0 || col > 2)
            return false;

        Mark mark = grid[row][col];

        if (mark == Mark.EMPTY) {
            grid[row][col] = userMark;
            return true;
        }
        return false;
    }

    public void setMarks(char mark) {
        switch (mark) {
            case 'X':
                userMark = Mark.CROSS;
                break;
            case 'O':
                userMark = Mark.NOUGHT;
                break;
            default:
                break;
        }
        gameMark = userMark == Mark.NOUGHT ? Mark.CROSS : Mark.NOUGHT;
    }

    public Winner checkWinner() {
        // check rows
        for (int n = 0; n < ROWS; n++)
            if (grid[n][0] == grid[n][1] && grid[n][0] == grid[n][2])
                return markToWinner(grid[n][0]);

        // check columns
        for (int n = 0; n < COLS; n++)
            if (grid[0][n] == grid[1][n] && grid[0][n] == grid[2][n])
                return markToWinner(grid[0][n]);

        // check diagonal
        if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2])
            return markToWinner(grid[0][0]);

        if (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0])
            return markToWinner(grid[0][2]);

        return Winner.NONE;
    }

    public void reset() {
        for (int n = 0; n < ROWS; ++n)
            for (int m = 0; m < COLS; ++m)
                grid[n][m] = Mark.EMPTY;
    }

    boolean placeMark(Mark smark, Mark mark) {
        // Check rows
        for (short n = 0; n < ROWS; n++) {
            if (grid[n][0] == smark && grid[n][1] == smark && grid[n][2] == Mark.EMPTY) {
                grid[n][2] = mark;
                return true;
            } else if (grid[n][0] == Mark.EMPTY && grid[n][1] == smark && grid[n][2] == smark) {
                grid[n][0] = mark;
                return true;
            } else if (grid[n][0] == smark && grid[n][1] == Mark.EMPTY && grid[n][2] == smark) {
                grid[n][1] = mark;
                return true;
            }
        } // for

        // check columns
        for (short n = 0; n < COLS; n++) {
            if (grid[0][n] == smark && grid[1][n] == smark && grid[2][n] == Mark.EMPTY) {
                grid[2][n] = mark;
                return true;
            } else if (grid[0][n] == Mark.EMPTY && grid[1][n] == smark && grid[2][n] == smark) {
                grid[0][n] = mark;
                return true;
            } else if (grid[0][n] == smark && grid[1][n] == Mark.EMPTY && grid[2][n] == smark) {
                grid[1][n] = mark;
                return true;
            }
        } // for

        // check diagonal downward
        if (grid[0][0] == Mark.EMPTY && grid[1][1] == smark && grid[2][2] == smark) {
            grid[0][0] = mark;
            return true;
        } else if (grid[0][0] == smark && grid[1][1] == Mark.EMPTY && grid[2][2] == smark) {
            grid[1][1] = mark;
            return true;
        } else if (grid[0][0] == smark && grid[1][1] == smark && grid[2][2] == Mark.EMPTY) {
            grid[2][2] = mark;
            return true;
        }
        // check diagonal upward
        if (grid[2][0] == Mark.EMPTY && grid[1][1] == smark && grid[0][2] == smark) {
            grid[2][0] = mark;
            return true;
        } else if (grid[2][0] == smark && grid[1][1] == Mark.EMPTY && grid[0][2] == smark) {
            grid[1][1] = mark;
            return true;
        } else if (grid[2][0] == smark && grid[1][1] == smark && grid[0][2] == Mark.EMPTY) {
            grid[0][2] = mark;
            return true;
        }
        return false;
    }

    public void print() {

        String devider = rightPad("-", '-', 14);

        System.out.println();

        for (char c : cols) {
            System.out.printf("   %c", c);
        }
        System.out.println();
        System.out.println(devider);

        for (short r = 0; r < ROWS; ++r) {
            System.out.print((r + 1) + " | ");
            for (short c = 0; c < COLS; ++c)
                System.out.print((grid[r][c]) + " | ");
            System.out.println();
        }
        System.out.println(devider);

    }

}
