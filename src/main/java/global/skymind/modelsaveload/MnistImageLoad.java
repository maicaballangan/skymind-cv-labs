package global.skymind.modelsaveload;

import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**

 *  This examples builds on the MnistImagePipelineExample
 *  by loading the trained network
 *
 * To run this sample, you must have
 * (1) save trained mnist model
 * (2) test image
 */

public class MnistImageLoad
{
    private static Logger log = LoggerFactory.getLogger(MnistImageLoad.class);

    public static void main(String[] args) throws Exception
    {
        // image information
        // 28 * 28 grayscale
        // grayscale implies single channel
        int height = 28;
        int width = 28;
        int channels = 1;


        File modelSave =  new File(System.getProperty("java.io.tmpdir"), "/trained_mnist_model.zip");

        if(modelSave.exists() == false)
        {
            System.out.println("Model not exist. Abort");
            return;
        }
        File imageToTest = new ClassPathResource("image/five.png").getFile();

        /*
		Load the saved model
        */
        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(modelSave);
        // Use NativeImageLoader to convert to numerical matrix
        NativeImageLoader loader = new NativeImageLoader(height, width, channels);
        // Get the image into an INDarray
        INDArray image = loader.asMatrix(imageToTest);

        /*
		[Optional] Preprocessing to 0-1 or 0-255
        */
        DataNormalization scaler = new ImagePreProcessingScaler(0,1);
        scaler.transform(image);

        /*
		[Optional] Pass to the neural net for prediction
        */
        INDArray output = model.output(image);
        log.info("Label:         " + Nd4j.argMax(output, 1));
        log.info("Probabilities: " + output.toString());
    }

}
