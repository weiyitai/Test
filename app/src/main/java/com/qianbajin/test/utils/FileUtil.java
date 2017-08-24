package com.qianbajin.test.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/*
 * @des
 * @Created  Administrator  at 2017/8/19 0019  14:50 
 *
 */
public class FileUtil {

    public static final String TAG = "FileUtil";
    private static boolean success;
    private static int count;
    private static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tencent/MobileQQ/shortvideo/";

    /**
     * 删除腾讯 shortvideo 目录下的空文件夹,记得在子线程调用
     *
     * @return 成功删除的空文件夹个数
     */
    public static int deleteEmptyDir() {
        count = 0;
        File file = new File(path);
        boolean exists = file.exists();
        if (exists) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                Log.d(TAG, "shortvideo目录下总的文件夹和文件个数>files.length:" + files.length);
                for (File file1 : files) {
                    if (file1.isDirectory()) {
                        File[] dir = file1.listFiles();
                        if (dir.length == 1) {
                            File files2 = dir[0];
                            boolean equals = files2.getName().equals(".nomedia");
                            if (equals) {
                                boolean delete = files2.delete();
                                if (delete) {
                                    String name = file1.getName();
                                    Log.d(TAG, "name:" + name);
                                    if (file1.delete()) {
                                        count++;
                                    }
                                }
                            }
                        } else {
//                                    String name = file1.getName();
//                                    Log.d(TAG, "empty name:" + name);
                        }
                    } else {
                        Log.d(TAG, "file1.getName():" + file1.getName());
                    }

                }
            }
        }
        return count;
    }

    /**
     * 删除腾讯 shortvideo 目录下一个文件夹里的多余文件,记得在子线程调用
     *
     * @return 是否有文件删除成功
     */
    public static long deleteUnnecessaryFile() {
        long deleteLength = 0;
        File file = new File(path);
        boolean exists = file.exists();
        if (exists) {
            List<File> fileList = new ArrayList<>();
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                Log.d(TAG, "shortvideo目录下总的文件夹和文件个数>files.length:" + files.length);
                for (File file1 : files) {
                    if (file1.isDirectory()) {
                        File[] dir = file1.listFiles();
                        if (dir.length > 2) {  //如果这个文件夹里文件个数大于2，则肯定有多余的重复文件
                            fileList.clear();
                            for (File file2 : dir) {
                                String name = file2.getName();
                                if (!name.equals(".nomedia")) {
                                    fileList.add(file2);
//                                    Log.d(TAG, "name:" + name);
                                }
                            }
                            int size = fileList.size();
                            Log.d(TAG, "文件夹:" + file1.getName() + ":里重复文件总数>>size:" + size);
                            for (int i = 1; i < size; i++) {
                                File file2 = fileList.get(i);
                                Log.d(TAG, "文件:" + file2.getName());
                                long length = file2.length();
                                boolean delete = file2.delete();
                                if (delete) {
                                    deleteLength += length;
                                }
                                Log.d(TAG, "delete:" + delete);
                            }
                        } else {
                            //只有一个文件在这个文件夹里
                        }
                    } else {
                        Log.d(TAG, "file1.getName():" + file1.getName());
                    }
                }
            }
        }
        return deleteLength;
    }

}
