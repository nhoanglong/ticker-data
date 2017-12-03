ticker-data processing and tranformation
=====

This document contains many sections: usage description, data source and data processing & transformation: include TWAP, SMA, LWMA.


####1. Requirement

Java 1.8


####2. Usage

To run the CLI, `cd` to the folder contains `data.jar`.

a. For Help, type:
`java -jar data-jar-with-dependencies.jar --help`

```
usage: ticker-data CLI
 -h,--help           Show help.
 -m,--models <arg>   Expected models that will generated, separated by
                     colon.
                     For example: TWAP,SMA
 -t,--ticker <arg>   Ticker symbol which generates TWAP, SMA, LWMA.
```

b. For retrieving and processing historical data of a ticker symbol, uses the `-t` or `--ticker` argument. For example, to retrieve and process historical data of `GE` ticker symbol, type: 
`java -jar data-jar-with-dependencies.jar --ticker GE`

After complete the process, find the output result of REQ #1, REQ #2 and REQ #3 in `ticker-name.csv` and `ticker-name.json`. Such as, in the above case for `GE`, those files are `GE.json` contains data in JSON format and `GE.csv` contains data in csv format.

For different tickers have different output files. If we have one common file for all tickers, than, each time run CLI, we have to rewrite the data and the old data will be lost. With different files for different tickers approach, end-users can view and compare different tickers result. 

Furthermore, the output result of REQ #4 is in `alerts.dat`.

####3. Data source

The data is retrieved from `https://www.quandl.com/` as the following URL for `GE`:

`https://www.quandl.com/api/v3/datasets/WIKI/GE.csv?order=asc`

The reason why the author decided to retrieve data as ascending order is for convenient and efficient `SMA` and `LWMA` computation. More detail explanation will be discuss later sections.

The author also had planned to retrieve only necessary data, include date, open, high, low, close and volume values only for performance purpose. Unfortunately, the time series data of `https://www.quandl.com/` not support for multiple columns specification, it only support one column retrieval using `column_index` argument.

Furthermore, the csv format has best format when compares to JSON and XML. Those two contain multiple unnecessary tags and symbol (such as XML tags, or JSON metadata, etc).

As a result, the author decided to choose the csv format for dataset retrieval.

####4. TWAP

Time Weighted Average Prices for the Open, High, Low and Close prices. These values are calculated as the average of each over every day in the range. 

That means we will calculate the average of Open, High, Low and Close prices in the range as TWAP-Open, TWAP-High, TWAP-Low and TWAP-Close. For example, we have 3 days as: [72, 73, 74, 75], [50, 51, 52, 53], [60, 61, 62, 63]. Hence, the TWAP-Open for that day is: (72+50+60)/3 ~ 60.67. The same for others.

####5. Simple Moving Average

