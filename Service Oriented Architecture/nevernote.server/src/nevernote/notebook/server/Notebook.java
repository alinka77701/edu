package nevernote.notebook.server;


public class Notebook {
	int id;
	String name;
	String type;
	Boolean readOnly;
	String created;
	Boolean shared;
	int userId;
	
	public Notebook() {
		super();
	}
	
	public Notebook(int id, String name, String type, Boolean readOnly, String created, Boolean shared, int userId) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.readOnly = readOnly;
		this.created = created;
		this.shared = shared;
		this.userId = userId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getReadOnly() {
		return readOnly;
	}
	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public Boolean getShared() {
		return shared;
	}
	public void setShared(Boolean shared) {
		this.shared = shared;
	}	
}
