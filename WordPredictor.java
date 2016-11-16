import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * WordPredictor class. Makes a WordTree from the file specified in FILENAME, then creates a hashtable of all possible
 * prefixes of all the words in the tree. Then, lets the user search for a word.
 *
 * USER INSTRUCTIONS:
 * Start by entering a character for a word you are looking for, then the program will give you 3 words.
 * If you see the word you are looking for, you enter the number of the word.
 * If you do not see the word, enter another character.
 * If your word cannot be found, then you are able to enter it, and then search again, so the program can begin to learn new words.
 *
 * I received no help on this project.
 * I helped Ricardo, Kate, Jon, Yifan a bit, and Will a bit on this project.
 *
 * @author Jordan Turley
 */
public class WordPredictor {
	public static final String FILENAME = "mobydick.txt";
	public static final int HASHTABLE_SIZE = 10000;

	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		WordTree tree = null;
		try {
			tree = new WordTree(FILENAME);
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			return;
		}

		Hashtable hashtable = createHashtable(tree);

		String totalWord = "";

		ASL3<WordChoice> mostRecentPossibleWords = null;

		boolean running = true;
		boolean first = true;
		while (running) {
			String inStr = getCharacterFromUser(first);
			first = false;

			System.out.println(); //Print a blank line after their input

			//If they input a number, then they chose one of the words
			char choice = inStr.charAt(0);

			if (choice == '1' || choice == '2' || choice == '3') {
				int idx = choice - '0';
				if (idx < mostRecentPossibleWords.size()) {
					idx = mostRecentPossibleWords.size();
				}
				String word = null;
				//Go through the ASL3 to get the word they chose. This could be a bit more efficient
				//if I had stored the words in a regular array when printing them out, but this
				//loop will go through a max of 3 times, so it really doesn't matter
				mostRecentPossibleWords.reset();
				for (int i1 = 0; i1 < idx; i1++) {
					word = mostRecentPossibleWords.getNext().getWord();
				}
				System.out.println("Your word is: " + word);
				System.out.println("Type \"again\" to find another word, or \"exit\" to exit the program.");
				String againCheck = input.nextLine();
				while (!againCheck.equals("again") && !againCheck.equals("exit")) {
					System.out.println("Your input was not recognized. Try again: ");
					againCheck = input.nextLine();
				}
				if (againCheck.equals("again")) {
					totalWord = "";
				} else {
					running = false;
				}
			} else {
				totalWord += inStr;
				mostRecentPossibleWords = hashtable.get(totalWord);
				if (mostRecentPossibleWords.size() == 0) {
					System.out.println("No words starting with \"" + totalWord + "\" could not be found. Please type the full word you were trying to find:");
					String fullWord = input.nextLine();
					fullWord = WordTree.processString(fullWord); //Remove leading or trailing punctuation, just in case
					WordChoice newWord = new WordChoice(fullWord, 1);
					addWordToHashtable(hashtable, newWord);

					System.out.println("The word \"" + fullWord + "\" has been added. Type \"again\" to predict another word, or \"exit\" to exit the program.");
					String againCheck = input.nextLine();
					while (!againCheck.equals("again") && !againCheck.equals("exit")) {
						System.out.println("Your input was not recognized. Try again: ");
						againCheck = input.nextLine();
					}
					if (againCheck.equals("again")) {
						totalWord = "";
					} else {
						running = false;
					}
				} else {
					printPossibleWords(totalWord, mostRecentPossibleWords);
					//I don't have to check if it is none, because it will automatically loop again
					//and let the user enter another character
				}
			}
		}
	}

	/**
	 * Creates a Hashtable from a WordTree
	 * @param tree The WordTree of words
	 * @return A Hashtable containing all of the possible prefixes of any word, and its 3 most popular words
	 */
	private static Hashtable createHashtable(WordTree tree) {
		Hashtable hashtable = new Hashtable(HASHTABLE_SIZE);

		int count = tree.reset();
		for (int i1 = 0; i1 < count; i1++) {
			WordRec wr = tree.getNext();
			WordChoice wc = new WordChoice(wr.getWord(), wr.getCount());
			addWordToHashtable(hashtable, wc);
		}

		return hashtable;
	}

	/**
	 * Adds every prefix of a word (including the word itself) to a given Hashtable
	 * @param table The Hashtable to add the words to
	 * @param WordChoice the word to add to the Hashtable
	 */
	private static void addWordToHashtable(Hashtable table, WordChoice wc) {
		String str = wc.getWord();
		for (int i2 = 0; i2 < str.length(); i2++) {
			String prefix = str.substring(0, i2 + 1);
			table.add(prefix, wc);
		}
	}

	/**
	 * Prints out all of the words in the list passed
	 * @param words The ASL3 of words to print out
	 */
	private static void printPossibleWords(String prefix, ASL3<WordChoice> words) {
		System.out.println("The word so far: " + prefix + "\n");
		System.out.println("Matching words:");
		words.reset();
		for (int i1 = 0; i1 < words.size(); i1++) {
			String word = words.getNext().getWord();
			System.out.println((i1 + 1) + ". " + word + "\n");
		}
	}

	/**
	 * Gets a single character input from the user. Makes sure that they only enter 1 character
	 * @return The character that the user entered
	 */
	private static String getCharacterFromUser(boolean first) {
		if (first) {
			System.out.println("Enter a single character:");
		} else {
			System.out.println("Enter a number if you see your word, or another letter:");
		}
		String inStr = input.nextLine();
		while (inStr.length() > 1 || inStr.length() == 0) {
			System.out.println("Please enter only 1 character. Try again:");
			inStr = input.nextLine();
		}
		return inStr;
	}

	/**
	 * Gets either a 1, 2, 3, or none from the user, when they are choosing if one of the words matches their word
	 * @return a char containing 1, 2, 3, or n (for none)
	 */
	private static char getWordChoice() {
		System.out.println("Enter the number of the word that matches, or enter \"none\" to enter another character.");
		String inStr = input.nextLine();
		while (!inStr.equals("1") && !inStr.equals("2") && !inStr.equals("3") && !inStr.equals("none")) {
			System.out.println("Your input was not recognized. Enter again:");
			inStr = input.nextLine();
		}
		char firstChar = inStr.charAt(0);
		return firstChar;
	}
}
