//package yugioh.client.model.cards.justforscore;
//
//import com.opencsv.bean.CsvToBeanBuilder;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.util.List;
//
//public class JustForScore {

//    public static List<MonsterCardDetails> importMonstersDetails() throws FileNotFoundException {
//        List<MonsterCardDetails> monsterCardsDetailsList = new CsvToBeanBuilder(
//                new FileReader("src/resources/cards details/Monster.csv"))
//                .withType(MonsterCardDetails.class).build().parse();
//
//        return monsterCardsDetailsList;
//    }
//
//    public static List<TrapAndSpellCardDetails> importTrapAndSpellsDetails() throws FileNotFoundException {
//        List<TrapAndSpellCardDetails> trapAndSpellCardDetailsList = new CsvToBeanBuilder(
//                new FileReader("src/resources/cards details/SpellTrap.csv"))
//                .withType(TrapAndSpellCardDetails.class).build().parse();
//
//        return trapAndSpellCardDetailsList;
//    }

//    public static void main(String[] args) throws IOException {
//        List<MonsterCardDetails> monsters = importMonstersDetails();
//        for (MonsterCardDetails monsterCardsDetails: monsters){
//            String className = monsterCardsDetails.getName().trim()
//                    .replaceAll(" ","").replaceAll("-", "")
//                    .replaceAll(",","").replaceAll("'", "");
//            String fileContent = "package model.cards.monsters;\n" +
//                    "\n" +
//                    "import model.cards.cardfeaturesenums.MonsterAttribute;\n" +
//                    "import model.cards.cardfeaturesenum.CardType;\n\n" +
//                    "\n" +
//                    "public class " + className + " {\n" +
//                    "\n" +
//                    "    private String name = \"" + monsterCardsDetails.getName() + "\";\n" +
//                    "    private int level = " + monsterCardsDetails.getLevel() + ";\n" +
//                    "    private MonsterAttribute attribute = MonsterAttribute." + monsterCardsDetails.getAttribute().toUpperCase() + ";\n" +
//                    "    private String monsterType = \"" + monsterCardsDetails.getMonsterType() + "\";\n" +
//                    "    private CardType cardType = CardType." + monsterCardsDetails.getCardType().toUpperCase() + ";\n" +
//                    "    private int atk = " + monsterCardsDetails.getAtk() + ";\n" +
//                    "    private int def = " + monsterCardsDetails.getDef() + ";\n" +
//                    "    private static final String description = \"" + monsterCardsDetails.getDescription() + "\";\n" +
//                    "    private int price = " + monsterCardsDetails.getPrice() + ";\n" +
//                    "\n" +
//                    "\n" +
//                    "}\n";
//            writeFile("src\\main\\java\\model\\cards\\monsters\\" + className + ".java", fileContent);
//
//        }
//    }

//    public static void main(String[] args) throws IOException {
//        List<TrapAndSpellCardDetails> trapAndSpellCardDetailsList = importTrapAndSpellsDetails();
//        for (TrapAndSpellCardDetails trapAndSpellCardDetails: trapAndSpellCardDetailsList){
//            String className = trapAndSpellCardDetails.getName().trim()
//                    .replaceAll(" ","").replaceAll("-", "")
//                    .replaceAll(",","").replaceAll("'", "");
//            String fileContent = "package model.cards.trapandspells;\n" +
//                    "\n" +
//                    "import model.cards.cardfeaturesenums.SpellOrTrap;\n" +
//                    "import model.cards.cardfeaturesenums.SpellOrTrapAttribute;\n" +
//                    "import model.cards.cardfeaturesenums.Status;\n" +
//                    "\n" +
//                    "public class " + className +" {\n" +
//                    "\n" +
//                    "    private boolean isActive = false;\n" +
//                    "\n" +
//                    "    private String name = \"" + trapAndSpellCardDetails.getName() + "\";\n" +
//                    "    private SpellOrTrap type = SpellOrTrap." + trapAndSpellCardDetails.getType().toUpperCase() + ";\n" +
//                    "    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute." + trapAndSpellCardDetails.getIconOrProperty().toUpperCase() + ";\n" +
//                    "    private static final String description = \"" + trapAndSpellCardDetails.getDescription() + "\";\n" +
//                    "    private Status status = Status." + trapAndSpellCardDetails.getStatus().toUpperCase() + ";\n" +
//                    "    private int price = " + trapAndSpellCardDetails.getPrice() + ";\n" +
//                    "    \n" +
//                    "}";
//            writeFile("src\\main\\java\\model\\cards\\trapandspells\\" + className + ".java", fileContent);
//
//        }
//    }

//    public static void main(String[] args) throws FileNotFoundException {
//        List<MonsterCardDetails> monsters = importMonstersDetails();
//        for (MonsterCardDetails monsterCardsDetails: monsters) {
//            String className = monsterCardsDetails.getName().trim()
//                    .replaceAll(" ","").replaceAll("-", "")
//                    .replaceAll(",","").replaceAll("'", "");
//            System.out.println("case \"" + monsterCardsDetails.getName() + "\":\nreturn new " + className + "();\n");
//        }
//    }

//        public static void main(String[] args) throws FileNotFoundException {
//        List<TrapAndSpellCardDetails> traps = importTrapAndSpellsDetails();
//        for (TrapAndSpellCardDetails trapAndSpellCardDetails: traps) {
//            String className = trapAndSpellCardDetails.getName().trim()
//                    .replaceAll(" ","").replaceAll("-", "")
//                    .replaceAll(",","").replaceAll("'", "");
//            System.out.println("case \"" + trapAndSpellCardDetails.getName() + "\":\nreturn new " + className + "();\n");
//        }
//    }


//    public static void main(String[] args) throws IOException {
//        List<MonsterCardDetails> monsters = importMonstersDetails();
//        for (MonsterCardDetails monsterCardsDetails: monsters){
//            String className = monsterCardsDetails.getName().trim()
//                    .replaceAll(" ","").replaceAll("-", "")
//                    .replaceAll(",","").replaceAll("'", "");
//            String fileContent = "package model.cards.monsters;\n" +
//                    "\n" +
//                    "import model.cards.Monster;\n" +
//                    "import model.cards.cardfeaturesenums.CardType;\n" +
//                    "import model.cards.cardfeaturesenums.MonsterAttribute;\n" +
//                    "import model.cards.cardfeaturesenums.MonsterType;\n" +
//                    "\n" +
//                    "public class " + className + " extends Monster {\n" +
//                    "\n" +
//                    "    public " + className + "() {\n" +
//                    "        super(\"" + monsterCardsDetails.getName() + "\", \"" + monsterCardsDetails.getDescription() + "\"\n" +
//                    "                , " + monsterCardsDetails.getPrice() + ", " + monsterCardsDetails.getAtk() + ", " + monsterCardsDetails.getDef() + ", " + monsterCardsDetails.getLevel() + ", MonsterAttribute." + monsterCardsDetails.getAttribute().toUpperCase() + ", MonsterType." + monsterCardsDetails.getMonsterType().toUpperCase() + ", CardType." + monsterCardsDetails.getCardType().toUpperCase() + ");\n" +
//                    "    }\n" +
//                    "\n" +
//                    "}\n";
//            writeFile("src\\main\\java\\model\\cards\\monsters\\" + className + ".java", fileContent);
//
//        }
//    }

//    public static void main(String[] args) throws IOException {
//        List<TrapAndSpellCardDetails> trapAndSpellCardDetailsList = DataBaseController.getInstance().importTrapAndSpellsDetails();
//        for (TrapAndSpellCardDetails trapAndSpellCardDetails : trapAndSpellCardDetailsList) {
//            String className = trapAndSpellCardDetails.getName().trim()
//                    .replaceAll(" ", "").replaceAll("-", "")
//                    .replaceAll(",", "").replaceAll("'", "");
//            String fileContent = "package model.cards.trapandspells;\n" +
//                    "\n" +
//                    "import model.cards.SpellAndTrap;\n" +
//                    "import model.cards.cardfeaturesenums.SpellOrTrap;\n" +
//                    "import model.cards.cardfeaturesenums.SpellOrTrapAttribute;\n" +
//                    "import model.cards.cardfeaturesenums.Status;\n" +
//                    "\n" +
//                    "public class " + className + " extends SpellAndTrap {\n" +
//                    "    \n" +
//                    "    public " + className + "() {\n" +
//                    "        super(\"" + trapAndSpellCardDetails.getName() + "\", \"" + trapAndSpellCardDetails.getDescription() + "\",\n" +
//                    "                " + trapAndSpellCardDetails.getPrice() + ", false, SpellOrTrap." + trapAndSpellCardDetails.getType().toUpperCase() +
//                    ", SpellOrTrapAttribute." + trapAndSpellCardDetails.getIconOrProperty().toUpperCase() + ", Status." + trapAndSpellCardDetails.getStatus().toUpperCase() +
//                    ");\n" +
//                    "    }\n" +
//                    "    \n" +
//                    "}";
//            writeFile("src\\main\\java\\model\\cards\\trapandspells\\" + className + ".java", fileContent);
//
//        }
//    }
//
//}
