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

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

/**
 * Author: Paul Minasian
 */
public class SpeechRecognitionManager {

    private static final String SPEECH_CONFIG_PATH = ""
            + "org/giccon/ai/sphinx_blackjack/assets/speech/speech_recognition.config.xml";
    private static final SpeechRecognitionManager instance = new SpeechRecognitionManager();
    private Recognizer recognizer;
    private SpeechRecognitionLoop speechRecognitionLoop;

    private SpeechRecognitionManager() {
        speechRecognitionLoop = new SpeechRecognitionLoop();
    }

    public synchronized static SpeechRecognitionManager getInstance() {
        return instance;
    }

    public synchronized void initSpeechRecognitionEngine() throws SpeechRecognitionException {
        ConfigurationManager cm =
                new ConfigurationManager(ClassLoader.getSystemResource(SPEECH_CONFIG_PATH));

        recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            recognizer.deallocate();
            throw new SpeechRecognitionException("Cannot start microphone");
        }
    }

    public synchronized void startSpeechRecognitionEngine() throws SpeechRecognitionException {
        if (recognizer == null) {
            throw new SpeechRecognitionException("Speech Recognition Engine must be initialised first");
        }
        speechRecognitionLoop.start();
    }

    public synchronized Recognizer getRecognizer() {
        return recognizer;
    }

    private class SpeechRecognitionLoop extends Thread {
        @Override
        public void run() {
            while (true) {
                Result result = getRecognizer().recognize();

                if (result != null) {
                    String resultText = result.getBestFinalResultNoFiller();
                    System.out.println("You said: " + resultText + "\n");
                } else {
                    System.out.println("I can't hear what you said.\n");
                }
            }
        }
    }
}
