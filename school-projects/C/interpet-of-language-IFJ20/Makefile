CFLAGS=-std=c99 -Wall -g
BIN=ifj20
CC=gcc
RM=rm -f

ALL: str.o ifj20.o symtable.o scanner.o stack.o parser.o 
	$(CC) $(CFLAGS) -o $(BIN) str.o ifj20.o symtable.o scanner.o stack.o parser.o 

clean:
	$(RM) *.o $(BIN)
