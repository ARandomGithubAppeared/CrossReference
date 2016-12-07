//Created by Shelby Huston

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class CrossReference {
	private ArrayList<Document> docList;
	private TreeSet<Word> wordTree;
	private ArrayList<Word> wordList;

	public CrossReference(File word, File doc) {
		wordList = new ArrayList<Word>();
		docList = new ArrayList<Document>();
		wordTree = new TreeSet<Word>();

		wordList.add(new Word("dummy header"));
		try {
			BufferedReader feeder = new BufferedReader(new FileReader(word));

			String curLine = feeder.readLine();
			while (curLine != null) {
				wordList.add(new Word(curLine));
				curLine = feeder.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader feeder = new BufferedReader(new FileReader(doc));

			String curLine = feeder.readLine();
			while (curLine != null) {
				Scanner scan = new Scanner(curLine);
				String docString = scan.next();
				Integer docId = stripId(docString);
				// System.out.println(docId);
				docList.add(new Document(docId));
				while (scan.hasNext()) {
					docList.get(docId).add(wordList.get(scan.nextInt()));
				}
				scan.close();
				curLine = feeder.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int x = 0; x < docList.size(); x++) {
			Document curDoc = docList.get(x);
			ArrayList<Word> curArray = curDoc.getArray();
			wordTree.addAll(curArray);
		}

		// java.util.Iterator<Word>tree = wordTree.iterator();
		// int x = 0;
		// while(tree.hasNext()){
		// x=x+1;
		// System.out.println(tree.next().getStem() + " " + x);
		// }

	}

	public Document findDoc(int docId) {
		try {
			return docList.get(docId);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public Word findWord(String find) {
		for (Word word : wordList) {
			if (word.getStem().equals(find)) {
				if (wordTree.contains(word)) {
					return word;
				}
			}
		}
		return null;
	}

	public void occursWith(Word word) {
		TreeSet<Word> with = new TreeSet<Word>();
		for (Document doc : docList) {
			if (doc.contains(word)) {
				with.addAll(doc.getArray());
			}
		}
		for (Word fin : with) {
			System.out.println(fin.getStem());
		}
	}

	public void listWords(Document doc) {
		if (doc != null) {
			doc.listWords();
		} else {
			System.out.println("Document does not exist");
		}
	}

	public void listDocs(Word word) {
		if (word != null) {
			word.listDocs();
		} else {
			System.out.println("Word does not exist in any documents");
		}
	}

	private int stripId(String docString) {
		Integer docId = Integer.parseInt(docString.substring(docString.indexOf('_') + 1, docString.length()));
		return docId;
	}

	public void start() {
		Scanner scan = new Scanner(System.in);
		System.out.println("wordlist <document id> : lists all the words that occur in the specified document");
		System.out.println("doclist <word> : lists all the documents that contain the specified word");
		System.out.println(
				"occurswith <word> : list all the words that occur in documents conatining the specified word");
		System.out.println("quit: quits the program");
		while (scan.hasNext()) {
			String curToken = scan.next().trim();
			if (curToken.equals("wordlist")) {
				this.listWords(findDoc(stripId(scan.next())));
			}
			if (curToken.equals("doclist")) {
				this.listDocs(findWord(scan.next()));
			}
			if (curToken.equals("occurswith")) {
				this.occursWith(findWord(scan.next()));
			}
			if (curToken.equals("quit")) {
				System.exit(0);;
			}

			System.out.println("\n" + "Ready for next command:");
		}
	}

	public static void main(String[] args) {
		File doc = new File(args[0]);
		File word = new File(args[1]);
		CrossReference cr = new CrossReference(doc, word);
		cr.start();

	}
}
