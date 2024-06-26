package GUI.actions;

import java.awt.event.ActionEvent;

import GUI.Controller;
import model.Field;
import model.Pipe;
import model.Player;

/**
 * Gomb a cső eltöréséhez.
 */
public class BreakPipeButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet beállítása.
     */
    public BreakPipeButton() {
        super();

        this.setName("breakPipeButton");
        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("BREAK PIPE");
        this.addActionListener((ActionEvent e) -> Controller.instance.breakPipe());
    }

    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 0,
     * és a kiválasztott játékos pozíciója cső, és a cső nem törhetetlen, akkor igazat ad vissza.
     * @return igaz, ha a gomb használható, hamis, ha nem.
     */
    public boolean canPerform() {
        if (Controller.instance == null)
            return false;

        Player player = Controller.instance.selectedPlayer;
        if (player == null)
            return false;

        if (!Controller.instance.selectedFields.isEmpty())
            return false;

        Field position = player.getPosition();
        return position instanceof Pipe && !((Pipe) position).isBroken() && ((Pipe) position).isBreakable();
    }
}
