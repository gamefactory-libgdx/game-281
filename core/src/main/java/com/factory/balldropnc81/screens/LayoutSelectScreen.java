package com.factory.balldropnc81.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class LayoutSelectScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final StretchViewport viewport;
    private final Stage stage;
    private final AssetManager manager;
    private final BitmapFont headerFont;
    private final BitmapFont cardLabelFont;
    private final BitmapFont cardBodyFont;
    private final BitmapFont buttonFont;
    private final Texture btnDark;
    private final Texture cardBg;
    private final Texture cardBorderBlue;
    private final Texture cardBorderGreen;
    private final Texture cardBorderYellow;
    private final Texture pegTex;

    public LayoutSelectScreen(MainGame game) {
        this.game = game;
        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        manager = new AssetManager();
        manager.load(Constants.BG_MAIN, Texture.class);
        manager.finishLoading();

        FreeTypeFontGenerator orbitronGen = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_ORBITRON));
        FreeTypeFontParameter p = new FreeTypeFontParameter();
        p.size = 40;
        headerFont = orbitronGen.generateFont(p);
        orbitronGen.dispose();

        FreeTypeFontGenerator robotoGen = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_ROBOTO));
        FreeTypeFontParameter p2 = new FreeTypeFontParameter();
        p2.size = 22;
        cardLabelFont = robotoGen.generateFont(p2);
        FreeTypeFontParameter p3 = new FreeTypeFontParameter();
        p3.size = Constants.FONT_SIZE_SMALL;
        p3.color = new Color(0x2C3E50FF).mul(1f, 1f, 1f, 0.7f);
        cardBodyFont = robotoGen.generateFont(p3);
        FreeTypeFontParameter p4 = new FreeTypeFontParameter();
        p4.size = Constants.FONT_SIZE_BUTTON;
        buttonFont = robotoGen.generateFont(p4);
        robotoGen.dispose();

        btnDark          = solidTexture(new Color(0x2C3E50FF));
        cardBg           = solidTexture(new Color(1f, 1f, 1f, 0.97f));
        cardBorderBlue   = solidTexture(new Color(0x1E88E5FF));
        cardBorderGreen  = solidTexture(new Color(0x4CAF50FF));
        cardBorderYellow = solidTexture(new Color(0xFFC107FF));
        pegTex           = solidTexture(new Color(0x2C3E50FF));

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

    private TextButton buildCard(Texture borderColor, String title, String description) {
        Label.LabelStyle titleStyle = new Label.LabelStyle(cardLabelFont, new Color(0x2C3E50FF));
        Label.LabelStyle descStyle  = new Label.LabelStyle(cardBodyFont,  new Color(0x2C3E50FF));

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up        = new TextureRegionDrawable(borderColor);
        style.down      = new TextureRegionDrawable(borderColor);
        style.over      = new TextureRegionDrawable(borderColor);
        style.font      = cardLabelFont;
        style.fontColor = Color.CLEAR; // hide default text; we add custom table inside

        // We build a nested table inside a TextButton container
        // Use a simple TextButton with label, but wrap it visually
        TextButton.TextButtonStyle cardStyle = new TextButton.TextButtonStyle();
        cardStyle.up        = new TextureRegionDrawable(cardBg);
        cardStyle.down      = new TextureRegionDrawable(cardBg);
        cardStyle.over      = new TextureRegionDrawable(cardBg);
        cardStyle.font      = cardLabelFont;
        cardStyle.fontColor = Color.CLEAR;

        // Create a simple table that acts as card interior
        Table inner = new Table();
        inner.pad(16f);
        inner.add(new Label(title, titleStyle)).padBottom(8f).row();
        inner.add(new Label(description, descStyle)).row();

        // Outer border container
        Table card = new Table();
        card.setBackground(new TextureRegionDrawable(borderColor));
        card.pad(2f);
        card.add(inner).fill().expand();

        // Wrap in a TextButton (we use the table approach via a custom container)
        // Since Scene2D doesn't support adding widgets inside TextButton easily,
        // we use a Container + Table pattern with a click listener added directly.
        // Return a placeholder TextButton pointing to the table trick below.
        // The caller adds a ChangeListener; we return the card in a group.
        TextButton btn = new TextButton("", new TextButton.TextButtonStyle());
        btn.getStyle().up   = new TextureRegionDrawable(borderColor);
        btn.getStyle().down = new TextureRegionDrawable(borderColor);
        btn.getStyle().over = new TextureRegionDrawable(borderColor);
        btn.getStyle().font = cardLabelFont;
        btn.clearChildren();
        btn.add(inner).fill().expand();
        return btn;
    }

    private void buildUI() {
        Image bgImage = new Image(manager.get(Constants.BG_MAIN, Texture.class));
        bgImage.setFillParent(true);
        stage.addActor(bgImage);

        Label.LabelStyle headerStyle = new Label.LabelStyle(headerFont, new Color(0x2C3E50FF));

        TextButton.TextButtonStyle backStyle = new TextButton.TextButtonStyle();
        backStyle.up        = new TextureRegionDrawable(btnDark);
        backStyle.down      = new TextureRegionDrawable(btnDark);
        backStyle.over      = new TextureRegionDrawable(btnDark);
        backStyle.font      = buttonFont;
        backStyle.fontColor = Color.WHITE;

        TextButton backBtn = new TextButton("< BACK", backStyle);
        backBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        TextButton classicBtn = buildCard(cardBorderBlue,   "CLASSIC",  "Evenly spaced grid layout");
        TextButton diamondBtn = buildCard(cardBorderGreen,  "DIAMOND",  "Diamond-shaped peg formation");
        TextButton zigzagBtn  = buildCard(cardBorderYellow, "ZIGZAG",   "Alternating offset rows");

        classicBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new ClassicLayoutScreen(game));
            }
        });
        diamondBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new DiamondLayoutScreen(game));
            }
        });
        zigzagBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new ZigzagLayoutScreen(game));
            }
        });

        Table root = new Table();
        root.setFillParent(true);
        root.top().padTop(60f);
        root.add(new Label("SELECT LAYOUT", headerStyle)).padBottom(40f).row();
        root.add(classicBtn).size(Constants.CARD_WIDTH, Constants.CARD_HEIGHT).padBottom(Constants.CARD_SPACING).row();
        root.add(diamondBtn).size(Constants.CARD_WIDTH, Constants.CARD_HEIGHT).padBottom(Constants.CARD_SPACING).row();
        root.add(zigzagBtn) .size(Constants.CARD_WIDTH, Constants.CARD_HEIGHT).padBottom(40f).row();
        root.add(backBtn)   .size(Constants.BUTTON_WIDTH_LARGE, Constants.BUTTON_HEIGHT_MEDIUM).row();
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
        cardLabelFont.dispose();
        cardBodyFont.dispose();
        buttonFont.dispose();
        btnDark.dispose();
        cardBg.dispose();
        cardBorderBlue.dispose();
        cardBorderGreen.dispose();
        cardBorderYellow.dispose();
        pegTex.dispose();
    }
}
