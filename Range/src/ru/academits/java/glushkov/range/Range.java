package ru.academits.java.glushkov.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("(%.2f; %.2f)", from, to);
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        double epsilon = 1e-10;
        return number >= from - epsilon && number <= to + epsilon;
    }

    public Range getIntersection(Range range) {
        if (this.to <= range.from || range.to <= this.from) {
            return null;
        }

        if (this.from <= range.from && range.from <= this.to && this.to <= range.to) {
            return new Range(range.from, this.to);
        }

        if (this.from <= range.from && range.to <= this.to) {
            return new Range(range.from, range.to);
        }

        if (range.from <= this.from && this.from <= range.to && range.to <= this.to) {
            return new Range(this.from, range.to);
        }

        return new Range(this.from, this.to);
    }

    public Range[] getUnion(Range range) {
        if (this.to < range.from) {
            return new Range[]{new Range(this.from, this.to), new Range(range.from, range.to)};
        }

        if (range.to < this.from) {
            return new Range[]{new Range(range.from, range.to), new Range(this.from, this.to)};
        }

        if (this.from <= range.from && range.from <= this.to && this.to <= range.to) {
            return new Range[]{new Range(this.from, range.to)};
        }

        if (this.from <= range.from && range.to <= this.to) {
            return new Range[]{new Range(this.from, this.to)};
        }

        if (range.from <= this.from && this.from <= range.to && range.to <= this.to) {
            return new Range[]{new Range(range.from, this.to)};
        }

        return new Range[]{new Range(range.from, range.to)};
    }

    public Range[] getDifference(Range range) {
        if (this.to <= range.from || range.to <= this.from) {
            return new Range[]{new Range(this.from, this.to)};
        }

        if (this.from <= range.from && range.from <= this.to && this.to <= range.to) {
            return new Range[]{new Range(this.from, range.from)};
        }

        if (this.from <= range.from && range.to <= this.to) {
            return new Range[]{new Range(this.from, range.from), new Range(range.to, this.to)};
        }

        if (range.from <= this.from && this.from <= range.to && range.to <= this.to) {
            return new Range[]{new Range(range.to, this.to)};
        }

        return new Range[]{};
    }
}