import os
import glob
import time
from subprocess import call

import serial
from bluetooth import *
import binascii
import struct
import RPIO

RPIO.setmode(RPIO.BOARD)

RPIO.setup(16,RPIO.OUT, initial=RPIO.LOW) ##Ready
RPIO.setup(18,RPIO.OUT, initial=RPIO.LOW) ##Scanning
RPIO.setup(22,RPIO.OUT, initial=RPIO.LOW) ##Connected
RPIO.setup(7,RPIO.OUT, initial=RPIO.LOW) ##Chinese Taser

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

RPIO.output(16,RPIO.HIGH) ##Light 'Ready' LED
while True:
    print "Waiting for connection on RFCOMM channel %d" % port
    client_sock, client_info = server_sock.accept()
    print "Accepted connection from ", client_info
    RPIO.output(22,RPIO.HIGH) ##Light 'Connected' LED
    while True:
        try:
            data = client_sock.recv(1024)
            if len(data) == 0: break
            ##print "received [%s]" % data

            if "S" in data:
                RPIO.output(18,RPIO.HIGH) ##Light 'Scanning' LED
                specPort.write("S")
                lastByte = None
                lastData = None
                output = ""
                specPort.read(1)
                while lastData != 65533:
                    lastByte = specPort.read(2)
                    lastData = struct.unpack(">H", binascii.a2b_hex(binascii.b2a_hex(lastByte)))[0]
                    print lastData
                    output += (str(lastData) + " ")
                ##print "\n\n" + output + "\n"
                #print len(output)
                client_sock.send(output)
                ##print "Scan Sent"
                RPIO.output(18,RPIO.LOW) ##Unlight 'Scanning' LED

            elif "zap" in data:
                #trigger chinese taser
                RPIO.output(18,RPIO.HIGH)
                RPIO.output(7,RPIO.HIGH) ##Zap
                time.sleep(1)
                RPIO.output(7,RPIO.LOW) ##Safe
                RPIO.output(18,RPIO.LOW)

            elif "I" in data:
                #might need modification
                specPort.write(data)
                lastByte = None
                LastData = None
                #output = ""
                lastByte = specPort.read(1)
                ##print lastByte
                
                if lastByte == '\x06':
                    print "Acknowledged"
                    client_sock.send("ACK")
                elif lastByte == '\x15':
                    print "NAK"
                    client_sock.send("NAK")
                else:
                    print "Bad return"
                    client_sock.send("NAK")

            #elif "P" in data:

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

            elif "kill" in data:
                call(["shutdown", "now"])
                

        except IOError:
            print "bad stuff"
            RPIO.output(22,RPIO.LOW) ##Unlight 'Connected' LED
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
            RPIO.output(16,RPIO.LOW) ##Unlight 'Ready' LED
            RPIO.output(18,RPIO.LOW) ##Unlight 'Scanning' LED
            RPIO.output(22,RPIO.LOW) ##Unlight 'Connected' LED

            break

    print "connection dropped"
    RPIO.output(22,RPIO.LOW) ##Unlight 'Connected' LED
    client_sock.close()

