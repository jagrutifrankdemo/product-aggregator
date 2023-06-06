A ‘Product Aggregator’ application should read product data from a *.csv file.
<B> refer service method readCSVFile (read)</B>
<BR><BR>A ‘Product Aggregator’ application should implement a filter by ‘product line’.
<b>refer to method :achived by calling getFilteredList  using correct param values </B> <BR><BR>A ‘Product Aggregator’ application should implement a filter by ‘product brand’.
<b>refer to method :achived by calling getFilteredList  using correct param values </B> <BR><BR>A ‘Product Aggregator’ application should implement a sum of all product prices.
<b>method : returnSumofPrices</B> <BR><BR>A ‘Product Aggregator’ application should implement a filter by ‘product line’ and sum the filtered product prices.
<B>method:Achieved by calling two methods getFilteredList and returnSumofPrices</B> <BR><BR>A ‘Product Aggregator’ application should return a list of differences between the ‘product generics’ and the ‘product lines’ for all products. 
<B>method: getdifferenceForAllRecords</B>

Use mvn test to run all the test