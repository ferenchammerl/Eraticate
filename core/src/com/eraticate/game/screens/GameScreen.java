package com.eraticate.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.eraticate.game.Eraticate;
import com.eraticate.game.RatCamera;
import com.eraticate.game.RatWorld;

/**
 * Created by Ferenc on 5/21/2015.
 */
public class GameScreen extends RatScreen implements InputProcessor
{
    private final Eraticate game; //Instance of the game class so we are able to access its methods (setScreen primarily)
    private Batch batch; //The object handling the render
    RatCamera camera; //To select a part of our map to look at

    Viewport mapViewport; //The image "taken" by the camera needs to be handled

    RatWorld ratWorld;
    private OrthogonalTiledMapRenderer mapRenderer;


    public GameScreen(Eraticate game)
    {
        this.game = game;
        batch = game.getBatch();
        Gdx.input.setInputProcessor(this);
    }
    @Override
    public void show()
    {
        ratWorld = new RatWorld(new TmxMapLoader().load("maps/demomap.tmx"));
        ratWorld.initRats(0.11f);
        mapRenderer = new OrthogonalTiledMapRenderer(ratWorld.getMap());

        camera = new RatCamera(0.5f, 2f, 3200);
        mapViewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        camera.moveBy(0, 0);
    }

    @Override
    public void update(float delta)
    {
        camera.update();
        ratWorld.update(delta);
    }
    @Override
    public void renderScreen(float delta)
    {
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //We let the map take care of itself
        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        ratWorld.Draw(batch);

        batch.end();
    }
    @Override
    public void resize(int width, int height)
    {
        mapViewport.update(width, height);
        camera.calcCameraBoundaries();
        mapViewport.update(mapViewport.getScreenWidth(), mapViewport.getScreenHeight());

    }

    @Override
    public void pause()
    {

    }
    @Override
    public void resume()
    {

    }
    @Override
    public void hide()
    {

    }
    @Override
    public void dispose()
    {

    }

    //INPUT METHODS
    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == Keys.PLUS)
        {
            camera.zoomIn();
        }
        if (keycode == Keys.F)
        {

        }
        if (keycode == Keys.MINUS)
        {
            camera.zoomOut();
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }
    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        lastPos.set(screenX, screenY);

        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            Vector3 vec = new Vector3(screenX, screenY, 0);
            camera.unproject(vec);
            ratWorld.tapped(vec.x, vec.y);

        }
        return true;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }
    Vector2 lastPos = new Vector2();
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        camera.moveBy((lastPos.x - screenX), (screenY - lastPos.y));
        lastPos.set(screenX, screenY);

        return true;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }
    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }


}
