package edu.ncsu.csc316.movie.manager;

import java.io.FileNotFoundException;
import java.text.ParseException;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.movie.data.Movie;
import edu.ncsu.csc316.movie.data.WatchRecord;

/**
 * Provides methods used by the UI class to provide a report
 * to the user.
 * 
 * @author Alex Raum
 */
public class ReportManager {
	
	/** A MovieManager object */
	private MovieManager manager;
	/** A String used to assist in proper formatting of output */
	private static final String INDENT = "   ";
	///** */
	//private WatchRecord record;

	/**
	 * Creates a new ReportManager for generating reports for the MovieManager software
	 * 
	 * @param pathToMovieFile the path to the file that contains movie records
	 * @param pathToWatchFile the path to the file that contains watch records
	 * @throws FileNotFoundException if either input file cannot be found/read/opened
	 * @throws ParseException if the watch record file contains incorrectly formatted date information
	 */
	public ReportManager(String pathToMovieFile, String pathToWatchFile) throws FileNotFoundException, ParseException {
		manager = new MovieManager(pathToMovieFile, pathToWatchFile);
	}

	/**
	 * Returns a report of the most frequently watched movies that contains the top
	 * n movies most watched
	 * 
	 * @param numberOfMovies the number of movies to include in the report
	 * @return a report of the most frequently watched movies
	 */
	public String getTopMoviesReport(int numberOfMovies) {
		List<Movie> movies = manager.getMostFrequentlyWatchedMovies(numberOfMovies);
		String films = "";
		for (Movie m : movies) {
			films += INDENT + m.getTitle() + "\n";
		}
		return films;
	}

	/**
	 * Returns a report of movies below a specific watch percentage threshold.
	 * 
	 * @param threshold the percentage threshold (as a whole number)
	 * @return a report of movies below a specific watch percentage threshold
	 */
	public String getMovieCompletionReport(int threshold) {
		List<Movie> movies = manager.getMoviesByWatchDuration(threshold);
		String films = "";
		for (Movie m : movies) {
			films += INDENT + m.getTitle() + "\n";
		}
		return films;
	}

	/**
	 * Return a report of dates on which a specific movie was watched
	 * 
	 * @param title the title of the movie for which to retrieve watch dates
	 * @return a report of dates on which a specific movie was watched
	 */
	public String getWatchDates(String title) {
		List<WatchRecord> freqList = manager.getWatchHistory(title);
		String dates = "";
		for (WatchRecord w : freqList) {
			dates += INDENT + w.getDate().toString() + "\n";
		}
		return dates;
	}
}