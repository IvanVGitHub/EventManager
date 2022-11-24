package com.ivank.fraui;

import com.ivank.fraui.utils.CamData;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;

import javax.swing.*;
import java.awt.*;

import static com.ivank.fraui.settings.AppConfig.getScale;
import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_QUIET;
import static org.bytedeco.ffmpeg.global.avutil.av_log_set_level;

public class WindowCameraLiveView extends CanvasFrame implements Runnable {
    volatile boolean running;
    volatile CamData cd;
    //размер окна, подстраивается под разрешение экрана
    final int width = (int)(getScale() * 640);
    final int height = (int)(getScale() * 360);

    FFmpegFrameGrabber getGrabber(CamData camera) {
        try {
            System.out.println("Run camera: " + camera.cameraName);
            FFmpegFrameGrabber streamGrabber = new FFmpegFrameGrabber(camera.getConnectionUrl());
            streamGrabber.setFrameRate(camera.framerate);
            streamGrabber.setImageWidth(camera.width);
            streamGrabber.setImageHeight(camera.height);
            streamGrabber.setOption("rtsp_transport", "tcp");
            return streamGrabber;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void run() {
        running = true;

        FFmpegFrameGrabber grabber = getGrabber(cd);
        Frame frame = null;
        try {
            grabber.start();
            while(running) {
                frame = grabber.grabImage();
                //отрисовка кадра в окне
                this.showImage(frame);
            }
        } catch (java.lang.Exception ex) {ex.printStackTrace();}
        finally {
            try {
                if (grabber != null)
                    grabber.close();
                if (frame != null)
                    frame.close();
            } catch (FrameGrabber.Exception e) {throw new RuntimeException(e);}
        }
    }

    public WindowCameraLiveView(int idCamera) {
        super("4 сыра общий план");
        CamData cd = new CamData("172.20.13.10", "/", "admin", "WRPas7dZ5!", "4 сыра общий план");
        init(cd, 640, 360);
    }

    public WindowCameraLiveView(CamData cd) {
        super(cd.cameraName);
        init(cd, width, height);
    }

    public WindowCameraLiveView(CamData cd, int w, int h) {
        super(cd.cameraName);
        init(cd, w, h);
    }

    void init(CamData cd, int w, int h) {
        //отключаем вывод в консоли ошибки:
        //"[swscaler @ 00000000728a90c0] deprecated pixel format used, make sure you did set range correctly"
        //НЕИЗВЕСТНО, отключает ли это все остальные ошибки и предупреждения!
        av_log_set_level(AV_LOG_QUIET);

        this.setTitle(cd.cameraName);
        System.out.println(cd.cameraName + " init");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setPreferredSize (new Dimension(w, h));

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                stop();
            }
        });
        start(cd);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        stop();
    }

    public void start(CamData cd) {
        if(running)
            return;
        this.cd = cd;
        Thread t = new Thread(this);
        t.start();
    }

    public void stop() {
        running = false;
    }
}
