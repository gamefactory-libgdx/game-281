# Ball Drop — Figma AI Design Brief

---

## 1. Art Style & Color Palette

**Art Style:** Flat, minimalist design with subtle rounded corners and soft shadows. Cartoon-adjacent but clean—no heavy outlines or gradients on UI elements. Physics-game aesthetic inspired by classic Pachinko/plinko visual language with modern mobile sensibility.

**Primary Color Palette:**
- `#1E88E5` (Bright Electric Blue) — primary action buttons, active states, ball highlights
- `#FFC107` (Warm Golden Yellow) — score zones, high-value slots, accent highlights
- `#2C3E50` (Dark Slate Gray) — backgrounds, text, peg outlines
- `#FFFFFF` (Clean White) — UI panels, text on dark, clarity

**Accent Colors:**
- `#FF6B6B` (Coral Red) — warning states, danger zones, low-value slots
- `#4CAF50` (Fresh Green) — success states, high-value zones, positive feedback

**Font Mood & Weight:** Modern, geometric sans-serif (Roboto or equivalent). Primary headers use Bold weight (700) for impact; body text and scores use Regular (400) or Medium (500). All text should feel energetic yet legible—no thin weights, no serifs.

---

## 2. App Icon — icon_512.png (512×512px)

**Background:** Radial gradient from `#1E88E5` (top-left) to `#0D47A1` (bottom-right), creating depth and a sense of falling motion. Subtle texture overlay (0.5% opacity) for tactile feel.

**Central Symbol:** A bold, simplified ball (golden `#FFC107`) positioned center-left, with 3–4 stylized pegs (`#2C3E50`, circular dots) arranged in a descending diagonal pattern to its right. The ball appears mid-bounce with a subtle motion blur trail in semi-transparent white (`rgba(255,255,255,0.6)`).

**Glow & Shadow Effects:** 
- Outer glow around the ball (8px blur, `#FFC107`, 30% opacity) to make it "pop"
- Inner shadow on pegs (2px, `#000000`, 20% opacity) for dimensional separation
- Subtle radial highlight on ball (top-left quadrant, white, 15% opacity) to suggest reflectivity

**Overall Mood:** Energetic, inviting, instantly recognizable as a tap-based physics game. The gradient and glow communicate motion and excitement. Rounded safe-zone respected (all detail within central 400×400px).

---

## 3. Backgrounds (480×854 portrait)

**Background List (derived from peg layouts and game zones):**
1. `bg_main.png` — Main menu / title screen
2. `bg_classic.png` — Classic layout gameplay environment
3. `bg_diamond.png` — Diamond layout gameplay environment
4. `bg_zigzag.png` — Zigzag layout gameplay environment
5. `bg_results.png` — Round results & game over summary environment

---

### bg_main.png
Soft gradient background transitioning from `#1E88E5` (top) to `#87CEEB` (bottom), evoking a clear sky. Subtle animated cloud shapes (very light, nearly white at 95% opacity) scattered across upper third for playful mood. Faint grid or dot pattern (0.8% opacity, `#2C3E50`) in center creates a "game board" feel without being distracting. Bottom corners feature soft vignette (5% opacity) to frame the content.

---

### bg_classic.png
Clean, neutral environment suggesting a wooden or metallic game table. Base color `#3A4A5C` (dark blue-gray) with vertical wood-grain texture overlay (5% opacity). Pegs cast subtle shadows downward (`#000000`, 8% opacity). Score zone area at bottom features subtle horizontal stripe pattern in alternating `#2C3E50` and `#445566` (2px stripes, 3% opacity) to suggest distinct slot sections. Light ambient glow from top (`#FFFFFF`, 3% opacity) as if lit by studio light.

---

### bg_diamond.png
More dramatic environment—deep indigo gradient (`#2C3E50` to `#1A1A2E`) with geometric diamond-shaped decorative elements scattered subtly in corners (`#1E88E5`, 8% opacity, rotated 45°). Creates a sense of structure and precision matching the diamond peg layout. Slight radial vignette around the center gameplay area (5% opacity, `#000000`) draws eye inward. Score zones glow faintly with `#FFC107` (2% opacity) beneath surface.

---

### bg_zigzag.png
High-energy environment with diagonal stripe pattern (alternating `#3A4A5C` and `#445566`, 8px width, 45° angle, 4% opacity). Base color slightly warmer than classic (`#4A5A6C`) to suggest movement and dynamism. Zigzag accent line runs vertically down center (`#FF6B6B`, 1px, 6% opacity) as a visual echo of the peg layout. Faint motion blur effect in background (`#FFFFFF`, 1% opacity, horizontal streaks) reinforces the chaotic bouncing mechanic.

---

### bg_results.png
Neutral, calm environment suggesting completion and reflection. Soft gradient (`#F5F5F5` to `#E8E8E8`) for light, clean aesthetic. Faint circular bokeh shapes (`#1E88E5`, 2% opacity, 30–50px diameter) scattered softly in background suggesting celebration or confetti aftermath. Bottom third slightly darker (`#D0D0D0`, 2% opacity) to frame result cards visually. Overall mood: achievement-focused, ready for next action.

---

## 4. UI Screens (480×854 portrait)

### MainMenuScreen
**Background:** `bg_main.png`  
**Header:** "BALL DROP" centered at top in `#2C3E50`, Bold 48px, with subtle drop shadow (2px, `#000000`, 15% opacity). Subtitle "Tap. Bounce. Score." below in Regular 16px, `#1E88E5`.  
**Buttons (centered, stacked vertically in middle area):**
- "PLAY" — Bold, 56px wide × 48px tall, `#1E88E5` background, `#FFFFFF` text, rounded corners (8px), top center-ish
- "LEADERBOARD" — Medium, 56px wide × 40px tall, `#FFC107` background, `#2C3E50` text, rounded corners (8px)
- "HOW TO PLAY" — Medium, 56px wide × 40px tall, `#4CAF50` background, `#FFFFFF` text, rounded corners (8px)
- "SETTINGS" — Gear icon (24×24px) or labeled, `#2C3E50` outline, `#2C3E50` text, bottom-right corner

**Key Elements:** Decorative ball icon (64×64px, `#FFC107`) floating in upper-right area; soft peg accent (circular, `#1E88E5`, 24px diameter) in lower-left. Overall layout feels spacious and inviting.

---

### LayoutSelectScreen
**Background:** `bg_main.png`  
**Header:** "SELECT LAYOUT" centered at top in `#2C3E50`, Bold 40px, with drop shadow (2px, `#000000`, 15% opacity).  
**Layout Cards (three equal-width cards, stacked vertically, centered):**
- **Classic Card:** Top center; 140px wide × 180px tall, white background with 2px `#1E88E5` border, rounded corners (12px). Shows simplified visual of classic peg pattern (3 rows, evenly spaced dots in `#2C3E50`). Label "CLASSIC" in Bold 18px, `#2C3E50`, bottom-center of card. On tap, highlights with light blue overlay (20% opacity).
- **Diamond Card:** Middle; same dimensions and styling, diamond-pattern pegs visible. Label "DIAMOND" in Bold 18px.
- **Zigzag Card:** Bottom; same styling, zigzag pattern visible. Label "ZIGZAG" in Bold 18px.

**Navigation:** Back button (< arrow, 24×24px, `#2C3E50`) top-left corner.

---

### ClassicLayoutScreen
**Background:** `bg_classic.png`  
**Gameplay Area (center-to-upper area):** Peg field rendered in real-time via libGDX. Pegs are circles (`#2C3E50`, 16px diameter) with subtle inner shadow. Ball (`#FFC107`, 14px diameter) animated falling and bouncing. Score zones at bottom (5 zones: 10, 25, 50, 100, 10 points from left to right) rendered as rectangles with zone labels in white text, zone backgrounds in gradient (`#FF6B6B` → `#FFC107` → `#4CAF50` left-to-right).  
**HUD (top bar, full width):** Left side shows "BALLS: 10/10" in Regular 14px, `#2C3E50`. Right side shows "SCORE: 0" in Bold 20px, `#FFC107`.  
**Instruction Text (center-upper, faint):** "TAP TO DROP" in Regular 16px, `#2C3E50`, 40% opacity.  
**Buttons (bottom):** "PAUSE" button (48×40px, `#1E88E5` background, white text) center-bottom. Once all 10 balls released, a semi-transparent overlay appears with "ROUND COMPLETE" message and "NEXT" button.

---

### DiamondLayoutScreen
**Background:** `bg_diamond.png`  
**Gameplay Area:** Identical HUD and score zone layout as Classic. Pegs arranged in diamond pattern (wider middle rows, narrower top/bottom). All other elements consistent with ClassicLayoutScreen.  
**HUD:** Same format — "BALLS: X/10" top-left, "SCORE: Y" top-right in matching colors.  
**Buttons:** "PAUSE" center-bottom in same style.

---

### ZigzagLayoutScreen
**Background:** `bg_zigzag.png`  
**Gameplay Area:** Identical HUD and score zone layout. Pegs arranged in zigzag pattern (alternating left-right columns). Visual styling consistent across all three gameplay screens.  
**HUD:** "BALLS: X/10" top-left, "SCORE: Y" top-right.  
**Buttons:** "PAUSE" center-bottom.

---

### RoundResultScreen
**Background:** `bg_results.png`  
**Header:** "ROUND COMPLETE" centered at top in Bold 36px, `#2C3E50`, drop shadow (2px, `#000000`, 15% opacity).  
**Results Card (centered, 320px wide × 260px tall):** White background with 1px `#1E88E5` border, rounded corners (12px), subtle drop shadow (4px, `#000000`, 10% opacity).
- "FINAL SCORE: [score]" in Bold 32px, `#FFC107`, centered top-third
- "COINS EARNED: [coins]" in Bold 20px, `#4CAF50`, centered middle
- Star rating display (if applicable) — 1–5 stars in `#FFC107`, centered below score

**Buttons (below card, stacked):**
- "PLAY AGAIN" — Bold, 56px wide × 48px tall, `#1E88E5` background, white text, rounded 8px, center-bottom
- "MAIN MENU" — Medium, 56px wide × 40px tall, `#2C3E50` outline (2px), `#2C3E50` text, rounded 8px

---

### GameOverScreen
**Background:** `bg_results.png`  
**Header:** "SESSION OVER" centered at top in Bold 36px, `#2C3E50`, drop shadow.  
**Stats Card (centered, 320px wide × 300px tall):** White background, 1px `#1E88E5` border, rounded corners (12px), drop shadow (4px, `#000000`, 10% opacity).
- "TOTAL SCORE: [total]" in Bold 32px, `#FFC107`, top-third
- "HIGH SCORE: [best]" in Bold 20px, `#4CAF50`, middle
- "ROUNDS PLAYED: [count]" in Regular 16px, `#2C3E50`, below
- (Optional) Comparison badge: "NEW RECORD!" or "Personal Best!" in `#FF6B6B` if applicable

**Buttons (below card):**
- "RESTART" — Bold, 56px wide × 48px tall, `#1E88E5` background, white text, rounded 8px
- "MAIN MENU" — Medium, 56px wide × 40px tall, outline style, `#2C3E50`

---

### LeaderboardScreen
**Background:** `bg_main.png`  
**Header:** "TOP 10 SCORES" centered at top in Bold 36px, `#2C3E50`, drop shadow.  
**Leaderboard List (centered, 340px wide, 500px tall):** Scrollable area with 10 rows.
- Each row: rank (1–10) in bold `#FFC107` (24px), left-aligned with 16px left margin. Player name in Regular 14px, `#2C3E50`, score in Bold 16px, `#1E88E5`, right-aligned. Row height 48px; alternating subtle background colors (`#F5F5F5` and `#FFFFFF`).
- Row 1 (top score): gold highlight background (`#FFC107`, 5% opacity). Row 2 & 3: silver and bronze accents (`#C0C0C0` and `#CD7F32`, 3% opacity).

**Buttons (bottom):**
- "BACK" — Medium, 56px wide × 40px tall, `#2C3E50` outline, top-center-left

---

### SettingsScreen
**Background:** `bg_main.png`  
**Header:** "SETTINGS" centered at top in Bold 36px, `#2C3E50`, drop shadow.  
**Settings Panel (centered, 340px wide, 500px tall):** White background, rounded corners (12px), drop shadow (4px, `#000000`, 10% opacity).
- **Sound Toggle:** Label "SOUND" in Regular 14px, `#2C3E50`, left side. Toggle switch (50px wide, rounded, `#1E88E5` when on, `#D0D0D0` when off) right-aligned. Row height 56px.
- **Music Toggle:** Same layout, label "MUSIC", toggle below sound.
- **Brightness Slider:** Label "BRIGHTNESS" left, horizontal slider (200px wide, `#1E88E5` track, `#FFC107` thumb) right. Row height 56px.
- **Reset Data Button:** "RESET DATA" label left, small button (60px wide × 32px tall, `#FF6B6B` background, white text, rounded 6px) right. Row height 56px.
- **Credits Link:** "CREDITS" label left, text link in `#1E88E5` right. Row height 48px.

**Back Button:** Top-left corner, "< BACK" text or arrow icon (24×24px, `#2C3E50`).

---

### HowToPlayScreen
**Background:** `bg_main.png`  
**Header:** "HOW TO PLAY" centered at top in Bold 36px, `#2C3E50`, drop shadow.  
**Tutorial Content (centered, 340px wide, scrollable, 550px height):** White background panel, rounded corners (12px), drop shadow (4px, `#000000`, 10% opacity). Contains 4–5 short tutorial sections:

1. **Section 1 — "TAP TO DROP"** (heading in Bold 16px, `#1E88E5`)
   - Icon (ball + tap gesture, 40×40px) top-left.
   - Text in Regular 13px, `#2C3E50`: "Press and release to drop a ball from the top of the board."

2. **Section 2 — "BOUNCE OFF PEGS"**
   - Icon (ball bouncing, 40×40px).
   - Text: "Balls bounce off pegs as they fall. Aim for high-value zones!"

3. **Section 3 — "SCORE ZONES"**
   - Icon (five colored zones, 40×40px).
   - Text: "Land in colored zones at the bottom. Red = 10pts, Yellow = 25pts, Green = 50pts, Blue = 100pts."

4. **Section 4 — "10 BALLS PER ROUND"**
   - Icon (number 10, 40×40px).
   - Text: "You have 10 balls per round. Use them wisely!"

**Back Button:** Top-left corner or bottom "BACK" button (56px wide × 40px tall, `#2C3E50` outline).

---

## 5. Export Checklist

```
- icon_512.png (512×512)
- backgrounds/bg_main.png (480×854)
- backgrounds/bg_classic.png (480×854)
- backgrounds/bg_diamond.png (480×854)
- backgrounds/bg_zigzag.png (480×854)
- backgrounds/bg_results.png (480×854)
- screens/main_menu.png (480×854)
- screens/layout_select.png (480×854)
- screens/game_classic.png (480×854)
- screens/game_diamond.png (480×854)
- screens/game_zigzag.png (480×854)
- screens/round_result.png (480×854)
- screens/game_over.png (480×854)
- screens/leaderboard.png (480×854)
- screens/settings.png (480×854)
- screens/how_to_play.png (480×854)
```

---

**NOTES FOR FIGMA AI:**
- All hex colors and measurements are exact; maintain pixel-perfect alignment.
- Typography uses Roboto Bold (700) for headers, Regular (400) / Medium (500) for body.
- Preserve all drop shadows (2–4px blur, 10–15% opacity `#000000`) for depth consistency.
- Score zones in gameplay screens must show all five slots with distinct colors: Red `#FF6B6B`, Yellow `#FFC107`, Green `#4CAF50`, Blue `#1E88E5`, Red (mirrored).
- All buttons use 8px rounded corners unless noted otherwise.
- Maintain 480×854px portrait orientation across all screens.
