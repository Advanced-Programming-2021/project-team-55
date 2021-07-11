package yugioh.server.model.cards;

import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.monsters.*;
import yugioh.server.model.cards.trapandspells.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Card {

    public static ArrayList<Card> allCards;

    static {
        allCards = new ArrayList<>();
    }

    protected String name;
    protected String description;
    protected int price;
    protected SpellOrTrap magicType;
    protected Kind cardKind;

    public Card(String name, String description, int price, Kind cardKind, SpellOrTrap magicType) {
        setName(name);
        setDescription(description);
        setPrice(price);
        this.cardKind = cardKind;
        this.magicType = magicType;

        if (getCardByName(name) == null) {
            allCards.add(this);
        }
    }

    public static ArrayList<Card> getCards() {
        return allCards;
    }

    public static void setCards(ArrayList<Card> cards) {
        Card.allCards = cards;
    }

    public static Card getCardByName(String name) {
        for (Card card : allCards) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }

    public static ArrayList<Card> sortCards(ArrayList<Card> cardsToBeSorted) {
        Collections.sort(cardsToBeSorted, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return card1.name.compareTo(card2.name);
            }
        });
        return cardsToBeSorted;
    }

    public static ArrayList<Card> getMonstersSorted(ArrayList<Card> cards) {
        ArrayList<Card> monsters = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Monster) {
                monsters.add(card);
            }
        }
        Collections.sort(monsters, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return card1.name.compareTo(card2.name);
            }
        });
        return monsters;
    }

    public static ArrayList<Card> getMagicsSorted(ArrayList<Card> cards) {
        ArrayList<Card> spellAndTraps = new ArrayList<>();
        for (Card card : cards) {
            if (!(card instanceof Monster)) {
                spellAndTraps.add(card);
            }
        }
        Collections.sort(spellAndTraps, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return card1.name.compareTo(card2.name);
            }
        });
        return spellAndTraps;
    }

    public static Card getNewCardObjectByName(String name) {

        switch (name) {
            case "Battle OX":
                return new BattleOX();

            case "Axe Raider":
                return new AxeRaider();

            case "Yomi Ship":
                return new YomiShip();

            case "Horn Imp":
                return new HornImp();

            case "Silver Fang":
                return new SilverFang();

            case "Suijin":
                return new Suijin();

            case "Fireyarou":
                return new Fireyarou();

            case "Curtain of the dark ones":
                return new Curtainofthedarkones();

            case "Feral Imp":
                return new FeralImp();

            case "Dark magician":
                return new Darkmagician();

            case "Wattkid":
                return new Wattkid();

            case "Baby dragon":
                return new Babydragon();

            case "Hero of the east":
                return new Herooftheeast();

            case "Battle warrior":
                return new Battlewarrior();

            case "Crawling dragon":
                return new Crawlingdragon();

            case "Flame manipulator":
                return new Flamemanipulator();

            case "Blue-Eyes white dragon":
                return new BlueEyeswhitedragon();

            case "Crab Turtle":
                return new CrabTurtle();

            case "Skull Guardian":
                return new SkullGuardian();

            case "Slot Machine":
                return new SlotMachine();

            case "Haniwa":
                return new Haniwa();

            case "Man-Eater Bug":
                return new ManEaterBug();

            case "Gate Guardian":
                return new GateGuardian();

            case "Scanner":
                return new Scanner();

            case "Bitron":
                return new Bitron();

            case "Marshmallon":
                return new Marshmallon();

            case "Beast King Barbaros":
                return new BeastKingBarbaros();

            case "Texchanger":
                return new Texchanger();

            case "Leotron ":
                return new Leotron();

            case "The Calculator":
                return new TheCalculator();

            case "Alexandrite Dragon":
                return new AlexandriteDragon();

            case "Mirage Dragon":
                return new MirageDragon();

            case "Herald of Creation":
                return new HeraldofCreation();

            case "Exploder Dragon":
                return new ExploderDragon();

            case "Warrior Dai Grepher":
                return new WarriorDaiGrepher();

            case "Dark Blade":
                return new DarkBlade();

            case "Wattaildragon":
                return new Wattaildragon();

            case "Terratiger, the Empowered Warrior":
                return new TerratigertheEmpoweredWarrior();

            case "The Tricky":
                return new TheTricky();

            case "Spiral Serpent":
                return new SpiralSerpent();

            case "Command Knight":
                return new CommandKnight();



            case "Trap Hole":
                return new TrapHole();

            case "Mirror Force":
                return new MirrorForce();

            case "Magic Cylinder":
                return new MagicCylinder();

            case "Mind Crush":
                return new MindCrush();

            case "Torrential Tribute":
                return new TorrentialTribute();

            case "Time Seal":
                return new TimeSeal();

            case "Negate Attack":
                return new NegateAttack();

            case "Solemn Warning":
                return new SolemnWarning();

            case "Magic Jamamer":
                return new MagicJamamer();

            case "Call of The Haunted":
                return new CallofTheHaunted();

            case "Vanity's Emptiness":
                return new VanitysEmptiness();

            case "Wall of Revealing Light":
                return new WallofRevealingLight();

            case "Monster Reborn":
                return new MonsterReborn();

            case "Terraforming":
                return new Terraforming();

            case "Pot of Greed":
                return new PotofGreed();

            case "Raigeki":
                return new Raigeki();

            case "Change of Heart":
                return new ChangeofHeart();

            case "Swords of Revealing Light":
                return new SwordsofRevealingLight();

            case "Harpie's Feather Duster":
                return new HarpiesFeatherDuster();

            case "Dark Hole":
                return new DarkHole();

            case "Supply Squad":
                return new SupplySquad();

            case "Spell Absorption":
                return new SpellAbsorption();

            case "Messenger of peace":
                return new Messengerofpeace();

            case "Twin Twisters":
                return new TwinTwisters();

            case "Mystical space typhoon":
                return new Mysticalspacetyphoon();

            case "Ring of defense":
                return new Ringofdefense();

            case "Yami":
                return new Yami();

            case "Forest":
                return new Forest();

            case "Closed Forest":
                return new ClosedForest();

            case "Umiiruka":
                return new Umiiruka();

            case "Sword of dark destruction":
                return new Swordofdarkdestruction();

            case "Black Pendant":
                return new BlackPendant();

            case "United We Stand":
                return new UnitedWeStand();

            case "Magnum Shield":
                return new MagnumShield();

            case "Advanced Ritual Art":
                return new AdvancedRitualArt();

            default:
                return null;
        }

    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    protected void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }

    public boolean isMonster() {
        return cardKind == Kind.MONSTER;
    }

    public boolean isSpell() {
        if (cardKind == Kind.MONSTER) {
            return false;
        } else {
            return magicType == SpellOrTrap.SPELL;
        }
    }

    public boolean isSpellAndTrap() {
        return cardKind != Kind.MONSTER;
    }


    public Card clone() {
        return Card.getNewCardObjectByName(this.getName());
    }

    public Kind getCardKind() {
        return cardKind;
    }

    public enum Kind {MONSTER, MAGIC}

}
