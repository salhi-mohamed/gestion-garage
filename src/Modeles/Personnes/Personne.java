
package Modeles.Personnes;

//Classe Personne abstraite
public abstract  class Personne {
    //---------------------attributs--------------------
    private int id;
    private String nom;
    private String prenom;
    private int telephone ;
    private String adresse;


    //--------------------constructeurs--------------------
    public Personne(int id , String nom , String prenom , int telephone , String adresse)
    {
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.telephone=telephone;
        this.adresse=adresse;
    }

    public Personne() {

    }


    //--------------------geters & seters--------------------

    public void set_id(int id)
    {
        this.id=id;
    }

    public void set_nom(String nom)
    {
        this.nom=nom;
    }

    public void set_prenom(String prenom)
    {
        this.prenom=prenom;
    }

    public void set_telephone(int telephone)
    {
        this.telephone=telephone;
    }

    public void set_adresse(String adresse)
    {
        this.adresse=adresse;
    }

    public int get_id()
    {
        return this.id;
    }

    public String get_nom()
    {
        return this.nom;
    }

    public String get_prenom()
    {
        return this.prenom;
    }

    public int get_telephone()
    {
        return this.telephone;
    }

    public String get_adresse()
    {
        return this.adresse;
    }
    //--------------------methodes--------------------
    
//Methode abstraite pour modifier une personne
   public abstract void modifier();
//Méthode pour afficher une personne 
   public void afficher()
   {
       System.out.println("ID : "+this.get_id());
              System.out.println("Nom : "+this.get_nom());
           System.out.println("Prénom : "+this.get_prenom());
                System.out.println("Téléphone  : "+this.get_telephone());
                   System.out.println("Adresse : "+this.get_adresse());

   }
    @Override
   public String toString()
    {
        return("Id : "+this.id+ "\n" +"Nom : "+this.nom+ "\n" +"Prenom : "+this.prenom+ "\n"  +"téléphone : "+this.telephone+ "\n" +"adresse : "+this.adresse);
    }
   
    
       
    
            
}
