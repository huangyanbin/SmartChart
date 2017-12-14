package com.daivd.chart.data.format;

/**
 * 格式化
 * <p>用于横轴和竖轴文本格式化</p>
 * @author huangyanbin
 */

public interface IFormat<T> {

    String format( T t);
}
