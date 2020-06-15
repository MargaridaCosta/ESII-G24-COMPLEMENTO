package EIC1_24_Req03.EIC1_24_Req03;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * <h1>Scientific Article Test Class</h1>
 * <p>
 * Class that tests {@link covidSciDiscoveries.ScientificArticle}.
 * </p>
 * 
 * @author MargaridaCosta
 */
public class ScientificArticleTest {

	/**
	 * <p>List to be filled before all tests with the author names.</p>
	 */
	static ArrayList<String> authorList = new ArrayList<String>();

	/**
	 * <p>Scientific Article to test various functionalities.</p>
	 */
	static ScientificArticle article;

	/**
	 * <p>Method to be executed before all tests in order to initiate the class attributes.</p>
	 */
	@BeforeAll
	static void setup() {
		authorList.add("John Snow");
		authorList.add("Daenerys Targaryen");
		article = new ScientificArticle("The final war", "books/historyOfWesteros", "HBO", "2019", authorList);
	}

	/**
	 * <p>Test method for
	 * {@link covidSciDiscoveries.ScientificArticle#ScientificArticle(String, String, String, String, java.util.ArrayList)}.</p>
	 */
	@Test
	void testScientificArticle() {
		ScientificArticle newArticle = new ScientificArticle("Beyond the Wall War", "books/historyOfWesteros", "HBO",
				"2017", authorList);
		assertEquals("Beyond the Wall War", newArticle.getArticleTitle());
		assertEquals("books/historyOfWesteros", newArticle.getArticleHyperlink());
		assertEquals("HBO", newArticle.getJournalName());
		assertEquals("2017", newArticle.getPublicationYear());
		ArrayList<String> newAuthorList = new ArrayList<String>();
		newAuthorList.add("John Snow");
		newAuthorList.add("Daenerys Targaryen");
		assertEquals(newAuthorList, newArticle.getAuthors());
	}

	/**
	 * <p>Test method for
	 * {@link covidSciDiscoveries.ScientificArticle#getArticleTitle()}.</p>
	 */
	@Test
	void testGetArticleTitle() {
		assertEquals("The final war", article.getArticleTitle());
	}

	/**
	 * <p>Test method for
	 * {@link covidSciDiscoveries.ScientificArticle#getJournalName()}.</p>
	 */
	@Test
	void testGetJournalName() {
		assertEquals("HBO", article.getJournalName());
	}

	/**
	 * <p>Test method for
	 * {@link covidSciDiscoveries.ScientificArticle#getPublicationYear()}.</p>
	 */
	@Test
	void testGetPublicationYear() {
		assertEquals("2019", article.getPublicationYear());
	}

	/**
	 * <p>Test method for {@link covidSciDiscoveries.ScientificArticle#getAuthors()}.</p>
	 */
	@Test
	void testGetAuthors() {
		ArrayList<String> authors = new ArrayList<String>();
		authors.add("John Snow");
		authors.add("Daenerys Targaryen");
		assertEquals(authors, article.getAuthors());
	}

	/**
	 * <p>Test method for
	 * {@link covidSciDiscoveries.ScientificArticle#getArticleHyperlink()}.</p>
	 */
	@Test
	void testGetArticleHyperlink() {
		assertEquals("books/historyOfWesteros", article.getArticleHyperlink());
	}

	/**
	 *<p>Test method for {@link covidSciDiscoveries.ScientificArticle#toXML()}.</p>
	 */
	@Test
	void testToXML() {
		String xml = "<Article>\r\n" + 
				"<articleTitle>The final war</articleTitle>\r\n" + 
				"<articleHyperlink>books/historyOfWesteros</articleHyperlink>\r\n" + 
				"<journalName>HBO</journalName>\r\n" + 
				"<publicationYear>2019</publicationYear>\r\n" + 
				"<authorNames>[John Snow, Daenerys Targaryen]</authorNames>\r\n" + 
				"</Article>";
		assertEquals(xml, article.toXML());
	}
}
