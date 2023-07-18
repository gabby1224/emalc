# EmALC

This repository provides the code for training EmALC, a neural-symbolic model that can use the knowledge with expressivity no more than $\mathcal{ALC}$ from any OWL ontologies to guide the learning of neural models.

## Overview

The input of EmALC is an OWL ontology and a neural model. In the structure of EmALC, the domain of discourse is the object set of the neural model, and the signature is the union of concept names and role names in the ontology. By reconstructing the output of the neural model into an interpretation of the ontology, we can revise the neural model through learning with the hierarchical loss presented in the paper. The parameters of the neural model can also be revised in a way of multi-task learning, which is easy to be extended with the codes in this repository.
The output of EmALC is the revised neural model output (/revised neural model parameters with the extension).

## Details for Reproducing

### Preprocessing for the OWL ontology

The input OWL ontology should be truncated into its $\mathcal{ALC}$ fragment, and then be normalized. 
Run the following command with **JDK 1.8** under [the root of this directory](https://anonymous.4open.science/r/EmALC/): 

    java -jar Normalization.jar training/ontologies training/input

The output of preprocessing is the files in 'training/input':

- 'concepts.txt', 'roles.txt', 'individuals.txt': the concept names(/role names/individual names) set.
- 'normalization.txt': the nomalized TBox axioms.
- 'abox.txt': the abox assertions.
- 'subclassaixoms.txt': the original TBox axioms.

Note: The source code of 'Normalization.jar' and 'CQGenerator.jar' is in [normalization](https://anonymous.4open.science/r/EmALC/normalization). If you want to repackage the jar based on our source code, remember to delete all dependencies named 'owlapi-xxx.jar' in the artifact, while only remain the 'owlapi-distribution-5.1.3.jar'. 

### Training

The training and evaluation is in [training](https://anonymous.4open.science/r/EmALC/training), to train EmALC, run:

    python .\run.py --info_path input --out_path output --save_path output --iter_path ontologies --mask_rate 0.2 --alpha 0.8 --device_name cpu

For evaluation, we randomly masked the ABox of the input ontology as the initial output of the neural models, so can evaluate the performance of EmALC when meeting with different distributions. The generation of the masked ABox (imitation of the output of a neural model) is in [Evaluation.MaskABox](https://anonymous.4open.science/r/EmALC/training/Evaluation.py), the masked ABox and the original ABox are saved in '--save_path'. And the mask rate is designated by '--mask_rate'. While '--alpha' is the threshold of truth value for the transformation between fuzzy ALC and crisp ALC. And the masked value is in the range of (1-alpha, alpha). The model also supports using GPU, with '--device_name cuda:0'.

For comparison with the Logical Tensor Network, run:

    python .\run.py --info_path input --out_path output --save_path output --iter_path ontologies --mask_rate 0.2 --alpha 0.8 --device_name cpu --model_name LTN


### Performance Evaluation

The revised results of EmALC and LTN are evaluated under the semantics of fuzzy first-order logic, with codes in [training/evaluation](https://anonymous.4open.science/r/EmALC/training/evaluation/). To compute the successful rate, run [run.ipynb](https://anonymous.4open.science/r/EmALC/training/evaluation/run.ipynb)

To do the conjunctive query answering (CQA) evaluation, firstly,
generate the conjunctive queries and answers:

    java -jar CQGenerator.jar training/ontologies training/input

Then run [CQAnswering_evaluation.ipynb](https://anonymous.4open.science/r/EmALC/training/CQAnswering_evaluation.ipynb) to generate the CQA evaluation results.

### Application: Semantic Image Interpretation
ALC ontologies and EL ontologies are saved in [SII/ontologies](https://anonymous.4open.science/r/EmALC/SII/ontologies).
Run evaluation with EmALC on notebook [SII_code/dfalc_run.ipynb](https://anonymous.4open.science/r/EmALC/SII/SII_code/dfalc_run.ipynb). Run evaluation with LTN on notebook [SII_code/run_ltn.ipynb](https://anonymous.4open.science/r/EmALC/SII/SII_code/run_ltn.ipynb). 

## Dependencies

    JDK 1.8
    python 3.7.0
    torch 1.8.1
    python-csv 0.0.13
    matplotlib 3.3.2
    pickle 4.0
    numpy 1.21.4
    pandas 1.1.3
    pyparsing 3.0.6
    loguru 0.6.0
## Performance Evaluation Results

Results of EmALC and LTN are output in [output](https://anonymous.4open.science/r/EmALC/training/output/), [product_output](https://anonymous.4open.science/r/EmALC/training/product_output/), respectively. We zipped the training results in [results](https://drive.google.com/drive/folders/1ob0RVM6GwAQvgew9yZTrCfNrfvbWFKRb?usp=sharing).


