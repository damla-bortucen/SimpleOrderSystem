public class Book extends Product{
    private String title;
    private String author;
    private int pages;

    public Book(int code, int price, String title, String author, int pages)
    {
        super(code, price);
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    @Override
    public String getDescription()
    {
        return "Title: " + title + ", Author: " + author + ", Pages: " + pages;
    }
}
