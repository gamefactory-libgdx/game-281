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

public class SettingsScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final StretchViewport viewport;
    private final Stage stage;
    private final AssetManager manager;
    private final BitmapFont headerFont;
    private final BitmapFont labelFont;
    private final BitmapFont buttonFont;
    private final Texture btnBlue;
    private final Texture btnGreen;
    private final Texture btnGray;
    private final Texture btnDark;

    private boolean musicOn;
    private boolean soundOn;
    private TextButton musicToggle;
    private TextButton soundToggle;

    public SettingsScreen(MainGame game) {
        this.game = game;
        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        manager = new AssetManager();
        manager.load(Constants.BG_MAIN, Texture.class);
        manager.finishLoading();

        FreeTypeFontGenerator orbitronGen = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_ORBITRON));
        FreeTypeFontParameter p = new FreeTypeFontParameter();
        p.size = Constants.FONT_SIZE_HEADER;
        headerFont = orbitronGen.generateFont(p);
        orbitronGen.dispose();

        FreeTypeFontGenerator robotoGen = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_ROBOTO));
        FreeTypeFontParameter p2 = new FreeTypeFontParameter();
        p2.size = Constants.FONT_SIZE_BODY;
        labelFont = robotoGen.generateFont(p2);
        FreeTypeFontParameter p3 = new FreeTypeFontParameter();
        p3.size = Constants.FONT_SIZE_BUTTON;
        buttonFont = robotoGen.generateFont(p3);
        robotoGen.dispose();

        btnBlue  = solidTexture(new Color(0x1E88E5FF));
        btnGreen = solidTexture(new Color(0x4CAF50FF));
        btnGray  = solidTexture(new Color(0xD0D0D0FF));
        btnDark  = solidTexture(new Color(0x2C3E50FF));

        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        musicOn = prefs.getBoolean(Constants.KEY_MUSIC_ENABLED, true);
        soundOn = prefs.getBoolean(Constants.KEY_SOUND_ENABLED, true);

        buildUI();

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

    private TextButton.TextButtonStyle toggleStyle(boolean on) {
        return btnStyle(on ? btnGreen : btnGray, on ? Color.WHITE : new Color(0x2C3E50FF));
    }

    private void buildUI() {
        Image bg = new Image(manager.get(Constants.BG_MAIN, Texture.class));
        bg.setFillParent(true);
        stage.addActor(bg);

        Label.LabelStyle headerStyle = new Label.LabelStyle(headerFont, new Color(0x2C3E50FF));
        Label.LabelStyle labelStyle  = new Label.LabelStyle(labelFont,  new Color(0x2C3E50FF));

        musicToggle = new TextButton(musicOn ? "ON" : "OFF", toggleStyle(musicOn));
        soundToggle = new TextButton(soundOn ? "ON" : "OFF", toggleStyle(soundOn));

        musicToggle.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                musicOn = !musicOn;
                musicToggle.setText(musicOn ? "ON" : "OFF");
                musicToggle.setStyle(toggleStyle(musicOn));
                Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
                prefs.putBoolean(Constants.KEY_MUSIC_ENABLED, musicOn);
                prefs.flush();
            }
        });
        soundToggle.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                soundOn = !soundOn;
                soundToggle.setText(soundOn ? "ON" : "OFF");
                soundToggle.setStyle(toggleStyle(soundOn));
                Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
                prefs.putBoolean(Constants.KEY_SOUND_ENABLED, soundOn);
                prefs.flush();
            }
        });

        TextButton menuBtn = new TextButton("MAIN MENU", btnStyle(btnBlue, Color.WHITE));
        menuBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table panel = new Table();
        panel.setBackground(new TextureRegionDrawable(solidTexture(new Color(1f, 1f, 1f, 0.92f))));
        panel.pad(24f);

        panel.add(new Label("MUSIC",  labelStyle)).left().expandX().padBottom(8f);
        panel.add(musicToggle).size(Constants.TOGGLE_WIDTH * 2f, Constants.SETTINGS_ROW_HEIGHT - 16f).padBottom(8f).row();
        panel.add(new Label("SOUND",  labelStyle)).left().expandX().padBottom(8f);
        panel.add(soundToggle).size(Constants.TOGGLE_WIDTH * 2f, Constants.SETTINGS_ROW_HEIGHT - 16f).padBottom(8f).row();

        Table root = new Table();
        root.setFillParent(true);
        root.top().padTop(60f);
        root.add(new Label("SETTINGS", headerStyle)).padBottom(40f).row();
        root.add(panel).width(Constants.SETTINGS_PANEL_WIDTH).padBottom(40f).row();
        root.add(menuBtn).size(Constants.BUTTON_WIDTH_LARGE, Constants.BUTTON_HEIGHT_LARGE).row();
        stage.addActor(root);
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
        headerFont.dispose();
        labelFont.dispose();
        buttonFont.dispose();
        btnBlue.dispose();
        btnGreen.dispose();
        btnGray.dispose();
        btnDark.dispose();
    }
}
