package com.jeremy.datum.tools;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class JAudio {
	private static Clip clip;

	public static void playTheme() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final URL resource = JFile.class.getResource("/audio/theme.wav");
					final AudioInputStream inputStream = AudioSystem.getAudioInputStream(resource);
					JAudio.access$0(AudioSystem.getClip());
					JAudio.clip.open(inputStream);
					JAudio.clip.loop(-1);
					Thread.sleep(10000L);
				} catch (InterruptedException | UnsupportedAudioFileException | IOException | LineUnavailableException ex2) {
					ex2.printStackTrace();
				}
			}
		}).start();
	}

	public static void setVolume(final float volume) {
		if (JAudio.clip != null) {
			if (!JAudio.clip.isOpen()) {
				try {
					JAudio.clip.open();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
			final FloatControl control = (FloatControl) JAudio.clip.getControl(FloatControl.Type.MASTER_GAIN);
			control.setValue(volume);
		}
	}

	public static float getVolume() {
		final FloatControl control = (FloatControl) JAudio.clip.getControl(FloatControl.Type.MASTER_GAIN);
		return control.getValue();
	}

	static /* synthetic */ void access$0(final Clip clip) {
		JAudio.clip = clip;
	}
}
