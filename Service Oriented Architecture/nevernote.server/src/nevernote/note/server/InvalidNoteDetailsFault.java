package nevernote.note.server;

public class InvalidNoteDetailsFault extends Exception {
	protected String title;
	protected Boolean uniqueTitle;
	
	public InvalidNoteDetailsFault() {}
	
	public InvalidNoteDetailsFault(String name) {
		this.uniqueTitle = false;
		this.title = name;
	}
	
	public void setTitle(String name) {
		this.title = name;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Boolean getUniqueTitle() {
		return uniqueTitle;
	}
	
	@Override
	public String getMessage() {
		return "Note with the title '" + title + "' already exist! Note should have a unique title. ";
	}
}
