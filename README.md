# Bitcoin Ethereum Exchange Rate Streaming with Sliding Windows

This project is an implementation of a streaming application that processes an exchange rate data stream using a sliding window with a fixed window size and slide size. The data is sourced from the Binance API and the sliding window is used to compute the moving average of the exchange rate over a certain period. The application is built using Apache Flink, a stream processing framework.

Getting Started
To run this project, you need to have Apache Flink installed on your machine. You can download and install Apache Flink from the official website https://flink.apache.org/.

After installing Apache Flink, you can clone this repository using the command below:

# Project Structure
The project contains two Scala classes:

git clone https://github.com/lachtarnour/BitcoinRateStreaming.git


`BinanceAPI`: This class is used to fetch the exchange rate data from the Binance API. It extends the SourceFunction[Double] class, which is a built-in Flink interface for defining a streaming source.

`BitcoinExchangeRate`: This class is the main entry point of the application. It sets up the streaming environment, adds the BinanceAPI source, applies a sliding window to the data stream, and prints the results to the console.

# Sliding Window Processing
In stream processing, a window is a finite-sized buffer that collects a subset of data items from an unbounded stream. The sliding window is a type of window that is used to process a continuous stream of data in a fixed-size buffer.

A sliding window has two parameters: window size and slide size. The window size specifies the number of data items that the buffer can hold, while the slide size determines the number of data items that are added to the buffer after each operation.

In this use case, we are processing an exchange rate data stream, and we want to compute the moving average of the exchange rate over a certain period. We can use a sliding window with a fixed window size and slide size to compute the moving average.

For example, if we use a window size of 10 seconds and slide size of 5 seconds, the sliding window will hold exchange rate data for 10 seconds at a time and slide over the stream, adding new exchange rate data to the buffer every 5 seconds.