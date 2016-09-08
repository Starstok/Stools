/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Iterator
 *  java.util.List
 */
package com.stk.stools;

import com.stk.stools.DeviceInfo;
import com.stk.stools.FileUtils;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class DeviceUtils {
    private static final String BUILD_PROP = "/system/build.prop";
    private static final String RO_BOARD_PLATFORM = "ro.board.platform";
    private static final String RO_BUILD_DESCRIPTION = "ro.build.description";
    private static final String RO_BUILD_ID = "ro.build.id";
    private static final String RO_BUILD_VERSION_RELEASE = "ro.build.version.release";
    private static final String RO_BUILD_VERSION_SDK = "ro.build.version.sdk";
    private static final String RO_PRODUCT_BOARD = "ro.product.board";
    private static final String RO_PRODUCT_BRAND = "ro.product.brand";
    private static final String RO_PRODUCT_CPU_ABI = "ro.product.cpu.abi";
    private static final String RO_PRODUCT_CPU_ABI2 = "ro.product.cpu.abi2";
    private static final String RO_PRODUCT_DEVICE = "ro.product.device";
    private static final String RO_PRODUCT_MANUFACTURER = "ro.product.manufacturer";
    private static final String RO_PRODUCT_MODEL = "ro.product.model";
    private static final String RO_PRODUCT_NAME = "ro.product.name";
    private static final String RO_PRODUCT_VERSION = "ro.product.version";
    private static List<String> buildProp = null;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String findPropValue(String string2) {
        int n;
        String string3;
        Iterator iterator = buildProp.iterator();
        do {
            boolean bl = iterator.hasNext();
            String string4 = null;
            if (!bl) return string4;
        } while ((n = (string3 = (String)iterator.next()).indexOf("=")) < 0 || !string3.substring(0, n).equals((Object)string2));
        return string3.substring(n + 1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getBuildProp(String string2) {
        if (buildProp != null) return DeviceUtils.findPropValue(string2);
        try {
            buildProp = FileUtils.readFile("/system/build.prop");
        }
        catch (IOException iOException) {
            return null;
        }
        return DeviceUtils.findPropValue(string2);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static DeviceInfo getDeviceInfo() throws IOException {
        if (buildProp == null) {
            buildProp = FileUtils.readFile("/system/build.prop");
        }
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setRoBuildId(DeviceUtils.findPropValue("ro.build.id"));
        deviceInfo.setRoBuildVersionSdk(DeviceUtils.findPropValue("ro.build.version.sdk"));
        deviceInfo.setRoBuildVersionRelease(DeviceUtils.findPropValue("ro.build.version.release"));
        deviceInfo.setRoProductModel(DeviceUtils.findPropValue("ro.product.model"));
        deviceInfo.setRoProductBrand(DeviceUtils.findPropValue("ro.product.brand"));
        deviceInfo.setRoProductName(DeviceUtils.findPropValue("ro.product.name"));
        deviceInfo.setRoProductDevice(DeviceUtils.findPropValue("ro.product.device"));
        deviceInfo.setRoProductBoard(DeviceUtils.findPropValue("ro.product.board"));
        deviceInfo.setRoProductCpuAbi(DeviceUtils.findPropValue("ro.product.cpu.abi"));
        deviceInfo.setRoProductCpuAbi2(DeviceUtils.findPropValue("ro.product.cpu.abi2"));
        deviceInfo.setRoProductManufacturer(DeviceUtils.findPropValue("ro.product.manufacturer"));
        deviceInfo.setRoBoardPlatform(DeviceUtils.findPropValue("ro.board.platform"));
        deviceInfo.setRoBuildDescription(DeviceUtils.findPropValue("ro.build.description"));
        deviceInfo.setRoProductVersion(DeviceUtils.findPropValue("ro.product.version"));
        return deviceInfo;
        
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String propNameToReadableName(String string2) {
        String string3 = "";
        if (string2.equals((Object)"BuildId")) {
            return "BuildId";
        }
        if (string2.equals((Object)"BuildVersionSdk")) {
            return "BuildVersionSdk";
        }
        if (string2.equals((Object)"BuildVersionRelease")) {
            return "BuildVersionRelease";
        }
        if (string2.equals((Object)"ProductModel")) {
            return "ProductModel";
        }
        if (string2.equals((Object)"ProductBrand")) {
            return "ProductBrand";
        }
        if (string2.equals((Object)"ProductName")) {
            return "ProductName";
        }
        if (string2.equals((Object)"ProductDevice")) {
            return "ProductDevice";
        }
        if (string2.equals((Object)"ProductBoard")) {
            return "ProductBoard";
        }
        if (string2.equals((Object)"ProductCpuAbi")) {
            return "ProductCpuAbi";
        }
        if (string2.equals((Object)"ProductCpuAbi2")) {
            return "ProductCpuAbi2";
        }
        if (string2.equals((Object)"ProductManufacturer")) {
            return "ProductManufacturer";
        }
        if (string2.equals((Object)"BoardPlatform")) {
            return "BoardPlatform";
        }
        if (string2.equals((Object)"BuildDescription")) {
            return "BuildDescription";
        }
        if (string2.equals((Object)"ProductVersion")) {
            return "ProductVersion";
        }
        if (string2.equals((Object)"ScreenSize")) {
            return "ScreenSize";
        }
        if (string2.equals((Object)"ScreenDpi")) {
            return "ScreenDpi";
        }
        if (string2.equals((Object)"lDpi")) {
            return "lDpi";
        }
        if (string2.equals((Object)"mDpi")) {
            return "mDpi";
        }
        if (string2.equals((Object)"hDpi")) {
            return "hDpi";
        }
        if (string2.equals((Object)"fixmark")) {
            return "fixmark";
        }
        if (!string2.equals((Object)"markdetail")) return string3;
        return "%s (markdetail)";
    }
}

