package com.qianbajin.test.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.qianbajin.test.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Executors;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private boolean isBreak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private SharedPreferences getSp() {
        return getSharedPreferences("xinxin", MODE_PRIVATE);
    }

    public void saveSP(View view) {
        SharedPreferences sp = getSp();
        boolean commit = sp.edit().putString("hehe", "去洗吧你").commit();

        Log.d("MainActivity", "commit:" + commit);

    }

    public void getSP(View view) {
//        String hehe = getSp().getString("hehe", "");
//        Log.d("MainActivity", hehe);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                X509TrustManager trustManager = null;
                SSLSocketFactory socketFactory = null;
                try {
                    InputStream open = getAssets().open("srca.cer");
                    trustManager = trustManagerForCertificates(open);
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, new TrustManager[]{trustManager}, null);
                    socketFactory = sslContext.getSocketFactory();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }
                OkHttpClient client = new OkHttpClient
                        .Builder()
                        .sslSocketFactory(socketFactory, trustManager)
                        .build();

                String url = "https://api.github.com/repos/square/okhttp/contributors";
                String url2 = "http://www.baidu.com";
                String url3 = "https://kyfw.12306.cn/otn/";
                Request request = new Request.Builder()
                        .url(url3).build();
                try {
                    Response response = client.newCall(request).execute();
                    Log.d(TAG, "response:" + response);
                    ResponseBody body = response.body();
                    Log.d(TAG, "body:" + body);
                    BufferedSource source = body.source();
                    Log.d(TAG, "source:" + source);
                    String string = body.string();
                    Log.d(TAG, "string:" + string);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    /**
     * 添加password
     *
     * @param password
     * @return
     * @throws GeneralSecurityException
     */
    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // 这里添加自定义的密码，默认
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public void deleteDir(View view) {
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                long length = FileUtil.deleteUnnecessaryFile();
//                Log.d("FileUtil", "length:" + Formatter.formatFileSize(MainActivity.this, length));
//            }
//        });
    }

}
