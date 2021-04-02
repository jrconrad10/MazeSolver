// Jacob Conrad and Jack Handy, The MazesSolver class includes a main method, the solveMaze method, and the findGoal method. This class makes the maze class functional and completes the program.

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MazeSolver {
	
	// Maze instance variable
	private static Maze maze;
	
	// Main method loads maze file based on name given by user. Also prints the maze and asks user for start and end coordinates(ensuring they are in bounds).
	// Calls solveMaze method and prints whether it was solved, and then the solved maze. Saves the solved maze to a file.
	public static void main(String[] args)
	{
		Scanner mazeSolver = new Scanner(System.in);
		Maze mazeTemp = null;
		String fileName = "";
		while(mazeTemp == null)
		{
			System.out.println("What is the name of your maze file?");
			fileName = mazeSolver.nextLine();
			mazeTemp = Maze.loadMaze(fileName);
		}
		maze = mazeTemp;
		System.out.println(maze.toString());
		
		boolean validPoints = false;
		Point start = new Point(0,0);
		Point end = new Point(0,0);
		
		do {
			try {
				System.out.println("What is the x value of your starting point?");
				int startX = mazeSolver.nextInt();
				System.out.println("What is the y value of your starting point?");
				int startY = mazeSolver.nextInt();
				
				start = new Point(startX, startY);
				
				if(maze.inBounds(start) == true && maze.get(start) == maze.EMPTY)
				{
					System.out.println("What is the x value of your ending point?");
					int endX = mazeSolver.nextInt();
					System.out.println("What is the y value of your ending point?");
					int endY = mazeSolver.nextInt();
					
					end = new Point(endX, endY);
					
					if(maze.inBounds(end) == true && maze.get(end) == maze.EMPTY)
					{
						validPoints = true;
					}
				}
			}
			catch (InputMismatchException e) {
				mazeSolver.nextLine();
			}
		} while(!validPoints);
		
		if(solveMaze(maze, start, end) == true)
		{
			System.out.println("The maze was solved!");
			System.out.println(maze.toString());
			Maze.saveMaze(fileName + ".solved", maze);
		}
	}
	
	// solveMaze marks the beginning and end of maze, calls find goal to solve the maze, and returns whether it was solved.
	public static boolean solveMaze(Maze maze, Point start, Point end)
	{
		maze.set(end, maze.GOAL);
		boolean solved = findGoal(maze, start);
		maze.set(start, maze.START);
		return solved;
	}
	
	// findGoal recursively explores the maze to find goal character. Marks dead end points on path as visited.
	public static boolean findGoal(Maze maze, Point current)
	{
		if(maze.get(current) == Maze.GOAL)
		{
			return true;
		}
		else if(maze.get(current) != Maze.EMPTY)
		{
			return false;
		}
		else
		{
			maze.set(current, maze.PATH);
			Point[] adjacent = current.getAdjacentPoints();
			for(int count = 0; count < adjacent.length; count++)
			{
				if(maze.inBounds(adjacent[count]))
				{
					if(findGoal(maze, adjacent[count]))
					{
						return true;
					}
				}
			}
			maze.set(current, maze.VISITED);
			return false;
		}
	}
}
