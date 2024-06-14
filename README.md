# skymind-cv-labs

## Built with
- deeplearning4j beta 5.0
- CUDA 10.0 (Note: Optional if you are using CPU)
- cuDNN 7.6 (Note: Optional if you are using CPU)

## Getting Started ##

### Install Java ###

Download Java JDK
[here](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).  
(Note: Use Java 8 for full support of DL4J operations)

Check the version of Java using: 
```sh
java -version
```

Make sure that 64-Bit version of Java is installed.

### Install IntelliJ IDEA Community Edition ###
Download and install 
[IntelliJ IDEA](https://www.jetbrains.com/idea/download/).

### Install Apache Maven  *Optional* ###
IntelliJ provides a default Maven that is bundled with the installer.
Follow these [instructions](https://maven.apache.org/install.html) to install Apache Maven.

### GPU setup  *Optional* ##
Follow the instructions below if you plan to use GPU setup.
1. Install CUDA and cuDNN <br> 
    Requirements:
   -  CUDA 10.0 
   -  cuDNN 7.6


CUDA and cuDNN can be downloaded from
[here](https://developer.nvidia.com/cuda-10.0-download-archive) and
[here](https://developer.nvidia.com/cudnn). Step by step installation
guides can be found
[here](https://docs.nvidia.com/deeplearning/sdk/cudnn-install/index.html).

2. Dependencies are needed to be included into Maven project if we wish
   to use GPU for training. Follow the links below for instructions in
   details.
   -  [ND4J backends for GPUs](https://deeplearning4j.org/docs/latest/deeplearning4j-config-gpu-cpu)
   - [Using Deeplearning4J with cuDNN](https://deeplearning4j.org/docs/latest/deeplearning4j-config-cudnn)
