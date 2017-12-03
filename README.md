ticker-data processing and tranformation
=====

This document contains many sections: usage description, data source and data processing & transformation: include TWAP, SMA, LWMA.

1. Requirement
--
Java 1.8
1. Usage:
--
To run the CLI, `cd` to the folder contains `data.jar`.
For Help, type: 
`java -jar data-jar-with-dependencies.jar --help`
```
usage: ticker-data CLI
 -h,--help           Show help.
 -m,--models <arg>   Expected models that will generated, separated by
                     colon.
                     For example: TWAP,SMA
 -t,--ticker <arg>   Ticker symbol which generates TWAP, SMA, LWMA.
```
