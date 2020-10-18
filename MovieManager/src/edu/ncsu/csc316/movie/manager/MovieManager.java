package edu.ncsu.csc316.movie.manager;

import java.io.FileNotFoundException;
import java.text.ParseException;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.sorter.Sorter;
import edu.ncsu.csc316.dsa.sorter.MergeSorter;
import edu.ncsu.csc316.movie.data.Movie;
import edu.ncsu.csc316.movie.data.WatchRecord;
import edu.ncsu.csc316.movie.factory.DSAFactory;
import edu.ncsu.csc316.movie.io.InputFileReader;

public class MovieManager {

	/** */
	private List<WatchRecord> historyFile;;
	/** */
	private List<Movie> movieFile;

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
		//reader = new InputFileReader();
		//factory = new DSAFactory();
		historyFile = InputFileReader.readHistoryFile(pathToHistory);
		movieFile = InputFileReader.readMovieFile(pathToMovies);	
	}

	/**
	 * Returns a list of watch records associated with the requested movie title
	 * 
	 * @param title the title of the movie for which to retrieve watch record
	 *              information
	 * @return a list of watch records associated with the requested movie title
	 */
	public List<WatchRecord> getWatchFrequency(String title) {
		// TODO: ask if this is the right way to be approaching this, or if my reasoning is off
		List<WatchRecord> freqList = DSAFactory.getIndexedList();
		String id = "";
		for (int i = 0; i < movieFile.size(); i++) {
			if (movieFile.get(i).getTitle().equals(title)) {
				id = movieFile.get(i).getId();
			}
		}
		for (int i = 0; i < historyFile.size(); i++) {
			if (historyFile.get(i).getMovieId().equals(id)) {
				freqList.addLast(historyFile.get(i));
			}
		}
		// turn the list into an array
		WatchHistory[] records = new WatchHistory[freqList.size()];
		for (int i = 0; i < freqList.size(); i++) {
			records[i] = new WatchHistory(freqList.get(i));
		}
		// sort the array
		Sorter<WatchHistory> sorter = DSAFactory.getComparisonSorter();
		sorter.sort(records);
		// convert the array back into a list
		// keep in mind that compareTo doesn't return -1 or 1
		List<WatchRecord> newList = DSAFactory.getIndexedList();
		for (int i = 0; i < records.length; i++) {
			newList.addLast(records[i].getRecord());
		}
		return newList;
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
		// determine how many times each movie was watched
		List<Integer> freqs = DSAFactory.getIndexedList();
		int unique = 0;
		for (Movie m : movieFile) {
			int count = 0;
			for (WatchRecord w : historyFile) {
				if ((w.getMovieId().equals(m.getId())) && (count == 0)) {
					unique++;
				}
				if (w.getMovieId().equals(m.getId())) {
					count++;
				}
			}
			freqs.addLast((Integer)count);
		}
		// If the user enters a number greater than the number of unique 
		// movies in the watch history file, then the report will contain
		// all of the unique movies contained in the watch history file.
		if (numberOfMovies > unique) {
			numberOfMovies = unique;
		} 
		// turn the list into an array
		FrequentMovie[] movies = new FrequentMovie[freqs.size()];
		for (int i = 0; i < freqs.size(); i++) {
			movies[i] = new FrequentMovie(freqs.get(i), movieFile.get(i));
		}
		// sort the array
		Sorter<FrequentMovie> sorter = DSAFactory.getComparisonSorter();
		sorter.sort(movies);
		// convert the array back into a list
		List<Movie> newList = DSAFactory.getIndexedList();
		for (int i = 0; i < numberOfMovies; i++) {
			newList.addLast(movies[i].getMovie());
		}
		return newList;
	}

	/**
	 * Return a list of movie records that have been watched less than a specific
	 * threshold percentage
	 * 
	 * @param threshold the percentages threshold to use, as a whole number
	 * @return a list of movie records that have been watched less than the specified
	 *         threshold percentage
	 */
//	public List<Movie> getMoviesByWatchDuration(int threshold) {

//	}
	
	
	// TODO: create an inner class to sort list or map
	// use compareTo method inside inner class and implement comparable interface
	// manually define the compareTo method based on what they want us to sort by
	
	// hold a key and value as fields inside inner class
	private class FrequentMovie implements Comparable<FrequentMovie> {
		
		private Integer frequency;
		private Movie movie;
		
		public FrequentMovie(Integer frequency, Movie movie) {
			setFrequency(frequency);
			setMovie(movie);
		}
		
		public Integer getFrequency() {
			return this.frequency;
		}
		
		public Movie getMovie() {
			return this.movie;
		}
		
		public void setFrequency(Integer frequency) {
			this.frequency = frequency;
		}
		
		public void setMovie(Movie movie) {
			this.movie = movie;
		}
		
		// TODO: Do we need multiple comparators? (one for each use case?)
	    //		 How can we use an inner class that deals with maps if some of
		// 		 our methods use lists instead of maps? 
		public int compareTo(FrequentMovie f) {
			int freqCompare = frequency.compareTo(f.getFrequency());
			// TODO: may need to change from compareToIgnoreCase to compareTo
			int titleCompare = movie.getTitle().compareToIgnoreCase(f.getMovie().getTitle());
			int idCompare = movie.getId().compareToIgnoreCase(f.getMovie().getId());
			
			if (freqCompare < 0) {
				return 1;
			} else if (freqCompare > 0) {
				return -1;
			} else if (titleCompare > 0) {
				return 1;
			} else if (titleCompare < 0) {
				return -1;
			} else if (idCompare > 0) {
				return 1;
			} else if (idCompare < 0) {
				return -1;
			} else {
				return 0;
			}
		}	
	}
	
//	private class UnfinishedMovie implements Comparable<Movie> {
//		
//		
//		// TODO: Do we need multiple comparators? (one for each use case?)
//	    //		 How can we use an inner class that deals with maps if some of
//		// 		 our methods use lists instead of maps? 
//		public int compareTo(Movie m) {
//			// TODO Auto-generated method stub
//			
//			return 0;
//		}
//		
//	}
	
	private class WatchHistory implements Comparable<WatchHistory> {
		
		private WatchRecord record;
		
		public WatchHistory(WatchRecord record) {
			setRecord(record);
		}
		
		public WatchRecord getRecord() {
			return this.record;
		}
		
		public void setRecord(WatchRecord record) {
			this.record = record;
		}
		
		// TODO: Do we need multiple comparators? (one for each use case?)
	    //		 How can we use an inner class that deals with maps if some of
		// 		 our methods use lists instead of maps? 
		public int compareTo(WatchHistory w) {
			int dateCompare = record.getDate().compareTo(w.getRecord().getDate()); 
			
			if (dateCompare < 0) {
				return 1;
			} else if (dateCompare > 0) {
				return -1;
			} else if (record.getWatchTime() < w.getRecord().getWatchTime()) {
				return 1;
			} else if (record.getWatchTime() > w.getRecord().getWatchTime()) {
				return -1;
			} else {
				return 0;
			}
		}		
	}	
}