char ip;
void setup()
{
  Serial.begin(9600);
  pinMode(13, OUTPUT);
  digitalWrite(13, LOW);
}

void loop()
{
  //digitalWrite(13, HIGH);
  if (Serial.available() > 0)
  {
    ip = Serial.read();
    if (ip == '1')
    {
      digitalWrite(13, LOW);
    }
    else
    {
      digitalWrite(13, HIGH);

    }
  }
}
