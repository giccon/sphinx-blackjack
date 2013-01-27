/*
 * This file is part of Sphinx Blackjack.
 *
 * Sphinx Blackjack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Sphinx Blackjack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Sphinx Blackjack.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.giccon.ai.sphinx_blackjack.speech;

import edu.cmu.sphinx.decoder.ResultListener;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.util.props.ConfigurationManager;

/**
 * Author: Paul Minasian
 */
public class SpeechRecognitionManager {

    private static final String SPEECH_CONFIG_PATH = ""
            + "/org/giccon/ai/sphinx_blackjack/assets/speech/speech_recognition.config.xml";
    private static final SpeechRecognitionManager instance = new SpeechRecognitionManager();
    private final Recognizer recognizer;
    private final Microphone microphone;
    private SpeechRecognitionLoop speechRecognitionLoop;
    private volatile boolean runSpeechRecognitionEngine;
    private State state = State.DEINITIALIZED;

    private SpeechRecognitionManager() {
        ConfigurationManager config_manager = new ConfigurationManager(getClass().getResource(SPEECH_CONFIG_PATH));
        recognizer = (Recognizer) config_manager.lookup("recognizer");
        microphone = (Microphone) config_manager.lookup("microphone");
    }

    public synchronized static SpeechRecognitionManager getInstance() {
        return instance;
    }

    public State getState() {
        return state;
    }

    public synchronized void initSpeechRecognitionEngine() throws SpeechRecognitionException, MicrophoneException {
        if (state != State.DEINITIALIZED) {
            throw new SpeechRecognitionException("Speech Recognition Engine must be in deinitialized state");
        }

        recognizer.allocate();

        if (!microphone.startRecording()) {
            recognizer.deallocate();
            throw new MicrophoneException("Cannot start the microphone");
        }
        state = State.INITIALIZED;
    }

    public synchronized void shutdownSpeechRecognitionEngine() throws SpeechRecognitionException {
        if (state != State.INITIALIZED && state != State.STOPPED) {
            throw new SpeechRecognitionException("Speech Recognition Engine must be in initialized or stopped state");
        }

        microphone.stopRecording();
        recognizer.deallocate();
        speechRecognitionLoop = null;

        state = State.DEINITIALIZED;
    }

    public synchronized void startSpeechRecognitionEngine() throws SpeechRecognitionException {
        if (state != State.INITIALIZED && state != State.STOPPED) {
            throw new SpeechRecognitionException("Speech Recognition Engine must be in initialised or stopped state");
        }

        runSpeechRecognitionEngine = true;
        speechRecognitionLoop = new SpeechRecognitionLoop();
        speechRecognitionLoop.setName("Speech Recognition Loop");
        speechRecognitionLoop.start();
        state = State.STARTED;
    }

    public synchronized void stopSpeechRecognitionEngine() throws SpeechRecognitionException {
        if (state != State.STARTED) {
            throw new SpeechRecognitionException("Speech Recognition Engine must be in started state");
        }

        runSpeechRecognitionEngine = false;
        while (speechRecognitionLoop.isAlive()) {
            try {
                speechRecognitionLoop.join();
            } catch (InterruptedException ignore) {
                // ignore
            }
        }

        state = State.STOPPED;
    }

    public void addResultListener(ResultListener resultListener) throws SpeechRecognitionException {
        synchronized (recognizer) {
            recognizer.addResultListener(resultListener);
        }
    }

    public void removeResultListener(ResultListener resultListener) throws SpeechRecognitionException {
        synchronized (recognizer) {
            recognizer.removeResultListener(resultListener);
        }
    }

    public static enum State {
        INITIALIZED, STARTED, STOPPED, DEINITIALIZED
    }

    private class SpeechRecognitionLoop extends Thread {
        @Override
        public void run() {
            while (runSpeechRecognitionEngine) {
                synchronized (recognizer) {
                    recognizer.recognize();
                }
            }
        }
    }
}
