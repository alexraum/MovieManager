package edu.ncsu.csc316.movie.manager;

import java.io.FileNotFoundException;
import java.text.ParseException;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.sorter.Sorter;
//import edu.ncsu.csc316.dsa.sorter.MergeSorter;
import edu.ncsu.csc316.movie.data.Movie;
import edu.ncsu.csc316.movie.data.WatchRecord;
import edu.ncsu.csc316.movie.factory.DSAFactory;
import edu.ncsu.csc316.movie.io.InputFileReader;

/**
 * The MovieManager contains the methods used to find and sort data
 * as specified by the user.
 * 
 * @author Alex Raum
 */
public class MovieManager {

	/** A list of historyFile objects */
	private List<WatchRecord> historyFile;;
	/** A list of movieFile objects */
	private List<Movie> movieFile;

	/**
	 * Creates a MovieManager instance for handling utility functions
	 * 
	 * @param pathToMovies the path to the file of movie records
	 * @param pathToHistory the path to the file of watch records
	 * @throws FileNotFoundException if the file cannot be found
	 * @throws ParseException if the watch history file has incorrectly
	 *         formatted date information
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
	 * 		  information
	 * @return a list of watch records associated with the requested movie title
	 */
	public List<WatchRecord> getWatchHistory(String title) {
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
	 * 		  list
	 * @return a list of movie records that contains the top n most frequently
	 * 		   watched movies
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
	public List<Movie> getMoviesByWatchDuration(int threshold) {
		// initialize a list of ints the same size as the Movie list
		List<Integer> percents = DSAFactory.getIndexedList();
		// For each Movie in Movie record
		for (Movie m : movieFile) {
			// initialize a dummy variable (ratio) to 0
			int ratio = 0;
			// check each entry in the watch record
			for (WatchRecord w : historyFile) {
				// if the current movies id matches the id of the current entry in the watch record
				if (w.getMovieId().equals(m.getId())) {
					// compute the ratio of the minutes watched field of the current entry in the watch record to the current movies minutes watched field
					double value = (((double)w.getWatchTime()) / ((double)m.getRuntime())) * 100;
					int percentComp = (int)value; 
					// if percentComp is above threshold, set ratio to zero and go to next WatchHistory
					if (percentComp > threshold) {
						ratio = 0;
						break;
					}
					// if the ratio is below threshold parameter
					if (percentComp < threshold) {
						// redefine ratio variable by computing the maximum of this ratio and the one before it
						if (percentComp > ratio) {
							ratio = percentComp;
						}
					}
				}
			}
			// add dummy variable to the list of ints
			percents.addLast((Integer)ratio);
		}
		// turn list into an array
		UnfinishedMovie[] movies = new UnfinishedMovie[percents.size()];
		for (int i = 0; i < percents.size(); i++) {
			// if the percent completion in the percents list in non zero, add it to the array
			movies[i] = new UnfinishedMovie(percents.get(i), movieFile.get(i));
		}
		// sort the array
		Sorter<UnfinishedMovie> sorter = DSAFactory.getComparisonSorter();
		sorter.sort(movies);
		// convert the array back into a list
		List<Movie> newList = DSAFactory.getIndexedList();
		for (int i = 0; i < movies.length; i++) {
			if (movies[i].getPercentComp() != 0) {
				newList.addLast(movies[i].getMovie());
			}
		}
		return newList;
	}
	
	
	/**
	 * Inner class used to assist in sorting by getMostFrequentlyWatchedMovies method
	 * 
	 * @author Alex Raum
	 */
	private class FrequentMovie implements Comparable<FrequentMovie> {
		
		/** An Integer object */
		private Integer frequency;
		/** A Movie object */
		private Movie movie;
		
		/**
		 * The FrequentMovie construtor
		 * 
		 * @param frequency the frequency a movie was watched
		 * @param movie the movie that was watched
		 */
		public FrequentMovie(Integer frequency, Movie movie) {
			setFrequency(frequency);
			setMovie(movie);
		}
		
		/**
		 * Returns the frequency
		 * 
		 * @return the frequency
		 */
		public Integer getFrequency() {
			return this.frequency;
		}
		
		/**
		 * Returns the movie
		 * 
		 * @return the movie
		 */
		public Movie getMovie() {
			return this.movie;
		}
		
		/**
		 * Sets the frequency to the specified frequency
		 * 
		 * @param frequency the frequency to set the field to
		 */
		public void setFrequency(Integer frequency) {
			this.frequency = frequency;
		}
		
		/**
		 * Sets the movie to the specified movie
		 * 
		 * @param movie the movie to set the field to
		 */
		public void setMovie(Movie movie) {
			this.movie = movie;
		}
		
		// Do we need multiple comparators? (one for each use case?)
	    // How can we use an inner class that deals with maps if some of
		// our methods use lists instead of maps?
		/**
		 * Defines a relative ordering between two FrequentMovie objects
		 * 
		 * @param f object to compare this object to
		 */
		public int compareTo(FrequentMovie f) {
			int freqCompare = frequency.compareTo(f.getFrequency());
			// May need to change from compareToIgnoreCase to compareTo
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
	
	/**
	 * Inner class used to assist in sorting by getMoviesByWatchDuration method
	 * 
	 * @author Alex Raum
	 */
	private class UnfinishedMovie implements Comparable<UnfinishedMovie> {
		
		/** An Integer object */
		private Integer percentComp;
		/** A Movie object */
		private Movie movie;
		
		/**
		 * The UnfinishedMovie constructor
		 * 
		 * @param percentComp the percentage of the movie that was watched
		 * @param movie the movie that was partially watched
		 */
		public UnfinishedMovie(Integer percentComp, Movie movie) {
			setPercentComp(percentComp);
			setMovie(movie);
		}
		
		/**
		 * Returns the percentComp
		 * 
		 * @return the percentComp
		 */
		public Integer getPercentComp() {
			return this.percentComp;
		}
		
		/**
		 * Returns the movie
		 * 
		 * @return the movie
		 */
		public Movie getMovie() {
			return this.movie;
		}
		
		/**
		 * Sets the percentComp to the specified percentComp
		 * 
		 * @param percentComp the percentComp to set the field to
		 */
		public void setPercentComp(Integer percentComp) {
			this.percentComp = percentComp;
		}
		
		/**
		 * Sets the movie to the specified movie
		 * 
		 * @param movie the movie to set the field to
		 */
		public void setMovie(Movie movie) {
			this.movie = movie;
		}
		
		// Do we need multiple comparators? (one for each use case?)
	    // How can we use an inner class that deals with maps if some of
		// our methods use lists instead of maps? 
		/**
		 * Defines a relative ordering between two UnfinishedMovie objects
		 * 
		 * @param u object to compare this object to
		 */
		public int compareTo(UnfinishedMovie u) {
			int percentCompare = percentComp.compareTo(u.getPercentComp());
			// May need to change from compareToIgnoreCase to compareTo
			int titleCompare = movie.getTitle().compareToIgnoreCase(u.getMovie().getTitle());
			int idCompare = movie.getId().compareToIgnoreCase(u.getMovie().getId());
			
			if (percentCompare < 0) {
				return 1;
			} else if (percentCompare > 0) {
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
	
	/**
	 * Inner class used to assist in sorting by getWatchFrequency method
	 * 
	 * @author Alex Raum
	 */
	private class WatchHistory implements Comparable<WatchHistory> {
		
		/** A WatchRecord object */
		private WatchRecord record;
		
		/**
		 * The WatchHistory constructor
		 * 
		 * @param record the WatchRecord to set the field to
		 */
		public WatchHistory(WatchRecord record) {
			setRecord(record);
		}
		
		/**
		 * Returns the record 
		 * 
		 * @return the record
		 */
		public WatchRecord getRecord() {
			return this.record;
		}
		
		/**
		 * Sets the record to the specified record
		 * 
		 * @param record the record to set the field to
		 */
		public void setRecord(WatchRecord record) {
			this.record = record;
		}
		
		// Do we need multiple comparators? (one for each use case?)
	    // How can we use an inner class that deals with maps if some of
		// our methods use lists instead of maps?
		/**
		 * Defines a relative ordering between two WatchHistory objects
		 * 
		 * @param w object to compare this object to
		 */
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