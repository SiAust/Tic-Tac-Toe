import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final char X = 'X';
    private static final char O = 'O';
    private static final char GAP = '_';

    private static char[] input;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter cells: ");
        input = sc.nextLine().toCharArray();

        char[][] field = {new char[]{input[0], input[1], input[2]}
                        , new char[]{input[3], input[4], input[5]}
                        , new char[]{input[6], input[7], input[8]}};

        System.out.println("---------");
        for (char[] chars : field) {
            for (int i = 0; i < chars.length; i++) {
                if (i == 0) {
                    System.out.print("| ");
                    System.out.print(chars[i] + " ");
                }else if (i == chars.length -1) {
                    System.out.print(chars[i]);
                    System.out.print(" |");
                } else {
                    System.out.print(chars[i] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("---------");

        System.out.println(checkState(field));
    }

    private static String checkState(char[][] field) {
        Set<Character> winningChar = new HashSet<>();

        int countX = 0;
        int countO = 0;
        int countGap = 0;

        for (char[] array : field) {
            for (char c : array) {
                if (c == X) {
                    countX++;
                } else if (c == O) {
                    countO++;
                } else {
                    countGap++;
                }
            }
        }

        // check all possible win states for x or o

        boolean win = false;
        if (field[0][0] == field[0][1] && field[0][0] == field[0][2] && field[0][0] != GAP) { // left/right top
            winningChar.add(field[0][0]);
            win = true;
        }
        if (field[0][0] == field[1][0] && field[0][0] == field[2][0] && field[0][0] != GAP) { // top/bottom left
            winningChar.add(field[0][0]);
            win = true;
        }
        if (field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0] != GAP) { // diagonal left/right
            winningChar.add(field[0][0]);
            win = true;
        }
        if (field[0][1] == field[1][1] && field[0][1] == field[2][1] && field[0][1] != GAP) { // top/bottom middle
            winningChar.add(field[0][1]);
            win = true;
        }
        if (field[0][2] == field[1][2] && field[0][2] == field[2][2] && field[0][2] != GAP) { // top/bottom right
            winningChar.add(field[0][2]);
            win = true;
        }
        if (field[0][2] == field[1][1] && field[0][2] == field[2][0] && field[0][2] != GAP) { // right/left diagonal
            winningChar.add(field[0][2]);
            win = true;
        }
        if (field[1][0] == field[1][1] && field[1][0] == field[1][2] && field[1][0] != GAP) { // left/right middle
            winningChar.add(field[1][0]);
            win = true;
        }
        if (field[2][0] == field[2][1] && field[2][0] == field[2][2] && field[2][0] != GAP) { // left right bottom
            winningChar.add(field[2][0]);
            win = true;
        }

        // if field has 2 or more of x, or o, than the other. When X and O both have 3 in a row
        if (countX - countO >= 2 || countO - countX >= 2 || winningChar.size() > 1) {
            return "Impossible";
        }

        if (win) {
            return winningChar.toString().replaceAll("[\\[\\]]", "") + " wins";
        }

        // if no win then check draw
        if (countGap == 0) {
            return "Draw";
        } else { // if no draw check game not finished?
            return "Game not finished";
        }
    }
}
