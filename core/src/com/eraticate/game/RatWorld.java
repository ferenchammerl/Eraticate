package com.eraticate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.eraticate.game.gamescreenobjects.Rat;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ferenc on 5/30/2015.
 */
public class RatWorld
{
    private TiledMapTileLayer collLayer;
    private TiledMap map;
    private ArrayList<Rat> rats = new ArrayList<Rat>();

    private ArrayList<int[]> roadCoords;

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
        roadCoords = new ArrayList<int[]>();
        for (int i = 1; i <= collLayer.getHeight(); i++)
        {
            for (int j = 1; j <= collLayer.getWidth(); j++)
            {
                if (collLayer.getCell(i, j) != null)
                {
                    roadCoords.add(new int[]{i, j});
                    if (Math.random() < density)
                    {
                        rats.add(new Rat(i, j, collLayer));
                    }
                }
            }
        }
        for (int i = 0; i < roadCoords.size(); i++)
        {

            Gdx.app.log(i + " X", String.valueOf(roadCoords.get(i)[1]));
            Gdx.app.log(i + " Y", String.valueOf(roadCoords.get(i)[0]));
        }

    }
    float timeSinceLastSpawn = 0;
    float frequency = 0;
    float maxFrequency = 0.5f;
    public OutCome update(float delta)
    {

        if (rats.size() > 100) {return OutCome.Defeat;}
        if (rats.size() == 0) {return OutCome.Victory;}
        timeSinceLastSpawn += delta;
        frequency = (float) rats.size() / 100;
        frequency = frequency > maxFrequency ? maxFrequency : frequency;

        if (timeSinceLastSpawn * frequency > 1)
        {

            int[] coordinates = roadCoords.get(new Random().nextInt(roadCoords.size()));
            rats.add(new Rat(new Texture(Gdx.files.internal("textures/rat/ratsecond.png")), coordinates[0], coordinates[1], collLayer));
            timeSinceLastSpawn = 0;
        }
        for (Rat rat : rats)
        {
            rat.update(delta);
        }
        return OutCome.Continue;
    }
    public void Draw(Batch batch)
    {
        for (Rat rat : rats)
        {
            rat.draw(batch, 64, 64);
        }
    }
    public boolean executed(float screenX, float screenY)
    {

        for (int i = 0; i < rats.size(); i++)
        {
            if (rats.get(i).isClicked(screenX, screenY, i))
            {
                rats.remove(i);
                return true;
            }
        }
        return false;
    }
    public enum OutCome
    {
        Continue, Defeat, Victory
    }

}
