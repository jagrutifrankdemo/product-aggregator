package com.ennov.read;

import com.ennov.read.dao.Product;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface CSVFileOps {
    public Stream<Product> readCSVFile(String fileName, String path) throws IOException;
    public double returnSumofPrices(Stream<Product> productStream, String fileName, String path) throws IOException, Exception;
    public List getFilteredList(int columnNo, String value, String path, String fileName) throws IOException;
    public List getdifferenceForAllRecords(Stream<Product> productStream, String fileName, String path) throws IOException, Exception;
    }
