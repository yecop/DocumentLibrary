package application.model;

public class Paper extends Document {
    private String conferenceName;

    public Paper(String id, String title, String publicationDate, String[] authors, String publisher, String creationDate, String creator, String conferenceName, String status) {
        super(id, title, publicationDate, authors, publisher, creationDate, creator, status);
        this.conferenceName = conferenceName;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    @Override
    public String getSpecificDetails() {
        return conferenceName;
    }

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}
}
