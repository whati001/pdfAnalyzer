# PDF file analyzer

## Description
Very simple and tiny pdf file analyzer.
It is more or less a small script, which iterates over all files with the `.pdf` extension from the given directory.
For each pdf file, it extracts following metadata:
* pageCount
* pageCount in color
* pageCount in grayscale
* pageDimension

Finally it collects all those metadata and calculates the final price.
Currently the price is still hardcoded in the source, sorry for that.

## Install
The project is managed by [Apache Maven](https://maven.apache.org/), hence you just need to clone and install.
```bash
git clone https://github.com/whati001/pdfAnalyzer.git
mvn clean compile install
# generated exectuable jar with dependencies
mvn clean compile assembly:single 
```

## Testing
The project does not include any test yet. I know it is bad, but hey feel free to add some.
```bash
mvn test
``` 

## Know-Issues
* hardcoded price value
* very poor user-interface
  
## Authors
* [whati001](https://github.com/whati001)