/**
 * WordChoice class, Comparable depending on the count of the WordChoices
 * Two WordChoice objects are considered equal if their word is the same
 * A WordChoice object comes before another if it has a higher count. If the counts are equal, the strings are compared
 *
 * @author Jordan Turley
 */
public class WordChoice implements Comparable<WordChoice> {
	private String word;
	private int count;

	/**
	 * Creates a new WordChoice with a word and a count
	 * @param word The word of this WordCount
	 * @param count The count of this WordCount
	 */
	public WordChoice(String word, int count) {
		this.word = word;
		this.count = count;
	}

	/**
	 * @return The word of this WordChoice
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @return The count of this WordChoice
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Checks if an Object is equal to this WordChoice, depending on it's and word
	 * @param o The Object to check equality
	 * @return True or false if they are equal or not
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof WordChoice) {
			WordChoice wc = (WordChoice) o;
			return compareTo(wc) == 0;
		}
		return false;
	}

	/**
	 * Compares this WordChoice to another WordChoice, depending on their counts.
	 * @param wc The WordChoice to compare this to
	 * @return -1 if this comes before wc, 1 if this comes after, 0 if they have the same count
	 */
	public int compareTo(WordChoice wc) {
		if (count > wc.getCount()) {
			return -1;
		} else if (count < wc.getCount()) {
			return 1;
		} else {
			return word.compareTo(wc.getWord());
		}
	}

	/**
	 * @return A String representation of this WordChoice, with its word and count.
	 */
	public String toString() {
		return word + " - " + count;
	}

	public static void main(String[] args) {
		WordChoice w1 = new WordChoice("a", 1);
		WordChoice w2 = new WordChoice("b", 1);
		WordChoice w3 = new WordChoice("b", 10);

		System.out.println("This should be false: " + w1.equals(w2));
		System.out.println("This should be 1: " + w2.compareTo(w3));
		System.out.println("This should be 0: " + w1.compareTo(w2));
	}
}