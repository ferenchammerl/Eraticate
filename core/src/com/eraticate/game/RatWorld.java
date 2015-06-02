package com.eraticate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.eraticate.game.gamescreenobjects.Rat;

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
                if (collLayer.getCell(i, j) != null)
                {


                    if (Math.random() < density)
                    {
                        rats.add(new Rat(i, j, collLayer));
                    }
                }
            }
        }

    }
    public void update(float delta)
    {
        for (Rat rat : rats)
        {
            rat.update(delta);
        }
    }
    public void Draw(Batch batch)
    {
        for (Rat rat : rats)
        {
            rat.draw(batch, 64, 64);
        }
    }
    public void tapped(float screenX, float screenY)
    {
        Gdx.app.log("Tapped", "yes it is");
        Gdx.app.log("Tap screen pos x", String.valueOf(screenX));
        Gdx.app.log("Tap screen pos y", String.valueOf(screenY));
        for (int i = 0; i < rats.size(); i++)
        {
            if (rats.get(i).isClicked(screenX, screenY, i))
            {

                Gdx.app.log("ClickedIndex", String.valueOf(i));
                rats.remove(i);
                Gdx.input.vibrate(500);
                break;
            }
        }
    }
}
