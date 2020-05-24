package Utilities;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;

;


public class YAMLReader {
    private LinkedHashMap<String,LinkedHashMap> ymlData;
    private LinkedHashMap DataSetMap;
    private String FileWithpath;
    private String filename;

    public YAMLReader(String FileWithpath) {
        this.FileWithpath = FileWithpath;
    }



    public LinkedHashMap<String,LinkedHashMap> GetDataSet(String DataSet){
        Yaml yaml =new Yaml();
        try{
            InputStream inputStream =new FileInputStream(FileWithpath);
            ymlData = yaml.loadAs(inputStream,LinkedHashMap.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.print(ymlData.get(DataSet));
        return ymlData.get(DataSet);
    }
}

