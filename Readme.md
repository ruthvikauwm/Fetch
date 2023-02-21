Assignment #1
Background:

Fecth users have points in their accounts. Users only see a single balance in their account. But for reporting purposes, Fetch actually track their points per payer. In our system, each transaction record contains: payer (string), points (integer), timestamp (date).

For earning points, it is easy to assign a payer. We know which actions earned the points. And thus, which partner should be paying for the points.

When a user spends points, they don't know or care which payer the points come from. But, our accounting team does care how the points are spent. There are two rules for determining what points to "spend" first:
● We want the oldest points to be spent first (oldest based on transaction timestamp, not the order they’re received)
● We want no payer's points to go negative.

write a program that reads from a CSV file called transactions.csv - https://fetch-hiring.s3.amazonaws.com/transactions.csv)

Code is expected to
1. Read the transactions from a CSV file.
2. Spend points based on the argument using the rules above.
3. Return all payer point balances.

Solution:

Note: Java software is required to run the program

Code is written in the Accounts.java file.

Please run the following commands in the 'Fetch' directory to execute the program

#1: To compile: javac Accounts.java     
#2: To run: java Accounts 5000 transactions.csv

Expected output:

UNILEVER : 0
MILLER COORS : 5300
DANNON : 1000

