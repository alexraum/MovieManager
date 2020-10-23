package manager;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.movie.data.WatchRecord;
import edu.ncsu.csc316.movie.manager.MovieManager.WatchHistory;
import edu.ncsu.csc316.movie.manager.ReportManager;

/**
 * ReportManagerTest checks the behavior of the methods in the
 * ReportManager class.
 * 
 * @author Alex Raum
 */
public class ReportManagerTest {

	/** A ReportManager object */
	private ReportManager manager;
//	/** The Movie path */
//	private String MOVIE = "input/movieRecord_sample.csv";
//	/** The WatchRecord path */
//	private String WATCH = "input/watchRecord_sample.csv";
	/** String used for formatting */
	private static final String INDENT = "   ";
	private WatchHistory history;
	private WatchHistory history2;
	
	/**
	 * Initializes the fields
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		manager = new ReportManager("input/movieRecord_sample.csv", "input/watchRecord_sample.csv");
	}

	/**
	 * Tests the functionality of the getTopMoviesReport method
	 */
	@Test
	public void testGetTopMoviesReport() {
		StringBuilder report = manager.getTopMoviesReport(3);
		System.out.print(report);
		assertEquals(report, "The 3 most frequently watched movies [\n" + INDENT + "Pete's Dragon (2016)\n" + INDENT + 
				"Guardians of the Galaxy (2014)\n" + INDENT + "Hidden Figures (2016)\n]");
	}
	
	/**
	 * Tests the functionality of the getMovieCompletionReport method
	 */
	@Test
	public void testGetMovieCompletionReport() {
		StringBuilder report = manager.getMovieCompletionReport(90);
		assertEquals(report, "The movies that have been watched less than 90% [\n" + INDENT + "The Martian (2015)\n" + INDENT + 
				"The Great Wall (2016)\n" + INDENT + "Guardians of the Galaxy (2014)\n]");
	}
	
	/**
	 * Tests the functionality of the getWatchDates method
	 */
	@Test
	public void testGetWatchDates() {
		StringBuilder report = manager.getWatchDates("Pete's Dragon");
		assertEquals(report, "The movie \"Pete's Dragon\" was streamed on [\n" + INDENT + "03/04/2020\n" + INDENT + 
				"02/05/2020\n" + INDENT + "02/04/2020\n" + INDENT + "05/01/2019\n]");
	}
	
	/**
	 * Tests the functionality of the getWatchDates method
	 */
	@Test
	public void testWatchHistory() {
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate date = LocalDate.parse("10/20/2020", sdf);
		WatchRecord record = new WatchRecord("Good Will Hunting", date, 20);
		WatchRecord record2 = new WatchRecord("Good Will Hunting", date, 30);
		history = new WatchHistory(record);
		history2 = new WatchHistory(record2);
		assertTrue(history.compareTo(history2) == 1);		
	}

}
