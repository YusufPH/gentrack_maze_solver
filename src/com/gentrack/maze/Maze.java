package com.gentrack.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Maze {

    private int[][] maze;
    private boolean[][] explored;
    int height;
    int width;
    private Coordinate startCoordinate;
    private Coordinate endCoordinate;

    private static final int passage = 0;
    private static final int wall = 1;
    private static final int startPoint = 2;
    private static final int endPoint = 3;
    private static final int route = 4;

    public Maze(File maze) throws FileNotFoundException {
        String fileText = "";
        try (Scanner scanner = new Scanner(maze)) {
            while (scanner.hasNextLine()) {
                fileText += scanner.nextLine() + "\n";
            }
        }
        extractMazeInformation(fileText);
    }

    private void extractMazeInformation(String text){

        if (text == null || (text = text.trim()).length() == 0) {
            throw new IllegalArgumentException("incorrect file format: empty text file");
        }

        String[] lines = text.split("[\r]?\n");

        if(!lines[0].matches("^(\\d+)(\\s)(\\d+)$") || !lines[1].matches("^(\\d+)(\\s)(\\d+)$") || !lines[2].matches("^(\\d+)(\\s)(\\d+)$")) //checks format of dimensions, start and end in the format [int][white space][int]
        {
            throw new IllegalArgumentException("incorrect file format: incorrect input format");
        }
        String[] dimensions = lines[0].split("\\s+"); // line 1 (index 0) of txt file holds the dimensions of the maze
        width = Integer.parseInt(dimensions[0]);
        height = Integer.parseInt(dimensions[1]);

        String[] start = lines[1].split("\\s+"); // line 2 (index 1) of txt file holds the start coordinates
        startCoordinate = new Coordinate(Integer.parseInt(start[1]), Integer.parseInt(start[0]));

        String[] end = lines[2].split("\\s+"); // line 3 (index 2) of txt file holds the end coordinates
        endCoordinate = new Coordinate(Integer.parseInt(end[1]), Integer.parseInt(end[0]));

        initializeMaze(lines); //format the maze object with all its components i.e. walls, passages, start and end point
    }

    private void initializeMaze(String[] lines) {

        maze = new int[height][width];
        explored = new boolean[height][width];

        for(int row = 0; row < height; row++){
            for(int col = 0; col< width; col++){
                if(lines[row+3].charAt(2*col) == '1') // row+3 and 2*col due to format of expected txt file -> maze structure starts on line 4 and white space between columns
                {
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

    public void printSolution(List<Coordinate> route) {
        int[][] tempMaze = this.maze;
        for (Coordinate c : route) {
            if (isStart(c.getX(), c.getY()) || isEnd(c.getX(), c.getY())) {
                continue;
            }
            tempMaze[c.getX()][c.getY()] = Maze.route;
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

    public Coordinate getStart() {
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

    //method to identify is wrapping is possible and necessary for a particular movement
    public int isWrappedMovement(int row, int col){
        if(row < 0) return 0; //wrap when moving North
        else if(row >= getHeight()) return 1; //wrap when moving South
        else if(col < 0) return 2; //wrap when moving West
        else if(col>=getWidth()) return 3; //wrap when moving East
        else return 4; //No wrapping necessary
    }

    public void setExplored(int row, int col, boolean value) {
        explored[row][col] = value;
    }

}
