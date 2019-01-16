package org.htsjdk.core.io.internal;

import org.htsjdk.core.io.IOResource;
import org.htsjdk.core.io.IOResourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Daniel Gomez-Sanchez (magicDGS)
 */
class PathResource implements IOResource<InputStream, OutputStream> {

    private final Path path;

    PathResource(final Path path) {
        this.path = path;
    }

    @Override
    public String uriIdentifier() {
        return PathResourceProvider.INSTANCE.uriIdentifier();
    }

    @Override
    public InputStream openReader() throws IOException {
        return Files.newInputStream(path);
    }

    @Override
    public OutputStream openWriter() throws IOException {
        return Files.newOutputStream(path);
    }
}
