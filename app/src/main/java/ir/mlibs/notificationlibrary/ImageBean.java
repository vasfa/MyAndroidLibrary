package ir.mlibs.notificationlibrary;

/**
 * Created by vali on 2018-03-13.
 */

public class ImageBean {

    private Integer imagePath;
    private String imageURLPath;
    private String videoURLPath;
    private String TITLE;
    private int TYPE;
    private Boolean isvideo;
    private Boolean isaparat;


    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTYPE(Integer TYPE) {
        this.TYPE = TYPE;
    }

    public Integer getTYPE() {
        return TYPE;
    }

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
}
