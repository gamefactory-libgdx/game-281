package com.factory.balldropnc81.screens;

import com.badlogic.gdx.math.Vector2;
import com.factory.balldropnc81.Constants;
import com.factory.balldropnc81.MainGame;

/**
 * Zigzag peg layout: 8 rows × 6 columns with alternating horizontal offset.
 * Even rows start at ZIGZAG_PEG_START_X; odd rows are shifted right by ZIGZAG_OFFSET_X,
 * creating the characteristic zigzag stagger that deflects balls unpredictably.
 */
public class ZigzagLayoutScreen extends BaseGameScreen {

    public ZigzagLayoutScreen(MainGame game) {
        super(game);
    }

    @Override
    protected void initPegs() {
        for (int row = 0; row < Constants.ZIGZAG_ROWS; row++) {
            float offsetX = (row % 2 == 1) ? Constants.ZIGZAG_OFFSET_X : 0f;
            float y       = Constants.ZIGZAG_PEG_START_Y + row * Constants.ZIGZAG_PEG_SPACING_Y;
            for (int col = 0; col < Constants.ZIGZAG_COLS; col++) {
                float x = Constants.ZIGZAG_PEG_START_X + offsetX + col * Constants.ZIGZAG_PEG_SPACING_X;
                pegs.add(new Vector2(x, y));
            }
        }
    }

    @Override
    protected String getBackgroundPath() {
        return Constants.BG_ZIGZAG;
    }

    @Override
    protected int getLayoutType() {
        return PauseScreen.LAYOUT_ZIGZAG;
    }
}
