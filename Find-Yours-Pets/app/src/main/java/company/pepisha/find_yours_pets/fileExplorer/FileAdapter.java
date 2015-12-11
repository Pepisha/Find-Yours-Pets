package company.pepisha.find_yours_pets.fileExplorer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Comparator;
import java.util.List;

import company.pepisha.find_yours_pets.R;

public class FileAdapter extends ArrayAdapter<File> {

    private LayoutInflater mInflater = null;

    private class FileComparator implements Comparator<File> {
        public int compare(File lhs, File rhs) {
            if (lhs.isDirectory() && rhs.isFile())
                return -1;
            if (lhs.isFile() && rhs.isDirectory())
                return 1;

            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
    }

    public FileAdapter(Context context, int textViewResourceId, List<File> objects) {
        super(context, textViewResourceId, objects);
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View fileView = null;

        if (convertView != null) {
            fileView = convertView;
        } else {
            fileView = mInflater.inflate(R.layout.filelayout, null);
        }

        TextView nameView = (TextView) fileView.findViewById(R.id.fileName);
        TextView sizeView = (TextView) fileView.findViewById(R.id.fileSize);
        ImageView imageView = (ImageView) fileView.findViewById(R.id.fileIcon);

        File item = getItem(position);
        nameView.setTextColor(Color.BLACK);
        nameView.setText(item.getName());
        sizeView.setTextColor(Color.DKGRAY);

        if (item.isDirectory()) {
            imageView.setImageResource(R.drawable.folder);
            sizeView.setText("");
        } else {
            imageView.setImageResource(R.drawable.image);

            float fileSize = (float) item.length() / (1024 * 1024);
            sizeView.setText(String.format("%.2f", fileSize) + " Mo");
        }

        return fileView;
    }

    public void sort () {
        super.sort(new FileComparator());
    }
}