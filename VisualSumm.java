import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.net.*;
import java.io.*;
import org.json.*;
import java.util.LinkedList;

public class VisualSumm {

	public static void main(String[] args) {
		File doThisOne = new File("toSumm.txt");
		VisualSumm vs = new VisualSumm();
		String[] output = vs.generateUrlList(doThisOne);
		for (String a : output) {
			System.out.println(a);
		}
	}

	public String[] generateUrlList(File toSummarize) {

		// This is where we store the urls
		LinkedList<String> urlList = new LinkedList<String>();

		try {
			// Read in word by word, using white space as delimiter.
			// Then remove all punctuation and surrounding whitespace.
			// Write results to file to ensure that we did it correctly.
			Scanner scan = new Scanner(toSummarize);
			LinkedList<WordWithCounts> words = new LinkedList<WordWithCounts>();
		
			// Go through and do processing on word tokens, then add in 
			// the linked list
			while (scan.hasNext()) {
				String next = scan.next();
				next = next.replaceAll("\\W+","");
				next = next.replaceAll("\\s+","");
				next = next.trim();
				next = next.toLowerCase();

				if (!next.equals("")) {
					WordWithCounts wordInQuestion = new WordWithCounts(next);

					boolean added = false;
					for (int i=0; i<words.size(); i++) {
						if (wordInQuestion.equals(words.get(i))) {
							words.get(i).incrementCount();
							added = true;
						}
					}

					if (!added) {
						words.add(wordInQuestion);
					}
				}
			}

			// Sort the list of words by their counts
			WordWithCounts[] arr = words.toArray(new WordWithCounts[0]);

			for (int i=0; i<words.size(); i++) {
				int maxIndex = i;
				for (int j=i; j<words.size(); j++) {
					if (arr[j].getCount() > arr[maxIndex].getCount()) {
						maxIndex = j;
					}
				}
				// Swap the max element with the current element
				WordWithCounts temp = arr[i];
				arr[i] = arr[maxIndex];
				arr[maxIndex] = temp;
			}

			for (WordWithCounts a:arr) {
				System.out.println(a);
			}

			// Now we pull out the top ten most relevant words, if they aren't equal to certain common words.
			LinkedList<String> notAllowed = new LinkedList<String>();
			notAllowed.add("he");
			notAllowed.add("she");
			notAllowed.add("it");
			notAllowed.add("a");
			notAllowed.add("an");
			notAllowed.add("the");
			notAllowed.add("to");
			notAllowed.add("of");			
			notAllowed.add("in");
			notAllowed.add("was");
			notAllowed.add("is");
			notAllowed.add("and");
			notAllowed.add("you");
			notAllowed.add("i");
			notAllowed.add("had");
			notAllowed.add("that");
			notAllowed.add("with");
			notAllowed.add("as");
			notAllowed.add("before");
			notAllowed.add("this");			
			notAllowed.add("when");
			notAllowed.add("but");
			notAllowed.add("because");
			notAllowed.add("this");
			notAllowed.add("her");
			notAllowed.add("for");
			notAllowed.add("one");
			notAllowed.add("at");
			notAllowed.add("they");			
			notAllowed.add("on");
			notAllowed.add("no");
			notAllowed.add("were");
			notAllowed.add("who");
			notAllowed.add("from");
			notAllowed.add("about");
			notAllowed.add("up");
			notAllowed.add("have");
			notAllowed.add("been");
			notAllowed.add("there");
			notAllowed.add("said");
			notAllowed.add("his");
			notAllowed.add("not");
			notAllowed.add("after");
			notAllowed.add("be");

			String[] wordsToLookUp = new String[10];
			int indexToLookAt = 0;
			boolean filled = false;
			for (int i=0; i<10; i++) {
				filled = false;
				while (!filled) {
					if (!notAllowed.contains(arr[indexToLookAt].getWord())) {
						wordsToLookUp[i] = arr[indexToLookAt].getWord();
						filled = true;
					}
					indexToLookAt++;
				}
			}

			

			for (String a:wordsToLookUp) {
				// Do a google image search, and output the first three results
				try {
					URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
       	       			  	"v=1.0&q=" + a + "&userip=198.137.20.133");

					URLConnection connection = url.openConnection();
					connection.addRequestProperty("Referer", "http://visual-summary.appspot.com/");

					String line;
					StringBuilder builder = new StringBuilder();
					BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					while((line = reader.readLine()) != null) {
						builder.append(line);
					}

					JSONObject json = new JSONObject(builder.toString());
					// now have some fun with the results...
					//System.out.println(json.get("responseData").getClass().getName());
					/*for (String a : JSONObject.getNames(json.get("responseData"))) {
						System.out.println(a);
					}*/

					JSONArray jsonarr = json.getJSONObject("responseData").getJSONArray("results");
					for (int i=0; i<jsonarr.length(); i++) {
						urlList.add(jsonarr.getJSONObject(i).getString("url"));
					}
				}
				catch (MalformedURLException e) {
					System.out.println("Nooo");
				}
				catch (IOException e) {
					System.out.println("NOOOOO");
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("noooo");
		}
		return urlList.toArray(new String[0]);
	}
}