import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

/**
 * <h1>Class that represents a Region</h1>
 * @author Miguel
 *
 */
public class Regiao {

	/**
	 * <p>Attribute that represents the region's name</p>
	 */
	private String nome;
	/**
	 * <p>Attribute that represents the number of infected people in the region</p>
	 */
	private int infecoes;
	/**
	 * <p>Attribute that represents the number of hospitalized people in the region</p>
	 */
	private int internamentos;
	/**
	 * <p>Attribute that represents the number of tested people in the region</p>
	 */
	private int testes;
	
	/**
	 * <p>Attribute that represents the XPath object to query XML files with.</p>
	 */
	private XPath xpath;
	/**
	 * <p>Attribute that represents the XML file.</p>
	 */
	private Document doc;

	
	/**
	 * <h1>Class constructor that sets up the class attributes.</h1>
	 * <p>Sets up the query language using the method {@link #setup()}</p>
	 * <p>Uses the method {@link #queryValues(String)} to initialize each region's attributes with XML file values</p>
	 * @param nome - The name for the region
	 */
	public Regiao(String nome) {
		setup();
		this.nome = nome;
		this.infecoes = queryValues("Infecoes");
		this.internamentos = queryValues("Internamentos");
		this.testes = queryValues("Testes");
	}

	/**
	 * @param infecoes - Number of infected people of the region
	 */
	public void setInfecoes(int infecoes) {
		this.infecoes = infecoes;
	}


	/**
	 * @param internamentos -Number of hospitalized people of the region
	 */
	public void setInternamentos(int internamentos) {
		this.internamentos = internamentos;
	}


	/**
	 * @param testes - Number of tested people of the region
	 */
	public void setTestes(int testes) {
		this.testes = testes;
	}


	/**
	 * @return The name of the region
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return The number of infected people of the region
	 */
	public int getInfecoes() {
		return infecoes;
	}
	
	/**
	 * @return The number of hospitalized people of the region
	 */
	public int getInternamentos() {
		return internamentos;
	}

	/**
	 * @return The number of tested people of the region
	 */
	public int getTestes() {
		return testes;
	}

	/**
	 * <h1>Method that sets up {@link #doc} and {@link #xpath}</h1>
	 * <p>Looks for the XML file and creates the necessary tools to query the document</p>
	 */
	private void setup() {
		try {
			File inputFile = new File(RegiaoFactory.folder + "/" + RegiaoFactory.file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			this.doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			XPathFactory xpathFactory = XPathFactory.newInstance();
			this.xpath = xpathFactory.newXPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>Using {@link #xpath}, the document {@link #doc} is queried for the field value</p>
	 * @param field - Represents the field for which the value is requested.
	 * @return The field value
	 */
	private int queryValues(String field) {
		String value = null;
		try {
			String query = "//*[contains(@about,'" + this.nome + "')]/" + field + "/text()";  
			XPathExpression expr = xpath.compile(query);
			value = (String)expr.evaluate(doc, XPathConstants.STRING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(value.equals(""))
			return 0;
		else return Integer.parseInt(value);
	}

	/**
	 *@return A prettified string containing the region's name and each value
	 */
	@Override
	public String toString() {
		return "Regiao: " + nome + " | Infeções: " + infecoes + " | Internamentos: " + internamentos + " | Teste: " + testes;
	}
	
	/**
	 * <h1>Method that gets the value corresponding to the required attribute</h1>
	 * <p>If attribute is "Internamentos", it gets the value using the method {@link #getInternamentos()}</p>
 	 * <p>If attribute is "Testes", it gets the value using the method {@link #getTestes()}}</p>
 	 * <p>If attribute is "Infecoes", it gets the value using the method {@link #getInfecoes()}</p>
	 * @param attribute - The attribute for which the value is required
	 * @return The value corresponding to the attribute
	 */
	public int get(String attribute) {
		int value = 0;
		switch (attribute) {
		case "Internamentos":
			value = getInternamentos();
			break;
		case "Testes":
			value = getTestes();
			break;
		case "Infecoes":
			value = getInfecoes();
			break;
		}
		return value;
	}
	
}
