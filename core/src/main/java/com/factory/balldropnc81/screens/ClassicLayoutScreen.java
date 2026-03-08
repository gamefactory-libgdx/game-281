package com.factory.balldropnc81.screens;

import com.badlogic.gdx.math.Vector2;
import com.factory.balldropnc81.Constants;
import com.factory.balldropnc81.MainGame;

/**
 * Classic peg layout: uniform grid of 8 rows × 7 columns.
 * Pegs are evenly spaced, giving a straightforward Plinko experience.
 */
public class ClassicLayoutScreen extends BaseGameScreen {

    public ClassicLayoutScreen(MainGame game) {
        super(game);
    }

    @Override
    protected void initPegs() {
        // Row 0 is at the bottom of the peg field (CLASSIC_PEG_START_Y).
        // Row 7 is at the top, closest to the ball drop position.
        // Rows indexed low→high map to y = START_Y + row * SPACING_Y.
        for (int row = 0; row < Constants.CLASSIC_ROWS; row++) {
            for (int col = 0; col < Constants.CLASSIC_COLS; col++) {
                float x = Constants.CLASSIC_PEG_START_X + col * Constants.CLASSIC_PEG_SPACING_X;
                float y = Constants.CLASSIC_PEG_START_Y + row * Constants.CLASSIC_PEG_SPACING_Y;
                pegs.add(new Vector2(x, y));
            }
        }
    }

    @Override
    protected String getBackgroundPath() {
        return Constants.BG_CLASSIC;
    }

    @Override
    protected int getLayoutType() {
        return PauseScreen.LAYOUT_CLASSIC;
    }
}
