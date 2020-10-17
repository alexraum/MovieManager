package edu.ncsu.csc316.movie.manager;

import java.io.FileNotFoundException;
import java.text.ParseException;

public class ReportManager {

	private MovieManager manager;
	private static final String INDENT = "   ";

	/**
	 * Creates a new ReportManager for generating reports for the MovieManager software
	 * 
	 * @param pathToMovieFile the path to the file that contains movie records
	 * @param pathToWatchFile the path to the file that contains watch records
	 * @throws FileNotFoundException if either input file cannot be found/read/opened
	 * @throws ParseException if the watch record file contains incorrectly formatted date information
	 */
	public ReportManager(String pathToMovieFile, String pathToWatchFile) throws FileNotFoundException, ParseException {
		// TODO: complete this constructor
	}

	/**
	 * Returns a report of the most frequently watched movies that contains the top
	 * n movies most watched
	 * 
	 * @param numberOfMovies the number of movies to include in the report
	 * @return a report of the most frequently watched movies
	 */
	public String getTopMoviesReport(int numberOfMovies) {
		// TODO: complete this method
	}

	/**
	 * Returns a report of movies below a specific watch percentage threshold.
	 * 
	 * @param threshold the percentage threshold (as a whole number)
	 * @return a report of movies below a specific watch percentage threshold
	 */
	public String getMovieCompletionReport(int threshold) {
		// TODO: complete this method
	}

	/**
	 * Return a report of dates on which a specific movie was watched
	 * 
	 * @param title the title of the movie for which to retrieve watch dates
	 * @return a report of dates on which a specific movie was watched
	 */
	public String getWatchDates(String title) {
		// TODO: complete this method
	}
}