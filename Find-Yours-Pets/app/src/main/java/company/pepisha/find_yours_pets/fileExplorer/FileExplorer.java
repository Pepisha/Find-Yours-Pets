package company.pepisha.find_yours_pets.fileExplorer;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import company.pepisha.find_yours_pets.R;
import company.pepisha.find_yours_pets.photo.PhotoConfirmationWindow;

public class FileExplorer extends ListActivity {

    private ListView fileList = null;

    private FileAdapter adapter = null;

    private boolean atRoot = false;

    private File currentFile = null;

    private void clearList() {
        if (!adapter.isEmpty()) {
            adapter.clear();
        }
    }

    private void updateDirectory(File pFile) {
        setTitle(pFile.getAbsolutePath());

        atRoot = false;
        currentFile = pFile;

        clearList();

        // On récupère la liste des fichiers du nouveau répertoire
        File[] fichiers = currentFile.listFiles(new ImageFileFilter());

        if (fichiers != null) {
            for (File f : fichiers) {
                adapter.add(f);
            }

            adapter.sort();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Si on a appuyé sur le retour arrière
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            File parent = currentFile.getParentFile();
            if (parent != null) {
                updateDirectory(parent);
            }
            else {
                // Sinon, si c'est la première fois qu'on fait un retour arrière
                if (!atRoot) {
                    Toast.makeText(this, "Nous sommes déjà à la racine ! Cliquez une seconde fois pour quitter", Toast.LENGTH_SHORT).show();
                    atRoot = true;
                } else {
                    finish();
                }
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void displayConfirmationWindow() {
        final PhotoConfirmationWindow alertDialog = new PhotoConfirmationWindow(this);

        alertDialog.setNegativeClickListener(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.cancel();
            }
        });

        alertDialog.setPositiveClickListener(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fileList = getListView();

        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            //mEmpty = (TextView) mList.getEmptyView();
            //mEmpty.setText("Vous ne pouvez pas accéder aux fichiers");
        } else {
            currentFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            setTitle(currentFile.getAbsolutePath());

            File[] files = currentFile.listFiles(new ImageFileFilter());

            // On transforme le tableau en une structure de données de taille variable
            List<File> list = new ArrayList<File>();
            for (File f : files) {
                list.add(f);
            }

            adapter = new FileAdapter(this, R.layout.filelayout, list);
            fileList.setAdapter(adapter);

            adapter.sort();

            fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                    File file = (File) adapter.getItemAtPosition(position);

                    if (file.isDirectory()) {
                        updateDirectory(file);
                    } else {
                        displayConfirmationWindow();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_explorer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
