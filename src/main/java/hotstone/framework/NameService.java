package hotstone.framework;

public interface NameService {
    void addCard(String id, Card card);

    Card getCard(String id);

    void addHero(String id, Hero hero);

    Hero getHero(String id);

}
