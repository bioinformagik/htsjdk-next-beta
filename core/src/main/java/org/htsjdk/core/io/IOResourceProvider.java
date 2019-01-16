package org.htsjdk.core.io;

import java.net.URI;

/**
 * Service class.
 *
 * @author Daniel Gomez-Sanchez (magicDGS)
 */
public interface IOResourceProvider<READER, WRITER> {

    public String uriIdentifier();

    public IOResource<READER, WRITER> create(final String resourceString);

	public IOResource<READER, WRITER> create(final URI resourceUri);
}
