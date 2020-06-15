package project;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

public class Req4 {

	/**
	 * Attribute that represents the Git Repository.
	 */
	private static Git git;

	/**
	 * Attribute that represents the directory to verify all the tags associated
	 * with commits.
	 */
	private static File dir;

	/**
	 * Attribute that represents name of the file to be parsed.
	 */
	private static String fileName = "covid19spreading.rdf";

	/**
	 * Attribute that represents a list with the information of each line for the
	 * HTML table.
	 */
	private static List<String> lineInfo = new ArrayList<String>();

	/**
	 * Attribute that represents the base url for the Spread Visualization Link
	 */
	private static String graphicUrl = "http://visualdataweb.de/webvowl/#iri=https://github.com/vbasto-iscte/ESII1920/raw";

	// private static File htmlDir = new File(System.getProperty("user.dir") +
	// "/htmlRep");

	/**
	 * Method to delete a directory and its files.
	 * 
	 * @param f A File directory
	 */
	private static void deleteDirectory(File f) {
		File[] files = f.listFiles();
		if (files != null) {
			for (File f1 : files) {
				deleteDirectory(f1);
			}
		}
		f.delete();
	}

	/**
	 * Method to access github and clone the repository to a specific folder inside
	 * this project.
	 * 
	 * @param link A String that represents the link to the repository
	 */
	private static void accessRepository(String link)
			throws InvalidRemoteException, TransportException, GitAPIException {
		git = Git.cloneRepository().setURI(link).setDirectory(dir).call();
	}

	/**
	 * <p>
	 * CreateLineInformation adds to the array {@link #lineInfo} the information to
	 * be put in all the columns of the HTML table.
	 * </p>
	 * 
	 * @param commit A RevCommit that represents a commit
	 * @param ref    A Ref that represents a tag associated with the commit
	 */
	private static void createLineInformation(RevCommit commit, Ref ref) {
		String timeStamp = commit.getAuthorIdent().getWhen().toString();
		String fileTag = ref.getName().split("/")[2];
		String tagDescription = commit.getFullMessage();
		String link = graphicUrl + "/" + fileTag + "/" + fileName;
		lineInfo.add(timeStamp + "---" + fileName + "---" + fileTag + "---" + tagDescription + "---" + link);
	}

	/**
	 * This method finds all the tags and its associated commits for the file
	 * covid19spreading.rdf. Gets all the information needed to fill the HTML table
	 * using the method {@link #createLineInformation(RevCommit, Ref)}
	 */
	private static void findAllTags()
			throws GitAPIException, MissingObjectException, IncorrectObjectTypeException, IOException {
		RevWalk revWalk = new RevWalk(git.getRepository());
		List<Ref> call = git.tagList().call();
		for (Ref ref : call) {
			RevCommit commit = revWalk.parseCommit(ref.getObjectId());
			RevTree tree = commit.getTree();
			TreeWalk treeWalk = new TreeWalk(git.getRepository());
			treeWalk.addTree(tree);
			treeWalk.setRecursive(true);
			treeWalk.setFilter(PathFilter.create(fileName));
			if (treeWalk.next()) {
				createLineInformation(commit, ref);
				revWalk.dispose();
			}
		}
	}

	/**
	 * <p>
	 * Gets the HTML Content by running through the list {@link #lineInfo}.
	 * </p>
	 * <p>
	 * Uses the method {@link #getMessage(String)} to format the description of the
	 * tag in HTML.
	 * </p>
	 * 
	 * @return A String with all the HTML code.
	 */
	private static String getHtmlContent() {
		String last = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "body {\r\n"
				+ "    font-family: Helvetica;\r\n" + "}\r\n" + "table {\r\n" + "  width:100%;\r\n" + "}\r\n"
				+ "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "  border-collapse: collapse;\r\n"
				+ "}\r\n" + "th{\r\n" + "    padding: 15px; \r\n" + "}\r\n" + "td {\r\n" + "    padding: 15px;\r\n"
				+ "    text-align: left;\r\n" + "}\r\n" + "table#t01 tr:nth-child(even) {\r\n"
				+ "    background-color: #eee;\r\n" + "}\r\n" + "table#t01 tr:nth-child(odd) {\r\n"
				+ "    background-color: #fff;\r\n" + "}\r\n" + "table#t01 th {\r\n" + "    font-size:20px;\r\n"
				+ "    background-color: #3385ff;\r\n" + "    color: white;\r\n" + "}\r\n" + "</style>\r\n"
				+ "</head>\r\n" + "<body>\r\n" + "<h1>Covid19 Spreading - Requisito 4</h1>\r\n"
				+ "    <table id=\"t01\">\r\n" + "    <tr>\r\n" + "        <th>File Timestamp</th>\r\n"
				+ "        <th>File Name</th> \r\n" + "        <th>File Tag</th>\r\n"
				+ "        <th>Tag Description</th>\r\n" + "        <th>Spread Visualization Link</th>\r\n"
				+ "    </tr>\r\n";

		for (String s : lineInfo) {
			last += "    <tr>\r\n" + "        <td>" + s.split("---")[0] + "</th>\r\n" + "        <td>"
					+ s.split("---")[1] + "</th> \r\n" + "        <td>" + s.split("---")[2] + "</th>\r\n"
					+ "        <td>" + getMessage(s.split("---")[3]) + "</th>\r\n" + "		 <td><a href=\""
					+ s.split("---")[4] + "\">Visualize Covid19 Data</a></th>\r\n" + "    </tr>\r\n";
		}
		last += "</table>\r\n" + "</body>\r\n" + "</html>";
		return last;
	}

	/**
	 * This method returns the tag description in a format where the HTML file will
	 * change paragraph accordingly to the number of \n.
	 * 
	 * @param s A string with the full description of a tag
	 * 
	 * @return A string with the description for the HTML Content
	 */
	private static String getMessage(String s) {
		String[] splitted = s.split("\n");
		String description = "";
		for (int i = 0; i != splitted.length; i++) {
			description += splitted[i] + "<br>";
		}
		return description;
	}

	/**
	 *
	 * Generate a standard HTTP HTML header.
	 *
	 * @return A String containing the standard HTTP HTML header.
	 *
	 */
	public static String Header() {
		return "Content-type: text/html\n\n";
	}

	/**
	 * <h1>Generating HTML Table with Repository Tags and Spread Visualiton
	 * Link</h1>
	 * <p>
	 * Firstly, the directory with the file to be fetched is deleted with
	 * {@link #deleteDirectory(File)} in order to clone and access the repository to
	 * that directory using the {@link #accessRepository(String)}.
	 * </p>
	 * <p>
	 * Secondly, the method {@link #findAllTags()} is going to store the information
	 * to be displayed later on the HTML page.
	 * </p>
	 * <p>
	 * Finally this program will exhibit a table with 5 columns (File Timestamp,
	 * File Name, File Tag, Tag Description and Spread Visualization Link) with
	 * {@link #getHtmlContent()} on the HTML page.
	 * </p>
	 * 
	 * @param args repository url.
	 */
	public static void main(String[] args) {
		System.setErr(new PrintStream(new ByteArrayOutputStream()));
		System.out.println(Header());
		dir = new File(System.getProperty("user.dir") + "/fileRep");
		deleteDirectory(dir);
		try {
			accessRepository(args[0]);
			findAllTags();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(getHtmlContent());

	}

}
