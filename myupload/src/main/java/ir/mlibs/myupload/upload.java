package ir.mlibs.myupload;

import android.content.Context;
import android.content.Intent;

/**
 * Created by vali on 2018-04-21.
 */

public class upload {

    public static void UploadVideo(Context mcontext
            , boolean isGallery
            , String UploadUrl
            , String DialogTitle
            , String DialogMessage
            , String DialogUploadTitle
            , String DialogcancelTitle
            , String DialogplayTitle
            , boolean isbroadcastset
            , String broadcastTitle
    ) {
        utilitys.isGallery = isGallery;
        utilitys.UploadUrl = UploadUrl;
        utilitys.DialogTitle = DialogTitle;
        utilitys.DialogMessage = DialogMessage;
        utilitys.DialogUploadTitle = DialogUploadTitle;
        utilitys.DialogcancelTitle = DialogcancelTitle;
        utilitys.DialogplayTitle = DialogplayTitle;
        utilitys.isbroadcastset = isbroadcastset;
        utilitys.broadcastTitle = broadcastTitle;

        Intent intent = new Intent(mcontext, UploadVideoActivity.class);
        mcontext.startActivity(intent);
    }

    public static void UploadImage(Context mcontext
            , boolean isGallery
            , String UploadUrl
            , String DialogTitle
            , String DialogMessage
            , String DialogUploadTitle
            , String DialogcancelTitle
            , String DialogplayTitle
            , boolean isbroadcastset
            , String broadcastTitle
    ) {
        utilitys.isGallery = isGallery;
        utilitys.UploadUrl = UploadUrl;
        utilitys.DialogTitle = DialogTitle;
        utilitys.DialogMessage = DialogMessage;
        utilitys.DialogUploadTitle = DialogUploadTitle;
        utilitys.DialogcancelTitle = DialogcancelTitle;
        utilitys.DialogplayTitle = DialogplayTitle;
        utilitys.isbroadcastset = isbroadcastset;
        utilitys.broadcastTitle = broadcastTitle;
        utilitys.CROPUSE = false;

        Intent intent = new Intent(mcontext, UploadImageActivity.class);
        mcontext.startActivity(intent);
    }

    public static void UploadImageWithDefaultCropSetting(Context mcontext
            , boolean isGallery
            , String UploadUrl
            , String DialogTitle
            , String DialogMessage
            , String DialogUploadTitle
            , String DialogcancelTitle
            , String DialogplayTitle
            , boolean isbroadcastset
            , String broadcastTitle, boolean setCropShapeRectangel
    ) {
        utilitys.isGallery = isGallery;
        utilitys.UploadUrl = UploadUrl;
        utilitys.DialogTitle = DialogTitle;
        utilitys.DialogMessage = DialogMessage;
        utilitys.DialogUploadTitle = DialogUploadTitle;
        utilitys.DialogcancelTitle = DialogcancelTitle;
        utilitys.DialogplayTitle = DialogplayTitle;
        utilitys.isbroadcastset = isbroadcastset;
        utilitys.broadcastTitle = broadcastTitle;
        utilitys.CROPUSE = true;
        utilitys.CROPSETTING = false;

        utilitys.d_quality = true;
        utilitys.d_acpectratio = true;
        utilitys.d_setMinWidth = true;
        utilitys.d_setMinHeight = true;
        utilitys.d_setBorderStrokeWidth = true;
        utilitys.d_setCornerStrokeWidth = true;
        utilitys.d_setGridStrokeWidth = true;
        utilitys.d_setBorderColor = true;
        utilitys.d_setCornerColor = true;
        utilitys.d_setGridColor = true;
        utilitys.d_setOverlayColor = true;
        utilitys.d_setScale = true;
        utilitys.d_setMinScale = true;
        utilitys.d_setMaxScale = true;
        utilitys.d_type = true;
        utilitys.d_setImageTranslationEnabled = true;
        utilitys.d_setDynamicCrop = true;
        utilitys.d_setShouldDrawGrid = true;
        utilitys.d_setImageScaleEnabled = true;
        utilitys.setCropShapeRectangel = setCropShapeRectangel;

        Intent intent = new Intent(mcontext, UploadImageActivity.class);
        mcontext.startActivity(intent);
    }

    public static void UploadImageShowCropSetting(Context mcontext
            , boolean isGallery
            , String UploadUrl
            , String DialogTitle
            , String DialogMessage
            , String DialogUploadTitle
            , String DialogcancelTitle
            , String DialogplayTitle
            , boolean isbroadcastset
            , String broadcastTitle
            ,boolean setCropShapeRectangel
    ) {
        utilitys.isGallery = isGallery;
        utilitys.UploadUrl = UploadUrl;
        utilitys.DialogTitle = DialogTitle;
        utilitys.DialogMessage = DialogMessage;
        utilitys.DialogUploadTitle = DialogUploadTitle;
        utilitys.DialogcancelTitle = DialogcancelTitle;
        utilitys.DialogplayTitle = DialogplayTitle;
        utilitys.isbroadcastset = isbroadcastset;
        utilitys.broadcastTitle = broadcastTitle;
        utilitys.CROPUSE = true;
        utilitys.CROPSETTING = true;

        utilitys.d_quality = true;
        utilitys.d_acpectratio = true;
        utilitys.d_setMinWidth = true;
        utilitys.d_setMinHeight = true;
        utilitys.d_setBorderStrokeWidth = true;
        utilitys.d_setCornerStrokeWidth = true;
        utilitys.d_setGridStrokeWidth = true;
        utilitys.d_setBorderColor = true;
        utilitys.d_setCornerColor = true;
        utilitys.d_setGridColor = true;
        utilitys.d_setOverlayColor = true;
        utilitys.d_setScale = true;
        utilitys.d_setMinScale = true;
        utilitys.d_setMaxScale = true;
        utilitys.d_type = true;
        utilitys.d_setImageTranslationEnabled = true;
        utilitys.d_setDynamicCrop = true;
        utilitys.d_setShouldDrawGrid = true;
        utilitys.d_setImageScaleEnabled = true;
        utilitys.setCropShapeRectangel = setCropShapeRectangel;

        Intent intent = new Intent(mcontext, UploadImageActivity.class);
        mcontext.startActivity(intent);
    }

    public static void UploadImageWithCustomizeCropSetting(Context mcontext
            , boolean isGallery
            , String UploadUrl
            , String DialogTitle
            , String DialogMessage
            , String DialogUploadTitle
            , String DialogcancelTitle
            , String DialogplayTitle
            , boolean isbroadcastset
            , String broadcastTitle
            , boolean UseCrop
            , boolean default_quality
            , int C_quality
            , boolean default_acpectratio
            , int C_acpectratio_width
            , int C_acpectratio_height
            , boolean default_setMinWidth
            , int C_setMinWidth
            , boolean default_setMinHeight
            , int C_setMinHeight
            , boolean default_setBorderStrokeWidth
            , int C_setBorderStrokeWidth
            , boolean default_setCornerStrokeWidth
            , int C_setCornerStrokeWidth
            , boolean default_setGridStrokeWidth
            , int C_setGridStrokeWidth
            , boolean default_setBorderColor
            , int C_setBorderColor
            , boolean default_setCornerColor
            , int C_setCornerColor
            , boolean default_setGridColor
            , int C_setGridColor
            , boolean default_setOverlayColor
            , int C_setOverlayColor
            , boolean default_setScale
            , float C_setScale
            , boolean default_setMinScale
            , float C_setMinScale
            , boolean default_setMaxScale
            , float C_setMaxScale
            , boolean default_type
            , String C_type
            , boolean default_setImageTranslationEnabled
            , boolean setImageTranslationEnabled
            , boolean default_setDynamicCrop
            , boolean setDynamicCrop
            , boolean default_setShouldDrawGrid
            , boolean setShouldDrawGrid
            , boolean default_setImageScaleEnabled
            , boolean setImageScaleEnabled
            , boolean setCropShapeRectangel
    ) {


        utilitys.d_quality = default_quality;
        utilitys.C_quality = C_quality;
        utilitys.d_acpectratio = default_acpectratio;
        utilitys.C_acpectratio_width = C_acpectratio_width;
        utilitys.C_acpectratio_height = C_acpectratio_height;
        utilitys.C_setMinWidth = C_setMinWidth;
        utilitys.d_setMinWidth = default_setMinWidth;
        utilitys.C_setMinHeight = C_setMinHeight;
        utilitys.d_setMinHeight = default_setMinHeight;
        utilitys.C_setBorderStrokeWidth = C_setBorderStrokeWidth;
        utilitys.d_setBorderStrokeWidth = default_setBorderStrokeWidth;
        utilitys.C_setCornerStrokeWidth = C_setCornerStrokeWidth;
        utilitys.d_setCornerStrokeWidth = default_setCornerStrokeWidth;
        utilitys.C_setGridStrokeWidth = C_setGridStrokeWidth;
        utilitys.d_setGridStrokeWidth = default_setGridStrokeWidth;
        utilitys.C_setBorderColor = C_setBorderColor;
        utilitys.d_setBorderColor = default_setBorderColor;
        utilitys.C_setCornerColor = C_setCornerColor;
        utilitys.d_setCornerColor = default_setCornerColor;
        utilitys.C_setGridColor = C_setGridColor;
        utilitys.d_setGridColor = default_setGridColor;
        utilitys.C_setOverlayColor = C_setOverlayColor;
        utilitys.d_setOverlayColor = default_setOverlayColor;
        utilitys.C_setScale = C_setScale;
        utilitys.d_setScale = default_setScale;
        utilitys.C_setMinScale = C_setMinScale;
        utilitys.d_setMinScale = default_setMinScale;
        utilitys.C_setMaxScale = C_setMaxScale;
        utilitys.d_setMaxScale = default_setMaxScale;
        utilitys.C_type = C_type;
        utilitys.d_type = default_type;
        utilitys.setImageTranslationEnabled = setImageTranslationEnabled;
        utilitys.d_setImageTranslationEnabled = default_setImageTranslationEnabled;
        utilitys.setDynamicCrop = setDynamicCrop;
        utilitys.d_setDynamicCrop = default_setDynamicCrop;
        utilitys.setShouldDrawGrid = setShouldDrawGrid;
        utilitys.d_setShouldDrawGrid = default_setShouldDrawGrid;
        utilitys.setImageScaleEnabled = setImageScaleEnabled;
        utilitys.d_setImageScaleEnabled = default_setImageScaleEnabled;
        utilitys.setCropShapeRectangel = setCropShapeRectangel;


        utilitys.isGallery = isGallery;
        utilitys.UploadUrl = UploadUrl;
        utilitys.DialogTitle = DialogTitle;
        utilitys.DialogMessage = DialogMessage;
        utilitys.DialogUploadTitle = DialogUploadTitle;
        utilitys.DialogcancelTitle = DialogcancelTitle;
        utilitys.DialogplayTitle = DialogplayTitle;
        utilitys.isbroadcastset = isbroadcastset;
        utilitys.broadcastTitle = broadcastTitle;
        utilitys.CROPUSE = UseCrop;
        utilitys.CROPSETTING = false;

        Intent intent = new Intent(mcontext, UploadImageActivity.class);
        mcontext.startActivity(intent);
    }
}
