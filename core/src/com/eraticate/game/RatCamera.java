package com.eraticate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Ferenc on 5/30/2015.
 */
public class RatCamera extends OrthographicCamera
{
    private float minZoom;
    private float maxZoom;
    private final float zoomSpeed = 0.25f;

    private float worldWidth;
    float xMin, xMax, yMin, yMax; //Boundaries of the map


    public void setZoom(float zoom)
    {
        zoom = zoom < minZoom ? minZoom : zoom > maxZoom ? maxZoom : zoom;
        this.zoom = zoom;
        calcCameraBoundaries();
        moveBy(0, 0);
    }


    public RatCamera(float minZoom, float maxZoom, float worldWidth)
    {
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.worldWidth = worldWidth;
    }

    public void zoomIn()
    {
        Gdx.app.log("zoom", String.valueOf(zoom));
        setZoom(zoom - zoomSpeed);
        calcCameraBoundaries();
    }
    public void zoomOut()
    {
        Gdx.app.log("zoom", String.valueOf(zoom));
        setZoom(zoom + zoomSpeed);
        calcCameraBoundaries();
    }


    public void calcCameraBoundaries()
    {
        xMin = viewportWidth / 2 * zoom;
        xMax = worldWidth - xMin;
        yMin = viewportHeight / 2 * zoom;
        yMax = worldWidth - yMin;
        moveBy(0, 0);

    }
    public void moveBy(float x, float y)
    {
        x *= zoom;
        y *= zoom;
        x += position.x;
        y += position.y;
        if (x < xMin) x = xMin;
        if (x > xMax) x = xMax;

        if (y < yMin) y = yMin;
        if (y > yMax) y = yMax;

        position.set(x, y, 0);
    }

}
