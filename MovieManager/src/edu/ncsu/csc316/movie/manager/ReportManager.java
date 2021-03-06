package edu.ncsu.csc316.movie.manager;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

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
		if (numberOfMovies <= 0) {
			return "Please enter a number > 0.";
		}
		List<Movie> movies = manager.getMostFrequentlyWatchedMovies(numberOfMovies);
		if (movies.size() == 0) {
			return "No movies have been streamed.";
		}
		StringBuilder report = new StringBuilder("The " + numberOfMovies + " most frequently watched movies [\n");
		for (Movie m : movies) {
			report.append(INDENT + m.getTitle() + " (" + m.getYear() + ")\n");
		}
		report.append("]");
		return report.toString();
	}

	/**
	 * Returns a report of movies below a specific watch percentage threshold.
	 * 
	 * @param threshold the percentage threshold (as a whole number)
	 * @return a report of movies below a specific watch percentage threshold
	 */
	public String getMovieCompletionReport(int threshold) {
		if (threshold <= 0 || threshold > 100) {
				return "Please enter a percentage completion between 1 and 100.";
		}	
		List<Movie> movies = manager.getMoviesByWatchDuration(threshold);
		if (movies.size() == 0) {
			return "No movies are less than " + threshold + "% completed.";
		}
		StringBuilder report = new StringBuilder("The movies that have been watched less than " + threshold + "% [\n");
		for (Movie m : movies) {
			report.append(INDENT + m.getTitle() + " (" + m.getYear() + ")\n");
		}
		report.append("]");
		return report.toString();
	}

	/**
	 * Return a report of dates on which a specific movie was watched
	 * 
	 * @param title the title of the movie for which to retrieve watch dates
	 * @return a report of dates on which a specific movie was watched
	 */
	public String getWatchDates(String title) {
		if (title.length() == 0) {
			return "Please enter a valid movie title.";
		}
		List<WatchRecord> freqList = manager.getWatchHistory(title);
		if (freqList.size() == 0) {
			return "No watch history for \"" + title + "\".";
		}
		StringBuilder report = new StringBuilder("The movie \"" + title + "\" was streamed on [\n");
		for (WatchRecord w : freqList) {
			report.append(INDENT + w.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + "\n");
		}
		report.append("]");
		return report.toString();
	}
}