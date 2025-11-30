import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class GameSound {

    private Clip clip;

    public void playMusic(String filePath) {
        new Thread(() -> {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public void playSoundEffect(String filePath) {
        new Thread(() -> { 
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
                Clip effectClip = AudioSystem.getClip();
                effectClip.open(audioStream);
                effectClip.start();

                Thread.sleep(effectClip.getMicrosecondLength() / 1000);
                effectClip.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
