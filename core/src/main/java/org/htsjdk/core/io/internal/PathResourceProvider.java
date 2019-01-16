package org.htsjdk.core.io.internal;

import org.htsjdk.core.io.IOResource;
import org.htsjdk.core.io.IOResourceFactory;
import org.htsjdk.core.io.IOResourceProvider;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Paths;

/**
 * @author Daniel Gomez-Sanchez (magicDGS)
 */
public class PathResourceProvider implements IOResourceProvider<InputStream, OutputStream> {

    public static final PathResourceProvider INSTANCE = new PathResourceProvider();

    // singleton
    private PathResourceProvider() {}

    @Override
    public String uriIdentifier() {
        return "file"; // files are always handled by java.nio.Path
    }

    @Override
    public IOResource<InputStream, OutputStream> create(String resourceString) {
        return new PathResource(Paths.get(resourceString));
    }

    @Override
    public IOResource<InputStream, OutputStream> create(URI resourceUri) {
        return new PathResource(Paths.get(resourceUri));
    }
}
