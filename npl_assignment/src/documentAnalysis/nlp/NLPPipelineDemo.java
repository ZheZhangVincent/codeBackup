package documentAnalysis.nlp;

import java.io.File;

import documentAnalysis.nlp.struct.Document;
import documentAnalysis.nlp.struct.Sentence;

/**
 * The demo of a NLP pipeline. 
 * Please make sure the two jar files of Stanford CoreNLP are in the classpath.
 * 
 * @author Lizhen Qu
 *
 */
public class NLPPipelineDemo {

	public static void main(String[] args) {
		// It requires the file path of the data directory as the argument of the main program.
		String errorMsg = "INPUT_DIRECTORY_FILE_PATH\n";
		if(args.length == 1){			
			String dirFilePath = args[0];
			File dir = new File(dirFilePath);
			if(dir.exists()){
				if(dir.isDirectory()){					
					NLPPipeline pipeline = new NLPPipeline(true,true); // (lemmatization, postagging)
					try {
						for(Document doc : pipeline.process(dirFilePath)){
							for(Sentence sentence : doc){
								if(sentence.size() > 0){
									int lastTokenIndex= sentence.size() - 1;
									for(int i = 0 ;i < lastTokenIndex; i++){
										System.out.print(sentence.get(i).getWordForm());
										System.out.print(' ');
									}
									System.out.println(sentence.get(lastTokenIndex));
									System.out.println();
								}
							}
						}
					} catch (TextProcessingException e) {
						e.printStackTrace();
					}
					
					
				}else{
					System.err.println(dirFilePath + " is not a directory.");
				}
			}else{
				System.err.println(dirFilePath + " cannot be found.");
			}
			
		}else{
			System.err.println(errorMsg);
		}

	}

}