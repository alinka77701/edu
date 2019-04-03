package nevernote.note.server;

public class InvalidNoteFault extends Exception {
    protected String title;
	
	public InvalidNoteFault() {}
	
	public InvalidNoteFault(String name) {
		this.title = name;
	}
	
	public void setTitle(String name) {
		this.title = name;
	}
	
	public String getTitle() {
		return title;
	}
	
	@Override
	public String getMessage() {
		return "Note '" + title + "' doesn't exist!";
	}
}
