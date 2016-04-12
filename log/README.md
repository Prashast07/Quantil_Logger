Quantil Logger

==============

git clone https://github.com/Prashast07/Quantil_Logger.git

cd log

mvn clean install

cd src/main/resources/

./generate.sh <DATA_PATH>

  Example : ./generate.sh /Users/Prashast/Desktop/log/src/main/resources/generateLogFor1Day.in
  
./query.sh <DATA_PATH>

  Example : ./query.sh /Users/Prashast/Desktop/log/src/main/resources/generateLogFor1Day.in
  
QUERY 192.168.0.10 0 2016-04-11 22:30 2016-04-11 22:35

QUERY 192.168.0.10 1 2016-04-11 22:30 2016-04-11 22:35

QUERY 192.168.1.10 1 2016-04-11 22:30 2016-04-11 22:35

EXIT
