#include <SPI.h>
#include <Ethernet.h>
#include <EthernetClient.h>
#include <EthernetServer.h>

int trig = 8;
int echo = 3;
int flag=1;
float t = 0, h = 0;
byte mac[] = { 0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02 };
byte ip[]={192,168,137,1};
EthernetClient client;
//EthernetServer fcmServer(80);

void setup()
{
  Serial.begin(9600);
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    while(1){;}
  }
  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT);
}

void loop(){
  Ethernet.maintain();
  digitalWrite(trig, LOW);
  delayMicroseconds(2);
  digitalWrite(trig, HIGH);
  delayMicroseconds(10);
  digitalWrite(trig, LOW);
  t = pulseIn(echo, HIGH);
  h = t*0.034/2;
  if(h>10 && flag==1){
    flag=0;
    sendDataToFirebase();
  }
 if(h<10 && flag==0){
  flag=1;
 }
  Serial.print(h);
  delay(1000);
}

void sendDataToFirebase() {
    //String data ="******"; /* your device id goes here */
    Serial.println("Send data...");
    int value= client.connect("yourwebsitename.000webhostapp.com", 80);
    Serial.println(value);
    if (value) {
        Serial.println("Connected to the server..\n");
        //String d=String(data.length());
        client.print("GET /aqua1.php?id=******* HTTP/1.1\r\n"); /* your device id goes here */
        client.print("Host: yourwebsitename.000webhostapp.com\r\n");
        client.print("Connection: close\r\n");
        client.println();
        while(client.connected()) {
              while(client.available()) {
                       Serial.write(client.read());
                       delay(100);
              }
        }
    }
    Serial.println("Finished!");
    client.flush();
    client.stop();
}
