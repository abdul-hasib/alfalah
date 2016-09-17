package org.apps.alfalahindia.Util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import org.apps.alfalahindia.activity.App;

import java.util.List;

public class FileDownloader {

    public static boolean download(String DownloadUrl, String fileName, String description) throws Exception {

        if (!ConnectionDetector.isOnline()) {
            ToastUtil.toast("You need internet connection to download the application");
            return false;
        }

        if (isDownloadManagerAvailable()) {
            ToastUtil.toast("Downloading " + description);

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadUrl));
            request.setDescription(description);
            request.setTitle(fileName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

            // get download service and enqueue file
            DownloadManager manager = (DownloadManager) App.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
            return true;

        } else {
            ToastUtil.toast("No download manager available in your device");
        }
        return false;
    }

    private static boolean isDownloadManagerAvailable() {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setClassName("com.android.providers.downloads.ui", "com.android.providers.downloads.ui.DownloadList");
            List<ResolveInfo> list = App.getContext().getPackageManager().queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            return list.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
