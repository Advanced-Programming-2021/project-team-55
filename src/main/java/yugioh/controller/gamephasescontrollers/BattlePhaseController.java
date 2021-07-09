package yugioh.controller.gamephasescontrollers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.model.Player;
import yugioh.model.board.CardStatus;
import yugioh.model.board.Cell;
import yugioh.model.board.GameBoard;
import yugioh.model.cards.Card;
import yugioh.model.cards.Monster;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.monsters.*;
import yugioh.model.cards.trapandspells.MagicCylinder;
import yugioh.model.cards.trapandspells.MirrorForce;
import yugioh.model.cards.trapandspells.NegateAttack;
import yugioh.model.cards.trapandspells.SwordsofRevealingLight;
import yugioh.model.exceptions.GameException;
import yugioh.view.gamephases.CardActionsMenu;
import yugioh.view.gamephases.Duel;
import yugioh.view.gamephases.GameResponses;

import java.util.ArrayList;

import static yugioh.view.SoundPlayable.playButtonSound;

public class BattlePhaseController {

    private static final ArrayList<String> attackEffectSpellAndTraps;

    static {
        attackEffectSpellAndTraps = new ArrayList<>();
        attackEffectSpellAndTraps.add(new MirrorForce().getName());
        attackEffectSpellAndTraps.add(new NegateAttack().getName());
        attackEffectSpellAndTraps.add(new MagicCylinder().getName());
    }

    private final GameController gameController;
    private boolean attackDisabled = false;
    private Cell attacker;

    public BattlePhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setAttackDisabled() {
        attackDisabled = true;
    }

    public Cell getAttacker() {
        return attacker;
    }

    private void setAttacker(Cell attacker) {
        if (attacker != null) {
            this.attacker = new Cell(attacker.getCellCard());
        }
    }

    public String attack(int attackedCellNumber) throws GameException {
        String response = "";
        Cell attackerCell = Cell.getSelectedCell();
        Cell attackedCell = null;
        GameBoard opponentGameBoard = gameController.currentTurnOpponentPlayer.getGameBoard();
        GameBoard playerGameBoard = gameController.currentTurnPlayer.getGameBoard();
        if (attackedCellNumber <= 4 && attackedCellNumber >= 0) {
            attackedCell = (gameController.getCurrentTurnOpponentPlayer().getGameBoard().getMonsterCardZone())[attackedCellNumber];
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
            setAttacker(attackerCell);
            activateTrapIfCanBeActivated(gameController);
            if (attackDisabled) {
                gameController.getAttackerCellsThisTurn().add(attackerCell);
                attackDisabled = false;
                return response;
            }
            if (SwordsofRevealingLight.handleEffect(gameController)) {
                throw new GameException("you can't attack because of your opponent's Swords of Revealing Light effect");
            }
            if (CommandKnight.handleEffect(gameController, attackedCell))
                throw new GameException("Command Knight effect activated: you should first destroy other opponent monsters");
            Suijin.handleEffect(attackerCell, attackedCell);
            if (Texchanger.handleEffect(gameController, attackedCell)) throw new GameException("your attack canceled.");
            gameController.getAttackerCellsThisTurn().add(attackerCell);
            response = ExploderDragon.handleEffect(gameController, attackerCell, attackedCell);
            if (attackedCell.getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED) {
                if (response.equals(""))
                    response = attackToOffensiveCell(attackerCell, attackedCell, opponentGameBoard, playerGameBoard);

            } else if (attackedCell.getCardStatus() == CardStatus.DEFENSIVE_OCCUPIED) {
                if (response.equals(""))
                    response = attackToDefensiveOccupiedCell(attackerCell, attackedCell, playerGameBoard);
            } else {
                if (response.equals(""))
                    response = attackToDefensiveHiddenCell(attackerCell, attackedCell, opponentGameBoard);
            }
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> CardActionsMenu.removeSword()));
            timeline.play();
        }
        setAttacker(null);
        Cell.deselectCell();
        return response;
    }

    private void activateTrapIfCanBeActivated(GameController gameController) {
        for (Cell cell : gameController.currentTurnOpponentPlayer.getGameBoard().getSpellAndTrapCardZone()) {
            if (!cell.isEmpty() && cell.getCardStatus() == CardStatus.HIDDEN) {
                Card card = cell.getCellCard();
                if (card.getName().equals("Mirror Force") || card.getName().equals("Negate Attack")
                        || card.getName().equals("Magic Cylinder")) {
                    gameController.changeTurn(true, false);
                    gameController.activateTrapEffect(attackEffectSpellAndTraps);
                    gameController.changeTurn(true, true);
                    break;
                }
            }
        }
    }

    private String attackToDefensiveHiddenCell(Cell attackerCell, Cell attackedCell, GameBoard opponentGameBoard) {
        String response;
        Duel.getGameController().currentTurnPlayer.getGameBoard().setFlipTransition(attackedCell.getCellCard(), attackedCell.getCellRectangle(), false);
        if (isAttackerStronger(attackerCell, attackedCell)) {
            response = "opponent’s monster card was " +
                    attackedCell.getCellCard().getName() + " the defense position monster is destroyed";
            response += Marshmallon.handleEffect(gameController, attackerCell, attackedCell);
            if (!Marshmallon.isMarshmallon(attackedCell)) {//todo chera ba payiniha fargh miknone? baad inja tanaghoz dareha
                Rectangle graveyard = GameMenuController.getGameMenuController().rivalGraveyard;
                if (CardActionsMenu.isBoardInverse()) graveyard = GameMenuController.getGameMenuController().userGraveyard;
                Rectangle finalGraveyard = graveyard;
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                    moveCardToGraveyard(attackedCell, finalGraveyard, gameController.currentTurnOpponentPlayer);
                    attackedCell.removeCardFromCell(opponentGameBoard);
                }));
                timeline.play();
            }
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
        gameController.getAttackerCellsThisTurn().add(attackerCell);
        return response;
    }

    private String attackToDefensiveOccupiedCell(Cell attackerCell, Cell attackedCell, GameBoard playerGameBoard) {
        String response;
        Duel.getGameController().currentTurnPlayer.getGameBoard().setFlipTransition(attackedCell.getCellCard(), attackedCell.getCellRectangle(), false);
        if (isAttackerStronger(attackerCell, attackedCell)) {
            decreasePlayersDamage(attackerCell, attackedCell);
            response = "the defense position monster is destroyed";
            YomiShip.handleEffect(gameController, attackerCell, attackedCell);
            Rectangle graveyard = GameMenuController.getGameMenuController().rivalGraveyard;
            if (CardActionsMenu.isBoardInverse()) graveyard = GameMenuController.getGameMenuController().userGraveyard;
            Rectangle finalGraveyard = graveyard;
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                moveCardToGraveyard(attackedCell, finalGraveyard, gameController.currentTurnOpponentPlayer);
                attackedCell.removeCardFromCell(playerGameBoard);
            }));
            timeline.play();
        } else if (isAttackerAndAttackedPowerEqual(attackerCell, attackedCell)) {
            response = "no card is destroyed";
        } else {
            decreasePlayersDamage(attackerCell, attackedCell);
            response = "no card is destroyed and you received " +
                    calculateDamage(attackerCell, attackedCell) + " battle damage";
        }
        gameController.getAttackerCellsThisTurn().add(attackerCell);
        return response;
    }

    private String attackToOffensiveCell(Cell attackerCell, Cell attackedCell, GameBoard opponentGameBoard, GameBoard playerGameBoard) {
        String response;
        if (isAttackerStronger(attackerCell, attackedCell)) {
            decreasePlayersDamage(attackerCell, attackedCell);
            response = "your opponent’s monster is destroyed and your opponent receives "
                    + calculateDamage(attackerCell, attackedCell) + " battle damage";
            YomiShip.handleEffect(gameController, attackerCell, attackedCell);
            Rectangle graveyard = GameMenuController.getGameMenuController().rivalGraveyard;
            if (CardActionsMenu.isBoardInverse()) graveyard = GameMenuController.getGameMenuController().userGraveyard;
            moveCardToGraveyard(attackedCell, graveyard, gameController.currentTurnOpponentPlayer);
            attackedCell.removeCardFromCell(opponentGameBoard);
        } else if (isAttackerAndAttackedPowerEqual(attackerCell, attackedCell)) {
            response = "both you and your opponent monster cards are destroyed and no one receives damage";
            if (CardActionsMenu.isBoardInverse()) {
                moveCardToGraveyard(attackedCell, GameMenuController.getGameMenuController().userGraveyard, gameController.currentTurnOpponentPlayer);
                moveCardToGraveyard(attackerCell, GameMenuController.getGameMenuController().rivalGraveyard, gameController.currentTurnPlayer);
            } else {
                moveCardToGraveyard(attackedCell, GameMenuController.getGameMenuController().rivalGraveyard, gameController.currentTurnOpponentPlayer);
                moveCardToGraveyard(attackerCell, GameMenuController.getGameMenuController().userGraveyard, gameController.currentTurnPlayer);
            }
            attackerCell.removeCardFromCell(playerGameBoard);
            attackedCell.removeCardFromCell(opponentGameBoard);
        } else {
            decreasePlayersDamage(attackerCell, attackedCell);
            response = "Your monster card is destroyed and you received " +
                    calculateDamage(attackerCell, attackedCell) + " battle damage";
            Rectangle graveyard = GameMenuController.getGameMenuController().userGraveyard;
            if (CardActionsMenu.isBoardInverse()) graveyard = GameMenuController.getGameMenuController().rivalGraveyard;
            moveCardToGraveyard(attackerCell, graveyard, gameController.currentTurnPlayer);
            attackerCell.removeCardFromCell(playerGameBoard);
        }
        gameController.getAttackerCellsThisTurn().add(attackerCell);
        return response;
    }

    public void moveCardToGraveyard(Cell cell, Rectangle graveyard, Player player) {
        playButtonSound("graveYard");
        graveyard.fillProperty().setValue(cell.getCellRectangle().getFill());
        player.getGameBoard().setFadeTransition(graveyard, 0, 1);
    }

    private void decreasePlayersDamage(Cell attackerCell, Cell attackedCell) {
        if (isAttackerStronger(attackerCell, attackedCell)) {
            (gameController.getCurrentTurnOpponentPlayer()).decreaseLP(calculateDamage(attackerCell, attackedCell));
        } else {
            (gameController.getCurrentTurnPlayer()).decreaseLP(calculateDamage(attackerCell, attackedCell));
        }
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

    public String directAttack(GameController gameController) throws GameException {
        String response = "";
        Player currentPlayer = gameController.currentTurnPlayer;
        Cell selectedCell = Cell.getSelectedCell();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        }
        if (!currentPlayer.getGameBoard().isCellInMonsterZone(selectedCell) || selectedCell.getCardStatus() != CardStatus.OFFENSIVE_OCCUPIED) {
            throw new GameException(GameResponses.CAN_NOT_ATTACK_WITH_THIS_CARD.response);
        }
        if (gameController.didCardAttackThisTurn(selectedCell)) {
            throw new GameException(GameResponses.CARD_ALREADY_ATTACKED.response);
        }
        if (!gameController.canPlayerDirectAttack()) {
            throw new GameException(GameResponses.CAN_NOT_DIRECT_ATTACK.response);
        }
        setAttacker(selectedCell);
        activateTrapIfCanBeActivated(gameController);
        if (attackDisabled) {
            gameController.getAttackerCellsThisTurn().add(selectedCell);
            attackDisabled = false;
            return response;
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
