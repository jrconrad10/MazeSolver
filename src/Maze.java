// Jacob Conrad and Jack Handy, the Maze class creates a new maze with given dimensions. Contains methods that set and get the characteristics of the maze, and a toString method that returns a string representation of maze.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Maze implements TextMaze {
	
	// maze character array instance variable
	private char[][] maze;
	// integer variables width and height to be used for the size of the maze
	private int width, height;
	
	// Creates a character array based on width and height parameters and fills it with empty string characters
	public Maze(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		maze = new char[width][height];
		
		for(int widthCount = 0; widthCount < width; widthCount++)
		{
			for(int heightCount = 0; heightCount < height; heightCount++)
			{
				maze[widthCount][heightCount] = EMPTY;
			}
		}
	}
	
	// Set character at a location within the maze if that location is in bounds.
	// If the location is out of bounds, throw an IndexOutOfBoundsException showing the point that was out of bounds.
	public void set(Point p, char c) {
		if(inBounds(p) == true)
		{
			maze[p.x][p.y] = c;
		}
		else 
		{
			throw new PointOutOfBoundsException(p.toString());
		}
	}

	// Get character at a location within the maze if that location is in bounds.
	// If the location is out of bounds, throw an IndexOutOfBoundsException showing the point that was out of bounds.
	public char get(Point p)
	{
		if(inBounds(p) == true)
		{
			return maze[p.x][p.y];
		}
		else 
		{
			throw new PointOutOfBoundsException(p.toString());
		}
	}

	// Return the maze's width.
	public int width()
	{
		return width;
	}
	

	// Return the maze's height.
	public int height()
	{
		return height;
	}

	// Returns whether a point is within the bounds of the maze.
	public boolean inBounds(Point p)
	{
		if(p.x > width-1 || p.x < 0 || p.y > height-1 || p.y <0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	// Flips the y values (height) of the maze so that 0,0 is in the bottom left of the maze instead of the top left
	// Makes the maze into a string
	public String toString()
	{
		String str = "";
		
		for(int heightCount = height-1; heightCount > -1; heightCount--)
		{
			for(int widthCount = 0; widthCount < width; widthCount++)
			{
				str = str + maze[widthCount][heightCount];
			}
			if(heightCount > 0)
			{
				str = str + "\n";
			}
		}
		return str;
	}

	// Reads a maze from a text file and returns either the maze or null if the file isn't found
	public static Maze loadMaze(String fileName)
	{
		try(Scanner loadMaze = new Scanner(new File(fileName)))
		{
			int width = loadMaze.nextInt();
			int height = loadMaze.nextInt();
			
			Maze maze = new Maze(width,height);
			loadMaze.nextLine();
			
			for(int heightCount = height-1; heightCount > -1; heightCount--)
			{
				String line = loadMaze.nextLine();
				
				for(int widthCount = 0; widthCount < line.length(); widthCount++)
				{
					if(line.charAt(widthCount) == WALL)
					{
						Point point = new Point(widthCount, heightCount);
						maze.set(point, WALL);
					}
				}
			}
			return maze;	
		}
		catch (FileNotFoundException e)
		{
			return null;
		}
	}
	
	// Creates and saves a maze to a text file
	public static void saveMaze(String fileName, Maze maze)
	{
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			PrintWriter out = new PrintWriter(fileWriter);
			out.println(maze.width + " " + maze.height);
			for(int heightCount = maze.height-1; heightCount > -1; heightCount--)
			{		
				String line = "";
				for(int widthCount = 0; widthCount < maze.width; widthCount++)
				{
					Point point = new Point(widthCount, heightCount);
					line = line + maze.get(point);
				}
				if(heightCount == 0)
				{
					out.print(line);
				}
				else
				{
					out.println(line);
				}
			}
			out.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

