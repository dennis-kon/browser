package de.baumann.browser.activity;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

import de.baumann.browser.BuildConfig;
import de.baumann.browser.fragment.Fragment_settings;
import de.baumann.browser.R;
import de.baumann.browser.unit.HelperUnit;

public class Settings_Activity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) getSupportActionBar().hide();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.md_theme_light_onBackground));

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        HelperUnit.initTheme(this);
        DynamicColors.applyToActivitiesIfAvailable(getApplication());
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new Fragment_settings())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        } else if (menuItem.getItemId() == R.id.menu_info) {
            SpannableString s= new SpannableString(Html.fromHtml(getString(R.string.changelog_dialog),Html.FROM_HTML_MODE_LEGACY));
            Linkify.addLinks(s, Linkify.WEB_URLS);
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setTitle(getString(R.string.menu_other_info)+"\t V"+ BuildConfig.VERSION_NAME);
            builder.setMessage(s);
            AlertDialog dialog = builder.create();
            dialog.show();
            ((TextView) Objects.requireNonNull(dialog.findViewById(android.R.id.message))).setMovementMethod(LinkMovementMethod.getInstance());
            Objects.requireNonNull(dialog.getWindow()).setGravity(Gravity.BOTTOM);
        }
        return true;
    }
}