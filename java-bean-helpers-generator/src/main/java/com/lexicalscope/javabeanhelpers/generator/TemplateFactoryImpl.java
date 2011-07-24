package com.lexicalscope.javabeanhelpers.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TemplateFactoryImpl implements TemplateFactory {
	private final String templatesPackage;

	@Inject
	public TemplateFactoryImpl(@Named("templatesPackage") final String templatesPackage) {
		this.templatesPackage = templatesPackage;
	}

	public List<Template> createTemplates() throws IOException {
		final List<Template> result = new ArrayList<Template>();

		final STGroupDir stGroupDir = new STGroupDir(templatesPackage);
		final List<String> templateNames = findEligableTypes(templatesPackage);
		for (final String name : templateNames) {
			if (name.startsWith("Main")) {
				result.add(createTemplate(stGroupDir, name));
			}
		}

		return result;
	}

	private List<String> findEligableTypes(final String basePackage) throws IOException {
		if (basePackage == null) {
			return Collections.emptyList();
		}

		final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

		final List<String> candidates = new ArrayList<String>();
		final String packageSearchPath =
				ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(basePackage) + "/" + "**/*.st";
		final Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
		for (final Resource resource : resources) {
			if (resource.isReadable()) {
				if (isCandidate(resource.getFilename())) {
					candidates.add(resource.getFilename().replace(".st", ""));
				}
			}
		}
		return candidates;
	}

	private String resolveBasePackage(final String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}

	private boolean isCandidate(final String string) {
		return string.startsWith("Main") && !string.endsWith("Name.st");
	}

	private Template createTemplate(final STGroupDir stGroupDir, final String name) {
		final StringTemplateFactory nameTemplateFactory = new StringTemplateFactory() {
			public StringTemplate create(final BeanModel beanModel) {
				final ST st = stGroupDir.getInstanceOf(name + "Name");
				st.add("bean", beanModel);
				return new StringTemplateImpl(st);
			}
		};

		final StringTemplateFactory contentTemplateFactory = new StringTemplateFactory() {
			public StringTemplate create(final BeanModel beanModel) {
				final ST st = stGroupDir.getInstanceOf(name);
				st.add("bean", beanModel);
				return new StringTemplateImpl(st);
			}
		};

		return new TemplateImpl(nameTemplateFactory, contentTemplateFactory);
	}
}
