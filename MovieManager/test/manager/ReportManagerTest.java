package manager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
	/** The Movie path */
	private String MOVIE = "input/movieRecord_sample.csv";
	/** The WatchRecord path */
	private String WATCH = "input/watchRecord_sample.csv";
	/** String used for formatting */
	private static final String INDENT = "   ";
	
	/**
	 * Initializes the fields
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		manager = new ReportManager(MOVIE, WATCH);
	}

	/**
	 * Tests the functionality of the getTopMoviesReport method
	 */
	@Test
	public void testGetTopMoviesReport() {
		String report = manager.getTopMoviesReport(3);
		assertEquals(report, INDENT + "Pete's Dragon\n" + INDENT + "Guardians of the Galaxy\n" + INDENT + "Hidden Figures\n");
	}
	
	/**
	 * Tests the functionality of the getMovieCompletionReport method
	 */
	@Test
	public void testGetMovieCompletionReport() {
		String report = manager.getMovieCompletionReport(90);
		assertEquals(report, INDENT + "The Martian\n" + INDENT + "The Great Wall\n" + INDENT + "Guardians of the Galaxy\n");
	}
	
	/**
	 * Tests the functionality of the getWatchDates method
	 */
	@Test
	public void testGetWatchDates() {
		String report = manager.getWatchDates("Pete's Dragon");
		assertEquals(report, INDENT + "2020-03-04\n" + INDENT + "2020-02-05\n" + INDENT + "2020-02-04\n" + INDENT + "2019-05-01\n");
	}

}
