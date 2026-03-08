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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.factory.balldropnc81.Constants;
import com.factory.balldropnc81.MainGame;

public class HowToPlayScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final StretchViewport viewport;
    private final Stage stage;
    private final AssetManager manager;
    private final BitmapFont headerFont;
    private final BitmapFont sectionFont;
    private final BitmapFont bodyFont;
    private final BitmapFont buttonFont;
    private final Texture btnDark;
    private final Texture panelTex;
    private final Texture sectionBlue;
    private final Texture sectionGreen;
    private final Texture sectionYellow;
    private final Texture sectionRed;

    public HowToPlayScreen(MainGame game) {
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
        p2.color = new Color(0x1E88E5FF);
        sectionFont = robotoGen.generateFont(p2);
        FreeTypeFontParameter p3 = new FreeTypeFontParameter();
        p3.size = Constants.FONT_SIZE_SMALL;
        p3.color = new Color(0x2C3E50FF);
        bodyFont = robotoGen.generateFont(p3);
        FreeTypeFontParameter p4 = new FreeTypeFontParameter();
        p4.size = Constants.FONT_SIZE_BUTTON;
        buttonFont = robotoGen.generateFont(p4);
        robotoGen.dispose();

        btnDark    = solidTexture(new Color(0x2C3E50FF));
        panelTex   = solidTexture(new Color(1f, 1f, 1f, 0.95f));
        sectionBlue   = solidTexture(new Color(0x1E88E5FF).mul(1f, 1f, 1f, 0.12f));
        sectionGreen  = solidTexture(new Color(0x4CAF50FF).mul(1f, 1f, 1f, 0.12f));
        sectionYellow = solidTexture(new Color(0xFFC107FF).mul(1f, 1f, 1f, 0.12f));
        sectionRed    = solidTexture(new Color(0xFF6B6BFF).mul(1f, 1f, 1f, 0.12f));

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

    private Table buildSection(Texture bg, String heading, String body) {
        Label.LabelStyle headStyle = new Label.LabelStyle(sectionFont, new Color(0x1E88E5FF));
        Label.LabelStyle bodyStyle = new Label.LabelStyle(bodyFont, new Color(0x2C3E50FF));

        Label headLabel = new Label(heading, headStyle);
        Label bodyLabel = new Label(body, bodyStyle);
        bodyLabel.setWrap(true);

        Table section = new Table();
        section.setBackground(new TextureRegionDrawable(bg));
        section.pad(12f);
        section.add(headLabel).left().padBottom(6f).row();
        section.add(bodyLabel).left().width(260f);
        return section;
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

        Table section1 = buildSection(sectionBlue,
            "TAP TO DROP",
            "Press anywhere on the board to drop a ball from the top. Aim carefully!");

        Table section2 = buildSection(sectionGreen,
            "BOUNCE OFF PEGS",
            "Balls bounce off circular pegs as they fall. The path is unpredictable — that's the fun!");

        Table section3 = buildSection(sectionYellow,
            "SCORE ZONES",
            "Five zones at the bottom score points:\nRed = 10 pts  |  Yellow = 25 pts\nGreen = 50 pts  |  Blue = 100 pts");

        Table section4 = buildSection(sectionRed,
            "10 BALLS PER ROUND",
            "Each round gives you 10 balls. Once all are dropped and land, the round ends. Maximise your score!");

        Table contentTable = new Table();
        contentTable.pad(16f);
        contentTable.add(section1).width(300f).padBottom(16f).row();
        contentTable.add(section2).width(300f).padBottom(16f).row();
        contentTable.add(section3).width(300f).padBottom(16f).row();
        contentTable.add(section4).width(300f).row();

        Table panel = new Table();
        panel.setBackground(new TextureRegionDrawable(panelTex));
        panel.add(contentTable).pad(8f);

        ScrollPane scroll = new ScrollPane(panel);
        scroll.setScrollingDisabled(true, false);
        scroll.setFadeScrollBars(true);

        Table root = new Table();
        root.setFillParent(true);
        root.top().padTop(50f);
        root.add(new Label("HOW TO PLAY", headerStyle)).padBottom(24f).row();
        root.add(scroll).width(340f).height(550f).padBottom(24f).row();
        root.add(backBtn).size(Constants.BUTTON_WIDTH_LARGE, Constants.BUTTON_HEIGHT_MEDIUM).row();
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
        sectionFont.dispose();
        bodyFont.dispose();
        buttonFont.dispose();
        btnDark.dispose();
        panelTex.dispose();
        sectionBlue.dispose();
        sectionGreen.dispose();
        sectionYellow.dispose();
        sectionRed.dispose();
    }
}
