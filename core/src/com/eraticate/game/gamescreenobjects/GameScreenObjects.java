package com.eraticate.game.gamescreenobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Ferenc on 5/31/2015.
 */
public abstract class GameScreenObjects
{
    Texture texture;
    public void setxPos(float xPos)
    {
        this.xPos = xPos;
    }
    public void setyPos(float yPos)
    {
        this.yPos = yPos;
    }

    float xPos;
    float yPos;

    public GameScreenObjects(Texture texture)
    {
        this.texture = texture;
    }
    public GameScreenObjects(Texture texture, float xPos, float yPos)
    {
        this.texture = texture;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public void draw(Batch batch, float x, float y, float width, float height)
    {
        batch.draw(texture, x, y, width, height);
    }
    public void draw(Batch batch, float width, float height)
    {
        draw(batch, width * xPos, height * yPos, width, height);
    }
    public boolean isClicked(float screenX, float screenY, int i)
    {
        float xPosScreen = xPos * 64;
        float yPosScreen = yPos * 64;

        Gdx.app.log(i + " pos x", String.valueOf(xPosScreen));
        Gdx.app.log(i + " pos y ", String.valueOf(yPosScreen));
        return xPosScreen-16 < screenX && screenX < xPosScreen + 80 && yPosScreen < screenY && screenY < yPosScreen + 80;
    }
}
