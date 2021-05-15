package controller.gamephasescontrollers;

import model.cards.SpellAndTrap;
import model.cards.monsters.*;
import model.board.CardStatus;
import model.cards.monsters.ExploderDragon;
import model.cards.monsters.Marshmallon;
import model.cards.monsters.TheCalculator;
import model.cards.trapandspells.MirrorForce;
import model.cards.trapandspells.NegateAttack;
import model.cards.trapandspells.SwordsofRevealingLight;
import model.cards.trapandspells.TorrentialTribute;
import model.exceptions.GameException;
import model.Player;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.Card;
import model.cards.Monster;
import model.cards.monsters.YomiShip;
import view.gamephases.GameResponses;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static model.board.CardStatus.DEFENSIVE_OCCUPIED;
import static model.board.CardStatus.OFFENSIVE_OCCUPIED;

public class BattlePhaseController implements methods {

    private final GameController gameController;
    private boolean attackDisabled=false;
    private static ArrayList<SpellAndTrap>attackEffectSpellAndTraps;
    static {
        attackEffectSpellAndTraps=new ArrayList<>();
        attackEffectSpellAndTraps.add(new MirrorForce());
        attackEffectSpellAndTraps.add(new NegateAttack());
    }

    public BattlePhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setAttackDisabled(){
        attackDisabled=true;
    }

    public String attack(int attackedCellNumber) throws GameException {
        String response = "";
        Cell attackerCell = Cell.getSelectedCell();
        Cell attackedCell = null;
        GameBoard opponentGameBoard = gameController.currentTurnOpponentPlayer.getGameBoard();
        GameBoard playerGameBoard = gameController.currentTurnPlayer.getGameBoard();
        if (attackedCellNumber <= 5 && attackedCellNumber >= 1) {
            attackedCell = (gameController.getCurrentTurnOpponentPlayer().getGameBoard().getMonsterCardZone())[attackedCellNumber - 1];
        }
        if (attackerCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        } else if (!playerGameBoard.isCellInMonsterZone(attackerCell)) {
            throw new GameException(GameResponses.CANT_ATTACK_CARD.response);
        } else if (gameController.didCardAttackThisTurn(attackerCell)) {
            throw new GameException(GameResponses.ALREADY_ATTACKED_CARD.response);
        } else if (attackedCell == null || attackedCell.getCellCard() == null) {
            throw new GameException(GameResponses.NO_CARD_TO_ATTACK.response);
        } else {
            activateTrapIfCanBeActivated(gameController);
            if(attackDisabled){
                attackDisabled=false;
                return response;
            }
            if(SwordsofRevealingLight.handleEffect(gameController)){
                throw new GameException("you can't attack because of your opponent's Swords of Revealing Light effect");
            }
            if (CommandKnight.handleEffect(gameController, attackedCell))
                throw new GameException("Command Knight effect activated: you should first destroy other opponent monsters");
            Suijin.handleEffect(attackerCell, attackedCell);
            if (Texchanger.handleEffect(gameController, attackedCell)) throw new GameException("your attack canceled.");
            response = ExploderDragon.handleEffect(gameController, attackerCell, attackedCell);
            if (attackedCell.getCardStatus() == OFFENSIVE_OCCUPIED) {
                if (response.equals(""))
                    response = attackToOffensiveCell(attackerCell, attackedCell, opponentGameBoard, playerGameBoard);

            } else if (attackedCell.getCardStatus() == DEFENSIVE_OCCUPIED) {
                if (response.equals(""))
                    response = attackToDefensiveOccupiedCell(attackerCell, attackedCell, playerGameBoard);
            } else {
                if (response.equals(""))
                    response = attackToDefensiveHiddenCell(attackerCell, attackedCell, opponentGameBoard);
            }
        }
        Cell.deselectCell();
        return response;
    }
    private void activateTrapIfCanBeActivated(GameController gameController){
        for(Cell cell:gameController.currentTurnPlayer.getGameBoard().getSpellAndTrapCardZone()){
            if(!cell.isEmpty()&&cell.getCardStatus()==CardStatus.HIDDEN){
                Card card=cell.getCellCard();
                if(card.getName().equals("Mirror Force")/*||//todo we have to add other traps here...*/){
                    gameController.activateTrapEffect(attackEffectSpellAndTraps);
                    break;
                }
            }
        }
        for(Cell cell:gameController.currentTurnOpponentPlayer.getGameBoard().getSpellAndTrapCardZone()){
            if(!cell.isEmpty()&&cell.getCardStatus()==CardStatus.HIDDEN){
                Card card=cell.getCellCard();
                if(card.getName().equals("Mirror Force")||card.getName().equals("Negate Attack")){
                    gameController.changeTurn(true);
                    gameController.activateTrapEffect(attackEffectSpellAndTraps);
                    gameController.changeTurn(true);
                    break;
                }
            }
        }
    }

    private String attackToDefensiveHiddenCell(Cell attackerCell, Cell attackedCell, GameBoard opponentGameBoard) {
        String response;
        if (isAttackerStronger(attackerCell, attackedCell)) {
            response = "opponent’s monster card was " +
                    attackedCell.getCellCard().getName() + " the defense position monster is destroyed";
            response += Marshmallon.handleEffect(gameController, attackerCell, attackedCell);
            if (!Marshmallon.isMarshmallon(attackedCell))//todo chera ba payiniha fargh miknone? baad inja tanaghoz dareha
                attackedCell.removeCardFromCell(opponentGameBoard);
        } else if (isAttackerAndAttackedPowerEqual(attackerCell, attackedCell)) {
            response = "opponent’s monster card was " +
                    attackedCell.getCellCard().getName() + " and no card is destroyed";
            response += Marshmallon.handleEffect(gameController, attackerCell, attackedCell);
        } else {
            decreasePlayersDamage(attackerCell, attackedCell);
            response = "opponent’s monster card was " + attackedCell.getCellCard().getName() +
                    " and no card is destroyed and you received " +
                    calculateDamage(attackerCell, attackedCell) + " battle damage";
            response += Marshmallon.handleEffect(gameController, attackerCell, attackedCell);
        }
        gameController.getAttackerCellsThisTurn().add(attackedCell);
        return response;
    }
    private String attackToDefensiveOccupiedCell(Cell attackerCell, Cell attackedCell, GameBoard playerGameBoard) {
        String response;
        if (isAttackerStronger(attackerCell, attackedCell)) {
            decreasePlayersDamage(attackerCell, attackedCell);
            response = "the defense position monster is destroyed";
            YomiShip.handleEffect(gameController, attackerCell, attackedCell);
            attackedCell.removeCardFromCell(playerGameBoard);
        } else if (isAttackerAndAttackedPowerEqual(attackerCell, attackedCell)) {
            response = "no card is destroyed";
        } else {
            decreasePlayersDamage(attackerCell, attackedCell);
            response = "no card is destroyed and you received " +
                    calculateDamage(attackerCell, attackedCell) + " battle damage";
        }
        gameController.getAttackerCellsThisTurn().add(attackedCell);
        return response;
    }

    private String attackToOffensiveCell(Cell attackerCell, Cell attackedCell, GameBoard opponentGameBoard, GameBoard playerGameBoard) {
        String response;
        if (isAttackerStronger(attackerCell, attackedCell)) {
            decreasePlayersDamage(attackerCell, attackedCell);
            response = "your opponent’s monster is destroyed and your opponent receives "
                    + calculateDamage(attackerCell, attackedCell) + " battle damage";
            YomiShip.handleEffect(gameController, attackerCell, attackedCell);
            attackedCell.removeCardFromCell(opponentGameBoard);
        } else if (isAttackerAndAttackedPowerEqual(attackerCell, attackedCell)) {
            response = "both you and your opponent monster cards are destroyed and no one receives damage";
            attackerCell.removeCardFromCell(playerGameBoard);
            attackedCell.removeCardFromCell(opponentGameBoard);
        } else {
            decreasePlayersDamage(attackerCell, attackedCell);
            response = "Your monster card is destroyed and you received " +
                    calculateDamage(attackerCell, attackedCell) + " battle damage";
            attackerCell.removeCardFromCell(playerGameBoard);
        }
        gameController.getAttackerCellsThisTurn().add(attackedCell);
        return response;
    }

    private void decreasePlayersDamage(Cell attackerCell, Cell attackedCell) {
        if (isAttackerStronger(attackerCell, attackedCell))
            (gameController.getCurrentTurnOpponentPlayer()).decreaseLP(calculateDamage(attackerCell, attackedCell));
        else
            (gameController.getCurrentTurnPlayer()).decreaseLP(calculateDamage(attackerCell, attackedCell));
    }

    public boolean isAttackerStronger(Cell attackerCell, Cell attackedCell) {
        return getPower(attackerCell, attackerCell, attackedCell) > getPower(attackedCell, attackerCell, attackedCell);
    }

    public boolean isAttackerAndAttackedPowerEqual(Cell attackerCell, Cell attackedCell) {
        return getPower(attackerCell, attackerCell, attackedCell) == getPower(attackedCell, attackerCell, attackedCell);
    }

    public int calculateDamage(Cell attackerCell, Cell attackedCell) {
        int damage = getPower(attackedCell, attackerCell, attackedCell) - getPower(attackerCell, attackerCell, attackedCell);
        if (damage >= 0)
            return damage;
        else
            return -damage;
    }

    public boolean canCardAttack(Card card) {
        return true;
    }

    public String directAttack(GameController gameController) throws GameException {
        Player currentPlayer = gameController.currentTurnPlayer;
        Cell selectedCell = Cell.getSelectedCell();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        }
        if (!currentPlayer.getGameBoard().isCellInMonsterZone(selectedCell)||selectedCell.getCardStatus()!= OFFENSIVE_OCCUPIED) {
            throw new GameException(GameResponses.CAN_NOT_ATTACK_WITH_THIS_CARD.response);
        }
        if (gameController.didCardAttackThisTurn(selectedCell)) {
            throw new GameException(GameResponses.CARD_ALREADY_ATTACKED.response);
        }
        if (!gameController.canPlayerDirectAttack(selectedCell)) {
            throw new GameException(GameResponses.CAN_NOT_DIRECT_ATTACK.response);
        }
        Monster attackerMonster = (Monster) selectedCell.getCellCard();
        gameController.getCurrentTurnOpponentPlayer().decreaseLP(attackerMonster.getAtk());
        gameController.getAttackerCellsThisTurn().add(selectedCell);
        Cell.deselectCell();
        return "your opponent receives " + attackerMonster.getAtk() + " battle damage";
    }

    public GameController getGameController() {
        return gameController;
    }

    public int getPower(Cell cell, Cell attackerCell, Cell attackedCell) {//todo null pointer exception
        switch (cell.getCardStatus()) {
            case DEFENSIVE_OCCUPIED:
            case DEFENSIVE_HIDDEN:
                return ((Monster) cell.getCellCard()).getDef();
            case OFFENSIVE_OCCUPIED: {
                TheCalculator.handleEffect(gameController, attackerCell, attackedCell);
                return ((Monster) cell.getCellCard()).getAtk();
            }
        }
        return 0;
    }
}
