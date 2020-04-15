package sample;

public class Item {
    int id;
    String name;
    String author;
    String location;
    int quantity;
    Item(int i, String n, String a, String l, int q){
        id=i;
        name=n;
        author=a;
        location=l;
        quantity=q;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getLocation() {
        return location;
    }
    public int getQuantity() {
        return quantity;
    }
}
