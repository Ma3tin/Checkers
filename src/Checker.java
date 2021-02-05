import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Checker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<int[]> canEatPositions = new ArrayList<>();
        boolean blackOrWhite = true;
        for (int i = 0; true; i++) {
            boolean jump = false;
            printBoard();
            if (i % 2 == 1) System.out.println("Na tahu je bílý");
            else System.out.println("Na tahu je černý");
            System.out.println("Chceš se vzdát? [a/N]");
            String forfeit = sc.nextLine();
            if (forfeit.toLowerCase().startsWith("a")) break;
            System.out.println("Zadej čím chceš táhnout");
            int x = Integer.parseInt(sc.nextLine());
            int y = Integer.parseInt(sc.nextLine());
            do {
                boolean good = isYourStone(x, y, i) && canMove(x, y, i, jump);
                if (jump) {
                    if (!good) break;
                    System.out.println("Chceš znova skákat? [A/n]");
                    String jumpingAgain = sc.nextLine();
                    if (jumpingAgain.toLowerCase().startsWith("n")) break;
                }
                while (!good) {
                    System.out.println("Toto není tvoje postavička nebo není kam táhnout, zkus to znovu.");
                    System.out.println("Zadej čím chceš táhnout");
                    x = Integer.parseInt(sc.nextLine());
                    y = Integer.parseInt(sc.nextLine());
                    good = isYourStone(x, y, i) && canMove(x, y, i, jump);
                }
                select(x, y);
                printBoard();
                System.out.println();
                System.out.println("Zadej kam chceš táhnout");
                int newx = Integer.parseInt(sc.nextLine());
                int newy = Integer.parseInt(sc.nextLine());
                boolean moveSelect = validateMove(x, y, i, newx, newy, jump);
                while (!moveSelect) {
                    System.out.println("Sem se nemůžeš pohnout, zkus to znova");
                    System.out.println("Kam by jsi chtěl táhnout?");
                    System.out.println("Zadej x");
                    newx = Integer.parseInt(sc.nextLine());
                    System.out.println("Zadej y");
                    newy = Integer.parseInt(sc.nextLine());
                    moveSelect = validateMove(x, y, i, newx, newy, jump);
                }
                jump = move(x, y, i, newx, newy);
                x = newx;
                y = newy;
            } while (jump);
            printBoard();
            System.out.println();
        }
    }

    public static int[][] board = {
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, 0, 1, 0, 1, 0, 1, 0, 1, -1},
            {-1, 0, 0, 1, 0, 1, 0, 1, 0, -1},
            {-1, 0, 1, 0, 1, 0, 1, 0, 1, -1},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, -1},
            {-1, 0, 1, 0, 0, 0, 0, 0, 0, -1},
            {-1, 2, 0, 2, 0, 2, 0, 2, 0, -1},
            {-1, 0, 2, 0, 2, 0, 2, 0, 2, -1},
            {-1, 2, 0, 2, 0, 2, 0, 2, 0, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
    };


    public static void printBoard() {

        String[] numbers = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧"};
        for (int i = 0; i < 8; i++) {
            System.out.print(numbers[i] + " ");
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0 && (i + j) % 2 == 1) System.out.print("⬜ ");
                else if (board[i][j] == 0 && (i + j) % 2 == 0) System.out.print(/*"⬛ "*/ "  ");
                else if (board[i][j] == 1) System.out.print("⚫ ");
                else if (board[i][j] == 2) System.out.print("⚪ ");
                else if (board[i][j] == 3) System.out.print("🔘 ");
            }
            if (i >= 1 && i <= 8) System.out.print("" + numbers[i - 1]);
            System.out.println();
        }
    }

    public static boolean isYourStone(int y, int x, int i) {
        int position = board[x][y];
        if (i % 2 == 0 && position == 2) return true;
        else if (i % 2 == 1 && position == 1) return true;
        else return false;
    }

    public static void select(int y, int x) {
        board[x][y] = 3;
    }

    public static boolean canMove(int y, int x, int i, boolean jump) {
        if (i % 2 == 0) {
            if (!jump && (board[x - 1][y - 1] == 0 || board[x - 1][y + 1] == 0)) return true;
            return (((board[x - 1][y - 1] == 1) && (board[x - 2][y - 2] == 0)) ||
                    ((board[x - 1][y + 1] == 1) && (board[x - 2][y + 2] == 0)));
        } else {
            if (!jump && (board[x + 1][y - 1] == 0 || board[x + 1][y + 1] == 0)) return true;
            return (((board[x + 1][y - 1] == 2) && (board[x + 2][y - 2] == 0)) ||
                    ((board[x + 1][y + 1] == 2) && (board[x + 2][y + 2] == 0)));
        }
    }

    public static boolean move(int y, int x, int i, int ny, int nx) {
        board[x][y] = 0;
        if (i % 2 == 0) {
            board[nx][ny] = 2;
        } else {
            board[nx][ny] = 1;
        }
        if (nx == x - 2) {
            board[x - 1][(y + ny) / 2] = 0;
            return true;
        } else if (nx == x + 2) {
            board[x + 1][(y + ny) / 2] = 0;
            return true;
        }
        return false;
    }

    public static boolean validateMove(int y, int x, int i, int ny, int nx, boolean jump) {
        if (i % 2 == 0) {
            if (!jump && (nx == x - 1)) {
                if ((y - 1 == ny) || (y + 1 == ny)) {
                    return (board[nx][ny] == 0);
                }
                return false;
            } else if (nx == x - 2) {
                if (((y - 2 == ny) && (board[x - 1][y - 1] == 1) && (board[nx][ny] == 0))) return true;
                if (((y + 2 == ny) && (board[x - 1][y + 1] == 1) && (board[nx][ny] == 0))) return true;
                return false;
            } else return false;
        } else {
            if (!jump && (nx == x + 1)) {
                if ((y - 1 == ny) || (y + 1 == ny)) {
                    return (board[nx][ny] == 0);
                }
                return false;
            } else if (nx == x + 2) {
                if (((y - 2 == ny) && (board[x + 1][y - 1] == 2) && (board[nx][ny] == 0))) return true;
                if (((y + 2 == ny) && (board[x + 1][y + 1] == 2) && (board[nx][ny] == 0))) return true;
                return false;
            } else return false;
        }
    }
}