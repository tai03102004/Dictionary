package com.example.directory.Dictionary;

import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

import javax.sound.sampled.*;
import java.io.*;

public class VoiceRSS {
    private static final String API_KEY = "ee1a861047db41e3aed6cca75554a826";
    private static final String AUDIO_PATH = "src/main/resources/audio/audio.wav";

    public static String voiceNameUS;
    public static String voiceNameUK;
    public static String language = "en-gb";
    public static String Name = "Linda";
    public static double speed = 1;

    public static void main(String[] args) throws Exception {
        speakWord("hi i am Tai");
    }

    public static void speakWord(String word) throws Exception {
        VoiceParameters params = buildVoiceParameters(word);
        byte[] voice = generateVoice(params);
        saveVoice(voice);
        playAudio();
    }

    private static VoiceParameters buildVoiceParameters(String word) {
        VoiceParameters params = new VoiceParameters(word, AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setLanguage(language);
        params.setVoice(Name);
        params.setRate((int) Math.round(-2.9936 * speed * speed + 15.2942 * speed - 12.7612));
        return params;
    }

    private static byte[] generateVoice(VoiceParameters params) throws Exception {
        VoiceProvider tts = new VoiceProvider(API_KEY);
        return tts.speech(params);
    }

    private static void saveVoice(byte[] voice) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(AUDIO_PATH)) {
            fos.write(voice, 0, voice.length);
        }
    }

    private static void playAudio() {
        try (InputStream input = new BufferedInputStream(new FileInputStream(AUDIO_PATH))) {
            AudioInputStream audio = AudioSystem.getAudioInputStream(input);
            System.out.println("Fail");
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
