package manager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.movie.manager.ReportManager;

/**
 * 
 * @author Alex Raum
 */
public class ReportManagerTest {

	/** */
	private ReportManager manager;
	/** */
	private String MOVIE_PATH = "input\\movieRecord_sample.csv";
	/** */
	private String WATCH_PATH = "input\\watchRecord_sample.csv";
	/** */
	private static final String INDENT = "   ";
	
	/**
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		manager = new ReportManager(MOVIE_PATH, WATCH_PATH);
	}

	/**
	 * 
	 */
	@Test
	public void testGetTopMoviesReport() {
		String report = manager.getTopMoviesReport(3);
		assertEquals(report, INDENT + "Pete's Dragon\n" + INDENT + "Guardians of the Galaxy\n" + INDENT + "Hidden Figures\n");
	}
	
	/**
	 * 
	 */
	@Test
	public void testGetMovieCompletionReport() {
		String report = manager.getMovieCompletionReport(90);
		assertEquals(report, INDENT + "The Martian\n" + INDENT + "The Great Wall\n" + INDENT + "Guardians of the Galaxy\n");
	}
	
	/**
	 * 
	 */
	@Test
	public void testGetWatchDates() {
		String report = manager.getWatchDates("Pete's Dragon");
		assertEquals(report, INDENT + "2020-03-04\n" + INDENT + "2020-02-05\n" + INDENT + "2020-02-04\n" + INDENT + "2019-05-01\n");
	}

}
