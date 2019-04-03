package nevernote.notebook.server;

public class InvalidNotebookFault extends Exception {
    protected String name;
	
	public InvalidNotebookFault() {}
	
	public InvalidNotebookFault(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String getMessage() {
		return "Notebook " + name + " doesn't exist!";
	}
}
