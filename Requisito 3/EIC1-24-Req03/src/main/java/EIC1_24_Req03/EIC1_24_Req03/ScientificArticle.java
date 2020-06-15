package EIC1_24_Req03.EIC1_24_Req03;

import java.util.ArrayList;

/**
 * <h1>Scientific Article</h1>
 * <p>This class represents a Scientific Article's metadata.</p>
 * 
 * @author MargaridaCosta
 */
public class ScientificArticle {

	/**
	 * <h3>Article Title</h3>
	 * <p>This attribute represents the title of the scientific article.</p>
	 */
	private String articleTitle;
	/**
	 * <h3>Article Hyperlink</h3>
	 * <p>This attribute represents the hyperlink that makes the scientific article
	 * accessible.</p>
	 */
	private String articleHyperlink;
	/**
	 * <h3>Journal Name</h3>
	 * <p>This attribute represents the name of the journal that published the
	 * scientific article.</p>
	 */
	private String journalName;
	/**
	 * <h3>Publication Year</h3>
	 * <p>This attribute represents the year when the scientific article was published.</p>
	 */
	private String publicationYear;
	/**
	 * <h3>Author names list</h3>
	 * <p>This attribute represents the list of the authors of the scientific article.</p>
	 */
	private ArrayList<String> authorNames;

	/**
	 * <h3>Scientific Article Constructor</h3>
	 * <p>Creates the object ScientificArticle with the following parameters.</p>
	 * 
	 * @param articleTitle     - title of the scientific article
	 * @param articleHyperlink - hyperlink that makes the scientific article
	 *                         accessible
	 * @param jounalName       - name of the journal that published the scientific
	 *                         article
	 * @param publicationYear  - year when the scientific article was published
	 * @param authors          - list of the authors of the scientific article
	 */
	public ScientificArticle(String articleTitle, String articleHyperlink, String jounalName, String publicationYear,
			ArrayList<String> authors) {
		this.articleTitle = articleTitle;
		this.articleHyperlink = articleHyperlink;
		this.journalName = jounalName;
		this.publicationYear = publicationYear;
		this.authorNames = authors;
	}

	/**
	 * <h3>Get the Title for the Article</h3>
	 * @return The title defined for this scientific article
	 */
	public String getArticleTitle() {
		return articleTitle;
	}

	/**
	 * <h3>Get the Journal Name</h3>
	 * @return The name of the journal that published the scientific article
	 */
	public String getJournalName() {
		return journalName;
	}

	/**
	 * <h3>Get the Publication Year</h3>
	 * @return The year when the scientific article was published
	 */
	public String getPublicationYear() {
		return publicationYear;
	}

	/**
	 * <h3>Get the author's name list</h3>
	 * @return The list of the authors who participated in the writing of this
	 *         scientific article
	 */
	public String getAuthors() {
		String names = "";
		for (String author : authorNames) {
			if (authorNames.get(authorNames.size() - 1).equals(author)) {
				names += author;
			} else {
				names += author + ", ";
			}
		}
		return names;
	}

	/**
	 * <h3>Get the Article Hyperlink</h3>
	 * @return The hyperlink that makes the scientific article accessible
	 */
	public String getArticleHyperlink() {
		return articleHyperlink;
	}

	/**
	 * <h3>Format information into XML type</h3>
	 * <p>Gathers all the Scientific Article's metadata in a string in a XML formatting style.</p>
	 * @return String - Content of the Scientific Article in XML format.
	 */
	public String toXML() {
		return "<Article>\r\n" + "<articleTitle>" + articleTitle + "</articleTitle>\r\n" + "<articleHyperlink>"
				+ articleHyperlink + "</articleHyperlink>\r\n" + "<journalName>" + journalName + "</journalName>\r\n"
				+ "<publicationYear>" + publicationYear + "</publicationYear>\r\n" + "<authorNames>" + authorNames
				+ "</authorNames>\r\n" + "</Article>\r\n";
	}

}
