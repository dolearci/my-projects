CC=g++
CFLAGS=-Wall -Wextra 
NAME=secret

TARGET = secret
 
all: $(TARGET)
 
$(TARGET): $(TARGET).cpp
	$(CC) $(CFLAGS) -o $(TARGET) $(TARGET).cpp -pedantic -lpcap -lcrypto -lssl -ggdb3
 
clean:
	$(RM) $(TARGET)