import java.io.*;
import java.util.*;

public class FirstWord {
	public static void main(String[] args) throws IOException {
		FirstWord fw = new FirstWord();
		fw.generateFirstWordsList("Words2", "GoodFirstWords");
	}
	
	public void generateFirstWordsList(String wordBank, String firstWords) throws IOException {
		String[] words = getWords(wordBank);
		int[] letterFrequency = getLetterFrequency(words);
		char[] rankedLetters = rankLetters(letterFrequency);
		int[] wordScores = getWordScores(words, rankedLetters);
		String[] rankedWords = rankWords(words, wordScores);
		
		PrintWriter pw = new PrintWriter(new File(firstWords));
		for (int i = 0; i < rankedWords.length; i++) {
			pw.println(rankedWords[i]);
		}
		pw.close();
	}
	
	public String[] getWords(String wordBank) throws IOException {
		String[] words;
		ArrayList<String> wordList = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(wordBank));
		StringTokenizer st = new StringTokenizer(br.readLine());
		while (st.hasMoreTokens()) {
			wordList.add(st.nextToken());
		}
		br.close();
		
		words = new String[wordList.size()];
		for (int i = 0; i < wordList.size(); i++) {
			words[i] = wordList.get(i);
		}
		return words;
	}
	
	public int[] getLetterFrequency(String[] words) {
		int[] letterFrequency = new int[26];
		String temp;
		for (String word : words) {
			temp = word.toLowerCase();
			for (int i = 0; i < temp.length(); i++) {
				letterFrequency[(int) temp.charAt(i) - 97] += 1;
			}
		}
		
		return letterFrequency;
	}
	
	public char[] rankLetters(int[] letterFrequency) {
		PriorityQueue<Letter> pq = new PriorityQueue<Letter>();
		for (int i = 0; i < 26; i++) {
			pq.add(new Letter(letterFrequency[i], (char) (i + 97)));
		}
		
		char[] rankedLetters = new char[26];
		for (int i = 0; i < 26; i++) {
			rankedLetters[i] = pq.poll().letter;
		}
		return rankedLetters;
	}
	
	public class Letter implements Comparable<Letter> {
		int nFrequency;
		char letter;
		
		public Letter(int f, char l) {
			nFrequency = -f;
			letter = l;
		}
		
		public int compareTo(Letter o) {
			return nFrequency - o.nFrequency;
		}
	}
	
	public int[] getWordScores(String[] words, char[] rankedLetters) {
		int[] wordScores = new int[words.length];
		String used;
		String curr;
		for (int i = 0; i < words.length; i++) {
			used = "";
			for (int j = 0; j < words[i].length(); j++) {
				curr = "" + words[i].charAt(j);
				if (used.contains(curr)) {
					wordScores[i] += 26;
				}
				else {
					wordScores[i] += findIdx(rankedLetters, words[i].charAt(j));
					used += curr;
				}
			}
		}
		
		return wordScores;
	}
	
	public int findIdx(char[] letters, char letter) {
		for (int i = 0; i < letters.length; i++) {
			if (letter == letters[i]) {
				return i;
			}
		}
		return -1;
	}
	
	public String[] rankWords(String[] words, int[] wordScores) {
		if (words.length != wordScores.length) {
			throw new IllegalArgumentException();
		}
		
		int l = words.length;
		PriorityQueue<Word> pq = new PriorityQueue<Word>();
		for (int i = 0; i < l; i++) {
			pq.add(new Word(wordScores[i], words[i]));
		}
		
		String[] rankedWords = new String[l];
		for (int i = 0; i < l; i++) {
			rankedWords[i] = pq.poll().word;
		}
		
		return rankedWords;
	}
	
	public class Word implements Comparable<Word> {
		int score;
		String word;
		
		public Word(int s, String w) {
			score = s;
			word = w;
		}
		
		public int compareTo(Word o) {
			return score - o.score;
		}
	}
}