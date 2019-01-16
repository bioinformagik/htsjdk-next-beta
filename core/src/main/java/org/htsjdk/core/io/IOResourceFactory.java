package org.htsjdk.core.io;

import org.htsjdk.core.io.internal.PathResourceProvider;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author Daniel Gomez-Sanchez (magicDGS)
 */
public final class IOResourceFactory {

    // installed providers
    private static volatile List<IOResourceProvider<?, ?>> installedProviders;

    public static final void registerProvider(final IOResourceProvider<?, ?> provider) {
        if (provider.uriIdentifier().equalsIgnoreCase(PathResourceProvider.INSTANCE.uriIdentifier())) {
            throw new IllegalArgumentException();
        }
        getLoadedProviders().add(provider);
    }

    private static final List<IOResourceProvider<?, ?>> getLoadedProviders() {
        if (installedProviders == null) {
            ServiceLoader<IOResourceProvider> sl = ServiceLoader
                    .load(IOResourceProvider.class, ClassLoader.getSystemClassLoader());

            for (IOResourceProvider provider: sl) {

                final String identifier = provider.uriIdentifier();

                // register if it is not the default
                if (!identifier.equalsIgnoreCase(PathResourceProvider.INSTANCE.uriIdentifier())) {
                    // first one takes effec
                    installedProviders.add(provider);
                }
            }
        }
        return installedProviders;
    }

    public static final IOResource<InputStream, OutputStream> getDefault(final String resourceString) {
        return PathResourceProvider.INSTANCE.create(resourceString);
    }

    public static final IOResource<InputStream, OutputStream> getDefault(final URI resourceUri) {
        return PathResourceProvider.INSTANCE.create(resourceUri);
    }

    public IOResource<?, ?> get(final String resourceString) {
        try {
            return get(new URI(resourceString));
        } catch (final URISyntaxException e) {
            // try to use the default
            return getDefault(resourceString);
        }
    }

    public IOResource<?, ?> get(final URI resourceUri) {
        if (resourceUri.getScheme() != null) {
            for (final IOResourceProvider<?, ?> provider: getLoadedProviders()) {
                if (resourceUri.getScheme().equalsIgnoreCase(provider.uriIdentifier())) {
                    return provider.create(resourceUri);
                }
            }
        }
        return getDefault(resourceUri);
    }
}
