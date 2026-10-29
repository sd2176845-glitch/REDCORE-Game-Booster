package com.roy.redcore;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final int bg = Color.rgb(10, 10, 13);
    private final int panel = Color.rgb(25, 25, 31);
    private final int red = Color.rgb(255, 48, 64);
    private LinearLayout root;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);
        getWindow().getDecorView().setSystemUiVisibility(0);
        buildUi();
    }

    private void buildUi() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bg);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(22), dp(20), dp(28));
        scroll.addView(root);

        TextView brand = new TextView(this);
        brand.setText("REDCORE  /  GAME SPACE");
        brand.setTextColor(red);
        brand.setTextSize(12);
        brand.setLetterSpacing(0.14f);
        brand.setTypeface(Typeface.DEFAULT_BOLD);
        root.addView(brand);

        TextView title = new TextView(this);
        title.setText("GAME\nBOOSTER");
        title.setTextColor(Color.WHITE);
        title.setTextSize(34);
        title.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL));
        title.setPadding(0, dp(12), 0, dp(4));
        root.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("POCO M4 PRO 4G  •  GAMING PANEL");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setTextSize(12);
        root.addView(subtitle);
        space(20);

        LinearLayout stats = new LinearLayout(this);
        stats.setOrientation(LinearLayout.HORIZONTAL);
        stats.addView(statCard("BATTERY", batteryText(), 1));
        stats.addView(statCard("ANDROID", Build.VERSION.RELEASE, 1));
        root.addView(stats);
        space(14);

        TextView note = new TextView(this);
        note.setText("PERFORMANCE STATUS");
        note.setTextColor(red);
        note.setTextSize(12);
        note.setTypeface(Typeface.DEFAULT_BOLD);
        root.addView(note);
        TextView status = new TextView(this);
        status.setText("Ready for gaming\nAndroid controls available depend on MIUI/HyperOS version.");
        status.setTextColor(Color.WHITE);
        status.setTextSize(14);
        status.setPadding(dp(14), dp(14), dp(14), dp(14));
        status.setBackgroundColor(panel);
        root.addView(status);
        space(18);

        addSection("QUICK LAUNCH");
        addButton("LAUNCH FREE FIRE", this::launchFreeFire);
        addButton("OPEN YOUTUBE", () -> launchPackage("com.google.android.youtube"));
        addButton("OPEN WHATSAPP", () -> launchPackage("com.whatsapp"));
        space(16);

        addSection("GAMING TOOLS");
        addButton("OPEN DO NOT DISTURB SETTINGS", () ->
            safeStart(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)));
        addButton("OPEN DISPLAY SETTINGS", () ->
            safeStart(new Intent(Settings.ACTION_DISPLAY_SETTINGS)));
        addButton("OPEN APP BATTERY SETTINGS", () ->
            safeStart(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()))));
        space(14);

        TextView footer = new TextView(this);
        footer.setText("REDCORE is an independent fan-made utility. It does not unlock hardware limits or force FPS beyond game/device support.");
        footer.setTextColor(Color.GRAY);
        footer.setTextSize(11);
        footer.setPadding(0, dp(12), 0, 0);
        root.addView(footer);

        setContentView(scroll);
    }

    private View statCard(String label, String value, int weight) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundColor(panel);
        card.setPadding(dp(14), dp(14), dp(14), dp(14));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, -2, weight);
        lp.setMargins(0, 0, dp(8), 0);
        card.setLayoutParams(lp);
        TextView l = new TextView(this); l.setText(label); l.setTextColor(red); l.setTextSize(11);
        TextView v = new TextView(this); v.setText(value); v.setTextColor(Color.WHITE); v.setTextSize(22);
        v.setTypeface(Typeface.DEFAULT_BOLD);
        card.addView(l); card.addView(v);
        return card;
    }

    private String batteryText() {
        try {
            BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
            int level = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            return level >= 0 ? level + "%" : "N/A";
        } catch (Exception e) { return "N/A"; }
    }

    private void addSection(String text) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextColor(red);
        t.setTextSize(12);
        t.setTypeface(Typeface.DEFAULT_BOLD);
        t.setPadding(0, 0, 0, dp(8));
        root.addView(t);
    }

    private void addButton(String text, Runnable action) {
        Button b = new Button(this);
        b.setText(text);
        b.setAllCaps(false);
        b.setTextColor(Color.WHITE);
        b.setTextSize(13);
        b.setTypeface(Typeface.DEFAULT_BOLD);
        b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(panel));
        b.setPadding(dp(10), dp(12), dp(10), dp(12));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, 0, 0, dp(8));
        b.setLayoutParams(lp);
        b.setOnClickListener(v -> action.run());
        root.addView(b);
    }

    private void launchFreeFire() {
        if (launchPackage("com.dts.freefireth")) return;
        if (launchPackage("com.dts.freefiremax")) return;
        addToast("Free Fire was not found. Install it first.");
    }

    private boolean launchPackage(String pkg) {
        try {
            Intent i = getPackageManager().getLaunchIntentForPackage(pkg);
            if (i == null) return false;
            startActivity(i);
            return true;
        } catch (Exception e) { return false; }
    }

    private void safeStart(Intent intent) {
        try { startActivity(intent); }
        catch (Exception e) { addToast("This setting is not available on this device."); }
    }

    private void addToast(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }

    private void space(int heightDp) {
        View v = new View(this);
        root.addView(v, new LinearLayout.LayoutParams(1, dp(heightDp)));
    }

    private int dp(int n) {
        return (int) (n * getResources().getDisplayMetrics().density + 0.5f);
    }
}
