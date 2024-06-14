package global.skymind.modelsaveload;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Random;

/**
 *  This builds on the MnistImagePipelineExample
 *  by Saving the Trained Network
 */
public class MnistImageSave
{
    /** Data URL for downloading */
    private static Logger log = LoggerFactory.getLogger(MnistImageSave.class);

    public static void main(String[] args) throws Exception
    {

        // image information
        // 28 * 28 grayscale
        // grayscale implies single channel

        int height = 28;
        int width = 28;
        int channels = 1;
        int rngseed = 123;

        Random randNumGen = new Random(rngseed);
        int batchSize = 128;
        int outputNum = 10;
        int numEpochs = 1;

        // DataSet Iterator
        DataSetIterator mnistTrain = new MnistDataSetIterator(batchSize,true, rngseed);


        // Scale pixel values to 0-1
        DataNormalization scaler = new ImagePreProcessingScaler(0,1);
        scaler.fit(mnistTrain);
        mnistTrain.setPreProcessor(scaler);


        // Build Our Neural Network

        log.info("**** Build Model ****");

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(rngseed)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Nesterovs())
                .l2(1e-4)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(height * width)
                        .nOut(100)
                        .activation(Activation.RELU)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(100)
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .backpropType(BackpropType.Standard)
                .setInputType(InputType.convolutional(height,width,channels))
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();

        model.setListeners(new ScoreIterationListener(10));


        log.info("*****TRAIN MODEL********");
        for(int i = 0; i<numEpochs; i++)
        {
            model.fit(mnistTrain);
        }


        log.info("******SAVE TRAINED MODEL******");
        // Where to save model
        File locationToSave = new File(System.getProperty("java.io.tmpdir"), "/trained_mnist_model.zip");
        log.info(locationToSave.toString());

        // boolean save Updater
        boolean saveUpdater = false;

        // ModelSerializer needs modelname, saveUpdater, Location
        ModelSerializer.writeModel(model,locationToSave,saveUpdater);

        log.info("******PROGRAM IS FINISHED PLEASE CLOSE******");
    }


}
