package org.dddjava.jig.domain.model.parts.class_.method;

/**
 * 分岐数
 */
public class DecisionNumber {
    int value;

    public DecisionNumber(int value) {
        this.value = value;
    }

    public String asText() {
        return Integer.toString(value);
    }

    public boolean notZero() {
        return value > 0;
    }

    public double intValue() {
        return value;
    }
}
