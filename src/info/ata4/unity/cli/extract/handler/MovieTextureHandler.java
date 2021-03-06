/*
 ** 2013 July 01
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.unity.cli.extract.handler;

import info.ata4.unity.asset.struct.AssetObjectPath;
import info.ata4.unity.cli.extract.AssetExtractHandler;
import info.ata4.unity.serdes.UnityBuffer;
import info.ata4.unity.serdes.UnityObject;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class MovieTextureHandler extends AssetExtractHandler {
    
    private static final Logger L = Logger.getLogger(MovieTextureHandler.class.getName());

    @Override
    public void extract(AssetObjectPath path, UnityObject obj) throws IOException {
        String name = obj.getValue("m_Name");
        UnityBuffer movieData = obj.getValue("m_MovieData");
        ByteBuffer movieBuffer = movieData.getBuffer();
        
        String ext;
        String fourCC = new String(movieBuffer.array(), 0, 4);
        
        switch (fourCC) {
            case "OggS":
                ext = "ogv";
                break;
                
            default:
                ext = "mov";
                L.log(Level.WARNING, "Unrecognized movie fourCC \"{0}\"", fourCC);
        }
        
        setFileExtension(ext);
        writeFile(movieBuffer, path.pathID, name);
    }
}
