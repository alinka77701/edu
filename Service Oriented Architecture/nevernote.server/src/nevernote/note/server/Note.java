package nevernote.note.server;


public class Note {
	int id;
	String title;
	String description;
	Boolean starred;
	String created;
	int notebookId;
	int userId;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Note() {
		super();
	}
	
	public Note(int id, String title, String description, Boolean starred, String created, int notebookId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.starred = starred;
		this.created = created;
		this.notebookId = notebookId;
	}
	public int getNoteBookId() {
		return notebookId;
	}
	public void setNoteBookId(int notebookId) {
		this.notebookId = notebookId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getStarred() {
		return starred;
	}
	public void setStarred(Boolean starred) {
		this.starred = starred;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}	
}
