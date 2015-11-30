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

        TextView textView = (TextView) fileView.findViewById(R.id.fileName);
        ImageView imageView = (ImageView) fileView.findViewById(R.id.fileIcon);

        File item = getItem(position);
        textView.setTextColor(Color.BLACK);
        textView.setText(item.getName());

        if (item.isDirectory()) {
            imageView.setImageResource(R.drawable.folder);
        } else {
            imageView.setImageResource(R.drawable.image);
        }

        return fileView;
    }

    public void sort () {
        super.sort(new FileComparator());
    }
}