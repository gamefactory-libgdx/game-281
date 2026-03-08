package com.factory.balldropnc81.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base for all peg-layout game screens.
 * Subclasses implement initPegs(), getBackgroundPath(), and getLayoutType().
 */
public abstract class BaseGameScreen extends ScreenAdapter {

    // ─── Inner: Ball ─────────────────────────────────────────────────────────

    protected static class Ball {
        float x, y;
        float vx, vy;
        boolean landed;
        int zoneScore;

        Ball(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    // ─── Inner: GameFieldActor ────────────────────────────────────────────────

    private class GameFieldActor extends Actor {

        private final float ZONE_W = Constants.WORLD_WIDTH / Constants.SCORE_ZONE_COUNT;

        private final Color[] ZONE_COLORS = {
            new Color(0xFF6B6BFF),  // Zone 0 — 10 pts, coral red
            new Color(0xFFC107FF),  // Zone 1 — 25 pts, yellow
            new Color(0x4CAF50FF),  // Zone 2 — 50 pts, green
            new Color(0x1E88E5FF),  // Zone 3 — 100 pts, blue
            new Color(0xFF6B6BFF)   // Zone 4 — 10 pts, coral red mirror
        };

        private final int[] ZONE_SCORES = {
            Constants.ZONE_SCORE_1,
            Constants.ZONE_SCORE_2,
            Constants.ZONE_SCORE_3,
            Constants.ZONE_SCORE_4,
            Constants.ZONE_SCORE_5
        };

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            // Score zones
            for (int i = 0; i < Constants.SCORE_ZONE_COUNT; i++) {
                shapeRenderer.setColor(ZONE_COLORS[i]);
                shapeRenderer.rect(
                    i * ZONE_W,
                    Constants.SCORE_ZONE_Y,
                    ZONE_W,
                    Constants.SCORE_ZONE_HEIGHT
                );
            }

            // Zone dividers (thin dark lines)
            shapeRenderer.setColor(new Color(0f, 0f, 0f, 0.25f));
            for (int i = 1; i < Constants.SCORE_ZONE_COUNT; i++) {
                shapeRenderer.rect(i * ZONE_W - 1f, Constants.SCORE_ZONE_Y, 2f, Constants.SCORE_ZONE_HEIGHT);
            }

            // Pegs
            shapeRenderer.setColor(new Color(0x2C3E50FF));
            for (Vector2 peg : pegs) {
                shapeRenderer.circle(peg.x, peg.y, Constants.PEG_RADIUS, 16);
            }

            // Balls
            shapeRenderer.setColor(new Color(0xFFC107FF));
            for (Ball ball : activeBalls) {
                shapeRenderer.circle(ball.x, ball.y, Constants.BALL_RADIUS, 16);
            }

            shapeRenderer.end();

            // Zone score labels (drawn back in batch)
            batch.begin();
            GlyphLayout layout = new GlyphLayout();
            for (int i = 0; i < Constants.SCORE_ZONE_COUNT; i++) {
                String text = String.valueOf(ZONE_SCORES[i]);
                layout.setText(zoneFont, text);
                zoneFont.draw(
                    batch,
                    text,
                    i * ZONE_W + (ZONE_W - layout.width) * 0.5f,
                    Constants.SCORE_ZONE_Y + Constants.SCORE_ZONE_HEIGHT * 0.5f + layout.height * 0.5f
                );
            }
        }
    }

    // ─── Fields ───────────────────────────────────────────────────────────────

    protected final MainGame          game;
    protected final OrthographicCamera camera;
    protected final StretchViewport   viewport;
    protected final Stage             stage;
    protected final AssetManager      manager;
    protected final ShapeRenderer     shapeRenderer;

    protected final BitmapFont hudFont;
    protected final BitmapFont buttonFont;
    protected final BitmapFont zoneFont;

    protected final Texture btnBlue;
    protected final Texture btnDark;

    protected final List<Vector2> pegs        = new ArrayList<>();
    protected final List<Ball>    activeBalls  = new ArrayList<>();

    protected int     score         = 0;
    protected int     ballsLaunched = 0;
    protected int     ballsLanded   = 0;
    protected boolean roundOver     = false;

    private Label ballsLabel;
    private Label scoreLabel;

    // ─── Constructor ──────────────────────────────────────────────────────────

    protected BaseGameScreen(MainGame game) {
        this.game = game;
        camera    = new OrthographicCamera();
        viewport  = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage     = new Stage(viewport, game.batch);

        manager = new AssetManager();
        manager.load(getBackgroundPath(), Texture.class);
        manager.finishLoading();

        shapeRenderer = new ShapeRenderer();

        FreeTypeFontGenerator robotoGen = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_ROBOTO));

        FreeTypeFontParameter p1 = new FreeTypeFontParameter();
        p1.size = Constants.FONT_SIZE_HUD;
        hudFont = robotoGen.generateFont(p1);

        FreeTypeFontParameter p2 = new FreeTypeFontParameter();
        p2.size = Constants.FONT_SIZE_BUTTON;
        buttonFont = robotoGen.generateFont(p2);

        FreeTypeFontParameter p3 = new FreeTypeFontParameter();
        p3.size  = Constants.FONT_SIZE_SMALL;
        p3.color = Color.WHITE;
        zoneFont = robotoGen.generateFont(p3);

        robotoGen.dispose();

        btnBlue = solidTexture(new Color(0x1E88E5FF));
        btnDark = solidTexture(new Color(0x2C3E50FF));

        initPegs();
        buildUI();
        setupInput();
    }

    // ─── Abstract API ─────────────────────────────────────────────────────────

    protected abstract void initPegs();
    protected abstract String getBackgroundPath();
    protected abstract int getLayoutType();

    // ─── Setup ────────────────────────────────────────────────────────────────

    private Texture solidTexture(Color color) {
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(color);
        pm.fill();
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }

    private void buildUI() {
        // Background
        Image bg = new Image(manager.get(getBackgroundPath(), Texture.class));
        bg.setFillParent(true);
        stage.addActor(bg);

        // Game field (zones, pegs, balls drawn via ShapeRenderer trick)
        stage.addActor(new GameFieldActor());

        // HUD
        Label.LabelStyle ballsStyle = new Label.LabelStyle(hudFont, new Color(0x2C3E50FF));
        Label.LabelStyle scoreStyle = new Label.LabelStyle(hudFont, new Color(0xFFC107FF));

        ballsLabel = new Label("BALLS: " + Constants.BALLS_PER_ROUND + "/" + Constants.BALLS_PER_ROUND, ballsStyle);
        scoreLabel = new Label("SCORE: 0", scoreStyle);

        // Pause button
        TextButton.TextButtonStyle pauseStyle = new TextButton.TextButtonStyle();
        pauseStyle.up        = new TextureRegionDrawable(btnBlue);
        pauseStyle.down      = new TextureRegionDrawable(btnBlue);
        pauseStyle.over      = new TextureRegionDrawable(btnBlue);
        pauseStyle.font      = buttonFont;
        pauseStyle.fontColor = Color.WHITE;

        TextButton pauseBtn = new TextButton("PAUSE", pauseStyle);
        pauseBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                if (!roundOver) pauseGame();
            }
        });

        // "TAP TO DROP" hint label
        Label.LabelStyle hintStyle = new Label.LabelStyle(hudFont, new Color(0x2C3E50FF).mul(1f, 1f, 1f, 0.45f));
        final Label hintLabel = new Label("TAP TO DROP", hintStyle);

        Table hud = new Table();
        hud.setFillParent(true);
        hud.top().pad(Constants.HUD_PAD);
        hud.add(ballsLabel).left().expandX();
        hud.add(pauseBtn).size(100f, 40f).right();
        hud.row();
        hud.add(scoreLabel).left().expandX();
        hud.row().padTop(Constants.WORLD_HEIGHT * 0.25f);
        hud.add(hintLabel).center().colspan(2);

        stage.addActor(hud);

        // Store hint label reference to hide it once first ball is dropped
        this.hintLabelRef = hintLabel;
    }

    // stored so we can hide it after first ball drop
    private Label hintLabelRef;

    private void setupInput() {
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (roundOver) return false;
                Vector2 world = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                // Reject taps in the HUD area (top strip)
                if (world.y > Constants.WORLD_HEIGHT - Constants.HUD_HEIGHT - 20f) return false;
                dropBall(world.x);
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    if (!roundOver) pauseGame();
                    return true;
                }
                return false;
            }
        }));
    }

    // ─── Game logic ───────────────────────────────────────────────────────────

    private void dropBall(float worldX) {
        if (ballsLaunched >= Constants.BALLS_PER_ROUND || roundOver) return;
        float x = Math.max(Constants.BALL_RADIUS, Math.min(worldX, Constants.WORLD_WIDTH - Constants.BALL_RADIUS));
        activeBalls.add(new Ball(x, Constants.BALL_DROP_Y));
        ballsLaunched++;
        if (hintLabelRef != null) hintLabelRef.setVisible(false);
        refreshHUD();
    }

    private void refreshHUD() {
        int remaining = Constants.BALLS_PER_ROUND - ballsLaunched;
        ballsLabel.setText("BALLS: " + remaining + "/" + Constants.BALLS_PER_ROUND);
        scoreLabel.setText("SCORE: " + score);
    }

    private void updateGame(float delta) {
        for (int i = activeBalls.size() - 1; i >= 0; i--) {
            Ball ball = activeBalls.get(i);
            stepBall(ball, delta);
            if (ball.landed) {
                score += ball.zoneScore;
                activeBalls.remove(i);
                ballsLanded++;
                refreshHUD();
            }
        }

        if (!roundOver && ballsLanded >= Constants.BALLS_PER_ROUND) {
            roundOver = true;
            onRoundComplete();
        }
    }

    private void stepBall(Ball ball, float delta) {
        // Gravity
        ball.vy += Constants.GRAVITY * delta;

        // Speed cap
        float speedSq = ball.vx * ball.vx + ball.vy * ball.vy;
        if (speedSq > Constants.BALL_MAX_SPEED * Constants.BALL_MAX_SPEED) {
            float ratio = Constants.BALL_MAX_SPEED / (float) Math.sqrt(speedSq);
            ball.vx *= ratio;
            ball.vy *= ratio;
        }

        // Integrate position
        ball.x += ball.vx * delta;
        ball.y += ball.vy * delta;

        // Wall collisions
        if (ball.x - Constants.BALL_RADIUS < 0f) {
            ball.x = Constants.BALL_RADIUS;
            ball.vx = Math.abs(ball.vx) * Constants.BOUNCE_DAMPING;
        } else if (ball.x + Constants.BALL_RADIUS > Constants.WORLD_WIDTH) {
            ball.x = Constants.WORLD_WIDTH - Constants.BALL_RADIUS;
            ball.vx = -Math.abs(ball.vx) * Constants.BOUNCE_DAMPING;
        }

        // Peg collisions
        for (Vector2 peg : pegs) {
            float dx     = ball.x - peg.x;
            float dy     = ball.y - peg.y;
            float distSq = dx * dx + dy * dy;
            float minD   = Constants.BALL_RADIUS + Constants.PEG_RADIUS;
            if (distSq < minD * minD && distSq > 0.0001f) {
                float dist = (float) Math.sqrt(distSq);
                float nx   = dx / dist;
                float ny   = dy / dist;
                float dot  = ball.vx * nx + ball.vy * ny;
                if (dot < 0f) {
                    ball.vx -= 2f * dot * nx;
                    ball.vy -= 2f * dot * ny;
                    ball.vx *= Constants.BOUNCE_DAMPING;
                    ball.vy *= Constants.BOUNCE_DAMPING;
                }
                float overlap = minD - dist;
                ball.x += nx * overlap;
                ball.y += ny * overlap;
            }
        }

        // Score zone detection (ball enters the scoring area from above)
        if (!ball.landed) {
            float zoneTop = Constants.SCORE_ZONE_Y + Constants.SCORE_ZONE_HEIGHT;
            if (ball.y - Constants.BALL_RADIUS <= zoneTop) {
                int zoneIdx = (int)(ball.x / (Constants.WORLD_WIDTH / Constants.SCORE_ZONE_COUNT));
                zoneIdx = Math.max(0, Math.min(zoneIdx, Constants.SCORE_ZONE_COUNT - 1));
                int[] zoneScores = {
                    Constants.ZONE_SCORE_1,
                    Constants.ZONE_SCORE_2,
                    Constants.ZONE_SCORE_3,
                    Constants.ZONE_SCORE_4,
                    Constants.ZONE_SCORE_5
                };
                ball.zoneScore = zoneScores[zoneIdx];
                ball.landed    = true;
            }
            // Safety: ball fell off the bottom of the world
            if (ball.y < -Constants.BALL_RADIUS * 2f) {
                ball.zoneScore = 0;
                ball.landed    = true;
            }
        }
    }

    private void pauseGame() {
        game.setScreen(new PauseScreen(game, this, getLayoutType()));
    }

    private void onRoundComplete() {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        int totalRounds = prefs.getInteger(Constants.KEY_TOTAL_ROUNDS, 0) + 1;
        prefs.putInteger(Constants.KEY_TOTAL_ROUNDS, totalRounds);
        prefs.flush();
        game.setScreen(new RoundResultScreen(game, score, getLayoutType()));
    }

    // ─── Screen lifecycle ─────────────────────────────────────────────────────

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.23f, 0.29f, 0.36f, 1f);
        updateGame(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int w, int h) {
        viewport.update(w, h, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        manager.dispose();
        shapeRenderer.dispose();
        hudFont.dispose();
        buttonFont.dispose();
        zoneFont.dispose();
        btnBlue.dispose();
        btnDark.dispose();
    }
}
