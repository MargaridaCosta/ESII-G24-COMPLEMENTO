package mainPackage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;

public class MainClass {

	/**
	 * Attribute that represents the file to verify the versions
	 */
	private static File f;

	/**
	 * Attribute that represents the Git Repository
	 */
	private static Git git;

	/**
	 * List that represents the last two tags
	 */
	private static List<String> latestTwoTags;

	/**
	 * List that represents the last two versions of the file
	 */
	private static List<RevCommit> latestTwoFiles = new ArrayList<RevCommit>();

	/**
	 * List that represents the last two tags that are linked to the file
	 */
	private static List<Ref> latestTwoTagsObj = new ArrayList<Ref>();

	/**
	 * List that represents the latest version of the file
	 */
	private static List<String> newFile;

	/**
	 * <p>
	 * List that represents the previous version of the file
	 * </p>
	 */
	private static List<String> oldFile;

	/**
	 * <p>
	 * Deletes an auxiliary folder with the method {@link #deleteDir(File)},
	 * ensuring that the remote repository can be cloned
	 * </p>
	 *
	 * @param dir target path of the cloned repo
	 */
	public static void deleteLocalRepo(String dir) {
		f = new File(System.getProperty("user.dir") + dir);
		deleteDir(f);
	}

	/**
	 * Clones the remote repository for later use
	 *
	 * @param repo git repository url
	 * @param dir  target path of the cloned repo
	 */
	public static void cloneRemoteRepo(String repo, String dir) throws Exception {
		git = Git.cloneRepository().setDirectory(new File(System.getProperty("user.dir") + dir)).setURI(repo).call();
	}

	/**
	 * Obtains the 2 latest tags of the cloned repository storing them in
	 * {@link #latestTwoTags} and {@link #latestTwoTagsObj}
	 *
	 */
	public static void getLastestTwoTags() throws Exception {
		List<Ref> allTags = git.tagList().call();
		latestTwoTags = new ArrayList<String>();
		allTags.subList(allTags.size() - 2, allTags.size()).forEach(tag -> {
			latestTwoTags.add(tag.toString().substring(tag.toString().indexOf('=') + 1, tag.toString().indexOf('(')));
			latestTwoTagsObj.add(tag);
		});
	}

	/**
	 * Obtains the versions of the files associated with the tags storing the new
	 * version in {@link #newFile} and the previous version in {@link #oldFile}
	 *
	 * @param file filename of the rdf file to be parsed
	 */
	public static void saveLastestTwoFileVersions(String file) throws Exception {
		List<byte[]> lastTwoFiles = new ArrayList<byte[]>();
		Iterable<RevCommit> fileCommits = git.log().addPath(file).call();
		for (RevCommit commit : fileCommits) {
			if (latestTwoTags.contains(commit.name().toString())) {
				TreeWalk treeWalk = TreeWalk.forPath(git.getRepository(), file, commit.getTree());
				ObjectId blobId = treeWalk.getObjectId(0);
				ObjectReader objectReader = git.getRepository().newObjectReader();
				ObjectLoader objectLoader = objectReader.open(blobId);
				lastTwoFiles.add(objectLoader.getBytes());
				latestTwoFiles.add(commit);
			}
		}
		newFile = Arrays.asList(new String(lastTwoFiles.get(0), StandardCharsets.UTF_8).split("\\r?\\n"));
		oldFile = Arrays.asList(new String(lastTwoFiles.get(1), StandardCharsets.UTF_8).split("\\r?\\n"));
	}

	/**
	 * Renders the files differences side by side on html
	 *
	 * @param file filename of the rdf file to be parsed
	 */
	public static void renderHtml(String file) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("Content-type: text/html\n\n");
		System.out.print(
				"<html lang=\"en\"><head><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\"><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\" integrity=\"sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk\" crossorigin=\"anonymous\"><title>G24-RQ6</title></head><body><div class=\"row mx-0\" style=\"font-size: 12px; line-height: 1;\"><div class=\"col-6 m-0 p-0\"><pre><code id=\"code1\" style=\"word-break: break-all; white-space: break-spaces;\">");
		int i = 0;
		System.out.print(
				"<h4 class=\"ml-4 mt-4\">" + latestTwoTagsObj.get(0).getName().replace("refs/tags/", "") + "</h4>");
		System.out.print(
				"<h6 class=\"ml-4\">" + dateFormat.format(latestTwoFiles.get(1).getAuthorIdent().getWhen()) + "</h6>");
		System.out.print("<p class=\"ml-4 mb-4\">" + file + "</p>");
		for (String l : oldFile) {
			System.out.print("<div class=\"container\">" + "<div class=\"row\">");
			if (!(newFile.contains(l) && newFile.indexOf(l) == oldFile.indexOf(l))) {
				System.out.print(
						"<span class=\"py-1 text-right pr-2\" style=\"background-color: #ffdce0; width: 2.5rem\">" + ++i
								+ "</span>");
				System.out
						.print("<span class=\"py-1\" style=\"background-color: #ffeef0\"><span class=\"ml-2\">-</span>"
								+ StringEscapeUtils.escapeHtml4(l) + "</span>");
			} else {
				System.out.print("<span class=\"py-1 text-right pr-2\" style=\"width: 2.5rem\">" + ++i + "</span>");
				System.out.print("<span class=\"py-1\"><span class=\"ml-2\"> </span>" + StringEscapeUtils.escapeHtml4(l)
						+ "</span>");
			}
			System.out.print("</div></div>");

		}
		System.out.print("</code></pre>" + "</div>"
				+ "<div class=\"col-6 m-0 p-0 border-left border-dark\"><pre><code id=\"code2\" style=\"word-break: break-all; white-space: break-spaces;\">");
		i = 0;
		System.out.print(
				"<h4 class=\"ml-4 mt-4\">" + latestTwoTagsObj.get(1).getName().replace("refs/tags/", "") + "</h4>");
		System.out.print(
				"<h6 class=\"ml-4\">" + dateFormat.format(latestTwoFiles.get(0).getAuthorIdent().getWhen()) + "</h6>");
		System.out.print("<p class=\"ml-4 mb-4\">" + file + "</p>");
		for (String l : newFile) {
			System.out.print("<div class=\"container\">" + "<div class=\"row\">");
			if (!(oldFile.contains(l) && newFile.indexOf(l) == oldFile.indexOf(l))) {
				System.out.print(
						"<span class=\"py-1 text-right pr-2\" style=\"background-color: #cdffd8; width: 2.5rem\">" + ++i
								+ "</span>");
				System.out
						.print("<span class=\"py-1\" style=\"background-color: #e6ffed\"><span class=\"ml-2\">+</span>"
								+ StringEscapeUtils.escapeHtml4(l) + "</span>");
			} else {
				System.out.print("<span class=\"py-1 text-right pr-2\" style=\"width: 2.5rem\">" + ++i + "</span>");
				System.out.print("<span class=\"py-1\"><span class=\"ml-2\"> </span>" + StringEscapeUtils.escapeHtml4(l)
						+ "</span>");
			}
			System.out.print("</div></div>");
		}

		System.out.println("</code></pre>" + "</div>" + "</div>"
				+ "<script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\" integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\"></script><script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" crossorigin=\"anonymous\"></script><script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js\" integrity=\"sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI\" crossorigin=\"anonymous\"></script></body></html>");
	}

	/**
	 * <h1>Generating HTML File with the differences side by side</h1>
	 * <p>
	 * Firstly, the program deletes the local repo with the given path and then
	 * clones the remote repo with that same path.
	 * </p>
	 * <p>
	 * Secondly, the program gets the latest two tags and saves the latest two file
	 * versions for the given filename.
	 * </p>
	 * <p>
	 * Finally, it renders the 2 latest tagged versions of a file on a git repo side
	 * by side on an HTML page.
	 * </p>
	 * 
	 * @param args {repository url, filename, auxiliar path}
	 *
	 */
	public static void main(String[] args) {
		try {
			System.setErr(new PrintStream(new ByteArrayOutputStream()));
			deleteLocalRepo(args[2]);
			cloneRemoteRepo(args[0], args[2]);
			getLastestTwoTags();
			saveLastestTwoFileVersions(args[1]);
			renderHtml(args[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes a file or a directory
	 * 
	 * @param f file or directory
	 */
	private static void deleteDir(File f) {
		File[] contents = f.listFiles();
		if (contents != null)
			for (File f1 : contents)
				deleteDir(f1);
		f.delete();
	}

}