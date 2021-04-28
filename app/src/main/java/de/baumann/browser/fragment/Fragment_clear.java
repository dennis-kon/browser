package de.baumann.browser.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceFragmentCompat;

import de.baumann.browser.R;

public class Fragment_clear extends PreferenceFragmentCompat {

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_clear, rootKey);
        findPreference("sp_deleteDatabase").setOnPreferenceClickListener(preference -> {
            final SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setMessage(R.string.hint_database);
            builder.setPositiveButton(R.string.app_ok, (dialog, whichButton) -> {
                dialog.cancel();
                getActivity().deleteDatabase("Ninja4.db");
                sp.edit().putInt("restart_changed", 1).apply();
                getActivity().finish();
            });
            builder.setNegativeButton(R.string.app_cancel, (dialog, whichButton) -> dialog.cancel());
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        });
    }
}
