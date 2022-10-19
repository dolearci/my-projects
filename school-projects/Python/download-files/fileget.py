#!/usr/bin/env python3.8
import socket
import os
import re
import sys
from pathlib import Path
from urllib.parse import urlparse

"""
error codes
ok = 0
udp_error = 1
arg_error = 2
tcp_error = 3
"""

def arguments():
    if(len(sys.argv) == 5):
        if(sys.argv[1] == "-n"):
            server = sys.argv[2]
        else :
            if(sys.argv[3] == "-n"):
                server = sys.argv[4]
            else:
                print("Bad arguments, usage: fileget -n NAMESERVER -f SURL")
                sys.exit(2)
        nameserver_testing(server)
        if(sys.argv[1] == "-f"):
            url = sys.argv[2]
        else:
            if(sys.argv[3] == "-f"):
                url = sys.argv[4]
            else:
                print("Bad arguments, usage: fileget -n NAMESERVER -f SURL")

                sys.exit(2)
        url = url_testing(url)
        return server,url
    else:
        print("Bad number of arguments, usage: ./fileget -n NAMESERVER -f SURL")

        exit(2)

def nameserver_testing(nameserver):
    try:
        ip,port = nameserver.split(":")
        port = int(port)
        ip = ip.replace(".","")
        ip = int(ip)
    except:
        print("Nameserver error")
        sys.exit(2)
        
def url_testing(url):
    if(url[0:6] == "fsp://"):
        url = url[6:]
    else:
        print("Surl error")
        sys.exit(2)
    try:
        url_try = url[6:]
        url_try,filepath = url_try.split("/")
    except:
        print("Surl error")
        sys.exit(2)
    return url

def udp_connection(nameserver,surl):
    url,filepath = surl.split("/")
    message = f"WHEREIS {url}"
    message = message.encode()
    ip,port = nameserver.split(":")
    try:
        socket1 = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
        socket1.settimeout(5)
        socket1.connect((ip, int(port)))
    except:
        print("Problem with creating or connecting socket")
        sys.exit(1)
    socket1.send(message)
    try:
        data, addr = socket1.recvfrom(1024)
    except:
        print("Problem with receiving answer from server")
        sys.exit(1)
    socket1.close()
    data = data.decode('utf-8',errors='ignore')
    if(data[:2] != "OK"):
        print("Invalid response from the server")
        sys.exit(1)
    return data,url,filepath

def tcp_connection(data,filepath,url):
    ip,port = data.split(":")
    ip = ip[3:]
    try:
        socket2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        socket2.settimeout(5)
        socket2.connect((ip, int(port)))
    except:
        print("Problem with creating or connecting socket")
        sys.exit(3)
    message = f"GET {filepath} FSP/1.0\r\nHostname: {url}\r\nAgent:xolear00\r\n\r\n"
    message = message.encode()
    socket2.send(message)
    return get_file(socket2)

def get_file(socket2):
    file_end = []
    try:
        chars = socket2.recv(4096)
    except:
        print("Problem with receiving answer from server")
        sys.exit(3)
    chars = chars.decode('utf-8',errors='ignore')
    if(chars[:15] != "FSP/1.0 Success"):
        print("Error in tcp connection, file not found")
        sys.exit(3)
    while 1:
        try:
            chars = socket2.recv(4096)
        except:
            print("Problem with receiving answer from server")
            sys.exit(3)
        file_end.append(chars)
        if len(chars) == 0:
            break
    file_end = b"".join(file_end)
    socket2.close()
    return file_end

def print_file(file_end,filepath):
    file = open(filepath,"wb")
    file.write(file_end)
    file.close()

nameserver,surl = arguments()
data,url,filepath = udp_connection(nameserver,surl)
if(filepath == "*"):
    filepath = "index"
    index = tcp_connection(data,filepath,url)
    files = index.decode('utf-8',errors='ignore').splitlines()
    for file in files:
        dirname = os.path.dirname(file)
        if dirname:
            Path(dirname).mkdir(parents=True, exist_ok=True)
        file_end = tcp_connection(data,file,url)
        print_file(file_end,file)
    sys.exit(0)
else:
    file_end = tcp_connection(data,filepath,url)
    print_file(file_end,filepath)
    sys.exit(0)
