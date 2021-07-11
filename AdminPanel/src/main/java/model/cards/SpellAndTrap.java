package model.cards;


import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class SpellAndTrap extends Card {

    private final SpellOrTrapAttribute attribute;
    private final EffectiveTerm status;
    protected SpellOrTrap type;

    public SpellAndTrap(String name, String description, int price,
                        SpellOrTrap type, SpellOrTrapAttribute attribute, EffectiveTerm status) {
        super(name, description, price, Kind.MAGIC, type);
        this.type = type;
        this.attribute = attribute;
        this.status = status;
    }


    public SpellOrTrap getType() {
        return type;
    }

    public SpellOrTrapAttribute getAttribute() {
        return attribute;
    }

    public EffectiveTerm getStatus() {
        return status;
    }

}
