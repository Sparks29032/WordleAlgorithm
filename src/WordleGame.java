import java.io.*;
import java.util.*;

public class WordleGame {
	String[] words;
	
	public static void main(String[] args) throws IOException {
		WordleGame wg = new WordleGame("Words");
		wg.getWords("WordleWords", "Words2");
		wg.playGame("party");
	}
	
	public WordleGame(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		ArrayList<String> words = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(br.readLine());
		while (st.hasMoreTokens()) {
			words.add(st.nextToken());
		}
		
		this.words = new String[words.size()];
		for (int i = 0; i < this.words.length; i++) {
			this.words[i] = words.remove(words.size() - 1);
		}
		br.close();
	}
	
	public void getWords(String wordBank, String wordFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(wordBank));
		ArrayList<String> words = new ArrayList<String>();
		StringTokenizer st;
		
		String s = br.readLine();
		while (s != null) {
			st = new StringTokenizer(s);
			st.nextToken();
			st.nextToken();
			words.add(st.nextToken());
			s = br.readLine();
		}
		
		this.words = new String[words.size()];
		for (int i = 0; i < this.words.length; i++) {
			this.words[i] = words.remove(words.size() - 1);
		}
		br.close();
		
		PrintWriter pw = new PrintWriter(new File(wordFile));
		for (int i = 0; i < this.words.length; i++) {
			pw.print(this.words[i] + " ");
		}
		pw.close();
	}
	
	public void playGame(String target) {
		if (!isWord(target)) {
			System.out.println("Sorry selected word is not valid.");
			return;
		}
		
		int c = 0;
		Scanner reader = new Scanner(System.in);
		String guess;
		char[] result;
		boolean endGame = true;
		
		while (c < 6) {
			System.out.print("Enter in guess: ");
			guess = reader.nextLine();
			result = giveResult(guess, target);
			
			if (result != null) {
				endGame = true;
				for (int i = 0; i < 5; i++) {
					if (result[i] != 'G') {
						endGame = false;
					}
					System.out.print(result[i]);
				}
				System.out.println();
				if (endGame) {
					System.out.println("Congratulations, you won.");
					reader.close();
					return;
				}
				c++;
			}
			else {
				System.out.println("Sorry, that is not a valid guess, please try again.");
			}
		}
		
		reader.close();
		return;
	}
	
	public char[] giveResult(String guess, String target) {
		guess = guess.toLowerCase();
		target = target.toLowerCase();
		
		if (guess.length() != 5 || target.length() != 5) {
			return null;
		}
		
		if (!isWord(guess) || !isWord(target)) {
			return null;
		}
		
		char[] result = new char[5];
		
		for (int i = 0; i < 5; i++) {
			if (guess.charAt(i) == target.charAt(i)) {
				result[i] = 'G';
				target = target.substring(0, i) + ' ' + target.substring(i + 1, 5);
			}
		}
		
		for (int i = 0; i < 5; i++) {
			if (result[i] == 'G') {
				
			}
			else if (target.indexOf(guess.charAt(i)) >= 0) {
				result[i] = 'Y';
				int x = target.indexOf(guess.charAt(i));
				target = target.substring(0, x) + ' ' + target.substring(x + 1, 5);
			}
		}
		
		for (int i = 0; i < 5; i++) {
			if (result[i] == '\0') {
				result[i] = 'B';
			}
		}
		
		return result;
	}
	
	public boolean isWord(String word) {
		for (int i = 0; i < words.length; i++) {
			if (word.equals(words[i])) {
				return true;
			}
		}
		
		return false;
	}
}
