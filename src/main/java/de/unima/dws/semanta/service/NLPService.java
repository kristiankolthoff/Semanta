package de.unima.dws.semanta.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class NLPService {

	private static SentenceDetector sentenceDetector;
	
	private NLPService() {}
	
	static {
		InputStream in = null;
		try {
			in = Files.newInputStream(Paths.get("src/main/resources/nlpmodels/en-sent.bin"));
			final SentenceModel sentenceModel = new SentenceModel(in);
			in.close();
			sentenceDetector = new SentenceDetectorME(sentenceModel);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	// Remove all non-English & special characters
	public static String stringCleaner(String s)
	{
		return s.replaceAll("[^A-Za-z0-9 ]", "");
	}
	
	public static String[] detectSentences(String content) {
		if(content == null || content.isEmpty()) {
			return new String[0];	
		}
		return sentenceDetector.sentDetect(content);
	}
	
	public static boolean isVowel(char c) {
		  return "AEIOUaeiou".indexOf(c) != -1;
		}
}
