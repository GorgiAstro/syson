/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.syson.application.configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.emfjson.resource.JsonResourceFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Load all KerML/SysML standard libraries at SysON start.
 * 
 * @author arichard
 */
@Configuration
public class SysMLStandardLibrariesConfiguration {

    public static final String SYSML_LIBRARY_SCHEME = "sysmllibrary";
    
    public static final String KERML_LIBRARY_SCHEME = "kermllibrary";

    private final Logger logger = LoggerFactory.getLogger(SysMLStandardLibrariesConfiguration.class);

    private ResourceSet librariesResourceSet;

    public SysMLStandardLibrariesConfiguration() {
        Instant start = Instant.now();
        librariesResourceSet = new ResourceSetImpl();
        loadResourcesFrom(librariesResourceSet, "kerml.libraries/", KERML_LIBRARY_SCHEME);
        loadResourcesFrom(librariesResourceSet, "sysml.libraries/", SYSML_LIBRARY_SCHEME);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        this.logger.info("SysML standard libraries initialization completed in {} ms", timeElapsed);
    }

    public ResourceSet getLibrariesResourceSet() {
        return librariesResourceSet;
    }

    private void loadResourcesFrom(ResourceSet resourceSet, String librariesDirectoryPath, String scheme) {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            String currentLibraryName = "";
            org.springframework.core.io.Resource[] resources = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + librariesDirectoryPath + "*." + JsonResourceFactoryImpl.EXTENSION);
            for (org.springframework.core.io.Resource resource : resources) {
                String libraryFilePath = resource.getFilename();
                currentLibraryName = FilenameUtils.getBaseName(libraryFilePath);
                ClassPathResource classPathResource = new ClassPathResource(librariesDirectoryPath + libraryFilePath);
                String path = classPathResource.getPath();
                URI uri = URI.createURI(scheme + ":///" + UUID.nameUUIDFromBytes(path.getBytes()));
                Resource emfResource = null;
                Optional<Resource> existingEMFResource = resourceSet.getResources().stream().filter(r -> uri.equals(r.getURI())).findFirst();
                if (existingEMFResource.isEmpty()) {
                    emfResource = new JSONResourceFactory().createResource(uri);
                    try (var inputStream = new ByteArrayInputStream(classPathResource.getContentAsByteArray())) {
                        resourceSet.getResources().add(emfResource);
                        emfResource.load(inputStream, Map.of());
                        emfResource.eAdapters().add(new ResourceMetadataAdapter(currentLibraryName));
                    } catch (IOException e) {
                        this.logger.warn("An error occured while loading {} sysml standard library: {}.", currentLibraryName, e.getMessage());
                        resourceSet.getResources().remove(emfResource);
                    }
                }
                this.logger.info("Loading {} sysml standard library", currentLibraryName);
            }
        } catch (IOException e) {
            this.logger.warn("An error occurred while accessing resources from sysml standard libraries directory: {}.", e.getMessage());
        }
    }
}
