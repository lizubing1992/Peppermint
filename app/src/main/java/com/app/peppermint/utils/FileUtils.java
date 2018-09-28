package com.app.peppermint.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.Key;
import java.security.interfaces.RSAPublicKey;

/**
 * File option utils
 */
public class FileUtils {

    /**
     * 检查目录是否存在
     *
     * @param dirPath 文件夹路径
     * @return
     */
    public static boolean isDirExists(String dirPath) {
        File dir = new File(dirPath);
        return dir.exists() && dir.isDirectory();
    }

    /**
     * 关闭流
     *
     * @param closeables Closeable
     */
    @SuppressWarnings("WeakerAccess")
    public static void close(Closeable... closeables) {
        if (closeables == null || closeables.length == 0) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File getSaveFile(Context context,String filName) {
        File file = new File(context.getFilesDir(), filName+".jpg");
        return file;
    }


    /**
     * 公钥
     */
    private static RSAPublicKey publicKey;
    /**
     * 从本地加载公钥
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static RSAPublicKey getPublicKeyByLocal(Context context,int id) {
        if(publicKey!=null){
            return publicKey;
        }
        RSAPublicKey keyRSA=null;
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(id);
            ObjectInputStream ois = new ObjectInputStream(is);
            if(null != ois){
                keyRSA = (RSAPublicKey) ois.readObject();
            }
        } catch (Exception e) {
           LogUtils.e("公钥数据流读取错误");
        }finally{
            try {
                if(null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        publicKey = keyRSA;
        return keyRSA;
    }


    public static synchronized Key getPubKeyRSA(Context context,int id){
        Key keyRSA=null;
        ObjectInputStream ois = null;
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(id);
            LogUtils.d("public key file : {}"+id);
            ois = new ObjectInputStream(is);
            keyRSA = (Key) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(null != is) {
                    is.close();
                }
                if(null != ois){
                     ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return keyRSA;
    }

    /**
     * 保存图片到相册，并添加到系统图库
     *
     * @param context
     * @param bmp
     */
    public static void saveBitmapToGallery(Context context, Bitmap bmp, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

    /**
     * 保存图片到相册，并添加到系统图库
     *
     * @param context
     * @param bmp
     */
    public static void saveBitmap(Context context, Bitmap bmp, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建文件夹
     *
     * @param dir
     */
    public static void makeDir(File dir) {
        if (!(dir.exists() && dir.isDirectory())) {
            dir.mkdirs();
        }
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String path) {
        boolean status;
        SecurityManager checker = new SecurityManager();
        if (StringUtils.isNotEmpty(path)) {
            File file = new File(path);
            checker.checkDelete(file.toString());
            if (file.isFile()) {
                try {
                    file.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else {
                status = false;
            }
        } else {
            status = false;
        }
        return status;
    }


    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void deleteAll(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteDirFiles(f);
                }
                file.delete();
            }
        }
    }

    /**
     * 递归删除文件夹下的所有文件（不包含文件夹）
     *
     * @param file 要删除的根目录
     */
    public static void deleteDirFiles(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                //是文件夹，拿到他下面的所有文件
                File[] childFile = file.listFiles();
                if (childFile != null && childFile.length > 0) {
                    for (File f : childFile) {
                        deleteDirFiles(f);
                    }
                }
            }
        }
    }

    /**
     * 创建文件
     *
     * @param folderPath
     * @param fileName
     * @return
     */
    public static File createFile(String folderPath, String fileName) {
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            //创建文件夹
            destDir.mkdirs();
        }
        return new File(folderPath, fileName);
    }


    /**
     * 计算一个文件及下面所有文件的大小总和
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long calculateFileSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                // 如果下面还有文件
                if (aFileList.isDirectory()) {
                    size = size + calculateFileSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static boolean fileIsExists(String strFile) {
        try {
            File f=new File(strFile);
            if(!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
