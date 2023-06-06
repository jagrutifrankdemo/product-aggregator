package com.ennov.read.impl;

import com.ennov.read.CSVFileOps;
import com.ennov.read.dao.Product;
import com.opencsv.CSVReader;
import com.opencsv.bean.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
public class CSVFileOpsImpl implements CSVFileOps {
// Global variable for setting strategy as it will be same accross ProductFile

    public HeaderColumnNameTranslateMappingStrategy<Product>  getProductStrategy(String fileName, String path) throws IOException {
       // HeaderColumnNameTranslateMappingStrategy
        HeaderColumnNameTranslateMappingStrategy<Product> strategy =
                new HeaderColumnNameTranslateMappingStrategy<Product>();
        strategy.setType(Product.class);
        strategy.setColumnMapping(getCSVMappingtoPojo());
  return strategy;
    }

@Override
public double returnSumofPrices(Stream<Product> productStream, String fileName, String path) throws IOException, Exception {
    // Create csvtobean and csvreader object
     return productStream
            .filter(p -> (p.getPrice()!=null && p.getPrice()!="" && p.getPrice()!="null"))
            .mapToDouble(p -> Double.parseDouble(p.getPrice())).sum();
}

    @Override
    public Stream<Product> readCSVFile(String fileName, String path) throws IOException {
        HeaderColumnNameTranslateMappingStrategy<Product> strategy =
                new HeaderColumnNameTranslateMappingStrategy<Product>();
        strategy.setType(Product.class);
        strategy.setColumnMapping(getCSVMappingtoPojo());

        // Create csvtobean and csvreader object
        CSVReader  csvReader = new CSVReader(
                Files.newBufferedReader(Paths.get(path+fileName)));

        CsvToBean csvToBean = new CsvToBean();
        csvToBean.setCsvReader(csvReader);
        csvToBean.setMappingStrategy(strategy);


        return csvToBean.stream(); //entire list as it is
    }

    //column number will be the from the csv starting with 0
    //eg/ productLine is columnNumber=1
    //int value "Theta Powder"
@Override
public List getFilteredList(int columnNo, String value, String path, String fileName) throws IOException {
      return  new CsvToBeanBuilder<Product>
            (Files.newBufferedReader(Paths.get(path+fileName)))
            .withType(Product.class)
            .withSeparator(',')
            .withFilter(line -> line[columnNo].equals(value))
            .withMappingStrategy(getProductStrategy(fileName, path))
            .withIgnoreEmptyLine(true)//ignore emplty line if incase any
            .build().parse();

}


    private Map getCSVMappingtoPojo() {
        Map<String, String> mapping = new
                HashMap<String, String>();
        mapping.put("product_id", "id");
        mapping.put("product_line", "line");
        mapping.put("product_brand", "brand");
        mapping.put("product_generic", "generic");
        mapping.put("product_price", "price");
        //mapping.put(getDiff(mapping.get("product_line"), mapping.get("product_brand")), "difference");

        return mapping;
    }

    private String getDiff(String str1, String str2){
        String[] strList1 = str1.split(" ");
        String[] strList2 = str2.split(" ");

        List<String> list1 = Arrays.asList(strList1);
        List<String> list2 = Arrays.asList(strList2);

        // Prepare a union
        List<String> union = new ArrayList<>(list1);
        union.addAll(list2);

        // Prepare an intersection
        List<String> intersection = new ArrayList<>(list1);
        intersection.retainAll(list2);

        // Subtract the intersection from the union
        union.removeAll(intersection);
        return StringUtils.join(union, " ");

    }

    @Override
    public List getdifferenceForAllRecords(Stream<Product> productStream, String fileName, String path) throws IOException, Exception {
        List<Product> pl = productStream.toList();
        // this might be costly operation, best done at database level
        for (Product strTemp : pl){
            strTemp.setDifference(getDiff(strTemp.getLine(),strTemp.getBrand()));
        }

        return pl;
   }
}



