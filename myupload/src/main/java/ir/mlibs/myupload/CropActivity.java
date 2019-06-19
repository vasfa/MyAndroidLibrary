package ir.mlibs.myupload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.steelkiwi.cropiwa.AspectRatio;
import com.steelkiwi.cropiwa.CropIwaView;
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig;
import com.steelkiwi.cropiwa.config.InitialPosition;
import com.steelkiwi.cropiwa.image.CropIwaResultReceiver;
import com.steelkiwi.cropiwa.shape.CropIwaOvalShape;
import com.steelkiwi.cropiwa.shape.CropIwaRectShape;
import com.wang.avi.AVLoadingIndicatorView;
import com.yarolegovich.mp.MaterialPreferenceScreen;

import ir.mlibs.myupload.cropiwa.config.CropViewConfigurator;

import static ir.mlibs.myupload.utilitys.CROPPATH;
import static ir.mlibs.myupload.utilitys.C_setMinWidth;
import static ir.mlibs.myupload.utilitys.d_setMinScale;

public class CropActivity extends AppCompatActivity {
    private CropIwaView cropView;
    private CropViewConfigurator configurator;
    CropIwaResultReceiver resultReceiver;
    ImageView iv_ok;
    AVLoadingIndicatorView avis;
    // private static final String EXTRA_URI = "http://wizlock.ir/files/2018_06_20_10_06_30___9794c016-31c0-4c8f-a5bc-11e335907042___222.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        try {
            avis = (AVLoadingIndicatorView) findViewById(R.id.aviLoading);
            avis.hide();
            iv_ok = (ImageView) findViewById(R.id.imageView_ok);
            iv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    avis.show();
                    //cropView.crop(configurator.getSelectedSaveConfig());
                    if(utilitys.d_quality)
                    {
                        if(utilitys.d_type)
                        {
                            cropView.crop(configurator.getSelectedSaveConfig());
                        }else {
                            if(utilitys.C_type.equals("png"))
                            {
                                cropView.crop(new CropIwaSaveConfig.Builder(configurator.getSelectedSaveConfig().getDstUri())
                                        .setCompressFormat(Bitmap.CompressFormat.PNG)
                                        .build());
                            }else if(utilitys.C_type.equals("jpg"))
                            {
                                cropView.crop(new CropIwaSaveConfig.Builder(configurator.getSelectedSaveConfig().getDstUri())
                                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                        .build());
                            }else if(utilitys.C_type.equals("webp"))
                            {
                                cropView.crop(new CropIwaSaveConfig.Builder(configurator.getSelectedSaveConfig().getDstUri())
                                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                        .build());
                            }

                        }

                    }else{
                        if(utilitys.d_type)
                        {
                            cropView.crop(new CropIwaSaveConfig.Builder(configurator.getSelectedSaveConfig().getDstUri())
                                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                                    .setQuality(utilitys.C_quality) //Hint for lossy compression formats
                                    .build());
                        }else {
                            if(utilitys.C_type.equals("png"))
                            {
                                cropView.crop(new CropIwaSaveConfig.Builder(configurator.getSelectedSaveConfig().getDstUri())
                                        .setCompressFormat(Bitmap.CompressFormat.PNG)
                                        .setQuality(utilitys.C_quality) //Hint for lossy compression formats
                                        .build());
                            }else if(utilitys.C_type.equals("jpg"))
                            {
                                cropView.crop(new CropIwaSaveConfig.Builder(configurator.getSelectedSaveConfig().getDstUri())
                                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                        .setQuality(utilitys.C_quality) //Hint for lossy compression formats
                                        .build());
                            }else if(utilitys.C_type.equals("webp"))
                            {
                                cropView.crop(new CropIwaSaveConfig.Builder(configurator.getSelectedSaveConfig().getDstUri())
                                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                        .setQuality(utilitys.C_quality) //Hint for lossy compression formats
                                        .build());
                            }

                        }
                    }

                }
            });

            Bundle extra = getIntent().getExtras();
            Uri imageUri = Uri.parse("file:///" + extra.getString("IMAGE"));
            cropView = (CropIwaView) findViewById(R.id.crop_view);
            cropView.setImageUri(imageUri);


            if (!utilitys.d_setImageTranslationEnabled) {
                // اگر false باشد اجازه نمیدهد عکس را جابجا کنید
                //Enable finger drag to translate an image.

                cropView.configureImage()
                        .setImageTranslationEnabled(utilitys.setImageTranslationEnabled)
                        .apply();
            }

            if(!utilitys.d_setDynamicCrop)
            {
                //
                //Enable user to resize a crop area. Default is true.
                cropView.configureOverlay()
                        .setDynamicCrop(utilitys.setDynamicCrop)
                        .apply();
            }


            if(!utilitys.d_setShouldDrawGrid)
            {
                //
                //Draw a 3x3 grid. Default is true.
                cropView.configureOverlay()
                        .setShouldDrawGrid(utilitys.setShouldDrawGrid)
                        .apply();
            }


            if(!utilitys.d_acpectratio)
            {
                //
                //Set an initial crop area's aspect ratio.
                //3.2 4.3 5.4 1.1 4.5
                cropView.configureOverlay()
                        .setAspectRatio(new AspectRatio(utilitys.C_acpectratio_width,utilitys.C_acpectratio_height))
                        .setAspectRatio(AspectRatio.IMG_SRC) //If you want crop area to be equal to the dimensions of an image
                        .apply();
            }



            if(!utilitys.d_setScale)
            {
                //
                //Set current scale of the image.
                //Value is a float from 0.01f to 1
                cropView.configureImage()
                        .setScale(utilitys.C_setScale)
                        .apply();
            }



            if(!utilitys.d_setImageScaleEnabled)
            {
                //
                //Enable pinch gesture to scale an image.
                cropView.configureImage()
                        .setImageScaleEnabled(utilitys.setImageScaleEnabled)
                        .apply();
            }



            //نوع کراپ را تعیین میکند یا دایره ای(هر شکلی که گرد باشد) یا چهار ضلعی
            //Choosing from default crop area shapes. Default is rectangle.
            if(utilitys.setCropShapeRectangel)
            {
                cropView.configureOverlay()
                        .setCropShape(new CropIwaRectShape(cropView.configureOverlay()))
                        .apply();
            }else
            {
                cropView.configureOverlay()
                    .setCropShape(new CropIwaOvalShape(cropView.configureOverlay()))
                    .apply();

            }



            if(!utilitys.d_setMinScale)
            {
                //You can set a min-max scale. Default min is 0.7, default max is 3.
                cropView.configureImage()
                        .setMinScale(utilitys.C_setMinScale)
                        .apply();
            }
            if(!utilitys.d_setMaxScale)
            {
                //You can set a min-max scale. Default min is 0.7, default max is 3.
                cropView.configureImage()
                        .setMaxScale(utilitys.C_setMaxScale)
                        .apply();
            }



            if(!utilitys.d_setMinWidth)
            {
                //Crop area min size.
                cropView.configureOverlay()
                        .setMinWidth(utilitys.C_setMinWidth)
                        .apply();
            }
            if(!utilitys.d_setMinHeight)
            {
                //Crop area min size.
                cropView.configureOverlay()
                        .setMinHeight(utilitys.C_setMinHeight)
                        .apply();
            }



            if(!utilitys.d_setBorderStrokeWidth)
            {
                //Dimensions.
                cropView.configureOverlay()
                        .setBorderStrokeWidth(utilitys.C_setBorderStrokeWidth)
                        .apply();
            }
            if(!utilitys.d_setCornerStrokeWidth)
            {
                //Dimensions.
                cropView.configureOverlay()
                        .setCornerStrokeWidth(utilitys.C_setCornerStrokeWidth)
                        .apply();
            }
            if(!utilitys.d_setGridStrokeWidth)
            {
                //Dimensions.
                cropView.configureOverlay()
                        .setGridStrokeWidth(utilitys.C_setGridStrokeWidth)
                        .apply();
            }



            if(!utilitys.d_setBorderColor)
            {
                //colors
                cropView.configureOverlay()
                        .setBorderColor(utilitys.C_setBorderColor)
                        .apply();
            }
            if(!utilitys.d_setCornerColor)
            {
                //colors
                cropView.configureOverlay()
                        .setCornerColor(utilitys.C_setCornerColor)
                        .apply();
            }
            if(!utilitys.d_setGridColor)
            {
                //colors
                cropView.configureOverlay()
                        .setGridColor(utilitys.C_setGridColor)
                        .apply();
            }
            if(!utilitys.d_setOverlayColor)
            {
                //colors
                cropView.configureOverlay()
                        .setOverlayColor(utilitys.C_setOverlayColor)
                        .apply();
            }


            MaterialPreferenceScreen cropPrefScreen = (MaterialPreferenceScreen) findViewById(R.id.crop_preference_screen);
            if(utilitys.CROPSETTING)
            {
                cropPrefScreen.setVisibility(View.VISIBLE);
            }else{
                cropPrefScreen.setVisibility(View.GONE);
            }
            configurator = new CropViewConfigurator(cropView, cropPrefScreen);
            cropPrefScreen.setStorageModule(configurator);
            resultReceiver = new CropIwaResultReceiver();
            resultReceiver.setListener(new CropIwaResultReceiver.Listener() {
                @Override
                public void onCropSuccess(Uri croppedUri) {
                    CROPPATH = String.valueOf(croppedUri);
                    CROPPATH = CROPPATH.toString().replace("file:///", "/");
                    sendBroadcast("BROADCAST_ACTION_CROPIMAGE");
                    resultReceiver.unregister(CropActivity.this);
                    avis.hide();
                    finish();
                }

                @Override
                public void onCropFailed(Throwable e) {
                    String sad = "";
                }
            });
            resultReceiver.register(CropActivity.this);

        } catch (Exception ex) {
            String aasd = "";
        }

    }


    public void sendBroadcast(String BroadCastNames) {

        Intent broadcast = new Intent();
        broadcast.putExtra("BroadCastNames", BroadCastNames);
        broadcast.setAction(BroadCastNames);
        broadcast.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcast);

    }

}
