package com.eraticate.game.screens;

import com.badlogic.gdx.Screen;
import com.eraticate.game.Eraticate;

/**
 * Created by Ferenc on 5/18/2015.
 */
public abstract class RatScreen implements Screen
{
    protected Eraticate game;//Instance of the game class so we are able to access its methods (setScreen primarily)
    public abstract void update(float delta);

    public abstract void renderScreen(float delta);

    public RatScreen(Eraticate game)
    {
        this.game = game;
    }
    @Override
    public final void render(float delta)
    {
        update(delta);
        renderScreen(delta);
    }
}
