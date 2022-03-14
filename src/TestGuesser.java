import java.io.*;
import java.util.*;

public class TestGuesser {
	String wordBank, wordFile;
	Guesser g;
	WordleGame wg;
	
	public TestGuesser(String wordBank, String wordFile) throws IOException {
		this.wordBank = wordBank;
		this.wordFile = wordFile;
		g = new Guesser(wordBank, wordFile);
		wg = new WordleGame(wordBank);
	}
	
	public static void main(String[] args) throws IOException {
		TestGuesser tg = new TestGuesser("Words", "GoodFirstWords");
		tg.testManyTimes(2500);
	}
	
	public int startGuessing() throws IOException {
		g.reset(wordBank, wordFile);
		
		int neededGuesses = 1;
		
		Random rand = new Random();
		String target = wg.words[rand.nextInt(wg.words.length)];
		System.out.println("The target is: " + target);
		
		String guess = g.words.get(0);
		System.out.println(guess);
		char[] colors = wg.giveResult(guess, target);
		while (!doneGuessing(colors)) {
			neededGuesses++;
			guess = g.autoGuess(colors);
			System.out.println(guess);
			colors = wg.giveResult(guess, target);
		}
		
		return neededGuesses;
	}
	
	public int getNeededGuesses(String target) throws IOException {
		g.reset(wordBank, wordFile);
		
		int neededGuesses = 1;
		String guess = g.words.get(0);
		char[] colors = wg.giveResult(guess, target);
		while (!doneGuessing(colors)) {
			neededGuesses++;
			guess = g.autoGuess(colors);
			colors = wg.giveResult(guess, target);
		}
		
		return neededGuesses;
	}
	
	public void testManyTimes(int n) throws IOException {
		int total = 0;
		int max = 0;
		int curr;
		int[] histo = new int[7];
		ArrayList<String> hardWords = new ArrayList<String>();
		String target;
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			target = wg.words[rand.nextInt(wg.words.length)];
			curr = getNeededGuesses(target);
			if (curr > 6) {
				histo[6]++;
				total += 6;
			}
			else {
				histo[curr - 1]++;
				total += curr;
			}
			if (max < curr) {
				max = curr;
			}
			if (curr > 6) {
				hardWords.add(target);
			}
		}
		
		System.out.println("Average number of guesses: " + ((double) total / n));
		System.out.println("Maximum guesses used in a round: " + max);
		System.out.print("Words failed: ");
		for (String word : hardWords) {
			System.out.print(word + " ");
		}
		System.out.println();
		
		System.out.println("Guess distribution:");
		for (int i = 0; i < 6; i++) {
			System.out.println((i + 1) + ":\t" + histo[i]);
		}
		System.out.println("X:\t" + histo[6]);
	}
	
	public boolean doneGuessing(char[] colors) {
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] != 'G') {
				return false;
			}
		}
		
		return true;
	}
}
