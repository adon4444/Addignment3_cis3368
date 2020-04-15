package sample;

public class Book extends Item implements Feature {
    String category1;
    Book(int i, String n, String a, String l, int q, String c){
        super(i,n,a,l,q);
        category1=c;
    }
    public void getFeature(){
        System.out.println(category1);
    }
    public String toString(){
        return(id +"\t"+ name + author + location + quantity +"\t"+ category1);
    }
    public String getCategory1() {
        return category1;
    }
}