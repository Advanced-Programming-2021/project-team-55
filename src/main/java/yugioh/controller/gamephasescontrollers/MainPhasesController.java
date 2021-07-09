package yugioh.controller.gamephasescontrollers;

import javafx.scene.image.ImageView;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.model.Player;
import yugioh.model.board.CardStatus;
import yugioh.model.board.Cell;
import yugioh.model.board.GameBoard;
import yugioh.model.cards.Card;
import yugioh.model.cards.Monster;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.model.cards.cardfeaturesenums.SummonTypes;
import yugioh.model.cards.monsters.GateGuardian;
import yugioh.model.cards.monsters.ManEaterBug;
import yugioh.model.cards.monsters.TerratigertheEmpoweredWarrior;
import yugioh.model.cards.monsters.TheTricky;
import yugioh.model.cards.trapandspells.TimeSeal;
import yugioh.model.cards.trapandspells.TorrentialTribute;
import yugioh.model.exceptions.GameException;
import yugioh.view.ConsoleColors;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.GameResponses;

import java.util.ArrayList;

public interface MainPhasesController {

    ArrayList<String> summonEffectSpellAndTrap = new ArrayList<>();
    ArrayList<String> flipSummonEffectSpellAndTrap = new ArrayList<>();
    ArrayList<String> SpecialSummonEffectSpellAndTrap = new ArrayList<>();
    ArrayList<String> ritualSummonEffectSpellAndTrap = new ArrayList<>();

    default void monsterSummon(GameController gameController) throws GameException {

        Player currentPlayer = gameController.currentTurnPlayer;
        Cell selectedCell = Cell.getSelectedCell();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        } else if (!isSummonable(selectedCell, gameController)) {
            throw new GameException(GameResponses.CANT_SUMMON_CARD.response);
        } else if (gameController.doPlayerSetOrSummonedThisTurn()) {
            throw new GameException(GameResponses.ALREADY_SUMMONED_SET_IN_THIS_TURN.response);
        }
        int monsterLevel = ((Monster) selectedCell.getCellCard()).getLevel();
        gameController.setLastSummonedMonster(selectedCell);
        if (TheTricky.handleEffect(gameController, selectedCell)) return;
        if (GateGuardian.handleEffect(gameController)) return;
        handleTribute(currentPlayer, gameController, monsterLevel, false, false);
    }

    default void continueMonsterSummon(GameController gameController, boolean isNormalSummon) {
//        double length = 4;
//        if (isNormalSummon) length = 0.1;
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(length), event -> {
        // Cell.setSelectedCell(CardActionsMenu.getToBeSummonedCell());
        Cell selectedCell = Cell.getSelectedCell();
        Player currentPlayer = gameController.currentTurnPlayer;
        TerratigertheEmpoweredWarrior.handleEffect(gameController, selectedCell);
        gameController.setDidPlayerSetOrSummonThisTurn(true);
        addMonstersToSummonEffectSpellAndTrap(selectedCell);
        activateTrapIfCanBeActivated(gameController, SummonTypes.NormalSummon);
        try {
            currentPlayer.getGameBoard().addCardToMonsterCardZone(selectedCell.getCellCard(), CardStatus.OFFENSIVE_OCCUPIED,
                    gameController);
        } catch (GameException e) {
            e.printStackTrace();
        }
        currentPlayer.getGameBoard().removeCardFromHand(selectedCell);
        Cell.deselectCell();
        //}));
        //timeline.play();
    }

    default boolean isSummonedMonsterATKMoreThan1000(Cell summonedCell) {//todo check null pointer exception
        return ((Monster) summonedCell.getCellCard()).getAtk() >= 1000;
    }

    private void activateTrapIfCanBeActivated(GameController gameController, SummonTypes summonType) {
        for (Cell cell : gameController.currentTurnPlayer.getGameBoard().getSpellAndTrapCardZone()) {
            if (!cell.isEmpty() && cell.getCardStatus() == CardStatus.HIDDEN) {
                if (summonType == SummonTypes.NormalSummon) {
                    if(summonEffectSpellAndTrap.contains(cell.getCellCard())) {
                        gameController.activateTrapEffect(summonEffectSpellAndTrap);
                        break;
                    }
                } else if (summonType == SummonTypes.FlipSummon) {
                    if(flipSummonEffectSpellAndTrap.contains(cell.getCellCard())) {
                        gameController.activateTrapEffect(flipSummonEffectSpellAndTrap);
                        break;
                    }
                } else if (summonType == SummonTypes.SpecialSummon) {
                    if(SpecialSummonEffectSpellAndTrap.contains(cell.getCellCard())) {
                        gameController.activateTrapEffect(SpecialSummonEffectSpellAndTrap);
                        break;
                    }
                } else if (summonType == SummonTypes.RitualSummon) {
                    if(ritualSummonEffectSpellAndTrap.contains(cell.getCellCard())) {
                        gameController.activateTrapEffect(ritualSummonEffectSpellAndTrap);
                        break;
                    }
                }
            }
        }
        for (Cell cell : gameController.currentTurnOpponentPlayer.getGameBoard().getSpellAndTrapCardZone()) {
            if (!cell.isEmpty() && cell.getCardStatus() == CardStatus.HIDDEN) {
                if (summonType == SummonTypes.NormalSummon) {
                    if(summonEffectSpellAndTrap.contains(cell.getCellCard())) {
                        gameController.changeTurn(true, false);
                        gameController.activateTrapEffect(summonEffectSpellAndTrap);
                        gameController.changeTurn(true, true);
                        break;
                    }
                } else if (summonType == SummonTypes.FlipSummon) {
                    if(flipSummonEffectSpellAndTrap.contains(cell.getCellCard())) {
                        gameController.changeTurn(true, false);
                        gameController.activateTrapEffect(flipSummonEffectSpellAndTrap);
                        gameController.changeTurn(true, true);
                        break;
                    }
                } else if (summonType == SummonTypes.SpecialSummon) {
                    if(SpecialSummonEffectSpellAndTrap.contains(cell.getCellCard())) {
                        gameController.changeTurn(true, false);
                        gameController.activateTrapEffect(SpecialSummonEffectSpellAndTrap);
                        gameController.changeTurn(true, true);
                        break;
                    }
                } else if (summonType == SummonTypes.RitualSummon) {
                    if(ritualSummonEffectSpellAndTrap.contains(cell.getCellCard())) {
                        gameController.changeTurn(true, false);
                        gameController.activateTrapEffect(ritualSummonEffectSpellAndTrap);
                        gameController.changeTurn(true, true);
                        break;
                    }
                }
            }
        }
    }

    private void addMonstersToSummonEffectSpellAndTrap(Cell summonedCell) {
        summonEffectSpellAndTrap.add(new TorrentialTribute().getName());
        if (isSummonedMonsterATKMoreThan1000(summonedCell))
            flipSummonEffectSpellAndTrap.add(new TorrentialTribute().getName());
        //todo add the rest of summon monsters thing
    }

    private void addMonstersToFlipSummonEffectSpellAndTrap(Cell summonedCell) {
        if (isSummonedMonsterATKMoreThan1000(summonedCell))
            flipSummonEffectSpellAndTrap.add(new TorrentialTribute().getName());
        //todo add the rest of summon monsters thing
    }

    private void addMonstersToSpecialSummonEffectSpellAndTrap() {
        SpecialSummonEffectSpellAndTrap.add(new TorrentialTribute().getName());
        //todo add the rest of summon monsters thing
    }

    default boolean hasEnoughTribute(Card card, Player currentPlayer, boolean isSpecialSummon) {

        int monsterLevel = ((Monster) card).getLevel();
        if (monsterLevel > 4 || isSpecialSummon) {
            if (isSpecialSummon) {
                if (currentPlayer.getGameBoard().getNumberOfMonstersOnMonsterCardZone() < 1)
                    return false;
            } else {
                if (monsterLevel < 7) {
                    if (currentPlayer.getGameBoard().getNumberOfMonstersOnMonsterCardZone() < 1)
                        return false;
                } else {
                    if (currentPlayer.getGameBoard().getNumberOfMonstersOnMonsterCardZone() < 2)
                        return false;
                }
            }
        }
        return true;
    }

    private void handleTribute(Player currentPlayer, GameController gameController, int monsterLevel, boolean isSpecialSummon, boolean isForSet) throws GameException {
        GameBoard playerGameBoard = currentPlayer.getGameBoard();
        if (monsterLevel > 4 || isSpecialSummon) {
            int numberOfTributes;
            if (isSpecialSummon) {
                if (currentPlayer.getGameBoard().getNumberOfMonstersOnMonsterCardZone() < 1)
                    throw new GameException(GameResponses.NOT_ENOUGH_CARDS_FOR_TRIBUTE.response);
                numberOfTributes = 1;
            } else {
                if (monsterLevel < 7) {
                    if (currentPlayer.getGameBoard().getNumberOfMonstersOnMonsterCardZone() < 1)
                        throw new GameException(GameResponses.NOT_ENOUGH_CARDS_FOR_TRIBUTE.response);
                    numberOfTributes = 1;
                } else {
                    if (currentPlayer.getGameBoard().getNumberOfMonstersOnMonsterCardZone() < 2)
                        throw new GameException(GameResponses.NOT_ENOUGH_CARDS_FOR_TRIBUTE.response);
                    numberOfTributes = 2;
                }
            }
            currentPlayer.getGameBoard().handleTribute(gameController, numberOfTributes, !isForSet);


           /* ArrayList<Cell> tributes = new ArrayList<>();
            Cell newSelectedCell;
            for (int i = 0; i < numberOfTributes; i++) {
                while (true) {
                    ViewInterface.showResult("select cell to tribute:");
                    String input = ViewInterface.getInput();
                    String result = Duel.processSelect(input);
                    if (!result.equals("card selected")) {
                        ViewInterface.showResult("Error: try again!");
                        continue;
                    }
                    newSelectedCell = Cell.getSelectedCell();
                    if (!playerGameBoard.isCellInMonsterZone(newSelectedCell)) {
                        ViewInterface.showResult(GameResponses.NO_MONSTER_ON_CELL.response);
                        continue;
                    }
                    tributes.add(newSelectedCell);
                    ViewInterface.showResult("cell taken");
                    break;
                }
            }
            Cell.setSelectedCell(selectedCell);
            for (Cell tribute : tributes) {
                tribute.removeCardFromCell(playerGameBoard);
            }*/

//            for (int i=0;i<numberOfTributes;i++){
//                ViewInterface.showResult("select cell to tribute:");
//                Duel.processSelect(ViewInterface.getInput());
//                newSelectedCell = Cell.getSelectedCell();
//                if (!currentPlayer.getGameBoard().isCellInMonsterZone(newSelectedCell))
//                    ViewInterface.showResult(GameResponses.NO_MONSTER_ON_CELL.response);
//                tributes.add(newSelectedCell);
//                ViewInterface.showResult("cell taken");
//            }
//            for (Cell tribute : tributes) {
//                tribute.removeCardFromCell(currentPlayer.getGameBoard());
//            }
//
//        }
        } else if (isForSet) {
            continueSetMonster(gameController);
        } else {
            continueMonsterSummon(gameController, true);
        }
    }

    default void setCard(GameController gameController) throws GameException {//todo, the method can insert 6 spells
        Cell selectedCell = Cell.getSelectedCell();
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        }
        Card selectedCard = selectedCell.getCellCard();
        if (selectedCard == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        } else if (!playerGameBoard.getHandCards().contains(selectedCell)) {
            throw new GameException(GameResponses.CANT_SET_CARD.response);
        } else {
            if (selectedCard.isMonster()) {
                if (gameController.doPlayerSetOrSummonedThisTurn()) {
                    throw new GameException(GameResponses.ALREADY_SUMMONED_SET_IN_THIS_TURN.response);
                } else if (!hasEnoughTribute(selectedCard, gameController.currentTurnPlayer, false)) {
                    throw new GameException(GameResponses.NOT_ENOUGH_CARDS_FOR_TRIBUTE.response);
                }
                handleTribute(gameController.currentTurnPlayer, gameController, ((Monster) selectedCard).getLevel(), false, true);
//                playerGameBoard.addCardToMonsterCardZone(selectedCard, CardStatus.DEFENSIVE_HIDDEN, gameController);
//                playerGameBoard.removeCardFromHand(selectedCell);
//                gameController.changedPositionCells.add(selectedCell);
//                gameController.setDidPlayerSetOrSummonThisTurn(true);
            } else {
                playerGameBoard.addCardToSpellAndTrapCardZone(selectedCard, CardStatus.HIDDEN, gameController);
                playerGameBoard.removeCardFromHand(selectedCell);
                gameController.changedPositionCells.add(selectedCell);
                TimeSeal.setActivated(gameController);
                Cell.deselectCell();
            }

        }
    }

    default void continueSetMonster(GameController gameController) {
        GameBoard playerGameBoard = gameController.currentTurnPlayer.getGameBoard();
        Cell selectedCell = Cell.getSelectedCell();
        Card selectedCard = selectedCell.getCellCard();
        try {
            playerGameBoard.addCardToMonsterCardZone(selectedCard, CardStatus.DEFENSIVE_HIDDEN, gameController);
        } catch (GameException e) {
            e.printStackTrace();
        }
        playerGameBoard.removeCardFromHand(selectedCell);
        gameController.changedPositionCells.add(selectedCell);
        gameController.setDidPlayerSetOrSummonThisTurn(true);
        Cell.deselectCell();
    }

    default void setPosition(String position, GameController gameController) throws GameException {
        Cell cell = Cell.getSelectedCell();
        GameBoard playerGameBoard = gameController.currentTurnPlayer.getGameBoard();
        if (cell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        } else if (!playerGameBoard.isCellInMonsterZone(cell)) {
            throw new GameException(GameResponses.CANT_CHANGE_CARD_POSITION.response);
        } else if (position.equals("attack") && cell.getCardStatus() != CardStatus.DEFENSIVE_OCCUPIED ||
                position.equals("defense") && cell.getCardStatus() != CardStatus.OFFENSIVE_OCCUPIED) {
            throw new GameException(GameResponses.CARD_IS_ALREADY_IN_WANTED_POSITION.response);
        } else if (gameController.changedPositionCells.contains(cell)) {
            throw new GameException(GameResponses.ALREADY_CHANGED_CARD_POSITION_IN_THIS_TURN.response);
        } else {
            gameController.changedPositionCells.add(cell);
            if (position.equals("attack")) {
                gameController.currentTurnPlayer.getGameBoard().setFlipZTransition(cell.getCellRectangle(), false);
                cell.setCardStatus(CardStatus.OFFENSIVE_OCCUPIED);
            } else {
                gameController.currentTurnPlayer.getGameBoard().setFlipZTransition(cell.getCellRectangle(), true);
                cell.setCardStatus(CardStatus.DEFENSIVE_OCCUPIED);
            }
            Cell.deselectCell();
        }
    }

    default boolean canSpecialSummon(GameController gameController) {
        GameBoard playerGameBoard = gameController.currentTurnPlayer.getGameBoard();
        for (Cell cell : playerGameBoard.getMonsterCardZone()) {
            if (!cell.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    default void activateSpell(GameController gameController) throws GameException {
        Cell selectedCell = Cell.getSelectedCell();
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        } else {
            Card card = selectedCell.getCellCard();
            if (card == null) return;
            if (card.isMonster()) {
                throw new GameException(GameResponses.ACTIVATION_ONLY_FOR_SPELL.response);
            } else {
                SpellAndTrap spell = (SpellAndTrap) card;
                if (selectedCell.getCardStatus() == CardStatus.OCCUPIED) {
                    throw new GameException(GameResponses.ALREADY_ACTIVATED.response);
                } else if (gameController.changedPositionCells.contains(selectedCell)) {
                    throw new GameException(GameResponses.SPELL_CANT_BE_ACTIVATED_THIS_TURN.response);
                } else if (playerGameBoard.isSpellAndTrapCardZoneFull() && spell.getAttribute() != SpellOrTrapAttribute.FIELD) {
                    throw new GameException(GameResponses.SPELL_ZONE_IS_FULL.response);
                } else {
                    //todo activate spell
                    if (Cell.getSelectedCell().isEmpty() || Cell.getSelectedCell() == null) {
                        System.out.println("error in activate effect");
                    }
                    SpellAndTrap.activateSpellEffects(gameController, spell.getName());
//                        if(!playerGameBoard.isCellInSpellAndTrapZone(selectedCell)) {
//                            playerGameBoard.getHandCards().remove(selectedCell);
//                            if (spell.getAttribute() == SpellOrTrapAttribute.FIELD) {
//                                playerGameBoard.addCardToFieldZone(card);
//                                gameController.currentTurnOpponentPlayer.getGameBoard().addCardToFieldZone(card);
//                            } else {
//                                playerGameBoard.addCardToSpellAndTrapCardZone(card, CardStatus.OCCUPIED,gameController);
//                            }
//                        }
//                        else{
//                            selectedCell.setCardStatus(CardStatus.OCCUPIED);
//                        }
//                        Cell.deselectCell();
                }
            }
        }
    }

    default void flipSummon(GameController gameController) throws GameException {
        Player currentPlayer = gameController.currentTurnPlayer;
        Cell selectedCell = Cell.getSelectedCell();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        }
        if (!currentPlayer.getGameBoard().isCellInMonsterZone(selectedCell)) {
            throw new GameException(GameResponses.CAN_NOT_CHANGE_CARD_POSITION.response);
        }
        //todo  ببینین ارور دوم رو درست هندل کردم: در همین دور تازه روی زمین گذاشته شده باشد
        if (selectedCell.getCardStatus() != CardStatus.DEFENSIVE_HIDDEN || gameController.getChangedPositionCells().contains(selectedCell)) {
            throw new GameException(GameResponses.CAN_NOT_FLIP_SUMMON.response);
        }
        gameController.setLastSummonedMonster(selectedCell);
        addMonstersToFlipSummonEffectSpellAndTrap(selectedCell);
        if (selectedCell.getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {
            gameController.currentTurnPlayer.getGameBoard().setFlipZTransition(selectedCell.getCellRectangle(), false);
        }
        selectedCell.setCardStatus(CardStatus.OFFENSIVE_OCCUPIED);
        gameController.currentTurnPlayer.getGameBoard().setFlipTransition(selectedCell.getCellCard(),
                selectedCell.getCellRectangle(), false);
        ManEaterBug.handleEffect(gameController, selectedCell);
        activateTrapIfCanBeActivated(gameController, SummonTypes.FlipSummon);
        Cell.deselectCell();
    }

    default void specialSummon(GameController gameController) throws GameException {
        addMonstersToSpecialSummonEffectSpellAndTrap();
        Player currentPlayer = gameController.currentTurnPlayer;
        Cell selectedCell = Cell.getSelectedCell();
        while (true) {
//            if (selectedCell == null) {
//                throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
//            } else if (!isSummonable(selectedCell.getCellCard())) {
//                throw new GameException(GameResponses.CANT_SUMMON_CARD.response);
//            }
            String input = ViewInterface.getInput();
            if (!input.equals("summon")) {
                ViewInterface.showResult(GameResponses.YOU_SHOULD_SPECIAL_SUMMON_NOW.response);
                continue;
            }
            int monsterLevel = ((Monster) selectedCell.getCellCard()).getLevel();
            handleTribute(currentPlayer, gameController, monsterLevel, true, false);

            currentPlayer.getGameBoard().addCardToMonsterCardZone(selectedCell.getCellCard(),
                    CardStatus.OFFENSIVE_OCCUPIED, gameController);
            currentPlayer.getGameBoard().removeCardFromHand(selectedCell);
            TerratigertheEmpoweredWarrior.handleEffect(gameController, selectedCell);
            gameController.setDidPlayerSetOrSummonThisTurn(true);
            gameController.shouldSpecialSummonNow = false;
            gameController.setLastSummonedMonster(selectedCell);
            addMonstersToSpecialSummonEffectSpellAndTrap();
            Cell.deselectCell();
            break;
        }
        activateTrapIfCanBeActivated(gameController, SummonTypes.SpecialSummon);
    }

    default boolean isSummonable(Cell cell, GameController gameController) {
        if (cell.isEmpty()) return false;
        Card card = cell.getCellCard();
        if (card.isMonster() && gameController.currentTurnPlayer.getGameBoard().isCellInHandZone(cell)) {
            return ((Monster) card).getCardType() != CardType.RITUAL;
        }
        return false;
    }

    default String showGameBoard(Player currentPlayer, Player opponentPlayer) {
        GameMenuController gameMenuController = GameMenuController.getGameMenuController();
        StringBuilder response = new StringBuilder(ConsoleColors.BLUE + "\t\t" + opponentPlayer.getUser().getNickname() + ":" + opponentPlayer.getLP() + "\n");
        GameBoard playerGameBoard = currentPlayer.getGameBoard();
        GameBoard opponentPlayerGameBoard = opponentPlayer.getGameBoard();
        response.append("\t".repeat(Math.max(0, 6 - opponentPlayerGameBoard.getHandCards().size())));

        for (int i = 0; i < opponentPlayerGameBoard.getHandCards().size(); i++) {
            //Cell cell=opponentPlayerGameBoard.getHandCards().get(i);
            //ImageView imageView = cell.getCellCard().getCardBackImage();
            //imageView.setFitWidth(70);
            //imageView.rotateProperty().setValue(180);
            //ImagePattern imagePattern=cell.getCellCard().getCardBackImagePattern();
            //javafx.scene.shape.Rectangle rectangleImage= new javafx.scene.shape.Rectangle();
            //rectangleImage.setWidth(90);
            //rectangleImage.setHeight(115);

            //rectangleImage.setFill(imagePattern);
            //gameMenuController.rivalHandCardsContainer.getChildren().add(rectangleImage);
            //gameMenuController.addEventForCardImageRectangle(rectangleImage, null);
            response.append("\tc");
        }

        double paneX = gameMenuController.rivalDeckZoneContainer.getLayoutX();
        for (int i = opponentPlayerGameBoard.getDeckZone().size() - 1; i >= 0; i--) {
            ImageView imageView = opponentPlayerGameBoard.getDeckZone().get(i).getCellCard().getCardBackImage();
            imageView.setFitWidth(70);
            imageView.rotateProperty().set(180.0);
            imageView.setLayoutX(paneX - i / 2);
            if(!gameMenuController.rivalDeckZoneContainer.getChildren().contains(imageView))
            gameMenuController.rivalDeckZoneContainer.getChildren().add(imageView);
//           gameMenuController.addEventForCardImageRectangle(imageView, null);
            //todo: i tried to make the deck zone Rectangles but i couldnt
//            javafx.scene.shape.Rectangle rectangleImage=new javafx.scene.shape.Rectangle();
//            rectangleImage.setLayoutX(paneX - i / 2);
//            rectangleImage.setWidth(70);
//            ImagePattern imagePattern = opponentPlayerGameBoard.getDeckZone().get(i).getCellCard().getCardBackImagePattern();
//            rectangleImage.setFill(imagePattern);
//            rectangleImage.rotateProperty().set(180.0);
//            gameMenuController.rivalDeckZoneContainer.getChildren().add(rectangleImage);
//            gameMenuController.addEventForCardImageRectangle(rectangleImage, null);
        }

        response.append("\n").append(opponentPlayerGameBoard.getDeckZone().size()).append("\n");
        response.append("\t4\t2\t1\t3\t5\n");
        int[] opponentCellNumbering = {3, 1, 0, 2, 4};

        for (int i = 0; i < 5; i++) {
            if (opponentPlayerGameBoard.getSpellAndTrapCardZone()[opponentCellNumbering[i]]
                    .getCellCard() == null) {
                response.append("\tE");
            } else {
                switch (opponentPlayerGameBoard.getSpellAndTrapCardZone()[opponentCellNumbering[i]]
                        .getCardStatus()) {
                    case HIDDEN: {
                        response.append("\tH");
                        break;
                    }
                    case OCCUPIED: {
                        response.append("\tO");
                        break;
                    }

                }
            }
        }

        response.append("\n");
        for (int i = 0; i < 5; i++) {
            if (opponentPlayerGameBoard.getMonsterCardZone()[opponentCellNumbering[i]]
                    .getCellCard() == null) {
                response.append("\tE");
            } else {
                switch (opponentPlayerGameBoard.getMonsterCardZone()[opponentCellNumbering[i]]
                        .getCardStatus()) {
                    case DEFENSIVE_HIDDEN: {
                        response.append("\tDH");
                        break;
                    }
                    case DEFENSIVE_OCCUPIED: {
                        response.append("\tDO");
                        break;
                    }
                    case OFFENSIVE_OCCUPIED: {
                        response.append("\tOO");
                    }
                }
            }
        }
        response.append("\n").append(opponentPlayerGameBoard.getGraveyard().size()).append("\t\t\t\t\t\t");
        if (opponentPlayerGameBoard.getFieldZone().isEmpty()) {
            response.append("E");
        } else {
            response.append("O");
        }
        response.append("\n\n--------------------------\n\n");
        if (playerGameBoard.getFieldZone().isEmpty()) {
            response.append("E");
        } else {
            response.append("O");
        }
        response.append("\t\t\t\t\t\t").append(playerGameBoard.getGraveyard().size()).append("\n");
        int[] playerCellNumbering = {4, 2, 0, 1, 3};
        for (int i = 0; i < 5; i++) {
            if (playerGameBoard.getMonsterCardZone()[playerCellNumbering[i]]
                    .getCellCard() == null) {
                response.append("\tE");
            } else {
                switch (playerGameBoard.getMonsterCardZone()[playerCellNumbering[i]]
                        .getCardStatus()) {
                    case DEFENSIVE_HIDDEN: {
                        response.append("\tDH");
                        break;
                    }
                    case DEFENSIVE_OCCUPIED: {
                        response.append("\tDO");
                        break;
                    }
                    case OFFENSIVE_OCCUPIED: {
                        response.append("\tOO");
                    }
                }
            }
        }
        response.append("\n");

        for (int i = 0; i < 5; i++) {
            if (playerGameBoard.getSpellAndTrapCardZone()[playerCellNumbering[i]]
                    .getCellCard() == null) {
                response.append("\tE");
            } else {
                switch (playerGameBoard.getSpellAndTrapCardZone()[playerCellNumbering[i]]
                        .getCardStatus()) {
                    case HIDDEN: {
                        response.append("\tH");
                        break;
                    }
                    case OCCUPIED: {
                        response.append("\tO");
                        break;
                    }

                }
            }
        }
        response.append("\n\t5\t3\t1\t2\t4");
        response.append("\n\t\t\t\t\t\t").append(playerGameBoard.getDeckZone().size()).append("\n");
        double xPane = gameMenuController.userDeckZoneContainer.getLayoutX();

        for (int i = playerGameBoard.getDeckZone().size() - 1; i >= 0; i--) {
            //todo: i tried to make the deck zone Rectangles but i couldnt
//            Rectangle rectangleImage=new Rectangle();
//            //rectangleImage.setLayoutX(xPane + i / 2);
//            //rectangleImage.setWidth(70);
//            ImagePattern imagePattern = playerGameBoard.getDeckZone().get(i).getCellCard().getCardBackImagePattern();
//            rectangleImage.setFill(imagePattern);
//            gameMenuController.userDeckZoneContainer2.getChildren().add(rectangleImage);
//            gameMenuController.addEventForCardImageRectangle(rectangleImage, null);
            ImageView imageView = playerGameBoard.getDeckZone().get(i).getCellCard().getCardBackImage();
            imageView.setFitWidth(70);
            imageView.setLayoutX(xPane + i / 2);
            if(!gameMenuController.userDeckZoneContainer.getChildren().contains(imageView))
            gameMenuController.userDeckZoneContainer.getChildren().add(imageView);
            //gameMenuController.addEventForCardImage(imageView,null);
        }

        for (int i = 0; i < playerGameBoard.getHandCards().size(); i++) {
            Cell cell = playerGameBoard.getHandCards().get(i);
//            ImageView imageView = Card.getCardImage(opponentPlayerGameBoard.getHandCards().get(i).getCellCard(), 80);
//            imageView.setX(ElementsCoordinates.getUserHandCards().get(i).getX());
//            imageView.setY(ElementsCoordinates.getUserHandCards().get(i).getY());
//            GameMenuController.getGameMenuController().gameBoardPane.getChildren().add(imageView);
            //Card card = playerGameBoard.getHandCards().get(i).getCellCard();
           /* ImagePattern imagePattern=cell.getCellCard().getCardImagePattern();
           Rectangle rectangleImage= new Rectangle();

            cell.setCellRectangle(rectangleImage);
            rectangleImage.setWidth(90);
            rectangleImage.setHeight(120);
            rectangleImage.setFill(imagePattern);*/
            //ImageView imageView = cell.getCellCard().getCardImageForDeck(90);
            // gameMenuController.userHandCardsContainer.getChildren().add(imageView);
          /*  gameMenuController.userHandCardsContainer.getChildren().add(rectangleImage);
           gameMenuController.addEventForCardImageRectangle(rectangleImage, cell.getCellCard());*/
            response.append("c\t");
        }
        response.append("\n\t\t").append(currentPlayer.getUser().getNickname()).append(":").append(currentPlayer.getLP()).append(ConsoleColors.RESET);
        return response.toString();
    }

}
