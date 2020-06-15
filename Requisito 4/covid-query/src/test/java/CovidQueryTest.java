import java.util.Hashtable;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CovidQueryTest {

	private static QueryParser parser;
	//Test 1
	static Hashtable form_data1;
	static String queryResult1 = "";
	
	//Test 2
	static Hashtable form_data2;
	static String queryResult2 = "";
	
	//Test 3
	static Hashtable form_data3;
	static String queryResult3 = "";
	
	//Test 4
	static Hashtable form_data4;
	static String queryResult4 = "";
	
	//Test 5
	static Hashtable form_data5;
	static String queryResult5 = "";
	
	//Test 6
	static Hashtable form_data6;
	static String queryResult6 = "";
	
	//Test 7
	static Hashtable form_data7;
	static String queryResult7 = "";
	
	//Test 8
	static Hashtable form_data8;
	static String queryResult8 = "";
	
	//Test 9
	static Hashtable form_data9;
	static String queryResult9 = "";
	
	//Test 10
	static Hashtable form_data10;
	static String queryResult10 = "";
	
	//Test Regiao
	private static Regiao algarve;
	
	
	@BeforeAll
	static void setUp() {
		//Test 1
		form_data1 = new Hashtable();
		form_data1.put("Regiao", "Regiao");
		queryResult1 = "<div><h3>Lista de todas as regioes: </h3><p>Alentejo</p><p>Algarve</p><p>Centro</p><p>Lisboa</p><p>Norte</p></div>";
		//Test 2
		form_data2 = new Hashtable();
		form_data2.put("Regiao", "Internamentos GTE Infecoes Testes GT 30");
		queryResult2 = "<div><p>Alentejo: Internamentos: 50</p><p>Algarve: Internamentos: 50</p><p>Centro: Internamentos: 0</p><p>Lisboa: Internamentos: 0</p><p>Norte: Internamentos: 0</p><p>Alentejo: Testes: 50</p><p>Algarve: Testes: 50</p></div>";
		//Test 3
		form_data3 = new Hashtable();
		form_data3.put("Regiao", "Regiao EQ Algarve Centro Internamentos Infecoes");
		queryResult3 = "<div><h3>Resultados para a regiao: Algarve</h3><p>Algarve: Internamentos: 50</p><p>Algarve: Infecoes: 50</p><h3>Resultados para a regiao: Centro</h3><p>Centro: Internamentos: 0</p><p>Centro: Infecoes: 0</p></div>";
		
		//Test 4
		form_data4 = new Hashtable();
		form_data4.put("Regiao", "Internamentos");
		queryResult4 = "<div><p>Alentejo: Internamentos: 50</p><p>Algarve: Internamentos: 50</p><p>Centro: Internamentos: 0</p><p>Lisboa: Internamentos: 0</p><p>Norte: Internamentos: 0</p></div>";
		
		//Test 5
		form_data5 = new Hashtable();
		form_data5.put("Regiao", "Internamentos GT 30 Internamentos GTE Infecoes");
		queryResult5 = "<div><p>Alentejo: Internamentos: 50</p><p>Algarve: Internamentos: 50</p><p>Alentejo: Internamentos: 50</p><p>Algarve: Internamentos: 50</p><p>Centro: Internamentos: 0</p><p>Lisboa: Internamentos: 0</p><p>Norte: Internamentos: 0</p></div>"; 
	
		//Test 6
		form_data6 = new Hashtable();
		form_data6.put("Regiao", "Infecoes LT Testes Testes LTE 30");
		queryResult6 = "<div><p>Centro: Testes: 0</p><p>Lisboa: Testes: 0</p><p>Norte: Testes: 0</p></div>";
	
		//Test 7
		form_data7 = new Hashtable();
		form_data7.put("Regiao", "Internamentos EQ Infecoes Testes NE Internamentos");
		queryResult7 = "<div><p>Alentejo: Internamentos: 50</p><p>Algarve: Internamentos: 50</p><p>Centro: Internamentos: 0</p><p>Lisboa: Internamentos: 0</p><p>Norte: Internamentos: 0</p></div>";
	
		//Test 8
		form_data8 = new Hashtable();
		form_data8.put("Regiao", "Internamentos GT Infecoes Testes LTE Infecoes");
		queryResult8 = "<div><p>Alentejo: Testes: 50</p><p>Algarve: Testes: 50</p><p>Centro: Testes: 0</p><p>Lisboa: Testes: 0</p><p>Norte: Testes: 0</p></div>";
	
		//Test 9
		form_data9 = new Hashtable();
		form_data9.put("Regiao", "Internamentos EQ 30 Infecoes GTE 40");
		queryResult9 = "<div><p>Alentejo: Infecoes: 50</p><p>Algarve: Infecoes: 50</p></div>";
	
		//Test 10
		form_data10 = new Hashtable();
		form_data10.put("Regiao", "Internamentos LT 40 Infecoes NE 0");
		queryResult10 = "<div><p>Centro: Internamentos: 0</p><p>Lisboa: Internamentos: 0</p><p>Norte: Internamentos: 0</p><p>Alentejo: Infecoes: 50</p><p>Algarve: Infecoes: 50</p></div>";
		
		//Test Regiao
		algarve = new Regiao("Algarve");
		algarve.setInfecoes(50);
		algarve.setInternamentos(50);
		algarve.setTestes(50);
	}
	
	@Test
	void testParseForm() {
		assertEquals(queryResult1, parser.parseForm(form_data1));
		assertEquals(queryResult2, parser.parseForm(form_data2));
		assertEquals(queryResult3, parser.parseForm(form_data3));
		assertEquals(queryResult4, parser.parseForm(form_data4));
		assertEquals(queryResult5, parser.parseForm(form_data5));
		assertEquals(queryResult6, parser.parseForm(form_data6));
		assertEquals(queryResult7, parser.parseForm(form_data7));
		assertEquals(queryResult8, parser.parseForm(form_data8));
		assertEquals(queryResult9, parser.parseForm(form_data9));
		assertEquals(queryResult10, parser.parseForm(form_data10));
	}
	
	
	@Test
	void testRegiao() {
		Regiao toTest = new Regiao("Algarve");
		toTest.setInfecoes(50);
		toTest.setInternamentos(50);
		toTest.setTestes(50);
		assertEquals(toTest.getNome(), algarve.getNome());
		assertEquals(toTest.getInfecoes(), algarve.getInfecoes());
		assertEquals(toTest.getInternamentos(), algarve.getInternamentos());
		assertEquals(toTest.getTestes(), algarve.getTestes());
	}

	@Test
	void testGetNome() {
		assertEquals("Algarve", algarve.getNome());
	}

	@Test
	void testGetInfecoes() {
		assertEquals(50, algarve.getInfecoes());
	}

	@Test
	void testGetInternamentos() {
		assertEquals(50, algarve.getInternamentos());
	}

	@Test
	void testGetTestes() {
		assertEquals(50, algarve.getTestes());
	}
	
	@Test
	void testSetInfecoes() {
		algarve.setInfecoes(50);
		assertEquals(50, algarve.getInfecoes());
	}

	@Test
	void testSetInternamentos() {
		algarve.setInternamentos(50);
		assertEquals(50, algarve.getInternamentos());
	}

	@Test
	void testSetTestes() {
		algarve.setTestes(50);
		assertEquals(50, algarve.getTestes());
	}

	@Test
	void testToString() {
		assertEquals("Regiao: " + "Algarve" + " | Infeções: " + "50" + " | Internamentos: " + "50" + " | Teste: " + "50", algarve.toString());
	}

	@Test
	void testGet() {
		assertEquals(50, algarve.get("Infecoes"));
		assertEquals(50, algarve.get("Testes"));
		assertEquals(50, algarve.get("Internamentos"));
	}

}
