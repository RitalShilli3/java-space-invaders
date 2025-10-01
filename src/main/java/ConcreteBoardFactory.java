public class ConcreteBoardFactory implements BoardFactory{

    @Override
    public Board createBoard(){

        // to create an alien prototype with its image initialized
        Alien prototype = new Alien(0, 0);
        final String alienpix = "/img/alien.png";
        prototype.setImage(SpriteManager.getInstance().getSprite(prototype.getClass().getResource(alienpix).getPath()));

        // inject prototype into Board so Board will clone it when creating aliens
        return new Board(prototype);
    }
}
