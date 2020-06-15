package rq2;

public class RunTest {

	static String path = "/jars/chromedriver";
	
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", path);
		Tests tests = new Tests();
	}

}