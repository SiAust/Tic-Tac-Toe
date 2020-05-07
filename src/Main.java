import java.util.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final char X = 'X';
    private static final char O = 'O';
    private static final char GAP = '_';
    private static final Map<Key, Value> coordMap = new HashMap<>();

    private static char[][] field;

    public static void main(String[] args) {
        field = new char[][]{new char[]{GAP, GAP, GAP}
                            , new char[]{GAP, GAP, GAP}
                            , new char[]{GAP, GAP, GAP}};

        /* Ask user if they would like to continue a game, entering the game field in the next step */
        System.out.print("Would you like to continue a game? [y/n] : ");
        if ((sc.nextLine()).toLowerCase().equals("y")) {
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

        coordMap.put(new Key(1,3), new Value(0, 0));
        coordMap.put(new Key(2,3), new Value(0, 1));
        coordMap.put(new Key(3,3), new Value(0, 2));

        coordMap.put(new Key(1,2), new Value(1, 0));
        coordMap.put(new Key(2,2), new Value(1, 1));
        coordMap.put(new Key(3,2), new Value(1, 2));

        coordMap.put(new Key(1,1), new Value(2, 0));
        coordMap.put(new Key(2,1), new Value(2, 1));
        coordMap.put(new Key(3,1), new Value(2, 2));

    /*
        Element mapping for matrix
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
                        Value value = coordMap.get(new Key(coord[0], coord[1]));
                        coord = value.toIntArray();
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
            } catch (Exception e) { // invalid input exception
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

/* Key and Value classes to allow int[] objects in a Map properly by overriding equals() and hashCode() */

class Key {
    int[] key = new int[2];

    public Key(int one, int two){
        this.key[0] = one;
        this.key[1] = two;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Key)) {
            return false;
        }
        Key otherKey = (Key) obj;
        return key[0] == otherKey.key[0] && key[1] == otherKey.key[1];
    }

    @Override
    public int hashCode() {
        int result = 17; // any prime number
        result = 31 * result + Integer.valueOf(this.key[0]).hashCode();
        result = 31 * result + Integer.valueOf(this.key[1]).hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "keys[0] " + key[0] + " keys[1] " + key[1];
    }
}

class Value {
    int[] values = new int[2];

    public Value(int one, int two) {
        this.values[0] = one;
        this.values[1] = two;
    }

    public int[] toIntArray() {
        return new int[]{values[0], values[1]};
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Value)) {
            return false;
        }
        Value otherValue = (Value) obj;
        return values[0] == otherValue.values[0] && values[1] == otherValue.values[1];

    }

    @Override
    public int hashCode() {
        int result = 17; // any prime number
        result = 31 * result + Integer.valueOf(this.values[0]).hashCode();
        result = 31 * result + Integer.valueOf(this.values[1]).hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "values[0] " + values[0] + " values[1] " + values[1];
    }
}