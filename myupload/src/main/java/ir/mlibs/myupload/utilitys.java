package ir.mlibs.myupload;

import android.graphics.Color;

/**
 * Created by vali on 2018-04-21.
 */

public class utilitys {

    public static boolean isGallery=true;
    public static boolean showdialogtitle=true;
    public static boolean showdialogmessage=true;
    public static boolean isdialogcancelable=false;
    public static String UploadUrl="";
    public static String DialogTitle="Title";
    public static String DialogMessage="Message";
    public static String DialogUploadTitle="upload";
    public static String DialogcancelTitle="cancel";
    public static String DialogplayTitle="play";
    public static boolean isbroadcastset=false;
    public static String broadcastTitle="";
    public static String CROPPATH="";
    public static boolean CROPUSE=false;
    public static boolean CROPSETTING=false;


    //Croper custoization
    public static boolean d_quality=true;
    public static int C_quality=80;

    public static boolean d_acpectratio=true;
    public static int C_acpectratio_width=1;
    public static int C_acpectratio_height=1;

    public static int C_setMinWidth=40;
    public static boolean d_setMinWidth=true;

    public static int C_setMinHeight=40;
    public static boolean d_setMinHeight=true;

    public static int C_setBorderStrokeWidth=1;
    public static boolean d_setBorderStrokeWidth=true;

    public static int C_setCornerStrokeWidth=1;
    public static boolean d_setCornerStrokeWidth=true;

    public static int C_setGridStrokeWidth=1;
    public static boolean d_setGridStrokeWidth=true;

    public static int C_setBorderColor=Color.WHITE;
    public static boolean d_setBorderColor=true;

    public static int C_setCornerColor=Color.WHITE;
    public static boolean d_setCornerColor=true;

    public static int C_setGridColor=Color.WHITE;
    public static boolean d_setGridColor=true;

    public static int C_setOverlayColor=Color.WHITE;
    public static boolean d_setOverlayColor=true;

    public static float C_setScale=1;
    public static boolean d_setScale=true;

    public static float C_setMinScale=1;
    public static boolean d_setMinScale=true;

    public static float C_setMaxScale=2;
    public static boolean d_setMaxScale=true;

    public static String C_type="png";//png-jpg-webp
    public static boolean d_type=true;

    public static Boolean setImageTranslationEnabled=true;
    public static Boolean d_setImageTranslationEnabled=true;

    public static Boolean setDynamicCrop=true;
    public static Boolean d_setDynamicCrop=true;

    public static Boolean setShouldDrawGrid=true;
    public static Boolean d_setShouldDrawGrid=true;

    public static Boolean setImageScaleEnabled=true;
    public static Boolean d_setImageScaleEnabled=true;

    public static Boolean setCropShapeRectangel=true;//false is oval


}
