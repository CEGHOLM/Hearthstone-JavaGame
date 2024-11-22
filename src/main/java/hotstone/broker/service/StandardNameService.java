package hotstone.broker.service;

import hotstone.framework.Card;
import hotstone.framework.Hero;
import hotstone.framework.NameService;
import hotstone.framework.mutability.MutableCard;

import java.util.HashMap;
import java.util.Map;

public class StandardNameService implements NameService {
    private final Map<String, Card> cardMap = new HashMap<>();
    private final Map<String, Hero> heroMap = new HashMap<>();

    @Override
    public void addCard(String id, Card card) {
        cardMap.put(id, card);
    }

    @Override
    public Card getCard(String id) {
        return cardMap.get(id);
    }

    @Override
    public void addHero(String id, Hero hero) {
        heroMap.put(id, hero);
    }

    @Override
    public Hero getHero(String id) {
        return heroMap.get(id);
    }

}
