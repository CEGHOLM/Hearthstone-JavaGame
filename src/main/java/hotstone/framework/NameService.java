package hotstone.framework;

import hotstone.framework.mutability.MutableCard;

public interface NameService {
    void addCard(String id, Card card);

    Card getCard(String id);

    void addHero(String id, Hero hero);

    Hero getHero(String id);

}
