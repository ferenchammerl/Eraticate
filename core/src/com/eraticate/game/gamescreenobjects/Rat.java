package com.eraticate.game.gamescreenobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.Random;

/**
 * Created by Ferenc on 5/26/2015.
 */
public class Rat extends GameScreenObjects
{

    public static final Texture right = new Texture(Gdx.files.internal("textures/rat/ratright.png"));
    public static final Texture left = new Texture(Gdx.files.internal("textures/rat/ratleft.png"));
    public static final Texture up = new Texture(Gdx.files.internal("textures/rat/ratup.png"));
    public static final Texture down = new Texture(Gdx.files.internal("textures/rat/ratdown.png"));

    float speed = 5f;
    Moving moving = Moving.No;

    TiledMapTileLayer collLayer;
    private boolean kill;

    public boolean isKill() {return kill;}
    public void setKill() {this.kill = true;}


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

    public Rat(int xPos, int yPos, TiledMapTileLayer collLayer)
    {
        this(right, xPos, yPos, collLayer);
    }
    public Rat(Texture texture, int xPos, int yPos, TiledMapTileLayer collLayer)
    {
        super(texture, xPos, yPos);
        this.collLayer = collLayer;
    }
    @Override
    public void draw(Batch batch, float x, float y, float width, float height)
    {
        switch (moving)
        {
            case Right:
                texture = right;
                break;
            case Left:
                texture = left;
                break;
            case Up:
                texture = up;
                break;
            case Down:
                texture = down;
                break;
        }
        batch.draw(texture, x, y, width, height); //figure it out how to rotate texture
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
            Random rand = new Random();

            int randomNum = rand.nextInt(4);
            moving = new Moving[]{Moving.Up, Moving.Down, Moving.Right, Moving.Left}[randomNum];
//            float x = (float) Math.random();
//            if (collLayer.getCell((int) xPos + 1, (int) yPos) != null)
//            {
//                moving = Moving.Right;
//                if (x < 0.25f) return;
//            }
//            x -= 0.25f;
//            if (collLayer.getCell((int) xPos - 1, (int) yPos) != null)
//            {
//                moving = Moving.Left;
//                if (x < 0.25f) return;
//            }
//            x -= 0.25f;
//
//            if (collLayer.getCell((int) xPos, (int) yPos + 1) != null)
//            {
//                moving = Moving.Up;
//                if (x < 0.25f) return;
//            }
//            if (collLayer.getCell((int) xPos, (int) yPos - 1) != null)
//            {
//                moving = Moving.Down;
//            }
//
            return;
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
