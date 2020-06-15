import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * <h1>Class that parses the form query</h1>
 * @author Miguel
 *
 */
public class QueryParser {

	/**
	 * <p>Field that defines a Factory of Region objects</p>
	 */
	public static RegiaoFactory regiaoFactory = new RegiaoFactory();

	/**
	 * <p>Function that parses the form query and outputs the query result obtained in the function {@link #parseString(String)} inside an HTML div tag.</p>
	 * @param form_data - Form Data
	 * @return String containing form query result inside an HTML div tag
	 *
	 */
	public static String parseForm(Hashtable form_data) {
		String returnString = new String();
		// Gets query data from form
		for (Enumeration e = form_data.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			String value = (String) form_data.get(key);
			returnString += value;
		}
		returnString = parseString(returnString);
		return "<div>" + returnString + "</div>";
	}

	/**
	 * <h1>Function that receives a query string and outputs the query results</h1>
	 * <p>If the query begins with the string "Regiao", the query is further processed in the function {@link #parseRegiao(String[])}</p>
	 * <p>If the query begins with one of the Enumerated Type Attributes, the query is further processed in the function {@link #parseAttributes(String[], String)} </p>
	 * @param value - String containing the query
	 * @return The query results
	 */
	private static String parseString(String value) {
		String[] query = value.split(" ");
		String queryResult = new String();
		String firstWord = query[0];
		// Query can start with the Keyword "Regiao"
		if (firstWord.equals("Regiao")) {
			queryResult = parseRegiao(query);
		}
		// Query can start with any of the Keywords "Infecoes" "Internamentos" "Testes"
		else if (Enums.Attributes.isAttributes(firstWord)) {
			queryResult = parseAttributes(query, null);
		} else {
			queryResult = "Query Error!";
		}

		return queryResult;
	}

	/**
	 * <h1>Function that receives a query pertaining to the keyword Regiao and outputs the query result</h1>
	 * <p>If the query contains only one word, the function will output a string containing a list of all the Regions</p>
	 * <p>If the query contains the relational operator "=", the function will apply all conditions on the query, if any, to each region in the query, using the function {@link #parseAttributes(String[], String)}</p>
	 * <p>Otherwise, the function will output a warning string saying that the query was created incorrectly</p>
	 * @param query - String array containing each query's elements
	 * @return The query results
	 */
	private static String parseRegiao(String[] query) {
		String response = new String();
		if (query.length == 1) {
			// Lists all regions
			response += "<h3>Lista de todas as regioes: </h3>";
			for (Regiao regiao : regiaoFactory.regioes) {
				response += "<p>" + regiao.getNome() + "</p>";
			}
		} 
		else if (query[1].equals(Enums.RelationalOperators.EQ.toString())) {
			List<String> regionsToQuery = new ArrayList<String>();
			int attributeIndex = 0;
			for (int i = 0; i != query.length; i++) {
				if (Enums.Regions.isRegions(query[i])) {
					regionsToQuery.add(query[i]);
				} else if (Enums.Attributes.isAttributes(query[i])) {
					attributeIndex = i;
					break;
				}
			}
			if(attributeIndex > 0) {
				String[] conditions = Arrays.copyOfRange(query, attributeIndex, query.length);
				for (String region : regionsToQuery) {
					response += "<h3>Resultados para a regiao: " + region + "</h3>";
					response += parseAttributes(conditions, region);
				}
			} else {
				response += "<p>Query mal construida!</p>";
			}
		}
		return response;
	}

	/**
	 * <h1>Function that receives a query pertaining to the Enumerated Types Attributes and outputs the query result</h1>
	 * <p>The function relies on a boolean array. This array will represent all the query elements that have been parsed and tested</p>
	 * <p>The function will iterate over the query array and check for either conditions or lonely attributes</p>
	 * <p>The conditions are parsed using the function {@link #parseCondition(String, String, String, String)}</p>
	 * <p>The single attribute will be parsed using the function {@link #parseAttribute(String, String)}</p>
	 * <p>After parsing and testing either the condition or attribute it finds, it will mark them as done on the boolean array</p>
	 * @param query - String Array containing the query to apply on regionName, if regionName is not null
	 * @param regionName - String that represents the regionName. Usually it will be a null value
	 * @return The query results
	 */
	private static String parseAttributes(String[] query, String regionName) {
		String response = new String();
		List<String> responses = new ArrayList<String>();
		if (query.length == 1) {
			responses.add(parseAttribute(query[0], regionName));
		}
		boolean[] checked = new boolean[query.length];
		Arrays.fill(checked, false);
		for (int i = 0; i != query.length; i++) {
			if (query.length == 1)
				break;
			if (i == 0) {
				if (Enums.RelationalOperators.isRelationalOperator(query[i + 1]) && checked[i] == false
						&& checked[i + 1] == false) {
					responses.add(parseCondition(query[i], query[i + 1], query[i + 2], regionName));
					checked[i] = true;
					checked[i + 1] = true;
					checked[i + 2] = true;
				} else if (checked[i] == false) {
					responses.add(parseAttribute(query[i], regionName));
					checked[i] = true;
				}
			} else if (i == query.length - 1) {
				if (checked[i] == false) {
					responses.add(parseAttribute(query[i], regionName));
					checked[i] = true;
				}
			} else if (Enums.RelationalOperators.isRelationalOperator(query[i]) && checked[i] == false) {
				responses.add(parseCondition(query[i - 1], query[i], query[i + 1], regionName));
				checked[i] = true;
				checked[i - 1] = true;
				checked[i + 1] = true;
			} else if (!Enums.RelationalOperators.isRelationalOperator(query[i - 1])
					&& !Enums.RelationalOperators.isRelationalOperator(query[i + 1]) && checked[i] == false) {
				responses.add(parseAttribute(query[i], regionName));
				checked[i] = true;
			}

		}
		for (String string : responses)
			response += string;
		return response;
	}

	/**
	 * <h1>Function that parses and outputs the condition results</h1>
	 * <p>The function will test if operand2 is numerical or belongs to the Enumerated Type Attributes</p>
	 * <p>If operand2 is numerical, the condition output comes from the function {@link RegiaoFactory#compareAttributeWith(String, String, int)}</p>
	 * <p>If operand2 is an attribute, the condition output comes from the function {@link RegiaoFactory#compareAttributeWith(String, String, String)}</p>
	 * <p>If the query structure is not respected, the output will be a warning string</p>
	 * @param operand1 - Attribute that represents the first operand in the condition. Is an attribute
	 * @param relationalOperator - Attribute that represents the relational operator to use on the condition
	 * @param operand2 - Attribute that represents the second operand in the condition. May be numerical or an attribute
	 * @param regionName - Attribute that represents a region's name. Works as a way to detect poorly built queries
	 * @return The condition result
	 */
	private static String parseCondition(String operand1, String relationalOperator, String operand2,
			String regionName) {
		String parsed = new String();
		if(regionName == null) {
			Enums.Attributes toCompare = Enums.Attributes.getAttributes(operand2);
			if (toCompare == null) {
				// operand2 is numeric
				List<Regiao> result = regiaoFactory.compareAttributeWith(operand1, relationalOperator,
						Integer.parseInt(operand2));
				for (Regiao regiao : result)
					parsed += "<p>" + regiao.getNome() + ": " + operand1 + ": " + regiao.get(operand1) + "</p>";
			} else {
				// operand2 is another Attribute
				List<Regiao> result = regiaoFactory.compareAttributeWith(operand1, relationalOperator, operand2);
				for (Regiao regiao : result)
					parsed += "<p>" + regiao.getNome() + ": " + operand1 + ": " + regiao.get(operand1) + "</p>";
			}
		} else {
			parsed += "<p>Query mal escrita!</p>";
		}
		return parsed;
	}

	/**
	 * <h1>Function that parses and outputs the attribute query results</h1>
	 * <p>The function will test if regionName is null</p>
	 * <p>If regionName is null, the function will search for attribute on all regions</p>
	 * <p>If it is not, the function will search for attribute on that region</p>
	 * @param attribute - String that represents the region's attribute to search for.
	 * @param regionName - String that represents the region's name. May be null in some cases.
	 * @return The query results
	 */
	private static String parseAttribute(String attribute, String regionName) {
		String parsed = new String();
		if (regionName == null) {
			for (Regiao regiao : regiaoFactory.regioes) {
				parsed += "<p>" + regiao.getNome() + ": " + attribute + ": " + regiao.get(attribute) + "</p>";
			}
		} else {
			Regiao regiao = regiaoFactory.getRegiao(regionName);
			parsed += "<p>" + regiao.getNome() + ": " + attribute + ": " + regiao.get(attribute) + "</p>";
		}
		return parsed;
	}

}
