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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.factory.balldropnc81.Constants;
import com.factory.balldropnc81.MainGame;

import java.util.Arrays;

public class LeaderboardScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final StretchViewport viewport;
    private final Stage stage;
    private final AssetManager manager;
    private final BitmapFont headerFont;
    private final BitmapFont rankFont;
    private final BitmapFont rowFont;
    private final BitmapFont buttonFont;
    private final Texture btnBlue;
    private final Texture rowEven;
    private final Texture rowOdd;
    private final Texture rowGold;

    // ── Static helpers ──────────────────────────────────────────────────────

    public static void addScore(int score) {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        String raw = prefs.getString(Constants.KEY_LEADERBOARD, "");
        int[] current = parseScores(raw);
        int[] updated = insertScore(current, score);
        prefs.putString(Constants.KEY_LEADERBOARD, joinScores(updated));
        prefs.flush();
    }

    private static int[] parseScores(String raw) {
        if (raw == null || raw.isEmpty()) return new int[0];
        String[] parts = raw.split(",");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            try { arr[i] = Integer.parseInt(parts[i].trim()); }
            catch (NumberFormatException ignored) { arr[i] = 0; }
        }
        return arr;
    }

    private static int[] insertScore(int[] existing, int newScore) {
        int[] all = Arrays.copyOf(existing, existing.length + 1);
        all[existing.length] = newScore;
        Arrays.sort(all);
        // Reverse to descending order
        for (int i = 0, j = all.length - 1; i < j; i++, j--) {
            int tmp = all[i]; all[i] = all[j]; all[j] = tmp;
        }
        return Arrays.copyOf(all, Math.min(all.length, Constants.LEADERBOARD_SIZE));
    }

    private static String joinScores(int[] scores) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < scores.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(scores[i]);
        }
        return sb.toString();
    }

    // ── Constructor ─────────────────────────────────────────────────────────

    public LeaderboardScreen(MainGame game) {
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
        p2.size = Constants.FONT_SIZE_HUD;
        rankFont = robotoGen.generateFont(p2);
        FreeTypeFontParameter p3 = new FreeTypeFontParameter();
        p3.size = Constants.FONT_SIZE_BODY;
        rowFont = robotoGen.generateFont(p3);
        FreeTypeFontParameter p4 = new FreeTypeFontParameter();
        p4.size = Constants.FONT_SIZE_BUTTON;
        buttonFont = robotoGen.generateFont(p4);
        robotoGen.dispose();

        btnBlue = solidTexture(new Color(0x1E88E5FF));
        rowEven = solidTexture(new Color(0xF5F5F5FF));
        rowOdd  = solidTexture(new Color(0xFFFFFFFF));
        rowGold = solidTexture(new Color(0xFFC107FF).mul(1f, 1f, 1f, 0.15f));

        int[] scores = loadScores();
        buildUI(scores);

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

    private int[] loadScores() {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        return parseScores(prefs.getString(Constants.KEY_LEADERBOARD, ""));
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

    private void buildUI(int[] scores) {
        Image bg = new Image(manager.get(Constants.BG_MAIN, Texture.class));
        bg.setFillParent(true);
        stage.addActor(bg);

        Label.LabelStyle headerStyle = new Label.LabelStyle(headerFont, new Color(0x2C3E50FF));
        Label.LabelStyle rankStyle   = new Label.LabelStyle(rankFont,   new Color(0xFFC107FF));
        Label.LabelStyle scoreStyle  = new Label.LabelStyle(rowFont,    new Color(0x1E88E5FF));
        Label.LabelStyle emptyStyle  = new Label.LabelStyle(rowFont,    new Color(0xAAAAAFFF));

        Table listTable = new Table();
        for (int i = 0; i < Constants.LEADERBOARD_SIZE; i++) {
            Texture rowBg = (i == 0) ? rowGold : (i % 2 == 0 ? rowEven : rowOdd);
            Table row = new Table();
            row.setBackground(new TextureRegionDrawable(rowBg));
            row.pad(8f, 16f, 8f, 16f);

            Label rankLabel = new Label("#" + (i + 1), rankStyle);
            if (i < scores.length) {
                Label scoreLabel = new Label(String.valueOf(scores[i]), scoreStyle);
                row.add(rankLabel).left().expandX();
                row.add(scoreLabel).right();
            } else {
                Label dashLabel = new Label("—", emptyStyle);
                row.add(rankLabel).left().expandX();
                row.add(dashLabel).right();
            }
            listTable.add(row).width(Constants.LEADERBOARD_WIDTH).height(Constants.LEADERBOARD_ROW_H).row();
        }

        ScrollPane scroll = new ScrollPane(listTable);
        scroll.setScrollingDisabled(true, false);
        scroll.setFadeScrollBars(true);

        TextButton menuBtn = new TextButton("MAIN MENU", btnStyle(btnBlue, Color.WHITE));
        menuBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table root = new Table();
        root.setFillParent(true);
        root.top().padTop(60f);
        root.add(new Label("TOP 10 SCORES", headerStyle)).padBottom(32f).row();
        root.add(scroll).width(Constants.LEADERBOARD_WIDTH).height(Constants.LEADERBOARD_ROW_H * Constants.LEADERBOARD_SIZE).padBottom(36f).row();
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
        rankFont.dispose();
        rowFont.dispose();
        buttonFont.dispose();
        btnBlue.dispose();
        rowEven.dispose();
        rowOdd.dispose();
        rowGold.dispose();
    }
}
