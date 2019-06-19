package ir.mlibs.myplayer;

import android.content.Context;
import android.content.Intent;

/**
 * Created by vali on 2018-04-21.
 */

public class LoadMediaData {

    public static void ShowMedia(Context main_context,
                                   String VideoOrSoundUrl,
                                   String TITLE,
                                   boolean isvideo,
                                   boolean isAparat,
                                 Boolean isFullScreen,
                                 String ImagePath

    ) {
        if(isFullScreen)
        {
            Intent intent=new Intent(main_context, LandScapeMediaPlayerActivity.class);
            intent.putExtra("VideoOrSoundUrl", VideoOrSoundUrl);
            intent.putExtra("TITLE", TITLE);
            intent.putExtra("isvideo",isvideo);
            intent.putExtra("isAparat",isAparat);
            intent.putExtra("ImagePath",ImagePath);
            main_context.startActivity(intent);
        }else{
            Intent intent=new Intent(main_context, MediaPlayerActivity.class);
            intent.putExtra("VideoOrSoundUrl", VideoOrSoundUrl);
            intent.putExtra("TITLE", TITLE);
            intent.putExtra("isvideo",isvideo);
            intent.putExtra("isAparat",isAparat);
            intent.putExtra("ImagePath",ImagePath);
            main_context.startActivity(intent);

        }

    }
}
