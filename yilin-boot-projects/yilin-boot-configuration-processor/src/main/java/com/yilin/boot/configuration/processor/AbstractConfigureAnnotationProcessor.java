package com.yilin.boot.configuration.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Copyright: Copyright (c) 2023
 * <a href="https://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p>
 * Description: 抽象注解处理器
 *
 * @author jiac
 * @version 2023.0.1 2023/7/3:23:24
 * @since 2023.0.1
 */
public abstract class AbstractConfigureAnnotationProcessor extends AbstractProcessor {

	/**
	 * 注解处理器的核心方法，处理具体的注解.
	 * @param annotations annotations
	 * @param roundEnv processingEnv
	 * @return boolean
	 */
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		try {
			return processImpl(annotations, roundEnv);
		}
		catch (Exception ex) {
			fatalError(ex);
			return false;
		}
	}

	/**
	 * 注解处理 实现类.
	 * @param annotations annotations
	 * @param roundEnv roundEnv
	 * @return boolean
	 */
	protected abstract boolean processImpl(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv);

	protected void log(String msg) {
		if (processingEnv.getOptions().containsKey("debug")) {
			processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
		}
	}

	protected void error(String msg, Element element, AnnotationMirror annotation) {
		processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, element, annotation);
	}

	protected void fatalError(Exception e) {
		// We don't allow exceptions of any kind to propagate to the compiler
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		fatalError(writer.toString());
	}

	protected void fatalError(String msg) {
		processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR: " + msg);
	}

	/**
	 * 返回注解支持的可选项.
	 * @return set&lt;String&gt;
	 */
	@Override
	public Set<String> getSupportedOptions() {
		return super.getSupportedOptions();
	}

	/**
	 * 返回此 Processor 支持的注解类型的名称。结果元素可能是某一受支持注解类型的规范（完全限定）名称。 它也可能是 ” name.” 形式的名称，表示所有以 ”
	 * name.” 开头的规范名称的注解类型集合。 最后，自身表示所有注解类型的集合，包括空集。注意，Processor 不应声明
	 * “*”，除非它实际处理了所有文件；声明不必要的注释可能导致在某些环境中的性能下降.
	 * @return 返回 Processor 支持的注解类型名称
	 */
	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return super.getSupportedAnnotationTypes();
	}

	/**
	 * 返回此注解 Processor 支持的最新的源版本，该方法可以通过注解 @SupportedSourceVersion 指定.
	 * @return /
	 */
	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	/**
	 * 初始化，该方法主要用于一些初始化的操作，通过该方法的参数 ProcessingEnvironment 可以获取一些列有用的工具类.
	 * @param processingEnv 初始化
	 */
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
	}

}
