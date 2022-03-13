import java.util.*;
import java.io.*;

public class Guesser {
	WordleGame wg;
	ArrayList<String> words;
	
	public Guesser(String wordBank, String firstWords) throws IOException {
		reset(wordBank, firstWords);
	}
	
	public static void main(String[] args) throws IOException {
		Guesser g = new Guesser("Words2", "GoodFirstWords");
		g.manuelGuess();
	}
	
	public void manuelGuess() {
		Scanner reader = new Scanner(System.in);
		String guess = words.get(0);
		System.out.println(words.get(0));
		String colors = reader.nextLine().toUpperCase();
		while (instancesOf(colors, 'G') != colors.length()) {
			guess = guess(guess, toChar(colors));
			System.out.println(guess);
			colors = reader.nextLine().toUpperCase();
		}
		reader.close();
	}
	
	public String autoGuess(char[] colors) {
		return guess(words.get(0), colors);
	}
	
	public void reset(String wordBank, String firstWords) throws IOException {
		wg = new WordleGame(wordBank);
		words = new ArrayList<String>();
		FirstWord fw = new FirstWord();
		fw.generateFirstWordsList(wordBank, firstWords);
		BufferedReader br = new BufferedReader(new FileReader(firstWords));
		String str = br.readLine();
		while (str != null) {
			words.add(str);
			str = br.readLine();
		}
		
		br.close();
	}
	
	public String guess(String guess, char[] result) {
		WordRanker wr = new WordRanker();
		words = wr.getRankedList(words);
		//***EVENTUALLY IMPLEMENT***
		
		if (guess.length() != result.length) {
			throw new IllegalArgumentException();
		}
		
		guess = guess.toLowerCase();
		
		String str;
		int[] ct = new int[26];
		
		for (int i = 0; i < result.length; i++) {
			if (result[i] == 'G') {
				ct[(int) guess.charAt(i) - 97]++;
				Iterator<String> it = words.iterator();
				while (it.hasNext()) {
					str = it.next();
					if (str.charAt(i) != guess.charAt(i)) {
						it.remove();
					}
				}
			}
			else if (result[i] == 'Y') {
				ct[(int) guess.charAt(i) - 97]++;
				Iterator<String> it = words.iterator();
				while (it.hasNext()) {
					str = it.next();
					if (str.charAt(i) == guess.charAt(i)) {
						it.remove();
					}
				}
			}
		}
		
		for (int i = 0; i < result.length; i++) {
			if (result[i] == 'B') {
				Iterator<String> it = words.iterator();
				while (it.hasNext()) {
					str = it.next();
					if (instancesOf(str, guess.charAt(i)) != ct[(int) guess.charAt(i) - 97]) {
						it.remove();
					}
				}
			}
			else {
				Iterator<String> it = words.iterator();
				while (it.hasNext()) {
					str = it.next();
					if (instancesOf(str, guess.charAt(i)) < ct[(int) guess.charAt(i) - 97]) {
						it.remove();
					}
				}
			}
		}
		
		if (words.size() == 0) {
			return null;
		}
		return words.get(0);
	}
	
	public int instancesOf(String s, char c) {
		int instances = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c) {
				instances++;
			}
		}
		return instances;
	}
	
	public char[] toChar(String colors) {
		colors = colors.toUpperCase();
		char[] cChars = new char[colors.length()];
		for (int i = 0; i < colors.length(); i++) {
			cChars[i] = colors.charAt(i);
		}
		return cChars;
	}
}
