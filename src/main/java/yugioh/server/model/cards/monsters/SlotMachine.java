package yugioh.server.model.cards.monsters;

import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

public class SlotMachine extends Monster {

    public SlotMachine() {
        super("Slot Machine", "The machine's ability is said to vary according to its slot results."
                , 7500, 2000, 2300, 7, MonsterAttribute.DARK, MonsterType.MACHINE, CardType.NORMAL);
    }

}
