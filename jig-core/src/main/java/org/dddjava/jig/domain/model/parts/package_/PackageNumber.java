package org.dddjava.jig.domain.model.parts.package_;

/**
 * パッケージ数
 */
public class PackageNumber {
    long value;

    public PackageNumber(long value) {
        this.value = value;
    }

    public String asText() {
        return Long.toString(value);
    }
}
