package com.chen.xinyueweather.widget.weather;

public class HazeLine extends BaseLine {

    public HazeLine(int maxX, int maxY) {
        super(maxX, maxY);
    }

    @Override
    protected void resetRandom() {
        startX = startX - maxX;
    }

    @Override
    protected void initRandom() {
        deltaX = 1 + random.nextInt(5);
        startX = random.nextInt(maxX);
        startY = random.nextInt(maxY);
        stopX = startX + deltaX;
    }

    public void rain() {
        if (outOfBounds())
            resetRandom();
        startX += deltaX;
        stopX += deltaX;
    }

    @Override
    protected boolean outOfBounds() {
        if (getStartX() >= maxX) {
            return true;
        }
        return false;
    }
}
