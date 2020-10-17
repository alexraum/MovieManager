package edu.ncsu.csc316.movie.manager;

import java.io.FileNotFoundException;
import java.text.ParseException;

public class MovieManager {

	/**
	 * Creates a MovieManager instance for handling utility functions
	 * 
	 * @param pathToMovies  the path to the file of movie records
	 * @param pathToHistory the path to the file of watch records
	 * @throws FileNotFoundException if the file cannot be found
	 * @throws ParseException        if the watch history file has incorrectly
	 *                               formatted date information
	 */
	public MovieManager(String pathToMovies, String pathToHistory) throws FileNotFoundException, ParseException {
		// TODO: complete this constructor
	}

	/**
	 * Returns a list of watch records associated with the requested movie title
	 * 
	 * @param title the title of the movie for which to retrieve watch record
	 *              information
	 * @return a list of watch records associated with the requested movie title
	 */
	public List<WatchRecord> getWatchFrequency(String title) {
		// TODO: complete this method
	}

	/**
	 * Return a list of movie records that contains the top n most frequently
	 * watched movies
	 * 
	 * @param numberOfMovies the n most frequently watched movies to include in the
	 *                       list
	 * @return a list of movie records that contains the top n most frequently
	 *         watched movies
	 */
	public List<Movie> getMostFrequentlyWatchedMovies(int numberOfMovies) {
		// TODO: complete this method
	}

	/**
	 * Return a list of movie records that have been watched less than a specific
	 * threshold percentage
	 * 
	 * @param threshold the percentages threshold to use, as a whole number
	 * @return a list of movie records that have been watched less than the specified
	 *         threshold percentage
	 */
	public List<Movie> getMoviesByWatchDuration(int threshold) {
		// TODO: complete this method
	}
}