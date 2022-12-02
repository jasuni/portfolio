package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class EvilHangmanGame implements IEvilHangmanGame {

	private Set<Character> guessedLetters;
	private Set<String> dictionary;
	private int guesses;
	private Pattern word; // the pattern of the word that the game has "chosen"
	public static final int DEFAULT_GUESSES = 10;
	// pattern that all words satisfy

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Usage: java [your main class name] dictionary wordLength guesses
		EvilHangmanGame game = new EvilHangmanGame(new File(args[0]), Integer.parseInt(args[1]) ); // starts the game automatically
		game.playGame(Integer.parseInt(args[2]));

	}
	
	public EvilHangmanGame() {
		guessedLetters = null;
		dictionary = null;
		guesses = DEFAULT_GUESSES;
		word = null;
	}
	
	public EvilHangmanGame(File dictionary, int wordLength ) {
		this();
		this.startGame(dictionary, wordLength);
	}
	
	@Override
	public void startGame(File dictionary, int wordLength) {
		if (wordLength < 2) {
			throw new IllegalArgumentException("WordLength must be >= 2");
		}
		try(Scanner scan = new Scanner(dictionary)) {
			// initialize stuff
			guessedLetters = new TreeSet<>();
			this.dictionary = new HashSet<String>();
			word = new Pattern(wordLength);
			// load in words from dictionary
			while (scan.hasNext()) {
				String word = scan.next();
				if (word.length() == this.word.length()) {
					this.dictionary.add(word);
				}
			}
			if (this.dictionary.size() == 0) {
				System.out.println("The dictionary does not contain any words of that length. Try again");
				this.word = null;
				this.dictionary = null;
				this.guessedLetters = null;
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Dictionary file was not found, game cannot be started");
		}
	}
	
	public void playGame(int guesses) {
		if (dictionary == null) {
			throw new IllegalStateException("Game has not yet been started");
		}
		if (guesses < 1) {
			throw new IllegalArgumentException("Guesses must be >= 1");
		}
		Scanner scan = new Scanner(System.in);
		while (guesses > 0) {
			takeTurn(scan);
		}
		if (dictionary.size() == 1 && dictionary.contains(word.toString()) ) {
			// you won
			System.out.println("You win");
		} else {
			// you lost
			System.out.println("You lose!");
			
		}
		System.out.println("The word was: " + getRandomWord());
	}
	
	private String getRandomWord() {
		int stop = (int) (dictionary.size()*Math.random()); // stop will be an int from 0 to size()-1
		Iterator<String> it = dictionary.iterator();
		for (int i = 0; i < stop; i++) {
			// at worst, i is size()-1, so this loop will stop before the last string
			it.next();
		}
		
		return it.next();
	}
	
	private void takeTurn(Scanner scan) {
		startTurn();
		char guess = getNextLetter(scan);
		try {
			Pattern orig = this.word;
			makeGuess(guess);
			if (orig.equals(this.word) ) { // no new chars
				guesses--;
				
			}
		} catch (GuessAlreadyMadeException ex) {
			System.out.println("You already used that letter");
		}
	}
	
	private void startTurn() {
		System.out.println("You have " + guesses + " guesses left");
		System.out.print("Used letters: ");
		printGuessedLetters();
		System.out.print("Word: ");
		System.out.println(word); 
	}
	
	private void printGuessedLetters() {
		for (Iterator<Character> it = guessedLetters.iterator(); it.hasNext();) {
			System.out.print(it.next());
			System.out.print(' ');
		}
		System.out.println();
		//for (Iterator<Character> ch: guessedLetters.iterator()) {
			
		//}
	}
	
	private char getNextLetter(Scanner scan) {
		String next = null; 
		boolean validInput = false;
		while (!validInput) {
			// invalid input
			//System.out.print("Guess a letter: ");
			System.out.print("GIVE ME THAT LETTER!");
			next = scan.nextLine();
			if (next.length() > 1 || !Character.isAlphabetic(next.charAt(0) )) {
				System.out.println("Invalid input");
			} else {
				validInput = true;
			}
		} 
		//char letter = next.charAt(0);
		return Character.toLowerCase(next.charAt(0) );
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		if (guessedLetters.contains(guess) ) {
			throw new GuessAlreadyMadeException();
		} else {
			guessedLetters.add(guess);
		}
		
		// TODO needs to create new dictionary based on the letter guessed, update word
		// 
		return null;
	}
}
