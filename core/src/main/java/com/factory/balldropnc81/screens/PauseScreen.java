package com.factory.balldropnc81.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.factory.balldropnc81.Constants;
import com.factory.balldropnc81.MainGame;

public class PauseScreen implements Screen {

    public static final int LAYOUT_CLASSIC = 0;
    public static final int LAYOUT_DIAMOND = 1;
    public static final int LAYOUT_ZIGZAG  = 2;

    private final MainGame game;
    private final Screen   previousScreen;
    private final int      layoutType;

    private final OrthographicCamera camera;
    private final StretchViewport    viewport;
    private final Stage              stage;

    private final BitmapFont headerFont;
    private final BitmapFont buttonFont;

    private final Texture overlayTex;
    private final Texture panelTex;
    private final Texture btnBlue;
    private final Texture btnGreen;
    private final Texture btnDark;

    public PauseScreen(MainGame game, Screen previousScreen, int layoutType) {
        this.game           = game;
        this.previousScreen = previousScreen;
        this.layoutType     = layoutType;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        FreeTypeFontGenerator orbitronGen = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_ORBITRON));
        FreeTypeFontParameter p = new FreeTypeFontParameter();
        p.size = Constants.FONT_SIZE_HEADER;
        headerFont = orbitronGen.generateFont(p);
        orbitronGen.dispose();

        FreeTypeFontGenerator robotoGen = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_ROBOTO));
        FreeTypeFontParameter p2 = new FreeTypeFontParameter();
        p2.size = Constants.FONT_SIZE_BUTTON;
        buttonFont = robotoGen.generateFont(p2);
        robotoGen.dispose();

        overlayTex = solidTexture(new Color(0f, 0f, 0f, 0.72f));
        panelTex   = solidTexture(new Color(1f, 1f, 1f, 0.97f));
        btnBlue    = solidTexture(new Color(0x1E88E5FF));
        btnGreen   = solidTexture(new Color(0x4CAF50FF));
        btnDark    = solidTexture(new Color(0x2C3E50FF));

        buildUI();

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    resumeGame();
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
        s.up        = new TextureRegionDrawable(bg);
        s.down      = new TextureRegionDrawable(bg);
        s.over      = new TextureRegionDrawable(bg);
        s.font      = buttonFont;
        s.fontColor = fontColor;
        return s;
    }

    private Screen createFreshGameScreen() {
        switch (layoutType) {
            case LAYOUT_DIAMOND: return new DiamondLayoutScreen(game);
            case LAYOUT_ZIGZAG:  return new ZigzagLayoutScreen(game);
            default:             return new ClassicLayoutScreen(game);
        }
    }

    private void resumeGame() {
        game.setScreen(previousScreen);
        // Do NOT dispose previousScreen — we're resuming it
    }

    private void buildUI() {
        // Full-screen semi-transparent overlay
        Table overlay = new Table();
        overlay.setFillParent(true);
        overlay.setBackground(new TextureRegionDrawable(overlayTex));
        stage.addActor(overlay);

        Label.LabelStyle headerStyle = new Label.LabelStyle(headerFont, new Color(0x2C3E50FF));

        TextButton resumeBtn  = new TextButton("RESUME",    btnStyle(btnGreen, Color.WHITE));
        TextButton restartBtn = new TextButton("RESTART",   btnStyle(btnBlue,  Color.WHITE));
        TextButton menuBtn    = new TextButton("MAIN MENU", btnStyle(btnDark,  Color.WHITE));

        resumeBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                resumeGame();
            }
        });
        restartBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                Screen fresh = createFreshGameScreen();
                game.setScreen(fresh);
                previousScreen.dispose();
            }
        });
        menuBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new MainMenuScreen(game));
                previousScreen.dispose();
            }
        });

        Table panel = new Table();
        panel.setBackground(new TextureRegionDrawable(panelTex));
        panel.pad(40f, 48f, 40f, 48f);
        panel.add(new Label("PAUSED", headerStyle)).padBottom(36f).row();
        panel.add(resumeBtn) .size(Constants.BUTTON_WIDTH_LARGE, Constants.BUTTON_HEIGHT_LARGE) .padBottom(16f).row();
        panel.add(restartBtn).size(Constants.BUTTON_WIDTH_LARGE, Constants.BUTTON_HEIGHT_LARGE) .padBottom(16f).row();
        panel.add(menuBtn)   .size(Constants.BUTTON_WIDTH_LARGE, Constants.BUTTON_HEIGHT_MEDIUM).row();

        Table root = new Table();
        root.setFillParent(true);
        root.add(panel);
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
        headerFont.dispose();
        buttonFont.dispose();
        overlayTex.dispose();
        panelTex.dispose();
        btnBlue.dispose();
        btnGreen.dispose();
        btnDark.dispose();
    }
}
