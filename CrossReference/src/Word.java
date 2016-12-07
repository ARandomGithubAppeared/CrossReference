import java.util.ArrayList;

public class Word implements Comparable<Word> {
	private String stem;
	private ArrayList<Document> docArray;
	
	public Word(String string) {
		stem=string;
		docArray=new ArrayList<Document>();
	}

	public void add(Document document) {
		docArray.add(document);
	}

	public String getStem() {
		return stem;
	}

	@Override
	public int compareTo(Word o) {
		return this.getStem().compareTo(o.getStem());
	}

	public void listDocs(){
		for(Document doc: docArray){
			System.out.println("document_"+doc.getDocId());
		}
	}
	
}
