package com.eraticate.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.eraticate.game.Eraticate;
import com.eraticate.game.RatCamera;

/**
 * Created by Ferenc on 5/21/2015.
 */
public class GameScreen extends RatScreen implements InputProcessor
{
    private final Eraticate game; //Instance of the game class so we are able to access its methods (setScreen primarily)
    private Batch batch; //The object handling the render
    RatCamera camera; //To select a part of our map to look at

    Viewport viewport; //The image "taken" by the camera needs to be handled
    float aspectRatio;

    private TiledMap map;
    private TiledMapTileLayer collLayer;
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
        map = new TmxMapLoader().load("maps/demomap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new RatCamera(0.5f, 1.5f, 3200);
        viewport = new FitViewport(960, Gdx.graphics.getHeight(), camera);
        camera.moveBy(0, 0);
    }

    @Override
    public void update(float delta)
    {
        camera.update();
    }
    @Override
    public void renderScreen(float delta)
    {
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
        camera.calcCameraBoundaries();
//        cameraMoveTo(0, 0);
        viewport.update(viewport.getScreenWidth(), viewport.getScreenHeight());

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
