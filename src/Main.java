import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static final char X = 'X';
    private static final char O = 'O';
    private static final char GAP = '_';
    private static final Map<String, String> coordMap = new HashMap<>();

    private static char[][] field;

    public static void main(String[] args) {
        field = new char[][]{new char[]{GAP, GAP, GAP}
                , new char[]{GAP, GAP, GAP}
                , new char[]{GAP, GAP, GAP}};

        System.out.print("Would you like to continue a game? [y/n] : ");
        String input;
        if ((input = sc.nextLine()).toLowerCase().equals("y")) {
            inputField();
        }

        createMap();
        // inputField();
        printField();

        // game loop
        char player = X;
        while (checkState(field)) {
            playTurn(player);
            switch (player) {
                case X:
                    player = O;
                    break;
                case O:
                    player = X;
                    break;
            }
        }

    }

    private static void createMap() {
        coordMap.put("13", "00");
        coordMap.put("23", "01");
        coordMap.put("33", "02");

        coordMap.put("12", "10");
        coordMap.put("22", "11");
        coordMap.put("32", "12");

        coordMap.put("11", "20");
        coordMap.put("21", "21");
        coordMap.put("31", "22");

    /* Element mapping for matrix
        (1, 3) (2, 3) (3, 3) // [0][0] [0][1] [0][2]
        (1, 2) (2, 2) (3, 2) // [1][0] [1][1] [1][2]
        (1, 1) (2, 1) (3, 1) // [2][0] [2][1] [2][2]
    */
    }

    private static void inputField() {
        System.out.print("Enter cells: ");
        char[] input = sc.nextLine().toUpperCase().toCharArray();

        field = new char[][]{new char[]{input[0], input[1], input[2]}
                , new char[]{input[3], input[4], input[5]}
                , new char[]{input[6], input[7], input[8]}};

    }

    private static void printField() {
        System.out.println("---------");
        for (char[] chars : field) {
            for (int i = 0; i < chars.length; i++) {
                if (i == 0) {
                    System.out.print("| ");
                    System.out.print(chars[i] + " ");
                } else if (i == chars.length - 1) {
                    System.out.print(chars[i]);
                    System.out.print(" |");
                } else {
                    System.out.print(chars[i] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("---------");
    }

    private static void playTurn(char pMarker) {
        String[] input;
        final int[] noCoord = {9, 9};  /* values which don't match any coordinate */
        int[] coord = {9, 9};

        while (Arrays.equals(coord, noCoord)) {
            System.out.print("Enter the coordinates: ");
            input = sc.nextLine().split(" ");
            try {
                if (input.length == 2) {
                    for (int i = 0; i < input.length; i++) {
                        coord[i] = Integer.parseInt(input[i]);
                    }
                    if (checkBounds(coord)) {
                        // workaround for Map equality check of Key int[] / value int[]
                        String[] converStr = coordMap.get(String.valueOf(coord[0]) + String.valueOf(coord[1])).split("");
                        coord = new int[]{Integer.parseInt(converStr[0]), Integer.parseInt(converStr[1])};
                         /* if there is a gap in the field, place an player marker at that coordinate */
                        if (field[coord[0]][coord[1]] == GAP) {
                            field[coord[0]][coord[1]] = pMarker;
                            printField();
                        } else {
                            System.out.println("This cell is occupied! Choose another one!");
                            coord[0] = 9;
                            coord[1] = 9;
                        }
                    } else { // out of bounds else
                        System.out.println("You should enter numbers!");
                        coord[0] = 9;
                        coord[1] = 9;
                    }

                } else { // only one input else
                    System.out.println("You should enter numbers!");
                }
            } catch (Exception e) { // invalid input else
                System.out.println("You should enter numbers!");
            }
        }
    }

    private static boolean checkBounds(int[] coord) {
        return coord[0] < 4 && coord[0] > 0 && coord[1] < 4 && coord[1] > 0;
    }

    private static boolean checkState(char[][] field) {
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
            System.out.println("Impossible");
            return false;
        }

        if (win) {
            System.out.println(winningChar.toString().replaceAll("[\\[\\]]", "") + " wins");
            return false;
        }

        // if not a win then check draw
        if (countGap == 0) {
            System.out.println("Draw");
            return false;
        } else { // if no draw the game is not finished
            return true;
        }
    }
}
