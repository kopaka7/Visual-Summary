public class WordWithCounts {

	private int countInDoc;
	private String theWord;

	public WordWithCounts(String input) {
		theWord = input;
		countInDoc = 1;
	}

	public void incrementCount() {
		countInDoc++;
	}

	public int getCount() {
		return this.countInDoc;
	}

	public String getWord() {
		return this.theWord;
	}

	public boolean equals(WordWithCounts otherWord) {
		if (this.theWord.equals(otherWord.theWord)) {
			return true;
		}
		else {
			return false;
		}
	}

	public String toString() {
		return theWord + " " + countInDoc;
	}
}