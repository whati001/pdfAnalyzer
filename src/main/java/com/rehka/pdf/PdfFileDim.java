package com.rehka.pdf;

public class PdfFileDim {
    private float x;
    private float y;

    public PdfFileDim() {
        this.x = 0;
        this.y = 0;
    }

    public PdfFileDim(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PdfFileDim that = (PdfFileDim) o;

        if (Float.compare(that.x, x) != 0) return false;
        return Float.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    public String getKey() {
        return String.format("%s:%s", this.x, this.y);
    }

    @Override
    public String toString() {
        return String.format("%s[x: %.2f, y: %.2f]", this.getClass().getSimpleName(), this.x, this.y);
    }
}
