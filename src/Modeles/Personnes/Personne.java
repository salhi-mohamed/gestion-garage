/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles.Personnes;

/**
 *
 * @author LENOVO
 */
public abstract  class Personne {
    //attributs
    private int id;
    private String nom;
    private String prenom;
    private int telephone ;
    private String adresse;


    //constructeur
    public Personne(int id , String nom , String prenom , int telephone , String adresse)
    {
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.telephone=telephone;
        this.adresse=adresse;
    }


    ///////////////////geters & seters//////////////////

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






    /////////////methodes////////////////////

   public abstract void modifier();
    @Override
   public String toString()
    {
        return("Id : "+this.id+ "\n" +"Nom : "+this.nom+ "\n" +"Prenom : "+this.prenom+ "\n"  +"téléphone : "+this.telephone+ "\n" +"adresse : "+this.adresse);
    }
   public void afficher()
   {
       System.out.println("ID : "+this.get_id());
              System.out.println("Nom : "+this.get_nom());
           System.out.println("Prénom : "+this.get_prenom());
                System.out.println("Téléphone  : "+this.get_telephone());
                   System.out.println("Adresse : "+this.get_adresse());

   }
    
       
    
            
}
