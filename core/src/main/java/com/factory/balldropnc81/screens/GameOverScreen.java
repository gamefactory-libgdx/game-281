package com.factory.balldropnc81.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
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

public class GameOverScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final StretchViewport viewport;
    private final Stage stage;
    private final AssetManager manager;
    private final BitmapFont headerFont;
    private final BitmapFont scoreFont;
    private final BitmapFont bodyFont;
    private final BitmapFont buttonFont;
    private final Texture btnBlue;
    private final Texture btnDark;
    private final Texture panelTex;

    public GameOverScreen(MainGame game, int score, int extra) {
        this.game = game;
        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        manager = new AssetManager();
        manager.load(Constants.BG_RESULTS, Texture.class);
        manager.finishLoading();

        FreeTypeFontGenerator orbitronGen = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_ORBITRON));
        FreeTypeFontParameter p = new FreeTypeFontParameter();
        p.size = Constants.FONT_SIZE_HEADER;
        headerFont = orbitronGen.generateFont(p);
        orbitronGen.dispose();

        FreeTypeFontGenerator robotoGen = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_ROBOTO));
        FreeTypeFontParameter p2 = new FreeTypeFontParameter();
        p2.size = 32;
        scoreFont = robotoGen.generateFont(p2);
        FreeTypeFontParameter p3 = new FreeTypeFontParameter();
        p3.size = Constants.FONT_SIZE_BODY;
        bodyFont = robotoGen.generateFont(p3);
        FreeTypeFontParameter p4 = new FreeTypeFontParameter();
        p4.size = Constants.FONT_SIZE_BUTTON;
        buttonFont = robotoGen.generateFont(p4);
        robotoGen.dispose();

        btnBlue  = solidTexture(new Color(0x1E88E5FF));
        btnDark  = solidTexture(new Color(0x2C3E50FF));
        panelTex = solidTexture(new Color(1f, 1f, 1f, 0.95f));

        // Persist high score and leaderboard
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        int highScore = prefs.getInteger(Constants.KEY_HIGH_SCORE, 0);
        boolean newRecord = score > highScore;
        if (newRecord) {
            prefs.putInteger(Constants.KEY_HIGH_SCORE, score);
            prefs.flush();
        }
        LeaderboardScreen.addScore(score);

        int displayHighScore = newRecord ? score : highScore;

        buildUI(score, displayHighScore, extra, newRecord);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.setScreen(new MainMenuScreen(game));
                    return true;
                }
                return false;
            }
        }));
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

    private void buildUI(int score, int highScore, int roundsPlayed, boolean newRecord) {
        Image bg = new Image(manager.get(Constants.BG_RESULTS, Texture.class));
        bg.setFillParent(true);
        stage.addActor(bg);

        Label.LabelStyle headerStyle = new Label.LabelStyle(headerFont, new Color(0x2C3E50FF));
        Label.LabelStyle scoreStyle  = new Label.LabelStyle(scoreFont,  new Color(0xFFC107FF));
        Label.LabelStyle bestStyle   = new Label.LabelStyle(scoreFont,  new Color(0x4CAF50FF));
        Label.LabelStyle bodyStyle   = new Label.LabelStyle(bodyFont,   new Color(0x2C3E50FF));
        Label.LabelStyle recordStyle = new Label.LabelStyle(bodyFont,   new Color(0xFF6B6BFF));

        Table panel = new Table();
        panel.setBackground(new TextureRegionDrawable(panelTex));
        panel.pad(28f);
        panel.add(new Label("TOTAL SCORE: " + score,          scoreStyle)).padBottom(16f).row();
        panel.add(new Label("HIGH SCORE:  " + highScore,      bestStyle)) .padBottom(16f).row();
        panel.add(new Label("ROUNDS PLAYED: " + roundsPlayed, bodyStyle)) .padBottom(newRecord ? 8f : 0f).row();
        if (newRecord) {
            panel.add(new Label("NEW RECORD!", recordStyle)).row();
        }

        TextButton retryBtn = new TextButton("RESTART",   btnStyle(btnBlue, Color.WHITE));
        TextButton menuBtn  = new TextButton("MAIN MENU", btnStyle(btnDark, Color.WHITE));

        retryBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new LayoutSelectScreen(game));
            }
        });
        menuBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table root = new Table();
        root.setFillParent(true);
        root.top().padTop(60f);
        root.add(new Label("SESSION OVER", headerStyle)).padBottom(32f).row();
        root.add(panel).width(Constants.RESULTS_CARD_WIDTH).padBottom(36f).row();
        root.add(retryBtn).size(Constants.BUTTON_WIDTH_LARGE, Constants.BUTTON_HEIGHT_LARGE).padBottom(16f).row();
        root.add(menuBtn) .size(Constants.BUTTON_WIDTH_LARGE, Constants.BUTTON_HEIGHT_MEDIUM).row();
        stage.addActor(root);
    }

    @Override public void show() {}

    @Override public void render(float delta) {
        ScreenUtils.clear(0.96f, 0.96f, 0.96f, 1f);
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
        headerFont.dispose();
        scoreFont.dispose();
        bodyFont.dispose();
        buttonFont.dispose();
        btnBlue.dispose();
        btnDark.dispose();
        panelTex.dispose();
    }
}
