package com.eraticate.game.gamescreenobjects;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

/**
 * Created by Ferenc on 6/2/2015.
 */
public class RatMenu
{
    ArrayList<MenuItem> items = new ArrayList<MenuItem>();

    public void addMenuItem(MenuItem item)
    {
        item.setxPos(items.size() % 2);
        item.setyPos(items.size() / 2);
        items.add(item);
    }

    public void draw(Batch batch) {for (MenuItem item : items) {
        item.draw(batch, 160, 160);}}
}
