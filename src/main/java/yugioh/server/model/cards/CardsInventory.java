package yugioh.server.model.cards;

import java.util.HashMap;

public class CardsInventory {
    public  HashMap<String,Integer> cardsInventory=new HashMap<>();
    public static CardsInventory inventory;

    public void addCardToInventory(Card card,int amount){
        if(cardsInventory.containsKey(card.getName())){
            cardsInventory.put(card.getName(),cardsInventory.get(card.getName())+amount);
        }
        else{
            cardsInventory.put(card.getName(),amount);
        }
    }
    public void removeCardFromInventory(Card card,int amount){
        cardsInventory.put(card.getName(),cardsInventory.get(card.getName())-amount);
    }

    public static void setInventory(CardsInventory inventory) {
        CardsInventory.inventory = inventory;
    }
    public void setCardsInventory(){
        for(Card card:Card.allCards){
            cardsInventory.put(card.getName(),0);
        }
    }
}
