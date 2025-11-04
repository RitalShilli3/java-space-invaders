public class ConcreteBoardFactory implements BoardFactory{

    @Override
    public Board createBoard(){

        // // to create an alien prototype with its image initialized
        // Alien prototype = new Alien(0, 0);
        // final String alienpix = "/img/alien.png";
        // prototype.setImage(SpriteManager.getInstance().getSprite(prototype.getClass().getResource(alienpix).getPath()));

        // // inject prototype into Board so Board will clone it when creating aliens
        // return new Board(prototype);

        // Get the weak alien type from Flyweight factory
        AlienType weakType = AlienTypeFactory.getInstance().getType("weak");

        // Create prototype with the AlienType
        Alien prototype = new Alien(0, 0, weakType);

        // Note: No need to setImage manually anymore - Alien constructor
        // automatically sets image from AlienType via getImage()

        // Inject prototype into Board
        return new Board(prototype);
    }
}
