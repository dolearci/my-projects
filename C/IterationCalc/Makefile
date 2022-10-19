CFLAGS=-std=c99 -Wall -g
BIN=BisectionMethod
CC=gcc
RM=rm -f

ALL: BisectionMethod.o
	$(CC) $(CFLAGS) -o $(BIN) BisectionMethod.o -lm

clean:
	$(RM) *.o $(BIN)