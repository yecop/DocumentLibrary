package application.model;

public class Book extends Document {
    private int pageCount;

    public Book(String id, String title, String publicationDate, String[] authors, String publisher, String creationDate, String creator, int pageCount, String status) {
        super(id, title, publicationDate, authors, publisher, creationDate, creator, status);
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public String getSpecificDetails() {
        return String.valueOf(pageCount);
    }

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;	
	}
}
