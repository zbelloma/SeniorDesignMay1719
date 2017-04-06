import os
import glob
import time

import serial

specPort = serial.Serial("/dev/ttyAMA0", baudrate=9600, parity=serial.PARITY_NONE)

##There are five baudrates supported by the spectrometer
## K1 - 2400
## K2 - 4800
## K3 - 9600 (default)
## K4 - 38400
## K6 - 115200
## Currently using K6 in order to have data transmission take the least amount of time
## If a different buadrate is desired, simply change the value for the command variable
## to the value associated with the desired baudrate.
## (The values for command are listed above in the form of "K1","K2",...)

command = "K6"
specPort.write(command)
returnByte=specPort.read(1)
print returnByte

if returnByte == "\0x06":

    #Program needs to wait longer than 50 milliseconds for the spectrometer to change baudrates
    time.sleep(0.1)

    ##Close old serial port
    specPort.close()
    specPort=None

    ##Create new serial port with faster baudrate
    if command == "K1":
        print "New baudrate of 2400"
        newSpecPort = serial.Serial("/dev/ttyAMA0", baudrate=2400, parity=serial.PARITY_NONE)
    elif command == "K2":
        print "New baudrate of 4800"
        newSpecPort = serial.Serial("/dev/ttyAMA0", baudrate=4800, parity=serial.PARITY_NONE)
    elif command == "K3":
        print "New baudrate of 9600"
        newSpecPort = serial.Serial("/dev/ttyAMA0", baudrate=9600, parity=serial.PARITY_NONE)
    elif command == "K4":
        print "New baudrate of 38400"
        newSpecPort = serial.Serial("/dev/ttyAMA0", baudrate=38400, parity=serial.PARITY_NONE)
    elif command == "K6":
        print "New baudrate of 115200"
        newSpecPort = serial.Serial("/dev/ttyAMA0", baudrate=115200, parity=serial.PARITY_NONE)
    
    ##Send the command again at the new baudrate
    newSpecPort.write(command)
    ##receive the ACK or NAK at the new baudrate
    returnByte=newSpecPort.read(1)
    print returnByte
    
    if returnByte == "\0x06":
        returnByte=newSpecPort.read(1)
    elif returnByte == "\0x15":
        print "Something went wrong."

elif returnByte == "\0x15":
    ##Do something here...



