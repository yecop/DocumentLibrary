package application.model;

public class Article extends Document {
    private String ssn;

    public Article(String id, String title, String publicationDate, String[] authors, String publisher, String creationDate, String creator, String ssn, String status) {
        super(id, title, publicationDate, authors, publisher, creationDate, creator, status);
        this.ssn = ssn;
    }

    public String getSSN() {
        return ssn;
    }

    @Override
    public String getSpecificDetails() {
        return ssn;
    }

	public void setSSN(String ssn) {
		this.ssn = ssn;
	}
}
