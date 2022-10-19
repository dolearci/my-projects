/**************************************
 *  VUT FIT ISA 1.project 2021/2022   *
 *   Daniel Olearčin                  *
***************************************/
#include <stdio.h>
#include <stdlib.h>
#include <pcap.h>
#include <pcap/pcap.h>
#include <errno.h>
#include <sys/socket.h>
#define __FAVOR_BSD         
#include <netinet/ip.h>
#include <netinet/tcp.h>
#include <netinet/udp.h>
#include <arpa/inet.h>
#include <netinet/ip_icmp.h>
#include <openssl/aes.h>
#include <netinet/if_ether.h> 
#include <err.h>
#include <iostream>
#include <fstream>
#ifdef __linux__            
#include <netinet/ether.h> 
#include <time.h>
#include <pcap/pcap.h>
#include <openssl/aes.h>
#include <string>
#include <string.h>
#include <vector>
#include <iterator>
#include <netdb.h>
#include <unistd.h>
#endif
using namespace std;
#ifndef PCAP_ERRBUF_SIZE
#define PCAP_ERRBUF_SIZE (256)
#endif

#define SIZE_ETHERNET (14)       
#define header_len (42)

//Arguments parsing
void *arguments_parsing(int argc, char **argv, char **file, char **host, bool *l_arg)
{	
	bool r = false;
	bool s = false;
	bool l = false;
	//for loop for every argument
	for (int i = 1; i < argc; i++)
    {	
        if (strcmp(argv[i],"--help") == 0)
        {
            printf("Prenos souboru skrz skryty kanal\n");
            printf("Arguments: secret -r <file> -s <ip|hostname> [-l] \n");
			exit(0);
        }   
		else if (strcmp(argv[i],"-r") == 0)
		{
			r = true;
			i++;
            *file = argv[i];
		}    
		else if (strcmp(argv[i],"-s") == 0)
		{
			s = true;
			i++;
            *host = argv[i];
		}    
		else if (strcmp(argv[i],"-l") == 0)
		{
			*l_arg = true;
			l = true;
		}    
        else
        {
			printf("Zle pouzitie\n");
            printf("Prenos souboru skrz skryty kanal\n");
            printf("Arguments: secret -r <file> -s <ip|hostname> [-l] \n");
            exit(1);
        }
    }
	//Condition for not sever, both r and s must be set
	if(!l)
	{
		if(!(r && s))
		{	
			printf("Zle pouzitie\n");
        	printf("Prenos souboru skrz skryty kanal\n");
        	printf("Arguments: secret -r <file> -s <ip|hostname> [-l] \n");
        	exit(1);
		}
	}
}
//Arguments parsing


void *get_in_addr(struct sockaddr *sa)
{
	if (sa->sa_family == AF_INET)
	{
		return &(((struct sockaddr_in *)sa)->sin_addr);
	}
	return &(((struct sockaddr_in6 *)sa)->sin6_addr);
}

//Pcap handler
void mypcap_handler(u_char *args, const struct pcap_pkthdr *header, const u_char *packet)
{
  // creating variables for decrypting
  AES_KEY key_d;
  AES_set_decrypt_key((const unsigned char *)"xolear0000000000", 128, &key_d);

  //creating variables for packet handling and getting message from packet
  unsigned char *data = new unsigned char[header->len];
  unsigned char final_output_dec[header->len - header_len + 1];
  memset(&final_output_dec, 0, header->len - header_len + 1);
  unsigned char *data_dec = new unsigned char[AES_BLOCK_SIZE];
  memcpy(data,packet,header->len);
  memmove(data, data + header_len, header->len - header_len);
  int file_cnt = 0;
  int counter = 2;
  int dec_counter = 1;
  int path = 0;
  char erase[2];
  vector <char> fileV;
  
  //decrypting message from packet
  AES_decrypt(data,data_dec, &key_d);
  
  //Checking if its the right packet
  if(!(data_dec[0] == '<' && data_dec[1] == '<'))
  {
    free(data);
    free(data_dec);
    return;
  }
  //Checking if its the right packet

  //While loop for getting the file name and number of added chars
  while(1)
  {
	//Getting file name
    while(data_dec[counter] != '<' && counter != 16)
    { 
      path++;
      if(data_dec[counter] == '/')
        fileV.clear();
      else
        fileV.push_back(data_dec[counter]);
      counter++;
    }
	//Getting number of added chars and adding rest of message to final_output
    if(data_dec[counter] == '<')
    { 
      if(++counter < 15)
      {
        erase[0] = data_dec[counter];
        erase[1] = data_dec[++counter];
		file_cnt = 16 - counter - 1;
        memcpy((char*)final_output_dec, (const char *)data_dec + counter + 1, 16 - counter - 1);
        memmove(data, data + AES_BLOCK_SIZE, header->len - header_len);
        break;
      }
      else
      {
        counter = 0;
	    memmove(data, data + AES_BLOCK_SIZE, header->len - header_len);
        AES_decrypt(data,data_dec, &key_d);
        erase[0] = data_dec[counter];
        erase[1] = data_dec[++counter];
		file_cnt = 16 - counter - 1;
        memcpy((char*)final_output_dec, (const char *)data_dec + counter + 1, 16 - counter - 1);
        memmove(data, data + AES_BLOCK_SIZE, header->len - header_len);
		dec_counter++;
        break;
      }
    }
    counter = 0;
	memmove(data, data + AES_BLOCK_SIZE, header->len - header_len);
    AES_decrypt(data,data_dec, &key_d);
    dec_counter++;
  }
  //While loop for getting the file name and number of added chars
  
  //Decrypting the rest of message
  for (int i = 0 ; i < (int)(header->len - header_len - dec_counter*AES_BLOCK_SIZE); i += 16)
  {
	AES_decrypt(data,data_dec, &key_d);
    memcpy((char*)final_output_dec + file_cnt + i , (const char *)data_dec, 16);
	memmove(data, data + AES_BLOCK_SIZE, header->len - header_len);
  }
  //Decrypting the rest of message

  //writing to file decrypted message
  char *file = new char[fileV.size()];
  copy(fileV.begin(), fileV.end(), file);
  ofstream outdata;
  outdata.open(file,ios_base::app); 
  if( !outdata ) 
  { 
    cerr << "Error: file could not be opened" << endl;
    exit(1);
  } 
  for (size_t i = 0; i < header->len - header_len - path - 5 - ((int)erase[1] - 48) - (((int)erase[0] - 48)*10); i++)
  {	
      outdata << final_output_dec[i];
  }
  //writing to file decrypted message

  //freeing allocated variables
  delete []file;
  delete []data;
  delete []data_dec;
}
//Pcap handler

int main(int argc, char **argv)
{
	char *host;
	char *file;
	bool l_arg = false;
	arguments_parsing(argc,argv,&file,&host,&l_arg);
	if(l_arg)
	{	
		///////////////////////////////////////////////////////////
		//Inspirovane zo suboru v Examples ISA (sniff-filter.c)//
		///////////////////////////////////////////////////////////
		
		char errbuf[PCAP_ERRBUF_SIZE];  // constant defined in pcap.h
		pcap_t *handle;                 // packet capture handle 
		pcap_if_t *alldev;				// a list of all input devices
		char *devname;                  // a name of the device
		struct in_addr a,b;
		bpf_u_int32 netaddr;            // network address configured at the input device
		bpf_u_int32 mask;               // network mask of the input device
		struct bpf_program fp;          // the compiled filter

		// open the input devices (interfaces) to sniff data
		if (pcap_findalldevs(&alldev, errbuf))
			err(1,"Can't open input device(s)");

		devname = alldev->name;  // select the name of first interface (default) for sniffing 
		
		// get IP address and mask of the sniffing interface
		if (pcap_lookupnet(devname,&netaddr,&mask,errbuf) == -1)
			err(1,"pcap_lookupnet() failed");

		a.s_addr=netaddr;
		printf("Opening interface \"%s\" with net address %s,",devname,inet_ntoa(a));
		b.s_addr=mask;
		printf("mask %s for listening...\n",inet_ntoa(b));

		// open the interface for live sniffing
		if ((handle = pcap_open_live(devname,BUFSIZ,1,1000,errbuf)) == NULL)
			err(1,"pcap_open_live() failed");

		// compile the filter
		if (pcap_compile(handle,&fp,"icmp",0,netaddr) == -1)
			err(1,"pcap_compile() failed");
		
		// set the filter to the packet capture handle
		if (pcap_setfilter(handle,&fp) == -1)
			err(1,"pcap_setfilter() failed");

		// read packets from the interface in the infinite loop (count == -1)
		// incoming packets are processed by function mypcap_handler() 
		if (pcap_loop(handle,0,mypcap_handler,NULL) == -1)
			err(1,"pcap_loop() failed");

		// close the capture device and deallocate resources
		pcap_close(handle);
		pcap_freealldevs(alldev);
	}
	
	//Creating variables needed
	char packet[1500];
	struct icmphdr *icmp_header = (struct icmphdr *)packet;

	unsigned char *output = new unsigned char[AES_BLOCK_SIZE];
 	unsigned char *data = new unsigned char[AES_BLOCK_SIZE];
	unsigned char *final_output;

	char element;
	vector<char> fileV;
	char erase[2];
	int file_len;
	int program_counter = 0;
	
	arguments_parsing(argc,argv,&file,&host,&l_arg);

	AES_KEY key_e;
	AES_set_encrypt_key((const unsigned char *)"xolear0000000000", 128, &key_e);
	//Creating variables needed

	//Socket creating
	struct addrinfo hints, *serverinfo;
	memset(&hints, 0, sizeof(hints));

	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_RAW;
	
	if ((getaddrinfo(host, NULL, &hints, &serverinfo)) != 0)
	{
		fprintf(stderr, "getaddrinfo error\n");
		return 1;
	}
    int protocol = serverinfo->ai_family == AF_INET ? IPPROTO_ICMP : IPPROTO_ICMPV6;
    int sock = socket(serverinfo->ai_family, serverinfo->ai_socktype, protocol);
    while(sock == -1)
    {
        serverinfo = serverinfo->ai_next;
        if(serverinfo == NULL) 
        {
            fprintf(stderr, "creating socket error\n");
		    return 1;
        }
        sock = socket(serverinfo->ai_family, serverinfo->ai_socktype, protocol);
    }
	//Socket creating
	
	// Opening file
	ifstream fin(file);
	if(!fin)
    {
		fprintf(stderr, "opening file error\n");
        return -1;
    } 
    fin >> noskipws;
	// Opening file
    
	//while loop for every char in file
	while (fin >> element)
    {
		program_counter++;
		fileV.push_back(element);
		// File Data into vector(max 1024 per packet)
		while (program_counter < 1024 && fin >> element)
		{
			program_counter++;
			fileV.push_back(element);
		}
		program_counter = 0;
		// File Data into vector
		
		// Adding informations about packet to file vector for verification right packet
		file_len = strlen(file);
		if(file_len != 12)
			sprintf(erase, "%02ld", 16 - ((fileV.size() + 5 + strlen(file)) % 16) );
		else
			sprintf(erase, "%02ld", 16 - ((fileV.size() + 6 + strlen(file)) % 16) );
		if(erase[0] == '1' && erase[1] == '6')
		{
			erase[0] = '0';
			erase[1] = '0';
		}
		fileV.insert(fileV.begin(),erase[1]);
		fileV.insert(fileV.begin(),erase[0]);
		fileV.insert(fileV.begin(),'<');
		for(int i = file_len -1 ; i >= 0; i--)
		{
			fileV.insert(fileV.begin(),file[i]);
		}
		if(file_len == 12)
			fileV.insert(fileV.begin(),'/');
		fileV.insert(fileV.begin(),'<');  fileV.insert(fileV.begin(),'<');
		if((fileV.size() % 16) != 0)
		{
			for(int i = (fileV.size() % 16); i < 16; i++)
				fileV.push_back('x');
		}
		string s(fileV.begin(), fileV.end()); 
		// Adding informations about packet to file vector
		
		//Encrypting
		final_output = new unsigned char[s.length()];
		for (long unsigned int i = 0; i < s.length(); i += AES_BLOCK_SIZE)
		{   
			memcpy((char*)data, s.c_str() + i,16);	
			AES_encrypt(data, output, &key_e);
			memcpy((char*)final_output + i, (char*)output,16);
		}
		//Encrypting
		
		//settings of packet
		memset(&packet, 0, 1500);
		icmp_header->code = ICMP_ECHO;
		icmp_header->checksum = 0;
		memcpy(packet + sizeof(struct icmphdr), final_output, s.length());
		//Settings of packet
		
		//Sending packet
		if (sendto(sock, packet, sizeof(struct icmphdr) + s.length(), 0, (struct sockaddr *)(serverinfo->ai_addr), serverinfo->ai_addrlen) < 0)
		{
			fprintf(stderr, "sending socket error\n");
			return 1;
		}
		//Sending packet
		
		//Clear and free for next packet
		fileV.clear();
		delete [] final_output;
	}
	//Clearing the rest on the end
	delete [] output;
	delete [] data;
	freeaddrinfo(serverinfo);
	return 0;
}
