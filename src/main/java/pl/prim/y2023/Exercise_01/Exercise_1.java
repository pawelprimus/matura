package pl.prim.y2023.Exercise_01;

import pl.prim.FileReader;

import java.util.ArrayList;
import java.util.List;

public class Exercise_1 {
    public static void main(String[] args) throws Exception {

        String[] input = FileReader.readFileAsString("01", "szachy_przyklad.txt").split("[\\n]");

        char[][] tiles = new char[8][8];

        List<Board> boards = new ArrayList<>();
        int tilesIndex = 0;
        for (int i = 0; i < input.length; i++) {
            String str = input[i];
            for (int j = 0; j < str.length(); j++) {
                tiles[tilesIndex][j] = str.charAt(j);
            }
            tilesIndex++;
            if (tilesIndex == 8) {
                Board board = new Board(tiles);
                tiles = new char[8][8];
                tilesIndex = 0;
                boards.add(board);
                i++;
            }
        }

        System.out.println(boards.size());

        // 1.1
        int maxEmptyColumns = Integer.MIN_VALUE;
        int boardsWithEmptyColumns = 0;
        for (Board board : boards) {
            int boardEmptyColumns = board.containEmptyColumn();
            if (boardEmptyColumns > 0) {
                boardsWithEmptyColumns++;
                if (boardEmptyColumns > maxEmptyColumns) {
                    maxEmptyColumns = boardEmptyColumns;
                }
            }
            System.out.println();
        }

        int equalBoards = 0;
        int minEualBoards = Integer.MAX_VALUE;
        System.out.println("1.1= " + boardsWithEmptyColumns + " | " + maxEmptyColumns);

        // 1.2
        for (Board board : boards) {
            if (board.isEqual()) {
                equalBoards++;
                int emptyPlaces = board.emptyPlace();
                if (minEualBoards > emptyPlaces) {
                    minEualBoards = emptyPlaces;
                }
            }
        }
        System.out.println("1.2= " + equalBoards + " | " + minEualBoards);

        // 1.3
        int whiteSzachBlack = 0;
        int blackSzachWhite = 0;
        for (Board board : boards) {
            if (board.isSzachWhiteAtt()) {
                whiteSzachBlack++;
            }
            if (board.isSzachBlack()) {
                blackSzachWhite++;
            }
        }
        System.out.println("1.3= " + whiteSzachBlack + " | " + blackSzachWhite);

    }
}


// K/k − król, H/h − hetman,
//W/w − wieża, G/g − goniec,
//S/s − skoczek, P/p − pionek.
class Board {

    char[][] tiles;

    public Board(char[][] tiles) {
        this.tiles = tiles;
    }

    public void printBoard() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                System.out.print(tiles[i][j]);
            }
            System.out.println();
        }
    }

    public int emptyPlace() {
        int emptyPlace = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == '.') {
                    emptyPlace++;
                }
            }
        }
        return 64 - emptyPlace;
    }

    // biała wieża szachuje czrnego króla
    public boolean isSzachWhiteAtt() {
        String row = "";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                row += tiles[i][j];
            }
            //System.out.println(row);
            if (isSzachWhiteAttach(row)) {
                return true;
            }
            row = "";
        }

        String col = "";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                col += tiles[j][i];
            }
            //System.out.println(col);
            if (isSzachWhiteAttach(col)) {
                return true;
            }
            col = "";
        }
        System.out.println();
        return false;
    }

    public boolean isSzachWhiteAttach(String row) {
        if (!isContainsTowerAndKingWhiteAttack(row)) {
            return false;
        }

        System.out.println("1" + row);
        row = row.replaceAll("\\.", "");
        System.out.println("2" + row);

        int kingIndex = row.indexOf("k");
        int index = 0;
        for (char c : row.toCharArray()) {
            if (c == 'W') {
                if (Math.abs(kingIndex - index) == 1) {
                    return true;
                }
            }
            index++;
        }
        return false;
    }

    public boolean isContainsTowerAndKingWhiteAttack(String row) {
        return row.contains("k") && row.contains("W");
    }


    // czarna wieża szachuje białego króla
    public boolean isSzachBlack() {
        String row = "";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                row += tiles[i][j];
            }
            //System.out.println(row);
            if (isSzachBlackAttach(row)) {
                return true;
            }
            row = "";
        }

        String col = "";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                col += tiles[j][i];
            }
            //System.out.println(col);
            if (isSzachBlackAttach(col)) {
                return true;
            }
            col = "";
        }
        System.out.println();
        return false;
    }

    public boolean isSzachBlackAttach(String row) {
        if (!isContainsTowerAndKingBlackAttack(row)) {
            return false;
        }
        System.out.println(row);

        row = row.replaceAll(".", "");
        int kingIndex = row.indexOf("K");
        int index = 0;
        for (char c : row.toCharArray()) {
            if (c == 'w') {
                if (Math.abs(kingIndex - index) == 1) {
                    return true;
                }
            }
            index++;
        }
        return false;
    }

    public boolean isContainsTowerAndKingBlackAttack(String row) {
        return row.contains("K") && row.contains("w");
    }


    public boolean isEqual() {
        int kAm = 0;
        int hAm = 0;
        int wAm = 0;
        int gAm = 0;
        int sAm = 0;
        int pAm = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                char tile = tiles[i][j];
                switch (tile) {
                    case 'k':
                        kAm++;
                        break;
                    case 'K':
                        kAm--;
                        break;
                    case 'h':
                        hAm++;
                        break;
                    case 'H':
                        hAm--;
                        break;
                    case 'w':
                        wAm++;
                        break;
                    case 'W':
                        wAm--;
                        break;
                    case 'g':
                        gAm++;
                        break;
                    case 'G':
                        gAm--;
                        break;
                    case 's':
                        sAm++;
                        break;
                    case 'S':
                        sAm--;
                        break;
                    case 'p':
                        pAm++;
                        break;
                    case 'P':
                        pAm--;
                        break;
                }
            }
            //System.out.println();
        }

        if (kAm == 0 && hAm == 0 && wAm == 0 && gAm == 0 && sAm == 0 && pAm == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int containEmptyColumn() {
        int emptyColumns = 0;
        for (int i = 0; i < tiles.length; i++) {
            char[] column = new char[8];
            for (int j = 0; j < tiles[i].length; j++) {
                column[j] = tiles[j][i];
            }
            if (isEmptyColumn(column)) {
                emptyColumns++;
            }
        }
        return emptyColumns;
    }

    private boolean isEmptyColumn(char[] column) {
        for (char c : column) {
            //System.out.print(c);
            if (c != '.') {
                return false;
            }
        }
        //System.out.println();
        return true;
    }


}