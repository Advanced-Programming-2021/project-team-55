package controller.gamephasescontrollers;

import model.Player;
import model.board.CardStatus;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.Card;
import model.cards.Monster;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.SummonTypes;
import model.cards.monsters.GateGuardian;
import model.cards.monsters.ManEaterBug;
import model.cards.monsters.TerratigertheEmpoweredWarrior;
import model.cards.monsters.TheTricky;
import model.cards.trapandspells.TimeSeal;
import model.cards.trapandspells.TorrentialTribute;
import model.cards.trapandspells.TrapHole;
import model.exceptions.GameException;
import view.ConsoleColors;
import view.ViewInterface;
import view.gamephases.Duel;
import view.gamephases.GameResponses;

import java.util.ArrayList;

public interface MainPhasesController {
    ArrayList<SpellAndTrap> summonEffectSpellAndTrap = new ArrayList<>();
    ArrayList<SpellAndTrap> flipSummonEffectSpellAndTrap = new ArrayList<>();
    ArrayList<SpellAndTrap> SpecialSummonEffectSpellAndTrap = new ArrayList<>();
    ArrayList<SpellAndTrap> ritualSummonEffectSpellAndTrap = new ArrayList<>();

    static void addMonstersToRitualSummonEffectSpellAndTrap() {
        ritualSummonEffectSpellAndTrap.add(new TorrentialTribute());
        //todo add the rest of summon monsters thing
    }

    default void monsterInsert(Cell cell) {

    }

    default void monsterSet(Cell cell) {

    }

    default void monsterSummon(GameController gameController) throws GameException {
        addMonstersToSummonEffectSpellAndTrap();
        Player currentPlayer = gameController.currentTurnPlayer;
        Cell selectedCell = Cell.getSelectedCell();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        } else if (!isSummonable(selectedCell.getCellCard())) {
            throw new GameException(GameResponses.CANT_SUMMON_CARD.response);
        } else if (gameController.DoPlayerSetOrSummonedThisTurn()) {
            throw new GameException(GameResponses.ALREADY_SUMMONED_SET_IN_THIS_TURN.response);
        }
        int monsterLevel = ((Monster) selectedCell.getCellCard()).getLevel();
        if (TheTricky.handleEffect(gameController, selectedCell)) return;
        if (GateGuardian.handleEffect(gameController)) return;
        selectedCell = handleTributeForNormalSummon(currentPlayer, selectedCell, monsterLevel, false);
        currentPlayer.getGameBoard().addCardToMonsterCardZone(selectedCell.getCellCard(), CardStatus.OFFENSIVE_OCCUPIED, gameController);
        currentPlayer.getGameBoard().getHandCards().remove(selectedCell);
        TerratigertheEmpoweredWarrior.handleEffect(gameController, selectedCell);
        gameController.setDidPlayerSetOrSummonThisTurn(true);
        gameController.setLastSummonedMonster(selectedCell);
        Cell.deselectCell();
        activateTrapIfCanBeActivated(gameController, SummonTypes.NormalSummon);
    }

    private void activateTrapIfCanBeActivated(GameController gameController, SummonTypes summonType) {
        for (Cell cell : gameController.currentTurnPlayer.getGameBoard().getSpellAndTrapCardZone()) {
            if (!cell.isEmpty() && cell.getCardStatus() == CardStatus.HIDDEN) {
                if (summonType == SummonTypes.NormalSummon) {
                    gameController.activateTrapEffect(summonEffectSpellAndTrap);
                    break;
                } else if (summonType == SummonTypes.FlipSummon) {
                    gameController.activateTrapEffect(flipSummonEffectSpellAndTrap);
                    break;
                } else if (summonType == SummonTypes.SpecialSummon) {
                    gameController.activateTrapEffect(SpecialSummonEffectSpellAndTrap);
                    break;
                } else if (summonType == SummonTypes.RitualSummon) {
                    gameController.activateTrapEffect(ritualSummonEffectSpellAndTrap);
                    break;
                }
            }
        }
        for (Cell cell : gameController.currentTurnOpponentPlayer.getGameBoard().getSpellAndTrapCardZone()) {
            if (!cell.isEmpty() && cell.getCardStatus() == CardStatus.HIDDEN) {
                Card card = cell.getCellCard();
                if (summonType == SummonTypes.NormalSummon) {
                    gameController.changeTurn(true, false);
                    gameController.activateTrapEffect(summonEffectSpellAndTrap);
                    gameController.changeTurn(true, true);
                    break;
                } else if (summonType == SummonTypes.FlipSummon) {
                    gameController.changeTurn(true, false);
                    gameController.activateTrapEffect(flipSummonEffectSpellAndTrap);
                    gameController.changeTurn(true, true);
                    break;
                } else if (summonType == SummonTypes.SpecialSummon) {
                    gameController.changeTurn(true, false);
                    gameController.activateTrapEffect(SpecialSummonEffectSpellAndTrap);
                    gameController.changeTurn(true, true);
                    break;
                } else if (summonType == SummonTypes.RitualSummon) {
                    gameController.changeTurn(true, false);
                    gameController.activateTrapEffect(ritualSummonEffectSpellAndTrap);
                    gameController.changeTurn(true, true);
                    break;
                }
            }
        }
    }

    private void addMonstersToSummonEffectSpellAndTrap() {
        summonEffectSpellAndTrap.add(new TorrentialTribute());
        if (TrapHole.isSummonedMonsterATKMoreThan1000(Duel.getGameController()))
            flipSummonEffectSpellAndTrap.add(new TorrentialTribute());
        //todo add the rest of summon monsters thing
    }

    private void addMonstersToFlipSummonEffectSpellAndTrap() {
        if (TrapHole.isSummonedMonsterATKMoreThan1000(Duel.getGameController()))
            flipSummonEffectSpellAndTrap.add(new TorrentialTribute());
        //todo add the rest of summon monsters thing
    }

    private void addMonstersToSpecialSummonEffectSpellAndTrap() {
        SpecialSummonEffectSpellAndTrap.add(new TorrentialTribute());
        //todo add the rest of summon monsters thing
    }

    private Cell handleTributeForNormalSummon(Player currentPlayer, Cell selectedCell, int monsterLevel, boolean isSpecialSummon) throws GameException {
        if (monsterLevel > 4 || isSpecialSummon) {
            int numberOfTributes;
            if (isSpecialSummon) {
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
            ArrayList<Cell> tributes = new ArrayList<>();
            Cell oldSelectedCell = selectedCell;
            Cell newSelectedCell;
            for (int i = 0; i < numberOfTributes; i++) {
                ViewInterface.showResult("select cell to tribute:");
                Duel.processSelect(ViewInterface.getInput());
                newSelectedCell = Cell.getSelectedCell();
                if (!currentPlayer.getGameBoard().isCellInMonsterZone(newSelectedCell))
                    throw new GameException(GameResponses.NO_MONSTER_ON_CELL.response);
                tributes.add(newSelectedCell);
                ViewInterface.showResult("cell taken");
            }
            for (Cell tribute : tributes) {
                tribute.removeCardFromCell(currentPlayer.getGameBoard());
            }
            selectedCell = oldSelectedCell;
        }
        return selectedCell;

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
                if (gameController.DoPlayerSetOrSummonedThisTurn()) {
                    throw new GameException(GameResponses.ALREADY_SUMMONED_SET_IN_THIS_TURN.response);
                }
                playerGameBoard.addCardToMonsterCardZone(selectedCard, CardStatus.DEFENSIVE_HIDDEN, gameController);
                playerGameBoard.getHandCards().remove(selectedCell);
                gameController.setDidPlayerSetOrSummonThisTurn(true);
            } else {
                playerGameBoard.addCardToSpellAndTrapCardZone(selectedCard, CardStatus.HIDDEN, gameController);
                playerGameBoard.getHandCards().remove(selectedCell);
                TimeSeal.setActivated(gameController);
            }
            Cell.deselectCell();
        }
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
            Cell.deselectCell();
            if (position.equals("attack")) {
                cell.setCardStatus(CardStatus.OFFENSIVE_OCCUPIED);
            } else {
                cell.setCardStatus(CardStatus.DEFENSIVE_OCCUPIED);
            }
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
                    SpellAndTrap.activateSpellOrTrapEffects(gameController, spell);
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
        addMonstersToFlipSummonEffectSpellAndTrap();
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
        selectedCell.setCardStatus(CardStatus.OFFENSIVE_OCCUPIED);
        ManEaterBug.handleEffect(gameController, selectedCell);
        Cell.deselectCell();
        activateTrapIfCanBeActivated(gameController, SummonTypes.FlipSummon);
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

            selectedCell = handleTributeForNormalSummon(currentPlayer, selectedCell, monsterLevel, true);

            currentPlayer.getGameBoard().addCardToMonsterCardZone(selectedCell.getCellCard(), CardStatus.OFFENSIVE_OCCUPIED, gameController);
            currentPlayer.getGameBoard().getHandCards().remove(selectedCell);
            TerratigertheEmpoweredWarrior.handleEffect(gameController, selectedCell);
            gameController.setDidPlayerSetOrSummonThisTurn(true);
            gameController.shouldSpecialSummonNow = false;
            Cell.deselectCell();
            break;
        }
        activateTrapIfCanBeActivated(gameController, SummonTypes.SpecialSummon);
    }


    default void changeMonsterMode(Cell cell) {

    }

    default boolean isSummonable(Card card) {
        if (card.isMonster()) {
            return ((Monster) card).getCardType() != CardType.RITUAL;
        }
        return false;
    }

    default boolean isRitualSummonable(Cell cell) {
        return false;
    }

    default boolean isSpecialSummonable(Cell cell) {
        return false;
    }

    default boolean isSettable(Cell cell) {
        return false;
    }

    default void nonMonsterInsert(Cell cell) {

    }

    default boolean isFieldZoneFull() {
        return false;
    }

    default boolean hasEnoughTribute(int cardLevel) {
        return false;
    }

    default boolean isInFieldZone(Cell cell) {
        return false;
    }

    default boolean isTributesAddressesValid(int[] addresses) {
        return false;
    }

    default String showGameBoard(Player currentPlayer, Player opponentPlayer) {
        String response = ConsoleColors.BLUE + "\t\t" + opponentPlayer.getUser().getNickname() + ":" + opponentPlayer.getLP() + "\n";
        GameBoard playerGameBoard = currentPlayer.getGameBoard();
        GameBoard opponentPlayerGameBoard = opponentPlayer.getGameBoard();
        for (int i = 0; i < 6 - opponentPlayerGameBoard.getHandCards().size(); i++) {
            response += "\t";
        }
        for (int i = 0; i < opponentPlayerGameBoard.getHandCards().size(); i++) {
            response += "\tc";
        }
        response += "\n" + opponentPlayerGameBoard.getDeckZone().size() + "\n";
        response += "\t4\t2\t1\t3\t5\n";
        int[] opponentCellNumbering = {3, 1, 0, 2, 4};
        for (int i = 0; i < 5; i++) {
            if (opponentPlayerGameBoard.getSpellAndTrapCardZone()[opponentCellNumbering[i]]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (opponentPlayerGameBoard.getSpellAndTrapCardZone()[opponentCellNumbering[i]]
                        .getCardStatus()) {
                    case HIDDEN: {
                        response += "\tH";
                        break;
                    }
                    case OCCUPIED: {
                        response += "\tO";
                        break;
                    }

                }
            }
        }
        response += "\n";
        for (int i = 0; i < 5; i++) {
            if (opponentPlayerGameBoard.getMonsterCardZone()[opponentCellNumbering[i]]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (opponentPlayerGameBoard.getMonsterCardZone()[opponentCellNumbering[i]]
                        .getCardStatus()) {
                    case DEFENSIVE_HIDDEN: {
                        response += "\tDH";
                        break;
                    }
                    case DEFENSIVE_OCCUPIED: {
                        response += "\tDO";
                        break;
                    }
                    case OFFENSIVE_OCCUPIED: {
                        response += "\tOO";
                    }
                }
            }
        }
        response += "\n" + opponentPlayerGameBoard.getGraveyard().size() + "\t\t\t\t\t\t";
        if (opponentPlayerGameBoard.getFieldZone().isEmpty()) {
            response += "E";
        } else {
            response += "O";
        }
        response += "\n\n--------------------------\n\n";
        if (playerGameBoard.getFieldZone().isEmpty()) {
            response += "E";
        } else {
            response += "O";
        }
        response += "\t\t\t\t\t\t" + playerGameBoard.getGraveyard().size() + "\n";
        int[] playerCellNumbering = {4, 2, 0, 1, 3};
        for (int i = 0; i < 5; i++) {
            if (playerGameBoard.getMonsterCardZone()[playerCellNumbering[i]]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (playerGameBoard.getMonsterCardZone()[playerCellNumbering[i]]
                        .getCardStatus()) {
                    case DEFENSIVE_HIDDEN: {
                        response += "\tDH";
                        break;
                    }
                    case DEFENSIVE_OCCUPIED: {
                        response += "\tDO";
                        break;
                    }
                    case OFFENSIVE_OCCUPIED: {
                        response += "\tOO";
                    }
                }
            }
        }
        response += "\n";

        for (int i = 0; i < 5; i++) {
            if (playerGameBoard.getSpellAndTrapCardZone()[playerCellNumbering[i]]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (playerGameBoard.getSpellAndTrapCardZone()[playerCellNumbering[i]]
                        .getCardStatus()) {
                    case HIDDEN: {
                        response += "\tH";
                        break;
                    }
                    case OCCUPIED: {
                        response += "\tO";
                        break;
                    }

                }
            }
        }
        response += "\n\t5\t3\t1\t2\t4";
        response += "\n\t\t\t\t\t\t" + playerGameBoard.getDeckZone().size() + "\n";
        for (int i = 0; i < playerGameBoard.getHandCards().size(); i++) {
            response += "c\t";
        }
        response += "\n\t\t" + currentPlayer.getUser().getNickname() + ":" + currentPlayer.getLP() + ConsoleColors.RESET;
        return response;
    }

}
