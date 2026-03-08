package com.factory.balldropnc81.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.factory.balldropnc81.Constants;
import com.factory.balldropnc81.MainGame;

public class MainMenuScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final StretchViewport viewport;
    private final Stage stage;
    private final AssetManager manager;
    private final BitmapFont titleFont;
    private final BitmapFont subtitleFont;
    private final BitmapFont buttonFont;
    private final Texture btnBlue;
    private final Texture btnYellow;
    private final Texture btnGreen;
    private final Texture btnDark;

    public MainMenuScreen(MainGame game) {
        this.game = game;
        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        manager = new AssetManager();
        manager.load(Constants.BG_MAIN, Texture.class);
        manager.finishLoading();

        FreeTypeFontGenerator orbitronGen = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_ORBITRON));
        FreeTypeFontParameter p = new FreeTypeFontParameter();
        p.size = Constants.FONT_SIZE_TITLE;
        titleFont = orbitronGen.generateFont(p);
        orbitronGen.dispose();

        FreeTypeFontGenerator robotoGen = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_ROBOTO));
        FreeTypeFontParameter p2 = new FreeTypeFontParameter();
        p2.size = Constants.FONT_SIZE_BODY;
        subtitleFont = robotoGen.generateFont(p2);
        FreeTypeFontParameter p3 = new FreeTypeFontParameter();
        p3.size = Constants.FONT_SIZE_BUTTON;
        buttonFont = robotoGen.generateFont(p3);
        robotoGen.dispose();

        btnBlue   = solidTexture(new Color(0x1E88E5FF));
        btnYellow = solidTexture(new Color(0xFFC107FF));
        btnGreen  = solidTexture(new Color(0x4CAF50FF));
        btnDark   = solidTexture(new Color(0x2C3E50FF));

        buildUI();

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {}));
    }

    private Texture solidTexture(Color color) {
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(color);
        pm.fill();
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }

    private TextButton.TextButtonStyle btnStyle(Texture bg, Color fontColor) {
        TextButton.TextButtonStyle s = new TextButton.TextButtonStyle();
        s.up       = new TextureRegionDrawable(bg);
        s.down     = new TextureRegionDrawable(bg);
        s.over     = new TextureRegionDrawable(bg);
        s.font     = buttonFont;
        s.fontColor = fontColor;
        return s;
    }

    private void buildUI() {
        Image bg = new Image(manager.get(Constants.BG_MAIN, Texture.class));
        bg.setFillParent(true);
        stage.addActor(bg);

        Label.LabelStyle titleStyle    = new Label.LabelStyle(titleFont,    new Color(0x2C3E50FF));
        Label.LabelStyle subtitleStyle = new Label.LabelStyle(subtitleFont, new Color(0x1E88E5FF));

        TextButton playBtn     = new TextButton("PLAY",         btnStyle(btnBlue,   Color.WHITE));
        TextButton leaderBtn   = new TextButton("LEADERBOARD",  btnStyle(btnYellow, new Color(0x2C3E50FF)));
        TextButton howToBtn    = new TextButton("HOW TO PLAY",  btnStyle(btnGreen,  Color.WHITE));
        TextButton settingsBtn = new TextButton("SETTINGS",     btnStyle(btnDark,   Color.WHITE));

        playBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new LayoutSelectScreen(game));
            }
        });
        leaderBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new LeaderboardScreen(game));
            }
        });
        howToBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new HowToPlayScreen(game));
            }
        });
        settingsBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new SettingsScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.top().padTop(80f);
        table.add(new Label("BALL DROP",           titleStyle)).padBottom(8f).row();
        table.add(new Label("Tap. Bounce. Score.", subtitleStyle)).padBottom(80f).row();
        table.add(playBtn)    .size(Constants.BUTTON_WIDTH_LARGE,  Constants.BUTTON_HEIGHT_LARGE) .padBottom(20f).row();
        table.add(leaderBtn)  .size(Constants.BUTTON_WIDTH_LARGE,  Constants.BUTTON_HEIGHT_MEDIUM).padBottom(20f).row();
        table.add(howToBtn)   .size(Constants.BUTTON_WIDTH_LARGE,  Constants.BUTTON_HEIGHT_MEDIUM).padBottom(20f).row();
        table.add(settingsBtn).size(Constants.BUTTON_WIDTH_LARGE,  Constants.BUTTON_HEIGHT_MEDIUM).row();
        stage.addActor(table);
    }

    @Override public void show() {}

    @Override public void render(float delta) {
        ScreenUtils.clear(0.12f, 0.23f, 0.31f, 1f);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h) { viewport.update(w, h, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override public void dispose() {
        stage.dispose();
        manager.dispose();
        titleFont.dispose();
        subtitleFont.dispose();
        buttonFont.dispose();
        btnBlue.dispose();
        btnYellow.dispose();
        btnGreen.dispose();
        btnDark.dispose();
    }
}
