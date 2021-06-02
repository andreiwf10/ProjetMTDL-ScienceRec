package sample;

public class Articles {

    String nume;
    String hyperlinks;
    String categorie;
    String rating;

    public Articles(String nume,String categorie, String hyperlink) {
        this.nume = nume;
        this.categorie = categorie;
        this.hyperlinks = hyperlink;
    }

    public Articles(String nume,String categorie,String rating, String hyperlinks) {
        this.nume = nume;
        this.categorie = categorie;
        this.rating = rating;
        this.hyperlinks = hyperlinks;
    }

    public Articles(String nume, String rating) {
        this.nume = nume;
        this.rating = rating;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getHyperlinks() {
        return hyperlinks;
    }

    public void setHyperlinks(String hyperlinks) {
        this.hyperlinks = hyperlinks;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
