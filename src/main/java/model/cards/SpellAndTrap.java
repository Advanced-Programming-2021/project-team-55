package model.cards;

import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public abstract class SpellAndTrap extends Card {

    private boolean isActive;

    private SpellOrTrap type;
    private SpellOrTrapAttribute attribute;
    private EffectiveTerm status;

    public SpellAndTrap(String name, String description, int price, boolean isActive,
                        SpellOrTrap type, SpellOrTrapAttribute attribute, EffectiveTerm status) {
        super(name, description, price);
        this.isActive = isActive;
        this.type = type;
        this.attribute = attribute;
        this.status = status;
    }

    public void setActivated() {

    }

    public void deactivate() {

    }

    public boolean isActivated() {
        return isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public SpellOrTrap getType() {
        return type;
    }

    public void setType(SpellOrTrap type) {
        this.type = type;
    }

    public SpellOrTrapAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(SpellOrTrapAttribute attribute) {
        this.attribute = attribute;
    }

    public EffectiveTerm getStatus() {
        return status;
    }

    public void setStatus(EffectiveTerm status) {
        this.status = status;
    }

}
