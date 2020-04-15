package sample;

public class Media extends Item implements Feature{
    String category2;
    Media(int i, String n, String a, String l, int q, String c){
        super(i,n,a,l,q);
        category2 =c;
    }
    public void getFeature(){
        System.out.println(category2);
    }

    public String getCategory2() {
        return category2;
    }
    public String toString(){
        return(id +"\t"+ name + author + location + quantity +"\t"+ category2);
    }
}
