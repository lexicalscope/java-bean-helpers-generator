package com.lexicalscope.javabeanhelpers.generator.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lexicalscope.javabeanhelpers.generator.HelperGenerator;
import com.lexicalscope.javabeanhelpers.generator.JavaBeansHelperGeneratorModule;

/**
 * @goal generate-helpers
 * @phase generate-sources
 * @requiresDependencyResolution compile
 */
public class JavaBeanHelpersGeneratorMojo
		extends AbstractMojo {

	/**
	 * @parameter expression="${project}"
	 * @required
	 */
	private MavenProject project;

	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${output-directory}"
	 *            default-value="${project.build.directory}/generated-sources/java"
	 * 
	 * @required
	 */
	private File outputDirectory;

	/**
	 * Packages to generate
	 * 
	 * @parameter alias="packages"
	 * 
	 * @required
	 */
	private String[] packages;

	private ClassLoader classLoader;

	public void execute()
			throws MojoExecutionException {
		if (!outputDirectory.exists()) {
			outputDirectory.mkdirs();
		}

		project.addCompileSourceRoot(outputDirectory.getAbsolutePath());

		final Injector injector = Guice.createInjector(new JavaBeansHelperGeneratorModule(outputDirectory));
		final HelperGenerator helperGenerator = injector.getInstance(HelperGenerator.class);

		final List<Class<?>> classes = new ArrayList<Class<?>>();
		for (final String packag3 : packages) {
			getLog().info("loading classes in package " + packag3);
			try {
				classes.addAll(findEligableTypes(packag3));
			} catch (final IOException e) {
				throw new MojoExecutionException("unable to load classes in packages " + packag3, e);
			} catch (final ClassNotFoundException e) {
				throw new MojoExecutionException("unable to load classes in packages " + packag3, e);
			} catch (final DependencyResolutionRequiredException e) {
				throw new MojoExecutionException("unable to resolve dependencies " + packag3, e);
			}
		}
		for (final Class<?> klass : classes) {
			try {
				getLog().info("generating helpers for " + klass.getName());
				helperGenerator.generateHelpers(klass);
			} catch (final IOException e) {
				throw new MojoExecutionException("unable to generate helpers for " + klass.getName(), e);
			}
		}
	}

	private List<Class<?>> findEligableTypes(final String basePackage)
			throws IOException,
			ClassNotFoundException,
			DependencyResolutionRequiredException {
		if (basePackage == null) {
			return Collections.emptyList();
		}

		final ResourcePatternResolver resourcePatternResolver =
				new PathMatchingResourcePatternResolver(getClassLoader());
		final MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

		final List<Class<?>> candidates = new ArrayList<Class<?>>();
		final String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
								resolveBasePackage(basePackage) + "/" + "**/*.class";
		final Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
		for (final Resource resource : resources) {
			if (resource.isReadable()) {
				final MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
				final Class<?> loadedClass =
						getClassLoader().loadClass(metadataReader.getClassMetadata().getClassName());
				if (isCandidate(loadedClass)) {
					getLog().info("found resource " + resource);
					candidates.add(loadedClass);
				}
			}
		}
		return candidates;
	}

	private ClassLoader getClassLoader() throws MalformedURLException, DependencyResolutionRequiredException {
		synchronized (JavaBeanHelpersGeneratorMojo.class) {
			if (classLoader == null) {
				final List<URL> urls = new ArrayList<URL>();
				for (final Object object : project.getCompileClasspathElements()) {
					if (!object.equals(project.getBuild().getOutputDirectory())) {
						final String path = (String) object;
						urls.add(new File(path).toURI().toURL());
					}
				}
				classLoader = new URLClassLoader(urls.toArray(new URL[] {}));
			}
			return classLoader;
		}
	}

	private String resolveBasePackage(final String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}

	private boolean isCandidate(final Class<?> loadedClass) throws ClassNotFoundException {
		try {
			// if (c.getAnnotation(XmlRootElement.class) != null) {
			return true;
			// }
		} catch (final Throwable e) {
		}
		return false;
	}
}
