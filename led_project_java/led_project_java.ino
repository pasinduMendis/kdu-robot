char ip;
void setup()
{
Serial.begin(9600);
pinMode(13,OUTPUT);
}

void loop()
{
   
  if (Serial.available() > 0)
{
   ip = Serial.read();
   if(ip == 'f')
   {
 digitalWrite(13, LOW);
 }
  if(ip == 'b')
 {
 digitalWrite(13, HIGH);
 
 }
}
}
