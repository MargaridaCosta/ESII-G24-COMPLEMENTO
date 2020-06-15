import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * <h1>Class that generates HTML code</h1>
 * <p>Class that outputs an HTML Form when receiving a GET Request and the HTML Form response when receiving a POST Request</p>
 * @author Miguel
 * 
 *
 */
public class GenerateForm {

	public static void main(String[] args) {
		if (args[0].equals("GET")) {
			System.out.println(Header());
			System.out.println(HTML());

		} else if (args[0].equals("POST")) {
			ResponseHTML();
		}

	}

	/**
	 * <p>Function that returns a string containing HTTP Header</p>
	 * @return String containing HTTP Header
	 */
	private static String Header() {
		return "Content-type: text/html\n\n";
	}

	/**
	 * <p>Method that reads an HTML form through an InputStream and prints out the form response</p>
	 */
	private static void ResponseHTML() {
		Hashtable form_data = new Hashtable();
		form_data = ReadParse(System.in);
		System.out.println(Header());
		System.out.println("<!doctype html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n" + "<head>\r\n"
				+ "    <!-- Required meta tags -->\r\n" + "    <meta charset=\"utf-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n"
				+ "    <!-- Bootstrap CSS -->\r\n"
				+ "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\"\r\n"
				+ "        integrity=\"sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk\" crossorigin=\"anonymous\">\r\n"
				+ "    <title>G24-RQ5</title>\r\n" + "</head>");
		System.out.println("<body class=\"m-3 p-3\">");
		System.out.println(QueryParser.parseForm(form_data));
		System.out.println("</body> </html> ");
	}

	/**
	 * @return String containing the form page HTML code 
	 */
	private static String HTML() {
		return "<!doctype html>\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"\r\n" + 
				"<head>\r\n" + 
				"    <!-- Required meta tags -->\r\n" + 
				"    <meta charset=\"utf-8\">\r\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n" + 
				"    <!-- Bootstrap CSS -->\r\n" + 
				"    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\"\r\n" + 
				"        integrity=\"sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk\" crossorigin=\"anonymous\">\r\n" + 
				"    <title>G24-RQ5</title>\r\n" + 
				"</head>\r\n" + 
				"\r\n" + 
				"<body onload=\"enable(toEnableInitial)\">\r\n" + 
				"    <div class=\"container text-left mt-3 bg-light p-1\">\r\n" + 
				"        <h1>Instrucoes de utilizacao</h1>\r\n" + 
				"        <ul>\r\n" + 
				"            <li>\r\n" + 
				"                Para fazer queries sobre o numero de Testes/Infecoes/Internamentos usar\r\n" + 
				"                <ul>\r\n" + 
				"                    <li>Testes/Infecoes/Internamentos + Condicao</li>\r\n" + 
				"                    <li>Condicao pode usar um valor numerico ou Testes/Infecoes/Internamentos</li>\r\n" + 
				"                </ul>\r\n" + 
				"            </li>\r\n" + 
				"            <li>\r\n" + 
				"                Para fazer queries sobre uma da regiao deve declarar obrigatoriamente qual o dado que pretende ver\r\n" + 
				"                <ul>\r\n" + 
				"                    <li>Regiao para listar todas</li>\r\n" + 
				"                    <li>Regiao = \"Nome da Regiao\" Testes/Infecoes/Internamentos</li>\r\n" + 
				"                </ul>\r\n" + 
				"            </li>\r\n" + 
				"            <li>\r\n" + 
				"                Exemplos\r\n" + 
				"                <ul>\r\n" + 
				"                    <li>Regiao = Alentejo Testes</li>\r\n" + 
				"                    <ul>\r\n" + 
				"                        <li>Devolve os testes da regiao do Alentejo</li>\r\n" + 
				"                    </ul>\r\n" + 
				"                    <li>Internamento = Testes Infetados</li>\r\n" + 
				"                    <ul>\r\n" + 
				"                        <li>\r\n" + 
				"                            Devolve o nome das regioes e o numero de internamentos apenas para as regioes com o numero de internamentos igual ao numero de testes<br>\r\n" + 
				"                            Devolve o nome das regioes e o numero de infetados <br>\r\n" + 
				"                        </li>\r\n" + 
				"                    </ul>\r\n" + 
				"                </ul>\r\n" + 
				"            </li>\r\n" + 
				"        </ul>\r\n" + 
				"\r\n" + 
				"    </div>\r\n" + 
				"    <div class=\"container text-center mt-3\">\r\n" + 
				"        <div class=\"d-flex justify-content-center\">\r\n" + 
				"            <div id=\"ActionButtons\" class=\"col-md-7\">\r\n" + 
				"                <div class=\"d-flex justify-content-center\">\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button id=\"Alentejo\" class=\"btn btn-outline-primary w-100\">Alentejo</button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button id=\"Algarve\" class=\"btn btn-outline-primary w-100\">Algarve</button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button id=\"Centro\" class=\"btn btn-outline-primary w-100\">Centro</button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button id=\"Lisboa\" class=\"btn btn-outline-primary w-100\">Lisboa</button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button id=\"Norte\" class=\"btn btn-outline-primary w-100\">Norte</button>\r\n" + 
				"                    </div>\r\n" + 
				"                </div>\r\n" + 
				"                <div id=\"attributes\" class=\"d-flex justify-content-center mt-2\">\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button id=\"Regiao\" class=\"btn btn-outline-primary w-100\">Regiao</button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button id=\"Internamentos\" class=\"btn btn-outline-primary w-100\">Internamentos</button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button id=\"Infecoes\" class=\"btn btn-outline-primary w-100\">Infecoes</button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button id=\"Testes\" class=\"btn btn-outline-primary w-100\">Testes</button>\r\n" + 
				"                    </div>\r\n" + 
				"                </div>\r\n" + 
				"                <div id=\"relational\" class=\"d-flex justify-content-center mt-2\">\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button disabled=\"true\" id=\"GT\" class=\"btn btn-outline-primary w-100\">></button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button disabled=\"true\" id=\"GTE\" class=\"btn btn-outline-primary w-100\">>=</button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button disabled=\"true\" id=\"LT\" class=\"btn btn-outline-primary w-100\"><</button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button disabled=\"true\" id=\"LTE\" class=\"btn btn-outline-primary w-100\"><=</button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button disabled=\"true\" id=\"NE\" class=\"btn btn-outline-primary w-100\">!=</button>\r\n" + 
				"                    </div>\r\n" + 
				"                    <div class=\"col\">\r\n" + 
				"                        <button disabled=\"true\" id=\"EQ\" class=\"btn btn-outline-primary w-100\">=</button>\r\n" + 
				"                    </div>\r\n" + 
				"                </div>\r\n" + 
				"            </div>\r\n" + 
				"            <div class=\"col-md-3\">\r\n" + 
				"                <input id=\"Number\" type=\"number\" value=\"10\" class=\"form-control\">\r\n" + 
				"                <button disabled=\"true\" id=\"AddNumber\" class=\"btn btn-outline-primary w-100 mt-2\">Adicionar\r\n" + 
				"                    Numero</button>\r\n" + 
				"            </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"d-flex justify-content-center mt-4\">\r\n" + 
				"            <div class=\"col-md-7 px-4\">\r\n" + 
				"                <form id=\"form\" method=\"POST\" action=\"/cgi-bin/cgi-g24-rq5-parse.sh\">\r\n" + 
				"                    <textarea form=\"form\" name=\"query\" id=\"query\" style=\"resize: none;\"\r\n" + 
				"                        class=\"w-100 form-control border-primary m-1\" type=\"text\" readonly rows=\"4\"></textarea>\r\n" + 
				"                </form>\r\n" + 
				"            </div>\r\n" + 
				"            <div class=\"col-md-3\">\r\n" + 
				"                <button id=\"limpar\" class=\"btn btn-danger m-1 w-100\">Limpar</button>\r\n" + 
				"                <button id=\"submeter\" type=\"submit\" form=\"form\" class=\"btn btn-success m-1 w-100\">Submeter</button>\r\n" + 
				"            </div>\r\n" + 
				"        </div>\r\n" + 
				"\r\n" + 
				"    </div>\r\n" + 
				"    <script type=\"text/javascript\">\r\n" + 
				"        var input = new Array();\r\n" + 
				"        var ids = [\"Alentejo\", \"Algarve\", \"Centro\", \"Lisboa\", \"Norte\", \"Regiao\", \"Internamentos\", \"Infecoes\", \"Testes\", \"GT\", \"LT\", \"GTE\", \"LTE\", \"NE\", \"EQ\", \"AddNumber\"];\r\n" + 
				"\r\n" + 
				"        var toEnableInitial = [\"Regiao\", \"Internamentos\", \"Infecoes\", \"Testes\"];\r\n" + 
				"        var toEnableRegiao = [\"EQ\"];\r\n" + 
				"        var toEnableAttributes = [\"Testes\", \"Internamentos\", \"Infecoes\", \"GT\", \"LT\", \"GTE\", \"LTE\", \"NE\", \"EQ\"];\r\n" + 
				"        var toEnableRegioes = [\"Alentejo\", \"Algarve\", \"Centro\", \"Lisboa\", \"Norte\", \"Internamentos\", \"Infecoes\", \"Testes\"];\r\n" + 
				"        var toEnableRelational = [\"AddNumber\", \"Testes\", \"Internamentos\", \"Infecoes\"];\r\n" + 
				"        var toEnableEqual = [\"AddNumber\", \"Alentejo\", \"Algarve\", \"Centro\", \"Lisboa\", \"Norte\", \"Testes\", \"Internamentos\", \"Infecoes\"];\r\n" + 
				"        var toEnableNumber = [\"Internamentos\", \"Infecoes\", \"Testes\"];\r\n" + 
				"\r\n" + 
				"        document.getElementById(\"limpar\").onclick = function () {\r\n" + 
				"            input = [];\r\n" + 
				"            document.getElementById(\"query\").innerHTML = \"\";\r\n" + 
				"            for (let i = 0; i < ids.length; i++) {\r\n" + 
				"                document.getElementById(ids[i]).disabled = true;\r\n" + 
				"            }\r\n" + 
				"            for (let i = 0; i < toEnableInitial.length; i++) {\r\n" + 
				"                document.getElementById(toEnableInitial[i]).disabled = false;\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        var children = document.getElementById(\"ActionButtons\").getElementsByTagName(\"button\");\r\n" + 
				"        for (let i = 0; i < children.length; i++) {\r\n" + 
				"            children[i].onclick = function () {\r\n" + 
				"                var id = children[i].id;\r\n" + 
				"                input.push(id);\r\n" + 
				"                var string = input.toString().replace(/,/g, \" \");\r\n" + 
				"                document.getElementById(\"query\").innerHTML = string;\r\n" + 
				"                if (id === \"Regiao\") {\r\n" + 
				"                    enable(toEnableRegiao);\r\n" + 
				"                }\r\n" + 
				"                if (id === \"Testes\" || id === \"Internamentos\" || id === \"Infecoes\" || id === \"EQ\") {\r\n" + 
				"                    enable(toEnableAttributes);\r\n" + 
				"                }\r\n" + 
				"                if (id === \"Alentejo\" || id === \"Algarve\" || id === \"Centro\" || id === \"Lisboa\" || id === \"Norte\") {\r\n" + 
				"                    enable(toEnableRegioes);\r\n" + 
				"                }\r\n" + 
				"                if (id === \"GT\" || id === \"LT\" || id === \"GTE\" || id === \"LTE\" || id === \"NE\") {\r\n" + 
				"                    enable(toEnableRelational);\r\n" + 
				"                }\r\n" + 
				"                if (id === \"EQ\") {\r\n" + 
				"                    enable(toEnableEqual)\r\n" + 
				"                }\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        document.getElementById(\"AddNumber\").onclick = function () {\r\n" + 
				"            input.push(document.getElementById(\"Number\").value);\r\n" + 
				"            document.getElementById(\"query\").innerHTML = input.toString().replace(/,/g, \" \");\r\n" + 
				"            enable(toEnableNumber);\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        function enable(array) {\r\n" + 
				"            for (let i = 0; i < ids.length; i++) {\r\n" + 
				"                document.getElementById(ids[i]).disabled = true;\r\n" + 
				"            }\r\n" + 
				"            for (let i = 0; i < array.length; i++) {\r\n" + 
				"                document.getElementById(array[i]).disabled = false;\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"    </script>\r\n" + 
				"    <!-- Optional JavaScript -->\r\n" + 
				"    <!-- jQuery first, then Popper.js, then Bootstrap JS -->\r\n" + 
				"    <script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\"\r\n" + 
				"        integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\"\r\n" + 
				"        crossorigin=\"anonymous\"></script>\r\n" + 
				"    <script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\"\r\n" + 
				"        integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\"\r\n" + 
				"        crossorigin=\"anonymous\"></script>\r\n" + 
				"    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js\"\r\n" + 
				"        integrity=\"sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI\"\r\n" + 
				"        crossorigin=\"anonymous\"></script>\r\n" + 
				"</body>\r\n" + 
				"\r\n" + 
				"</html>";
	}

	/**
	 * <p>Parses the form data received from the InputStream and outputs an HashTable containing the form fields ID and Value</p>
	 * @param inStream - Input Stream to parse
	 * @return HashTable containing the form fields ID and Value
	 */
	private static Hashtable ReadParse(InputStream inStream) {
		Hashtable form_data = new Hashtable();

		String inBuffer = "";

		DataInput d = new DataInputStream(inStream);
		String line;
		try {
			while ((line = d.readLine()) != null) {
				inBuffer = inBuffer + line;
			}
		} catch (IOException ignored) {
		}

		//
		// Split the name value pairs at the ampersand (&)
		//
		StringTokenizer pair_tokenizer = new StringTokenizer(inBuffer, "&");

		while (pair_tokenizer.hasMoreTokens()) {
			String pair = urlDecode(pair_tokenizer.nextToken());
			//
			// Split into key and value
			//
			StringTokenizer keyval_tokenizer = new StringTokenizer(pair, "=");
			String key = new String();
			String value = new String();
			if (keyval_tokenizer.hasMoreTokens())
				key = keyval_tokenizer.nextToken();
			else
				; // ERROR - shouldn't ever occur
			if (keyval_tokenizer.hasMoreTokens())
				value = keyval_tokenizer.nextToken();
			else
				; // ERROR - shouldn't ever occur
			//
			// Add key and associated value into the form_data Hashtable
			//
			form_data.put(key, value);
		}
		return form_data;
	}
	
  /**
   *
   * URL decode a string.<p>
   *
   * Data passed through the CGI API is URL encoded by the browser.
   * All spaces are turned into plus characters (+) and all "special"
   * characters are hex escaped into a %dd format (where dd is the hex
   * ASCII value that represents the original character).  You probably
   * won't ever need to call this routine directly; it is used by the
   * ReadParse method to decode the form data.
   *
   * @param in The string you wish to decode.
   *
   * @return The decoded string.
   *
   */
	private static String urlDecode(String in) {
		StringBuffer out = new StringBuffer(in.length());
		int i = 0;
		int j = 0;

		while (i < in.length()) {
			char ch = in.charAt(i);
			i++;
			if (ch == '+')
				ch = ' ';
			else if (ch == '%') {
				ch = (char) Integer.parseInt(in.substring(i, i + 2), 16);
				i += 2;
			}
			out.append(ch);
			j++;
		}
		return new String(out);
	}

}
