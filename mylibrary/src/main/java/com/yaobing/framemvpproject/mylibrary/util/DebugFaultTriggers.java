package com.yaobing.framemvpproject.mylibrary.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.yaobing.framemvpproject.mylibrary.BuildConfig;


/**
 * Debug-only crash/ANR triggers for validating crash collection (e.g. Sentry).
 *
 * Important: all entry points are guarded by {@link BuildConfig#DEBUG}.
 */
public final class DebugFaultTriggers {

    private DebugFaultTriggers() {
    }

    /**
     * Triggers an immediate crash on the current thread.
     */
    public static void triggerCrashNow() {
        if (!BuildConfig.DEBUG) return;
        throw new RuntimeException("Debug test crash (triggerCrashNow)");
    }

    /**
     * Triggers a crash on a background thread.
     */
    public static void triggerBackgroundCrash() {
        if (!BuildConfig.DEBUG) return;
        new Thread(() -> {
            throw new RuntimeException("Debug test crash (triggerBackgroundCrash)");
        }, "DebugCrashThread").start();
    }

    /**
     * Blocks the main thread long enough to reliably cause an ANR.
     * <p>
     * Notes:
     * - This will freeze UI and may show the system ANR dialog.
     * - Default duration is 12 seconds (ANR threshold is typically ~5s for input dispatch).
     */
    public static void triggerMainThreadAnr() {
        triggerMainThreadAnr(12_000);
    }

    public static void triggerMainThreadAnr(long blockMs) {
        if (!BuildConfig.DEBUG) return;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            new Handler(Looper.getMainLooper()).post(() -> triggerMainThreadAnr(blockMs));
            return;
        }
        long end = android.os.SystemClock.uptimeMillis() + Math.max(0, blockMs);
        while (android.os.SystemClock.uptimeMillis() < end) {
            // Busy loop intentionally blocks main thread.
        }
    }

    /**
     * Safer helper: asks for confirmation before triggering a fault.
     */
    public static void confirmAndRun(Activity activity, String title, String message, Runnable action) {
        if (!BuildConfig.DEBUG) return;
        if (activity == null || activity.isFinishing()) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) return;

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (d, w) -> action.run())
                .show();
    }

    /**
     * Triggers a NullPointerException (NPE) immediately on the current thread.
     */
    public static void triggerNullPointerCrash() {
        if (!BuildConfig.DEBUG) return;
            Object obj = null;
            //noinspection ConstantConditions
            obj.toString();

    }
}
