package dev.aether.client.module;

import java.util.List;

public abstract class Setting<T> {
    private final String name;
    private final String description;
    private T value;

    protected Setting(String name, String description, T value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }
    public String name() { return name; }
    public String description() { return description; }
    public T get() { return value; }
    public void set(T value) { this.value = value; }

    public static final class Bool extends Setting<Boolean> {
        public Bool(String name, String description, boolean value) { super(name, description, value); }
        public void toggle() { set(!get()); }
    }
    public static final class Number extends Setting<Double> {
        private final double min, max, step;
        public Number(String name, String description, double value, double min, double max, double step) {
            super(name, description, value);
            this.min = min; this.max = max; this.step = step;
        }
        public double min() { return min; }
        public double max() { return max; }
        public double step() { return step; }
        public void adjust(double direction) {
            double next = Math.max(min, Math.min(max, get() + direction * step));
            set(Math.round(next / step) * step);
        }
    }
    public static final class Choice extends Setting<String> {
        private final List<String> choices;
        public Choice(String name, String description, String value, List<String> choices) {
            super(name, description, value); this.choices = List.copyOf(choices);
        }
        public List<String> choices() { return choices; }
        public void next() {
            int i = choices.indexOf(get());
            set(choices.get((i + 1) % choices.size()));
        }
    }
}
