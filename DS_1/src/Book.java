import java.io.Serializable;

public class Book implements Serializable
{
    String title;
    String isbn;
    String author;
    String year;
    String category;

    public Book(String title, String isbn, String author, String year, String category)
    {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.year = year;
        this.category = category;
    }  
}