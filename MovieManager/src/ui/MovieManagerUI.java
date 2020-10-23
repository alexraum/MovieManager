package ui;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

//import edu.ncsu.csc316.dsa.list.List;
//import edu.ncsu.csc316.movie.data.WatchRecord;
//import edu.ncsu.csc316.movie.manager.MovieManager;
import edu.ncsu.csc316.movie.manager.ReportManager;
//import edu.ncsu.csc316.movie.manager.ReportManager;

/**
 * Provides a user interface and a main method to enter 
 * the MovieManager program with.
 * 
 * @author Alex Raum
 */
public class MovieManagerUI {

	//private static MovieManager movieManager;
	/** A ReportManager object */
	private static ReportManager reportManager;
	
	/**
	 * The main method of the program
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		
		// create a stream for user input
		Scanner in = new Scanner(System.in);
		
		// request files from the user and store filenames 
		boolean flag = true;
		while (flag) {
			System.out.println("Enter a file containing movie record data: ");
			String movieFile = in.next();
		
			System.out.println("Enter a file containing watch record data: ");
			String watchFile = in.next();
		
			try {
				//movieManager = new MovieManager(movieFile, watchFile);
				reportManager = new ReportManager(movieFile, watchFile);
				flag = false;
			} catch (FileNotFoundException | ParseException e) {
				System.out.println("File doesn't exist, enter a new file.");
			}
		}
		
		// prompt the user to pick an option
		System.out.println("Select an option of what to do next: ");
		System.out.println("(1) for a report of most frequently watched movies, ");
		System.out.println("(2) for a report of movies below a percent completed, ");
		System.out.println("(3) for a report of dates when a movie was streamed. ");
		System.out.println("(4) to exit the program.");
		
		// store their choice
		int choice = in.nextInt();
		
		// call the appropriate routine based on the users choice
		if (choice == 1) {
			viewFrequentlyWatched();
		} else if (choice == 2) {
			viewUnfinished();
		} else if (choice == 3) {
			viewWatchHistory();
		} else if (choice == 4) {
			System.exit(1);
		} else {
			// user has not entered a valid option
			System.out.println("Invalid choice. Please select a valid option.");
		}
		
		// close the scanner
		in.close();
	}
	
	/**
	 * Helper method used to gather user input to generate report of most frequently
	 * films.
	 */
	public static void viewFrequentlyWatched() {
		// create a stream for user input
		Scanner in2 = new Scanner(System.in);
		
		// prompt user for input
		System.out.println("Enter the number of movies to be included in the report: ");
		int numFilms = in2.nextInt();
		
		// if numFilms <= 0, user is prompted to enter a number > 0
//		if (numFilms <= 0) {
//			while (numFilms <= 0) {
//				System.out.println("Please enter a number > 0: ");
//				numFilms = in2.nextInt();
//			}
//		}
		
		// if user enters a number greater than the number of unique movies in the watch
		// history file, the report will contain all of the unique movies contained in 
		// watch history file
		
		// if the input watch history file does not contain any movies, then indicate
		// "no movies have been streamed"
		String report = reportManager.getTopMoviesReport(numFilms);
		System.out.println(report);
		
		// close the scanner
		in2.close();
	}
	
	/**
	 * Helper method used to generate report of films falling below a specified
	 * percent completion.
	 */
	public static void viewUnfinished() {
		// create a stream for user input
		Scanner in3 = new Scanner(System.in);
		
		// prompt user for input
		System.out.println("Enter the maximum percent completion of films listed in report: ");
		double num = in3.nextDouble();
		
		// cast to an integer
		int percentComp = (int)num;
		
		// if the user enters a percentage threshold <= 0 or > 100, prompt user to "Please
		// enter a percentage completion between 1 and 100."
//		if (num <= 0 || num > 100) {
//			while (num <= 0 || num > 100) {
//				System.out.println("Please enter a percentage completion between 1 and 100.");
//				num = in3.nextDouble();
//			}
//		}		
	
		// if no movies have a percent completion below the given threshold, indicate "No movies
		// are less than percentComp percent completed."
		String report = reportManager.getMovieCompletionReport(percentComp);
		System.out.print(report);
		
		// close the scanner
		in3.close();
	}
	
	/**
	 * Helper method used to generate report of the films watch history
	 */
	public static void viewWatchHistory() {
		// create a stream for user input
		Scanner in4 = new Scanner(System.in);
		
		// prompt the user for input
		System.out.println("Enter a movie title: ");
		String title = in4.nextLine();
		
		// If the user enters an invalid title (no text),
		// prompt the user to “Please enter a valid movie title”.
				
		// If the movie title does not exist in the movie record
		// catalog, indicate: No watch history for “(title)”.
		
		// If the movie has never been streamed before, indicate:
		// No watch history for “(title)”.
		String report = reportManager.getWatchDates(title);
		System.out.print(report);
		
		// close the scanner
		in4.close();
	}
}
