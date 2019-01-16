package org.htsjdk.core.io;

import java.io.IOException;
import java.net.URI;

/**
 * IO resource for HTSJDK.
 *
 * @author Daniel Gomez-Sanchez (magicDGS)
 */
public interface IOResource<READER, WRITER> {

    public String uriIdentifier();

    public READER openReader() throws IOException;

    public WRITER openWriter() throws IOException;

}
