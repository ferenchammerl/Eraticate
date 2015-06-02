package com.eraticate.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.eraticate.game.screens.GameScreen;
import com.eraticate.game.screens.MainMenuScreen;

public class Eraticate extends Game
{
    private SpriteBatch batch;


    public SpriteBatch getBatch()
    {
        return batch;
    }

    @Override
    public void create()
    {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }


    public void dispose()
    {
        batch.dispose();
    }
}
