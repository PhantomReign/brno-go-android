package cz.vutbr.fit.brnogo.interactors.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import javax.inject.Inject;
import javax.inject.Singleton;

import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;

@Singleton
public class PermissionChecker {

	@Inject
	@ApplicationContext
	Context context;

	@Inject
	public PermissionChecker() {
	}

	public boolean hasAny(@NonNull String... permissions) {
		for (String permission : permissions) {
			if (has(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasAll(@NonNull String... permissions) {
		boolean result = true;

		for (String permission : permissions) {
			result &= has(permission);
		}

		return result;
	}

	public boolean hasLocation() {
		return has(Manifest.permission.ACCESS_FINE_LOCATION) && has(Manifest.permission.ACCESS_COARSE_LOCATION);
	}

	private boolean has(String permissions) {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ContextCompat.checkSelfPermission(context, permissions) == PackageManager.PERMISSION_GRANTED;
	}
}
