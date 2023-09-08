package comp3350.inventracker.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.inventracker.R;

public class AppPersistence
{
    private static final  String ORG_HSQLDB_JDBC_DRIVER = "org.hsqldb.jdbcDriver";
    private static final String dbScriptName           = "SC";
    private static       String dbPathName             = "";
    
    public static void setDBPathName(final String name) {
        try {
            Class.forName(ORG_HSQLDB_JDBC_DRIVER).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbPathName = name;
    }
    
    public static String getDBPathName() {
        return dbPathName;
    }
    
    public static void copyDatabaseToDevice(
        Activity owner,
        Context context,
        AssetManager assetManager,
        String db_path
    ) {
        String[] assetNames;
        File     dataDirectory = context.getDir(db_path, Context.MODE_PRIVATE);
        
        try {
            
            assetNames = assetManager.list(db_path);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = db_path + "/" + assetNames[i];
            }
            
            copyAssetsToDirectory(assetManager, assetNames, dataDirectory);
            
            setDBPathName(dataDirectory.toString() + "/" + dbScriptName);
        }
        catch (final IOException ioe) {
            AlertDialog alertDialog = new AlertDialog.Builder(owner).create();
    
            alertDialog.setTitle(owner.getString(R.string.warning));
            alertDialog.setMessage("Unable to access application data: " + ioe.getMessage());
    
            alertDialog.show();
        }
    }
    
    private static void copyAssetsToDirectory(AssetManager assetManager, String[] assets, File directory)
        throws IOException {
        for (String asset : assets) {
            String[] components = asset.split("/");
            String   copyPath   = directory.toString() + "/" + components[components.length - 1];
            
            char[] buffer = new char[1024];
            int    count;
            
            File outFile = new File(copyPath);
            
            if (!outFile.exists()) {
                InputStreamReader in  = new InputStreamReader(assetManager.open(asset));
                FileWriter        out = new FileWriter(outFile);
                
                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }
                
                out.close();
                in.close();
            }
        }
    }
}
