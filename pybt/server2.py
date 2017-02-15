import os
import glob
import time
import RPi.GPIO as GPIO
from bluetooth import *

server_sock = BluetoothSocket(RFCOMM)
server_sock.bind(("", PORT_ANY))
server_sock.listen(1)

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

    try:
        data = client_sock.recv(1024)
        response = "Received scan command"

        if len(data) == 0: break
        print "received [%s]" % data

        if data == "S": client_sock.send(str.encode(response))

except IOError:
pass

except KeyboardInterrupt:

print "disconnected"

client_sock.close()
server_sock.close()
print "all done"

break
