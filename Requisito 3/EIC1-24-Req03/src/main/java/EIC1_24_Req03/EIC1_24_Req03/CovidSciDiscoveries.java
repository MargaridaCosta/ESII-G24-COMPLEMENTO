package EIC1_24_Req03.EIC1_24_Req03;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.metadata.model.DateType;
import pl.edu.icm.cermine.metadata.model.DocumentAuthor;
import pl.edu.icm.cermine.metadata.model.DocumentMetadata;

/**
 * <h1>Covid19 Scientific Discoveries</h1>
 * <p>
 * This class serves the purpose of reading PDF files from the
 * ScientificArticles folder and build an HTML table with the extracted metadata
 * from those files.
 * </p>
 * 
 * @author MargaridaCosta
 */
public class CovidSciDiscoveries {

	/**
	 * <h3>Article List</h3>
	 * <p>
	 * List to be populated with all details from the articles in the
	 * ScientificArticles folder.
	 * </p>
	 */
	private static ArrayList<ScientificArticle> articleList = new ArrayList<ScientificArticle>();

	/**
	 * <h3>Temporary Article List</h3>
	 * <p>
	 * List to be populated (by the method {@link #extractXMLFileMetadata(File)})
	 * with all the details from the articles with metadata inside the XML file.
	 * </p>
	 */
	private static ArrayList<ScientificArticle> tempArticleList = new ArrayList<ScientificArticle>();

	/**
	 * <h3>Main method</h3>
	 * <p>
	 * First, the metadata inside the XML file from the resource folder is
	 * extracted, if exists ({@link #extractXMLFileMetadata(File)}).
	 * </p>
	 * <p>
	 * Secondly, each PDF file inside the resource folder is verified to check if
	 * its metadata was already extracted via XML file or still needs to be read
	 * ({@link #verifyScientificArticles(String)}).
	 * </p>
	 * <p>
	 * Then, with all the information read the HTML table can be created with all
	 * the information ({@link #createHTMLTable()}).
	 * </p>
	 * <p>
	 * Finally, a XML file is created with all the data present in the HTML table to
	 * aid faster consulting in the future ({@link #createXMLFile(String)}).
	 * </p>
	 * 
	 * @param args - first argument references the resource folder path (folder with
	 *             the Scientific Article PDF files)
	 * 
	 * @return String with the resulting HTML code that forms the table with the
	 *         data
	 */
	public static void main(String[] args) {
		File xmlFile = new File(args[0] + "/ArticleMetadata.xml");
		extractXMLFileMetadata(xmlFile);
		verifyScientificArticles(args[0]);
		System.out.println(createHTMLTable());
		createXMLFile(args[0]);
	}

	/**
	 * <h3>Read a PDF file</h3>
	 * <p>
	 * Method that uses CERMINE to read a PDF file and get its metadata. The
	 * metadata extracted is stored in the {@link #articleList}, according to the
	 * parsed fields.
	 * </p>
	 * 
	 * @param file - PDF file to be read from ScientificArticle folder
	 * 
	 */
	private static void readPdfFile(File file) {
		try {
			ContentExtractor extractor = new ContentExtractor();
			FileInputStream inputStream = new FileInputStream(file);
			extractor.setPDF(inputStream);
			DocumentMetadata result = extractor.getMetadata();
			ArrayList<String> authorList = new ArrayList<String>();
			for (DocumentAuthor da : result.getAuthors()) {
				authorList.add(da.getName());
			}
			ScientificArticle article = new ScientificArticle(result.getTitle(), file.getAbsolutePath().toString(),
					result.getJournal(), result.getDate(DateType.PUBLISHED).getYear(), authorList);
			articleList.add(article);
		} catch (Exception e) {
			System.out.println("<h4>An exception occured while trying to read a PDF file</h4>");
		}
	}

	/**
	 * <h3>Verify all Scientific Articles inside the resource folder</h3>
	 * <p>
	 * Method that runs {@link #readPdfFile(File)} for all the PDF files inside
	 * ScientificArticles folder that are not present in the XML file.
	 * </p>
	 * 
	 * @param folderName - name of the folder with the resources.
	 */
	private static void verifyScientificArticles(String folderName) {
		try {
			File folder = new File(folderName);
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				if (!isPDFFileInArticleList(file) && file.getName().endsWith(".pdf")) {
					readPdfFile(file);
				}
			}
		} catch (Exception e) {
			System.out.println(
					"<h4>Something went wrong while accessing the files in this folder: " + folderName + "</h4>");
		}
	}

	/**
	 * <h3>Verify if article is already inside the list</h3>
	 * <p>
	 * Function that verifies if the metadata of a PDF file is already in
	 * {@link #tempArticleList}.
	 * </p>
	 * <p>
	 * If it is, it means that the metadata of that PDF file was previously inside
	 * the XML file.
	 * </p>
	 * 
	 * @param file - PDF file to be verified.
	 * @return if the metadata of that PDF file is already in
	 *         {@link #tempArticleList}.
	 */
	private static boolean isPDFFileInArticleList(File file) {
		boolean found = false;
		for (ScientificArticle article : tempArticleList) {
			if (file.getAbsolutePath().equals(article.getArticleHyperlink())) {
				found = true;
				articleList.add(article);
			}
		}
		return found;
	}

	/**
	 * <h3>Create an HTML Table</h3>
	 * <p>
	 * This function creates an HTML table with some styling and cycles through the
	 * data on {@link #articleList} to form the lines of the table with information
	 * from the articles.
	 * </p>
	 * 
	 * @return String with all the HTML code to form a table with the data from
	 *         {@link #articleList}}.
	 */
	private static String createHTMLTable() {
		String html = "Content-type: text/html\n\n"+"<!DOCTYPE html>\r\n" + 
				"<html><head>\r\n" + "<style>\r\n" + "table {\r\n" + "  font-family: arial, sans-serif;\r\n"
				+ "  color: #362a2c;\r\n" + "  border-collapse: collapse;\r\n" + "  width: 100%;\r\n" + "}\r\n" + "\r\n"
				+ "td, th {\r\n" + "  border: 1px solid #c9dfff;\r\n" + "  text-align: left;\r\n"
				+ "  padding: 8px;\r\n" + "}\r\n" + "\r\n" + "tr:nth-child(odd) {\r\n"
				+ "  background-color: #e3eeff;\r\n" + "}\r\n" + "\r\n" + "tr:nth-child(even) {\r\n"
				+ "  background-color: #c9dfff;\r\n" + "}\r\n" + "</style>\r\n" + "</head>" + "<body>"
				+ "<h2 style=\"text-align: center;color: #362a2c;\">Covid19 Scientific Discoveries</h2>" + "<table>"
				+ "<tr>\r\n" + "<th>Article Title</th>\r\n" + "<th>Journal name</th>\r\n"
				+ "<th>Publication year</th>\r\n" + "<th>Authors</th>\r\n" + "</tr>";
		for (ScientificArticle article : articleList) {
			html += "<tr>\r\n" + "<th> <a href=\"" + article.getArticleHyperlink().replace("var/www/html/","") + "\" target=\"_blank\">"
					+ article.getArticleTitle() + "</a></th>\r\n" + "<th>" + article.getJournalName() + "</th>\r\n"
					+ "<th>" + article.getPublicationYear() + "</th>\r\n" + "<th>" + article.getAuthors() + "</th>\r\n"
					+ "</tr>";
		}
		html += "</table>\r\n" + "</body></html>";
		return html;
	}

	/**
	 * <h3>Extract Data from XML file</h3>
	 * <p>
	 * Method that summons a XML parser and runs through the XML file fetching
	 * information to populate {@link #tempArticleList}, if the XML file is not
	 * empty.
	 * </p>
	 * 
	 * @param xmlFile - File containing Scientific Article metadata inside the
	 *                resource folder.
	 */
	private static void extractXMLFileMetadata(File xmlFile) {
		if (xmlFile.length() != 0) {
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(xmlFile);
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("Article");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						storeXMLArticleInfo(nNode);
					}
				}
			} catch (Exception e) {
				System.out.println("A problem occured while extracting the XML data.");
			}
		}
	}

	/**
	 * <h3>Store parsed metadata from a Scientific Article</h3>
	 * <p>
	 * Method that inserts a Scientific Article into {@link #tempArticleList} by
	 * identifying the tags for the corresponding attributes inside the object.
	 * </p>
	 * 
	 * @param node - Node that contains the information of a Scientific Article.
	 */
	private static void storeXMLArticleInfo(Node node) {
		Element eElement = (Element) node;
		String title = eElement.getElementsByTagName("articleTitle").item(0).getTextContent();
		String hyperlink = eElement.getElementsByTagName("articleHyperlink").item(0).getTextContent();
		String journalName = eElement.getElementsByTagName("journalName").item(0).getTextContent();
		String publicationYear = eElement.getElementsByTagName("publicationYear").item(0).getTextContent();
		String authorNames = eElement.getElementsByTagName("authorNames").item(0).getTextContent();
		authorNames = authorNames.substring(1, authorNames.length() - 1);
		String[] authors = authorNames.split(", ");
		ArrayList<String> authorList = new ArrayList<String>(Arrays.asList(authors));
		ScientificArticle article = new ScientificArticle(title, hyperlink, journalName, publicationYear, authorList);
		tempArticleList.add(article);
	}

	/**
	 * <h3>Create a XML File with all the data</h3>
	 * <p>
	 * This method runs through the {@link #articleList} adding to the basis of the
	 * XML file all the details about each article. Then opens the file or creates a
	 * new one if it doesn't exist and inputs the XML code created.
	 * </p>
	 * 
	 * @param folderName - name of the folder with all the resources.
	 */
	private static void createXMLFile(String folderName) {
		try {
			String xmlData = "<?xml version = \"1.0\" encoding=\"UTF-8\" ?>\r\n" + "<ScientificArticle>\r\n";
			for (ScientificArticle article : articleList) {
				xmlData += article.toXML();
			}
			xmlData += "</ScientificArticle>";
			File xmlFile = new File(folderName + "/ArticleMetadata.xml");
			FileWriter myWriter = new FileWriter(xmlFile);
			myWriter.write(xmlData);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("There was an error creating the XML file!");
		}
	}
}
