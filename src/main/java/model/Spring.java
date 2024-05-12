package model;

/**
 * A forrásokért felelős osztály
 * A rendszert, pontosabban a belőle kivezető csöveket látja el vízzel
 */
public class Spring extends FieldNode {
    /**
     * Konstruktor
     */
    public Spring() {
        // This constructor is intentionally left empty
        // The Spring class does not require any specific initialization logic
    }
    /**
     * Egy időegység elteltét jelenti.
     * A forráshoz kapcsolt csövekbe a lehető legtöbb vizet folyatja a forrásból.
     */
    @Override
    public void tick() {
        for (Pipe pipe : pipes) {
            pipe.flow(Integer.MAX_VALUE);
        }
    }

    /**
     * Eldönti, hogy a paraméterként kapott mező szomszédja e a forrásnak, azaz a forráshoz
     * csatlakoztatott cső-e vagy nem.
     * @param field - A mező amiről eldöntjük, hogy szomszédja e a
     * @return igaz, ha szomszédja, hamis, ha nem.
     */
    @Override
    public boolean hasNeighbour(Field field) {
        return pipes.contains(field);
    }
}
