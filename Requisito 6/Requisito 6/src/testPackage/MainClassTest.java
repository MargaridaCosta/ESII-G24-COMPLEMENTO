package testPackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mainPackage.MainClass;

/**
 * <h1>MainClass Test Class</h1>
 * <p>
 * Class that tests {@link #main}.
 * </p>
 * 
 * @author RicardoFaria
 */
class MainClassTest {

	String[] args;

	/**
	 * <p>
	 * Method to be executed before each test in order to initiate the class
	 * attributes.
	 * </p>
	 */
	@BeforeEach
	void setUp() throws Exception {
		String[] args = { "https://github.com/vbasto-iscte/ESII1920", "covid19spreading.rdf", "/rq6" };
		this.args = args;
		MainClass.deleteLocalRepo(args[2]);
		MainClass.cloneRemoteRepo(args[0], args[2]);
		MainClass.getLastestTwoTags();
		MainClass.saveLastestTwoFileVersions(args[1]);
		MainClass.renderHtml(args[1]);
	}

	/**
	 * <p>
	 * Test method for {@link main#main(String[])}.
	 * </p>
	 */
	@Test
	void testMain() {
		MainClass.main(args);
	}

}