package ir.mlibs.myupload;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

/**
 * @author yarolegovich
 * 25.02.2017.
 */
public class ViewImageActivity extends AppCompatActivity {

    private static final String EXTRA_IMAGE = "extra_image";

    public static Intent callingIntent(Context c, Uri imageUri) {
        Intent intent = new Intent(c, ViewImageActivity.class);
        intent.putExtra(EXTRA_IMAGE, imageUri);
        return intent;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        Uri imageUri = getIntent().getParcelableExtra(EXTRA_IMAGE);
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageURI(imageUri);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
