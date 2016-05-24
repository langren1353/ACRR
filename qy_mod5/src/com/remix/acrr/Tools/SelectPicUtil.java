package com.remix.acrr.Tools;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;
 
/**
 * ѡ�񱾵�ͼƬ������
 * <br>
 * ��Ϊֱ�ӻ�ȡͼƬ���ױ���������ֱ�Ӵ���SD�����ٻ�ȡ
 * <br>
 * ����Ϊд������ȷ���׵��²��ֻ����޷�ʹ�ã����Է�װ��������
 * <br>
 * ʹ�÷�����
 * <br>
 * 1������getByAlbum��getByCameraȥ��ȡͼƬ
 * <br>
 * 2����onActivityResult�е��ñ��������onActivityResult�������д���
 * <br>
 * 3��onActivityResult���ص�Bitmap�ǵÿ�ָ���ж�
 * 
 * <br><br>
 * PS����������ֻ�ܴ���ü�ͼƬ���������ü�����ʹ�ñ��������onActivityResult���Լ���������
 * 
 * @author linin630
 * FROM http://my.oschina.net/u/816576/blog/498767
 *
 */
public class SelectPicUtil {
     
    /**��ʱ���ͼƬ�ĵ�ַ�������޸ģ���ǵô�����·���µ��ļ���*/
    private static final String lsimg = "file:///sdcard/temp.jpg";
     
    public static final int GET_BY_ALBUM = 123;//����г�ͻ���ǵ��޸�
    public static final int GET_BY_CAMERA = 124;//����г�ͻ���ǵ��޸�
    public static final int CROP = 125;//����г�ͻ���ǵ��޸�
     
    /**������ȡͼƬ*/
    public static void getByAlbum(Activity act){
/*        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");*/
    	Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        act.startActivityForResult(intent, GET_BY_ALBUM);
    }
     
    /**ͨ�����ջ�ȡͼƬ*/
    public static void getByCamera(Activity act){
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(lsimg));
            getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            act.startActivityForResult(getImageByCamera, GET_BY_CAMERA);
        } else {
            Toast.makeText(act,"��ȷ���Ѿ�����SD��",1).show();
        }
    }
    /**
     * �����ȡ��ͼƬ��ע���жϿ�ָ�룬Ĭ�ϴ�С480*480������1:1
     */
    public static Bitmap onActivityResult(Activity act, int requestCode, int resultCode, Intent data){
        return onActivityResult(act, requestCode, resultCode, data, 0, 0, 0, 0);
    }
    /**
     * �����ȡ��ͼƬ��ע���жϿ�ָ��
     */
    public static Bitmap onActivityResult(Activity act, int requestCode, int resultCode, Intent data, 
            int w, int h,int aspectX,int aspectY){
        Bitmap bm = null;
        System.out.println("����ֵ+"+requestCode+"����ֵ"+resultCode);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            switch (requestCode) {
            case GET_BY_ALBUM:
                uri = data.getData();
                act.startActivityForResult(crop(uri,w,h,aspectX,aspectY),CROP);
                break;
            case GET_BY_CAMERA:
                uri = Uri.parse(lsimg);
                act.startActivityForResult(crop(uri,w,h,aspectX,aspectY),CROP);
                break;
            case CROP:
                bm = dealCrop(act);
                break;
            }
        }
        return bm;
    }
     
    /**Ĭ�ϲü����480*480������1:1*/
    public static Intent crop(Uri uri){
        return crop(uri,480,480,1,1);
    }
    /**
     * �ü������磺���100*100��С��ͼƬ����߱�����1:1
     * @param w �����
     * @param h �����
     * @param aspectX �����
     * @param aspectY �߱���
     */
    public static Intent crop(Uri uri,int w,int h,int aspectX,int aspectY){
        if (w==0&&h==0) {
            w=h=480;
        }
        if (aspectX==0&&aspectY==0) {
            aspectX=aspectY=1;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        // ��ƬURL��ַ
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", w);
        intent.putExtra("outputY", h);
        // ���·��
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(lsimg));
        // �����ʽ
        intent.putExtra("outputFormat", "JPEG");
        // ����������ʶ��
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);
        return intent;
    }
     
    /**����ü�����ȡ�ü����ͼƬ*/
    public static Bitmap dealCrop(Context context){
        // �ü�����
        Uri uri = Uri.parse(lsimg);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
 
}