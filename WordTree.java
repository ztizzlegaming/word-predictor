import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import ch08.trees.BinarySearchTree;

/**
 * WordTree class
 *
 * @author Jordan Turley
 */
public class WordTree {
	private BinarySearchTree<WordRec> tree;

	/**
	 * Creates a new WordTree from a given path file
	 * @param path The path to read in the WordTree from 
	 * @throws FileNotFoundException If the path to the file does not exist
	 */
	public WordTree(String path) throws FileNotFoundException {
		tree = new BinarySearchTree<WordRec>();

		Scanner s = new Scanner(new File(path));
		while (s.hasNext()) {
			String next = s.next();
			next = processString(next);
			if (next.length() == 0) {
				continue;
			}

			WordRec wc = new WordRec(next);
			WordRec wordFromTree = tree.get(wc);
			if (wordFromTree != null) {
				wordFromTree.increment();
			} else {
				tree.add(wc);
			}
		}
	}

	/**
	 * Performs an inorder traversal of the tree and prints each word and its count
	 */
	public void dump() {
		int count = reset();
		for (int i1 = 0; i1 < count; i1++) {
			WordRec wr = getNext();
			System.out.println(wr);
		}
	}

	/**
	 * Prepares the tree for an inorder traversal
	 * @return The number of nodes in the tree
	 */
	public int reset() {
		return tree.reset(BinarySearchTree.INORDER);
	}

	/**
	 * @return The next WordRec in the tree of an inorder traversal
	 */
	public WordRec getNext() {
		return tree.getNext(BinarySearchTree.INORDER);
	}

	/**
	 * Removes all of the leading and trailing punctuation from a String and converts it to lower case
	 * I got some help on regular expressions for this from here: http://stackoverflow.com/a/12506842
	 * @param s The String to remove the punctuation from and convert to lower case
	 * @return A new String with the punctuation removed and lower case
	 */
	public static String processString(String s) {
		return s.replaceAll("^[^a-zA-Z0-9]+", "").replaceAll("[^a-zA-Z0-9]+$", "").toLowerCase();
	}

	public static void main(String[] args) {
		try {
			WordTree wordTree = new WordTree("test.txt");
			wordTree.dump();

			//This just gets the word with the highest count from the tree
			int count = wordTree.reset();
			WordRec top = wordTree.getNext();
			for (int i1 = 0; i1 < count - 1; i1++) {
				WordRec next = wordTree.getNext();
				if (next.getCount() > top.getCount()) {
					top = next;
				}
			}
			System.out.println("Top word: " + top);
		} catch (FileNotFoundException e) {
			System.out.println("WordTree file not found.");
		}
	}
}