package nevernote.notebook.server;

public class InvalidNotebookDetailsFault extends Exception {
	protected String name;
	protected Boolean uniqueName;
	
	public InvalidNotebookDetailsFault() {}
	
	public InvalidNotebookDetailsFault(String name) {
		this.uniqueName = false;
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Boolean getUniqueName() {
		return uniqueName;
	}
	
	@Override
	public String getMessage() {
		return "Notebook with the name '" + name + "' already exists! Notebook should have a unique name. ";
	}
}
