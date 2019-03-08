package com.gentrack.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class maze{

    private int[][] maze;
    private boolean[][] explored;
    int height;
    int width;
    private coordinate startCoordinate;
    private coordinate endCoordinate;

    private static final int passage = 0;
    private static final int wall = 1;
    private static final int startPoint = 2;
    private static final int endPoint = 3;
    private static final int route = 4;

    public maze(File maze) throws FileNotFoundException {
        String fileText = "";
        try (Scanner input = new Scanner(maze)) {
            while (input.hasNextLine()) {
                fileText += input.nextLine() + "\n";
            }
        }
        extractMazeInformation(fileText);
    }

    private void extractMazeInformation(String text){
        if (text == null || (text = text.trim()).length() == 0) {
            throw new IllegalArgumentException("empty lines data");
        }

        String[] lines = text.split("[\r]?\n");

        String[] dimensions = lines[0].split("\\s+");
        width = Integer.parseInt(dimensions[0]);
        height = Integer.parseInt(dimensions[1]);

        String[] start = lines[1].split("\\s+");
        startCoordinate = new coordinate(Integer.parseInt(start[1]), Integer.parseInt(start[0]));

        String[] end = lines[2].split("\\s+");
        endCoordinate = new coordinate(Integer.parseInt(end[1]), Integer.parseInt(end[0]));

        initializeMaze(lines);
    }

    private void initializeMaze(String[] lines) {

        maze = new int[height][width];
        explored = new boolean[height][width];

        for(int row = 0; row < height; row++){
            for(int col = 0; col< width; col++){
                if(lines[row+3].charAt(2*col) == '1'){
                    maze[row][col] = wall;
                }
                else{
                    maze[row][col] = passage;
                }
            }
        }

        maze[startCoordinate.getX()][startCoordinate.getY()] = startPoint;
        maze[endCoordinate.getX()][endCoordinate.getY()] = endPoint;
    }

    public void printSolution(List<coordinate> route) {
        int[][] tempMaze = this.maze;
        for (coordinate c : route) {
            if (isStart(c.getX(), c.getY()) || isEnd(c.getX(), c.getY())) {
                continue;
            }
            tempMaze[c.getX()][c.getY()] = com.gentrack.maze.maze.route;
        }
        System.out.println(toString(tempMaze));
    }

    public String toString(int[][] maze) {
        StringBuilder mazeSolution = new StringBuilder(width * (height + 1));
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze[row][col] == passage) {
                    mazeSolution.append(' ');
                } else if (maze[row][col] == wall) {
                    mazeSolution.append('#');
                } else if (maze[row][col] == startPoint) {
                    mazeSolution.append('S');
                } else if (maze[row][col] == endPoint) {
                    mazeSolution.append('E');
                } else {
                    mazeSolution.append('X');
                }
            }
            mazeSolution.append('\n');
        }
        return mazeSolution.toString();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public coordinate getStart() {
        return startCoordinate;
    }

    public boolean isStart(int x, int y) {
        return x == startCoordinate.getX() && y == startCoordinate.getY();
    }

    public boolean isEnd(int x, int y) {
        return x == endCoordinate.getX() && y == endCoordinate.getY();
    }

    public boolean isExplored(int row, int col) {
        return explored[row][col];
    }

    public boolean isWall(int row, int col) {
        return maze[row][col] == wall;
    }

    public int isWrappedMovement(int row, int col){
        if(row < 0) return 0;
        else if(row >= getHeight()) return 1;
        else if(col < 0) return 2;
        else if(col>=getWidth()) return 3;
        else return 4;
    }

    public void setExplored(int row, int col, boolean value) {
        explored[row][col] = value;
    }

}
