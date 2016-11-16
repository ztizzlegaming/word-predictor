/**
 * WordRec Comparable class, that holds a word and a count
 * 
 * @author Jordan Turley
 */
public class WordRec implements Comparable<WordRec> {
	private String word;
	private int count;

	/**
	 * Creates a new WordRec with a given word, and count = 1
	 * @param  word The word of this WordRec
	 */
	public WordRec(String word) {
		this.word = word;
		count = 1;
	}

	/**
	 * @return The word of this WordRec
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @return The count of this WordRec
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Increments count by one
	 */
	public void increment() {
		count++;
	}

	/**
	 * Compares this WordRec to another object, depending on the word
	 * @param o The object to compare this to
	 * @return If the words are equal of this and o
	 */
	public boolean equals(Object o) {
		if (o instanceof WordRec) {
			WordRec wr = (WordRec) o;
			return word.equals(wr.getWord());
		} else {
			return false;
		}
	}

	/**
	 * Compares this WordRec to another WordRec, depending on the word they each hold
	 * @param wr The WordRec to compare this to
	 * @return If this comes before, the same as, or after the given WordRec
	 */
	public int compareTo(WordRec wr) {
		return word.compareTo(wr.getWord());
	}

	/**
	 * @return A String with the word and count of this WordRec
	 */
	public String toString() {
		return word + " - " + count;
	}

	public static void main(String[] args) {
		WordRec word1 = new WordRec("the");
		WordRec word2 = new WordRec("a");
		System.out.println("This should be positive: " + word1.compareTo(word2));
		System.out.println("This should be negative: " + word2.compareTo(word1));
		System.out.println("This should be 0: " + word1.compareTo(word1));
	}
}