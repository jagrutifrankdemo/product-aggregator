package com.ennov.readTest;

import com.ennov.read.dao.Product;
import com.ennov.read.impl.CSVFileOpsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.hasItem;

@ExtendWith(MockitoExtension.class)
public class CSVFileOpsTest {

    @InjectMocks
    CSVFileOpsImpl CSVFileOps;

    File resourcesDirectory = new File("src/test/resources");
    @Test
    void readCSVTest() throws IOException {
        //Stream<Product> productStream= CSVFileOps.readCSVFile("product.csv", "D:\\Development\\product-aggregator\\src\\test\\resources\\");
        Stream<Product> productStream= CSVFileOps.readCSVFile("product.csv", resourcesDirectory.getPath()+"\\");

        List<Product> result = productStream.toList();
        assertEquals(result.size(),1000); //check number of records in file are all read

        //check for particular record
        Product p = new Product("0", "Theta Powder","Theta Powder Super","Theta",	"3.6",null);

        //check size
        assertThat(result, hasItem(p)); //filtered record contains required record

    }

    @Test
    void getFilteredList() throws IOException {
        ArrayList<Product> filteredListonProductLine = (ArrayList<Product>) CSVFileOps.getFilteredList(1,
                "Theta Powder",

                resourcesDirectory.getPath()+"\\", "product.csv");
        Assert.notEmpty(filteredListonProductLine, "filteredList should not be empty");

        Product p = new Product("0", "Theta Powder","Theta Powder Super","Theta",	"3.6", null);
        Product filteredProduct= filteredListonProductLine.get(0);

        //check size
        assertThat(filteredListonProductLine, hasItem(p)); //filtered result contains required record

        assertTrue(p.equals(filteredProduct)); //check if record 0 is the required record.


    }


    @Test
    void getsummationofAllProductPricesNoFilters() throws Exception {

        Stream<Product> productStream = CSVFileOps.readCSVFile("product.csv", resourcesDirectory.getPath()+"\\");
        //getsum
        double sum = CSVFileOps.returnSumofPrices(productStream, "product.csv", resourcesDirectory.getPath()+"\\");
        assertEquals(sum, 4540.7);
    }
    @Test
    void getsummationofFilteredProduct() throws Exception {

        //getfilteredList andthen call Sum emthod

        //int columnNo, String value, String path, String fileName,HeaderColumnNameTranslateMappingStrategy strategy
        ArrayList<Product> filteredListonProductLine = (ArrayList<Product>) CSVFileOps.getFilteredList(1,
                "Theta Powder",

                resourcesDirectory.getPath()+"\\", "product.csv");
        Assert.notEmpty(filteredListonProductLine, "filteredList should not be empty");

       //summation of FilteredList
        double sum= CSVFileOps.returnSumofPrices(filteredListonProductLine.stream(), "product.csv", resourcesDirectory.getPath()+"\\");
        assertEquals(sum,15.3);

    }

    @Test
    void getdiffernces() throws Exception {
        Stream<Product> productStream = CSVFileOps.readCSVFile("product.csv", resourcesDirectory.getPath()+"\\");
        List<Product> productArrayList= (List<Product>)
                CSVFileOps.getdifferenceForAllRecords(productStream,"product.csv",resourcesDirectory.getPath()+"\\");
        Product p = new Product("0", "Theta Powder","Theta Powder Super","Theta",	"3.6", "Super");

        //check size
        assertTrue(productArrayList.size()== 1000);

        Product filteredProduct= productArrayList.get(0);
        assertTrue(p.equals(filteredProduct)); //check if record 0 is the required record i.e it contains difference for the reqcord.


    }



}