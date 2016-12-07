import java.util.ArrayList;

public class Document {
	private int docId;
	private ArrayList<Word> wordArray;
	
	public Document(int docId) {
		this.docId=docId;
		wordArray=new ArrayList<Word>();
	}

	public void add(Word word) {
		wordArray.add(word);
		word.add(this);
	}

	public ArrayList<Word> getArray() {
		return wordArray;
	}

	public int getDocId() {
		return docId;
	}
	
	public void listWords(){
		for(Word word: wordArray){
			System.out.println(word.getStem());
		}
	}

	public boolean contains(Word word) {
		if (wordArray.contains(word)){
			return true;
		}
			return false;
	}
}
