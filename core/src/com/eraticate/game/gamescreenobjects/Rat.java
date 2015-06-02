package com.eraticate.game.gamescreenobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ferenc on 5/26/2015.
 */
public class Rat extends GameScreenObjects
{

    public static final Texture def = new Texture(Gdx.files.internal("textures/rat/ratdef.png"));

    float speed = 5f;
    Moving moving = Moving.No;

    TiledMapTileLayer collLayer;
    private boolean kill;




    private void change_xPos(float v)
    {
        /* The conditional operators reintroduce information that was lost during the float to int conversion. */
        int desX = (int) (xPos + v) + (moving == Moving.Right ? 0 : 1);
        if (collLayer.getCell(desX + (moving == Moving.Right ? 1 : -1), (int) yPos) != null) //if can continue walking
        //1 and -1 so we know when we hit a wall
        {
            xPos += v;
        } else
        {
            xPos = desX;
            moving = Moving.No;

        }
    }
    private void change_yPos(float v)
    {
        int desY = (int) (yPos + v) + (moving == Moving.Up ? 0 : 1);
        if (collLayer.getCell((int) xPos, desY + (moving == Moving.Up ? 1 : -1)) != null)
        {
            yPos += v;
        } else
        {
            yPos = desY;
            moving = Moving.No;

        }
    }
    public Rat(Texture texture, int coordinate, int coordinate1, TiledMapTileLayer collLayer, float speed)
    {
        this(texture, coordinate, coordinate1, collLayer);
        this.speed = speed;
    }
    public Rat(int xPos, int yPos, TiledMapTileLayer collLayer)
    {
        this(def, xPos, yPos, collLayer);
    }
    public Rat(Texture texture, int xPos, int yPos, TiledMapTileLayer collLayer)
    {
        super(texture, xPos, yPos);
        this.collLayer = collLayer;
    }
    @Override
    public void draw(Batch batch, float x, float y, float width, float height)
    {
        float rotation;
        switch (moving)
        {
            case Right:
                rotation = 270;
                break;
            case Left:
                rotation = 90;
                break;
            case Up:
                rotation = 0;
                break;
            case Down:
                rotation = 180;
                break;
            default:
                rotation = 0;
        }
        //  batch.draw(texture, x, y, 32, 32, width, height, 1, 1, 157.07f, 0, 0, 768, 768, false, false); //figure it out how to rotate texture
        batch.draw(texture, x, y, 32, 32, width, height, 1, 1, rotation, 0, 0, 768, 768, false, false);
    }
    public void draw(Batch batch, float width, float height)
    {
        draw(batch, width * xPos, height * yPos, width, height);
    }
    public void update(float delta)
    {
        go(delta);
    }
    private void go(float delta)
    {
        if (moving == Moving.No)
        {
            ArrayList<Moving> directions = new ArrayList<Moving>();
            if (collLayer.getCell((int) xPos + 1, (int) yPos) != null)
            {
                directions.add(Moving.Right);
            }
            if (collLayer.getCell((int) xPos - 1, (int) yPos) != null)
            {
                directions.add(Moving.Left);
            }

            if (collLayer.getCell((int) xPos, (int) yPos + 1) != null)
            {
                directions.add(Moving.Up);
            }
            if (collLayer.getCell((int) xPos, (int) yPos - 1) != null)
            {
                directions.add(Moving.Down);
            }
            if (directions.size() == 0) return;
            moving = directions.get(new Random().nextInt(directions.size()));
        }
        switch (moving)
        {
            case Right:
                change_xPos(speed * delta);
                break;
            case Left:
                change_xPos(-speed * delta);
                break;
            case Up:
                change_yPos(speed * delta);
                break;
            case Down:
                change_yPos(-speed * delta);
                break;
        }
    }
    public enum Moving
    {
        No, Up, Down, Right, Left
    }
}
