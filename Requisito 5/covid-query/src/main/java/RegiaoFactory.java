import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * <h1>Class that represents a Factory of the Regiao object</h1>
 * @author Miguel
 *
 */
public class RegiaoFactory {
	/**
	 * <p>Field that represents the URI to the XML document repository</p>
	 */
	private static final String URI = "https://github.com/vbasto-iscte/ESII1920.git";
	/**
	 * <p>Field that represents the folder name</p>
	 */
	public static final String folder = "Rep";
	/**
	 * <p>Field that represents the file name</p>
	 */
	public static final String file = "covid19spreading.rdf";

	/**
	 * <p>Field that represents a list of all regions</p>
	 */
	public List<Regiao> regioes = new ArrayList<Regiao>();
	/**
	 * <p>Field that represents a list of all attributes</p>
	 */
	private static List<String> attributes = new ArrayList<String>();
	
	
	/**
	 * <h1>Constructor that sets up the class fields</h1>
	 * <p>Clones the Git Repository from the URI {@link #URI} using the method {@link #cloneGit()}</p>
	 * <p>Initializes the class fields with {@link #file} data using the method {@link #initializeData()}</p>
	 */
	public RegiaoFactory() {
		cloneGit();
		initializeData();
	}
	
	/**
	 * <h1>Method that creates a local directory by cloning the GitHub repository located on {@link #URI}</h1>
	 * <p>It will always delete the directory before cloning</p>
	 */
	private void cloneGit() {
		try {
			// Deletes the directory in order to always use fresh information
			FileUtils.deleteDirectory(new File(folder));

			// Using the JGit dependency, the repo is cloned
			Git.cloneRepository().setURI(URI).setDirectory(new File(folder)).call();
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	/**
	 * <h1>Initializes the class fields with {@link #file} data</h1>
	 * <p>Utilizes the XPath library and queries the document {@link #file} to obtain the class fields</p>
	 */
	private void initializeData() {
		try {
			File inputFile = new File(folder + "/" + file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			String query = "/RDF/NamedIndividual/@*";
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile(query);
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nl.getLength(); i++) {
				regioes.add(new Regiao(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#")));
			}

			query = "/RDF/DatatypeProperty/@*";
			expr = xpath.compile(query);
			NodeList nl2 = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nl2.getLength(); i++) {
				attributes.add(StringUtils.substringAfter(nl2.item(i).getNodeValue(), "#"));
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * Method that returns the Regiao object with the given name. If name does not exist, returns null
	 * @param name - Name of the requested region
	 * @return Regiao object
	 */
	public Regiao getRegiao(String name) {
		Regiao goal = null;
		for(Regiao regiao : regioes) {
			if(regiao.getNome().equals(name))
				goal = regiao;
		}
		return goal;
	}
	
	/**
	 * <h1>Method that compares the values of the operands</h1>
	 * <p>For each region in {@link #regioes} the method will test if the value of the region field corresponding to the first operand is equal the value of the region field corresponding to the second operand</p>
	 * <p>Second operand is always an Attribute</p>
	 * @param regionAttribute - Operand 1 of condition
	 * @param relationalOperator - Condition Operator
	 * @param toCompareAttribute - Operand 2 of condition - Is an Attribute
	 * @return - List containing the regions that matches the condition
	 */
	public List<Regiao> compareAttributeWith(String regionAttribute, String relationalOperator , String toCompareAttribute) {
		List<Regiao> list = new ArrayList<Regiao>();
		if (relationalOperator.equals(Enums.RelationalOperators.EQ.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) == regiao.get(toCompareAttribute)) {
					list.add(regiao);
				}
			}
		} else if (relationalOperator.equals(Enums.RelationalOperators.GT.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) > regiao.get(toCompareAttribute)) {
					list.add(regiao);
				}
			}
		} else if (relationalOperator.equals(Enums.RelationalOperators.GTE.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) >= regiao.get(toCompareAttribute)) {
					list.add(regiao);
				}
			}
		} else if (relationalOperator.equals(Enums.RelationalOperators.LT.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) < regiao.get(toCompareAttribute)) {
					list.add(regiao);
				}
			}
		} else if (relationalOperator.equals(Enums.RelationalOperators.LTE.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) <= regiao.get(toCompareAttribute)) {
					list.add(regiao);
				}
			}
		} else if (relationalOperator.equals(Enums.RelationalOperators.NE.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) != regiao.get(toCompareAttribute)) {
					list.add(regiao);
				}
			}
		}
		return list;
	}
	
	/**
	 * <h1>Method that compares the values of the operands</h1>
	 * <p>For each region in {@link #regioes} the method will test if the value of the region field corresponding to the first operand is equal the value of the second operand</p>
	 * <p>Second operand is always numeric</p>
	 * @param regionAttribute - Operand 1 of condition
	 * @param relationalOperator - Condition Operator
	 * @param toCompareValue - Operand 2 of condition Is numeric
	 * @return List containing the regions that matches the condition
	 */
	public List<Regiao> compareAttributeWith(String regionAttribute, String relationalOperator , int toCompareValue) {
		List<Regiao> list = new ArrayList<Regiao>();
		if (relationalOperator.equals(Enums.RelationalOperators.EQ.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) == toCompareValue) {
					list.add(regiao);
				}
			}
		} else if (relationalOperator.equals(Enums.RelationalOperators.GT.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) > toCompareValue) {
					list.add(regiao);
				}
			}
		} else if (relationalOperator.equals(Enums.RelationalOperators.GTE.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) >= toCompareValue) {
					list.add(regiao);
				}
			}
		} else if (relationalOperator.equals(Enums.RelationalOperators.LT.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) < toCompareValue) {
					list.add(regiao);
				}
			}
		} else if (relationalOperator.equals(Enums.RelationalOperators.LTE.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) <= toCompareValue) {
					list.add(regiao);
				}
			}
		} else if (relationalOperator.equals(Enums.RelationalOperators.NE.toString())) {
			for (Regiao regiao : regioes) {
				if (regiao.get(regionAttribute) != toCompareValue) {
					list.add(regiao);
				}
			}
		}
		return list;
	}
	
}
