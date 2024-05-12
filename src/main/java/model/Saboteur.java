package model;

/**
 * A Saboteur osztálya.
 */
public class Saboteur extends Player {

    /**
     * Konstruktor.
     */
    public Saboteur() {
        // This constructor is intentionally left empty
        // The Saboteur class inherits the constructor from the Player class
        // and does not require any additional initialization logic
    }
    /**
     * A szabotőr csúszóssá teszi a paraméterben kapott csövet
     * @param p - a cső amit a szabotőr csúszóssá tesz
     */
    public void makeSlippery(Pipe p) {
        p.makeSlippery();
    }
}
