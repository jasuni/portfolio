package hangman;

public class Pattern implements Comparable<Pattern> {
	
	private String wordPattern;
	public final static char PLACEHOLDER_CHAR = '-';
	
	// TODO make case-insensitive
	// creates a pattern for a blank word
	public Pattern(int length) {
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < length; i++) {
			build.append(PLACEHOLDER_CHAR);
		}
		wordPattern = build.toString();
	}
	
	public Pattern(Pattern known, String word, char guessed) {
		// Could check that known and word have same length...
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == guessed) {
				build.append(guessed);
			} else if (known.charAt(i) != PLACEHOLDER_CHAR) {
				build.append(known.charAt(i) );
			} else {
				build.append(PLACEHOLDER_CHAR);
			}
		}
		wordPattern = build.toString();
	}
	
	//private Pattern
	
	public int length() {
		return wordPattern.length();
	}
	
	public char charAt(int i) {
		return wordPattern.charAt(i);
	}
	
	public String toString() {
		return wordPattern;
	}
	
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else if (o.getClass() != this.getClass()) {
			return false;
		} else if (this == o) {
			return true;
		} else {
			return ((Pattern) o).wordPattern.equals(this.wordPattern);
		}
	}
	
	public int hashCode() {
		return wordPattern.hashCode();
	}

	@Override
	public int compareTo(Pattern arg0) { // will go for negative values
		if (this.getNumLetters() != arg0.getNumLetters()) {
			return this.getNumLetters() - arg0.getNumLetters(); // + if this has more letters
		}
		// if equal letters, then go for rightmost letter
		for (int i = wordPattern.length()-1; i > 0; i--) {
			if (charAt(i) == PLACEHOLDER_CHAR) {
				if (arg0.charAt(i) != PLACEHOLDER_CHAR) {
					return -1;
				}
			} else {
				if (arg0.charAt(i) == PLACEHOLDER_CHAR) {
					return 1;
				}
			}
		}
		if (arg0.equals(this)) {
			return 0;
		} else {
			System.out.println("Pattern.compareTo() not working properly"); // should never hit
			//throw new Exception();
		}
		return 0; // should be equal
	}
	
	public int getNumLetters() {
		int letters = 0;
		for (int i = 0; i < wordPattern.length(); i++) {
			if (charAt(i) != PLACEHOLDER_CHAR) {
				letters++;
			}
		}
		return letters;
	}
	
	// methods that may be useful: make pattern
	
	// makes a new pattern for the given word based on this pattern and on the guessedLetter
	//public Pattern makePattern(String word, char guessedLetter) {
		
	//}
}
