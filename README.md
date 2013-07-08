# expenses

Example application for FRP presentation.

## Prerequisites

You will need Java JRE and  [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

Install mysql server.
    
    apt-get install mysql-client mysql-server

Create database expenses:

    mysql> create database expenses;

Create expenses table

    mysql> CREATE TABLE `expenses` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `summary` varchar(255) DEFAULT NULL,
      `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `amount` float DEFAULT NULL,
      `category` varchar(25) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=MyISAM DEFAULT CHARSET=latin1 

Grant access to expenses user

    mysql> grant all on expenses.* to expenses@localhost identified by 'expenses';
    

To start a web server for the application, run:

    lein run

Then open in browser http://localhost:8080/

## License

Copyright © 2013 Dmitry Kasimtsev
