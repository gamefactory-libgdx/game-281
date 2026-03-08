package com.factory.balldropnc81;

public class Constants {

    // World dimensions
    public static final float WORLD_WIDTH  = 480f;
    public static final float WORLD_HEIGHT = 854f;

    // Ball physics
    public static final float BALL_DIAMETER    = 14f;
    public static final float BALL_RADIUS      = BALL_DIAMETER / 2f;
    public static final float BALL_MASS        = 1f;
    public static final float GRAVITY          = -600f;
    public static final float BOUNCE_DAMPING   = 0.7f;
    public static final float BALL_DROP_X      = WORLD_WIDTH / 2f;
    public static final float BALL_DROP_Y      = WORLD_HEIGHT - 60f;
    public static final float BALL_MAX_SPEED   = 800f;

    // Peg geometry
    public static final float PEG_DIAMETER     = 16f;
    public static final float PEG_RADIUS       = PEG_DIAMETER / 2f;

    // Peg layout — Classic
    public static final int   CLASSIC_ROWS     = 8;
    public static final int   CLASSIC_COLS     = 7;
    public static final float CLASSIC_PEG_SPACING_X = 60f;
    public static final float CLASSIC_PEG_SPACING_Y = 60f;
    public static final float CLASSIC_PEG_START_X   = 60f;
    public static final float CLASSIC_PEG_START_Y   = 350f;

    // Peg layout — Diamond
    public static final int   DIAMOND_ROWS     = 9;
    public static final float DIAMOND_PEG_SPACING_X = 55f;
    public static final float DIAMOND_PEG_SPACING_Y = 55f;
    public static final float DIAMOND_PEG_START_X   = 60f;
    public static final float DIAMOND_PEG_START_Y   = 360f;

    // Peg layout — Zigzag
    public static final int   ZIGZAG_ROWS      = 8;
    public static final int   ZIGZAG_COLS      = 6;
    public static final float ZIGZAG_PEG_SPACING_X  = 65f;
    public static final float ZIGZAG_PEG_SPACING_Y  = 60f;
    public static final float ZIGZAG_PEG_START_X    = 55f;
    public static final float ZIGZAG_PEG_START_Y    = 350f;
    public static final float ZIGZAG_OFFSET_X       = 32f;

    // Score zones (5 zones left to right)
    public static final int   SCORE_ZONE_COUNT = 5;
    public static final int   ZONE_SCORE_1     = 10;   // outer left (red)
    public static final int   ZONE_SCORE_2     = 25;   // mid-left (yellow)
    public static final int   ZONE_SCORE_3     = 50;   // center (green)
    public static final int   ZONE_SCORE_4     = 100;  // mid-right (blue)
    public static final int   ZONE_SCORE_5     = 10;   // outer right (red mirror)
    public static final float SCORE_ZONE_HEIGHT = 80f;
    public static final float SCORE_ZONE_Y      = 20f;

    // Game rules
    public static final int   BALLS_PER_ROUND  = 10;

    // Leaderboard
    public static final int   LEADERBOARD_SIZE = 10;

    // Font sizes (world units)
    public static final int   FONT_SIZE_TITLE  = 48;
    public static final int   FONT_SIZE_HEADER = 36;
    public static final int   FONT_SIZE_BUTTON = 24;
    public static final int   FONT_SIZE_BODY   = 16;
    public static final int   FONT_SIZE_SMALL  = 13;
    public static final int   FONT_SIZE_HUD    = 20;

    // Button sizes (world units)
    public static final float BUTTON_WIDTH_LARGE  = 260f;
    public static final float BUTTON_HEIGHT_LARGE = 60f;
    public static final float BUTTON_WIDTH_MEDIUM = 240f;
    public static final float BUTTON_HEIGHT_MEDIUM = 50f;
    public static final float BUTTON_WIDTH_SMALL  = 120f;
    public static final float BUTTON_HEIGHT_SMALL = 40f;
    public static final float BUTTON_CORNER_RADIUS = 8f;
    public static final float BUTTON_PAD          = 12f;

    // Layout card sizes (LayoutSelectScreen)
    public static final float CARD_WIDTH         = 260f;
    public static final float CARD_HEIGHT        = 180f;
    public static final float CARD_CORNER_RADIUS = 12f;
    public static final float CARD_SPACING       = 20f;

    // Leaderboard row
    public static final float LEADERBOARD_WIDTH  = 340f;
    public static final float LEADERBOARD_ROW_H  = 48f;

    // Results card
    public static final float RESULTS_CARD_WIDTH  = 320f;
    public static final float RESULTS_CARD_HEIGHT = 300f;
    public static final float RESULTS_CARD_CORNER = 12f;

    // Settings panel
    public static final float SETTINGS_PANEL_WIDTH  = 340f;
    public static final float SETTINGS_PANEL_HEIGHT = 500f;
    public static final float SETTINGS_ROW_HEIGHT   = 56f;
    public static final float TOGGLE_WIDTH           = 50f;

    // HUD
    public static final float HUD_PAD   = 16f;
    public static final float HUD_HEIGHT = 48f;

    // Animation / timing
    public static final float RESULT_DELAY_SECONDS = 0.5f;

    // SharedPreferences keys
    public static final String PREFS_NAME          = "BallDropPrefs";
    public static final String KEY_HIGH_SCORE      = "highScore";
    public static final String KEY_SOUND_ENABLED   = "soundEnabled";
    public static final String KEY_MUSIC_ENABLED   = "musicEnabled";
    public static final String KEY_LEADERBOARD     = "leaderboard";
    public static final String KEY_TOTAL_ROUNDS    = "totalRounds";

    // Asset paths — backgrounds
    public static final String BG_MAIN    = "backgrounds/bg_main.png";
    public static final String BG_CLASSIC = "backgrounds/bg_classic.png";
    public static final String BG_DIAMOND = "backgrounds/bg_diamond.png";
    public static final String BG_ZIGZAG  = "backgrounds/bg_zigzag.png";
    public static final String BG_RESULTS = "backgrounds/bg_results.png";

    // Asset paths — UI
    public static final String UI_MAIN_MENU     = "ui/main_menu.png";
    public static final String UI_LAYOUT_SELECT = "ui/layout_select.png";
    public static final String UI_GAME_CLASSIC  = "ui/game_classic.png";
    public static final String UI_GAME_DIAMOND  = "ui/game_diamond.png";
    public static final String UI_GAME_ZIGZAG   = "ui/game_zigzag.png";
    public static final String UI_ROUND_RESULT  = "ui/round_result.png";
    public static final String UI_GAME_OVER     = "ui/game_over.png";
    public static final String UI_LEADERBOARD   = "ui/leaderboard.png";
    public static final String UI_SETTINGS      = "ui/settings.png";
    public static final String UI_HOW_TO_PLAY   = "ui/how_to_play.png";

    // Asset paths — fonts
    public static final String FONT_ROBOTO   = "fonts/Roboto-Regular.ttf";
    public static final String FONT_ORBITRON = "fonts/Orbitron-Regular.ttf";
}
