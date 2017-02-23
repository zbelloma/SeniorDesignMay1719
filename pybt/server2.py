import os
import glob
import time

import serial
from bluetooth import *
import binascii
import struct

server_sock = BluetoothSocket(RFCOMM)
server_sock.bind(("", PORT_ANY))
server_sock.listen(1)

specPort = serial.Serial("/dev/ttyAMA0", baudrate=9600, parity=serial.PARITY_NONE)
port = server_sock.getsockname()[1]

uuid = "00001101-0000-1000-8000-00805f9b34fb"

advertise_service(server_sock, "SpectroPi",
service_id=uuid,
service_classes=[uuid, SERIAL_PORT_CLASS],
profiles=[SERIAL_PORT_PROFILE],
#                   protocols = [ OBEX_UUID ]
)
while True:
    print "Waiting for connection on RFCOMM channel %d" % port
    client_sock, client_info = server_sock.accept()
    print "Accepted connection from ", client_info
    while True:
        try:
            data = client_sock.recv(1024)
            if len(data) == 0: break
            ##print "received [%s]" % data

            if "S" in data:
                specPort.write("S")
                lastByte = None
                lastData = None
                output = ""
                specPort.read(1)
                while lastData != 65533:
                    lastByte = specPort.read(2)
                    lastData = struct.unpack(">H", binascii.a2b_hex(binascii.b2a_hex(lastByte)))[0]
                    output += (str(lastData) + " ")
                ##print "\n\n" + output
                client_sock.send(output)
                ##print "Scan Sent"

            elif "?x17" in data:
                sendstring = "\x3F\x78\x00\x11"
                specPort.write(sendstring)
                lastData = None
                lastByte = None
                output = ""

                while len(output) < 40:
                    lastByte = specPort.read(1)
                    lastData = binascii.b2a_hex(lastByte)
                    output += (str(lastData) + " ")
                    print "\n" + output
                client_sock.send(output)
                print "Calibration Sent"

        except IOError:
            print "bad stuff"
            break
    ##    try:
    ##        data = client_sock.recv(1024)
    ##        response = "Received scan command"
    ##
    ##        if len(data) == 0: break
    ##        print "received [%s]" % data
    ##
    ##        if data == "S": client_sock.send(str.encode(response))
    ##
    ##    except IOError:
    ##        pass

        except KeyboardInterrupt:

            print "disconnected"

            client_sock.close()
            server_sock.close()
            print "all done"

            break

    print "connection dropped"
    client_sock.close()

