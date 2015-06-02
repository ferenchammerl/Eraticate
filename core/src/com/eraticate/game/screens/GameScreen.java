package com.eraticate.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.input.GestureDetector;
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
public class GameScreen extends RatScreen implements GestureDetector.GestureListener
{
    private Batch batch; //The object handling the render
    RatCamera camera; //To select a part of our map to look at

    Viewport mapViewport; //The image "taken" by the camera needs to be handled

    RatWorld ratWorld;
    private OrthogonalTiledMapRenderer mapRenderer;


    public GameScreen(Eraticate game)
    {
        super(game);
        batch = game.getBatch();
        Gdx.input.setInputProcessor(new GestureDetector(this));
    }
    @Override
    public void show()
    {
        ratWorld = new RatWorld(new TmxMapLoader().load("maps/demomap.tmx"));
        ratWorld.initRats(0.11f);
        mapRenderer = new OrthogonalTiledMapRenderer(ratWorld.getMap());
        float mapWidth = ratWorld.getMap().getProperties().get("width", Integer.class) * 64;

        float closestZoom = Gdx.graphics.getWidth() > 1280 ? 0.5f : 0.8f;
        float farthestZoom = Gdx.graphics.getWidth() > 1280 ? 1.2f : 1.5f;
        camera = new RatCamera(closestZoom, farthestZoom, mapWidth);
        mapViewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        camera.moveBy(0, 0);
    }

    @Override
    public void update(float delta)
    {
        camera.update();
        switch (ratWorld.update(delta))
        {
            case Continue:
                break;
            case Defeat:
                game.setScreen(new MainMenuScreen(this.game));
                break;
            case Victory:
                game.setScreen(new MainMenuScreen(this.game));
                break;
        }
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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button)
    {
        return false;
    }
    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        Vector3 vec = new Vector3(x, y, 0);
        camera.unproject(vec);
        if (ratWorld.executed(vec.x, vec.y))
            Gdx.input.vibrate(200);
        return true;
    }
    @Override
    public boolean longPress(float x, float y)
    {
        return false;
    }
    @Override
    public boolean fling(float velocityX, float velocityY, int button)
    {
        return false;
    }
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
        camera.moveBy(-deltaX, deltaY);

        return true;
    }
    @Override
    public boolean panStop(float x, float y, int pointer, int button)
    {
        return false;
    }
    @Override
    public boolean zoom(float initialDistance, float distance)
    {
        camera.setZoom((float) (camera.zoom * initialDistance / distance));

        return false;
    }
    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        return false;
    }
}
