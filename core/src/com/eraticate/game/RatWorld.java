package com.eraticate.game;

import com.eraticate.game.mapobjects.Rat;

/**
 * Created by Ferenc on 5/30/2015.
 */
public class RatWorld
{
    private void initRats()
    {
        for (int i = 1; i <= collLayer.getHeight(); i++)
        {
            for (int j = 1; j <= collLayer.getWidth(); j++)
            {
                if (collLayer.getCell(i, j) == null) continue;
                if (Math.random() < 0.15) rats.add(new Rat(i, j, collLayer));

            }
        }
    }
    public void Draw()
}
