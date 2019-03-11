package com.gentrack.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class GentrackMazeSolver {
    public static void main(String[] args) {

        System.out.println("Enter file name (e.g. 'input.txt'):");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        System.out.println("The file you have selected is " + filename +"\n");
        try {
            File file = new File("../Samples/" + filename); //file path for executable .jar file, if running from different location e.g src, change pathname
            execute(filename, file);
        }
        catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        }

    }

    private static void execute(String filename, File file) throws FileNotFoundException {
        Maze m = new Maze(file);
        MazeSolver solver = new MazeSolver();
        List<Coordinate> route = solver.solve(m);
        if(route.isEmpty()){
            System.out.println("Unfortunately " + filename +  " is Unsolvable");
        } else{
            System.out.println("Solution:\n");
            m.printSolution(route);
        }

    }

}
