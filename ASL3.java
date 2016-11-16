/**
 * ASL3 class, extends ArraySortedList, adds addBd method that only keeps the 3 highest items in the list.
 * 
 * @author Jordan Turley
 */
public class ASL3<T> extends ArraySortedList<T> {
	/**
	 * Creates a new ASL3
	 */
	public ASL3() {
		super(4);
	}

	/**
	 * Adds to the list, but if there are more than 3 items in the list after adding, only keeps the top 3.
	 * @param element The item to add to the list
	 */
	public void addBd(T element) {
		add(element);

		if (numElements > 3) {
			remove(list[3]);
		}
	}

	public static void main(String[] args) {
		ASL3<WordChoice> list = new ASL3<WordChoice>();

		list.addBd(new WordChoice("a", 1));
		System.out.println("The list should be {(\"a\", 1)}:\n" + list);

		list.addBd(new WordChoice("b", 5));
		list.addBd(new WordChoice("asdf", 10));
		System.out.println("The list should be {(\"asdf\", 10), (\"b\", 5), (\"a\", 1)}:\n" + list);

		list.addBd(new WordChoice("z", 20));
		list.addBd(new WordChoice("123", 15));
		System.out.println("The list should be {(\"z\", 20), (\"123\", 15), (\"asdf\", 10)}:\n" + list);

		list.addBd(new WordChoice("wiofj", 5));
		System.out.println("The list should still be {(\"z\", 20), (\"123\", 15), (\"asdf\", 10)}:\n" + list);
	}
}