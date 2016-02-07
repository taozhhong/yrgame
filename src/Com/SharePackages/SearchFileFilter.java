package Com.SharePackages;

import java.io.File;
import java.io.FileFilter;

/**
 * Created with IntelliJ IDEA.
 * User: T410
 * Date: 12-6-26
 * Time: 下午4:16
 * To change this template use File | Settings | File Templates.
 */
public class SearchFileFilter implements FileFilter {
    private String extension;

    public SearchFileFilter (String extension) {
        this.extension = extension;
    }

    public boolean accept (File file) {
        if (file.isDirectory ()) {
            return false;
        } String name = file.getName ();
        return this.extension.contains (name);

        // find the last
        /*int index = name.lastIndexOf(".");
        if(index == -1)
        {
            return false;
        }
        else
        {
            if(index == name.length( ) -1)
            {
                return false;
            }
            else
            {
                return this.extension.equals(name.substring(index+1));
            }
        }*/
    }
}
