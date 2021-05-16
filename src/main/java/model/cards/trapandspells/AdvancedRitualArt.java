package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.CardStatus;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.Monster;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.exceptions.GameException;
import view.ViewInterface;
import view.gamephases.Duel;
import view.gamephases.GameResponses;

import java.util.ArrayList;

public class AdvancedRitualArt extends SpellAndTrap {

    public AdvancedRitualArt() {
        super("Advanced Ritual Art", "This card can be used to Ritual Summon any 1 Ritual Monster. You must also send Normal Monsters from your Deck to the Graveyard whose total Levels equal the Level of that Ritual Monster.",
                3000, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.RITUAL, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        if (!canRitualSummon(gameController)) {
            ViewInterface.showResult(GameResponses.NO_WAY_TO_RITUAL_SUMMON.response);
        } else {
            try {
                ritualSummon(gameController);
            } catch (GameException e) {
                ViewInterface.showResult("Error: ritual summon has a problem");
            }
        }

    }

    private static boolean canRitualSummon(GameController gameController) {
        Cell[] monsterCardZone = gameController.currentTurnPlayer.getGameBoard().getMonsterCardZone();
        ArrayList<Cell> handCards = gameController.currentTurnPlayer.getGameBoard().getHandCards();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty()) {
                monsters.add((Monster) monsterCardZone[i].getCellCard());
            }
        }
        for (int i = 0; i < handCards.size(); i++) {
            if (handCards.get(i).getCellCard().isMonster()) {
                Monster monster = (Monster) handCards.get(i).getCellCard();
                if (monster.getCardType() == CardType.RITUAL) {
                    int monsterLevel = monster.getLevel();
                    if (monsterLevel >= 7) {
                        for (int j = 0; j < monsters.size(); j++) {
                            for (int k = j + 1; k < monsters.size(); k++) {
                                if (monsters.get(j).getLevel() + monsters.get(k).getLevel() == monsterLevel) {
                                    return true;
                                }
                            }
                        }

                    } else {
                        for (int j = 0; j < monsters.size(); j++) {
                            if (monsters.get(j).getLevel() == monsterLevel) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static void ritualSummon(GameController gameController) throws GameException {
        Cell selectedSpellCell = Cell.getSelectedCell();
        GameBoard playerGameBoard = gameController.currentTurnPlayer.getGameBoard();
        while (true) {
            ViewInterface.showResult("Advanced Ritual Art effect activated : select a card from hand to ritual summon:");
            String input = ViewInterface.getInput();
            String response = Duel.processSelect(input);
            if (input.equals("cancel")) {
                ViewInterface.showResult("you cancelled the activation of Advanced Ritual Art");
                return;
            }
            if (input.matches("^select --hand (\\d+)$")) {
                Cell ritualMonsterCell = Cell.getSelectedCell();
                if (response.equals(GameResponses.CARD_SELECTED) || !ritualMonsterCell.getCellCard().isMonster() ||
                        ((Monster) ritualMonsterCell.getCellCard()).getCardType() != CardType.RITUAL) {
                    ViewInterface.showResult(GameResponses.YOU_SHOULD_RITUAL_SUMMON_NOW.response);
                    ViewInterface.showResult("Error: try again!");
                    continue;
                } else {
                    Monster ritualMonster = (Monster) ritualMonsterCell.getCellCard();
                    Cell tributeCell1 = new Cell();
                    Cell tributeCell2 = new Cell();
                    while (true) {
                        ViewInterface.showResult("select cards to tribute:");
                        if (ritualMonster.getLevel() < 7) {
                            String input2 = ViewInterface.getInput();
                            String response2 = Duel.getMainPhase1().processSelect(input2);
                            if (input2.equals("cancel")) {
                                ViewInterface.showResult("you cancelled the activation of Advanced Ritual Art");
                                return;
                            }
                            if (input.matches("^select --monster (\\d+)$")) {
                                if (response2.equals(GameResponses.CARD_SELECTED.response)) {
                                    Cell tributeCell = Cell.getSelectedCell();
                                    Monster selectedMonsterTribute = ((Monster) tributeCell.getCellCard());
                                    if (selectedMonsterTribute.getLevel() != ritualMonster.getLevel()) {
                                        ViewInterface.showResult(GameResponses.SELECTED_MONSTERS_DONT_MATCH.response);
                                        ViewInterface.showResult("Error: try again!");
                                        continue;
                                    } else {
                                        while (true) {
                                            ViewInterface.showResult("choose card position: (attack/defense)");
                                            String input3 = ViewInterface.getInput();
                                            if (input3.equals("cancel")) {
                                                ViewInterface.showResult("you cancelled the activation of Advanced Ritual Art");
                                                return;
                                            }
                                            if (input3.equals("attack")) {
                                                tributeCell.removeCardFromCell(playerGameBoard);
                                                ritualMonsterCell.removeCardFromCell(playerGameBoard);
                                                playerGameBoard.addCardToMonsterCardZone(ritualMonster, CardStatus.OFFENSIVE_OCCUPIED, gameController);
                                                Cell.setSelectedCell(selectedSpellCell);
                                                updateSpellInGameBoard(gameController);
                                                return;
                                            } else if (input3.equals("defense")) {
                                                tributeCell.removeCardFromCell(playerGameBoard);
                                                ritualMonsterCell.removeCardFromCell(playerGameBoard);
                                                playerGameBoard.addCardToMonsterCardZone(ritualMonster, CardStatus.DEFENSIVE_HIDDEN, gameController);
                                                Cell.setSelectedCell(selectedSpellCell);
                                                updateSpellInGameBoard(gameController);
                                                return;

                                            } else {
                                                ViewInterface.showResult("Error: try again!");
                                                continue;
                                            }
                                        }
                                    }
                                } else {
                                    ViewInterface.showResult(response2);
                                    ViewInterface.showResult("Error: try again!");
                                    continue;
                                }
                            }
                        } else {
                            String input2 = ViewInterface.getInput();
                            String response2 = Duel.getMainPhase1().processSelect(input2);
                            if (input2.equals("cancel")) {
                                ViewInterface.showResult("you cancelled the activation of Advanced Ritual Art");
                                return;
                            }
                            if (input2.matches("^select --monster (\\d+)$")) {
                                if (response2.equals(GameResponses.CARD_SELECTED.response)) {
                                    if (tributeCell1.isEmpty()) {
                                        ViewInterface.showResult("first card selected!");
                                        tributeCell1 = Cell.getSelectedCell();
                                        continue;
                                    }
                                    else {
                                        tributeCell2=Cell.getSelectedCell();
                                        if (tributeCell1 == tributeCell2) {
                                            ViewInterface.showResult("Error: select another card!");
                                            ViewInterface.showResult("Error: try again");
                                            continue;
                                        } else {
                                            while (true) {
                                                ViewInterface.showResult("choose card position: (attack/defense)");
                                                String input3 = ViewInterface.getInput();
                                                if (input3.equals("cancel")) {
                                                    ViewInterface.showResult("you cancelled the activation of Advanced Ritual Art");
                                                    return;
                                                }
                                                if (input3.equals("attack")) {
                                                    tributeCell1.removeCardFromCell(playerGameBoard);
                                                    tributeCell2.removeCardFromCell(playerGameBoard);
                                                    ritualMonsterCell.removeCardFromCell(playerGameBoard);
                                                    playerGameBoard.addCardToMonsterCardZone(ritualMonster, CardStatus.OFFENSIVE_OCCUPIED, gameController);
                                                    Cell.setSelectedCell(selectedSpellCell);
                                                    updateSpellInGameBoard(gameController);
                                                    return;
                                                } else if (input3.equals("defense")) {
                                                    tributeCell1.removeCardFromCell(playerGameBoard);
                                                    tributeCell2.removeCardFromCell(playerGameBoard);
                                                    ritualMonsterCell.removeCardFromCell(playerGameBoard);
                                                    playerGameBoard.addCardToMonsterCardZone(ritualMonster, CardStatus.DEFENSIVE_HIDDEN, gameController);
                                                    Cell.setSelectedCell(selectedSpellCell);
                                                    updateSpellInGameBoard(gameController);
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    ViewInterface.showResult(response2);
                                    ViewInterface.showResult("Error: try again!");
                                    continue;
                                }
                            } else {
                                ViewInterface.showResult(response2);
                                ViewInterface.showResult("Error: try again!");
                                continue;
                            }
                        }
                    }
                }
            }
        }
    }
}