package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class SlotMachine extends Monster {

    public SlotMachine() {
        super("Slot Machine", "The machine's ability is said to vary according to its slot results."
                , 7500, 2000, 2300, 7, MonsterAttribute.DARK, MonsterType.MACHINE, CardType.NORMAL);
    }

}
