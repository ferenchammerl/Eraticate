package com.eraticate.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.eraticate.game.Eraticate;


public class MainMenuScreen extends RatScreen
{
    private Batch batch;

    private Stage stage;
    private Skin skin = new Skin(Gdx.files.internal("textures/mainmenu/menuSkin.json"),
            new TextureAtlas(Gdx.files.internal("textures/mainmenu/menuSkin.pack")));

    private Table table = new Table(skin);

    private Label title = new Label("Eraticate", skin);

    private TextButton buttonPlay = new TextButton("Play", skin),
            buttonQuit = new TextButton("Quit", skin);

    private ImageButton buttonMusic = new ImageButton(skin);

    Music mainTheme;


    public MainMenuScreen(Eraticate game)
    {
        super(game);
        this.batch = game.getBatch();
        this.stage = new Stage(new StretchViewport(800, 480), game.getBatch());
        this.mainTheme = Gdx.audio.newMusic(Gdx.files.internal("audio/music/mainmenu.wav"));
        mainTheme.setLooping(true);
        mainTheme.play();

    }

    @Override
    public void update(float delta)
    {

    }
    @Override
    public void renderScreen(float delta)
    {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.end();

        stage.act();
        stage.draw();
    }


    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void show()
    {
        buttonPlay.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new GameScreen(game));
            }
        });
        buttonQuit.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });

        buttonMusic.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                mainTheme.setVolume(buttonMusic.isChecked() ? 0 : 1);
            }
        });

        //The elements are displayed in the order you add them.
        //The first appear on top, the last at the bottom.
        table.setBackground("bg");
        table.add(buttonMusic).expand().right().top().row();
        table.add(title).padBottom(40).row();
        table.add(buttonPlay).size(150, 60).padBottom(20).row();
        table.add(buttonQuit).size(150, 60).padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide()
    {
        dispose();
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
    public void dispose()
    {
        stage.dispose();
        skin.dispose();
    }


}