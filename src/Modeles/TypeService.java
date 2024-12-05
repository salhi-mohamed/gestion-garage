package Modeles;

public enum TypeService {
    ENTRETIEN("Entretien"),
    REPARATION("Réparation"),
    DIAGNOSTIC("Diagnostic"),
    LAVAGE("Lavage");

    private final String name;

    TypeService(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
