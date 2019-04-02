package temple.edu;
import java.io.Serializable;
import java.util.ArrayList;
public class Item implements Serializable{
    private static final long serialVersionId = 1234;


    //this classs is for the books list
    private int id;
    private String bookTitle;   //book title and its body
    private String body;
    private String author;
    private int published;
    private String coverURL;
    // Save the Data
    //constructor for the class
    public Item(String bookTitle, String body){
        this.bookTitle = bookTitle;
        this.body = body;
    }

    public String getTitle(){  //get the title
        return bookTitle;
    }
    public String getBody(){
        return body;
    }
    public static  ArrayList<Item>  getItems(){  //get the books or item
//        ArrayList<Item> items = new ArrayList<Item>();
//        items.add(new Item("Becomming", "This is Becoming books"));
//        items.add(new Item("Educated", "This is Educated books"));
//        items.add(new Item("The Complete", "This is The Complete books"));
//        items.add(new Item("Calculus", "This is Calculus books"));
//        items.add(new Item("Chemistry", "This is Chemistry books"));
    return BookApi.books;
    }

    public Item(int id, String bookTitle, String author, int published, String coverURL) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.author = author;
        this.published = published;
        this.coverURL = coverURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    @Override
    public String toString(){

        return getTitle();
    }
}
