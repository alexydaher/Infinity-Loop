package fr.dauphine.JavaAvance.Solve;



import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.Grid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class Checker {
    /**
     * read and return grid from file
     * @param inputFile
     * @return
     * @throws FileNotFoundException
     */
    public static Grid readGrid(String inputFile) throws FileNotFoundException {
        BufferedReader fr = new BufferedReader(new FileReader(inputFile));
        try {
            Grid grid = new Grid(Integer.parseInt(fr.readLine()),Integer.parseInt(fr.readLine()));
            for(int i = 0; i < grid.getHeight(); i++){
                for(int j = 0; j < grid.getWidth(); j++){
                    String[] piece = fr.readLine().split(" ");
                    if(piece.length != 0){
                        PieceType pieceType = PieceType.getTypeFromValue(Integer.parseInt(piece[0]));
                        Orientation orientation = Orientation.getOriFromValue(Integer.parseInt(piece[1]));
                        grid.setPiece(i,j,new Piece(i,j, PieceType.getTypeFromValue(Integer.parseInt(piece[0])), Orientation.getOriFromValue(Integer.parseInt(piece[1]))));
                    }
                }
            }
            return grid;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * return true if grid isSolved
     * @param grid
     * @return
     */
    synchronized public static boolean isSolved(Grid grid) {
        for(int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                for (Orientation orientation : grid.getPiece(i, j).getConnectors()) {
                    switch (orientation) {
                        case NORTH -> {
                            if (i == 0 || !grid.getPiece(i - 1, j).getConnectors().contains(orientation.getOpposedOrientation())) {
                                return false;
                            }
                        } case EAST -> {
                            if (j == grid.getWidth() - 1 || !grid.getPiece(i, j + 1).getConnectors().contains(orientation.getOpposedOrientation())) {
                                return false;
                            }
                        } case SOUTH -> {
                            if (i == grid.getHeight() - 1 || !grid.getPiece(i + 1, j).getConnectors().contains(orientation.getOpposedOrientation())) {
                                return false;
                            }
                        } case WEST -> {
                            if (j == 0 || !grid.getPiece(i, j - 1).getConnectors().contains(orientation.getOpposedOrientation())) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
