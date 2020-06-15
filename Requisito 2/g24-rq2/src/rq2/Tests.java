package rq2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Tests {

	static WebDriver webDriver;
	static String username = "bmcca1";
	static String password = "covid19-ES-grupo24";
	static String adminMail = "joaopedro.94@icloud.com";
	static String webMail = "report@covid.iscte.com";
	static ArrayList<String> errors = new ArrayList<String>();
	static String emailUser = "report.wp.cms@gmail.com";
	static String emailPass = "Palavra1!";

	public Tests() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("--headless");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("disable-gpu");
		webDriver = new ChromeDriver(options);

		doTests();

		if (errors.size() > 0) {
			System.out.println(errors.toString());
			sendMail();
		}

		// Closing the browser and WebDriver
		webDriver.close();
		webDriver.quit();
	}

	private static void doTests() {
		arePagesUp();
		isContactUsWorking();
		isCovidResearchWorking();
		isJoinUsWorking();
		isAdminLoginWorking();
	}

	private static void isJoinUsWorking() {
		webDriver.get("http://0.0.0.0:80/join-us");

		WebElement firstname = webDriver.findElement(By.id("first_name"));
		WebElement surname = webDriver.findElement(By.id("last_name"));
		WebElement username = webDriver.findElement(By.id("user_login"));
		WebElement useremail = webDriver.findElement(By.id("user_email"));
		WebElement userpassword = webDriver.findElement(By.id("user_pass"));
		WebElement confirmpassword = webDriver.findElement(By.id("user_confirm_password"));
		WebElement affiliation = webDriver.findElement(By.id("affiliation"));
		WebElement country = webDriver.findElement(By.id("country"));
		WebElement vaccine = webDriver.findElement(By.id("keywords_Vaccine"));
		WebElement treatment = webDriver.findElement(By.id("keywords_Treatment"));
		WebElement tests = webDriver.findElement(By.id("keywords_Tests"));
		WebElement privacy = webDriver.findElement(By.id("privacy_policy"));
		WebElement other = webDriver.findElement(By.id("other"));
		WebElement form = webDriver.findElement(By.className("register"));

		Random random = new Random();
		String name = "teste" + random.nextInt(900) + 100;

		firstname.sendKeys("Joao");
		surname.sendKeys("Teste");
		username.sendKeys(name);
		useremail.sendKeys(name + "@teste.com");
		userpassword.sendKeys("qwerty123");
		confirmpassword.sendKeys("qwerty123");
		affiliation.sendKeys("none");
		other.sendKeys("");
		country.sendKeys("Portugal");
		vaccine.click();
		treatment.click();
		tests.click();
		privacy.click();

		form.submit();

		try {
			WebElement success = webDriver.findElement(By.id("ur-submit-message-node"));
			System.out.println("Success at Join-Us");
		} catch (Exception e) {
			if (e.getCause() == null) {
				errors.add("Failed at Join-Us");
				System.out.println("Failed submitting at ttp://0.0.0.0/join-us");
			}
		}
	}

	private static void isCovidResearchWorking() {
		try {
			isPageUp("http://localhost/wp-content/uploads/2020/06/1-s2.0-S1755436517301135-main.pdf");
			isPageUp("http://localhost/wp-content/uploads/2020/06/biology-09-00094.pdf");
			isPageUp("http://localhost/wp-content/uploads/2020/06/178-1-53.pdf");
			isPageUp("http://localhost/wp-content/uploads/2020/06/biology-09-00097.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void isContactUsWorking() {
		webDriver.get("http://0.0.0.0:80/contact-us");

		WebElement subject = webDriver.findElement(By.id("wpforms-58-field_3"));
		WebElement email = webDriver.findElement(By.id("wpforms-58-field_1"));
		WebElement message = webDriver.findElement(By.id("wpforms-58-field_2"));
		WebElement form = webDriver.findElement(By.id("wpforms-form-58"));

		subject.sendKeys("Test Subject");
		email.sendKeys("teste@teste.com");
		message.sendKeys("lorem ipsum");

		form.submit();
		
		try {
			WebElement success = webDriver.findElement(By.id("wpforms-confirmation-58"));
			System.out.println("Success at Contact-Us");
		} catch (Exception e) {
			if (e.getCause() == null) {
				errors.add("Failed at Contact-Us");
				System.out.println("Failed submitting at ttp://0.0.0.0/contact-us");
			}
		}
	}

	private static void arePagesUp() {
		try {
			isPageUp("http://0.0.0.0");
			isPageUp("http://0.0.0.0/wp-admin");
			isPageUp("http://0.0.0.0/about-us/");
			isPageUp("http://0.0.0.0/contact-us/");
			isPageUp("http://0.0.0.0/covid-19/");
			isPageUp("http://0.0.0.0/covid-scientific-discoveries-repository/");
			isPageUp("http://0.0.0.0/faqs/");
			isPageUp("http://0.0.0.0/home/");
			isPageUp("http://0.0.0.0/join-us/");
			isPageUp("http://0.0.0.0/open-forum/");
			isPageUp("http://0.0.0.0/post-here/");
			isPageUp("http://0.0.0.0/web-site-analytics/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void isAdminLoginWorking() {
		webDriver.get("http://0.0.0.0:80/wp-admin");

		WebElement login = webDriver.findElement(By.id("user_login"));
		WebElement pass = webDriver.findElement(By.id("user_pass"));
		WebElement loginForm = webDriver.findElement(By.id("loginform"));

		login.sendKeys(username);
		pass.sendKeys(password);
		loginForm.submit();

		try {
			WebElement loginError = webDriver.findElement(By.id("login_error"));
			if (loginError.getText().contentEquals("Unknown username. Check again or try your email address.")) {
				System.out.println("Login Unsuccessful");
				errors.add("Admin Login Unsuccessful");
			}
		} catch (Exception e) {
			if (e.getCause() == null) {
				System.out.println("Admin Login Successful");
			} else {
				System.out.println(e.toString());
			}
		}
	}

	public static void sendMail() {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); // TLS
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailUser, emailPass);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(webMail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(adminMail));
			message.setSubject("WP-CMS Report");
			message.setText(errors.toString());

			Transport.send(message);
			System.out.println("Sent email successfully");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public static boolean isPageUp(String link) throws IOException {
		URL url = new URL(link);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		int x = connection.getResponseCode();
		if (x != 200) {
			errors.add("Problem at " + link + " error " + x);
		} else {
			System.out.println("Success connecting to " + link);
		}

		return (x == 200);
	}

}
