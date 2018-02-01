# ipv4converter
Converts an IPV4 string address to an unique long value and viceversa.
What is this worth?
An IPV4 string occupies more string than a long.
An index on a long is quicker than on a string.

Suppose you have a relational table with one field for the ip address. Would be more convenient to have it as a long?.
Suppose you create an index on this field.
Suppose you have 30 million records.
This is applicable in other domains.

The time spent to execute this methods are really slow. Much less than a millisecond.
In a laptop with a cpu intel i7 4800MQ these are the results:

getIpString(4294967295) executed 50 times took an average of 5846,540000 nanoseconds 0,005847 milliseconds
getIpNumber(255.255.255.255) executed 50 times took an average of 31482,960000 nanoseconds 0,031483 milliseconds

Even more you can create a HashMap with your conversion and the respose time will be reduced.
