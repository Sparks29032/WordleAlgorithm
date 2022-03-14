import java.util.*;

public class WordRanker {
	public ArrayList<String> getRankedList(ArrayList<String> words) {
		int[] letterFrequency = getLetterFrequency(words);
		char[] rankedLetters = rankLetters(letterFrequency);
		int[] wordScores = getWordScores(words, rankedLetters);
		return rankWords(words, wordScores);
	}
	
	public int[] getLetterFrequency(ArrayList<String> words) {
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
	
	//gives 
	public int[] getWordScores(ArrayList<String> words, char[] rankedLetters) {
		int[] wordScores = new int[words.size()];
		String used;
		String curr;
		for (int i = 0; i < words.size(); i++) {
			used = "";
			for (int j = 0; j < words.get(i).length(); j++) {
				curr = "" + words.get(i).charAt(j);
				if (used.contains(curr)) {
					// heavily penalizes duplicates
					wordScores[i] += 26;
				}
				else {
					wordScores[i] += findIdx(rankedLetters, words.get(i).charAt(j));
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
	
	public ArrayList<String> rankWords(ArrayList<String> words, int[] wordScores) {
		if (words.size() != wordScores.length) {
			throw new IllegalArgumentException();
		}
		
		int l = words.size();
		PriorityQueue<Word> pq = new PriorityQueue<Word>();
		for (int i = 0; i < l; i++) {
			pq.add(new Word(wordScores[i], words.get(i)));
		}
		
		ArrayList<String> rankedWords = new ArrayList<String>();
		for (int i = 0; i < l; i++) {
			rankedWords.add(pq.poll().word);
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
