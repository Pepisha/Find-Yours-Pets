package company.pepisha.find_yours_pets.fileExplorer;


import java.io.File;
import java.io.FileFilter;

public class ImageFileFilter implements FileFilter {

    private final String[] fileExtensions =
            new String[] {"jpg", "png", "gif"};

    public boolean accept(File file) {
        for (String extension : fileExtensions) {
            if (file.isDirectory() || file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }

        return false;
    }
}
