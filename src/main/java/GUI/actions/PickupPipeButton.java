package GUI.actions;

import java.awt.event.ActionEvent;

import GUI.Controller;
import model.Cistern;
import model.Mechanic;
import model.Player;
/**
 * Gomb a cső felvételéhez.
 */
public class PickupPipeButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet és tualjdonságok beállítása.
     */
    public PickupPipeButton() {
        super();

        this.setName("pickupPipeButton");
        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("PICKUP PIPE");
        this.addActionListener((ActionEvent e) -> Controller.instance.pickupPipe());
    }
    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 0,
     * és a kiválasztott játékos pozíciója ciszterna, és a ciszternánál van cső,
     * és a játékosnál nincs cső, akkor igazat ad vissza.
     * @return igaz, ha a gomb használható, hamis, ha nem.
     */
    public boolean canPerform() {
        if (Controller.instance == null)
            return false;

        Player player = Controller.instance.selectedPlayer;
        if (!(player instanceof Mechanic))
            return false;

        if (!Controller.instance.selectedFields.isEmpty()
                || !(player.getPosition() instanceof Cistern))
            return false;
        return ((Cistern)player.getPosition()).isPipeAvailable() && ((Mechanic) player).getPipe() == null;
    }
}
