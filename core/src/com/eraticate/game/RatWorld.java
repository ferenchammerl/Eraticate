package com.eraticate.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.eraticate.game.mapobjects.Rat;

import java.util.ArrayList;

/**
 * Created by Ferenc on 5/30/2015.
 */
public class RatWorld
{
    private TiledMapTileLayer collLayer;
    private TiledMap map;
    private ArrayList<Rat> rats = new ArrayList<Rat>();

    public TiledMap getMap()
    {
        return map;
    }

    public RatWorld(TiledMap map)
    {
        this.map = map;
        this.collLayer = (TiledMapTileLayer) map.getLayers().get(1);
    }
    public void initRats(float density)
    {
        for (int i = 1; i <= collLayer.getHeight(); i++)
        {
            for (int j = 1; j <= collLayer.getWidth(); j++)
            {
                if (collLayer.getCell(i, j) == null) continue;
                if (Math.random() < density) rats.add(new Rat(i, j, collLayer));

            }
        }
    }
    public void Draw(Batch batch, float delta)
    {
        for (Rat rat : rats)
        {
            rat.draw(batch, 64, 64);
        }
    }
}
