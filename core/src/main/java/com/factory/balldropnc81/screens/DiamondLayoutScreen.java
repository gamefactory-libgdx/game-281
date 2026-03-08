package com.factory.balldropnc81.screens;

import com.badlogic.gdx.math.Vector2;
import com.factory.balldropnc81.Constants;
import com.factory.balldropnc81.MainGame;

/**
 * Diamond peg layout: 9 rows arranged in a diamond (rhombus) formation.
 * Row 0 (bottom of field) and row 8 (top) each have 1 peg.
 * Row 4 (middle) has the most pegs (5), mirroring a Galton board diamond shape.
 */
public class DiamondLayoutScreen extends BaseGameScreen {

    public DiamondLayoutScreen(MainGame game) {
        super(game);
    }

    @Override
    protected void initPegs() {
        // DIAMOND_ROWS = 9
        // pegsInRow for row r: r <= 4 → r+1 pegs; r > 4 → 9-r pegs
        // All rows centered at WORLD_WIDTH / 2
        for (int row = 0; row < Constants.DIAMOND_ROWS; row++) {
            int pegsInRow = (row <= Constants.DIAMOND_ROWS / 2)
                ? row + 1
                : Constants.DIAMOND_ROWS - row;

            float rowWidth = (pegsInRow - 1) * Constants.DIAMOND_PEG_SPACING_X;
            float startX   = Constants.WORLD_WIDTH / 2f - rowWidth / 2f;
            float y        = Constants.DIAMOND_PEG_START_Y + row * Constants.DIAMOND_PEG_SPACING_Y;

            for (int col = 0; col < pegsInRow; col++) {
                float x = startX + col * Constants.DIAMOND_PEG_SPACING_X;
                pegs.add(new Vector2(x, y));
            }
        }
    }

    @Override
    protected String getBackgroundPath() {
        return Constants.BG_DIAMOND;
    }

    @Override
    protected int getLayoutType() {
        return PauseScreen.LAYOUT_DIAMOND;
    }
}
