package org.dddjava.jig.domain.model.implementation;

/**
 * 解析コンテキスト
 *
 * TODO 実装をどう特徴付けるかの話なのでimplementationからは取り除いた方が良さそう
 */
public interface ImplementationAnalyzeContext {

    boolean isModel(Implementation implementation);

    boolean isRepository(Implementation implementation);
}
