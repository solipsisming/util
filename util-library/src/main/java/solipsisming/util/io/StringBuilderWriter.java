package solipsisming.util.io;

import java.io.Serializable;
import java.io.Writer;

/**
 * 可写的stringBuffer</p>
 * 创建于 2015-07-04 13:43:09
 *
 * @author 洪东明
 * @version 1.0
 */
class StringBuilderWriter extends Writer implements Serializable {

    private final StringBuilder builder;

    public StringBuilderWriter() {
        this.builder = new StringBuilder();
    }

    public StringBuilderWriter(int capacity) {
        this.builder = new StringBuilder(capacity);
    }

    public StringBuilderWriter(StringBuilder builder) {
        this.builder = builder != null ? builder : new StringBuilder();
    }

    @Override
    public Writer append(char value) {
        builder.append(value);
        return this;
    }

    @Override
    public Writer append(CharSequence value) {
        builder.append(value);
        return this;
    }

    @Override
    public Writer append(CharSequence value, int start, int end) {
        builder.append(value, start, end);
        return this;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() {
    }


    @Override
    public void write(String value) {
        if (value != null) {
            builder.append(value);
        }
    }

    @Override
    public void write(char[] value, int offset, int length) {
        if (value != null) {
            builder.append(value, offset, length);
        }
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}