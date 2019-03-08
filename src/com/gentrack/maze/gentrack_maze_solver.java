package com.gentrack.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class gentrack_maze_solver {
    public static void main(String[] args) {

        System.out.println("Enter file name (e.g. 'input.txt'):");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        System.out.println("the file you have selected is " + filename);
        try {
            File file = new File("src/resources/Samples/" + filename);
            execute(file);
        }
        catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        }

    }

    private static void execute(File file) throws FileNotFoundException {
        maze m = new maze(file);
        maze_solver solver = new maze_solver();
        List<coordinate> route = solver.solve(m);
        if(route.isEmpty()){
            System.out.println("Maze is Unsolveable");
        } else{
            m.printSolution(route);
        }

    }

}
