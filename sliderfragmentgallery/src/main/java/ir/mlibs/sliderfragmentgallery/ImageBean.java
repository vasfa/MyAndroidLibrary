package ir.mlibs.sliderfragmentgallery;

/**
 * Created by vali on 2018-03-13.
 */

public class ImageBean {

    private Integer imagePath;
    private String imageURLPath;
    private String videoURLPath;
    private Boolean isvideo;
    private Boolean isaparat;
    private Boolean isonlySound;
    private Boolean isimageZoomable;
    private Boolean istitleAvalable;

    public void setImagePath(Integer imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getImagePath() {
        return imagePath;
    }

    public void setImageURLPath(String imageURLPath) {
        this.imageURLPath = imageURLPath;
    }

    public String getImageURLPath() {
        return imageURLPath;
    }

    public void setVideoURLPath(String videoURLPath) {
        this.videoURLPath = videoURLPath;
    }

    public String getVideoURLPath() {
        return videoURLPath;
    }

    public void setIsaparat(boolean isaparat) {
        this.isaparat = isaparat;
    }

    public boolean getIsaparat() {
        return isaparat;
    }

    public void setIsvideo(boolean isvideo) {
        this.isvideo = isvideo;
    }

    public boolean getIsvideo() {
        return isvideo;
    }

    public void setIsonlySound(boolean isonlySound) {
        this.isonlySound = isonlySound;
    }

    public boolean getIsonlySound() {
        return isonlySound;
    }


    public void setIsimageZoomable(boolean isimageZoomable) {
        this.isimageZoomable = isimageZoomable;
    }

    public boolean getIsimageZoomable() {
        return isimageZoomable;
    }

    public void setIstitleAvalable(boolean istitleAvalable) {
        this.istitleAvalable = istitleAvalable;
    }

    public boolean getIstitleAvalable() {
        return istitleAvalable;
    }
}
