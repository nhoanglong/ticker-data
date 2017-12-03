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

####4 Main flow

The following steps describe main flow of the provided CLI:

```
Start
Retrieve dataset in ascending order
Loop through each record in dataset
	Compute TWAPs, SMAs, LWMAs and VA
	Append in ticker-name.csv
	Append JSONObject into JSONArray
	Append alert of REQ #4 at the end of alerts.dat if conditions are satisfied
End loop
Write JSONArray as JSONObject in ticker-name.json
End
```

With the above flow, the retrieved dataset is iterated only one time, means `O(n)` for all computations and writing data for CSV for and alerts. Except for JSON format, since we have to write as data as a JSONObject.

*If we retrieve data as descending order, then we have to run 2 loops, one via dataset for computation and holders result in memory, and then another loop for writing data, **means `O(2n)` compares to `O(n)` when doing all computations and writing data for CSV for and alerts**.

####4. TWAP

Time Weighted Average Prices for the Open, High, Low and Close prices. These values are calculated as the average of each over every day in the range. 

That means we will calculate the average of Open, High, Low and Close prices in the range as TWAP-Open, TWAP-High, TWAP-Low and TWAP-Close. For example, we have 3 days as: [72, 73, 74, 75], [50, 51, 52, 53], [60, 61, 62, 63]. Hence, the TWAP-Open for that day is: (72+50+60)/3 ~ 60.67. The same for others.

####5. Simple Moving Average

The reason why the author retrieves dataset as ascending order (from the past to present order), so we can examine from the past to calculate SMA of past N days.

The author decided to choose a Queue as a window, with the size for SMA-50 is 50 and size for SMA-200 is 200.

For example: the last five closing prices for MSFT are:
28.93+28.48+28.44+28.91+28.48 = 143.24 (this value will be stored, and re-use when adding new value for efficient performance)
Simple Moving Average in the past 5 days is:
SMA-5 = 143.24/5 = 28.65

When new value (assume 50) is added, so, we use the sum which is stored substract old value and add new value, **instead of looping and re-calculate when adding new value everytime**. This way provides better performance. For example: (143.24 - 28.93 + 50)/5 as new SMA-5. Hence it's O(n).

For the first N days in the series, set value to 0 when writing to files.

The complexity of this SMA is O(n) as explained above.

####5. Linearly Weighted Moving Average

The same as SMA, the reason why the author retrieves dataset as ascending order (from the past to present order), so we can examine from the past to calculate LWMA of past N days.

Since LWMA has common attributes (such as Queue) and basic logic as SMA, the author decided to create LWMA model based on SMA (LWMA will be extended from SMA, see source code for more detail).

For example, we have a queue as: tail [2, 4, 7, 4, 5] head, and multiplier is from 1-5.

Means 2x5 + 4x4 + 7x3 + 4x2 + 5x1 = 60 -> valuesSum

1 + 2 + 3 + 4 + 5 = 15 -> multiplierSum

2 + 4 + 7 + 4 + 5 = 22 -> basicSum

LWMA-5 = 60 / 15 = 4

When new value is added, so, we use the valuesSum which is stored substract old multiplied value and add new multiplied value, **instead of looping and re-calculate when adding new value everytime**. This way provides better performance as SMA.

For example, add new value as 6 into the queue, we have new queue as tail [6, 2, 4, 7, 4] head:

new valueSum = 6x5 + 2x(5-1) + 4x(4-1) + 7x(3-1) + 4x(2-1) = 6x5 + 2x5 + 4x4 + 7x3 + 4x2 + 5x1 - (2 + 4 + 7 + 4 + 5) = 6*5 + 60 - 22

new LWMA-5 = (90 - 22)/15 = 4.533

For the first N days in the series, set value to 0 when writing to files as SMA.

The complexity of this LWMA is O(n) as explained above.

#### 7. Alerts

One of the condition is calculating Volume Averge of past 50 days. This model has similar characteristics of SMA. Hence the author re-uses the SMA by providing Volume Average extends from SMA. For detail, please refer to source code, `sentifi.data.models.VolumeAverage` class.

The window size of VA-50 is 50. The same logic as SMA, however, instead of passing close price as value, Volume Average uses volume value. For logic explanation, please refer to SMA, since these two have similar logic.

The result of this REQ #4 is exported in `alerts.dat`. When end-users re-run the CLI (with different ticker), the newly generated alerts are APPENDED at the end of `alerts.dat`, instead of re-write the whole file. This also improve performance.