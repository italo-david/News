package unifbv.news.ClassesAuxiliares;

import android.graphics.Bitmap;

public class BitmapHelper
{
    private Bitmap bitmap = null;
    private static BitmapHelper instance=null;
    
    public BitmapHelper() {}
    
    public static BitmapHelper getInstance() // garantindo a existência de apenas uma instância da classe, assim mantendo  um ponto global de acesso ao objeto
    {
        if(instance == null){
            instance = new BitmapHelper();
        }
        return instance;
    }
    
    public Bitmap getBitmap()
    {
        return bitmap;
    }
    
    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }
}
