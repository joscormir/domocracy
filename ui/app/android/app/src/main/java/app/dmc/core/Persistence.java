package app.dmc.core;

import android.content.Context;
import org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Joscormir on 16/02/2015.
 */

public class Persistence {
    //-----------------------------------------------------------------------------------------------------------------
    static public void init(Context _context){
        mContext = _context;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public static Persistence get(){
        assert mContext != null;
        if(instance == null) instance = new Persistence(mContext);
        return instance;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject getData(String _fileName){
       JSONObject json = null;
        File file = new File(mContext.getExternalFilesDir(null), _fileName + ".json");
            try {
                InputStream in = new FileInputStream(file);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] data = new byte[in.available()];
                in.read(data);
                out.write(data);
                String outToString = new String(out.toByteArray());
                json = new JSONObject(outToString);
            } catch (Exception e) {
                e.printStackTrace();
            }
       return json;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public boolean putData(String _fileName, JSONObject _json){
        boolean success = false;
        File file = new File(mContext.getExternalFilesDir(null), _fileName + ".json");
        try{
            InputStream in = new ByteArrayInputStream(_json.toString().getBytes());
            OutputStream out = new FileOutputStream(file);
            byte[] data = new byte[in.available()];
            in.read(data);
            out.write(data);
            in.close();
            out.close();
            success = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return success;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //Private interface
    private Persistence(Context _context){

    }

    //-----------------------------------------------------------------------------------------------------------------
    private static Persistence  instance = null;
    private static Context      mContext = null;

}