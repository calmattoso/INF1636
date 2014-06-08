package com.monopoly.game;




import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;


public class Music implements Runnable{

//SOURCE: http://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
//Note: I've modified it slightly

private String location;
private boolean play;

public void playMusic(String loc) {
    location = loc;
    play = true;
    try{
        Thread t = new Thread(this);
        t.start();
    }catch(Exception e){
        System.err.println("Could not start music thread");
    }
}

public void run(){
    SourceDataLine soundLine = null;
    int BUFFER_SIZE = 64*1024;
    while(play == true){
    try {
        File soundFile = new File(location);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        AudioFormat audioFormat = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        soundLine = (SourceDataLine) AudioSystem.getLine(info);
        soundLine.open(audioFormat);
        soundLine.start();
        int nBytesRead = 0;
        byte[] sampledData = new byte[BUFFER_SIZE];

        while (nBytesRead != -1 && play == true) {
            nBytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
            if (nBytesRead >= 0) {
               soundLine.write(sampledData, 0, nBytesRead);
            }
        }
    } catch (Exception e) {
        System.err.println("Could not start music!");
    }

    }
    soundLine.drain();
    soundLine.close();

}

public void stop(){
    play = false;
}


}
